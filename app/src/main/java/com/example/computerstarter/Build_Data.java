package com.example.computerstarter;

import java.util.ArrayList;

public class Build_Data{
    private ArrayList<String> build_name;
    private ArrayList<String> build_date;
    //private Integer build_image;
    private ArrayList<Double> price;
    private String buildName;
    private String buildDate;
    private Double buildPrice;
    private Integer cpuid;
    private ArrayList<Integer> partIDS;
    private int motid;

    public Build_Data(){}
    public Build_Data(ArrayList<String> build_name, ArrayList<String> build_date, ArrayList<Double> price){
        this.build_date = build_date;
        //this.build_image = build_image;
        this.build_name = build_name;
        this.price = price;
        this.partIDS = partIDS;
    }
    public Build_Data(String buildName, String buildDate, Double buildPrice){
        this.buildDate = buildDate;
        this.buildName = buildName;
        this.buildPrice = buildPrice;
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

    public Double getBuildPrice() {
        return buildPrice;
    }

    public void setBuildPrice(Double buildPrice) {
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

    public ArrayList<Double> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Double> price) {
        this.price = price;
    }

    public void setBuild_name(ArrayList<String> build_name) {
        this.build_name = build_name;
    }
    

    /*public Integer getBuild_image() {
        return build_image;
    }

    public void setBuild_image(Integer build_image) {
        this.build_image = build_image;
    }

     */
}
