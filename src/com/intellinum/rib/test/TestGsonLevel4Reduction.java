package com.intellinum.rib.test;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Otniel on 4/28/2015.
 */
public class TestGsonLevel4Reduction {
    public static int counter = 1;

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

    public static void metaDataProcessor(JsonObject parent, JsonArray children, int childrenIndex, int iterator, int maxLevel){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if(parent == null || maxLevel < 2){
            // do nothing
        }
        else{
            List<Map<String, Object>> removeCandidate = new ArrayList<Map<String, Object>>();
            // select candidate
            for(int i=0;i<children.size();i++){
                JsonObject child = children.get(i).getAsJsonObject();
                boolean shouldRemove = iterator >= maxLevel;

                if(shouldRemove){
                    Map<String, Object> map = new HashMap<>();
                    map.put("index", i);
                    map.put("child", child);
                    removeCandidate.add(map);
                }
                else if(child.has("children")){
                    JsonObject nextParent = parent.get("children").getAsJsonArray().get(childrenIndex).getAsJsonObject();
                    JsonArray nextChildren = child.get("children").getAsJsonArray();
                    int nextChildrenIndex = i;
                    metaDataProcessor(nextParent, nextChildren, nextChildrenIndex, iterator+1, maxLevel);
                }
            }

            if(!removeCandidate.isEmpty()) {

                // convert parent children to List
                JsonArray jsonArrayParent = parent.get("children").getAsJsonArray();
                JsonArray newJsonArrayParent = new JsonArray();
                // move candidate to up
                for (int i = 0; i < jsonArrayParent.size(); i++) {
                    if (i == childrenIndex) {
                        for (Map<String, Object> map : removeCandidate) {
                            JsonObject child = (JsonObject) map.get("child");

                            int index = (int) map.get("index");

                            children.remove(child);
                            // process recursively
                            JsonObject nextParent = child;
                            if(child.has("children")) {
                                JsonArray nextParentChildren = child.get("children").getAsJsonArray();
                                for (int j = 0; j < nextParentChildren.size(); j++) {
                                    JsonObject nextParentChild = nextParentChildren.get(j).getAsJsonObject();
                                    if (nextParentChild.has("children")) {
                                        JsonArray nextChildren = nextParentChild.get("children").getAsJsonArray();
                                        int nextChildrenIndex = j;
                                        metaDataProcessor(nextParent, nextChildren, nextChildrenIndex, iterator, maxLevel);
                                        nextParentChildren = child.get("children").getAsJsonArray();
                                        counter++;
                                    }
                                }
                            }

                            if(child.has("type")){
                                if(child.get("type").toString().equals("\"Button\"")){
                                    child.addProperty("block", counter);
                                }
                            }

                            newJsonArrayParent.add(child);
                        }
                    } else {
                        newJsonArrayParent.add(jsonArrayParent.get(i));
                    }
                }

                parent.remove("children");
                parent.add("children", newJsonArrayParent);
            }
        }
    }

    public static JsonElement jsonLevelProcessor(String json, int maxLevel){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement element = new JsonParser().parse(json);
        JsonObject parent   = element.getAsJsonObject();

        /**
         * if parent has children, get one more level children (children from children)
         * children element supposed to be array
         */
        if(parent.has("children")){
            JsonArray parentChildren    = parent.get("children").getAsJsonArray();
            JsonArray newParentChildren = new JsonArray();

            /**
             * iterate through parent's children array elements
             * for every elements in array, get as json object
             * then check whether each elements has children or not
             * if has, get the array from element's array
             */
            for(int i=0; i<parentChildren.size(); i++){
                JsonObject parentChildrenItem = parentChildren.get(i).getAsJsonObject();
                if(parentChildrenItem.has("children")){
                    JsonArray children = parentChildrenItem.get("children").getAsJsonArray();
                    metaDataProcessor(parent, children, i, 2, maxLevel);
                }
                else{
                    newParentChildren.add(parentChildrenItem);
                }
            }
        }

        return parent;
    }

    public static void main(String[] args){
        String ribJson = jsonReader("D:\\Projects\\Rapid Interface Builder\\JsonTestCase\\simple_page_1.json").replace("\t", "");
        JsonElement element = new JsonParser().parse(ribJson);

        JsonElement ribStructure = jsonLevelProcessor(ribJson, 2);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //System.out.println(gson.toJson(ribStructure));
        //System.out.println("next");

        System.out.println(gson.toJson(element));
    }
}
