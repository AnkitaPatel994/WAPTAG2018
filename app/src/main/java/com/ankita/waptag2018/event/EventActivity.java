package com.ankita.waptag2018.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.ankita.waptag2018.BaseAppCompatActivity;
import com.ankita.waptag2018.R;
import com.ankita.waptag2018.custom.SwitchAnimationUtil;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class EventActivity extends BaseAppCompatActivity {

    ImageView imgAdv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_activity);

        new SwitchAnimationUtil().startAnimation(getWindow().getDecorView(), SwitchAnimationUtil.AnimationType.SCALE);

        setUpActionBar(getString(R.string.title_events));

        imgAdv = (ImageView) findViewById(R.id.imgAdv);

      updateUI(imgAdv);
    }


}
