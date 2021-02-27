package com.tejas.magicslate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tejas.magicslate.Retrofit.IUploadApi;
import com.tejas.magicslate.Retrofit.RetrofitClient;
import com.tejas.magicslate.Utils.Common;
import com.tejas.magicslate.Utils.IUploadCallbacks;
import com.tejas.magicslate.Utils.ProgressRequestBody;

import java.io.File;
import java.net.URISyntaxException;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanActivity extends AppCompatActivity implements IUploadCallbacks {

    IUploadApi mService;
    Uri selectedFileUri;
    ProgressDialog dialog;

    private IUploadApi getApiUpload() {
        return RetrofitClient.getClient().create(IUploadApi.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        mService = getApiUpload();

        Uri image_uri = getIntent().getData();
        ImageView imageView =findViewById(R.id.image_preview);
        imageView.setImageURI(image_uri);
        selectedFileUri = image_uri;
    }

    public void onClickCancel(View w) {
        ScanActivity.this.finish();
    }

    public void onClickSubmit(View w) {
        uploadFile();
    }

    private void uploadFile() {
        //now here we check we have uri of file or not
        if (selectedFileUri != null) {
            dialog = new ProgressDialog(ScanActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage("Uploading..");
            dialog.setIndeterminate(false);
            dialog.setMax(100);
            dialog.setCancelable(false);
            dialog.show();

            //now we load actual file from uri
            File file = null;
            try {
                file = new File(Common.getFilePath(this, selectedFileUri));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if (file != null) {
                //if we get file and it is not empty then
                final ProgressRequestBody requestBody = new ProgressRequestBody(this, file);

                final MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.uploadFile(body)
                                .enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {

                                        String image_processed_link = new StringBuilder("http://0.0.0.0/" +
                                                response.body().replace("\"", "")).toString();
                                        System.out.println(image_processed_link);
                                        System.out.println(response.body());

                                        Toast.makeText(ScanActivity.this, "Please wait, Image is processing..", Toast.LENGTH_SHORT).show();

                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast.makeText(ScanActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                    }
                }).start();

            }
        }
        else {
            Toast.makeText(this, "Cannot upload this file..", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onProgressUpdate(int percent) {

    }
}
