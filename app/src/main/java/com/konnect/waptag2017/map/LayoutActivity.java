package com.konnect.waptag2017.map;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.konnect.waptag2017.BaseAppCompatActivity;
import com.konnect.waptag2017.R;

/**
 * Created by qlooit-9 on 29/12/16.
 */
public class LayoutActivity extends BaseAppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_activity);
        setUpActionBar(getString(R.string.title_map));

        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.imageView);
        imageView.setImage(ImageSource.uri("sdcard/WAPTAG/layout.jpg"));


    }
}
