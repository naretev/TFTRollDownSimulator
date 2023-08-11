package com.naretevprojects.tft.rolldownsimulator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to add, return and store data about other player's shops.
 */
public class Shops {

    private final Map<UnitData, Integer> unitCopiesMap;
    private int sameTierUnits;

    public Shops(List<UnitData> unitDataList) {
        this.unitCopiesMap = new HashMap<>();
        this.sameTierUnits = 0;

        for (UnitData unitData : unitDataList) {
            unitCopiesMap.put(unitData, 0);
        }
    }

    public int getSameTierUnits() {
        return sameTierUnits;
    }

    public int getCopies() {
        int totalCopies = 0;
        for (int copies : unitCopiesMap.values()) {
            totalCopies += copies;
        }
        return totalCopies;
    }

    public void addCopy(UnitData unitData) {
        unitCopiesMap.put(unitData, unitCopiesMap.get(unitData) + 1);
    }

    public void addSameTierUnit() {
        sameTierUnits++;
    }

    public int getCopies(UnitData unitData) {
        return unitCopiesMap.get(unitData);
    }
}