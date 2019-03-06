package com.intellinum.rib.db;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Otniel on 4/15/2015.
 */
public class Model extends QueryHandler {
    protected static QueryHandler find(String query, Object... params){
        return QueryHandler.getHandler(query, params);
    }

    protected static QueryHandler findAll(Object params){
        return QueryHandler.getHandler(params);
    }

    public int save(Object object){
        return QueryHandler.getHandler(object).save();
    }

    public static int getIdCounter(){
        return QueryHandler.getHandler(null).getLatestId();
    }
}
