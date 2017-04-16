package com.test.etermax.test_saravillarreal.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.test.etermax.test_saravillarreal.R;
import com.test.etermax.test_saravillarreal.bean.ItemFlickr;

import java.util.ArrayList;

/**
 * Created by saravillarreal on 4/16/17.
 */

public class ImageAdapter extends BaseAdapter {
    final ArrayList<ItemFlickr> mItems;
    Context mContext;


    /**
     * Default constructor
     * @param items to fill data to
     */
    public ImageAdapter(final ArrayList<ItemFlickr> items, Context context) {
        mItems = items;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(final int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        }

        final TextView text = (TextView) view.findViewById(R.id.firstLine);
        final ImageView mImage = (ImageView) view.findViewById(R.id.icon);
        text.setText(mItems.get(position).getTitle());
        Glide.with(mContext).load(mItems.get(position).getMedia().getM()).asBitmap().centerCrop().into(new BitmapImageViewTarget(mImage));
        return view;
    }
}



