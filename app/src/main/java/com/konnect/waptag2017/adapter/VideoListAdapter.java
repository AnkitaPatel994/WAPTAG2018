package com.konnect.waptag2017.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.konnect.waptag2017.R;

import java.util.ArrayList;

/**
 * Created by qlooit-9 on 6/4/16.
 */
public class VideoListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> video_title_list = new ArrayList<>();
    private Context mContext;


    public VideoListAdapter(Context applicationContext, ArrayList<String> category_name_list) {
        inflater = LayoutInflater.from(applicationContext);
        this.video_title_list = category_name_list;
        mContext = applicationContext;

    }

    @Override
    public int getCount() {
        return video_title_list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.video_list, parent, false);
            holder.imgVideo = (ImageView) convertView.findViewById(R.id.imgVideo);
            holder.txtVideoTitle = (TextView) convertView.findViewById(R.id.txtVideoTitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtVideoTitle.setText(video_title_list.get(position));
        if (position == 0) {
            Glide.with(mContext)
                    .load(R.drawable.video_1)
                    .into(holder.imgVideo);
        } else if (position == 1) {
            Glide.with(mContext)
                    .load(R.drawable.video_2)
                    .into(holder.imgVideo);
        } else if (position == 2) {
            Glide.with(mContext)
                    .load(R.drawable.video_3)
                    .into(holder.imgVideo);
        } else {
        }

        return convertView;
    }

    class ViewHolder {

        private TextView txtVideoTitle;
        private ImageView imgVideo;
    }
}
