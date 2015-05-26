package com.elvis.magicdevice.Content;

import android.os.Build;

import com.elvis.magicdevice.BuildPropTool;

import java.util.ArrayList;

public class Utils {
    public static final String product_model = "ro.product.model";
    public static final String product_name = "ro.product.name";
    public static final String product_manufacturer = "ro.product.device";
    public static final String product_brand = "ro.product.brand";
    public static final String basebrand = "ro.baseband";//PROPERTY_BASEBAND_VERSION = "gsm.version.baseband";
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
    public static ArrayList<String> info = new ArrayList<String>() {


        {
            add(product_model);
            add(product_name);
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
            add(build_all_codenames);
            add(build_codename);
            add(build_incremental);
            add(build_release);
            add(build_sdk);
            add(carrier);
        }
    };

    public Utils(ArrayList<String> info) {
        this.info = info;
    }
}