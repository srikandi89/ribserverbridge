package com.intellinum.rib.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * Created by Otniel on 4/14/2015.
 */
public class RequestHandler extends AbstractHandler {
    public String getJsonData(HttpServletRequest httpServletRequest) throws IOException {
        Map<String, String[]> params = httpServletRequest.getParameterMap();

        if(params.containsKey("callback")){
            System.out.println("Callback found :"+params.get("callback")[0]);
        }
        else{
            System.out.println("Callback not exist");
        }

        String json = params.get("_data").toString();

        return json;
    }

    public ArrayList<String> listParameterNames(HttpServletRequest httpServletRequest) throws IOException {
        ArrayList<String> params    = new ArrayList<>();
        Enumeration<String> names   = httpServletRequest.getParameterNames();

        while(names.hasMoreElements()){
            params.add(names.nextElement());
        }

        return params;
    }

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        //httpServletResponse.setContentType("text/html;charset=utf-8");
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        request.setHandled(true);

        String contextPath = httpServletRequest.getPathInfo();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String jsonRib = getJsonData(httpServletRequest);

        System.out.println("Json rib :"+jsonRib);
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new RequestHandler());
        server.start();
        server.join();
    }
}
