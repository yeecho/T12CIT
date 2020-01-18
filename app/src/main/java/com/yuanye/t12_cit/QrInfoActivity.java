package com.yuanye.t12_cit;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.os.Build;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.yuanye.t12_cit.util.EncodingHandler;

public class QrInfoActivity extends Activity {

    private String tag = "QrInfoActivity";
    private WifiManager mWifiManager;
    private ImageView imgvSn, imgvImei, imgvMeid, imgvBt, imgvWifi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.yy_qr_code);
        setContentView(R.layout.activity_qrinfo);
        imgvSn = findViewById(R.id.qr_sn);
        imgvImei = findViewById(R.id.qr_imei);
        imgvMeid = findViewById(R.id.qr_meid);
        imgvBt = findViewById(R.id.qr_bt);
        imgvWifi = findViewById(R.id.qr_wifi);
        imgvSn.setImageBitmap(getQrPic("SN", 200));
        imgvImei.setImageBitmap(getQrPic("IMEI", 200));
        imgvMeid.setImageBitmap(getQrPic("MEID", 200));
        imgvBt.setImageBitmap(getQrPic("BT", 200));
        imgvWifi.setImageBitmap(getQrPic("WIFI", 200));
    }

    public static Bitmap getQrPic(String str, int widthAndHeight){
        //Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.myhead);
        Bitmap bitmap = null;
        try {
            bitmap = EncodingHandler.createQRCode(str, widthAndHeight);
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }

    private void setWifiStatus() {
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
//        boolean hasMacAddress = wifiInfo != null && wifiInfo.hasRealMacAddress();
        boolean hasMacAddress = wifiInfo != null;
        String macAddress = hasMacAddress ? wifiInfo.getMacAddress() : null;
//        Log.d(tag, macAddress);
    }

    private String setBtStatus() {
        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        if (bluetooth != null) {
            String address = bluetooth.isEnabled() ? bluetooth.getAddress() : null;
//            Log.d(tag, address);
            return address;
        }
        return "none";
    }
}
