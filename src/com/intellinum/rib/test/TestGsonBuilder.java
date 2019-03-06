package com.intellinum.rib.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Otniel on 4/17/2015.
 */
public class TestGsonBuilder {
    class Employee{
        public int idNum;
        public String firstNameStr;
        public String lastNameStr;
        public List<String> rolesLst;
    }

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

    public static void metaDataProcessor(String json){
        GsonBuilder builder                 = new GsonBuilder();
        Gson gson                           = builder.create();
        HashMap<String, String> properties  = new HashMap<String, String>();

        List<JsonObject> tempChildrenJson;
        String tempJson;

        //List<JsonObject> jsonObject = gson.fromJson(json, new TypeToken<List<JsonObject>>(){}.getType());
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        tempJson = jsonObject.get("children").toString();

        JsonElement element = new JsonParser().parse(tempJson);

        if(element.isJsonArray()){
            for(JsonElement e : element.getAsJsonArray()){
                //System.out.println("J > "+e.toString());
                JsonObject jsonObject1 = gson.fromJson(e.toString(), JsonObject.class);
                JsonObject jsonObject2 = e.getAsJsonObject();
                System.out.println("J > "+jsonObject2.toString());
                /*
                if(jsonObject1.has("children")){
                    //System.out.println(jsonObject1.toString());
                    JsonElement jsonElement = new JsonParser().parse(jsonObject1.get("children").toString());
                    for(JsonElement el : jsonElement.getAsJsonArray()){
                        System.out.println(el.toString());
                    }
                }
                */
            }
        }
        if(element.isJsonObject()){
            System.out.println(element.toString());
        }
    }

    public  static  void main(String[] args){
        GsonBuilder builder = new GsonBuilder();
        Gson gson           = builder.create();
        /*
        String json     = "{'idNum':1,'firstNameStr':'Lokesh','lastNameStr':'Gupta','rolesLst':['ADMIN','MANAGER']}";
        String ljson    = "[{'idNum':1,'firstNameStr':'Lokesh','lastNameStr':'Gupta','rolesLst':['ADMIN','MANAGER']},";
        ljson           += "{'idNum':1,'firstNameStr':'Lokesh','lastNameStr':'Gupta','rolesLst':['ADMIN','MANAGER']}]";

        Employee employees = gson.fromJson(json, Employee.class);
        System.out.println("Employee Id :"+employees.idNum);
        System.out.println("Employee First Name :"+employees.firstNameStr);
        System.out.println("Employee Last Name :"+employees.lastNameStr);
        System.out.println("Employee Roles :"+employees.rolesLst.get(0)+" & "+employees.rolesLst.get(1));

        List<Employee> employeeList = gson.fromJson(ljson, new TypeToken<List<Employee>>(){}.getType());

        for(int i=0; i<employeeList.size(); i++){
            System.out.println("Employee Id :"+employeeList.get(i).idNum);
            System.out.println("Employee First Name :"+employeeList.get(i).firstNameStr);
            System.out.println("Employee Last Name :"+employeeList.get(i).lastNameStr);
            System.out.println("Employee Roles :"+employeeList.get(i).rolesLst.get(0)+" & "+employeeList.get(i).rolesLst.get(1));
            System.out.println("----------------------------------------");
        }

        List<JsonObject> jsonObject = gson.fromJson(ljson, new TypeToken<List<JsonObject>>(){}.getType());
        for(int i=0; i<jsonObject.size(); i++){
            System.out.println("#"+i+" - "+jsonObject.get(0).toString());
        }
        */

        String ribJson = jsonReader("D:\\Projects\\Rapid Interface Builder\\JsonTestCase\\rib3.json").replace("\t","");

        metaDataProcessor(ribJson);
    }
}
