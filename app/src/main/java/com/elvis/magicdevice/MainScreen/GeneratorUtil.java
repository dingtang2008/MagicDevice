package com.elvis.magicdevice.MainScreen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import com.elvis.magicdevice.R;
import com.elvis.magicdevice.RootTools.NameGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

/**
 * Created by elvis on 15/5/16.
 */
public class GeneratorUtil {

    public final static String URL = "/data/data/com.elvis.magicdevice/files";
    //数据库文件
    public final static String DB_FILE_NAME = "deviceshop.db";
    public final static String TABLE_NAME = "deviceinfo";
    public final static int MANUFACTOR = 0;
    public final static int MODEL = 1;
    public final static int ANDROIDVERSION = 2;
    private static int devicecount = 1039; //hardcode to db count to have better performance
    private SQLiteDatabase db = null;

    //keys
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
    //[ro.build.brand]/[ro.product.name]/[ro.product.device]:[ro.build.version.release]/[ro.b
    //uild.id]/[ro.build.version.incremental]:[ro.build.type]/[ro.build.tags]
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

    //android_id  pm settings put sercure android_id randomString(16,false)
    public static String randomIMEI() {
        int pos;
        int[] imei = new int[15];
        int sum = 0;
        int len = imei.length;
        int m10, mul = 2;

        //
        // Fill in the first two values of the string based with the specified prefix.
        // Reporting Body Identifier list: http://en.wikipedia.org/wiki/Reporting_Body_Identifier
        //

        String[] rbis = new String[]{"01", "10", "30", "33", "35", "44", "45", "49", "50", "51", "52", "53", "54", "86", "91", "98", "99"};
        String rbi = rbis[(int) Math.floor(Math.random() * rbis.length)];
        imei[0] = Integer.valueOf(rbi.substring(0, 1));
        imei[1] = Integer.valueOf(rbi.substring(1, 2));

        pos = 1;

        //
        // Fill all the remaining numbers except for the last one with random values.
        //
        while (pos < len - 2) {
            imei[pos++] = (int) Math.floor(Math.random() * 10) % 10;
        }

        //
        // Calculate the Luhn checksum of the values thus far.
        //
        for (int i = len - 2; i >= 0; i--) {
            if ((imei[i] * mul) >= 10)
                sum += ((imei[i] * mul) / 10) + ((imei[i] * mul) % 10);

            else
                sum += imei[i] * mul;

            if (mul == 2) mul = 1;
            else mul = 2;
        }

        m10 = sum % 10;
        if (m10 == 0) m10 = 10;//fix me
        imei[len - 1] = 10 - m10;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < imei.length; i++)
            sb.append(imei[i]);
        return sb.toString();
    }


    /**
     * MCC:  移动国家码，三个数字，如中国为 460
     * MNC：移动网号，两个数字，如中国移动的MNC为00（联通是01，移动159新号段是02，更多在此）
     * MSIN：移动客户识别号，在某一PLMN中移动台的唯一识别码，MSIN＝H0H1H2H3（S）XXXXXX（共11位）
     * 中国移动网络编号：46000、46002、46007 （国家码+网号）
     * <p/>
     * 中国移动网络段号：
     * 段号代码    手机段号      网络标识
     * 0            159             46002
     * 1            158             46002
     * 2            150             46002
     * 3            151             46002
     * 4            1340-1348       46002
     * 5            135             46000
     * 6            136             46000
     * 7            137             46000
     * 8            138             46000
     * 9            139             46000
     * A            157             46007
     * B            188             46007
     * C            152             46002
     * D            187             46007
     * E            147             46007
     * 159 158 150 151 1340-1348 135 136 137 138 139 157 188 152 187 147
     * <p/>
     * 中国移动SIM卡制造商列表：
     * <p/>
     * <p/>
     * 制造商代码         制造商名称
     * 0　　　　　　　　　 法国斯伦贝谢
     * 1 　　　　　　　　  法国金普斯
     * 2　　　 　　　　　  德国欧伽
     * 3　　　　　　　　　　江西捷德
     * 4　　　　　　　　　　东信和平
     * 5　　　　　　　　　　大唐电信
     * 6　　　　　　　　　　航天九洲通
     * 7　　　　　　　　　　北京握奇
     * 8　　　　　　　　　　东方英卡
     * 9　　　　　　　　　　北京华虹
     * A　　　　　　　　　　上海柯斯
     * <p/>
     * <p/>
     * 中国移动卡性质： 0:普通单模卡    5：远程卡　　E：预付费卡
     * <p/>
     * [中国联通、中国电信]
     * 示例IMSI号码： 46001  2 024 007697 T
     * 匹配正则表达式：(01|03)(\d{2})(\w{1})(\w{1})(\d{3})(\d{6})(\w{1})
     * 中国国际字冠（+86） + 国际网络运营商识别码 + 卡发行年 + 段号 + 段后地区识别码 + 所属城市区号 + 卡编号 + 卡制造商
     * <p/>
     * 中国联通、电信国际字冠：+86
     * 中国联通、电信网络编号：46001（中国联通） 46003（中国电信）
     * 中国联通、电信网络段号：
     * <p/>
     * 段号代码      手机段号            网络标识
     * <p/>
     * 0               130          46001
     * 1　　　　        131　　　　　　46001
     * 2　　　　        132　　　　　　46001
     * 3　　　　        133　　　　　　46003
     * 4　　　　        1349　　　　　 46003
     * 5　　　　        155　　　　　　46001
     * 6　　　        　156　　　　　　46001
     * 7　　　        　180　　　　　　46003
     * 8　　　        　153　　　　　　46003
     * 9　　　        　189　　　　　　46003
     * <p/>
     * <p/>
     * 中国联通、电信SIM卡制造商列表：
     * 制造商代码        制造商名称
     * A　　　　　　　　　　东方英卡
     * B　　　　　　　　　　布尔公司
     * C　　　　　　　　　　上海柯斯
     * D　　　　　　　　　　欧贝特
     * E　　　　　　　　　　东信和平
     * G　　　　　　　　　　法国金普斯
     * H　　　　　　　　　　北京华虹
     * J　　　　　　　　　　江西捷德
     * S　　　　　　　　　　法国斯伦贝谢
     * T　　　　　　　　　　大唐电信
     * W　　　　　　　　　　北京握奇
     * Y　　　　　　　　　　武汉天喻
     * 附：省份代码表
     * 01：北京 02：天津 03：河北 04：山西 05：内蒙古 06：辽宁
     * 07：吉林 08：黑龙江 09：上海 10：江苏 11：浙江 12：安徽
     * 13：福建 14：江西 15：山东 16：河南 17：湖北  18：湖南
     * 19：广东 20：广西 21：海南 22：四川 23：贵州  24：云南
     * 25：西藏  26：陕西 27：甘肃 28：青海 29：宁夏 30：新 疆 31：重庆
     *
     * @return
     */

    //for cmcc
    private static String[] mnc_cmcc = new String[]{"00", "02", "07"};
    private static String[] mnc_unicom = new String[]{"01", "03"};
    private static String[] sagement_cmcc = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E"};
    private static String[] sagement_unicom = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static String[] manufactor_cmcc = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A"};
    private static String[] manufactor_unicom = new String[]{"A", "B", "C", "D", "E", "G", "H", "J", "S", "T", "W", "Y"};
    private static String[] provincecode = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    public static String randomIMSI() {
        int i = randomInt(1);
        StringBuilder sb = new StringBuilder();
        sb.append("86460");
        if (i == 0) {//cmcc
            sb.append(mnc_cmcc[randomInt(mnc_cmcc.length)]);
            sb.append(randomInt(9));
            sb.append(sagement_cmcc[randomInt(sagement_cmcc.length)]);
            sb.append(provincecode[randomInt(provincecode.length)]);
            sb.append(getRandomString(5, true));
            sb.append(manufactor_cmcc[randomInt(manufactor_cmcc.length)]);
        } else {//unicom
            sb.append(mnc_unicom[randomInt(mnc_unicom.length)]);
            sb.append(randomInt(9));
            sb.append(sagement_unicom[randomInt(sagement_unicom.length)]);
            sb.append(provincecode[randomInt(provincecode.length)]);
            sb.append(getRandomString(5, true));
            sb.append(manufactor_unicom[randomInt(manufactor_unicom.length)]);
        }
        return sb.toString();

    }

    private static int randomInt(int range) {
        return (int) Math.floor(Math.random() * range);
    }

    private static String getRandomString(int length, boolean forcenumber) {
        String str;
        if (forcenumber)
            str = "0123456789";
        else
            str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public void prepareProfile(Context mContext, HashMap<String, DeviceItem> deviceProfile) {
        String defaultvalue;
        String[] deviceModel = RandomDeviceModel(mContext);
        DeviceItem item;
        int sdkversion = 19;
        if (deviceModel[2].startsWith("1.5"))
            sdkversion = 3;
        if (deviceModel[2].startsWith("1.6"))
            sdkversion = 4;
        if (deviceModel[2].startsWith("2.0"))
            sdkversion = 5;
        if (deviceModel[2].startsWith("2.0.1"))
            sdkversion = 6;
        if (deviceModel[2].startsWith("2.1"))
            sdkversion = 7;
        if (deviceModel[2].startsWith("2.2"))
            sdkversion = 8;
        if (deviceModel[2].startsWith("2.3"))
            sdkversion = 9;
        if (deviceModel[2].startsWith("2.3.3"))
            sdkversion = 10;
        if (deviceModel[2].startsWith("3.0"))
            sdkversion = 11;
        if (deviceModel[2].startsWith("4.0"))
            sdkversion = 14;
        if (deviceModel[2].startsWith("4.0.3"))
            sdkversion = 15;
        if (deviceModel[2].startsWith("4.1"))
            sdkversion = 16;
        if (deviceModel[2].startsWith("4.2"))
            sdkversion = 17;
        if (deviceModel[2].startsWith("4.3"))
            sdkversion = 18;
        if (deviceModel[2].startsWith("4.4"))
            sdkversion = 19;
        for (String key : deviceProfile.keySet()) {
            //  defaultvalue = getRandomProp(key);
            item = deviceProfile.get(key);
            Log.e("elvisding", "item=" + item.getName() + "key=" + key);
            if (product_manufacturer[0].equals(key) || product_brand[0].equals(key))
                item.setTargetValue(deviceModel[0]);
            else if (product_model[0].equals(key) || product_name[0].equals(key) || product_device[0].equals(key) || build_product[0].equals(key))
                item.setTargetValue(deviceModel[1]);
            else if (build_release[0].equals(key))
                item.setTargetValue(deviceModel[2]);
            else if (build_fingerprint[0].equals(key) || build_description[0].equals(key) || build_displayid[0].equals(key))
                ; //donothing here
            else if (build_sdk[0].equals(key))
                item.setTargetValue("" + sdkversion);
            else {
                item.setTargetValue((String) getDefacedProp(item.getName()));
            }
        }

        String buildfp = deviceProfile.get(product_brand[0]).getTargetValue() + "/" +
                deviceProfile.get(product_name[0]).getTargetValue() + "/" +
                deviceProfile.get(product_device[0]).getTargetValue() + ":" +
                deviceProfile.get(build_release[0]).getTargetValue() + "/" +
                deviceProfile.get(build_id[0]).getTargetValue() + "/" +
                deviceProfile.get(build_incremental[0]).getTargetValue() + ":" +
                deviceProfile.get(build_type[0]).getTargetValue() + "/" +
                deviceProfile.get(build_tags[0]).getTargetValue();
        deviceProfile.get(build_fingerprint[0]).setTargetValue(buildfp);
        String builddesp = deviceProfile.get(product_name[0]).getTargetValue() + "-" +

                deviceProfile.get(build_type[0]).getTargetValue() + " " +
                deviceProfile.get(build_release[0]).getTargetValue() + " " +
                deviceProfile.get(build_id[0]).getTargetValue() + " " +
                deviceProfile.get(build_incremental[0]).getTargetValue() + " " +
                deviceProfile.get(build_tags[0]).getTargetValue();
        deviceProfile.get(build_description[0]).setTargetValue(builddesp);
        deviceProfile.get(build_displayid[0]).setTargetValue(builddesp);
    }
    private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }

    private static String getTel() {
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String thrid=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+thrid;
    }
    public static Object getDefacedProp(String name) {
        if (name.equals(basebrand[0])) {
            return "msm";
        }
        if (name.equals(bluetoothname[0])) {
            return getRandomString(8,false);
        }
        if (name.equals(product_device[0])) {
            //never go here
        }
        if (name.equals(build_charaters[0])) {
            return "phone";
        }
        if (name.equals(build_host[0])) {
            return getRandomString(6, false) + "-" + getRandomString(2, true);
        }
        if (name.equals(build_id[0])) {
            return getRandomString(7, false).toUpperCase();
        }
        if (name.equals(build_product[0])) {

        }
        if (name.equals(build_tags[0])) {
            int rd = Math.random() > 0.5 ? 1 : 0;
            String[] buildtags = new String[]{"test-keys", "release-keys"};
            return buildtags[rd];
        }
        if (name.equals(build_type[0])) {
            int rd = Math.random() > 0.5 ? 1 : 0;
            String[] buildtype = new String[]{"user", "userdebug"};
            return buildtype[rd];
        }
        // user name
        if (name.equals(build_user[0]))
            return new NameGenerator().getName();

        if (name.equals(build_codename[0])) {
            return "REL";
        }
        if (name.equals(build_incremental[0])) {
            return getRandomString(4, false).toUpperCase() + getRandomString(4, true);
        }

        // Serial number
        if (name.equals(serial[0])) {
            return getRandomProp("SERIAL");
        }

        // MAC addresses
        if (name.equals(wifimac[0])) {
            return getRandomProp("MAC");
        }
        // BT addresses
        if (name.equals(btaddr[0])) {
            return getRandomProp("MAC");
        }
        // IMEI
        if (name.equals(imei[0])) {
            return randomIMEI();
        }

        // Phone
        if (name.equals(phone_number[0])) {
            return getTel();
        }

        // Android ID
        if (name.equals(androiodid[0])) {
            return getRandomProp("ANDROID_ID");
        }

        // imsi
        if (name.equals(imsi[0]))
            return randomIMSI();
        //wifi name
        if (name.equals(wifi_name[0]))
            return new NameGenerator().getName();
        if (name.equals("getIsimImpi"))
            return null;
        if (name.equals("getIsimImpu"))
            return null;

//        if (name.equals("getNetworkCountryIso") || name.equals("CountryIso")) {
//            // ISO country code
//            String value = getSetting(uid, cSettingCountry, "XX");
//            return (cValueRandom.equals(value) ? getRandomProp("ISO3166") : value);
//        }
//        if (name.equals("getNetworkOperator"))
//            // MCC+MNC: test network
//            return getSetting(uid, cSettingMcc, "001") + getSetting(uid, cSettingMnc, "01");
//        if (name.equals("getNetworkOperatorName"))
//            return getSetting(uid, cSettingOperator, cDeface);
//
//        if (name.equals("getSimCountryIso")) {
//            // ISO country code
//            String value = getSetting(uid, cSettingCountry, "XX");
//            return (cValueRandom.equals(value) ? getRandomProp("ISO3166") : value);
//        }
//        if (name.equals("getSimOperator"))
//            // MCC+MNC: test network
//            return getSetting(uid, cSettingMcc, "001") + getSetting(uid, cSettingMnc, "01");
//        if (name.equals("getSimOperatorName"))
//            return getSetting(uid, cSettingOperator, cDeface);
//
//        if (name.equals("getSimSerialNumber") || name.equals("getIccSerialNumber") || name.equals("getIccSerialNumber"))
//            return getSetting(uid, cSettingIccId, null);

        if (name.equals(boad[0])) { // IMSI for a GSM phone
            int rd = Math.random() > 0.5 ? 1 : 0;
            String[] boad = new String[]{"Qualcomm","高通"};
            return boad[rd];
        }

        if (name.equals(carrier[0])) { // IMSI for a GSM phone
            int rd = Math.random() > 0.5 ? 1 : 0;
            String[] carrier = new String[]{"中国移动", "中国联通"};
            return carrier[rd];
        }

        if (name.equals("SSID")) {
            // Default hidden network
            return getRandomProp("SSID");
        }

//        // Google services framework ID
//        if (name.equals("GSF_ID")) {
//            long gsfid = 0xDEFACE;
//            try {
//                String value = getSetting(uid, cSettingGsfId, "DEFACE");
//                if (cValueRandom.equals(value))
//                    value = getRandomProp(name);
//                gsfid = Long.parseLong(value.toLowerCase(), 16);
//            } catch (Throwable ignored) {
//            }
//            return gsfid;
//        }
//
//        // Advertisement ID
//        if (name.equals("AdvertisingId")) {
//            String adid = getSetting(uid, cSettingAdId, "DEFACE00-0000-0000-0000-000000000000");
//            if (cValueRandom.equals(adid))
//                adid = getRandomProp(name);
//            return adid;
//        }

        if (name.equals("InetAddress")) {
            // Set address
            String ip = "";//fixme getSetting(uid, cSettingIP, null);
            if (ip != null)
                try {
                    return InetAddress.getByName(ip);
                } catch (Throwable ignored) {
                }

            // Any address (0.0.0.0)
            try {
                Field unspecified = Inet4Address.class.getDeclaredField("ANY");
                unspecified.setAccessible(true);
                return (InetAddress) unspecified.get(Inet4Address.class);
            } catch (Throwable ex) {
                return null;
            }
        }

//        if (name.equals("IPInt")) {
//            // Set address
//            String ip = getSetting(uid, cSettingIP, null);
//            if (ip != null)
//                try {
//                    InetAddress inet = InetAddress.getByName(ip);
//                    if (inet.getClass().equals(Inet4Address.class)) {
//                        byte[] b = inet.getAddress();
//                        return b[0] + (b[1] << 8) + (b[2] << 16) + (b[3] << 24);
//                    }
//                } catch (Throwable ex) {
//                    Util.bug(null, ex);
//                }
//
//            // Any address (0.0.0.0)
//            return 0;
//        }

//        if (name.equals("Bytes3"))
//            return new byte[] { (byte) 0xDE, (byte) 0xFA, (byte) 0xCE };
//
//        if (name.equals("UA"))
//            return getSetting(uid, cSettingUa,
//                    "Mozilla/5.0 (Linux; U; Android; en-us) AppleWebKit/999+ (KHTML, like Gecko) Safari/999.9");
//
//        // InputDevice
//        if (name.equals("DeviceDescriptor"))
//            return cDeface;
//
//        // getExtraInfo
//        if (name.equals("ExtraInfo"))
//            return cDeface;
//
//        if (name.equals("MCC"))
//            return getSetting(uid, cSettingMcc, "001");
//
//        if (name.equals("MNC"))
//            return getSetting(uid, cSettingMnc, "01");
//
//        if (name.equals("CID"))
//            try {
//                return Integer.parseInt(getSetting(uid, cSettingCid, "0")) & 0xFFFF;
//            } catch (Throwable ignored) {
//                return -1;
//            }
//
//        if (name.equals("LAC"))
//            try {
//                return Integer.parseInt(getSetting(uid, cSettingLac, "0")) & 0xFFFF;
//            } catch (Throwable ignored) {
//                return -1;
//            }
//
//        if (name.equals("USB"))
//            return cDeface;
//
//        if (name.equals("BTName"))
//            return cDeface;
//
//        if (name.equals("CastID"))
//            return cDeface;
//
//        // Fallback
//        Util.log(null, Log.ERROR, "Fallback value name=" + name);
//        Util.logStack(null, Log.ERROR);
        return null;
    }

    public static Location getDefacedLocation(int uid, Location location) {
        // Christmas Island ~ -10.5 / 105.667
//        String sLat = getSetting(uid, cSettingLatitude, "-10.5");
//        String sLon = getSetting(uid, cSettingLongitude, "105.667");
//        String sAlt = getSetting(uid, cSettingAltitude, "686");
//
//        // Backward compatibility
//        if ("".equals(sLat))
//            sLat = "-10.5";
//        if ("".equals(sLon))
//            sLon = "105.667";
//
//        if (cValueRandom.equals(sLat))
//            sLat = getRandomProp("LAT");
//        if (cValueRandom.equals(sLon))
//            sLon = getRandomProp("LON");
//        if (cValueRandom.equals(sAlt))
//            sAlt = getRandomProp("ALT");
//
//        // 1 degree ~ 111111 m
//        // 1 m ~ 0,000009 degrees
//        if (location == null)
//            location = new Location(cDeface);
//        location.setLatitude(Float.parseFloat(sLat) + (Math.random() * 2.0 - 1.0) * location.getAccuracy() * 9e-6);
//        location.setLongitude(Float.parseFloat(sLon) + (Math.random() * 2.0 - 1.0) * location.getAccuracy() * 9e-6);
//        location.setAltitude(Float.parseFloat(sAlt) + (Math.random() * 2.0 - 1.0) * location.getAccuracy());

        return location;
    }

    public static String getRandomProp(String name) {
        Random r = new Random();
        r.setSeed(System.nanoTime());
        if (product_model[0].equals(name)) {

        }
        if (name.equals("SERIAL")) {
            long v = r.nextLong();
            return Long.toHexString(v).toUpperCase();
        }

        if (name.equals("MAC")) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                if (i != 0)
                    sb.append(':');
                int v = r.nextInt(256);
                if (i == 0)
                    v = v & 0xFC; // unicast, globally unique
                sb.append(Integer.toHexString(0x100 | v).substring(1));
            }
            return sb.toString().toUpperCase();
        }

        // IMEI/MEID
        if (name.equals("IMEI")) {
            // http://en.wikipedia.org/wiki/Reporting_Body_Identifier
            String[] rbi = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "30", "33",
                    "35", "44", "45", "49", "50", "51", "52", "53", "54", "86", "91", "98", "99"};
            String imei = rbi[r.nextInt(rbi.length)];
            while (imei.length() < 14)
                imei += Character.forDigit(r.nextInt(10), 10);
            imei += getLuhnDigit(imei);
            return imei;
        }
        if (name.equals("IMSI")) {
            return randomIMSI();
        }
        if (name.equals("PHONE")) {
            String phone = "0";
            for (int i = 1; i < 10; i++)
                phone += Character.forDigit(r.nextInt(10), 10);
            return phone;
        }

        if (name.equals("ANDROID_ID")) {
            long v = r.nextLong();
            return Long.toHexString(v);
        }

        if (name.equals("ISO3166")) {
            String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String country = Character.toString(letters.charAt(r.nextInt(letters.length())))
                    + Character.toString(letters.charAt(r.nextInt(letters.length())));
            return country;
        }

        if (name.equals("GSF_ID")) {
            long v = Math.abs(r.nextLong());
            return Long.toString(v, 16).toUpperCase();
        }

        if (name.equals("AdvertisingId"))
            return UUID.randomUUID().toString().toUpperCase();

        if (name.equals("LAT")) {
            double d = r.nextDouble() * 180 - 90;
            d = Math.rint(d * 1e7) / 1e7;
            return Double.toString(d);
        }

        if (name.equals("LON")) {
            double d = r.nextDouble() * 360 - 180;
            d = Math.rint(d * 1e7) / 1e7;
            return Double.toString(d);
        }

        if (name.equals("ALT")) {
            double d = r.nextDouble() * 2 * 686;
            return Double.toString(d);
        }

        if (name.equals("SubscriberId")) {
            String subscriber = "00101";
            while (subscriber.length() < 15)
                subscriber += Character.forDigit(r.nextInt(10), 10);
            return subscriber;
        }

        if (name.equals("SSID")) {
            String ssid = "";
            while (ssid.length() < 6)
                ssid += (char) (r.nextInt(26) + 'A');

            ssid += Character.forDigit(r.nextInt(10), 10);
            ssid += Character.forDigit(r.nextInt(10), 10);
            return ssid;
        }

        return "";
    }

    private static char getLuhnDigit(String x) {
        // http://en.wikipedia.org/wiki/Luhn_algorithm
        int sum = 0;
        for (int i = 0; i < x.length(); i++) {
            int n = Character.digit(x.charAt(x.length() - 1 - i), 10);
            if (i % 2 == 0) {
                n *= 2;
                if (n > 9)
                    n -= 9; // n = (n % 10) + 1;
            }
            sum += n;
        }
        return Character.forDigit((sum * 9) % 10, 10);
    }

    //get manufactor ,model,androidversion at once from the market
    public String[] RandomDeviceModel(Context mContext) {
        if (copyDB(mContext)) {
            File file = new File(URL, DB_FILE_NAME);
            db = SQLiteDatabase.openOrCreateDatabase(file, null);
            Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null) {
                int temp = new Random(devicecount).nextInt();
                cursor.moveToPosition(new Random().nextInt(devicecount));
                Log.e("elvisding", "manufactor=" + cursor.getString(MANUFACTOR) + "model=" + cursor.getString(MODEL) + "version=" + cursor.getString(ANDROIDVERSION));
                return new String[]{cursor.getString(MANUFACTOR), cursor.getString(MODEL), cursor.getString(ANDROIDVERSION)};

            }
        }
        return null;
    }

    public static boolean copyDB(Context mContext) {

        try {
            if (!(new File(URL)).exists()) {
                InputStream is = mContext.getResources().openRawResource(R.raw.deviceshop);
                FileOutputStream fos = mContext.openFileOutput(DB_FILE_NAME, Context.MODE_WORLD_READABLE);
                byte[] buffer = new byte[1024 * 1024];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }

                fos.close();
                is.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
