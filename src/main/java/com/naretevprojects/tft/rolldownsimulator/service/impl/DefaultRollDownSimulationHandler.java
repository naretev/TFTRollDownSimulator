package com.naretevprojects.tft.rolldownsimulator.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.naretevprojects.tft.rolldownsimulator.service.RollDownSimulationHandler;
import com.naretevprojects.tft.rolldownsimulator.service.RollDownSimulator;
import com.naretevprojects.tft.rolldownsimulator.service.UnitData;

import lombok.NoArgsConstructor;

/**
 * Default implementation of the TFT Rolldown Simulation handler
 */
@Component
@NoArgsConstructor
public class DefaultRollDownSimulationHandler implements RollDownSimulationHandler {

  @Override public List<Integer> simulateRolls(int tier, int level, int poolConcentration, int championDuplicators,
    List<Integer> levelList, List<UnitData> unitDataList, int iterations) {

    RollDownSimulator simulator = new RollDownSimulator(tier, level, poolConcentration, championDuplicators, levelList, unitDataList);
    return simulator.getRolls(iterations);
  }
}
