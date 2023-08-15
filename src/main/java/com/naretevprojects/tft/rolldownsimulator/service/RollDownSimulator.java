package com.naretevprojects.tft.rolldownsimulator.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class is a Monte Carlo simulator which simulates a player in Teamfight Tactics, rolling for certain units until
 * they've gotten a target number of those units in. This simulator helps estimate the average number of rolls it would
 * take to get such an upgrade.
 */
public class RollDownSimulator {
    private final int poolConcentration;
    private final int championDuplicators;

    private final int shopOdds;
    private final int numberOfUnits;
    private final int numberOfCopies;
    private final List<Integer> otherPlayerShopOdds;
    private final List<UnitData> unitDataList;
    private final Random random;

    /**
     * The constructor
     * @param tier Tier of the unit(s) being searched for
     * @param level The level of the player
     * @param poolConcentration The number of same tiered units bought by other players
     * @param championDuplicators The number of champion duplicators to be used in conjunction with rolling
     * @param levelList A list of the other players levels
     * @param unitDataList A list containing data about the units being searched for
     */
    public RollDownSimulator(int tier, int level, int poolConcentration, int championDuplicators, List<Integer> levelList, List<UnitData> unitDataList) {
        this.poolConcentration = poolConcentration;
        this.championDuplicators = championDuplicators;
        int[][] shopOddsSpreadCheat = {
                {100, 0, 0, 0, 0},
                {100, 0, 0, 0, 0},
                {75, 25, 0, 0, 0},
                {55, 30, 15, 0, 0},
                {45, 33, 20, 2, 0},
                {25, 40, 30, 5, 0},
                {19, 30, 35, 15, 1},
                {16, 20, 35, 25, 4},
                {9, 15, 30, 30, 16},
                {5, 10, 20, 40, 25},
                {1, 2, 12, 50, 35},
        };
        this.shopOdds = shopOddsSpreadCheat[level-1][tier-1];
        int[] numberOfUnitsInTier = {13, 13, 13, 12, 8};
        this.numberOfUnits = numberOfUnitsInTier[tier-1];
        int[] numberOfCopiesInTier = {29, 22, 18, 12, 10};
        this.numberOfCopies = numberOfCopiesInTier[tier-1];
        this.random = new Random();
        this.otherPlayerShopOdds = new ArrayList<>();
        for (Integer otherPlayerLevel : levelList) {
            otherPlayerShopOdds.add(shopOddsSpreadCheat[otherPlayerLevel - 1][tier - 1]);
        }
        this.unitDataList = unitDataList;
    }

    /**
     * This method starts iterating the method which simulates each roll-down. After each iteration it stores the rolls
     * in a result list, which is then returned sorted.
     * @param iterations The number of simulations
     * @return An integer list containing the result of each simulation
     */
    public List<Integer> getRolls(int iterations) {
        //If shopOdds is 0, we can never get units of the correct tier.
        if (shopOdds == 0) return null;

        //At least target number copies needs to be available for us to get a certain upgrade.
        for (UnitData unitData : unitDataList) {
            if (numberOfCopies - unitData.contested < unitData.target) return null;
        }

        List<Integer> numberOfRolls = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            int rolls = simulateRollDown();
            numberOfRolls.add(rolls);
        }

