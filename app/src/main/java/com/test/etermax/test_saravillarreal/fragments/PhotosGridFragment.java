package com.test.etermax.test_saravillarreal.fragments;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.test.etermax.test_saravillarreal.R;
import com.test.etermax.test_saravillarreal.adapters.ImageAdapter;
import com.test.etermax.test_saravillarreal.adapters.PhotosAdapter;
import com.test.etermax.test_saravillarreal.bean.FlickrFeed;
import com.test.etermax.test_saravillarreal.bean.ItemFlickr;
import com.test.etermax.test_saravillarreal.databinding.PhotosGridFragmentBinding;
import com.test.etermax.test_saravillarreal.util.AppUtil;
import com.test.etermax.test_saravillarreal.util.SessionUtil;
import com.test.etermax.test_saravillarreal.web.UrlBase;
import com.test.etermax.test_saravillarreal.web.UrlFlickr;

import java.util.ArrayList;

/**
 * Created by saravillarreal on 4/14/17.
 */

public class PhotosGridFragment extends BaseFragment {

    public final static String TAG = "PhotosGridFragment";
    ArrayList<ItemFlickr> listPhotosFilter;
    private ArrayList<ItemFlickr> mPhotosList= new ArrayList<>();

    public String query = "";
    private ImageAdapter mAdapter;
    private int page = 1;

    private PhotosGridFragmentBinding photosGridFragmentBinding;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        photosGridFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.photos_grid_fragment, container, false );

        photosGridFragmentBinding.editTextSearch.addTextChangedListener(searchWatcher);
        photosGridFragmentBinding.editTextSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                photosGridFragmentBinding.editTextSearch.setCursorVisible(true);
                return false;
            }
        });


        if (SessionUtil.getArrayListItems() == null){
            getPhoto();
        }

        else {
            mPhotosList = SessionUtil.getPreferencesItems(getActivity(), getActivity().getString(R.string.items_arraylist));
        }

        mAdapter = new ImageAdapter(mPhotosList, getActivity());
        photosGridFragmentBinding.gridView1.setAdapter(mAdapter);




        photosGridFragmentBinding.gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

            }
        });

        return photosGridFragmentBinding.getRoot();
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
                    mAdapter = new ImageAdapter( listPhotosFilter, getActivity());
                    photosGridFragmentBinding.gridView1.setAdapter(mAdapter);
                    hideSoftKeyboard(getActivity());
                }

            }else if(s.length() == 0) {
                if (mPhotosList != null ){
                    mAdapter = new ImageAdapter( mPhotosList, getActivity());
                    photosGridFragmentBinding.gridView1.setAdapter(mAdapter);
                    hideSoftKeyboard(getActivity());
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void setViewsEvents() {

    }

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                photosGridFragmentBinding.getRoot().getWindowToken(), 0);
    }


    private void pullToRefreshFunctionality() {
        if (AppUtil.isNetworkAvailable(getActivity())) {
            page = 1;
            getPhoto();
            //photosGridFragmentBinding.flickrPhotoSwipeRefresh.setRefreshing(false);
        } else {
            //AppUtil.showSnackbar(getContext(), R.string.network_not_available, photosGridFragmentBinding.flickrPhotoSwipeRefresh);
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
                for (int i= 0; i< flickrFeed.getItems().size(); i++){
                    mPhotosList.add(flickrFeed.getItems().get(i));
                }

                SessionUtil.savePreferencesItemFlickr(getActivity(),mPhotosList);
                mAdapter = new ImageAdapter( mPhotosList, getActivity());
                photosGridFragmentBinding.gridView1.setAdapter(mAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, error.toString());
            }
        });



        requestQueue.add(stringRequest);

    }

}
