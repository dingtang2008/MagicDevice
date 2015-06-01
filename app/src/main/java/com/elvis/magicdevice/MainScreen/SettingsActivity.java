package com.elvis.magicdevice.MainScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.elvis.magicdevice.FileChooser.FileChooserActivity;
import com.elvis.magicdevice.MainScreen.ui.BootstrapButton;
import com.elvis.magicdevice.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class SettingsActivity extends PreferenceActivity {


    public static final String[] product_model = new String[]{"product_model", "ro.product.model"};
    public static final String[] product_name = new String[]{"product_name", "ro.product.name"};
    public static final String[] product_device = new String[]{"product_device", "ro.product.device"};
    public static final String[] product_manufacturer = new String[]{"product_manufacturer", "ro.product.manufacturer"};
    public static final String[] product_brand = new String[]{"product_brand", "ro.product.brand"};
    public static final String[] basebrand = new String[]{"basebrand", "ro.baseband"};//PROPERTY_BASEBAND_VERSION = "gsm.version.baseband";
    public static final String[] bluetoothname = new String[]{"bluetoothname", "ro.bluetooth.name"};
    public static final String[] build_charaters = new String[]{"build_charaters", "ro.build.characteristics"};
    public static final String[] build_description = new String[]{"build_description", "ro.build.description"};
    public static final String[] build_displayid = new String[]{"build_displayid", "ro.build.display.id"};
    public static final String[] build_fingerprint = new String[]{"build_fingerprint", "ro.build.fingerprint"};
    public static final String[] build_host = new String[]{"build_host", "ro.build.host"};
    public static final String[] build_id = new String[]{"build_id", "ro.build.id"};
    public static final String[] build_product = new String[]{"build_product", "ro.build.product"};
    public static final String[] build_tags = new String[]{"build_tags", "ro.build.tags"};
    public static final String[] build_type = new String[]{"build_type", "ro.build.type"};
    public static final String[] build_user = new String[]{"build_user", "ro.build.user"};
    public static final String[] build_codename = new String[]{"build_codename", "ro.build.version.codename"};
    public static final String[] build_incremental = new String[]{"build_incremental", "ro.build.version.incremental"};
    public static final String[] build_release = new String[]{"build_release", "ro.build.version.release"};
    public static final String[] build_sdk = new String[]{"build_sdk", "ro.build.version.sdk"};
    public static final String[] carrier = new String[]{"carrier", "ro.carrier"};

    public static final String[] boad = new String[]{"boad", "ro.board.platform"};
    public static final String[] serial = new String[]{"serial", "ro.serialno"};
    public static final String[] imei = new String[]{"imei", "ro.imei"};
    public static final String[] imsi = new String[]{"imsi", "ro.imsi"};

    public static final String[] wifi_name = new String[]{"wifi_name", "ro.wifiname"};
    public static final String[] androiodid = new String[]{"androidid", "ro.androidid"};
    public static final String[] phone_number = new String[]{"phone_number", "ro.phonenumber"};

    public static final String[] wifimac = new String[]{"wifimac", "ro.wifimac"};
    public static final String[] btaddr = new String[]{"btaddr", "ro.btaddr"};
    private static HashMap<String, String> changedInfo = new HashMap<String, String>();

    //private static HashSet<DeviceItem> deviceItems = new HashSet<DeviceItem>();

    public static HashMap<String, String> info = new HashMap<String, String>() {


        {
            put(product_model[0], product_model[1]);
            put(product_name[0], product_name[1]);
            put(product_device[0], product_device[1]);
            put(product_manufacturer[0], product_manufacturer[1]);
            put(product_brand[0], product_brand[1]);
            put(basebrand[0], basebrand[1]);
            put(bluetoothname[0], bluetoothname[1]);
            put(build_charaters[0], build_charaters[1]);
            put(build_description[0], build_description[1]);
            put(build_displayid[0], build_displayid[1]);
            put(build_fingerprint[0], build_fingerprint[1]);
            put(build_host[0], build_host[1]);
            put(build_id[0], build_id[1]);
            put(build_product[0], build_product[1]);
            put(build_tags[0], build_tags[1]);
            put(build_type[0], build_type[1]);
            put(build_user[0], build_user[1]);
            put(build_codename[0], build_codename[1]);
            put(build_incremental[0], build_incremental[1]);
            put(build_release[0], build_release[1]);
            put(build_sdk[0], build_sdk[1]);
            put(carrier[0], carrier[1]);
            put(boad[0], boad[1]);
            put(serial[0], serial[1]);
            put(imei[0], imei[1]);
            put(imsi[0], imsi[1]);
            put(wifi_name[0], wifi_name[1]);
            put(androiodid[0], androiodid[1]);
            put(phone_number[0], phone_number[1]);
            put(wifimac[0], wifimac[1]);
            put(btaddr[0], btaddr[1]);
        }
    };
    public static HashMap<String, DeviceItem> deviceProfile = new HashMap<String, DeviceItem>() {


        {
            put(product_model[0], new DeviceItem(product_model));
            put(product_name[0], new DeviceItem(product_name));
            put(product_device[0], new DeviceItem(product_device));
            put(product_manufacturer[0], new DeviceItem(product_manufacturer));
            put(product_brand[0], new DeviceItem(product_brand));
            put(basebrand[0], new DeviceItem(basebrand));
            put(bluetoothname[0], new DeviceItem(bluetoothname));
            put(build_charaters[0], new DeviceItem(build_charaters));
            put(build_description[0], new DeviceItem(build_description));
            put(build_displayid[0], new DeviceItem(build_displayid));
            put(build_fingerprint[0], new DeviceItem(build_fingerprint));
            put(build_host[0], new DeviceItem(build_host));
            put(build_id[0], new DeviceItem(build_id));
            put(build_product[0], new DeviceItem(build_product));
            put(build_tags[0], new DeviceItem(build_tags));
            put(build_type[0], new DeviceItem(build_type));
            put(build_user[0], new DeviceItem(build_user));
            put(build_codename[0], new DeviceItem(build_codename));
            put(build_incremental[0], new DeviceItem(build_incremental));
            put(build_release[0], new DeviceItem(build_release));
            put(build_sdk[0], new DeviceItem(build_sdk));
            put(carrier[0], new DeviceItem(carrier));
            put(boad[0], new DeviceItem(boad));
            put(serial[0], new DeviceItem(serial));
            put(imei[0], new DeviceItem(imei));
            put(imsi[0], new DeviceItem(imsi));
            put(wifi_name[0], new DeviceItem(wifi_name));
            put(androiodid[0], new DeviceItem(androiodid));
            put(phone_number[0], new DeviceItem(phone_number));
            put(wifimac[0], new DeviceItem(wifimac));
            put(btaddr[0], new DeviceItem(btaddr));
        }
    };


    private BootstrapButton randomBTN, exportCfgBTN, imoprtCfgBTN;
    private GeneratorUtil mAutoGen = new GeneratorUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefs);
        randomBTN = (BootstrapButton) findViewById(R.id.random);
        imoprtCfgBTN = (BootstrapButton) findViewById(R.id.importbtn);
        exportCfgBTN = (BootstrapButton) findViewById(R.id.exportbtn);
        randomBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePreferences(true);
                Intent intent =new Intent();
                intent.setClass(SettingsActivity.this, FileChooserActivity.class);
                startActivity(intent);

            }
        });
        imoprtCfgBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deviceProfile = (HashMap<String, DeviceItem>) readObjectFromFile();
                if (deviceProfile != null) updatePreferences(false);
            }
        });
        exportCfgBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeObjectToFile(deviceProfile);

            }
        });
    }

    private void updatePreferences(boolean isRandomNeeded) {
        if (isRandomNeeded) prepareRandomProfile();
        Preference pref;
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();

        //clear it first when update it
        editor.clear();
        for (String key : deviceProfile.keySet()) {
            pref = findPreference(key);
            if (pref instanceof EditTextPreference)
                //update pref summary
                pref.setSummary(deviceProfile.get(key).getTargetValue());

            //update shared preference
            editor.putString(key, deviceProfile.get(key).getTargetValue());


        }

        editor.commit();
    }

    private void prepareRandomProfile() {
//        for (String key : deviceProfile.keySet()) {
//            deviceProfile.get(key).setDefaultvalue(mAutoGen.getRandomProp(key));
//        }
        mAutoGen.prepareProfile(this.getApplicationContext(), deviceProfile);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setupSimplePreferencesScreen();
    }

    private void setupSimplePreferencesScreen() {


        setTitle(R.string.pref_header_deviceinfo);
        addPreferencesFromResource(R.xml.pref_general);
        PreferenceCategory fakeHeader = new PreferenceCategory(this);
        fakeHeader.setTitle(R.string.pref_header_btwifi);
        getPreferenceScreen().addPreference(fakeHeader);

        addPreferencesFromResource(R.xml.pref_notification);

        fakeHeader = new PreferenceCategory(this);
        fakeHeader.setTitle(R.string.pref_header_data_sync);
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.pref_data_sync);


        for (String key : deviceProfile.keySet()) {
            Log.e("elvisding2", "key=" + key);
            bindPreferenceSummaryToValue(findPreference(key));
        }


