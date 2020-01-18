package com.yuanye.t12_cit.constant;

import com.yuanye.t12_cit.AudioActivity;
import com.yuanye.t12_cit.BackLightActivity;
import com.yuanye.t12_cit.BatteryActivity;
import com.yuanye.t12_cit.DCPAcitvity;
import com.yuanye.t12_cit.DRPActivity;
import com.yuanye.t12_cit.DisplayActivity;
import com.yuanye.t12_cit.GsensorActivity;
import com.yuanye.t12_cit.LedActivity;
import com.yuanye.t12_cit.SimActivity;
import com.yuanye.t12_cit.SystemInfoActivity;
import com.yuanye.t12_cit.TFActivity;
import com.yuanye.t12_cit.VibratorActivity;

public class YC {
    public static final String SHARE_PREF_NAME = "T12CIT";
    public static final String SP_RESULTS = "test_results";

    public static final Class[] classes = {
            SystemInfoActivity.class,
            DisplayActivity.class,
            LedActivity.class,
            BackLightActivity.class,
            TFActivity.class,
            DCPAcitvity.class,
            DRPActivity.class,
            VibratorActivity.class,
            AudioActivity.class,
            SimActivity.class,
            GsensorActivity.class,
            BatteryActivity.class
    };

}
