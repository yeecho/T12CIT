package com.yuanye.t12_cit;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuanye.t12_cit.system.MyApplication;
import com.yuanye.t12_cit.util.CameraUtil;

import java.io.File;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    private ImageView ivTest,ivTest2;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("摄像头");
        setContentView(R.layout.activity_camera);
        ivTest = findViewById(R.id.ivTest);
        ivTest2 = findViewById(R.id.ivTest2);
//        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "Pictures/" + System.currentTimeMillis() + ".jpg");
    }

    public void topClick(View v){
        switch (v.getId()){
            case R.id.btnPass:
                MyApplication.setResults(this, true);
                finish();
                break;
            case R.id.btnFail:
                MyApplication.setResults(this, false);
                finish();
                break;
            case R.id.btnTest:
//                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                Uri uri = getOutputMediaFileUri(this);
//                Log.d("yeecho:", uri.toString());
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                startActivityForResult(intent, 666);
                goCamera();
                break;
        }
    }

    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    //激活相机操作
    private void goCamera() {
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "Pictures/" + System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 1);
    }

    public static Uri getOutputMediaFileUri(Context context) {
        File mediaFile = null;
        String cameraPath;
        try {
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }
            mediaFile = new File(mediaStorageDir.getPath()
                    + File.separator
                    + "Pictures/external_files.jpg");//注意这里需要和filepaths.xml中配置的一样
            cameraPath = mediaFile.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {// sdk >= 24  android7.0以上
            Uri contentUri = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".fileprovider",//与清单文件中android:authorities的值保持一致
                    mediaFile);//FileProvider方式或者ContentProvider。也可使用VmPolicy方式
            return contentUri;

        } else {
            return Uri.fromFile(mediaFile);//或者 Uri.isPaise("file://"+file.toString()

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }
            Log.d("拍照返回图片路径:", photoPath);
            if (count%2 == 0){
                Glide.with(this).load(photoPath).into(ivTest);
            }else{
                Glide.with(this).load(photoPath).into(ivTest2);
            }
            count ++;

        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = CameraUtil.getRealPathFromUri(this, data.getData());
            Glide.with(this).load(photoPath).into(ivTest);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
