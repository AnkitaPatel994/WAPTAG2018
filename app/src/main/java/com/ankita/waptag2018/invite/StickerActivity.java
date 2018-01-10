package com.ankita.waptag2018.invite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankita.waptag2018.BaseAppCompatActivity;
import com.ankita.waptag2018.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qlooit-9 on 26/12/16.
 */
public class StickerActivity extends BaseAppCompatActivity {

    private LinearLayout mScrollView;
    private ImageView imgLogo;
    private TextView txtName, txtStall;
    private Bitmap bmp;

    @Override
    protected void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.temp_test);
        setUpActionBar("");
        getSupportActionBar().setElevation(0);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            byte[] byteArray = getIntent().getByteArrayExtra("LOGO");
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }

        mScrollView = (LinearLayout) findViewById(R.id.mScrollView);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        txtName = (TextView) findViewById(R.id.txtName);
        txtStall = (TextView) findViewById(R.id.txtStall);

        imgLogo.setImageBitmap(bmp);
        txtName.setText(getPref(PREF_STICKER_COMPANY, ""));
        txtStall.setText(getPref(PREF_STICKER_STALL, ""));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        LinearLayout optionMenuLayout = (LinearLayout) menu.findItem(
                R.id.action_register).getActionView();
        ImageView imgSecond = (ImageView) optionMenuLayout.findViewById(R.id.second);
        TextView text = (TextView) optionMenuLayout.findViewById(R.id.third);
        text.setText("SHARE");

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenShot();

            }
        });
        imgSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenShot();
            }
        });
        return super.onPrepareOptionsMenu(menu);

    }

    private void takeScreenShot() {

        startDialog();
        int totalHeight = mScrollView.getChildAt(0).getHeight();
        int totalWidth = mScrollView.getChildAt(0).getWidth();

        Bitmap b = getBitmapFromView(mScrollView, totalHeight, totalWidth);
        //Save bitmap
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/WAPTAG/";
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();

        String format = new SimpleDateFormat("yyyyMMddHHmmss",
                java.util.Locale.getDefault()).format(new Date());

        File file = new File(dir, format + ".png");
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDialog();
        preToast("Invitation saved in Gallery..");

        Uri uri = Uri.fromFile(file);
        refreshAndroidGallery(uri);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        //ADD SHARE TEXT
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Sharing Invitation"));
    }

    public void refreshAndroidGallery(Uri fileUri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(fileUri);
            getApplicationContext().sendBroadcast(mediaScanIntent);
        } else {
            getApplicationContext().sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }

    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }


}
