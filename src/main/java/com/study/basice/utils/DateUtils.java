package com.study.basice.utils;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by yukun on 20170922.
 */
public class DateUtils {

    public static final String CURRENT_DATE_PATTERN = "yyyyMMdd";

    public static final String CURRENT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String CURRENT_DATE2_PATTERN = "yyyy-MM-dd";

    public static final String CURRENT_DATETIME_PATTERN_ZERO = "yyyy-MM-dd 00:00:00";

    /**
     * String 类型转 Date 类型
     *
     * @param string
     * @return
     */
    public static Date parseDateTime(String string) {
        SimpleDateFormat df = new SimpleDateFormat(CURRENT_DATETIME_PATTERN);
        try {
            return df.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDate(String string) {
        SimpleDateFormat df = new SimpleDateFormat(CURRENT_DATE2_PATTERN);
        try {
            return df.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取当前日期 dd
    public static String getCurDay() {
        Calendar calendar = Calendar.getInstance();
        Integer day = calendar.get(calendar.DAY_OF_MONTH);
        return day.toString();
    }

    /**
     * 获取当前日期
     *
     * @return：yyyyMMdd
     */
    public static String getCurDate() {
        return new SimpleDateFormat(CURRENT_DATE_PATTERN).format(new Date());
    }

    public static final String getCurTime() {
        return new SimpleDateFormat(CURRENT_DATETIME_PATTERN).format(new Date());
    }

    /**
     * 获取当前日期 提前或延后
     *
     * @return:yyyy-MM-dd HH:mm:ss
     */
    public static String getAfterDateTime(int hour) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(CURRENT_DATETIME_PATTERN);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.HOUR_OF_DAY, hour);
            return format.format(cal.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取当前日期 提前或延后 的零点
     *
     * @return:yyyy-MM-dd HH:mm:ss
     */
    public static String getAfterDateTimeZero(int hour) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(CURRENT_DATETIME_PATTERN_ZERO);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.HOUR_OF_DAY, hour);
            return format.format(cal.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    public static String getCurDate2() {
        return new SimpleDateFormat(CURRENT_DATE2_PATTERN).format(new Date());
    }

    /**
     * 计算两个时间差多少小时
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getDateDiffForDay(String startTime, String endTime) {
        SimpleDateFormat sdfDateTime = new SimpleDateFormat(CURRENT_DATE2_PATTERN);
        long day = 0;
        try {
            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
            long nh = 1000 * 60 * 60;// 一小时的毫秒数
            long diff = sdfDateTime.parse(endTime).getTime() - sdfDateTime.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            // hour = diff % nd / nh + day * 24;// 计算差多少小时
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    public static String longToString(long currentTime, String formatType) {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = formatDate(dateOld, formatType); // 把date类型的时间转换为string
        return sDateTime;
    }

    public static String formatDate(Date date, String formatStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        String outDate = formatter.format(date);
        return outDate;
    }

    public static String formatDateTime(Date date) {
        SimpleDateFormat sdfDateTime = new SimpleDateFormat(CURRENT_DATETIME_PATTERN);
        return sdfDateTime.format(date);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdfDateTime = new SimpleDateFormat(CURRENT_DATE2_PATTERN);
        return sdfDateTime.format(date);
    }

    public static int daysBetween(String smdate, String bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(CURRENT_DATE_PATTERN);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return 0;
    }

    public static int daysBetween2(String smdate, String bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(CURRENT_DATE2_PATTERN);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取两个时间内的 时间值
     *
     * @param start
     * @param end
     * @return
     */
    public static List<String> collectLocalDates(String start, String end) {
        LocalDate starts = LocalDate.parse(start);
        LocalDate ends = LocalDate.parse(end);
        return Stream.iterate(starts, localDate -> localDate.plusDays(1))
                .limit(ChronoUnit.DAYS.between(starts, ends) + 1).map(LocalDate::toString).collect(Collectors.toList());
    }

    public static String getCollectLocalDates(String start, String end) {
        List<String> dates = collectLocalDates(start, end);
        StringBuffer resultDate = new StringBuffer();
        if (dates == null || dates.size() == 0) {
            return resultDate.toString();
        }
        for (String date : dates) {
            resultDate.append(date).append(",");
        }
        // resultDate = resultDate.substring(0, resultDate.toString().length() - 1);
        return resultDate.toString().replace("-", "");
    }

    //时间戳 转时间格式
    public static String timeStamp2Date(String seconds) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(CURRENT_DATETIME_PATTERN);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    public static String timeStampchina(String seconds) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH时mm分");
        return sdf.format(parseDateTime(seconds));
    }


    /**
     * 获取昨天日期   yyyymmdd
     *
     * @return
     */
    public static String getYesterday() {
        String yesterday = getAfterDateTime(-24);
        SimpleDateFormat sdf = new SimpleDateFormat(CURRENT_DATE_PATTERN);
        return sdf.format(parseDateTime(yesterday));
    }

    /**
     * 获取本月 第一天
     *
     * @return:yyyy-MM-dd
     */
    public static String getMonthFirstDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(CURRENT_DATE2_PATTERN);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取本月 最后一天
     *
     * @return:yyyy-MM-dd
     */
    public static String getMonthLastDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(CURRENT_DATE2_PATTERN);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return sdf.format(calendar.getTime());
    }


    /**
     * 获取输入年月的 月份最后一日
     *
     * @return:yyyy-MM-dd
     */
    public static String getLastDayofMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastday);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastdayofmonth = sdf.format(cal.getTime());
        return lastdayofmonth;
    }

