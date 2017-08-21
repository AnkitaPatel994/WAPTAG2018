package com.konnect.waptag2017.invite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.konnect.waptag2017.BaseAppCompatActivity;
import com.konnect.waptag2017.R;
import com.konnect.waptag2017.custom.CropperActivity;

import java.io.File;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class InviteActivity extends BaseAppCompatActivity {

    private Button btnSubmit;
    private ImageView imgLogo;
    private File temp_file;
    private Uri imageUri;
    private static final int PICK_IMAGE = 6;
    private static final int CAMERA_REQUEST = 5;
    private byte[] byteArray;
    private TextView txtStallNo, txtCompanyName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActionBar(getString(R.string.title_invite));
        setContentView(R.layout.invite_activity);

        initControl();

    }

    private void initControl() {

        imgAdv = (ImageView) findViewById(R.id.imgAdv);

        updateUI(imgAdv);

        txtStallNo = (TextView) findViewById(R.id.txtStallNo);
        txtCompanyName = (TextView) findViewById(R.id.txtCompanyName);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        txtStallNo.setText("Stall No: " + getPref(PREF_STICKER_STALL, ""));
        txtCompanyName.setText(getPref(PREF_STICKER_COMPANY, ""));

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callUpload();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    manageResponse();
                }
            }
        });

    }

    private ImageView imgAdv;


    public void callUpload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[] array = {"Camera", "Gallery"};
        builder.setTitle("Choose any action").setItems(array,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {//CAMERA
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File dir =
                                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                            temp_file = new File(dir, String.valueOf(System.currentTimeMillis()) + ".jpg");
                            imageUri = Uri.fromFile(temp_file);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                if (imageUri != null) {
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                    startActivityForResult(intent, CAMERA_REQUEST);
                                }
                            }
                        } else if (which == 1) {//GALLERY
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, PICK_IMAGE);
                        }
                    }
                });
        builder.create();
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 301) {
            manageImage(data);
            return;
        }
        try {
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                    && null != data) {
                Uri selectedImage = data.getData();
                callDropperView(selectedImage);
            } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                Uri selectedImage = imageUri;
                callDropperView(selectedImage);
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    private void callDropperView(Uri mImageCaptureUri2) {
        Intent intent = new Intent(getApplicationContext(), CropperActivity.class);
        intent.putExtra("data", mImageCaptureUri2);
        startActivityForResult(intent, 301);
    }

    private void manageImage(Intent data) {
        try {
            byteArray = data.getByteArrayExtra("data");
            deBug(data.getStringExtra("filename"));
            Bitmap mImageBitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                    byteArray.length);
            imgLogo.setImageBitmap(mImageBitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void manageResponse() {

        Intent i = new Intent(getApplicationContext(), StickerActivity.class);
        i.putExtra("LOGO", byteArray);
        startActivity(i);

    }

    private boolean checkValidation() {
        boolean status = true;

        if (byteArray == null || byteArray.length == 0) {
            preToast("Please upload company logo");
            status = false;
        }
        return status;
    }

}
