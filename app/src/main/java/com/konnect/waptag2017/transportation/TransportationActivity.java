package com.konnect.waptag2017.transportation;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.konnect.waptag2017.BaseAppCompatActivity;
import com.konnect.waptag2017.R;
import com.konnect.waptag2017.custom.SwitchAnimationUtil;

import java.util.Locale;

/**
 * Created by qlooit-9 on 11/11/16.
 */
public class TransportationActivity extends BaseAppCompatActivity {

    private ImageView imgMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transportation_activity);

        new SwitchAnimationUtil().startAnimation(getWindow().getDecorView(), SwitchAnimationUtil.AnimationType.SCALE);


        setUpActionBar(getString(R.string.title_transportation));

        imgMap = (ImageView) findViewById(R.id.imgMap);

        imgAdv = (ImageView) findViewById(R.id.imgAdv);

        updateUI(imgAdv);

        imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("http://maps.google.com/maps?center=23.2316847,72.6334691"));
//                startActivity(intent);
                try {
                    if (isGoogleMapsInstalled()) {
                        String uri = String.format(Locale.ENGLISH, "geo:" + 23.2316847 + "," +
                                72.6334691 + "?q=Mahatma Mandir, Gandhinagar");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                    } else {
                        preToast("Google Map is not installed");
                    }
                } catch (Exception e) {
                    preToast("Something went wrong. Try again");
                    e.printStackTrace();
                }
//                String uri = String.format(Locale.ENGLISH, "geo:0,0?q=Mahatma Mandir, Gandhinagar");
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(intent);
            }
        });

    }

    private ImageView imgAdv;

    public boolean isGoogleMapsInstalled() {
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
