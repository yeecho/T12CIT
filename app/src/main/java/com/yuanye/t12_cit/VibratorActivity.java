package com.yuanye.t12_cit;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yuanye.t12_cit.system.MyApplication;

public class VibratorActivity extends AppCompatActivity{

    Vibrator vibrator;
    boolean isTesting;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("马达");
        setContentView(R.layout.activity_vibrator);
        button = findViewById(R.id.btnTest);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vibrator.cancel();
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
                if (isTesting){
                    vibrator.cancel();
                    isTesting = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            button.setText("测试");
                        }
                    });
                }else{
                    vibrator.vibrate(300*1000);
                    isTesting = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            button.setText("关闭");
                        }
                    });
                }
                break;
        }

    }
}
