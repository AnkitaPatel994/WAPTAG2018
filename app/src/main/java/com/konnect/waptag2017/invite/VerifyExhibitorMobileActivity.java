package com.konnect.waptag2017.invite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.konnect.waptag2017.BaseAppCompatActivity;
import com.konnect.waptag2017.R;
import com.konnect.waptag2017.volleyRequest.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by qlooit-9 on 27/12/16.
 */
public class VerifyExhibitorMobileActivity extends BaseAppCompatActivity {

    private EditText edtMobileNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.verify_mobile_activity);
        setUpActionBar("Verify Mobile");

        initControl();


    }

    private void initControl() {

        edtMobileNo = (EditText) findViewById(R.id.edtMobileNo);

        imgAdv = (ImageView) findViewById(R.id.imgAdv);

        updateUI(imgAdv);

        edtMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    if (edtMobileNo.getText().toString().startsWith("7") ||
                            edtMobileNo.getText().toString().startsWith("8") ||
                            edtMobileNo.getText().toString().startsWith("9")) {
                    } else {
                        edtMobileNo.setText("");
                        edtMobileNo.setError("Enter Numbers starting with 7,8 or 9");
                    }
                }
            }
        });

        edtMobileNo.setText(getPref(PREF_USER_MOBILE, ""));
        edtMobileNo.setSelection(edtMobileNo.getText().toString().length());


    }

    private ImageView imgAdv;

    private void callSendApi() {

        startDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = WEB_KEY + API_EXHIBITOR_MOBILE_VERIFY;
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", edtMobileNo.getText().toString().trim());
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                closeDialog();
                try {
                    String error = response.getString("error");
                    String message = response.getString("message");
                    if (error.toUpperCase(Locale.US).equals("FALSE") && message.equals("done")) {
                        updatePref(PREF_USER_MOBILE, edtMobileNo.getText().toString().trim());
                        startActivity(new Intent(getApplicationContext(), VerifyExhibitorPwdActivity.class));
                    } else {
                        alertBox(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                closeDialog();
                preToast(getString(R.string.try_again));
            }
        });

        int socketTimeout = 60000;//60 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        LinearLayout optionMenuLayout = (LinearLayout) menu.findItem(
                R.id.action_register).getActionView();
        ImageView imgSecond = (ImageView) optionMenuLayout.findViewById(R.id.second);
        imgSecond.setImageDrawable(getResources().getDrawable(R.drawable.ic_done));
        imgSecond.setVisibility(View.VISIBLE);
        TextView text = (TextView) optionMenuLayout.findViewById(R.id.third);
        text.setText("NEXT");
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFinish();

            }
        });
        imgSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFinish();
            }
        });
        return super.onPrepareOptionsMenu(menu);

    }

    private void callFinish() {

        if (edtMobileNo.getText().toString().trim().isEmpty()) {
            preToast("Enter Mobile no");
        } else if (edtMobileNo.getText().toString().trim().length() < 10) {
            preToast("Enter valid Mobile no");
        } else {
            if (isNetworkAvailable(getApplicationContext()))
                callSendApi();
//                        manageResponse();
            else
                preToast(getString(R.string.no_internet_connection));
        }

    }
}
