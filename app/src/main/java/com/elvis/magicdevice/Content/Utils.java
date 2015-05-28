package com.elvis.magicdevice.Content;

import android.os.Build;

import com.elvis.magicdevice.BuildPropTool;

import java.util.ArrayList;

public class Utils {
    public static final String[] product_model = new String[]{"手机型号", "ro.product.model"};
    public static final String[] product_name = new String[]{"手机名称", "ro.product.name"};
    public static final String[] product_device = new String[]{"采用设备", "ro.product.device"};
    public static final String[] product_manufacturer = new String[]{"制造商", "ro.product.manufacturer"};
    public static final String[] product_brand = new String[]{"品牌", "ro.product.brand"};
    public static final String[] basebrand = new String[]{"基带版本", "ro.baseband"};//PROPERTY_BASEBAND_VERSION = "gsm.version.baseband";
    public static final String[] bluetoothname = new String[]{"蓝牙名", "ro.bluetooth.name"};
    public static final String[] build_charaters = new String[]{"编译", "ro.build.characteristics"};
    public static final String[] build_description = new String[]{"软件版本", "ro.build.description"};
    public static final String[] build_displayid = new String[]{"显示版本", "ro.build.display.id"};
    public static final String[] build_fingerprint = new String[]{"编译指纹", "ro.build.fingerprint"};
    public static final String[] build_host = new String[]{"编译主机", "ro.build.host"};
    public static final String[] build_id = new String[]{"版本ID", "ro.build.id"};
    public static final String[] build_product = new String[]{"产品名", "ro.build.product"};
    public static final String[] build_tags = new String[]{"编译TAG", "ro.build.tags"};
    public static final String[] build_type = new String[]{"编译模式", "ro.build.type"};
    public static final String[] build_user = new String[]{"编译用户", "ro.build.user"};
    public static final String[] build_codename = new String[]{"版本代号", "ro.build.version.codename"};
    public static final String[] build_incremental = new String[]{"版本增量", "ro.build.version.incremental"};
    public static final String[] build_release = new String[]{"Android版本", "ro.build.version.release"};
    public static final String[] build_sdk = new String[]{"SDK版本", "ro.build.version.sdk"};
    public static final String[] carrier = new String[]{"运营商信息", "ro.carrier"};

    public static final String[] boad = new String[]{"平台", "ro.board.platform"};
    public static final String[] serial = new String[]{"序列号", "ro.serialno"};
    public static final String[] imei = new String[]{"机器唯一码", "getimei"};
    public static final String[] imsi = new String[]{"手机入网号", "ro.serialno"};
    //public static final String[] serial = new String[]{"序列号", "ro.serialno"};
    //public static final String[] serial = new String[]{"序列号", "ro.serialno"};
    //public static final String[] serial = new String[]{"序列号", "ro.serialno"};

    public static ArrayList<String[]> info = new ArrayList<String[]>() {


        {
            add(product_model);
            add(product_name);
            add(product_device);
            add(product_manufacturer);
            add(product_brand);
            add(basebrand);
            add(bluetoothname);
            add(build_charaters);
            add(build_description);
            add(build_displayid);
            add(build_fingerprint);
            add(build_host);
            add(build_id);
            add(build_product);
            add(build_tags);
            add(build_type);
            add(build_user);
            add(build_codename);
            add(build_incremental);
            add(build_release);
            add(build_sdk);
            add(carrier);
        }
    };

    public Utils(ArrayList<String[]> info) {
        this.info = info;
    }
}