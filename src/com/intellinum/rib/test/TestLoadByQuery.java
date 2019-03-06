package com.intellinum.rib.test;

import com.intellinum.rib.model.Component;

import java.util.List;

/**
 * Created by Otniel on 5/19/2015.
 */
public class TestLoadByQuery {
    public static void main(String[] args){
        Component newComponent = new Component();
        List<Component> componentList = newComponent.findByQuery();
        System.out.println("Component list size :"+componentList.size());

        for(int i=0; i<componentList.size(); i++){
            System.out.println("Component ID :"+componentList.get(i).componentId);
            System.out.println("Component Type :"+componentList.get(i).componentType);
            System.out.println("Container ID :"+componentList.get(i).containerId);
            System.out.println("Component Sequence :"+componentList.get(i).sequence);
            System.out.println("---------- Properties ----------");
            System.out.println("Properties size :"+componentList.get(i).property.size());

            for(int j=0; j<componentList.get(i).property.size(); j++){
                System.out.println("\tProperty ID \t:"+componentList.get(i).property.get(j).propertyId);
                System.out.println("\tProperty Name \t:"+componentList.get(i).property.get(j).propertyName);
                System.out.println("\tProperty Value \t:"+componentList.get(i).property.get(j).propertyValue);
            }

            System.out.println("---------------------");
        }
    }
}
