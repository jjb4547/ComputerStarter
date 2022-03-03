package com.example.computerstarter.Build;

import java.util.ArrayList;

public class Build_Data{
    private ArrayList<String> build_name;
    private ArrayList<String> build_date;
    private ArrayList<String> price;
    private String buildName;
    private String buildDate;
    private int[] partID;

    public Build_Data(String cpuName, String motName, String memName, String storName, String psuName, String coolName, String monName, String vgaName, String caseName) {
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

    private String buildPrice;
    private ArrayList<String> parts_id;
    private String partsId;
    private String cpuName,motName,memName, storName,psuName,coolName, monName, vgaName, caseName;

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

    public Build_Data(){}
    public Build_Data(ArrayList<String> build_name, ArrayList<String> build_date, ArrayList<String> price,ArrayList<String> parts_id){
        this.build_date = build_date;
        this.build_name = build_name;
        this.price = price;
        this.parts_id = parts_id;
    }
    public Build_Data(String buildName, String buildDate, String buildPrice,String partsId){
        this.buildDate = buildDate;
        this.buildName = buildName;
        this.buildPrice = buildPrice;
        this.partsId= partsId;
    }
    public ArrayList<String> getParts_id() {
        return parts_id;
    }

    public void setParts_id(ArrayList<String> parts_id) {
        this.parts_id = parts_id;
    }

    public String getPartsId() {
        return partsId;
    }

    public void setPartsId(String partsId) {
        this.partsId = partsId;
    }
    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public String getBuildPrice() {
        return buildPrice;
    }

    public void setBuildPrice(String buildPrice) {
        this.buildPrice = buildPrice;
    }

    public ArrayList<String> getBuild_name() {
        return build_name;
    }

    public ArrayList<String> getBuild_date() {
        return build_date;
    }

    public void setBuild_date(ArrayList<String> build_date) {
        this.build_date = build_date;
    }

    public ArrayList<String> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<String> price) {
        this.price = price;
    }

    public void setBuild_name(ArrayList<String> build_name) {
        this.build_name = build_name;
    }

    public void setCleanID(int[] partID){
        this.partID = partID;
    }
    public int[] getCleanID(){
        return partID;
    }
}
