package com.test.etermax.test_saravillarreal.web;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.test.etermax.test_saravillarreal.util.JsonArrayRequest;
import com.test.etermax.test_saravillarreal.util.MySingletonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by saravillarreal on 4/13/17.
 */

public class UrlFlickr {

    private static final String LOG_TAG = "UrlFlickr";


    public final static String GET_PUBLIC_PHOTOS_URL = "/photos_public.gne?format=json&api_key=c1e485c42500fde73f2c35252971c2fb&nojsoncallback=1";
    public final static String FORMAT = "format";
    public final static String API_KEY = "api_key";




    public static JsonArrayRequest jsonObjectRequest = null;

    public static void getPhotosFlickr(Context context, int page, Response
            .Listener<JsonArrayRequest> listener, Response.ErrorListener errorListener) {

        JSONObject mVideoJsonObject = null;
        try {
            mVideoJsonObject = new JSONObject();
            mVideoJsonObject.put(FORMAT, "json");
            mVideoJsonObject.put(API_KEY, UrlBase.API_KEY);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String photosUrl = UrlBase.createUrl(GET_PUBLIC_PHOTOS_URL);

        Log.i(LOG_TAG, photosUrl);
        Log.i(LOG_TAG, mVideoJsonObject.toString());

        //jsonObjectRequest = new JsonArrayRequest(photosUrl, listener, errorListener);
        jsonObjectRequest.setShouldCache(false);
        MySingletonUtil.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
