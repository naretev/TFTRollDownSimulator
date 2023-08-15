package com.naretevprojects.tft.rolldownsimulator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.naretevprojects.tft.rolldownsimulator.service.TFTBoard;
import com.naretevprojects.tft.rolldownsimulator.service.UnitData;

public class TFTBoardTest {

    @Test
    public void TFTBoardGeneralTest() {
        List<UnitData> unitDataList = new ArrayList<>();
        UnitData unitData = new UnitData(3,0,3);
        unitDataList.add(unitData);

        TFTBoard board = new TFTBoard(unitDataList);
        int[] expectedAnswers = {9, 8, 9, 8, 7, 8, 7, 6};
        for (int i = 0; i < 10; i++) {
            board.buySameTierUnit();
        }
        assertEquals(9, board.getSameTierUnits());
        for (int i = 0; i < 8; i++) {
            unitData.currentCopies++;
            board.buyDesiredUnit(unitData);
            board.buySameTierUnit();
            assertEquals(expectedAnswers[i], board.getSameTierUnits());
        }
    }
}