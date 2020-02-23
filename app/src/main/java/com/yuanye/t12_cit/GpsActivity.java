package com.yuanye.t12_cit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class GpsActivity extends AppCompatActivity {

    private TextView textView;
    private LocationManager locationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("GPS测试");
        setContentView(R.layout.activity_gps);
        textView = findViewById(R.id.textview);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!isGpsAble(locationManager)) {
            Toast.makeText(GpsActivity.this, "请打开Gps!", Toast.LENGTH_SHORT).show();
            openGps();
        }
        // 从gps获取最近的定位信息
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location lc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateShow(lc);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
    }

    // 定义更新显示的方法
    private void updateShow(Location location){
        if (location!=null){
            StringBuilder sb =new StringBuilder();
            sb.append("当前gps位置定位信息:\n");
            sb.append("经度:"+location.getLongitude()+"\n");
            sb.append("维度:"+location.getLatitude()+"\n");
            sb.append("海拔:"+location.getAltitude()+"\n");
            sb.append("速度:"+location.getSpeed()+"\n");
            sb.append("方位:"+location.getBearing()+"\n");
            sb.append("时间:"+location.getTime()+"\n");
            sb.append("定位精度:"+location.getLongitude()+"\n");
            textView.setText(sb.toString());
        }else
            textView.setText("");
    }

    private boolean isGpsAble(LocationManager lm) {
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)?true:false;
    }
    // 打开设置界面让用户自己设置
    private void openGps(){
        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivityForResult(intent,0);
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateShow(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            // 当gpsLocationProvider可用时,更新定位
            if (ActivityCompat.checkSelfPermission(GpsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GpsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            updateShow(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onProviderDisabled(String s) {
            updateShow(null);
        }
    };
}
