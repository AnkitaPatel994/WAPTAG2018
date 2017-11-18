package com.ankita.waptag2018.videos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankita.waptag2018.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kalpe on 11/16/2017.
 */

class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> videoListArray;
    View v;

    public VideoListAdapter(Context context, ArrayList<HashMap<String, String>> videoListArray) {

        this.context = context;
        this.videoListArray = videoListArray;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String title = videoListArray.get(position).get("title");
        final String url = videoListArray.get(position).get("url");

        final String urlVideo = "https://www.youtube.com/embed/"+url;
        String urlImg = "http://img.youtube.com/vi/"+url+"/mqdefault.jpg";

        holder.txtVideoTitle.setText(title);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);

        ImageLoader imageLoader = ImageLoader.getInstance();
        int fallback = 0;
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(fallback)
                .showImageOnFail(fallback)
                .showImageOnLoading(fallback).build();
        imageLoader.displayImage(urlImg,holder.imgVideo, options);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, VideoViewActivity.class);
                i.putExtra("URL",urlVideo);
                i.putExtra("TITLE",title);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgVideo;
        TextView txtVideoTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            imgVideo = (ImageView)itemView.findViewById(R.id.imgVideo);
            txtVideoTitle = (TextView)itemView.findViewById(R.id.txtVideoTitle);

        }
    }
}
