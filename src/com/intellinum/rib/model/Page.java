package com.intellinum.rib.model;

import com.intellinum.rib.db.Model;

import java.util.List;

/**
 * Created by Otniel on 4/15/2015.
 */
public class Page extends Model {
    public int pageId;
    public String pageName;
    public String ribJson;

    public List<Property> property;

    public Page(){}

    public Page(int pageId, String pageName, String ribJson){
        this.pageId     = pageId;
        this.pageName   = pageName;
        this.ribJson    = ribJson;
    }

    public Page getPage(int pageId, String pageName, String ribJson){
        Page p = new Page(pageId, pageName, ribJson);

        return p;
    }


    public List<Page> findAll(){
        return Page.findAll(this).fetch();
    }

    public List<Page> findByQuery(){
        return Page.find("SELECT * FROM page WHERE page_id = 19", this).fetchQuery();
    }
}
