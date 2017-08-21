package com.konnect.waptag2017.attraction;

/**
 * Created by qlooit-9 on 11/11/16.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.konnect.waptag2017.BaseAppCompatActivity;
import com.konnect.waptag2017.R;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AttractionActivity extends BaseAppCompatActivity {

    private LinearLayout attractionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpActionBar(getString(R.string.title_attraction));
        setContentView(R.layout.attraction_activity);


        attractionLayout = (LinearLayout) findViewById(R.id.attractionLayout);

        imgAdv = (ImageView) findViewById(R.id.imgAdv);

      updateUI(imgAdv);

        inflateView(getResources().getDrawable(R.drawable.ic_1), "India's Largest Water Exhibition");
        inflateView(getResources().getDrawable(R.drawable.ic_2), "Product Presentation");
        inflateView(getResources().getDrawable(R.drawable.ic_3), "Executive Office & Personal Office");
        inflateView(getResources().getDrawable(R.drawable.ic_4), "Genuine B2B Business");
        inflateView(getResources().getDrawable(R.drawable.ic_5), "Technical Seminars");
        inflateView(getResources().getDrawable(R.drawable.ic_6), "More Than 15,000 Visitors Expected");
        inflateView(getResources().getDrawable(R.drawable.ic_7), "WapTag Expo Mobile Application");
        inflateView(getResources().getDrawable(R.drawable.ic_8), "Foreign Delegates to Participate");
        inflateView(getResources().getDrawable(R.drawable.ic_9), "Dedicated Helpline for Exhibitor");
        inflateView(getResources().getDrawable(R.drawable.ic_10), "Easy & Free Transportation");
        inflateView(getResources().getDrawable(R.drawable.ic_11), "Newspaper Advertisement");
        inflateView(getResources().getDrawable(R.drawable.ic_12), "Promote Your Brand Globally");
        inflateView(getResources().getDrawable(R.drawable.ic_13), "Lower Rates in Expo Industry");
        inflateView(getResources().getDrawable(R.drawable.ic_14), "Ideal Platform to Demonstrate");
        inflateView(getResources().getDrawable(R.drawable.ic_15), "Hoardings Throughout Gujarat");
        inflateView(getResources().getDrawable(R.drawable.ic_16), "Radio Advertisement / Digital Marketing");
        inflateView(getResources().getDrawable(R.drawable.ic_17), "Entertaining Evening");
        inflateView(getResources().getDrawable(R.drawable.ic_18), "Gala Dinner");

    }

    private ImageView imgAdv;

    private void inflateView(Drawable drawable, String text) {

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.list_attraction_view, null);

        TextView txtAttraction = (TextView) rowView.findViewById(R.id.txtAttraction);
        ImageView imgAttraction = (ImageView) rowView.findViewById(R.id.imgAttraction);

        txtAttraction.setText(text);
        imgAttraction.setImageDrawable(drawable);

        attractionLayout.addView(rowView);
//        new SwitchAnimationUtil().startAnimation(getWindow().getDecorView(), SwitchAnimationUtil.AnimationType.SCALE);


    }

}

