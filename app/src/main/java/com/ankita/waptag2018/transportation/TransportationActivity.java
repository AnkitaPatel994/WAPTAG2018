package com.ankita.waptag2018.transportation;

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
import com.ankita.waptag2018.custom.SwitchAnimationUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by qlooit-9 on 11/11/16.
 */
public class TransportationActivity extends BaseAppCompatActivity {

    private ImageView imgAdv;
    RecyclerView rvTransportation;
    ArrayList<HashMap<String,String>> TransportationListArray=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transportation_activity);

        new SwitchAnimationUtil().startAnimation(getWindow().getDecorView(), SwitchAnimationUtil.AnimationType.SCALE);

        setUpActionBar(getString(R.string.title_transportation));

        imgAdv = (ImageView) findViewById(R.id.imgAdv);

        updateUI(imgAdv);

        rvTransportation = (RecyclerView)findViewById(R.id.rvTransportation);
        rvTransportation.setHasFixedSize(true);

        RecyclerView.LayoutManager rvTransportationManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvTransportation.setLayoutManager(rvTransportationManager);

        if (isNetworkAvailable(getApplicationContext()))
        {
            GetTransportation getTransportation = new GetTransportation();
            getTransportation.execute();
        }
        else
        {
            preToast(getString(R.string.no_internet_connection));
        }

    }

    private class GetTransportation extends AsyncTask<String,Void,String> {

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
                String pdAttraction = postdata.post(WEB_KEY+API_FETCH_SPONCER,joAttraction.toString());
                JSONObject j=new JSONObject(pdAttraction);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray Advertise=j.getJSONArray("Sponcer");
                    for (int i=0;i<Advertise.length();i++)
                    {
                        JSONObject jo=Advertise.getJSONObject(i);
                        HashMap<String,String > hashMap = new HashMap<>();

                        String image = jo.getString("image");

                        hashMap.put("image",image);

                        TransportationListArray.add(hashMap);
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
                TransportationListAdapter transportationListAdapter = new TransportationListAdapter(getApplicationContext(),TransportationListArray);
                rvTransportation.setAdapter(transportationListAdapter);
            }
            else
            {
                preToast(message);
            }
        }
    }

}
