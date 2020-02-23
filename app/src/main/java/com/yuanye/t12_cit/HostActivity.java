package com.yuanye.t12_cit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import com.yuanye.t12_cit.bean.StorageBean;
import com.yuanye.t12_cit.system.MyApplication;
import com.yuanye.t12_cit.util.StorageUtil;

import java.io.File;
import java.util.List;

public class HostActivity extends AppCompatActivity {

    private final String VIDEO_NAME = "video_test.mp4";

    private TextView textView, textView2;
    private VideoView videoView;

    private UsbReceiver usbReceiver;
    private Handler hanlder;
    private int count = 30;
    StorageManager storageManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Host测试");
        setContentView(R.layout.activity_host);
        textView = findViewById(R.id.textview);
        textView2 = findViewById(R.id.textview2);
        videoView = findViewById(R.id.videoview);
        setupVideo();
        hanlder = new MyHandler();
        hanlder.postDelayed(runnable, 1500);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_EJECT);
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_CHECKING);
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
        filter.addDataScheme("file");
        usbReceiver = new UsbReceiver();
        this.registerReceiver(usbReceiver, filter);

        storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        storageManager.registerListener(storageEventListener);
    }

    StorageEventListener storageEventListener = new StorageEventListener(){
        @Override
        public void onStorageStateChanged(String path, String oldState, String newState) {
            super.onStorageStateChanged(path, oldState, newState);

        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (count >= 0){
                textView2.setText(""+ count +"秒");
                count--;
                hanlder.postDelayed(this, 1000);
            }else{
                textView.setText("测试失败");
                hanlder.removeCallbacks(runnable);
                hanlder.sendEmptyMessage(0);
            }

        }
    };

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
                break;
        }

    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                MyApplication.setResults(HostActivity.this, false);
                finish();
            }else{

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!videoView.isPlaying()){
            videoView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView.canPause()){
            videoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlaybackVideo();
        unregisterReceiver(usbReceiver);
    }

    private void setupVideo() {
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaybackVideo();
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                stopPlaybackVideo();
                return true;
            }
        });

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaybackVideo();
                hanlder.sendEmptyMessage(1);
            }
        });

//        try {
//            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + R.raw.video_test);
//            videoView.setVideoURI(uri);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void loadVideoResource(String videoPath){
        try{
            videoView.setVideoPath(videoPath);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void stopPlaybackVideo() {
        try {
            videoView.stopPlayback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkSDcard(List<StorageBean> volumes) {
        if (volumes.size()>0){
            for (StorageBean storageBean : volumes){
                if (storageBean.getRemovable()){
//                    txtVolumeName.setText("外置存储："+storageBean.getPath());
                    File[] files = new File(storageBean.getPath()).listFiles();
                    if (files != null){
                        if (files.length>0){
                            for (File f : files){
                                if (f.getName().equals(VIDEO_NAME)){
                                    loadVideoResource(f.getAbsolutePath());
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    class UsbReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Log.d(tag, "ACTION:"+intent.getAction().toString());
//            Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
            switch (intent.getAction()){
                case Intent.ACTION_MEDIA_CHECKING:
                    textView.setText("设备检测中..");
                    break;
                case Intent.ACTION_MEDIA_MOUNTED:
                    textView.setText("设备已挂载");
                    textView2.setText("");
                    hanlder.removeCallbacks(runnable);
                    checkSDcard(StorageUtil.getStorageData(HostActivity.this));
//                    setupVideo();
                    break;
//                case Intent.ACTION_MEDIA_EJECT:
////                    adapter.clear();
//                    textView.setText("设备强制移除");
//                    break;
                case Intent.ACTION_MEDIA_REMOVED:
//                    adapter.clear();
                    textView.setText("设备已移除");
                    break;
            }
        }
    }

}
