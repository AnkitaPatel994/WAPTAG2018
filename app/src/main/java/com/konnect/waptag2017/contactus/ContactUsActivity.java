package com.konnect.waptag2017.contactus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.konnect.waptag2017.BaseAppCompatActivity;
import com.konnect.waptag2017.R;
import com.konnect.waptag2017.custom.SwitchAnimationUtil;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class ContactUsActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contact_us_activity);
//        getSupportActionBar().setElevation(0);
        new SwitchAnimationUtil().startAnimation(getWindow().getDecorView(), SwitchAnimationUtil.AnimationType.SCALE);
        setUpActionBar(getString(R.string.title_contact));

        imgAdv = (ImageView) findViewById(R.id.imgAdv);

      updateUI(imgAdv);
    }

    private ImageView imgAdv;

}
