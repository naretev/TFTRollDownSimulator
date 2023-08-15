package com.naretevprojects.tft.rolldownsimulator.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The TFTBoard class is used to keep track of all the units on the players board. This information is managed to
 * increase accuracy of the simulation.
 */
public class TFTBoard {
    private final Map<UnitData, Integer> unitCopiesMap;
    private int sameTierUnits;

    /**
     * The constructor takes the number of copies owned of each unit and calculates how slots those units take up on
     * the board. Assuming that the player fields 1 copy of each unit they are attempting to upgrade, this is accurate.
     * @param unitDataList A list of UnitData used for board simulation
     */
    public TFTBoard(List<UnitData> unitDataList) {
        this.unitCopiesMap = new HashMap<>();
        this.sameTierUnits = 0;

        for (UnitData unitData : unitDataList) {
            int boardFootprint = unitData.copies > 8 ? 0 : Math.max(0, (unitData.copies % 3) + (unitData.copies / 3) - 1);

            unitCopiesMap.put(unitData, boardFootprint);
        }
    }

    public void buyDesiredUnit(UnitData unitData) {
        int boardFootprint = unitData.currentCopies > 8 ? 0 : Math.max(0, (unitData.currentCopies % 3) + (unitData.currentCopies / 3) - 1);
        if (unitCopiesMap.get(unitData) < boardFootprint && boardIsFull()) {
            sellSameTierUnit();
        }
        unitCopiesMap.put(unitData, boardFootprint);
    }

    private boolean boardIsFull() {
        int totalBoardFootprint = 0;
        for (int boardFootprint : unitCopiesMap.values()) {
            totalBoardFootprint += boardFootprint;
        }

        int BOARD_SIZE = 9;
        return totalBoardFootprint + sameTierUnits >= BOARD_SIZE;
    }

    public void buySameTierUnit() {
        if (!boardIsFull()) sameTierUnits++;
    }

    public int getSameTierUnits() {
        return sameTierUnits;
    }

    private void sellSameTierUnit() {
        sameTierUnits--;
    }
}
