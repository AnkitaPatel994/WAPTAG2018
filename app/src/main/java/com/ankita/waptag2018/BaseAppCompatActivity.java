package com.ankita.waptag2018;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ankita.waptag2018.interfaces.Constant;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class BaseAppCompatActivity extends AppCompatActivity implements Constant {

    public SharedPreferences preferencesVal;
    public SharedPreferences.Editor editor;
    private DialogPlus dialog;
    ArrayList<Integer> imagesToShow = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferencesVal = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        imagesToShow.add(R.drawable.ad1);
        imagesToShow.add(R.drawable.ad2);
        imagesToShow.add(R.drawable.ad3);
        imagesToShow.add(R.drawable.ad4);
        imagesToShow.add(R.drawable.ad5);
        imagesToShow.add(R.drawable.ad6);
        imagesToShow.add(R.drawable.ad7);
        imagesToShow.add(R.drawable.ad9);
        imagesToShow.add(R.drawable.ad9);
        imagesToShow.add(R.drawable.ad10);

    }

    public void updatePref(String key, String val) {
        editor = preferencesVal.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public String getPref(String key, String defVal) {
        return preferencesVal.getString(key, defVal);
    }

    public void preToast(String val) {
        Toast customToast = new Toast(getBaseContext());
        customToast = Toast.makeText(getBaseContext(), val, Toast.LENGTH_SHORT);
        customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        customToast.show();
//        Snackbar.make(getWindow().getDecorView().getRootView(), val, Snackbar.LENGTH_SHORT).show();
    }

    public void setUpfullScreen() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    public void setAppTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
            getSupportActionBar().setTitle(Html.fromHtml("<ba <font color='#ffffff'>" + title + "</font>"));

            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            LayoutInflater inflator = LayoutInflater.from(this);
            View v = inflator.inflate(R.layout.actionbar_titleview, null);
            ((TextView) v.findViewById(R.id.title)).setText(title);
            ((ImageView) v.findViewById(R.id.imgHeadIcon)).setVisibility(View.VISIBLE);
            getSupportActionBar().setCustomView(v);
        }
    }

    public void setUpActionBar(String Actiontitle) {
        getSupportActionBar().setTitle((Html.fromHtml("<ba <font color='#ffffff'>" + Actiontitle + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.actionbar_titleview, null);
        ((TextView) v.findViewById(R.id.title)).setText(Actiontitle);
        getSupportActionBar().setCustomView(v);

    }

    public void hideKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            hideKeyboard();
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void trasparentActionBar(String title) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setElevation(0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public void deBug(String string) {
//        Log.e("DEBUG : ", string);
    }

    public void startDialog() {
        dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.load_dialog_activity))
                .setCancelable(false)
                .setGravity(Gravity.CENTER)
                .create();
        dialog.show();

//        TextView dialog_title = (TextView) dialog.findViewById(R.id.text_dialog_title);
//        dialog_title.setText(mVal);
    }

    public void closeDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public String getEven(String input, boolean even) {
        String text = "";
        int i = 0;
        if (even) { // if we need the even chars, start from 1;
            i = 1;
        }
        for (; i < input.length(); i = i + 2) {
            text = text + input.charAt(i);
            // System.out.println(input.charAt(i));
        }
        return text;
    }

    public void alertBox(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name))
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                })
                .show();
    }

    public String printDifference(Date startDate, Date endDate) {
        try {
            long different = endDate.getTime() - startDate.getTime();
            if (different <= 0) {
                return "0";
            }
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            String mSec = "";
            if (elapsedSeconds <= 9)
                mSec = "0";
            return "0" + elapsedMinutes + ":" + mSec + elapsedSeconds + "";
            // return elapsedMinutes + " min " + elapsedSeconds + " Sec";

        } catch (Exception e) {
            return "0";
        }
    }

    @SuppressLint("SimpleDateFormat")
    public Date getDate(String endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(endDate);
            // System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return date;
    }

    public void updateUI(final ImageView imgAdv) {
        String getAdsVal = getPref("ads_waptag_count", "1");
        animate(imgAdv, imagesToShow, Integer.valueOf(getAdsVal), true);
    }


    private void animate(final ImageView imageView, final ArrayList<Integer> images, final int imageIndex, final boolean forever) {

        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 4000;
        int fadeOutDuration = 1000;
deBug("Index "+imageIndex);
        imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        imageView.setImageResource(images.get(imageIndex - 1));

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

//        Animation animSlideLR = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.ltor);
//        Animation animSlideRL = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.rtol);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (images.size() > imageIndex) {
                    if (imageIndex + 1 == 14) {
                        updatePref("ads_waptag_count", String.valueOf(1));
                    } else
                        updatePref("ads_waptag_count", String.valueOf(imageIndex + 1));
                    animate(imageView, images, imageIndex + 1, forever); //Calls itself until it gets to the end of the array
                } else {
                    if (forever == true) {
                        animate(imageView, images, 1, forever);  //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }

            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });
    }

}
