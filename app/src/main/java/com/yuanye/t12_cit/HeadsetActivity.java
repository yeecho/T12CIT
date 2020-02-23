package com.yuanye.t12_cit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yuanye.t12_cit.system.MyApplication;

public class HeadsetActivity extends AppCompatActivity {

    TextView textView;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("耳机测试");
        setContentView(R.layout.activity_headset);
        textView = findViewById(R.id.textview);
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        receiver = new HeadsetReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
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
                break;
        }

    }

    class HeadsetReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getIntExtra("state",0)==1) {
                textView.setText("耳机检测：插入");
            }
        }
    }
}
