package com.intellinum.rib.test;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Otniel on 4/20/2015.
 */

public class TestGsonBuilder3 {
    public static ArrayList<JsonElement> nodes = new ArrayList<>();

    public static String jsonReader(String path){
        BufferedReader reader = null;
        String line;
        String content = "";

        try{
            reader = new BufferedReader(new FileReader(path));

            while((line = reader.readLine()) != null){
                content += line;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return content;
    }

    public static String metaDataProcessor(String json, String prevJson, String nextJson, int iterator, int maxLevel){
        String tempPrevJson                 = json;
        String tempNextJson                 = nextJson;
        String tempCurrJson                 = prevJson;
        JsonElement element                 = new JsonParser().parse(tempNextJson);
        int tempIterator                    = iterator;

        JsonObject jsonObject;

        if(element.isJsonArray()){
            for (JsonElement e : element.getAsJsonArray()){
                System.out.println("Array Elmt #"+tempIterator+" :"+e.toString());
                metaDataProcessor(tempCurrJson, tempCurrJson, e.toString(), tempIterator, maxLevel);
            }
        }

        if(element.isJsonObject()){
            jsonObject = element.getAsJsonObject();

            if (jsonObject.has("children")){
                tempNextJson        = jsonObject.get("children").toString();
                tempCurrJson        = jsonObject.toString();
                JsonElement prev    = new JsonParser().parse(tempPrevJson);
                JsonElement next    = new JsonParser().parse(tempNextJson);
                JsonElement curr    = new JsonParser().parse(tempCurrJson);
                nodes.add(prev);
                //System.out.println("Curr #"+tempIterator+" :"+tempCurrJson);
                //System.out.println("Next #"+tempIterator+" :"+tempNextJson);
                //System.out.println("Prev #"+tempIterator+" :"+tempPrevJson);
                //System.out.println();
                tempIterator++;
                metaDataProcessor(tempNextJson, tempCurrJson, tempNextJson, tempIterator, maxLevel);
            }
            else{
                //tempNextJson        = jsonObject.get("children").toString();
                tempCurrJson        = jsonObject.toString();
                System.out.println("Curr #"+tempIterator+" :"+tempCurrJson);
                System.out.println("Next #"+tempIterator+" :"+tempNextJson);
                System.out.println("Prev #"+tempIterator+" :"+tempPrevJson);
                System.out.println();
            }
        }

        return "";
    }

    public static void main(String[] args){
        String ribJson = jsonReader("D:\\Projects\\Rapid Interface Builder\\JsonTestCase\\rib3.json").replace("\t","");

        metaDataProcessor(ribJson, ribJson, ribJson, 1, 2);
    }
}
