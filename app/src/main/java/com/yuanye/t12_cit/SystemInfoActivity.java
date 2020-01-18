package com.yuanye.t12_cit;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yuanye.t12_cit.system.MyApplication;

public class SystemInfoActivity extends AppCompatActivity {

    TextView txtKernel, txtProductModel, txtBuildNumber,
            txtMipiLcd, txtTouchPanel,
            txtSerialNumber;

    boolean test_result = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("系统信息");
        setContentView(R.layout.activity_system_info);
        txtKernel = findViewById(R.id.kernel);
        txtProductModel = findViewById(R.id.product_model);
        txtBuildNumber = findViewById(R.id.build_number);
        txtMipiLcd = findViewById(R.id.mipi_lcd);
        txtTouchPanel = findViewById(R.id.touch_panel);
        txtSerialNumber = findViewById(R.id.serial_number);
        init();
    }

    private void init() {
        int color = getColor(R.color.color_system_info_pass);

        String kernel = System.getProperty("os.version");
        txtKernel.setText("Kernel版本： " + kernel);
        if (kernel.equals("4.4.167")) {
            txtKernel.setTextColor(color);
        }else{
            test_result = false;
        }

        String productModel = Build.MODEL;
        txtProductModel.setText("Product Model: " + productModel);
        if (productModel.equals("EBEN T12")) {
            txtProductModel.setTextColor(color);
        }else{
            test_result = false;
        }

        String buildNumber = Build.DISPLAY;
        txtBuildNumber.setText("Build Number: " + buildNumber);
        if (buildNumber.equals("rk3399_mid-userdebug 8.1.0 OPM8.190605.005 153515 test-keys")) {
            txtBuildNumber.setTextColor(color);
        }else{
            test_result = false;
        }
        String serial = Build.SERIAL;
        txtSerialNumber.setText("序列号: "+serial);
        if (serial.contains("ERO")) {
            txtSerialNumber.setTextColor(color);
        }else{
            test_result = false;
        }
        MyApplication.setResults(this, test_result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
