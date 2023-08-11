package com.naretevprojects.tft.rolldownsimulator;

public class UnitData {
    public final int target;
    public final int copies;
    public final int contested;
    public int currentCopies;

    public int copiesInPool;

    public UnitData(int target, int copies, int contested) {
        this.target = target;
        this.copies = copies;
        this.contested = contested;
        this.currentCopies = copies;
        this.copiesInPool = 0;
    }

    public void resetData() {
        this.currentCopies = copies;
        this.copiesInPool = 0;
    }
}
