package com.ankita.waptag2018.invite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
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
import com.ankita.waptag2018.BaseAppCompatActivity;
import com.ankita.waptag2018.Postdata;
import com.ankita.waptag2018.R;
import com.ankita.waptag2018.volleyRequest.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class VerifyExhibitorPwdActivity extends BaseAppCompatActivity {

    private final long startTime = 600000;
    private final long interval = 1000;
    private MalibuCountDownTimer countDownTimer;
    private Button butResend;
    private String mPwd;
    private EditText editPwd;
    private boolean isTimerFinish = false;
    private String mNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        setUpActionBar(getString(R.string.title_verify));
        mNumber = getPref(PREF_USER_MOBILE, "");
        initControl(1);

    }

    private void initControl(int tVal) {


        editPwd = (EditText) findViewById(R.id.editPwd);

        imgAdv = (ImageView) findViewById(R.id.imgAdv);

        updateUI(imgAdv);

        mPwd = editPwd.getText().toString().trim();

        editPwd.setOnEditorActionListener
                (new TextView.OnEditorActionListener() {
                     @Override
                     public boolean onEditorAction(TextView v, int actionId,
                                                   KeyEvent event) {
                         // TODO Auto-generated method stub
                         if (actionId == EditorInfo.IME_ACTION_SEARCH
                                 || actionId == EditorInfo.IME_ACTION_DONE
                                 || event.getAction() == KeyEvent.ACTION_DOWN
                                 && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                             callSubmitClick();
                             return true;
                         }
                         return false;
                     }
                 }
                );

        butResend = (Button) findViewById(R.id.butResend);
        butResend.setOnClickListener(new View.OnClickListener()

                                     {
                                         @Override
                                         public void onClick(View v) {
                                             callResendView();
                                         }
                                     }

        );
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        String currentDateandTime = sdf.format(addMinutesToDate(tVal,
                new Date()));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)

        {
            String data = bundle.getString("data");
            if (data.equals("true"))
                updatePref(PREF_USER_START_DATE, getPref(PREF_USER_START_DATE, currentDateandTime));
            else
                updatePref(PREF_USER_START_DATE, currentDateandTime);
        } else

            updatePref(PREF_USER_START_DATE, currentDateandTime);

        if (tVal == 3)

        {
            updatePref(PREF_USER_START_DATE, currentDateandTime);
        }

        countDownTimer = new

                MalibuCountDownTimer(startTime, interval);

        countDownTimer.start();
    }

    private ImageView imgAdv;

    private void callResendView() {
        if (isTimerFinish) {
            callResendSMS();
        } else {
            preToast(getResources().getString(R.string.wait_for_time_out));
        }
    }

    private void callResendSMS() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.confirm_your_number);
        alert.setMessage("Is " + mNumber + getString(R.string.your_number_));
        alert.setPositiveButton(R.string.edit,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        setResult(1);
                        finish();
                    }
                });

        alert.setNegativeButton(R.string.confirm,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        sendReSMS();
                    }
                });
        alert.show();
    }

    private void sendReSMS() {

        if (!isNetworkAvailable(getApplicationContext())) {
            preToast(getString(R.string.no_internet_connection));
            return;
        }

        GetReSendSMS getReSendSMS = new GetReSendSMS();
        getReSendSMS.execute();

    }

    private class GetReSendSMS extends AsyncTask<String,Void,String> {

        String status,message;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joReSMS=new JSONObject();
            try {

                joReSMS.put("mobile",mNumber);
                Postdata postdata=new Postdata();
                String pdReSMS=postdata.post(WEB_KEY + API_RESEND_EXHIBITORS_OTP,joReSMS.toString());
                JSONObject j=new JSONObject(pdReSMS);
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
                initControl(3);
            }
            else
            {
                alertBox(message);
            }
        }
    }

    private void callSubmitClick() {
        String textPwd = editPwd.getText().toString().trim();
        if (textPwd.length() == 0) {
            preToast(getResources().getString(R.string.please_enter_code));
        } else if (textPwd.length() != 6) {
            preToast(getResources().getString(R.string.please_enter_6_digit_code));
        } else {
            if (isNetworkAvailable(getApplicationContext()))
            {
                GetOTPVerify getOTPVerify = new GetOTPVerify();
                getOTPVerify.execute();

                //callOTPAPI();
            }
            else
            {
                getString(R.string.no_internet_connection);
            }
        }

    }

    private class GetOTPVerify extends AsyncTask<String,Void,String> {

        String status,message,company_name,stall_no;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joOV=new JSONObject();
            try {

                joOV.put("mobile",getPref(PREF_USER_MOBILE, ""));
                joOV.put("otp", editPwd.getText().toString().trim());
                Postdata postdata=new Postdata();
                String pdOV=postdata.post(WEB_KEY + API_VERIFY_EXHIBITOR,joOV.toString());
                JSONObject j=new JSONObject(pdOV);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONObject jo=j.getJSONObject("exhibitions");
                    company_name =jo.getString("company_name");
                    stall_no =jo.getString("stall_no");
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
                updatePref(PREF_OTP, "true");
                Intent i = new Intent(getApplicationContext(), InviteActivity.class);
                updatePref(PREF_STICKER_COMPANY, company_name);
                updatePref(PREF_STICKER_STALL, stall_no);
                startActivity(i);
                finish();
            }
            else
            {
                alertBox(message);
            }
        }
    }

    /*private void callOTPAPI() {

        startDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = WEB_KEY + API_VERIFY_EXHIBITOR;
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", getPref(PREF_USER_MOBILE, ""));
        params.put("otp", editPwd.getText().toString().trim());
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                closeDialog();
                try {
                    String error = response.getString("error");
                    String message = response.getString("message");
                    if (error.toUpperCase(Locale.US).equals("FALSE") && message.equals("done")) {
                        updatePref(PREF_OTP, "true");
                        JSONObject jsonObject = response.getJSONObject("data");
                        Intent i = new Intent(getApplicationContext(), InviteActivity.class);
                        updatePref(PREF_STICKER_COMPANY, jsonObject.getString("company_name"));
                        updatePref(PREF_STICKER_STALL, jsonObject.getString("stall_no"));
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
                preToast(getString(R.string.try_again));
            }
        });

        int socketTimeout = 60000;//60 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);


    }*/


    private Date addMinutesToDate(int minutes, Date beforeTime) {
        long ONE_MINUTE_IN_MILLIS = 60000;// millisecs
        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs
                + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        LinearLayout badgeLayout = (LinearLayout) menu.findItem(
                R.id.action_register).getActionView();
        LinearLayout layoutSave = (LinearLayout) badgeLayout.findViewById(R.id.layoutSave);
        ImageView img = (ImageView) badgeLayout.findViewById(R.id.second);
        img.setImageDrawable(getResources().getDrawable(R.drawable.ic_done));
        img.setVisibility(View.VISIBLE);
        TextView txt = (TextView) badgeLayout.findViewById(R.id.third);
        txt.setText("NEXT");
        layoutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSubmitClick();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSubmitClick();
            }
        });

        return super.onPrepareOptionsMenu(menu);

    }

    // CountDownTimer class
    public class MalibuCountDownTimer extends CountDownTimer {

        public MalibuCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
        }


        @Override
        public void onTick(long millisUntilFinished) {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy/MM/dd HH:mm:ss");
            String endDate = sdf.format(new Date());
            String startDate = getPref(PREF_USER_START_DATE, "");
            String val = printDifference(getDate(endDate),
                    getDate(startDate));
            if (val.equals("0")) {
//                textResend.setBackgroundColor(getResources().getColor(
//                        R.color.top_bar_color));
                butResend.setText(getResources().getString(R.string.resend_sms));
                butResend.setTextSize(18f);
                butResend.setVisibility(View.VISIBLE);
                this.onFinish();
                isTimerFinish = true;
            } else {
//                textResend.setBackgroundColor(getResources().getColor(
//                        R.color.bg_layout));
                butResend.setVisibility(View.VISIBLE);
                butResend.setTextSize(18f);
                butResend.setText(getString(R.string.resend)
                        + " (" + val + ")");
                isTimerFinish = false;
            }
        }
    }

}
