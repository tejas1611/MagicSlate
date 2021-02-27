package com.tejas.magicslate.lessons.lesson3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.tejas.magicslate.R;
import com.tejas.magicslate.ScanActivity;

public class WheelChallenge extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_challenge);
    }

    public void OpenCamera(View w) {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Image");
        values.put(MediaStore.Images.Media.DESCRIPTION,"from the camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this,"Unable to Open Camera", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Intent i = new Intent(this, ScanActivity.class);
            i.setData(imageUri);
            startActivity(i);
        }
    }
}
