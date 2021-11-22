package com.example.computerstarter;

import org.json.JSONException;
import org.json.JSONObject;

public class PriceList {
    public static JSONObject jsonObj;

    public static double getPrice(int id)
    {
        double answer = 0.0;
        try{
             answer = jsonObj.getJSONArray("items").getJSONObject(id).getDouble("price");
        } catch(JSONException e){
            System.out.println(e);
        }
        return answer;
    }

    public static String getPriceAsString(int id)
    {
        double answer = getPrice(id);
        String answerStr = "$" + answer;

        return answerStr;
    }

    public static String getName(int id)
    {
        String answer = "null";
        try{
            answer = jsonObj.getJSONArray("items").getJSONObject(id).getString("name");
        } catch(JSONException e){
            System.out.println(e);
        }
        return answer;
    }

    public static String getBrand(int id)
    {
        String answer = "null";
        try{
            answer = jsonObj.getJSONArray("items").getJSONObject(id).getString("icon");
        } catch(JSONException e){
            System.out.println(e);
        }
        return answer;
    }

    public static int getIcon(int id)
    {
        String brand = getBrand(id);
        switch(brand)
        {
            case "ryzen":
                return R.drawable.ryzen;
            case "nvidia":
                return R.drawable.nvidia;
            case "samsung":
                return R.drawable.samsung;
            case "corsair":
                return R.drawable.corsair;
            case "evga":
                return R.drawable.evga;
            case "wd":
                return R.drawable.wd;
            case "t-force":
                return R.drawable.t_force;
            case "zotac":
                return R.drawable.zotac;
            default:
                return R.drawable.amd_cpu;
        }
    }

    public static String getPart(int id)
    {
        String answer = "null";
        try{
            answer = jsonObj.getJSONArray("items").getJSONObject(id).getString("part");
        } catch(JSONException e){
            System.out.println(e);
        }
        return answer;
    }

    public static int getLength()
    {
        int i = 1;
        try{
            i = jsonObj.getJSONArray("items").length();
        } catch(JSONException e){
            System.out.println(e);
        }
        return i;
    }

}
