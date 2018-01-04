package com.ankita.waptag2018.login;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.ankita.waptag2018.BaseAppCompatActivity;
import com.ankita.waptag2018.Postdata;
import com.ankita.waptag2018.R;
import com.ankita.waptag2018.main.MainActivity;
import com.ankita.waptag2018.volleyRequest.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class LoginActivity extends BaseAppCompatActivity {

    private ImageView imgAdv;
    private EditText txtName, txtMobile, txtEmail, txtCompany,txtLocation;
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
        txtLocation = (EditText) findViewById(R.id.txtLocation);
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
                    if (isNetworkAvailable(getApplicationContext()))
                    {
                        InsertLogin insertLogin = new InsertLogin();
                        insertLogin.execute();
                    }
                    else
                    {
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

    private class InsertLogin extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joLogin=new JSONObject();
            try {

                joLogin.put("company_name",txtCompany.getText().toString());
                joLogin.put("name", txtName.getText().toString());
                joLogin.put("mobile", txtMobile.getText().toString());
                joLogin.put("email", txtEmail.getText().toString());
                joLogin.put("location", txtLocation.getText().toString());
                Postdata postdata=new Postdata();
                String pdLogin=postdata.post(WEB_KEY + API_EXHIBITOR_REGISTRATION,joLogin.toString());
                JSONObject j=new JSONObject(pdLogin);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
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
                updatePref(PREF_LOGIN, "true");
                updatePref(PREF_USER_MOBILE, txtMobile.getText().toString());
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
            else
            {
                alertBox(message);
            }
        }
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
        }else if (txtLocation.getText().toString().equals("")) {
            preToast("Enter Location");
            status = false;
        }

        return status;
    }
}
