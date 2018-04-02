package com.ankita.waptag2018.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.andexert.library.RippleView;
import com.ankita.waptag2018.BaseAppCompatActivity;
import com.ankita.waptag2018.Postdata;
import com.ankita.waptag2018.R;
import com.ankita.waptag2018.attraction.AttractionActivity;
import com.ankita.waptag2018.contactus.ContactUsActivity;
import com.ankita.waptag2018.custom.AppRater;
import com.ankita.waptag2018.custom.SwitchAnimationUtil;
import com.ankita.waptag2018.directory.ExhibitorsDirectoryActivity;
import com.ankita.waptag2018.event.EventActivity;
import com.ankita.waptag2018.exhibition.ExhibitorsActivity;
import com.ankita.waptag2018.invite.InviteActivity;
import com.ankita.waptag2018.invite.VerifyExhibitorMobileActivity;
import com.ankita.waptag2018.login.LoginActivity;
import com.ankita.waptag2018.map.LayoutActivity;
import com.ankita.waptag2018.transportation.TransportationActivity;
import com.ankita.waptag2018.videos.VideosActivity;
import com.ankita.waptag2018.visitor.VisitorsActivity;
import com.vansuita.library.CheckNewAppVersion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class MainActivity extends BaseAppCompatActivity {

    private ImageView imgAdv;
    private RippleView layout1, layout2, layout3, layout4, layout5, layout6, layout7, layout8, layout9, layout10;
    private AlertDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        new SwitchAnimationUtil().startAnimation(getWindow().getDecorView(), SwitchAnimationUtil.AnimationType.SCALE);


        if (getPref("offline_img", "f").equals("f")) {
            copyImageToCard();
            updatePref("offline_img", "t");
        }

        setAppTitle(getString(R.string.app_title));
        getSupportActionBar().setElevation(0);

        new CheckNewAppVersion(getApplicationContext()).setOnTaskCompleteListener(new CheckNewAppVersion.ITaskComplete() {
            @Override
            public void onTaskComplete(CheckNewAppVersion.Result result) {

                if (result.hasNewVersion()) {
                    showUpdateDialog();
                } else {
                    AppRater.onStart(MainActivity.this);
                    AppRater.showRateDialogIfNeeded(MainActivity.this);
                    initControl();
                }

            }
        }).execute();
    }

    private void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update " + getString(R.string.app_name));
        builder.setMessage("Newer version of WAPTAG 2017 app is available. Update now.");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                final String appPackageName = getApplicationContext().getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setCancelable(false);
        dialog = builder.show();

    }

    private void initControl() {

        layout1 = (RippleView) findViewById(R.id.layout1);
        layout2 = (RippleView) findViewById(R.id.layout2);
        layout3 = (RippleView) findViewById(R.id.layout3);
        layout4 = (RippleView) findViewById(R.id.layout4);
        layout5 = (RippleView) findViewById(R.id.layout5);
        layout6 = (RippleView) findViewById(R.id.layout6);
        layout7 = (RippleView) findViewById(R.id.layout7);
        layout8 = (RippleView) findViewById(R.id.layout8);
        layout9 = (RippleView) findViewById(R.id.layout9);
        layout10 = (RippleView) findViewById(R.id.layout10);
        imgAdv = (ImageView) findViewById(R.id.imgAdv);

        //CHANGE GIF ADS-----------------
        updateUI(imgAdv);

        layout1.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                startActivity(new Intent(getApplicationContext(), EventActivity.class));
            }
        });
        layout2.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                //startActivity(new Intent(getApplicationContext(), LayoutActivity.class));

                if (getPref("offline_img_view", "f").equals("f"))
                {
                    startActivity(new Intent(getApplicationContext(), LayoutActivity.class));
                    updatePref("offline_img_view", "t");
                }
                else
                {
                    if (isNetworkAvailable(getApplicationContext())){

                        GetLayoutimage getLayoutimage = new GetLayoutimage();
                        getLayoutimage.execute();
                    }
                    else {
                        File f = new File(Environment.getExternalStorageDirectory() + "/WAPTAG/layout.jpg");
                        if (f.exists())
                            startActivity(new Intent(getApplicationContext(), LayoutActivity.class));
                        else
                            preToast(getString(R.string.no_internet_connection));
                    }
                }
            }
        });
        layout3.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                startActivity(new Intent(getApplicationContext(), ExhibitorsActivity.class));
            }
        });
        layout4.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                startActivity(new Intent(getApplicationContext(), AttractionActivity.class));
            }
        });
        layout5.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                startActivity(new Intent(getApplicationContext(), VideosActivity.class));
            }
        });
        layout6.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                startActivity(new Intent(getApplicationContext(), TransportationActivity.class));
            }
        });
        layout7.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                if (getPref(PREF_OTP, "").equals("true"))
                {
                    startActivity(new Intent(getApplicationContext(), InviteActivity.class));
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), VerifyExhibitorMobileActivity.class));
                }
                /*if (getPref(PREF_LOGIN, "").equals("true")) {
                    if (getPref(PREF_OTP, "").equals("true")) {
                        startActivity(new Intent(getApplicationContext(), InviteActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), VerifyExhibitorMobileActivity.class));
                    }
                } else {
                    preToast("To invite your guests you need to login first.");
                }*/
            }
        });

        layout8.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                startActivity(new Intent(getApplicationContext(), ContactUsActivity.class));

            }
        });

        layout9.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                startActivity(new Intent(getApplicationContext(), VisitorsActivity.class));
            }
        });

        layout10.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                if (isNetworkAvailable(getApplicationContext())){
                    String pdfUrl = "http://waptag.momentswedding.in/html/document/Coming-soon.pdf";
                    callHtmlServicePdf(pdfUrl);
                }
                else {
                    preToast(getString(R.string.no_internet_connection));
                }
            }
        });

    }

    private void callHtmlServicePdf(String pdfUrl) {
        if (isNetworkAvailable(getApplicationContext())) {
            new DownloadPdfFile().execute(pdfUrl);
        } else {
            preToast(getString(R.string.no_internet_connection));
        }
    }


    public void copyImageToCard() {
        File file = new File(Environment.getExternalStorageDirectory() + "/WAPTAG/", "layout.jpg");
        File file1 = new File(Environment.getExternalStorageDirectory() + "/WAPTAG/");
        if (!file1.exists())
            file1.mkdir();
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.main_layout_image_new);
        try {
            FileOutputStream out = new FileOutputStream(file);
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetLayoutimage extends AsyncTask<String,Void,String> {

        String status,message,image;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joLayout=new JSONObject();
            try {
                Postdata postdata=new Postdata();
                String pdLayout = postdata.post(WEB_KEY+API_GET_LAYOUT,joLayout.toString());
                JSONObject j=new JSONObject(pdLayout);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONObject jo=j.getJSONObject("Layout");
                    image =jo.getString("image");

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
                callHtmlService(image);
            }
            else
            {
                alertBox(message);
            }
        }
    }

    private void callHtmlService(String image_path) {

        if (isNetworkAvailable(getApplicationContext())) {
            new DownloadFile().execute(image_path);
        } else {
            preToast(getString(R.string.no_internet_connection));
        }

    }

    class DownloadFile extends AsyncTask<String, Integer, Long> {
        ProgressDialog mProgressDialog = new ProgressDialog(MainActivity.this);// Change Mainactivity.this with your activity name.
        private String PATH;
        private String targetFileName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Downloading WAPTAG layout");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.show();
        }

        @Override
        protected Long doInBackground(String... aurl) {
            int count;
            try {
                URL url = new URL((String) aurl[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream input = new BufferedInputStream(url.openStream());
                int lenghtOfFile = connection.getContentLength();
                targetFileName = "layout.jpg";
                PATH = Environment.getExternalStorageDirectory() + "/WAPTAG/";
                File folder = new File(PATH);
                File file = new File(PATH + targetFileName);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                file.createNewFile();

                OutputStream output = new FileOutputStream(PATH + targetFileName);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.setMessage("Download Failed.\nPlease try again");
                        mProgressDialog.setCancelable(true);

                    }
                });
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            mProgressDialog.setProgress(progress[0]);
            if (mProgressDialog.getProgress() == mProgressDialog.getMax()) {
                mProgressDialog.dismiss();
            }
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            startActivity(new Intent(getApplicationContext(), LayoutActivity.class));
        }
    }

    class DownloadPdfFile extends AsyncTask<String, Integer, Long> {
        ProgressDialog mProgressDialog = new ProgressDialog(MainActivity.this);// Change Mainactivity.this with your activity name.
        private String PATH;
        private String targetFileName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Downloading WAPTAG Exhibitors Directory");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.show();
        }

        @Override
        protected Long doInBackground(String... aurl) {
            int count;
            try {
                URL url = new URL((String) aurl[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream input = new BufferedInputStream(url.openStream());
                int lenghtOfFile = connection.getContentLength();
                targetFileName = "Coming-soon.pdf";
                PATH = Environment.getExternalStorageDirectory() + "/WAPTAG/";
                File folder = new File(PATH);
                File file = new File(PATH + targetFileName);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                file.createNewFile();

                OutputStream output = new FileOutputStream(PATH + targetFileName);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.setMessage("Download Failed.\nPlease try again");
                        mProgressDialog.setCancelable(true);

                    }
                });
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            mProgressDialog.setProgress(progress[0]);
            if (mProgressDialog.getProgress() == mProgressDialog.getMax()) {
                mProgressDialog.dismiss();
            }
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            startActivity(new Intent(getApplicationContext(), ExhibitorsDirectoryActivity.class));
        }
    }
}


