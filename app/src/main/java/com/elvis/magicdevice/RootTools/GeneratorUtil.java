package com.elvis.magicdevice.RootTools;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by elvis on 15/5/16.
 */
public class GeneratorUtil {

    public static String imei_gen() {
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

    public static String generatorIMSI() {
        int i = randomInt(1);
        StringBuilder sb=new StringBuilder();
        sb.append("86460");
        if (i == 0) {//cmcc
            sb.append(mnc_cmcc[randomInt(mnc_cmcc.length)]);
            sb.append(randomInt(9));
            sb.append(sagement_cmcc[randomInt(sagement_cmcc.length)]);
            sb.append(provincecode[randomInt(provincecode.length)]);
            sb.append(getRandomString(5));
            sb.append(manufactor_cmcc[randomInt(manufactor_cmcc.length)]);
        }else{
            sb.append(mnc_unicom[randomInt(mnc_unicom.length)]);
            sb.append(randomInt(9));
            sb.append(sagement_unicom[randomInt(sagement_unicom.length)]);
            sb.append(provincecode[randomInt(provincecode.length)]);
            sb.append(getRandomString(5));
            sb.append(manufactor_unicom[randomInt(manufactor_unicom.length)]);
        }
        return sb.toString();

    }

    private static int randomInt(int range) {
        return (int) Math.floor(Math.random() * range);
    }
    private static String getRandomString(int length){
        //String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String str="0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }



}
