package com.ankita.waptag2018.attraction;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kalpe on 11/11/2017.
 */

class AttractionListAdapter extends RecyclerView.Adapter<AttractionListAdapter.ViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> attractionListArray;

    public AttractionListAdapter(Context context, ArrayList<HashMap<String, String>> attractionListArray) {

        this.context = context;
        this.attractionListArray = attractionListArray;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_attraction_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String title = attractionListArray.get(position).get("title");
        String image = attractionListArray.get(position).get("image");

        holder.txtAttraction.setText(title);

        /*DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
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
        imageLoader.displayImage(image,holder.imgAttraction, options);*/

        Picasso.with(context).load(image).into(holder.imgAttraction);

    }

    @Override
    public int getItemCount() {
        return attractionListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtAttraction;
        ImageView imgAttraction;

        public ViewHolder(View itemView) {
            super(itemView);

            txtAttraction = (TextView) itemView.findViewById(R.id.txtAttraction);
            imgAttraction = (ImageView) itemView.findViewById(R.id.imgAttraction);

        }
    }
}
