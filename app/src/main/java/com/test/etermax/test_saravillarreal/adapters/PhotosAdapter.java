package com.test.etermax.test_saravillarreal.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.test.etermax.test_saravillarreal.R;
import com.test.etermax.test_saravillarreal.bean.FlickrFeed;
import com.test.etermax.test_saravillarreal.bean.ItemFlickr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saravillarreal on 4/13/17.
 */

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {
    private ArrayList<ItemFlickr> values;
    Context mContex;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader, txtTitle, txtAuthor, txtLink, txtDescription, txtDate, txtTags;
        public ImageView mImage;
        public View layout;
        public LinearLayout linearLayoutInfo;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtTitle = (TextView) v.findViewById(R.id.title_info);
            txtAuthor = (TextView) v.findViewById(R.id.author_info);
            txtLink = (TextView) v.findViewById(R.id.link_info);
            txtDescription = (TextView) v.findViewById(R.id.description_info);
            txtDate = (TextView) v.findViewById(R.id.date_taken_info);
            txtTags = (TextView) v.findViewById(R.id.tags_info);
            linearLayoutInfo = (LinearLayout) v.findViewById(R.id.linear_info);

            mImage = (ImageView) v.findViewById(R.id.icon);
        }
    }

    public void add(int position, String item) {
        //values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PhotosAdapter(ArrayList<ItemFlickr> myDataset, Context context) {
        values = myDataset;
        mContex = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PhotosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = values.get(position).getTitle();
        holder.txtHeader.setText(name);
        holder.mImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.linearLayoutInfo.getVisibility() == View.GONE){
                    holder.linearLayoutInfo.setVisibility(View.VISIBLE);
                    holder.txtTitle.setText(values.get(position).getTitle());
                    holder.txtAuthor.setText(values.get(position).getAuthor());
                    holder.txtLink.setText(values.get(position).getLink());
                    //holder.txtDescription.setText(values.get(position).getDescription());
                    holder.txtDate.setText(values.get(position).getDate_taken());
                    holder.txtTags.setText(values.get(position).getTags());

                }
                else{
                    holder.linearLayoutInfo.setVisibility(View.GONE);
                }

            }
        });
       /* holder.txtHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });*/

        Glide.with(mContex).load(values.get(position).getMedia().getM()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.mImage));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}




