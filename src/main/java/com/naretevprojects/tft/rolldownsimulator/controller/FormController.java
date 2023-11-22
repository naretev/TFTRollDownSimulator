package com.naretevprojects.tft.rolldownsimulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import com.naretevprojects.tft.rolldownsimulator.configuration.TftRolldownSimulatorProperties;
import com.naretevprojects.tft.rolldownsimulator.service.RollDownSimulationHandler;
import com.naretevprojects.tft.rolldownsimulator.service.RollDownSimulator;
import com.naretevprojects.tft.rolldownsimulator.service.UnitData;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@NoArgsConstructor
public class FormController {

    @Autowired
    private RollDownSimulationHandler rollDownSimulationHandler;
    @Autowired
    private TftRolldownSimulatorProperties simulatorProperties;

    @PostMapping("/submit")
    public ResponseEntity<List<Integer>> submitForm(@RequestParam int tier,
                                     @RequestParam int level,
                                     @RequestParam int poolConcentration,
                                     @RequestParam int championDuplicators,
                                     @RequestParam String playerLevels,
                                     //@RequestParam boolean useHeadliners,
                                     @RequestParam int unitData0target,
                                     @RequestParam int unitData0copies,
                                     @RequestParam int unitData0contested,
                                     @RequestParam int unitData1target,
                                     @RequestParam(required = false) Integer unitData1copies,
                                     @RequestParam(required = false) Integer unitData1contested,
                                     @RequestParam int unitData2target,
                                     @RequestParam(required = false) Integer unitData2copies,
                                     @RequestParam(required = false) Integer unitData2contested) {

        //Check input
        if (!playerLevels.matches("^(?:[1-9]|1[0-1])(?:-(?:[1-9]|1[0-1])){0,6}$")) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        //Convert String to List<Integer>
        String[] numbersArray = playerLevels.split("-");
        List<Integer> levelList = new ArrayList<>();

        for (String number : numbersArray) {
            int intValue = Integer.parseInt(number);
            levelList.add(intValue);
        }

        List<UnitData> unitDataList = new ArrayList<>();
        unitDataList.add(new UnitData(unitData0target, unitData0copies, unitData0contested));

        //Only add UnitData objects to the list if there actually are UnitData objects to add
        if (unitData1target != 0) {
            unitDataList.add(new UnitData(unitData1target, unitData1copies, unitData1contested));
        }
        if (unitData2target != 0) {
            unitDataList.add(new UnitData(unitData2target, unitData2copies, unitData2contested));
        }

        List<Integer> resultList = rollDownSimulationHandler.simulateRolls(
          tier, level, poolConcentration,championDuplicators, levelList, unitDataList,
          simulatorProperties.getIterations()
        );

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }
}
