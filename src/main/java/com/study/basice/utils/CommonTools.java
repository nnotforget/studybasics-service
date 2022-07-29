package com.study.basice.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 工具类
 *
 * @date 2016-11-06
 */
@Slf4j
public class CommonTools {

    public static int outPageSize = 10;

    public static int inPageSize = 10;

    private static double X_PI = 3.1415926535897932384626;

    //验证姓名的正则
    public static Pattern NAME_PATTERN = Pattern.compile("[\u4E00-\u9FA5]{2,6}(?:·[\u4E00-\u9FA5]{2,6})*");
    public static Pattern NAME_PATTERN2 = Pattern.compile("[^\\x00-\\xff]{1,30}(?:·[^\\x00-\\xff]{1,30})*");

    //验证城市的正则
    public static Pattern CITY_PATTERN = Pattern.compile("[\u4E00-\u9FA5]{2,8}");

    //验证手机号码正则
    public static Pattern PHONE_PATTERN = Pattern.compile("^0{0,1}(13[0-9]|15[0-9]|173|176|177|178|147|145|170|18[0-9])[0-9]{8}$");

    //身份证号正则表达式
    public static String regIdCard = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
    public static Pattern IDCARD_PATTERN = Pattern.compile(regIdCard);
    public static int MT[] = new int[100];

    public static ScriptEngineManager manager = new ScriptEngineManager();


    static {
        for (int i = 11; i <= 15; i++) {
            MT[i] = 1;
        }
        for (int i = 21; i <= 23; i++) {
            MT[i] = 1;
        }
        for (int i = 31; i <= 37; i++) {
            MT[i] = 1;
        }
        for (int i = 41; i <= 46; i++) {
            MT[i] = 1;
        }
        for (int i = 50; i <= 54; i++) {
            MT[i] = 1;
        }
        for (int i = 61; i <= 65; i++) {
            MT[i] = 1;
        }
        MT[71] = 1;
        MT[81] = 1;
        MT[82] = 1;
        MT[91] = 1;
    }

