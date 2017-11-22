package com.ankita.waptag2018.invite;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ankita.waptag2018.BaseAppCompatActivity;
import com.ankita.waptag2018.R;
import com.ankita.waptag2018.custom.CropperActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
    private TextView txtStallNo, txtCompanyName;
    private ImageView imgAdv;

    Bitmap bitmap = null;
    String str_imgpath;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private byte[] byteArray;

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

        /*imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //callUpload();
            }
        });*/

        imgLogo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i("click","yes");

                if (ContextCompat.checkSelfPermission(InviteActivity.this,

                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(InviteActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(InviteActivity.this,
                            Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(InviteActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        selectImage();
                    } else {
                        ActivityCompat.requestPermissions(InviteActivity.this, new String[]{"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE"}, 200);
                        // No explanation needed, we can request the permission.
                    }
                } else {
                    selectImage();
                }

                return false;
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

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(InviteActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (options[item].equals("Choose from Gallery")) {

                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {

                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(
                        Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri tempUri = getImageUri(InviteActivity.this, bitmap);
                str_imgpath = getRealPathFromURI(tempUri);

                byte[] b = bytes.toByteArray();

                imgLogo.setImageBitmap(bitmap);

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();

                InputStream in = null;
                try {
                    final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
                    in = InviteActivity.this.getContentResolver().openInputStream(selectedImageUri);

                    // Decode image size
                    BitmapFactory.Options o = new BitmapFactory.Options();
                    o.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(in, null, o);
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int scale = 1;
                    while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                            IMAGE_MAX_SIZE) {
                        scale++;

                    }
                    in = InviteActivity.this.getContentResolver().openInputStream(selectedImageUri);
                    if (scale > 1) {
                        scale--;

                        o = new BitmapFactory.Options();
                        o.inSampleSize = scale;
                        bitmap = BitmapFactory.decodeStream(in, null, o);

                        // resize to desired dimensions
                        int height = bitmap.getHeight();
                        int width = bitmap.getWidth();

                        double y = Math.sqrt(IMAGE_MAX_SIZE
                                / (((double) width) / height));
                        double x = (y / height) * width;

                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) x,
                                (int) y, true);
                        bitmap.recycle();
                        bitmap = scaledBitmap;

                        System.gc();
                    } else {
                        bitmap = BitmapFactory.decodeStream(in);
                    }
                    in.close();

                } catch (Exception ignored) {

                }

                Uri tempUri = getImageUri(InviteActivity.this, bitmap);
                str_imgpath = getRealPathFromURI(tempUri);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                byteArray = bytes.toByteArray();


                imgLogo.setImageBitmap(bitmap);
            }
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = InviteActivity.this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
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
