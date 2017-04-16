package com.test.etermax.test_saravillarreal.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.etermax.test_saravillarreal.R;
import com.test.etermax.test_saravillarreal.bean.ItemFlickr;

import java.util.ArrayList;

/**
 * Created by saravillarreal on 4/16/17.
 */

public class SessionUtil {

    public final static String TAG = "SessionUtil";
    public  static ArrayList<ItemFlickr> arrayListItems = null;


    public static ArrayList<ItemFlickr> getArrayListItems() {
        return arrayListItems;
    }

    public static void setArrayListItems(ArrayList<ItemFlickr> arrayListItems) {
        SessionUtil.arrayListItems = arrayListItems;
    }

    public static void savePreferencesItemFlickr(Context act, ArrayList<ItemFlickr> items) {
        SharedPreferences prefs = act.getSharedPreferences(act.getString(R.string.items_arraylist), act.MODE_PRIVATE);
        Gson gson = new Gson();
        String longString = gson.toJson(items);
        prefs.edit().putString(act.getString(R.string.items_arraylist), longString).commit();

    }

    public static ArrayList<ItemFlickr> getPreferencesItems(Context mContext, String patron) {

        ArrayList<ItemFlickr> mItem = new ArrayList<>();
        Gson gson = new Gson();
        SharedPreferences prefs = mContext.getSharedPreferences(patron, mContext.MODE_PRIVATE);
        //get from shared prefs
        String storedAdvertisementString = prefs.getString(patron, "error");
        java.lang.reflect.Type type = new TypeToken<ArrayList<ItemFlickr>>() {
        }.getType();
        if (!storedAdvertisementString.equalsIgnoreCase("error")) {
            mItem = gson.fromJson(storedAdvertisementString, type);
        }
        return mItem;

    }

}
