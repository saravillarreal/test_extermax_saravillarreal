package com.test.etermax.test_saravillarreal.fragments;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.test.etermax.test_saravillarreal.R;
import com.test.etermax.test_saravillarreal.adapters.PhotosAdapter;
import com.test.etermax.test_saravillarreal.bean.APIResponse;
import com.test.etermax.test_saravillarreal.bean.FlickrFeed;
import com.test.etermax.test_saravillarreal.bean.ItemFlickr;
import com.test.etermax.test_saravillarreal.databinding.MainFragmentBinding;
import com.test.etermax.test_saravillarreal.global.TestGlobalValues;
import com.test.etermax.test_saravillarreal.util.AppUtil;
import com.test.etermax.test_saravillarreal.util.JsonUtil;
import com.test.etermax.test_saravillarreal.util.SessionUtil;
import com.test.etermax.test_saravillarreal.web.UrlBase;
import com.test.etermax.test_saravillarreal.web.UrlFlickr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saravillarreal on 4/13/17.
 */

public class MainFragment extends BaseFragment {
    public final static String TAG = "MainFragment";


    private MainFragmentBinding mainFragmentBinding;
    private int page = 1;
    private LinearLayoutManager mLinearLayoutManager;

    private PhotosAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public String query = "";
    ArrayList<ItemFlickr> listPhotosFilter;
    private ArrayList<ItemFlickr> mPhotosList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false );


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mainFragmentBinding.recyclerView.setLayoutManager(mLayoutManager);

        mainFragmentBinding.editTextSearch.addTextChangedListener(searchWatcher);
        mainFragmentBinding.editTextSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mainFragmentBinding.editTextSearch.setCursorVisible(true);
                return false;
            }
        });


        if (SessionUtil.getArrayListItems() == null){
            mPhotosList = SessionUtil.getPreferencesItems(getActivity(), getActivity().getString(R.string.items_arraylist));
            if (mPhotosList.size() > 0){
                mAdapter = new PhotosAdapter(mPhotosList, getActivity());
                mainFragmentBinding.recyclerView.setAdapter(mAdapter);
            }
            else{
                getPhoto();
            }

        }

        else {
            mPhotosList = SessionUtil.getPreferencesItems(getActivity(), getActivity().getString(R.string.items_arraylist));
        }
        getPhoto();

        mainFragmentBinding.flickrPhotoSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshFunctionality();
            }
        });
        //getPhotosFromService(Boolean.FALSE);

        return mainFragmentBinding.getRoot();

    }

    @Override
    protected void setViewsEvents() {
        if(getArguments()!= null){

            mainFragmentBinding.flickrPhotoSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    pullToRefreshFunctionality();
                }
            });
            getPhoto();
        }

        mainFragmentBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastItemPosition != RecyclerView.NO_POSITION && lastItemPosition == (mPhotosList.size() - 1)) {
                    page = page + 1;
                    getPhoto();
                }
            }
        });
    }

    private void pullToRefreshFunctionality() {
        if (AppUtil.isNetworkAvailable(getActivity())) {
            page = 1;
            getPhoto();
            mainFragmentBinding.flickrPhotoSwipeRefresh.setRefreshing(false);
        } else {
            AppUtil.showSnackbar(getContext(), R.string.network_not_available, mainFragmentBinding.flickrPhotoSwipeRefresh);
        }
    }



    public void getPhoto() {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getActivity());
        final String[] data = {""};
        String url = UrlBase.createUrl(UrlFlickr.GET_PUBLIC_PHOTOS_URL);

        // Casts results into the TextView found within the main layout XML with id jsonData


        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                JsonObject obj = new JsonParser().parse(response.toString()).getAsJsonObject();
                FlickrFeed flickrFeed = gson.fromJson(obj, FlickrFeed.class);
                mPhotosList = new ArrayList<>();
                mPhotosList = SessionUtil.getPreferencesItems(getActivity(), getActivity().getString(R.string.items_arraylist));
                for (int i= 0; i< flickrFeed.getItems().size(); i++){
                    mPhotosList.add(flickrFeed.getItems().get(i));
                }


                SessionUtil.setArrayListItems(mPhotosList);
                SessionUtil.savePreferencesItemFlickr(getActivity(),mPhotosList);
                mAdapter = new PhotosAdapter(mPhotosList, getActivity());
                mainFragmentBinding.recyclerView.setAdapter(mAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, error.toString());
            }
        });



        requestQueue.add(stringRequest);

    }

    private void getPhotosService(Boolean isPaging){
        if (AppUtil.isNetworkAvailable(getContext())) {
            getPhoto();
        } else {
            AppUtil.showSnackbar(getContext(), R.string.network_not_available, mainFragmentBinding.flickrPhotoSwipeRefresh);
        }
    }

    /*
    TextWatcher to find in list the element with the 3th character.
     */
    private final TextWatcher searchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s.length() >= 3) {

                query = s.toString();
                listPhotosFilter = new ArrayList<>();
                for (int i= 0; i < mPhotosList.size() ; i++){
                    if (mPhotosList.get(i).getTitle().toUpperCase().contains(query.toUpperCase())){
                        listPhotosFilter.add(mPhotosList.get(i));
                    }
                }
                if (listPhotosFilter != null){
                    mAdapter = new PhotosAdapter(listPhotosFilter, getActivity());
                    mainFragmentBinding.recyclerView.setAdapter(mAdapter);
                    hideSoftKeyboard(getActivity());
                }

            }else if(s.length() == 0) {
                if (mPhotosList != null ){
                    mAdapter = new PhotosAdapter(mPhotosList, getActivity());
                    mainFragmentBinding.recyclerView.setAdapter(mAdapter);
                    //setDoctorAdapter(mListPatient, mHashCenters);
                    hideSoftKeyboard(getActivity());
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                mainFragmentBinding.getRoot().getWindowToken(), 0);
    }



}
