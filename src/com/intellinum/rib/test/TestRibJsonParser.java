package com.intellinum.rib.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.intellinum.rib.parser.RibJsonParser;

/**
 * Created by Otniel on 5/1/2015.
 */
public class TestRibJsonParser {
    public enum ModelCollection {
        COMPONENT,
        CONTAINER,
        PAGE,
        PROPERTY
    }

    public static void main(String[] args){
        RibJsonParser parser = new RibJsonParser();
        String ribJson = parser.jsonReader("D:\\Projects\\Rapid Interface Builder\\JsonTestCase\\simple_page_1.json").replace("\t", "");
        //RibJsonParser.parsePage(ribJson);
        JsonArray container = parser.parseContainer(ribJson, 1).getAsJsonArray();
        JsonArray component = parser.getComponentList().getAsJsonArray();
        JsonObject property = parser.getPropertyList().getAsJsonObject();
        JsonObject page     = parser.parsePage(ribJson, 1).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println("-- pageId --");
        System.out.println(gson.toJson(page));
        System.out.println("-- containerId --");
        System.out.println(gson.toJson(container));
        System.out.println("-- component --");
        System.out.println(gson.toJson(component));
    }
}
