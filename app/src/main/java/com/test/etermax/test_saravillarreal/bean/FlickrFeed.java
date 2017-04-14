package com.test.etermax.test_saravillarreal.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by saravillarreal on 4/13/17.
 */

public class FlickrFeed implements Serializable {

    private String title;
    private String link;
    private String description;
    private String modified;
    private String generator;
    private ArrayList<ItemFlickr> items;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public ArrayList<ItemFlickr> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemFlickr> items) {
        this.items = items;
    }



}