    /**
     * 当前年的开始时间  yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentYearStartTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat(CURRENT_DATE2_PATTERN);
        return sdf.format(c.getTime());
    }

    /**
     * 获取上个月的第一天
     *
     * @return
     */
    public static String getUpMonthStartDay() {
        Calendar calendar = Calendar.getInstance();
        Date now = null;
        SimpleDateFormat sdf = new SimpleDateFormat(CURRENT_DATE2_PATTERN);
        try {
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            now = sdf.parse(sdf.format(calendar.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf.format(now);
    }

    /**
     * 得到上个月的最后一天
     */
    public static String getUpMonthLastDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(CURRENT_DATE2_PATTERN);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return sdf.format(calendar.getTime());
    }

    /**
     * 根据时间获取当年月
     *
     * @param monthValue
     * @return
     */
    public static int getMonth(String monthValue) {
        monthValue = monthValue.replace("-", "");
        return Integer.parseInt(monthValue.substring(0, 6));
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getMonth() {
        Calendar cale = Calendar.getInstance();
        return cale.get(Calendar.MONTH) + 1;
    }

    public static int getDay() {
        Calendar cale = Calendar.getInstance();
        return cale.get(Calendar.DATE);
    }

    public static int getHour() {
        Calendar caler = Calendar.getInstance();
        return caler.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        Calendar caler = Calendar.getInstance();
        return caler.get(Calendar.MINUTE);
    }

    //获取前一天
    public static String getBeforDay(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(CURRENT_DATE2_PATTERN);
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - 1); //此处修改为+1则是获取后一天
        String lastDay = sdf.format(calendar.getTime());
        return lastDay;
    }

    //获取后一天
    public static String getLastDay(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(CURRENT_DATE2_PATTERN);
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + 1); //此处修改为+1则是获取后一天
        String lastDay = sdf.format(calendar.getTime());
        return lastDay;
    }

    /**
     * String类型时间比较大小
     *
     * @param date1
     * @param date2
     */
    public static int compareToDate(String date1, String date2) {
        DateFormat dateFormat = new SimpleDateFormat(CURRENT_DATETIME_PATTERN);
        try {
            Date d1 = dateFormat.parse(date1);
            Date d2 = dateFormat.parse(date2);
            if (d1.equals(d2)) {
                return 0;
            } else if (d1.before(d2)) {
                return -1;
            } else if (d1.after(d2)) {
                return 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -2;
    }

    /**
     * 获取当前日期前*分钟
     *
     * @return:yyyy-MM-dd HH:mm:ss
     */
    public static String getBeforMinute(int minute) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(CURRENT_DATETIME_PATTERN);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -minute);// *分钟之前的时间
            return format.format(cal.getTime());
        } catch (Exception e) {
            return "";
        }
    }


    public static void main(String[] args) {
        try {
            System.out.println(System.currentTimeMillis());
            System.out.println(System.currentTimeMillis() + 3600 * 1000); // 查询1小时内的数据信息
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

}
