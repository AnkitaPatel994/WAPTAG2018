package com.ankita.waptag2018.exhibition;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ankita.waptag2018.BaseAppCompatActivity;
import com.ankita.waptag2018.Postdata;
import com.ankita.waptag2018.R;
import com.ankita.waptag2018.adapter.ExhibitorListAdapter;
import com.ankita.waptag2018.model.ExhibitorModel;
import com.ankita.waptag2018.volleyRequest.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class ExhibitorsActivity extends BaseAppCompatActivity {

    private ListView mListExhibitor;
    ExhibitorListAdapter exhibitorListAdapter;
    ArrayList<ExhibitorModel> exhibitorModelArrayList = new ArrayList<>();
    ArrayList<ExhibitorModel> exhibitorModelArrayList_dummy = new ArrayList<>();
    private EditText searchBox;
    private ImageView imgAdv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActionBar(getString(R.string.title_exhibitions));
        setContentView(R.layout.exhibition_activity);
        getSupportActionBar().setElevation(0);

        initControl();

        if (isNetworkAvailable(getApplicationContext()))
        {
            GetExhibitors getExhibitors = new GetExhibitors();
            getExhibitors.execute();
        }
        else
        {
            preToast(getString(R.string.no_internet_connection));
        }

    }

    private class GetExhibitors extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joExhibitor=new JSONObject();
            try {
                Postdata postdata=new Postdata();
                String pdExhibitor = postdata.post(WEB_KEY+API_EXHIBITORS_LIST,joExhibitor.toString());
                JSONObject j=new JSONObject(pdExhibitor);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray Exhibitor=j.getJSONArray("Exhibitions");
                    for (int i=0;i<Exhibitor.length();i++)
                    {
                        JSONObject jo=Exhibitor.getJSONObject(i);

                        String company_name = jo.getString("company_name");
                        String name = jo.getString("name");
                        String mobile = jo.getString("mobile");
                        String email = jo.getString("email");
                        String stall_no = jo.getString("stall_no");
                        String space = jo.getString("space");

                        ExhibitorModel exhibitorModel = new ExhibitorModel(company_name,name,mobile,email,stall_no,space);
                        exhibitorModelArrayList.add(exhibitorModel);
                        exhibitorModelArrayList_dummy.add(exhibitorModel);
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
            exhibitorListAdapter = new ExhibitorListAdapter(ExhibitorsActivity.this, exhibitorModelArrayList);
            mListExhibitor.setAdapter(exhibitorListAdapter);
        }
    }

    private void initControl() {

        mListExhibitor = (ListView) findViewById(R.id.listExhibitor);
        searchBox = (EditText) findViewById(R.id.searchBox);

        imgAdv = (ImageView) findViewById(R.id.imgAdv);

        updateUI(imgAdv);

        searchBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(String.valueOf(s));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_item_search) {
            if (searchBox.getVisibility() == View.GONE) {
                searchBox.setVisibility(View.VISIBLE);
                searchBox.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else {
                hideKeyboard();
                searchBox.setVisibility(View.GONE);
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void filter(String charText) {

        charText = charText.toLowerCase();

        exhibitorModelArrayList.clear();

        if (charText.length() == 0) {
            exhibitorModelArrayList.addAll(exhibitorModelArrayList_dummy);
            appendAddItem(exhibitorModelArrayList_dummy);
        } else {
            for (int i = 0; i < exhibitorModelArrayList_dummy.size(); i++) {
                if ((exhibitorModelArrayList_dummy.get(i).getCompanyName().toLowerCase().contains(charText)) ||
                        (exhibitorModelArrayList_dummy.get(i).getName().toLowerCase().contains(charText))) {
                    exhibitorModelArrayList.add(exhibitorModelArrayList_dummy.get(i));
                }
            }
            appendAddItem(exhibitorModelArrayList);
        }
    }

    private void appendAddItem(final ArrayList<ExhibitorModel> novaLista) {
        exhibitorListAdapter = new ExhibitorListAdapter(ExhibitorsActivity.this, exhibitorModelArrayList);
        mListExhibitor.setAdapter(exhibitorListAdapter);
    }

    @Override
    public void onBackPressed() {
        if (searchBox.getVisibility() == View.VISIBLE) {
            searchBox.setVisibility(View.GONE);
        } else
            super.onBackPressed();
    }

}
