package com.yuanye.t12_cit.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.google.zxing.WriterException;
import com.yuanye.t12_cit.Exception.ResultProblemException;
import com.yuanye.t12_cit.R;
import com.yuanye.t12_cit.constant.YC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YY {

    public static SharedPreferences getSP(Context context){
        return context.getSharedPreferences(YC.SHARE_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static List<String> getTestItems(Context context) {
        String[] items = context.getResources().getStringArray(R.array.item_names);
        return Arrays.asList(items);
    }

    public static List<String> getTestResults(Context context){
        String results = getSP(context).getString(YC.SP_RESULTS, "");
        List<String> testResults;
        int size = getTestItems(context).size();
        if (results.equals("")){
            testResults = new ArrayList<>();
            for (int i = 0; i<size ; i++){
                testResults.add("N");
            }
        }else{
            testResults = Arrays.asList(results.split("-"));
            if (testResults.size() != size){
//                throw new ResultProblemException();
            }
        }
        return testResults;
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
}
