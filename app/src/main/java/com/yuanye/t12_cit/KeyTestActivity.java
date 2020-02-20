package com.yuanye.t12_cit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.yuanye.t12_cit.system.MyApplication;

public class KeyTestActivity extends AppCompatActivity {

    TextView textView, textView2, textView3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("按键测试");
        setContentView(R.layout.activity_keytest);
        findViewById(R.id.btnTest).setVisibility(View.GONE);
        textView = findViewById(R.id.textview);
        textView2 = findViewById(R.id.textview2);
        textView3 = findViewById(R.id.textview3);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            textView.setBackgroundColor(ContextCompat.getColor(this, R.color.textview_green));
        } else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            textView2.setBackgroundColor(ContextCompat.getColor(this, R.color.textview_green));
        }
//        return super.onKeyDown(keyCode, event);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        textView3.setBackgroundColor(ContextCompat.getColor(this, R.color.textview_green));
//        return super.onTouchEvent(event);
        return true;
    }
}
