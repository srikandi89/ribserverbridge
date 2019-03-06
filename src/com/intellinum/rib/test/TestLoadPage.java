package com.intellinum.rib.test;

import com.google.gson.*;
import com.intellinum.rib.model.Page;

import java.util.List;

/**
 * Created by Otniel on 5/19/2015.
 */
public class TestLoadPage {
    public String getRibPageJsonList(){
        String pageListJson     = "";
        Page page               = new Page();
        JsonArray listJsonPages = new JsonArray();

        List<Page> pages        = page.findAll();

        for(int i=0; i<pages.size(); i++){
            JsonElement pageElement = new JsonParser().parse(pages.get(i).ribJson);
            listJsonPages.add(pageElement);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        pageListJson = listJsonPages.toString();

        return pageListJson;
    }

    public static void main(String[] args){
        TestLoadPage load = new TestLoadPage();
        System.out.println(load.getRibPageJsonList());

        Page p = new Page();
        List<Page> lists = p.findByQuery();

        for(int i=0; i<lists.size(); i++){
            System.out.println("Page ID :"+lists.get(i).pageId);
            System.out.println("Page Name :"+lists.get(i).pageName);
            System.out.println("---------- Properties ----------");
            for(int j=0; j<lists.get(i).property.size(); j++){
                System.out.println("\tProperty ID \t:"+lists.get(i).property.get(j).propertyId);
                System.out.println("\tProperty Name \t:"+lists.get(i).property.get(j).propertyName);
                System.out.println("\tProperty Value \t:"+lists.get(i).property.get(j).propertyValue);
                System.out.println("---------------------");
            }
        }
    }
}
