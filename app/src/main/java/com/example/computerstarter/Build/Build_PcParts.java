package com.example.computerstarter.Build;

public class Build_PcParts {
    private String cpuName,motName,memName, storName,psuName,coolName, monName, vgaName, caseName;
    private boolean expand;
    public Build_PcParts(String cpuName, String motName, String memName, String storName, String psuName, String coolName, String monName, String vgaName, String caseName) {
        this.cpuName = cpuName;
        this.motName = motName;
        this.memName = memName;
        this.storName = storName;
        this.psuName = psuName;
        this.coolName = coolName;
        this.monName = monName;
        this.vgaName = vgaName;
        this.caseName = caseName;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public String getMotName() {
        return motName;
    }

    public void setMotName(String motName) {
        this.motName = motName;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getStorName() {
        return storName;
    }

    public void setStorName(String storName) {
        this.storName = storName;
    }

    public String getPsuName() {
        return psuName;
    }

    public void setPsuName(String psuName) {
        this.psuName = psuName;
    }

    public String getCoolName() {
        return coolName;
    }

    public void setCoolName(String coolName) {
        this.coolName = coolName;
    }

    public String getMonName() {
        return monName;
    }

    public void setMonName(String monName) {
        this.monName = monName;
    }

    public String getVgaName() {
        return vgaName;
    }

    public void setVgaName(String vgaName) {
        this.vgaName = vgaName;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
}
