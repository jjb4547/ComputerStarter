package com.example.computerstarter.Build;

import java.util.ArrayList;

public class Build_Data{
    private ArrayList<String> build_name;
    private ArrayList<String> build_date;
    private ArrayList<String> price;
    private String buildName;
    private String buildDate;
    private int[] partID;
    private int[] numPart;

    private String buildPrice;
    private ArrayList<String> parts_id;
    private ArrayList<String> num_parts;
    private String partsId;
    private String numParts;

    public Build_Data(){}
    public Build_Data(ArrayList<String> build_name, ArrayList<String> build_date, ArrayList<String> price,ArrayList<String> parts_id,ArrayList<String> num_parts){
        this.build_date = build_date;
        this.build_name = build_name;
        this.price = price;
        this.parts_id = parts_id;
        this.num_parts = num_parts;
    }
    public Build_Data(String buildName, String buildDate, String buildPrice,String partsId,String numParts){
        this.buildDate = buildDate;
        this.buildName = buildName;
        this.buildPrice = buildPrice;
        this.partsId= partsId;
        this.numParts = numParts;
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


    public String getNumParts() {
        return numParts;
    }

    public void setNumParts(String numParts) {
        this.numParts = numParts;
    }

    public ArrayList<String> getNum_parts() {
        return num_parts;
    }

    public void setNum_parts(ArrayList<String> num_parts) {
        this.num_parts = num_parts;
    }

    public int[] getNumPart() {
        return numPart;
    }

    public void setNumPart(int[] numPart) {
        this.numPart = numPart;
    }
}
