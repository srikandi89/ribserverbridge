package com.intellinum.rib.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intellinum.rib.controller.PageSave;
import com.intellinum.rib.db.Model;
import com.intellinum.rib.model.Page;
import com.intellinum.rib.parser.RibJsonParser;

/**
 * Created by Otniel on 5/13/2015.
 */
public class TestPageSave {
    public static void main(String[] args){
        RibJsonParser parser = new RibJsonParser();
        String ribJson  = parser.jsonReader("D:\\Projects\\Rapid Interface Builder\\JsonTestCase\\page1.json").replace("\t", "");

        int latestId        = Model.getIdCounter();

        System.out.println("Latest id :"+latestId);

        JsonObject page     = parser.parsePage(ribJson, latestId).getAsJsonObject();

        Page p      = new Page();
        p.pageId    = page.get("page_id").getAsInt();

        JsonElement container = parser.parseContainer(ribJson, p.pageId);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //System.out.println(gson.toJson(container));

        PageSave pageSave   = new PageSave();
        pageSave.savePage(ribJson);
    }
}
