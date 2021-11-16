package com.example.computerstarter;

public class Build_Data {
    private String build_name;
    private String build_date;
    private Integer build_image;
    private Integer price;

    public Build_Data(String build_name,String build_date,Integer build_image, Integer price){
        this.build_date = build_date;
        this.build_image = build_image;
        this.build_name = build_name;
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBuild_name() {
        return build_name;
    }

    public void setBuild_name(String build_name) {
        this.build_name = build_name;
    }

    public String getBuild_date() {
        return build_date;
    }

    public void setBuild_date(String build_date) {
        this.build_date = build_date;
    }

    public Integer getBuild_image() {
        return build_image;
    }

    public void setBuild_image(Integer build_image) {
        this.build_image = build_image;
    }
}
