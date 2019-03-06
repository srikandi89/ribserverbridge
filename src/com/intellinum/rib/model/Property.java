package com.intellinum.rib.model;

import com.intellinum.rib.db.Model;

import java.util.List;

/**
 * Created by Otniel on 4/30/2015.
 */
public class Property extends Model {
    public int propertyId;
    public String propertyName;
    public String propertyValue;

    public List<Property> findAll(){
        return Property.findAll(this).fetch();
    }
}
