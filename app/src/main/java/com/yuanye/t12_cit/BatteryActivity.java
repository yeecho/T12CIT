package com.yuanye.t12_cit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanye.t12_cit.system.MyApplication;

public class BatteryActivity extends AppCompatActivity{

    private Button btnPass, btnFail;
    private TextView txtTips;
    private TextView txtTips2;
    private TextView txtVolt;
    private TextView txtCapacity;
    private TextView txtBurnInState;
    private TextView txtQEN;
    private TextView txtChargingState;
    private TextView txtLevel;
    private TextView txtHealthState;
    private TextView txtCurrent;
    private TextView txtTemperature;
    private BatteryManager batteryManager;
    private BatteryStateReceiver receiver;
    private Handler handler;
    private int count = 10;
    private int test_item = 0;

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            btnPass.setVisibility(View.VISIBLE);
            btnFail.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        setTitle("电池测试");
        initView();
        initData();
        startTest();
    }

    private void startTest() {
        test_item = 1;
        handler.postDelayed(runnable, 1000);
    }

    private void initData() {
        batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        receiver = new BatteryStateReceiver();
        registerReceiver(receiver, filter);
        txtCapacity.setText("容量："+getBatteryCapacity(this)+"mAh");
        handler = new MyHandler();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void initView() {
        findViewById(R.id.btnTest).setVisibility(View.GONE);
        btnPass = findViewById(R.id.btnPass);
        btnPass.setVisibility(View.GONE);
        btnFail = findViewById(R.id.btnFail);
        btnFail.setVisibility(View.GONE);
        txtTips = findViewById(R.id.tips);
        txtTips2 = findViewById(R.id.tips2);
        txtVolt = findViewById(R.id.txtVolt);
        txtCapacity = findViewById(R.id.txtCapacity);
        txtBurnInState = findViewById(R.id.txtBurnInState);
        txtQEN = findViewById(R.id.txtQEN);
        txtChargingState = findViewById(R.id.txtChargingState);
        txtLevel = findViewById(R.id.txtLevel);
        txtHealthState = findViewById(R.id.txtHealthState);
        txtCurrent = findViewById(R.id.txtCurrent);
        txtTemperature = findViewById(R.id.txtTemperature);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (count >= 0){
                txtTips2.setText(""+count+"秒");
                count--;
                handler.postDelayed(this, 1000);
            }else{
                txtTips2.setTextColor(Color.RED);
                txtTips2.setText("测试失败");
                MyApplication.setResults(BatteryActivity.this, false);
                finish();
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

    public double getBatteryCapacity(Context context) {
        Object mPowerProfile;
        double batteryCapacity =0; //电池的容量mAh
        final String POWER_PROFILE_CLASS ="com.android.internal.os.PowerProfile";
        try{
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS).getConstructor(Context.class).newInstance(context);
            batteryCapacity = (double) Class.forName(POWER_PROFILE_CLASS).getMethod("getBatteryCapacity").invoke(mPowerProfile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return batteryCapacity;
    }



    class BatteryStateReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                if (test_item == 1){

                    handler.removeCallbacks(runnable);
                    count = 10;
                    txtTips2.setText("充电线插入：成功");
                    txtTips.setText("请断开充电线");
                    test_item = 2;
                    txtTips2.setTextColor(Color.BLACK);
                    handler.postDelayed(runnable, 1000);
                }
//                Toast.makeText(context, "充电", Toast.LENGTH_LONG).show();
            }else if(intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
                if (test_item == 2){

                    handler.removeCallbacks(runnable);
                    count = 10;
                    txtTips2.setText("充电线断开：成功");
                    txtTips.setText("");
                    test_item = 0;
                    handler.sendEmptyMessage(0);
                }
//                Toast.makeText(context, "未充电", Toast.LENGTH_LONG).show();
            }else {

                //电压
                int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
                txtVolt.setText("电压：" + voltage / 1000 + "." + voltage % 1000 + "V");

                //容量
//            int currentLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
//            txtCapacity.setText("容量："+currentLevel);

                //老化

                //QEN

                //电量
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
                int levelPercent = (int)(((float)level / scale) * 100);
                txtLevel.setText("电量：" + levelPercent + "%");
                if (level <= 10) {
                    txtLevel.setTextColor(Color.RED);
                } else {
                    txtLevel.setTextColor(Color.BLUE);
                }

                //充电状态
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
                String strStatus = "未知状态";
                switch (status) {
                    case BatteryManager.BATTERY_STATUS_CHARGING:
                        strStatus = "充电中……";
//                    mTvStatus.setTextColor(Color.BLUE);
                        break;
                    case BatteryManager.BATTERY_STATUS_DISCHARGING:
                        strStatus = "放电中……";
                        break;
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                        strStatus = "未充电";
                        break;
                    case BatteryManager.BATTERY_STATUS_FULL:
                        strStatus = "充电完成";
                        break;
                }
                txtChargingState.setText("状态：" + strStatus);

                //健康状态
                int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN);
                String strHealth = "未知 :(";
                txtHealthState.setTextColor(Color.BLACK);
                switch (status) {
                    case BatteryManager.BATTERY_HEALTH_GOOD:
                        strHealth = "好 :)";
                        txtHealthState.setTextColor(Color.GREEN);
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                        strHealth = "过热！";
                        txtHealthState.setTextColor(Color.RED);
                        break;
                    case BatteryManager.BATTERY_HEALTH_DEAD: // 未充电时就会显示此状态，这是什么鬼？
                        strHealth = "良好";
                        txtHealthState.setTextColor(Color.GREEN);
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                        strHealth = "电压过高！";
                        txtHealthState.setTextColor(Color.RED);
                        break;
                    case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                        strHealth = "未知 :(";
                        break;
                    case BatteryManager.BATTERY_HEALTH_COLD:
                        strHealth = "过冷！";
                        txtHealthState.setTextColor(Color.RED);
                        break;
                }
                txtHealthState.setText("健康状况：" + strHealth);

                //电池温度
                int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
                txtTemperature.setText("温度：" + temperature / 10 + "." + temperature % 10 + "℃");

                //电流
                int current = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                txtCurrent.setText("电流: "+ current);

            }
        }
    }
}
