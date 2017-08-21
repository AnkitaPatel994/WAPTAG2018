package com.konnect.waptag2017.videos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.konnect.waptag2017.BaseAppCompatActivity;
import com.konnect.waptag2017.R;
import com.konnect.waptag2017.adapter.VideoListAdapter;

import java.util.ArrayList;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class VideosActivity extends BaseAppCompatActivity {

    private TextView txtNoVideo;
    private ListView mListVideo;
    private ArrayList<String> video_url_list = new ArrayList<>();
    private ArrayList<String> video_title_list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActionBar(getString(R.string.title_videos));
        setContentView(R.layout.videos_activity);

        if (!isNetworkAvailable(getApplicationContext())) {
            preToast(getString(R.string.no_internet_connection));
        }
        initControl();

    }

    private void initControl() {

        mListVideo = (ListView) findViewById(R.id.listVideo);
        txtNoVideo = (TextView) findViewById(R.id.txtNoVideo);

        imgAdv = (ImageView) findViewById(R.id.imgAdv);

        updateUI(imgAdv);

        mListVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isNetworkAvailable(getApplicationContext())) {
                    Intent i = new Intent(getApplicationContext(), VideoViewActivity.class);
                    i.putExtra("URL", video_url_list.get(position));
                    i.putExtra("TITLE", video_title_list.get(position));
                    startActivity(i);
                } else {
                    preToast(getString(R.string.no_internet_connection));
                }
            }
        });
        callVideoService();

//        if (isNetworkAvailable(getApplicationContext())) {
//            callVideoService();
//        } else {
//            mListVideo.setVisibility(View.GONE);
//            txtNoVideo.setVisibility(View.VISIBLE);
//            txtNoVideo.setText(getString(R.string.no_internet_connection));
//        }

    }

    private ImageView imgAdv;

    private void callVideoService() {

        //EMBED VIDEO ONLY--------------

        video_url_list.add("https://www.youtube.com/embed/pgNmnAniHPc");
        video_title_list.add("WAPTAG Expo 2016 Glimpse");
        video_url_list.add("https://www.youtube.com/embed/-GKZkTezHeY");
        video_title_list.add("WAPTAG Expo 2017");
        video_url_list.add("https://www.youtube.com/embed/CN4nRA5EGTg");
        video_title_list.add("WAPTAG Expo 2017");
        manageResponse();

    }

    private void manageResponse() {

        VideoListAdapter videoListAdapter = new VideoListAdapter(getApplicationContext(), video_title_list);
        mListVideo.setAdapter(videoListAdapter);

    }
}
