package com.test.etermax.test_saravillarreal.util;

import android.content.Context;
import android.util.Log;

import com.test.etermax.test_saravillarreal.global.TestGlobalValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by saravillarreal on 4/13/17.
 */

public class JsonUtil {

    public static JSONArray getJsonArray(String objectName, JSONObject jsonObject){
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray(objectName);

        }catch(JSONException e){
            Log.e(TestGlobalValues.TRACE_ID,"Error traying to get a jsonarray with the name: "+objectName,e);
        }
        return jsonArray;
    }

    public static JSONObject getJsonObject(String objectName, JSONObject jsonObjectParam){
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonObjectParam.getJSONObject(objectName);

        }catch(JSONException e){
            Log.e(TestGlobalValues.TRACE_ID,"Error traying to get a jsonObject with the name: "+objectName,e);
        }
        return jsonObject;
    }

    public static <T> T getEntityFromObject(JSONObject jsonObject, Type type, Context context) throws Exception{
        return MySingletonUtil.getInstance(context).getGSON().fromJson(jsonObject.toString(), type);
    }

    public static <T> T getEntityFromJSON(String jsonObject, Type type, Context context){

        return MySingletonUtil.getInstance(context).getGSON().fromJson(jsonObject, type);
    }
}