//        bindPreferenceSummaryToValue(findPreference("example_list"));
//        bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
//        bindPreferenceSummaryToValue(findPreference("sync_frequency"));
    }


    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            Log.e("elvisding", "stringvalue=" + stringValue);
            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else if (preference instanceof EditTextPreference) {
                // For all other preferences, set the summary to the value's
                // simple string representation.

                changedInfo.put(info.get(preference.getKey()), stringValue);


                preference.setSummary(stringValue);
                preference.setDefaultValue(stringValue);
            }
            return true;
        }
    };

    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        Log.e("elvisding", "preference.getKey()=" + preference.getKey());
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        if (deviceProfile.get(preference.getKey()) != null) {
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, deviceProfile.get(preference.getKey()).getDefaultvalue());

            preference.setDefaultValue(deviceProfile.get(preference.getKey()).getDefaultvalue());

            //preference
        }

    }


    public static void putString(Preference p, String key, String value) {

        SharedPreferences.Editor editor = p.getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Preference p, String key, String defValue) {
        String s = null;
        try {
            s = p.getSharedPreferences().getString(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static void writeObjectToFile(Object obj) {
        createSDCardDir();
        File file = new File(Environment.getExternalStorageDirectory() + "/MagicDevice/Profile/test.dat");
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
            Log.e("elvisdingwr", "write object success!");
        } catch (IOException e) {
            Log.e("elvisdingwr", "write object failed!");
            e.printStackTrace();
        }
    }

    public static Object readObjectFromFile() {
        Object temp = null;
        File file = new File(Environment.getExternalStorageDirectory() + "/MagicDevice/Profile/test.dat");

        if (!file.exists()) {
            try {
                file.createNewFile();
                Log.e("elvisdingwr", "read error success!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(in);
            temp = objIn.readObject();
            objIn.close();
            Log.e("elvisdingwr", "read object success!");
        } catch (IOException e) {
            Log.e("elvisdingwr", "read object failed!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static void createSDCardDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            String path = sdcardDir.getPath() + "/MagicDevice/Profile";
            File path1 = new File(path);
            if (!path1.exists()) {
                //若不存在，创建目录，可以在应用启动的时候创建
                path1.mkdirs();
                //setTitle("paht ok,path:"+path);
            }
        } else {
            Log.e("elvisdingwr", "create path failed!"); // setTitle("false");
            return;

        }

    }
}
