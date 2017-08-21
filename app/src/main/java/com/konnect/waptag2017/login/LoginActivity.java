package com.konnect.waptag2017.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.konnect.waptag2017.main.MainActivity;
import com.konnect.waptag2017.volleyRequest.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class LoginActivity extends BaseAppCompatActivity {

    private EditText txtName, txtMobile, txtEmail, txtCompany;
    private TextView txtLogin;
    private Button txtSkip;
    private Bundle bundle;
    private ImageView imgSplash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updatePref("ss_img","f");

        bundle = getIntent().getExtras();

        if (bundle == null) {
            setUpfullScreen();
        } else {
            setUpActionBar("Login");
        }
        if (getPref(PREF_LOGIN, "").equals("true")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            setContentView(R.layout.login_activity);
            initControl();
        }
    }

    private void initControl() {

        txtName = (EditText) findViewById(R.id.txtName);
        txtMobile = (EditText) findViewById(R.id.txtNumber);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtCompany = (EditText) findViewById(R.id.txtCompany);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtSkip = (Button) findViewById(R.id.txtSkip);

        if (bundle != null) {
            imgAdv = (ImageView) findViewById(R.id.imgAdv);
            imgAdv.setVisibility(View.VISIBLE);
            updateUI(imgAdv);
        }

        txtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    if (txtMobile.getText().toString().startsWith("7") ||
                            txtMobile.getText().toString().startsWith("8") ||
                            txtMobile.getText().toString().startsWith("9")) {
                    } else {
                        txtMobile.setText("");
                        txtMobile.setError("Enter Numbers starting with 7,8 or 9");
                    }
                }
            }
        });


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                if (checkvalidation()) {
                    if (isNetworkAvailable(getApplicationContext())) {
                        callLoginService();
                    } else {
                        preToast(getString(R.string.no_internet_connection));
                    }

                }
            }
        });


        if (bundle == null) {
            txtSkip.setVisibility(View.VISIBLE);
        } else {
            txtSkip.setVisibility(View.INVISIBLE);
        }

        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    private ImageView imgAdv;

    private void callLoginService() {

        startDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = WEB_KEY + API_EXHIBITOR_REGISTRATION;
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", txtName.getText().toString());
        params.put("mobile", txtMobile.getText().toString());
        params.put("email", txtEmail.getText().toString());
        params.put("company_name", txtCompany.getText().toString());
        deBug(params.toString());
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                closeDialog();
                try {
                    String error = response.getString("error");
                    String message = response.getString("message");
                    if (error.toUpperCase(Locale.US).equals("FALSE")) {
                        updatePref(PREF_LOGIN, "true");
                        updatePref(PREF_USER_MOBILE, txtMobile.getText().toString());
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
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
                preToast("Try again");
                Log.e("TAG", "ERROR in LOGIN RESPONSE LISTENER");
            }
        });
        int socketTimeout = 60000;//60 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);

    }

    private boolean checkvalidation() {
        Boolean status = true;

        if (txtName.getText().toString().equals("")) {
            preToast("Enter Name");
            status = false;
        } else if (txtMobile.getText().toString().equals("")) {
            preToast("Enter Mobile no");
            status = false;
        } else if (txtMobile.getText().toString().length() < 10) {
            preToast("Enter valid Mobile no");
            status = false;
        } else if (txtEmail.getText().toString().equals("")) {
            preToast("Enter Email id");
            status = false;
        } else if (!isValidEmail(txtEmail.getText().toString())) {
            preToast("Enter valid Email id");
            status = false;
        } else if (txtCompany.getText().toString().equals("")) {
            preToast("Enter Company name");
            status = false;
        }


        return status;
    }

}