    /***
     * 将字符串转为数字类型
     * @param string        字符串
     * @param defaultValue    默认值
     * @return 返回结果
     */
    public static int stringToInt(String string, int defaultValue) {
        if (string == null || "".equals(string)) {
            return defaultValue;
        }
        try {
            return new BigDecimal(string).intValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /***
     * 将字符串转为数字类型
     * @param string        字符串
     * @param defaultValue    默认值
     * @return 返回结果
     */
    public static Integer stringToIntger(String string, Integer defaultValue) {
        if (string == null || "".equals(string)) {
            return defaultValue;
        }
        try {
            return new BigDecimal(string).intValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /***
     * 将字符串转为数字类型
     * @param string        字符串
     * @param defaultValue    默认值
     * @return 返回结果
     */
    public static long stringToLong(String string, long defaultValue) {
        if (string == null || "".equals(string)) {
            return defaultValue;
        }
        try {
            return new BigDecimal(string).longValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /***
     * 将字符串转为浮点类型
     * @param string        字符串
     * @param defaultValue    默认值
     * @return 返回结果
     */
    public static double stringToDouble(String string, double defaultValue) {
        if (string == null || "".equals(string)) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /***
     * 替换换行符
     * @param content    源字符串
     * @return 返回结果
     */
    public static String repCRLF(String content) {
        return content.replaceAll("(\r\n|\r|\n|\n\r)", "");
    }

    /**
     * 取得当前类所在的目录
     *
     * @return 返回文件路径
     */
    public static String getClassPath() {
        // 当前类的路径
        String classPath = CommonTools.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        System.out.println("当前类的路径：" + classPath);
        try {
            classPath = URLDecoder.decode(classPath, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        File jarFile = new File(classPath);
        System.out.println("JAR 名:" + jarFile.getName());
        File parent = jarFile.getParentFile();
        if (parent != null) {
            classPath = parent.getAbsolutePath();
        }
        return classPath;
    }

    //根据指定目录及后缀名，取该类型的所有文件名，解析后，返回字符数组集合
    public synchronized static List<String[]> queryFileList(File fileDir, String suffix) {
        List<String[]> resultList = new ArrayList<String[]>();
        //取得该目录下所有文件
        File[] fileArray = fileDir.listFiles();
        for (int i = 0; i < fileArray.length; i++) {
            File file = fileArray[i];
            String fileName[] = file.getName().split("\\.");
            //如果文件存在扩展名，且与 suffix 扩展名相名，则解析该文件名
            if (fileName.length > 1 && fileName[fileName.length - 1].equals(suffix)) {
                resultList.add(fileName[0].split("\\_"));
            }
        }
        return resultList;
    }

    //根据指定目录及后缀名，取该类型的所有文件名
    public synchronized static List<String> queryFileNameList(File fileDir, String suffix) {
        List<String> resultList = new ArrayList<String>();
        //取得该目录下所有文件
        File[] fileArray = fileDir.listFiles();
        for (int i = 0; i < fileArray.length; i++) {
            File file = fileArray[i];
            String fileName[] = file.getName().split("\\.");
            //如果文件存在扩展名，且与 suffix 扩展名相名，则收录该文件名
            if (fileName.length > 1 && fileName[fileName.length - 1].equals(suffix)) {
                resultList.add(file.getName());
            }
        }
        return resultList;
    }


    /***
     * 将集合转为字符串
     * @param objectList
     * @return
     */
    public static String listToString(List<?> objectList) {
        if (objectList == null || objectList.size() == 0) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        for (Object object : objectList) {
            if (object instanceof Integer) {
                result.append(object.toString() + ",");
            } else if (object instanceof Long) {
                result.append(object.toString() + ",");
            } else {
                result.append("'" + object.toString() + "',");
            }
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    /***
     * 去除字符串最后一位
     * @param stringBuffer
     * @return
     */
    public static String deleteLast(StringBuffer stringBuffer) {
        if (stringBuffer == null || stringBuffer.length() == 0) {
            return "";
        }
        stringBuffer = stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }

    /***
     * 将数组转为字符串
     * @param array 数组
     * @return
     * @return 返回结果
     */
    public static String arrayToString(int[] array) {
        if (array == null || array.length == 0) {
            return "";
        }

        StringBuffer result = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            result.append(array[i] + ",");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    /***
     * 将数组转为字符串
     * @param array 数组
     * @return
     * @return 返回结果
     */
    public static String arrayToString(String[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            result.append(array[i] + ",");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    /***
     * 将集合转为字符串
     * @param list 数组
     * @return
     * @return 返回结果
     */
    public static String arrayToString(List<String> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i) + ",");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    /***
     * 将集合转为字符串
     * @param set 数组
     * @return
     * @return 返回结果
     */
    public static String arrayToString(Set<String> set) {
        if (set == null || set.size() == 0) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        for (String string : set) {
            result.append(string + ",");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    /***
     * 格式化数字,保留两位小数
     * @param allPrice  原数据
     * @return 返回格式化后结果
     */
    public static String formatNumber(double allPrice) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(allPrice);
    }

    /***
     * 格式化数字,保留两位小数
     * @param allPrice        原数据
     * @return 返回格式化后结果
     */
    public static Double formatDouble(Double allPrice) {
        if (allPrice == null) {
            return null;
        }
        BigDecimal result = new BigDecimal(allPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
        return result.doubleValue();
    }

    /***
     * 将对象转为double
     * @param object        对象
     * @return 返回结果
     */
    public static double objectToDouble(Object object) {
        try {
            if (object == null) {
                return 0d;
            }
            return Double.parseDouble(object.toString());
        } catch (Exception e) {
            return 0d;
        }
    }

    /***
     * 将对象转为字符串
     * @param object        对象
     * @return 返回结果
     */
    public static String objectToString(Object object) {
        if (object == null) {
            return null;
        }
        return object.toString();
    }

    /***
     * 将对象转为整型数字
     * @param object        对象
     * @return 返回结果
     */
    public static int objectToInt(Object object) {
        try {
            if (object == null) {
                return 0;
            }
            return Integer.parseInt(object.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    /***
     * 将字符串进行 urlCode 转化
     * @param source        源字符串
     * @return 返回转化结果
     */
    public static String urlEncode(String source) {
        String result = null;
        try {
            result = URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /****
     * 将字符串进行 urlCode 还原
     * @param source        源字符串
     * @return 返回还原结果
     */
    public static String urlDecode(String source) {
        String result = null;
        try {
            result = URLDecoder.decode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 默认值
     * @param srcString
     * @param defaultString
     * @return
     */
    public static String stringToString(String srcString, String defaultString) {
        if (StringUtils.isEmpty(srcString)) {
            return defaultString;
        }
        return srcString;
    }

    /***
     * 默认值
     * @param srcString
     * @param
     * @return
     */
    public static String stringToString(String srcString) {
        if (StringUtils.isEmpty(srcString)) {
            return "";
        }
        return srcString;
    }

    /***
     * 校验身份证号码是否正确
     * @param idCard        身份证号
     * @return 返回校验结果
     */
    public static boolean validateIdCard(String idCard) {
        try {
            if (idCard == null || idCard.length() == 0) {
                return false;
            }
            int index = Integer.parseInt(idCard.substring(0, 2));
            //地域信息
            if (MT[index] == 0) {
                return false;
            }
            Pattern p = IDCARD_PATTERN;
            if (p.matcher(idCard).matches()) {
                if (idCard.length() == 18) {
                    int[] idCardWi = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7,
                            9, 10, 5, 8, 4, 2};
                    int[] idCardY = new int[]{1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2};
                    int idCardWiSum = 0;
                    for (int i = 0; i < 17; i++) {
                        idCardWiSum += Integer.parseInt(idCard.substring(i, i + 1))
                                * idCardWi[i];
                    }
                    int idCardMod = idCardWiSum % 11;
                    char idCardLast = idCard.charAt(17);
                    if (idCardMod == 2) {
                        if (idCardLast == 'X' || idCardLast == 'x') {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        if (Integer.parseInt(idCardLast + "") == idCardY[idCardMod]) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                return false;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 验证手机号码
    public static boolean validateMobile(String mobile) {
        if (mobile == null || "".equals(mobile)) {
            return false;
        }
        if (mobile.length() != 11) {
            return false;
        }
        if (!PHONE_PATTERN.matcher(mobile).matches()) {
            return false;
        }
        return true;
    }

    // 验证姓名
    public static boolean validateName(String name) {
        if (name == null || "".equals(name.trim())) {
            return false;
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            if (!NAME_PATTERN2.matcher(name).matches()) {
                return false;
            }
        }
        return true;
    }

    /***
     * 读取身份证号码
     * @param idCardNo            身份证号码
     * @return 返回数组[0:省;1:市;2:县;3:生日;4:年份;5:性别]
     */
    public static String[] readIdCardNo(String idCardNo) {
        String result[] = new String[6];
        result[0] = idCardNo.substring(0, 2);
        result[1] = idCardNo.substring(0, 4);
        result[2] = idCardNo.substring(0, 6);
        result[3] = idCardNo.substring(6, 10);
        result[4] = idCardNo.substring(6, 14);
        result[5] = idCardNo.substring(16, 17);
        return result;
    }

    /***
     * 计算年龄
     * @param birthYear 出生年份
     * @return 返回当前年龄
     */
    public static Integer countAge(int birthYear) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int nowYear = stringToInt(sdf.format(new Date()), 0);
        return nowYear - birthYear;
    }

    /**
     * 获取请求 路径信息
     *
     * @param request
     * @return
     */
    public static String getAllPath(HttpServletRequest request) {
        String param = request.getQueryString();
        if (StringUtils.isNotEmpty(param)) {
            param = "?" + param;
        }
        if (param == null) {
            param = "";
        }
        int port = request.getServerPort();
        if (port == 80) {
            return request.getScheme() + "://" + request.getServerName() + request.getRequestURI() + param;
        }
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI() + param;
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
     * 即谷歌、高德 转 百度, longitude
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @returns {*[]}		39.955303,116.54122 转 39.9624972185,116.5535743459
     */
    public static Map<String, String> gcj02tobd09(String latitude, String longitude) {
        try {
            //纬度
            double lat = Double.parseDouble(latitude);
            //经度
            double lng = Double.parseDouble(longitude);
            double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * X_PI);
            double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * X_PI);
            double bd_lng = z * Math.cos(theta) + 0.0065;
            double bd_lat = z * Math.sin(theta) + 0.006;
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("lat", bd_lat + "");
            resultMap.put("lng", bd_lng + "");
            return resultMap;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 取得身份证号中的市级编号
     *
     * @param idCardNo 身份证号
     * @return
     */
    public static String getCityCode(String idCardNo) {
        return idCardNo.substring(0, 4);
    }

    /**
     * 取得身份证号中的省级编号
     *
     * @param idCardNo 身份证号
     * @return
     */
    public static String getProvinceCode(String idCardNo) {
        return idCardNo.substring(0, 2);
    }

    /**
     * 取得身份证号中的县级编号
     *
     * @param idCardNo 身份证号
     * @return
     */
    public static String getCountyCode(String idCardNo) {
        return idCardNo.substring(0, 6);
    }

    /**
     * 取得身份证号中的性别
     *
     * @param idCardNo 身份证号
     * @return
     */
    public static String getSex(String idCardNo) {
        String sexCode = idCardNo.substring(16, 17);
        int result = (stringToInt(sexCode, 0) % 2);
        return (result == 0 ? "女" : "男");
    }

    /****
     * @desc 将字符串以 ,号分割,转为 List 集合
     * @param phone    (1,3,4,5)
     * @return 返回结果
     */
    public static ArrayList<String> stringToList(String phone) {
        ArrayList<String> resultList = new ArrayList<String>();
        try {
            if (StringUtils.isEmpty(phone)) {
                return resultList;
            }
            String[] array = phone.split(",", -1);
            for (int i = 0; i < array.length; i++) {
                if (i == array.length - 1 && StringUtils.isEmpty(array[i])) {
                    continue;
                }
                resultList.add(array[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /***
     * @desc 拼接字符串(以空格分割)
     * @param stringList
     * @return
     */
    public static String connectString(String... stringList) {
        StringBuffer result = new StringBuffer();
        for (String string : stringList) {
            if (StringUtils.isNotEmpty(string)) {
                result.append(string + " ");
            }
        }
        return result.toString();
    }

    /**
     * long 转 int
     *
     * @param src
     * @return
     */
    public static Integer longToInt(Long src) {
        if (src == null) {
            return null;
        }
        return src.intValue();
    }

    /****
     * @desc 提取数字(23M, 返回 23 ; 23.7M, 返回 23.7 ; 23, 返回 23)
     *
     * @date 20210804
     * @param src
     * @return 返回提取到的数字
     */
    public static BigDecimal pickUpNumber(String src) {
        if (StringUtils.isEmpty(src)) {
            return new BigDecimal("0");
        }
        // 数字多个, 小数点, 数字, 字符 匹配(23.6GJ)
        Pattern pattern = Pattern.compile("(-?\\d+\\.\\d+)(\\D*)");
        Matcher matcher = pattern.matcher(src);
        if (matcher.find()) {
            return new BigDecimal(matcher.group(1));
        }

        // 数字多个, 小数点, 数字, 字符 匹配(23M)
        pattern = Pattern.compile("(-?\\d+)(\\D*)");
        matcher = pattern.matcher(src);
        if (matcher.find()) {
            return new BigDecimal(matcher.group(1));
        }
        return new BigDecimal("0");
    }

    /****
     * @desc 提取数字(23M, 返回 23 ; 23.7M, 返回 23.7 ; 23, 返回 23)
     *
     * @date 20210804
     * @param src
     * @return 返回提取到的数字
     */
    public static BigDecimal pickUpNorm(String src) {
        if (StringUtils.isEmpty(src)) {
            return new BigDecimal("0");
        }
        // 数字多个, 小数点, 数字, 字符 匹配(23.6GJ)
        Pattern pattern = Pattern.compile("^N(\\d+$)");
        Matcher matcher = pattern.matcher(src);
        if (matcher.find()) {
            return new BigDecimal(matcher.group(1));
        }
        return new BigDecimal("0");
    }

    public static String pickUpNumber(String src, String defaultValue) {
        if (StringUtils.isEmpty(src)) {
            return defaultValue;
        }
        // 数字多个, 小数点, 数字, 字符 匹配(23.6GJ)
        Pattern pattern = Pattern.compile("(-?\\d+\\.\\d+)(\\D*)");
        Matcher matcher = pattern.matcher(src);
        while (matcher.find()) {
            return new BigDecimal(matcher.group(1)).toString();
        }

        // 数字多个, 小数点, 数字, 字符 匹配(23M)
        pattern = Pattern.compile("(-?\\d+)(\\D*)");
        matcher = pattern.matcher(src);
        while (matcher.find()) {
            return new BigDecimal(matcher.group(1)).toString();
        }
        return defaultValue;
    }

    public static int varsionToInt(String version, int i) {
        try {
            if (StringUtils.isEmpty(version)) {
                return 0;
            }
            version = version.replaceAll("\\.", "");
            version = version.substring(0, 3);
            return Integer.parseInt(version);
        } catch (Exception e) {
            return 0;
        }
    }

    /***
     * @desc 执行 JS 运算
     * @param js        JS 运算((a > 1 && a <= 4))
     * @return 返回运算结果
     */
    public static Object exeJSOper(String js, Object aValue) {
        Object resultObject = null;
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.put("a", aValue);
        try {
            resultObject = engine.eval(js);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return resultObject;
    }

    /***
     * @desc 列表转为数组
     * @date 20210917
     * @param stationIdList
     * @return
     */
    public static Long[] listToArray(List<Long> stationIdList) {
        Long[] result = new Long[stationIdList.size()];
        for (int i = 0; i < stationIdList.size(); i++) {
            result[i] = stationIdList.get(i);
        }
        return result;
    }

    /****
     * @desc 去除特殊字符(# @ $ % ^ & * ())
     * @param param    参数名
     * @return
     */
    public static String replaceSpecialChar(String param) {
        if (StringUtils.isEmpty(param)) {
            return param;
        }
        // 替换掉所有特殊字符
        String result = param.replaceAll("\\#", "a")
                .replaceAll("\\@", "b")
                .replaceAll("\\$", "c")
                .replaceAll("\\%", "d")
                .replaceAll("\\^", "e")
                .replaceAll("\\&", "f");
        return result;
    }

    /**
     * 是否包含 特殊字符
     *
     * @param str
     * @return
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /****
     * @desc 是否为数字
     * @date 20211109
     * @param num    字符
     * @return 是否为数据(返回 true, false)
     */
    public static boolean isNum(String num) {
        try {
            new BigDecimal(num);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /***
     * @desc 字符串转 BigDecimal
     * @param value
     * @return
     */
    public static BigDecimal stringToDecimal(String value) {
        try {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            return new BigDecimal(value).setScale(4, BigDecimal.ROUND_DOWN);
        } catch (Exception e) {
            return null;
        }
    }

    public static String longToString(Long company) {
        if (company == null) {
            return null;
        }
        return company.toString();
    }


    /***
     * @desc int 集合转为 Long 集合
     * @date 20220117
     * @param stationIdListInt        int 集合
     * @return long 集合
     */
    public static List<Long> intListToLongList(List<Integer> stationIdListInt) {
        List<Long> resultList = new ArrayList<Long>();
        for (Integer integer : stationIdListInt) {
            if (integer != null) {
                resultList.add(Long.valueOf(integer));
            }
        }
        return resultList;
    }

    /***
     * @desc 对象转为长整型
     * @param object    对象
     * @return 返回结果
     */
    public static Long objectToLong(String object) {
        try {
            if (object == null) {
                return 0L;
            }
            return Long.parseLong(object.toString());
        } catch (Exception e) {
            return 0L;
        }
    }


    public static String objectToString(Object object, String string) {
        if (object == null) {
            return string;
        }
        if ("null".equals(object.toString())) {
            return string;
        }
        return object.toString();
    }


    public static String existNull(List<String> stringList) {
        for (String string : stringList) {
            if (StringUtils.isEmpty(string)) {
                return "exist";
            }
        }
        return null;
    }

    public static String existCompareValue(List<String> stringList, String compareValue) {
        for (String string : stringList) {
            if (compareValue.equals(string)) {
                return "exist";
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String ss = "@#$%";
        System.out.println("###:" + isSpecialChar(ss));
    }


}