        Collections.sort(numberOfRolls);
        return numberOfRolls;
    }

    /**
     * This method sets up all data which is needed before each call to the recursive method which performs the
     * simulations.
     * @return The number of rolls it took for the simulation to complete
     */
    private int simulateRollDown() {
        for (UnitData unitData : unitDataList) unitData.resetData();

        TFTBoard board = new TFTBoard(unitDataList);
        Shops shops = new Shops(unitDataList);

        simulateOtherPlayersShops(shops);
        for (UnitData unitData : unitDataList) {
            if (numberOfCopies - (shops.getCopies(unitData) + unitData.contested) < unitData.target) return 300;
        }

        return simulateRollDown(board, shops, 0);
    }

    /**
     * This method performs the simulations. Each recursive call represents one roll, and it keeps recursively calling
     * itself until enough copies of each unit is found. To find units it rolls each slot of the shop to see if it is
     * the right tier, if it is, it rolls to see which unit it is. Once a unit roll is performed we know if it is a unit
     * that is being searched for or a random same-tiered unit. The simulation buys units of the same tier as long as
     * there is room to simulate an optimal playstyle which increases odds.
     * @param board The board state
     * @param shops Other players shops
     * @param rolls The current number of rolls
     * @return The total number of rolls reached before the condition was met
     */
    private int simulateRollDown(TFTBoard board, Shops shops, int rolls) {
        if (allCopiesAreFound() || rolls == 300) return rolls;
        rolls++;

        //Loop 5 times so that we roll each slot in the shop.
        for (int i = 0; i < 5; i++) {
            //Rolls a random tier.
            if (desiredTier(shopOdds)) {
                //Get a random unit
                int unitRoll = calculateUnitRoll(shops, board);
                int allCopies = 0;
                boolean desiredUnitBought = false;

                for (UnitData unitData : unitDataList) {
                    if (unitData.currentCopies >= unitData.target) continue;
                    allCopies += unitData.copiesInPool;

                    //Checks to see if the random unit chosen is a desired unit
                    if (unitRoll <= allCopies) {
                        //Buy desired unit and update the board state
                        unitData.currentCopies++;
                        board.buyDesiredUnit(unitData);
                        desiredUnitBought = true;
                        break;
                    }
                }
                //If there was no desired unit, it was a same tiered unit, but it
                if (!desiredUnitBought) {
                    board.buySameTierUnit();
                }
            }
        }
        return simulateRollDown(board, shops, rolls);
    }

    /**
     * This method performs a check to see if enough copies of each unit has been found. It does this with champion
     * duplicators in mind to stop the simulation if it could be finished by using duplicators.
     * @return True if enough copies have been found, else false
     */
    private boolean allCopiesAreFound() {
        int allCopiesFound = 0;
        int totalTarget = 0;

        for (UnitData unitData : unitDataList) {
            totalTarget += unitData.target;
            allCopiesFound += Math.min(unitData.currentCopies, unitData.target);
        }
        return allCopiesFound + championDuplicators >= totalTarget;
    }

    /**
     * This method rolls a shop for each player to account for what other players have in their shops.
     * @param shops The shop class used to store other players shops
     */
    private void simulateOtherPlayersShops(Shops shops) {
        //Loop for each other player in the game
        for (int playerSpecificShopOdds : otherPlayerShopOdds) {
            //Loop for each slot in each player's shop.
            for (int i = 0; i < 5; i++) {
                if (desiredTier(playerSpecificShopOdds)) {
                    //Get a random unit
                    int unitRoll = calculateUnitRoll(shops, null);
                    int allCopies = 0;
                    boolean desiredUnitBought = false;

                    for (UnitData unitData : unitDataList) {
                        allCopies += unitData.copiesInPool;
                        //Checks to see if the random unit chosen is a unit being searched for
                        if (unitRoll <= allCopies) {
                            shops.addCopy(unitData);
                            desiredUnitBought = true;
                            break;
                        }
                    }
                    if (!desiredUnitBought) {
                        shops.addSameTierUnit();
                    }
                }
            }
        }
    }

    /**
     * This method rolls a random number to se if a shop slot should be of the correct tier or not.
     * @param odds The odds to get the right tier
     * @return True if it is the desired tier, else false
     */
    private boolean desiredTier(int odds) {
        //Generate a number 1-100, if it is lower or equal to shop odds, we hit the right tier.
        int tierRoll = random.nextInt(100) + 1;
        return tierRoll <= odds;
    }

    /**
     * This method calculates the total number of units that exists right now, and the total number of copies that exist
     * of the unit being searched for. This info is stored in the UnitData class to be used later. Then a number is
     * generated to represent a random unit.
     * @param shops The shops of other players
     * @param board Representing the players board
     * @return int A number representing a random unit
     */
    private int calculateUnitRoll(Shops shops, TFTBoard board) {
        int unitsOutOfThePool = poolConcentration + shops.getCopies() + shops.getSameTierUnits();
        if (board != null) unitsOutOfThePool += board.getSameTierUnits();

        for (UnitData unitData : unitDataList) {
            int copiesOutOfPool = unitData.currentCopies + unitData.contested + shops.getCopies(unitData);
            unitData.copiesInPool = numberOfCopies - copiesOutOfPool;

            unitsOutOfThePool += unitData.currentCopies + unitData.contested;
        }

        int currentPoolSize = numberOfUnits * numberOfCopies - unitsOutOfThePool;
        return random.nextInt(currentPoolSize) + 1;
    }
}
