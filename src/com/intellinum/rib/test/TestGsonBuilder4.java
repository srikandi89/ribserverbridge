package com.intellinum.rib.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Otniel on 4/21/2015.
 * This is the algorithm based on the result
 * 1. Iterate all json which has array type (already done)
 * 2. Store every node(json element inside array) to the HashMap
 *    key is integer, and the value is Array JsonElement
 * 3. The HashMap key is filled with iterator variable
 * 4. When attempt to fill, read the child level number(iterator)
 *    then find that number on HashMap
 *    if found, then add the node(JsonElement) into the HashMap
 *    otherwise, add new element into HashMap with the new key
 */
public class TestGsonBuilder4 {
    public static HashMap<Integer, ArrayList<JsonElement>> nodes = new HashMap<>();

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
        ArrayList<JsonElement> elements;

        if(element.isJsonArray()){
            for (JsonElement e : element.getAsJsonArray()){
                //System.out.println("Array #" + iterator + " :" + e.toString());
                metaDataProcessor(tempCurrJson, tempCurrJson, e.toString(), tempIterator, maxLevel);
            }
        }

        if(element.isJsonObject()){
            jsonObject              = element.getAsJsonObject();
            JsonElement tempElement = new JsonParser().parse(jsonObject.toString());
            JsonObject tempObject   = tempElement.getAsJsonObject();
            String tempJson;

            if(tempObject.has("children")){
                tempObject.remove("children");
                tempJson = tempObject.toString();
                tempElement = new JsonParser().parse(tempJson);
            }

            if(nodes.isEmpty()){
                elements = new ArrayList<>();
                nodes.put(iterator, elements);
            }
            if(!nodes.containsKey(iterator)){
                elements = new ArrayList<>();
                nodes.put(iterator, elements);
            }
            nodes.get(iterator).add(tempElement);

            if (jsonObject.has("children")){
                tempNextJson        = jsonObject.get("children").toString();
                tempCurrJson        = jsonObject.toString();

                tempIterator++;
                metaDataProcessor(tempNextJson, tempCurrJson, tempNextJson, tempIterator, maxLevel);
            }
        }

        return "";
    }

    public static void main(String[] args){
        String ribJson = jsonReader("D:\\Projects\\Rapid Interface Builder\\JsonTestCase\\rib3.json").replace("\t","");

        metaDataProcessor(ribJson, ribJson, ribJson, 0, 2);
        System.out.println();
        for(Map.Entry<Integer, ArrayList<JsonElement>> entry : nodes.entrySet()){
            int number = entry.getKey();
            ArrayList<JsonElement> elements = entry.getValue();
            for(int i=0; i<elements.size(); i++){
                System.out.println("#"+number+":"+elements.get(i).toString());
            }
            System.out.println();
        }
    }
}
