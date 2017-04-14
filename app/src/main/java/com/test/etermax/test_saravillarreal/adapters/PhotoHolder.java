package com.test.etermax.test_saravillarreal.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by saravillarreal on 4/13/17.
 */

public class PhotoHolder extends RecyclerView.ViewHolder{


    public ProgressBar mVideoProgressBar;
    public ImageView mVideoImage;
    public ImageView mVideoPlayIcon;
    public TextView mVideoTitle;
    public TextView mVideoDate;
    public ImageView mVideoShareButton;

    public PhotoHolder(View itemView) {
        super(itemView);
        setViews(itemView);
    }

    private void setViews(View view){
      /*  mVideoProgressBar = (ProgressBar) view.findViewById(R.id.video_image_progress);
        mVideoImage = (ImageView) view.findViewById(R.id.video_fight_image);
        mVideoPlayIcon = (ImageView) view.findViewById(R.id.video_fight_play);
        mVideoTitle = (TextView) view.findViewById(R.id.video_fight_title);
        mVideoDate = (TextView) view.findViewById(R.id.video_fight_date);
        mVideoShareButton = (ImageView) view.findViewById(R.id.video_fight_share);*/
    }
}
