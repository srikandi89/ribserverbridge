package com.intellinum.rib.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intellinum.rib.controller.PageSave;
import com.intellinum.rib.model.Component;
import com.intellinum.rib.model.Container;
import com.intellinum.rib.model.Page;
import com.intellinum.rib.model.Property;
import com.intellinum.rib.parser.RibJsonParser;

import java.util.List;
import java.util.Map;

/**
 * Created by Otniel on 4/16/2015.
 */
public class TestModel {
    public static void main(String[] args){
        RibJsonParser parser = new RibJsonParser();
        String ribJson  = parser.jsonReader("D:\\Projects\\Rapid Interface Builder\\JsonTestCase\\simple_page_1.json").replace("\t", "");
        JsonObject page = parser.parsePage(ribJson, 2).getAsJsonObject();
        //System.out.println(ribJson);
        //System.out.println(page);

        Page p      = new Page();
        //p.pageId    = page.get("page_id").getAsInt();
        //p.pageName  = page.get("page_name").getAsString();
        //p.ribJson   = ribJson;
        //p.save(p);

        //System.out.println("Latest id :"+p.getLatestId());

        JsonObject properties = page.get("properties").getAsJsonObject();

        for(Map.Entry<String, JsonElement> entry : properties.entrySet()){
            String propertyName     = entry.getKey();
            String propertyValue    = entry.getValue().getAsString();

            Property property = new Property();
            property.propertyId = p.pageId;

            property.propertyName = propertyName;
            property.propertyValue = propertyValue;
            //property.save(property);
        }

        Component c = new Component();
        c.componentType = "test";
        c.componentId = 2;
        c.containerId = 2;
        c.sequence = 2;

        //int status = c.save(c);

        PageSave pageSave = new PageSave();

        //pageSave.savePage(ribJson);

        Property property = new Property();

        List<Component> list = c.findAll();
        System.out.println(list.size());
        for(int i=0; i<list.size(); i++){
            System.out.println("component id :"+list.get(i).componentId);
            System.out.println("container id :"+list.get(i).containerId);
            System.out.println("component type :"+list.get(i).componentType);
            System.out.println("sequence :"+list.get(i).sequence);
            System.out.println("List Property :");
            for(int j=0; j<list.get(i).property.size(); j++){
                System.out.println("------------------------");
                System.out.println("Property ID :"+list.get(i).property.get(j).propertyId);
                System.out.println("Property Name :"+list.get(i).property.get(j).propertyName);
                System.out.println("Property Value :"+list.get(i).property.get(j).propertyValue);
            }
            System.out.println("------------");
        }
        System.out.println("Test single row");
        System.out.println("Component 0 :"+list.get(0).componentId);
        for(int i=0; i<list.get(0).property.size(); i++){
            System.out.println("Property ID :"+list.get(0).property.get(i).propertyId+" Property Name :"+list.get(0).property.get(i).propertyName+" Property Value :"+list.get(0).property.get(i).propertyValue);
        }
        List<Property> list2 = property.findAll();

        System.out.println("Properties :"+list2.size());

        for(int i=0; i<list2.size(); i++){
            System.out.println("Property "+(i+1)+" :"+list2.get(i).propertyId+" > "+list2.get(i).propertyName+" > "+list2.get(i).propertyValue);
        }


        System.out.println("Container");
        Container container = new Container();
        List<Container> list3 = container.findAll();

        System.out.println("List container size :"+list3.size());
        for(int i=0; i<list3.size(); i++){
            System.out.println("container "+(i+1)+" pageId :"+list3.get(i).pageId+" > containerId :"+list3.get(i).containerId+" > containerType :"+list3.get(i).containerType+" > "+list3.get(i).sequence);
        }
        /*
        Page page2 = new Page();
        List<Page> list4 = page2.findAll();

        System.out.println("List page size :"+list4.size());

        for(int i=0; i<list4.size(); i++){
            System.out.println("id : "+list4.get(i).pageId+" > page name : "+list4.get(i).pageName+" > json : "+list4.get(i).ribJson);
        }
        */
        /*
        System.out.println("Test fetchQuery()");
        Component newComponent = new Component();
        List<Component> componentList = newComponent.findByQuery();
        System.out.println("Component list size :"+componentList.size());

        for(int i=0; i<componentList.size(); i++){
            System.out.println("Component ID :"+componentList.get(i).componentId);
            System.out.println("Component Type :"+componentList.get(i).componentType);
            System.out.println("Container ID :"+componentList.get(i).containerId);
            System.out.println("Component Sequence :"+componentList.get(i).sequence);
            System.out.println("---------- Properties ----------");
            for(int j=0; j<componentList.get(i).property.size(); j++){
                System.out.println("\tProperty ID \t:"+componentList.get(i).property.get(j).propertyId);
                System.out.println("\tProperty Name \t:"+componentList.get(i).property.get(j).propertyName);
                System.out.println("\tProperty Value \t:"+componentList.get(i).property.get(j).propertyValue);
            }
            System.out.println("---------------------");
        }
        */
    }
}
