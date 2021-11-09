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

}
