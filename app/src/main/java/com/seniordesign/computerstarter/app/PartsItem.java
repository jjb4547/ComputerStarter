package com.seniordesign.computerstarter.app;

public class PartsItem {
    private String partsName;
    private int partsIml;

    public PartsItem(String partsName, int partsIml) {
        this.partsName = partsName;
        this.partsIml = partsIml;
    }

    public String getPartsName() {
        return partsName;
    }

    public void setPartsName(String partsName) {
        this.partsName = partsName;
    }

    public int getPartsIml() {
        return partsIml;
    }

    public void setPartsIml(int partsIml) {
        this.partsIml = partsIml;
    }
}
