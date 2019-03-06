package com.intellinum.rib.test;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Otniel on 4/22/2015.
 */
class JsonElementReference{
    public int belong;
    public JsonElement element;
}

class  JsonElementTemp{
    public int index;
    public JsonElement element;
}
public class TestGsonBuilder5 {
    public static HashMap<Integer, ArrayList<JsonElementReference>> nodes = new HashMap<>();
    public static int belongs = 0;
    public static int counter1 = 1;
    public static ArrayList<JsonElementTemp> listTempElmt = new ArrayList<>();

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
        if(parent == null || maxLevel < 1){
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
                                    }
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
        JsonElement element = new JsonParser().parse(json);
        JsonObject parent = element.getAsJsonObject();
        // covers maxLevel >=2
        if (parent.has("children")) {
            JsonArray parentChildren = parent.get("children").getAsJsonArray();
            JsonArray newParentChildren = new JsonArray();
            for (int i = 0; i < parentChildren.size(); i++) {
                JsonObject parentChild = parentChildren.get(i).getAsJsonObject();
                if (parentChild.has("children")) {
                    JsonArray children = parentChild.get("children").getAsJsonArray();
                    metaDataProcessor(parent, children, i, 2, maxLevel);
                    parentChildren = parent.get("children").getAsJsonArray();
                    parentChild = parentChildren.get(i).getAsJsonObject();

                    if(maxLevel>=2){
                        newParentChildren.add(parentChild);
                    }

                    else{
                        JsonArray parentGrandChildren = parentChild.get("children").getAsJsonArray();
                        for(int j=0;j<parentGrandChildren.size();j++){
                            JsonObject parentGrandChild = parentGrandChildren.get(j).getAsJsonObject();
                            newParentChildren.add(parentGrandChild);
                        }
                    }

                }
                else{
                    newParentChildren.add(parentChild);
                }
            }
            parent.remove("children");
            parent.add("children", newParentChildren);
        }
        return parent;
    }

    public static void main(String[] args){
        String ribJson = jsonReader("D:\\Projects\\Rapid Interface Builder\\JsonTestCase\\rib3.json").replace("\t", "");

        JsonElement parent = jsonLevelProcessor(ribJson, 2);

        System.out.println();
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(parent));
    }
}
