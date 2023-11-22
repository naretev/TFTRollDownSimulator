package com.naretevprojects.tft.rolldownsimulator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.naretevprojects.tft.rolldownsimulator.service.RollDownSimulator;
import com.naretevprojects.tft.rolldownsimulator.service.UnitData;

public class RollDownSimulatorTest {

    @Test
    public void getRolls() {
        List<Integer> levelList = new ArrayList<>(Arrays.asList(5,5,5,5,5,6,6));
        List<UnitData> unitDataList = new ArrayList<>();
        unitDataList.add(new UnitData(9, 0, 0));
        unitDataList.add(new UnitData(3, 0, 5));
        unitDataList.add(new UnitData(1, 0, 8));

        RollDownSimulator rollDownSimulator = new RollDownSimulator(1, 5, 45, 0, levelList, unitDataList);

        List<Integer> resultList = rollDownSimulator.getRolls(1000);
        assertTrue(resultList.get(0) > 0);
        assertTrue(resultList.get(resultList.size() - 1) > 0);
        assertEquals(1000, resultList.size());

        rollDownSimulator = new RollDownSimulator(5, 7, 35, 0, levelList, unitDataList);

        resultList = rollDownSimulator.getRolls(1000);
        assertTrue(resultList.get(0) > 0);
        assertEquals(300, (int) resultList.get(resultList.size() - 1));
        assertEquals(1000, resultList.size());

        rollDownSimulator = new RollDownSimulator(5, 1, 45, 0, levelList, unitDataList);

        resultList = rollDownSimulator.getRolls(1000);
        assertNull(resultList);
    }
}