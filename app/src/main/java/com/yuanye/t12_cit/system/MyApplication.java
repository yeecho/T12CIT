package com.yuanye.t12_cit.system;

import android.app.Activity;
import android.app.Application;

import com.yuanye.t12_cit.R;
import com.yuanye.t12_cit.constant.YC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MyApplication extends Application{

    public static List<String> testNames, testReults;

    @Override
    public void onCreate() {
        super.onCreate();

        String[] items = getResources().getStringArray(R.array.item_names);
        testNames = Arrays.asList(items);
        testReults = new ArrayList<>();
        for (int i = 0; i<testNames.size(); i++){
            testReults.add("N");
        }
    }

    public static void setResults(Activity activity, boolean b){
        for (int i = 0; i< YC.classes.length; i++){
            if (YC.classes[i] == activity.getClass()){
                testReults.set(i, b ? "P" : "F");
            }
        }

    }
}
