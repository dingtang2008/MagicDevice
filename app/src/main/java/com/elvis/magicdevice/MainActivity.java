package com.elvis.magicdevice;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.elvis.magicdevice.RootTools.NameGenerator;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Elvis Ding
 */
public class MainActivity extends Activity {

    private HashMap<String, String> map ;
    public static final String product_model = "ro.product.model";
    public static final String product_name = "ro.product.name";
    public static final String product_manufacturer = "ro.product.device";
    public static final String product_brand = "ro.product.brand";
    public static final String basebrand = "ro.baseband";
    public static final String bluetoothname = "ro.bluetooth.name";
    public static final String build_charaters = "ro.build.characteristics";
    public static final String build_description = "ro.build.description";
    public static final String build_displayid = "ro.build.display.id";
    public static final String build_fingerprint = "ro.build.fingerprint";
    public static final String build_host = "ro.build.host";
    public static final String build_id = "ro.build.id";
    public static final String build_product = "ro.build.product";
    public static final String build_tags = "ro.build.tags";
    public static final String build_type = "ro.build.type";
    public static final String build_user = "ro.build.user";
    public static final String build_all_codenames = "ro.build.version.all_codenames";
    public static final String build_codename = "ro.build.version.codename";
    public static final String build_incremental = "ro.build.version.incremental";
    public static final String build_release = "ro.build.version.release";
    public static final String build_sdk = "ro.build.version.sdk";
    public static final String carrier = "ro.carrier";

    public static final String replacePath = "/system/build.prop";
    public static final String replacePath1 = "/system/build.prop.bak";

    protected static final String TAG = "ElvisDing";
    public static String cur_path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for test
        String[] commands = new String[]{"mount -o rw,remount /system",
                "echo " + "\'2222222\' >" + cur_path + "/d",
                "cp " + replacePath + " " + cur_path + "/"};

        // / cp -r /data/data/com.waze/ /sdcard/11/
        String[] copycmd = new String[]{"cp -r /data/data/com.waze/  /sdcard/test/ "};
        BuildPropTool.setProp(product_model, "dingtang");


        TextView imei = (TextView) findViewById(R.id.editText1);
        imei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e(TAG, imei_gen());
            }
        });

        String test = "Linux version 3.10.49-g344651c (dingtang@50409-26-1) (gcc version 4.9.x-google 20140827 (prerelease) (GCC) ) #1 SMP PREEMPT Thu May 14 15:08:22 CST 2015";
        Matcher localMatcher = Pattern.compile("\\w+\\s+\\w+\\s+([^\\s]+)\\s+\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+([^\\s]+)\\s+(?:PREEMPT\\s+)?(.+)").matcher(test);
        if (localMatcher.matches()) {
            String str2 = localMatcher.group(1) + "\n" + localMatcher.group(2) + " " + localMatcher.group(3) + "\n" + localMatcher.group(4);

            String str = str2;
            int i = str.indexOf("-");
            if (i != -1) {
                str = str.substring(0, i);
            }
            int j = str.indexOf("\n");
            if (j != -1) {
                str = str.substring(0, j);
            }

            Log.e(TAG, str);
        }
       // imei.setText(generatorIMSI());
        NameGenerator gen = new NameGenerator();
        for (int i = 0; i < 30; i++) {
            System.out.println(gen.getName());
        }
    }


    private void buildProp() {

        map.put("xxx", "");



    }
}
