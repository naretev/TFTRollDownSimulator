package com.naretevprojects.tft.rolldownsimulator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.naretevprojects.tft.rolldownsimulator.service.Shops;
import com.naretevprojects.tft.rolldownsimulator.service.UnitData;

public class ShopsTest {
    @Test
    public void getSameTierUnits() {
        List<UnitData> unitDataList = new ArrayList<>();
        UnitData unitData = new UnitData(3,0,3);
        unitDataList.add(unitData);

        Shops shops = new Shops(unitDataList);
        int result = shops.getSameTierUnits();
        assertEquals(0, result);

        shops.addSameTierUnit();
        result = shops.getSameTierUnits();
        assertEquals(1, result);

        shops.addSameTierUnit();
        result = shops.getSameTierUnits();
        assertEquals(2, result);

        shops = new Shops(unitDataList);
        assertEquals(0, shops.getSameTierUnits());
    }

    @Test
    public void getCopies() {
        List<UnitData> unitDataList = new ArrayList<>();
        UnitData unitData1 = new UnitData(3,0,3);
        UnitData unitData2 = new UnitData(9,6,7);
        unitDataList.add(unitData1);
        unitDataList.add(unitData2);

        Shops shops = new Shops(unitDataList);
        int result = shops.getCopies(unitData1);
        assertEquals(0, result);

        shops.addCopy(unitData1);
        result = shops.getCopies(unitData1);
        assertEquals(1, result);

        shops.addCopy(unitData1);
        result = shops.getCopies(unitData1);
        assertEquals(2, result);

        shops.addCopy(unitData2);
        result = shops.getCopies(unitData2);
        assertEquals(1, result);

        result = shops.getCopies();
        assertEquals(3, result);

        shops = new Shops(unitDataList);
        assertEquals(0, shops.getCopies());
    }
}