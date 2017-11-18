package com.ankita.waptag2018.attraction;

/**
 * Created by qlooit-9 on 11/11/16.
 */

import android.os.AsyncTask;
import android.os.Bundle;

import com.ankita.waptag2018.BaseAppCompatActivity;
import com.ankita.waptag2018.Postdata;
import com.ankita.waptag2018.R;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AttractionActivity extends BaseAppCompatActivity {

    private ImageView imgAdv;
    RecyclerView rvAttraction;
    ArrayList<HashMap<String,String>> AttractionListArray=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpActionBar(getString(R.string.title_attraction));
        setContentView(R.layout.attraction_activity);

        imgAdv = (ImageView) findViewById(R.id.imgAdv);

        updateUI(imgAdv);

        rvAttraction = (RecyclerView)findViewById(R.id.rvAttraction);
        rvAttraction.setHasFixedSize(true);

        RecyclerView.LayoutManager rvAttractionManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvAttraction.setLayoutManager(rvAttractionManager);

        if (isNetworkAvailable(getApplicationContext()))
        {
            GetAttraction getAttraction = new GetAttraction();
            getAttraction.execute();
        }
        else
        {
            preToast(getString(R.string.no_internet_connection));
        }

    }

    private class GetAttraction extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joAttraction=new JSONObject();
            try {
                Postdata postdata=new Postdata();
                String pdAttraction = postdata.post(WEB_KEY+API_FETCH_ATTRACTIONS,joAttraction.toString());
                JSONObject j=new JSONObject(pdAttraction);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray Advertise=j.getJSONArray("Attractions");
                    for (int i=0;i<Advertise.length();i++)
                    {
                        JSONObject jo=Advertise.getJSONObject(i);
                        HashMap<String,String > hashMap = new HashMap<>();

                        String image = jo.getString("image");
                        String title = jo.getString("title");

                        hashMap.put("image",image);
                        hashMap.put("title",title);

                        AttractionListArray.add(hashMap);
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
                AttractionListAdapter attractionListAdapter = new AttractionListAdapter(getApplicationContext(),AttractionListArray);
                rvAttraction.setAdapter(attractionListAdapter);
            }
            else
            {
                preToast(message);
            }
        }
    }
}

