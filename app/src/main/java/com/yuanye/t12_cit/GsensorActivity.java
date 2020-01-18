package com.yuanye.t12_cit;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yuanye.t12_cit.system.MyApplication;

import org.w3c.dom.Text;

public class GsensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gsensor;
    private TextView txtX,txtY,txtZ;
    private float mLastX,mLastY,mLastZ;
    private String sX,sY,sZ;
    private boolean isTesting;
    private boolean xp,yp,zp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("G-SENSOR");
        setContentView(R.layout.activity_gsensor);
        txtX = findViewById(R.id.x);
        txtY = findViewById(R.id.y);
        txtZ = findViewById(R.id.z);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, gsensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onStop(){   //同样在退出activity时要注销监听
        super.onStop();
        if(sensorManager != null){
            sensorManager.unregisterListener(this);
            sensorManager = null;
        }
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
                isTesting = true;
                break;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor == null)
            return ;
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            //获得数据为float类型的数据
            mLastX = sensorEvent.values[0];
            mLastY = sensorEvent.values[1];
            mLastZ = sensorEvent.values[2];
            xp = (mLastX > -0.8 && mLastX < 0.8);
            yp = (mLastY > -0.8 && mLastY < 0.8);
            zp = (mLastZ > 9 && mLastZ < 10.6);
            //将float类型的数据转为字符型供textView显示
            sX = String.valueOf(mLastX);
            sY = String.valueOf(mLastY);
            sZ = String.valueOf(mLastZ);
            if (isTesting){
                if (xp){
                    txtX.setText(sX + " 通过");
                }else{
                    txtX.setText(sX);
                }
                if (yp){
                    txtY.setText(sY + " 通过");
                }else{
                    txtY.setText(sY);
                }
                if (zp){
                    txtZ.setText(sZ + " 通过");
                }else{
                    txtZ.setText(sZ);
                }
                if (xp && yp && zp){
                    MyApplication.setResults(this, true);
                    finish();
                }
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
