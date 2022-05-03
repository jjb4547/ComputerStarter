package com.seniordesign.computerstarter.Build;

import com.example.computerstarter.R;

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
            //System.out.println(e);
        }
        return answer;
    }

    public static String getPriceAsString(int id)
    {
        String answerStr = "";
        double answer = getPrice(id);
        if(answer == 0.00)
        {
            answerStr = "Out of stock.";
            return answerStr;
        }
        answerStr = String.format("$%.2f", answer);

        return answerStr;
    }

    public static String getName(int id)
    {
        String answer = "null";
        try{
            answer = jsonObj.getJSONArray("items").getJSONObject(id).getString("name");
        } catch(JSONException e){
            //System.out.println(e);
        }
        return answer;
    }

    public static String getBrand(int id)
    {
        String answer = "null";
        try{
            answer = jsonObj.getJSONArray("items").getJSONObject(id).getString("icon");
        } catch(JSONException e){
            //System.out.println(e);
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
            case "intel":
                return R.drawable.intelicon;
            case "asus":
                return R.drawable.asus;
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
            //System.out.println(e);
        }
        return answer;
    }

    public static int getLength()
    {
        int i = 1;
        try{
            i = jsonObj.getJSONArray("items").length();
        } catch(JSONException e){
            //System.out.println(e);
        }
        return i;
    }

    public static String getSocket(int id)
    {
        String answer = "null";
        if(getPart(id).equals("cpu") || getPart(id).equals("motherboards"))
        {
            try{
                answer = jsonObj.getJSONArray("items").getJSONObject(id).getString("socket");
            } catch(JSONException e){
                //System.out.println(e);
            }
        }
        return answer;
    }

    public static int getWattage(int id)
    {
        int answer = 0;
        if(!(getPart(id).equals("monitor") || getPart(id).equals("cases")))
        {
            try {
                answer = jsonObj.getJSONArray("items").getJSONObject(id).getInt("wattage");
            } catch (JSONException e) {
                //System.out.println(e);
            }
        }
        return answer;
    }

    public static String getMemType(int id)
    {
        String answer = "null";
        if(getPart(id).equals("memory") || getPart(id).equals("motherboards"))
        {
            try{
                answer = jsonObj.getJSONArray("items").getJSONObject(id).getString("mem_type");
            } catch(JSONException e){
                //System.out.println(e);
            }
        }
        return answer;
    }

    public static int getMemSlots(int id)
    {
        int answer = 0;
        if(getPart(id).equals("memory") || getPart(id).equals("motherboards"))
        {
            try {
                answer = jsonObj.getJSONArray("items").getJSONObject(id).getInt("mem_slots");
            } catch (JSONException e) {
                //System.out.println(e);
            }
        }
        return answer;
    }
}
