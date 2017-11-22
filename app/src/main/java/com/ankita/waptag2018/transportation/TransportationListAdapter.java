package com.ankita.waptag2018.transportation;

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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kalpe on 11/13/2017.
 */

class TransportationListAdapter extends RecyclerView.Adapter<TransportationListAdapter.ViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> transportationListArray;
    View v;

    public TransportationListAdapter(Context context, ArrayList<HashMap<String, String>> transportationListArray) {

        this.context = context;
        this.transportationListArray = transportationListArray;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transportation_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        /*String title = transportationListArray.get(position).get("title");
        String description = transportationListArray.get(position).get("description");*/

        String image = transportationListArray.get(position).get("image");

        /*holder.tvTransportationTitle.setText(title);
        holder.tvTransportationDescription.setText(description);*/

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
        imageLoader.displayImage(image,holder.ivTransportation, options);

    }

    @Override
    public int getItemCount() {
        return transportationListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /*TextView tvTransportationTitle,tvTransportationDescription;*/
        ImageView ivTransportation;

        public ViewHolder(View itemView) {
            super(itemView);

            /*tvTransportationTitle = (TextView)itemView.findViewById(R.id.tvTransportationTitle);
            tvTransportationDescription = (TextView)itemView.findViewById(R.id.tvTransportationDescription);*/

            ivTransportation = (ImageView)itemView.findViewById(R.id.ivTransportation);
        }
    }
}
