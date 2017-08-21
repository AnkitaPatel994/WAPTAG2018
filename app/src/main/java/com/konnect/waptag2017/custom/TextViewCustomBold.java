package com.konnect.waptag2017.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by qlooit5 on 6/10/15.
 */
public class TextViewCustomBold extends TextView {

    public TextViewCustomBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewCustomBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewCustomBold(Context context) {
        super(context);
        init();
    }

    public void init() {
        if (!isInEditMode()) {
            Typeface fontLight = Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Regular.otf");
            setTypeface(fontLight);
        } else {
            // Log.d(TAG, "TextView");
        }
    }

}
