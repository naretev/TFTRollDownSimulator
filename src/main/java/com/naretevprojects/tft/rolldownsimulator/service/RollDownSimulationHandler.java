package com.naretevprojects.tft.rolldownsimulator.service;

import java.util.List;

/**
 * Interface for TFT Rolldown Simulation
 */
public interface RollDownSimulationHandler {

  /**
   * @param tier Tier of the unit(s) being searched for
   * @param level The level of the player
   * @param poolConcentration The number of same tiered units bought by other players
   * @param championDuplicators The number of champion duplicators to be used in conjunction with rolling
   * @param levelList A list of the other players levels
   * @param unitDataList A list containing data about the units being searched for
   * @param iterations the number of iterations being done at the simulation
   * @return An integer list containing the result of each simulation
   */
  List<Integer> simulateRolls(int tier, int level, int poolConcentration, int championDuplicators,
    List<Integer> levelList, List<UnitData> unitDataList, int iterations);
}
