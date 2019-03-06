package com.intellinum.rib.test;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Otniel on 4/20/2015.
 */
public class TestGsonBuilder2 {
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

    public static String metaDataProcessor(String json, String prevJson, int iterator, int maxLevel){
        boolean emptyJson                   = false;
        String tempJson                     = json;
        String tempPrevJson                 = prevJson;
        GsonBuilder builder                 = new GsonBuilder();
        Gson gson                           = builder.create();
        HashMap<String, String> properties  = new HashMap<String, String>();
        JsonElement element                 = new JsonParser().parse(tempJson);
        JsonObject jsonObject               = new JsonObject();
        int tempIterator                    = iterator;

        if(element.isJsonArray()){
            for (JsonElement e : element.getAsJsonArray()){
                metaDataProcessor(e.toString(), tempPrevJson, tempIterator, maxLevel);
            }
        }

        if(element.isJsonObject()){
            jsonObject = element.getAsJsonObject();
            /*
            if(tempIterator >= 2){
                System.out.println("Last element :"+json);
                System.out.println("Prev element :"+prevJson);
                JsonElement prevElement = new JsonParser().parse(prevJson);
                JsonElement lastElement = new JsonParser().parse(json);
                for(JsonElement e : lastElement.getAsJsonArray()){
                    for(Map.Entry<String, JsonElement> m : e.getAsJsonObject().entrySet()){
                        System.out.println("Key #"+tempIterator+":"+m.getKey()+" > Value #"+tempIterator+":"+m.getValue().toString());
                        e.getAsJsonObject().add(m.getKey(), m.getValue());
                    }
                }
                System.out.println();
            }
            */

            if (jsonObject.has("children")){
                tempJson = jsonObject.get("children").toString();
                tempPrevJson = jsonObject.toString();
                System.out.println("Next Json :"+tempIterator+"# :"+tempJson);
                System.out.println("Prev Json :"+tempIterator+"# :"+tempPrevJson);
                tempIterator++;
                metaDataProcessor(tempJson, tempPrevJson, tempIterator, maxLevel);
            }
        }

        return tempJson;
    }

    public static void main(String[] args){
        String ribJson = jsonReader("D:\\Projects\\Rapid Interface Builder\\JsonTestCase\\rib3.json").replace("\t","");

        metaDataProcessor(ribJson, ribJson, 1, 2);
    }
}
