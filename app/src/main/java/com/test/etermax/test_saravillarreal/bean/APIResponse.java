package com.test.etermax.test_saravillarreal.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by saravillarreal on 4/13/17.
 */

public class APIResponse <T> implements Serializable {

    private ArrayList<T> jsonFlickrFeed;

    public ArrayList<T> getJsonFlickrFeed() {
        return jsonFlickrFeed;
    }

    public void setJsonFlickrFeed(ArrayList<T> jsonFlickrFeed) {
        this.jsonFlickrFeed = jsonFlickrFeed;
    }


}
