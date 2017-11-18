package com.ankita.waptag2018.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;


/**
 * Created by qlooit5 on 6/10/15.
 */
public class EditTextCustom extends EditText {

    public EditTextCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextCustom(Context context) {
        super(context);
        init();
    }

    public void init() {
        if (!isInEditMode()) {
            Typeface fontLight = Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Light.otf");
            // "Roboto-Regular.ttf");
            setTypeface(fontLight);
        } else {
            // Log.d(TAG, "TextView");
        }
    }

}
