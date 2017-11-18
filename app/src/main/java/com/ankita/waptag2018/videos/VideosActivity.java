package com.ankita.waptag2018.videos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.ankita.waptag2018.BaseAppCompatActivity;
import com.ankita.waptag2018.Postdata;
import com.ankita.waptag2018.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class VideosActivity extends BaseAppCompatActivity {

    private ImageView imgAdv;
    RecyclerView rvVideo;
    ArrayList<HashMap<String,String>> VideoListArray=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActionBar(getString(R.string.title_videos));
        setContentView(R.layout.videos_activity);

        imgAdv = (ImageView) findViewById(R.id.imgAdv);

        updateUI(imgAdv);

        rvVideo = (RecyclerView)findViewById(R.id.rvVideo);
        rvVideo.setHasFixedSize(true);

        RecyclerView.LayoutManager rvVideoManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvVideo.setLayoutManager(rvVideoManager);

        if (isNetworkAvailable(getApplicationContext()))
        {
            GetVideo getVideo = new GetVideo();
            getVideo.execute();
        }
        else
        {
            preToast(getString(R.string.no_internet_connection));
        }

    }

    private class GetVideo extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startDialog();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject joVideo=new JSONObject();
            try {
                Postdata postdata=new Postdata();
                String pdVideo = postdata.post(WEB_KEY+API_FETCH_VIDEO,joVideo.toString());
                JSONObject j=new JSONObject(pdVideo);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray Advertise=j.getJSONArray("Video");
                    for (int i=0;i<Advertise.length();i++)
                    {
                        JSONObject jo=Advertise.getJSONObject(i);
                        HashMap<String,String > hashMap = new HashMap<>();

                        String title = jo.getString("title");
                        String url = jo.getString("url");

                        hashMap.put("title",title);
                        hashMap.put("url",url);

                        VideoListArray.add(hashMap);
                    }
                }
                else
                {
                    message=j.getString("message");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            closeDialog();
            if(status.equals("1"))
            {
                VideoListAdapter videoListAdapter = new VideoListAdapter(getApplicationContext(),VideoListArray);
                rvVideo.setAdapter(videoListAdapter);
            }
            else
            {
                preToast(message);
            }
        }
    }
}
