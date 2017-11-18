package com.ankita.waptag2018.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.ankita.waptag2018.BaseAppCompatActivity;
import com.ankita.waptag2018.R;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class SingleEventActivity extends BaseAppCompatActivity {

    private TextView txtEventName, txtEventName1, txtEventDateTime, txtEventLocation,
            txtEventTime, txtEventDate, txtEventAbout;
    private CardView cardEvent, cardExhibition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_event_activity);

//        setUpActionBar(getString(R.string.title_events));
        trasparentActionBar("");
        getSupportActionBar().setElevation(0);

        Bundle bundle = getIntent().getExtras();

        initControl();
        if (bundle != null) {
            if (bundle.containsKey("FROM_EXHIBITION")) {
                cardEvent.setVisibility(View.GONE);
                cardExhibition.setVisibility(View.VISIBLE);
                callExhibitionService();
            } else {
                callEventService();
            }
        }

    }

    private void initControl() {

        txtEventName = (TextView) findViewById(R.id.txtEventName);
        txtEventName1 = (TextView) findViewById(R.id.txtEventName1);
        txtEventDateTime = (TextView) findViewById(R.id.txtEventDateTime);
        txtEventLocation = (TextView) findViewById(R.id.txtEventLocation);
        txtEventDate = (TextView) findViewById(R.id.txtEventDate);
        txtEventTime = (TextView) findViewById(R.id.txtEventTime);
        txtEventAbout = (TextView) findViewById(R.id.txtEventAbout);
        cardEvent = (CardView) findViewById(R.id.cardEvent);
        cardExhibition = (CardView) findViewById(R.id.cardExhibition);

    }

    private void callExhibitionService() {

        if (!isNetworkAvailable(getApplicationContext())) {
            preToast(getString(R.string.no_internet_connection));
            finish();
        } else {
            //API CALL
        }

    }

    private void callEventService() {
        if (!isNetworkAvailable(getApplicationContext())) {
            preToast(getString(R.string.no_internet_connection));
            finish();
        } else {
            //API CALL
        }
    }

}
