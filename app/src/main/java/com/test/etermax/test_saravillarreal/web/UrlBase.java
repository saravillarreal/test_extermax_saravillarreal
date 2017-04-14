package com.test.etermax.test_saravillarreal.web;

/**
 * Created by saravillarreal on 4/13/17.
 */

public class UrlBase {

    public final static String DEVELOPMENT_URL = "https://api.flickr.com/services/feeds";
    public final static String API_KEY = "c1e485c42500fde73f2c35252971c2fb";


    public final static int DEVELOPMENT_ENVIRONMENT = 0;

    public final static int ENVIRONMENT = DEVELOPMENT_ENVIRONMENT;



    public static String createUrl(String url){
        if(ENVIRONMENT == DEVELOPMENT_ENVIRONMENT){
            return DEVELOPMENT_URL + url;
        }
        return null;
    }

}
