package forever.foreverandroiddemo.utils;

import android.text.TextUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtil {
    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = null;

        if (isInEasternEightZones()) {
            time = toDate(sdate);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(Long.parseLong(sdate + "000"));
            sdate = format.format(date);
            try {
                time = format.parse(sdate);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else
            time = transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08:00"), TimeZone.getDefault());

        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天 ";
        } else if (days > 2 && days < 31) {
            ftime = days + "天前";
        } else if (days >= 31 && days <= 2 * 31) {
            ftime = "一个月前";
        } else if (days > 2 * 31 && days <= 3 * 31) {
            ftime = "2个月前";
        } else if (days > 3 * 31 && days <= 4 * 31) {
            ftime = "3个月前";
        } else {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static Date toDate(String sdate, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        return toDate(sdate, dateFormater.get());
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
    };


    /**
     * Name:getTabView() Describe:获取当前时间的毫秒数
     *
     * @return 当前时间毫秒数
     */
    public static String getMilTime() {

        Calendar cal = Calendar.getInstance();
        String str = cal.getTimeInMillis() + "";
        return str.substring(0, 10);
    }

    /**
     * Name:getCurrentTimeMillis() Describe:获取当前时间戳
     *
     * @return 当前时间戳
     */
    public static String getCurrentTimeMillis() {
        long time = System.currentTimeMillis();
        return time + "";
    }

    /**
     * Name:getTime() Describe:获取当前时间
     *
     * @return 当前时间
     */
    public static String getTime() {

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间

        return formatter.format(curDate);
    }

    /**
     * Name:getTime() Describe:获取当前小时分钟
     *
     * @return 当前时间
     */
    public static String getHourAndMinute() {

        // Calendar cal = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间

        return formatter.format(curDate);
    }

    /**
     * Name:getDay() Describe:获取当前时间
     *
     * @return 当前时间
     */
    public static String getDay() {
        String days;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (month < 10) {
            days = year + "-0" + month;
        } else {

            days = year + "-" + month;
        }
        if (day < 10) {
            days = days + "-0" + day;
        } else {
            days = days + "-" + day;

        }

        return days;
    }

    /**
     *
     */
    public static String getTimeBeijing() {
        // TimeZone zone1 = TimeZone.getTimeZone("GMT+8");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        // sdf.setTimeZone(zone1);
        // return sdf.format(curDate);
        Date date = null;
        try {
            URL url = new URL("http://www.bjtime.cn");
            // 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象

            uc.connect(); // 发出连接

            long ld = uc.getDate(); // 取得网站日期时间

            date = new Date(ld); // 转换为标准时间对象

            // // 分别取得时间中的小时，分钟和秒，并输出
            // System.out.print(date.getHours() + "时" + date.getMinutes() + "分"
            // + date.getSeconds() + "秒");

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    public static String getLocalDatetimeString(String local) {
        String days;
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone(local));
        cal.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
        // String date = cal.get(Calendar.YEAR) + "-"
        // + (cal.get(Calendar.MONTH) + 1) + "-"
        // +(cal.get(Calendar.DAY_OF_MONTH)-1);
        // String time = cal.get(Calendar.HOUR_OF_DAY) + ":"
        // + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (month < 10) {
            days = year + "-0" + month;
        } else {
            days = year + "-" + month;
        }
        if (day < 10) {
            days = days + "-0" + day;
        } else {
            days = days + "-" + day;
        }
        return days;
    }

    /**
     * 获取时区
     *
     * @return
     */
    public static String getTimeZoneNum() {
        TimeZone tz = TimeZone.getDefault();
        String s = "" + tz.getDisplayName(false, TimeZone.SHORT);
        return s;

    }

    /**
     * 获取时区
     *
     * @return
     */
    public static String getTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        String s = "" + tz.getID();
        return s;

    }

    /**
     * 判断用户的设备时区是否为东八区（中国）
     *
     * @return
     */
    public static boolean isInEasternEightZones() {

        TimeZone tz = TimeZone.getDefault();
        String s = tz.getDisplayName(false, TimeZone.SHORT);
        System.out.println(s);
        boolean defaultVaule = true;
        if (s.trim().equals("GMT+08:00"))
            defaultVaule = true;
        else

            defaultVaule = false;
        return defaultVaule;
    }

    /**
     * 根据不同时区，转换时间
     *
     * @return
     */
    public static Date transformTime(Date date, TimeZone oldZone, TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime())
                    - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;
    }

    /**
     * 时间戳转日期格式
     *
     * @param dataFormat
     * @param timeStamp
     * @return
     */
    public static String formatData(String dataFormat, String timeStamp) {
        if (TextUtils.isEmpty(timeStamp)) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        Date date = new Date(Integer.parseInt(timeStamp) * 1000l);
        String result = format.format(date);
        return result;
    }

    /**
     * 一般作为文件图片的名称
     *
     * @return
     */
    public static String getFormatData() {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// 获取当前时间，进一步转化为字符串
        date = new Date();
        return format.format(date);
    }

    /**
     * 判断这个日期是不是今天
     *
     * @param date
     */
    public static boolean isTheDay(String date) {
        String theDay = getTime();
        if (TextUtils.equals(theDay.substring(0, 4), date.substring(0, 4))
                && TextUtils.equals(theDay.substring(5, 7), date.substring(5, 7))
                && TextUtils.equals(theDay.substring(8, 10), date.substring(8, 10))) {
            return true;
        } else {
            return false;
        }
    }


    public static String timeStampToDate(long timeStamp) {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    public static int getYearByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String year = date.substring(0, 4);
        LogUtils.d("year====", year);
        return Integer.parseInt(year);
    }

    public static int getMonthByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String month = date.substring(5, 7);
        LogUtils.d("month====", month);
        return Integer.parseInt(month);
    }

    public static int getDayByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String day = date.substring(8, 10);
        LogUtils.d("day====", day);
        return Integer.parseInt(day);
    }

    public static int getHourByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String hour = date.substring(11, 13);
        return Integer.parseInt(hour);
    }


    //判断两个时间戳是否为同一天
    public static boolean isTwoTimeStampDayEqual(long firstTimeStamp, long secondTimeStamp) {
        if (getYearByTimeStamp(firstTimeStamp) == getYearByTimeStamp(secondTimeStamp) &&
                getMonthByTimeStamp(firstTimeStamp) == getMonthByTimeStamp(secondTimeStamp)
                && getDayByTimeStamp(firstTimeStamp) == getDayByTimeStamp(secondTimeStamp)) {
            return true;
        }
        return false;
    }
}
