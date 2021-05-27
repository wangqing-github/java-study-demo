package wq.study.demo.utils.date;

import org.apache.commons.lang3.math.NumberUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.SUNDAY;


public class DateUtil {

    public static final int DATE = 1;
    public static final int TIME = 2;
    private static final int DATE_TIME = 3;
    public static final String DEFAULT_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间截转转成日期
     *
     */
    public static Date millisToDate(Long millis) {
        return new Date(millis);
    }

    /**
     * 当前月
     *
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 当前周
     *
     */
    public static int getWeek() {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 当前天
     *
     */
    public static String getDay() {
        return toDate(new Date());
    }

    /**
     * 当前小时
     *
     */
    public static int getHour() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 当前分钟
     *
     */
    public static int getMin() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MINUTE);
    }

    /**
     * 小时榜单剩余有效时间
     *
     */
    public static long getFromLastHourSec(Date nowdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowdate);
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 1);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date NewDate = cal.getTime();
        return NewDate.getTime() - nowdate.getTime();

    }


    /**
     * 判断两个时间是否相同月份
     *
     */
    public static boolean isSameMonth(String start, String end) throws ParseException {
        boolean b = false;

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(start));
        c2.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(end));
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {
            b = true;
        }

        return b;
    }

    /**
     * 校验是否超过5分钟需要锁榜
     *
     */
    public static long getCheckHourIsLock(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, 5);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime() - date.getTime();
    }

    /**
     * 当前秒数
     *
     */
    public static int getSecond() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.SECOND);
    }

    public static String toDate() {
        return toDate(new Date());
    }

    /**
     * 简要说明：评论发表时间格式化 详细说明：
     * <p>
     * 发表时间，1小时以内用分钟显示，1天内用小时计，10天内以天计，10天以上用日期
     * <p>
     * 参数说明：
     *
     * @return 创建人： hyg 创建时间： 2012-6-11 下午02:05:30 Copyright (c)
     * 泡椒思志信息技术有限公司-版权所有
     */
    public static String getTimeLengthMinute(Date date) {
        return getTimeLengthMinute(date, "yyyy-MM-dd");
    }

    private static String getTimeLengthMinute(Date date, String fmt) {
        if (date == null) {
            return "";
        }
        Long nowtime = System.currentTimeMillis();
        Long tmptime = date.getTime();
        long timelen = nowtime - tmptime;
        // 小于1分钟
        if (timelen <= 60000) {
            return timelen / 6000 + "秒前";
        }
        // 小于1小时
        else if (timelen <= 3600000) {
            return timelen / 60000 + "分鐘前";
        }
        // 少于1天
        else if (timelen <= 86400000) {
            return timelen / 3600000 + "小時前";
        }
        // 少于10天
        else if (timelen <= 2592000000L) {
            return timelen / 86400000 + "天前";
        }
        // 少于60天
        else if (timelen <= 5184000000L) {
            return "一個月前";// timelen / 5184000000l + "天前";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(fmt);
            return formatter.format(date);
        }
    }

    /**
     * 获取所有表时间
     */
    public static Set<String> getTable(String startdate, String enddate) throws ParseException {
        Set<String> tables = new LinkedHashSet<>();

        if (startdate == null || enddate == null) {
            startdate = DateUtil.toDate();
            enddate = DateUtil.toDate();
        }

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(startdate));
        c2.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(enddate));

        int y1 = c1.get(Calendar.YEAR);
        int y2 = c2.get(Calendar.YEAR);
        int m1 = c1.get(Calendar.MONTH) + 1;
        int m2 = c2.get(Calendar.MONTH) + 1;

        while (!(y1 == y2 && m1 == m2)) {
            if (m1 < 10) {
                String table = y1 + "0" + m1;
                tables.add(table);
            } else {
                String table = y1 + "" + m1;
                tables.add(table);
            }
            m1++;
            if (m1 > 12) {
                m1 = 1;
                y1++;
            }
        }
        if (m2 < 10) {
            String table = y2 + "0" + m2;
            tables.add(table);
        } else {
            String table = y2 + "" + m2;
            tables.add(table);
        }

        return tables;
    }

    public static Date todayDate() {
        return parse(nowDate(), "yyyy-MM-dd");
    }



    public static String ToString(String pattern) {
        return toString(new Date(), pattern);
    }

    public static String day(String pattern, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, num);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(calendar.getTime());
    }

    public static Date day(int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, num);
        return calendar.getTime();
    }

    public static Date day(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, num);
        return calendar.getTime();
    }

    public static String dayOnMonth(String pattern, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(calendar.getTime());
    }

    public static Date dayOnMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static String day(Date date, String pattern, int num) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_YEAR, num);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(calendar.getTime());
    }

    public static String day(String date, String pattern, int num) {

        Date dayDate = parse(date, "yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayDate);

        calendar.add(Calendar.DAY_OF_YEAR, num);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(calendar.getTime());
    }

    public static String month(String date, String pattern, int num) {

        Date dayDate = parse(date, "yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayDate);
        calendar.add(Calendar.MONTH, num);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(calendar.getTime());
    }

    public static String prevHourStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(calendar.getTime());
    }

    public static String prevHourEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -1);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(calendar.getTime());
    }

    public static String nowDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static String nowDateTime() {
        return nowDateTime(DEFAULT_FORMAT_PATTERN);
    }

    public static String nowDateTime(String pattern) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static Date parse(String dateString) {
        DateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_PATTERN);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date timestampToDate(String timestamp, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        long time = Long.parseLong(timestamp);
        try {
            date = sdf.parse(sdf.format(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parse(String dateString, String pattern) {
        DateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String toString(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static String toString(Date date) {
        String pattern = DEFAULT_FORMAT_PATTERN;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static String Yesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        int d = cal.get(Calendar.DAY_OF_MONTH);
        String time = d + "日";
        return time;
    }

    public static String toYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return toDate(cal.getTime());
    }

    public static String toOneDay(int day) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        return toDate(cal.getTime());
    }

    public static String toDate(Date date, int day) {
        return toDate(day(date, day));
    }

    public static String toDate(Date date) {
        return toString(date, "yyyy-MM-dd");
    }

    public static String toDateTime(Date date) {
        return toString(date, DEFAULT_FORMAT_PATTERN);
    }

    public static String toDateTime() {
        return toString(new Date());
    }

    public static String toTime(Date date) {
        return toString(date, "HH:mm:ss");
    }

    public static String toTime(Date date, String pattern) {
        return toString(date, pattern);
    }

    public static Date isoStringToDate(String text, int type) {

        Calendar c = Calendar.getInstance();

        if (type != DATE_TIME)
            c.setTime(new Date(0));

        if ((type & DATE) != 0) {
            c.set(Calendar.YEAR, Integer.parseInt(text.substring(0, 4)));
            c.set(Calendar.MONTH, Integer.parseInt(text.substring(5, 7)) - 1 + Calendar.JANUARY);
            c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(text.substring(8, 10)));

            if (type == DATE_TIME)
                text = text.substring(11);
        }

        if ((type & TIME) == 0)
            return c.getTime();

        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(text.substring(0, 2))); // -11
        c.set(Calendar.MINUTE, Integer.parseInt(text.substring(3, 5)));
        c.set(Calendar.SECOND, Integer.parseInt(text.substring(6, 8)));

        int pos = 8;
        if (pos < text.length() && text.charAt(pos) == '.') {
            int ms = 0;
            int f = 100;
            while (true) {
                char d = text.charAt(++pos);
                if (d < '0' || d > '9')
                    break;
                ms += (d - '0') * f;
                f /= 10;
            }
            c.set(Calendar.MILLISECOND, ms);
        } else
            c.set(Calendar.MILLISECOND, 0);

        if (pos < text.length()) {

            if (text.charAt(pos) == '+' || text.charAt(pos) == '-')

                c.setTimeZone(TimeZone.getTimeZone("GMT" + text.substring(pos)));

                /*
                 * return new Date (c.getTime ().getTime () + (Integer.parseInt
                 * (text.substring (pos+1, pos+3)) * 60 + Integer.parseInt
                 * (text.substring (pos+4, pos+6))) (text.charAt (pos) == '-' ?
                 * -60000 : 60000));
                 */

            else if (text.charAt(pos) == 'Z')
                c.setTimeZone(TimeZone.getTimeZone("GMT"));
            else
                throw new RuntimeException("illegal time format!");
        }

        return c.getTime();
    }

    public static Long getDaysBetween(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
    }

    public static Long getDaysBetweenWhenFour(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.add(Calendar.HOUR_OF_DAY, -4);
        Date newDay = toCalendar.getTime();
        toCalendar.setTime(newDay);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        long cutTime = (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
        if (cutTime < 0) {
            cutTime = 0L;
        }
        return cutTime;
    }

    private static String getWeekFirstDay(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            cal.add(Calendar.WEEK_OF_YEAR, -1);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        return sdf.format(cal.getTime());
    }

    private static String getWeekFirstDay(Date date, String pattern) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            cal.add(Calendar.WEEK_OF_YEAR, -1);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        DateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(cal.getTime());
    }

    public static Date getFourHourWeekFirstDay() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurDate());
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            cal.add(Calendar.WEEK_OF_YEAR, -1);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 4);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date getStratHourWeekFirstDay(Integer day, Integer hour, Integer minute) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurDate());
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            cal.add(Calendar.WEEK_OF_YEAR, -1);
        }
        cal.set(Calendar.DAY_OF_WEEK, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }


    public static String getWeekFirstDay() {

        return getWeekFirstDay(Calendar.getInstance().getTime());
    }

    public static int getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static int getDayOfWeekMinusFourHour() {
        Calendar cal = Calendar.getInstance();
        long l = getCurDate().getTime() - 60 * 60 * 1000 * 4;
        cal.setTimeInMillis(l);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static String getMonthFirstDay() {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        return sdf.format(cal.getTime());
    }

    public static String getMonthFirstDayDouble() {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    private static String getMonthFirstDay(String parent) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);

        DateFormat sdf = new SimpleDateFormat(parent);
        return sdf.format(cal.getTime());
    }

    public static String getMonthFirstDay(String date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            calendar.setTime(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getPreMonthLastDay(String parent) {
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 0);
        DateFormat sdf = new SimpleDateFormat(parent);
        return sdf.format(cale.getTime());
    }

    public static String getPreMonthFirstDay(String parent) {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, -1);
        cale.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        DateFormat sdf = new SimpleDateFormat(parent);
        return sdf.format(cale.getTime());
    }

    public static boolean equals(Date d1, Date d2) {
        if (d1 == null || d2 == null)
            return false;
        return toDate(d1).equals(toDate(d2));
    }

    // 获得本星期（中国的星期 例如：201801 代表2018第一个星期）
    public static String getWeekStr() {
        return getWeekStr(0);
    }

    private static String getWeekStr(int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, week);
        return getWeekStr(calendar.getTime());
    }

    public static String getWeekStr(String date) {
        return getWeekStr(parse(date, "yyyy-MM-dd"));
    }

    public static String getWeekStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int y = calendar.get(Calendar.YEAR);
        int w = calendar.get(Calendar.WEEK_OF_YEAR);// 一年的第几周
        int d = calendar.get(Calendar.DAY_OF_WEEK);// 一年的第几周

        int m = calendar.get(Calendar.MONTH) + 1;

        if (w == 1 && d == 1) {
            calendar.add(Calendar.DAY_OF_WEEK, -2);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            y = calendar.get(Calendar.YEAR);
            w = calendar.get(Calendar.WEEK_OF_YEAR);
        } else if (d == 1) {// 普通的星期天
            w -= 1;
        }

        if (m == 12 && w == 1) {// 最后一个月的星期天
            y += 1;
        }

        String year = y + "";
        String ws = w + "";
        if (ws.length() == 1) {
            ws = "0" + ws;
        }
        return year + ws;
    }

    /***
     * HAS BUG
     *
     */
    public static String getWeekStrOld(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int w = calendar.get(Calendar.WEEK_OF_YEAR);
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1 && w == 1) {// 每年第一周的星期天，归类在去年最后一周
            calendar.add(Calendar.DATE, -1);
            w = calendar.get(Calendar.WEEK_OF_YEAR);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {// 普通的星期天,归类在上周
            w -= 1;
        }
        int m = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        if (m == 12 && w == 1) {// 最后一个月的星期天
            year += 1;
        }
        String y = year + "";
        String ws = w + "";
        if (ws.length() == 1) {
            ws = "0" + ws;
        }
        return y + ws;
    }

    public static String[] getWeeksStr(String start, String end) {
        return getWeeksStr(parse(start, "yyyy-MM-dd"), parse(end, "yyyy-MM-dd"));
    }

    private static String[] getWeeksStr(Date start, Date end) {
        Set<String> weekSet = new HashSet<String>();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);

        while (endCalendar.after(startCalendar)) {
            weekSet.add(getWeekStr(startCalendar.getTime()));
            startCalendar.add(Calendar.DATE, 7);
        }
        weekSet.add(getWeekStr(endCalendar.getTime()));
        return weekSet.toArray(new String[]{});
    }

    public static String[] getMonthWeeks(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date start = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = calendar.getTime();
        return getWeeksStr(start, end);
    }

    public static long getPeriod(String date, int num) {
        Date time = DateUtil.parse(date, "yyyy-MM-dd");
        Date finaltime = day(time, num);
        return Long.parseLong(DateUtil.toString(finaltime, "yyyyMMddHHmm"));
    }

    public static long getYMD() {
        return Long.parseLong(DateUtil.toString(new Date(), "yyyyMMdd"));
    }

    public static Long getYMDHM() {
        return Long.parseLong(DateUtil.toString(new Date(), "yyyyMMddHHmm"));
    }

    public static Long getYMDHM(int type, int num) {
        Calendar c = Calendar.getInstance();
        c.add(type, num);
        return Long.parseLong(DateUtil.toString(c.getTime(), "yyyyMMddHHmm"));
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            return 0;
        }
    }

    public static boolean compareDateStr(String date1, String date2) {
        try {
            return compareDate(DateUtil.parse(date1),DateUtil.parse(date2));
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * @return true=date1>date2
     */
    public static boolean compareDate(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(date2);
            long time2 = cal.getTimeInMillis();
            if (time1 > time2) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * CompareTimes
     *
     * @return true time1>time2
     */
    public static boolean compareTimes(String times1, String times2) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_PATTERN);
        try {
            Date date1 = sdf.parse(times1);
            Date date2 = sdf.parse(times2);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(date2);
            long time2 = cal.getTimeInMillis();
            if (time1 > time2) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static long NowTime() {
        return Calendar.getInstance(). getTime().getTime();
    }

    public static Date addDay(Date date, int amount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, amount);
        return c.getTime();
    }

    public static Date addMonth(Date date, int amount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, amount);
        return c.getTime();
    }

    public static Date addYear(Date date, int amount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, amount);
        return c.getTime();
    }

    public static Date addHous(Date date, long amount) {
        Long l = date.getTime() + 60 * 60 * 1000 * amount;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);
        return c.getTime();
    }

    public static Date addMinute(Date date, long amount) {
        Long l = date.getTime() + 60 * 1000 * amount;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);
        return c.getTime();
    }

    public static Date addSecond(Date date, long amount) {
        Long l = date.getTime() + 1000 * amount;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);
        return c.getTime();
    }

    public static int minusDate(Date d1, Date d2) {
        return Long.valueOf((d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000)).intValue();
    }

    public static int getMinuteBetween(Date d1, Date d2) {
        return Long.valueOf((d1.getTime() - d2.getTime()) / (60 * 1000)).intValue();
    }

    public static int getSecondBetween(Date d1, Date d2) {
        return Long.valueOf((d1.getTime() - d2.getTime()) / (1000)).intValue();
    }



    /**
     * TODO 获得下一个月的年月
     */
    public static String getNextMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        String year = cal.get(Calendar.YEAR) + "";
        String nextMonth = cal.get(Calendar.MONTH) + 2 + "";
        return year + "0" + nextMonth;
    }

    /**
     * 获取当天 开始时间
     *
     */
    public static Date startOfToday() {
        return startOfDay(new Date(),0);
    }

    /**
     * 获取当天 结束时间
     *
     */
    public static Date endOfToday() {
        return endOfDay(new Date());
    }

    /**
     * 获取指定的开始时间或时间几天后的开始时间
     * start开始时间
     *day 指定天数
     */
    public static Date startOfDay(Date start,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取结束时间
     *
     */
    public static Date endOfDay(Date end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static String getTimeDes(Date time) {
        String viewtime = "";
        Date now = new Date();
        long ny = 1000L * 24 * 60 * 60 * 365;// 一年的毫秒数
        long nmon = 1000L * 24 * 60 * 60 * 30;// 一月的毫秒数
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long df = now.getTime() - time.getTime();
        if (df < nm) {
            viewtime = "剛剛";
        } else if (df < nh) {
            viewtime = (df / nm + "分鐘前");
        } else if (df < nd) {
            viewtime = (df / nh + "小時前");
        } else if (df < nmon) {
            viewtime = (df / nd + "天前");
        } else if (df < ny) {
            viewtime = (df / nmon + "月前");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            viewtime = sdf.format(time);
        }
        return viewtime;
    }

    public static int getLastTimeToDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) ((cal.getTimeInMillis() - System.currentTimeMillis()) / 1000);
    }

    public static Map<String, String> getMonthFirAndLastByDay(String day) {
        Map<String, String> map = new HashMap<String, String>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = df.parse(day);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 0);
            Date theDate = calendar.getTime();

            GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
            gcLast.setTime(theDate);
            gcLast.set(Calendar.DAY_OF_MONTH, 1);
            String day_first = df.format(gcLast.getTime());

            calendar.add(Calendar.MONTH, 1); // 加一个月
            calendar.set(Calendar.DATE, 1); // 设置为该月第一天
            calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
            String day_last = df.format(calendar.getTime());

            map.put("first", day_first);
            map.put("last", day_last);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Object> getLastMonthFistLastDay() {
        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取前一个月第一天
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        String firstDay = sdf.format(calendar1.getTime());
        map.put("firstDay", firstDay);
        //获取前一个月最后一天
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH, 0);
        String lastDay = sdf.format(calendar2.getTime());
        map.put("lastDay", lastDay);
        return map;
    }

    // 获取当前时间所在年的周数
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);

        return c.get(Calendar.WEEK_OF_YEAR);
    }

    //获取今天是星期几
    public static int getNowWeek(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_WEEK)-1;
    }

    // 获取当前时间所在年的周数
    public static int getWeekOfYear(String strDate) {
        Date date = parse(strDate);
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);

        return c.get(Calendar.WEEK_OF_YEAR);
    }

    // 获取当前时间所在年的最大周数
    public static int getMaxWeekNumOfYear(int year) {
        Date date = parse(year + "-12-31 00:00:00");
        List<Integer> weeks = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            String week = getWeekStr(day(date, -1 * i));
            weeks.add(Integer.parseInt(week.substring(4, 6)));
        }
        return Collections.max(weeks);
    }

    private static long getMillisOfDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 + c.get(Calendar.MINUTE) * 60 * 1000
                + c.get(Calendar.SECOND) * 1000;
    }

    /**
     * 获取当前为早中晚班
     *
     */
    public static int getDayWork() {
        long t = getMillisOfDay();
        if (t >= 25200000 && t <= 52200000)
            return 1;
        if (t > 52200000 && t <= 79200000)
            return 2;
        return 3;
    }

    /**
     * 根据输入日期获取前一天
     *
     */
    public static String getDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    /**
     * 根据输入时间获取当前时间前N天
     *
     */
    public static String getDayBeforeAnyDay(int anyDay) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -anyDay);
        date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 根据输入日期获取后一天
     *
     */
    public static String getDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * xxxx-xx-xx--->xx月xx日
     *
     * @param date
     * @return
     */
    public static String getDateFormatCharacter(String date) {
        return date.replace("-", "").substring(4, 6) + "月" + date.replace("-", "").substring(6, 8) + "日";
    }

    public static int getHour(int h) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, h);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取毫秒级时间戳long（13位）
     *
     * @param timeStr 字符串日期，例如：2017-01-01 00:00:00
     * @return 例如：1483200000000
     */
    public static long getLongTimeByStr(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_PATTERN);
        String time;
        try {
            time = String.valueOf(sdf.parse(timeStr).getTime() / 1000);
            return NumberUtils.toLong(time + "000");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算两个日期相隔天数
     *
     */
    public static int getDftDaysByDate(String startdate, String enddate) {
        int days = 9999999;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1;
        Date date2;
        try {
            date1 = sdf.parse(startdate);
            date2 = sdf.parse(enddate);
            days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public static int getAge(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    public static int getTwoDateThanDays(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date ddate1;
        Date ddate2;
        try {
            ddate1 = sdf.parse(date1);
            ddate2 = sdf.parse(date2);
            return getTwoDateThanDays(ddate1, ddate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int getTwoDateThanDays(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    }



    /**
     * 本周周一日期
     *
     * @return xxxx-xx-xx
     */
    public static String getNowWeekMonDate() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(nowDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        // 所在周结束日期
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 本周周日日期
     *
     * @return xxxx-xx-xx
     */
    public static String getNowWeekSunDate() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(nowDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 上周周一日期
     *
     * @return xxxx-xx-xx
     */
    public static String getLastWeekMonDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 上周周日日期
     *
     * @return xxxx-xx-xx
     */
    public static String getLastWeekSunDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, SUNDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 上上周周一日期
     *
     * @return xxxx-xx-xx
     */
    public static String getLastLastWeekMonDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -14);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 上上周周日日期
     *
     * @return xxxx-xx-xx
     */
    public static String getLastLastWeekSunDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -14);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, SUNDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 获取添加完数据后的时间
     *
     */
    public static String getTimeByMinute(Date time, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MINUTE, minute);
        return toString(calendar.getTime());
    }

    /**
     * 取前当前时间前2个小时
     *
     */
    public static long getTwoHoursAgoYMDH() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 2);
        return Long.parseLong(DateUtil.toString(calendar.getTime(), "YYYYMMddHH"));
    }

    public static long getTwoHoursAgoYMD() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 2);
        return Long.parseLong(DateUtil.toString(calendar.getTime(), "YYYYMMdd"));
    }

    public static Date getDateTime(long millionSeconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millionSeconds);
        return c.getTime();
    }

    /**
     * 获取几天前的那天的日期字符串
     *
     * @param past 具体的多少天
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(today);
    }

    /**
     * 获取当前日期agoDateNum天前的日期
     *
     */

    public static String getAgoDateByNum(int agoDateNum) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -(agoDateNum - 1));
        Date monday = calendar.getTime();
        return sdf.format(monday);
    }

    /**
     * 从周一算作一周开始
     *
     */
    public static int getChineseWeekNum() {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取中国周数
     *
     */
    public static String getChineseWeekNum(int num) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.add(Calendar.WEEK_OF_YEAR, num);
        int year = c.get(Calendar.YEAR);
        int week = c.get(Calendar.WEEK_OF_YEAR);
        if (week < 10) {
            return year + "0" + week;
        }
        return year + "" + week;
    }

    public static String getDate(long millionSeconds) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_PATTERN);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millionSeconds);
        Date date = c.getTime();
        return sdf.format(date);
    }

    // 获取当前时间的年月 份 如：201801
    public static int getYearMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return Integer.valueOf(sdf.format(new Date()));
    }

    public static int getYearMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return Integer.valueOf(sdf.format(date));
    }

    /**
     * 获取两个时间段之间的月份：201809，201810，201811
     */
    private static List<String> getMonthBetween(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();

        Date d1 = parse(minDate, "yyyy-MM-dd");//定义起始日期
        Date d2 = parse(maxDate, "yyyy-MM-dd");//定义结束日期
        Calendar dd = Calendar.getInstance();//定义日期实例
        dd.setTime(d1);//设置日期起始时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        while (dd.getTime().before(d2)) {//判断是否到结束日期
            String str = sdf.format(dd.getTime());
            result.add(str);
            dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
        }
        result.add(sdf.format(dd.getTime()));
        return result;
    }

    public static long countDown(String start, String markTime) {
        long time1 = getLongTimeByStr(start);// 获取半点时间
        long time2 = getLongTimeByStr(markTime);// 获取半点时间
        return time2 - time1;
    }

    /**
     * 獲取上個月
     *
     */
    public static String getLastMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1); // 得到前一个月
        return format.format(c.getTime());
    }

    // ------------------------------活动相关
    // start--------------------------------------
    // 判断是否是2个点之间
    @SuppressWarnings("deprecation")
    public static boolean isbetweenTwoHour(int start, int end) {
        Date date = new Date();
        int hour = date.getHours();
        if (hour >= start && hour <= end) {
            if (hour == end) {
                //相等的时候，只有分钟为0的时候才执行
                if (date.getMinutes() == 0) {
                    return true;
                } else
                    return false;
            }
            return true;
        }
        return false;
    }

    //不在2个时间点之间
    public static boolean between(int start, int end) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour >= start && hour < end) {
            return true;
        }
        return false;
    }


    /**
     * 萤火虫之光活动 5月7号到5月13号，结束后可删除代码 start 3个时间点的Stage（比谁快1，比谁多2，抢热1 3） 其他是0
     * 20-21返回1 21-22返回2 22-23返回3 其他时间段返回0
     */
    @SuppressWarnings("deprecation")
    public static int fireFlytime() {
        // 3个时间点的Stage（比谁快1，比谁多2，抢热1 3） 其他是0
        Date date = new Date();
        int hour = date.getHours();
        if (hour == 20) {
            return 1;
        }
        if (hour == 21) {
            return 2;
        }
        if (hour == 22) {
            return 3;
        }
        return 0;

    }

    /**
     * 情人节活动时间 每个月的 12日的12点开始 到 13号24点 为海选 中间 14号0点到12点为缓冲期 14日12：00至14日24：00
     * 为决赛 剩余时间活动未开始
     *
     * @return 0：活动为开始 1：第一阶段（海选） 2：第二阶段（缓冲期） 3：第三阶段（决赛）
     */
    // 在4月13日改成了13号开始 到15号
    @SuppressWarnings("deprecation")
    public static int act_Valentines_Day() {
        // 当前是第几号就是几
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int hour = new Date().getHours();
        if (day >= 13 && day <= 14) {
            if (day == 13) {
                if (hour < 12) {
                    return 0;// 活动未开始
                }
            }
            return 1;// 第一阶段
        }
        if (day == 15) {
            if (hour < 12) {
                return 2;// 第二阶段
            }
            return 3;// 决赛阶段
        }
        return 0;// 活动未开始
    }

    // 获取每个月的月的14号的sdf值 4月13日改成了13号开始了一次
    public static String act_valentines_is14() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 15);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    // 是否在2个时间区间
    public static boolean isTimeRange(String start, String end) {
        long current = System.currentTimeMillis();
        if (current >= DateUtil.getLongTimeByStr(start) && current <= DateUtil.getLongTimeByStr(end)) {
            return true;
        }
        return false;
    }

    /**
     * 周末上头条活动 使用 daxin 判断是否是周5，6，日
     */
    public static boolean isWeekend() {
        // 获取当前时间
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == SUNDAY
                || cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            return true;
        } else
            return false;
    }

    /**
     * 周星活动 使用 daxin 判断是否是周6，日
     */
    public static boolean isWeekendOfWeekStar() {
        // 获取当前时间
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == SUNDAY;
    }

    /**
     * 判断是否是周1
     *
     * @return
     */
    public static boolean isMonday() {
        // 获取当前时间
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
    }

    public static boolean isSameDay(Date date1,Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(date2);
        return dayOfYear == calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 周末上头条活动 使用 daxin 判断时间是否是在00：00到23：30之间
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static boolean is0_23_30(int type) {
        // 获取当前时间
        Date date = new Date();
        int hour = date.getHours();
        int min = date.getMinutes();
        if (type == 1) {
            // 定时器调用
            return hour < 23 || min <= 30;
        } else {
            // api调用
            return hour < 23 || min < 30;
        }

    }

    /**
     * 周末上头条活动 根据当前时间生成redis的命名 命名规则 act_weekend_月份_日_时_大于或小于30分钟 例子：
     * act_weekend_12_8_9_30> 代表12月8号的9点30以后 act_weekend_12_8_9_30<
     * 代表12月8号的9点30以前
     */
    @SuppressWarnings("deprecation")
    private static String generateRedisName() {
        StringBuilder sb = new StringBuilder();
        sb.append("act_weekend_");
        Date date = new Date();
        int hour = date.getHours();
        int min = date.getMinutes();
        int day = date.getDate();
        int month = date.getMonth() + 1;
        sb.append(month).append("_").append(day).append("_").append(hour);
        if (min - 30 < 0) {
            sb.append("_").append("30<");
        } else {
            sb.append("_").append("30>");
        }
        return sb.toString();
    }

    @SuppressWarnings("deprecation")
    public static String generateRedisUserName() {
        StringBuilder sb = new StringBuilder();
        sb.append("act_weekenduser_");
        Date date = new Date();
        int hour = date.getHours();
        int min = date.getMinutes();
        int day = date.getDate();
        int month = date.getMonth() + 1;
        sb.append(month).append("_").append(day).append("_").append(hour);
        if (min - 30 < 0) {
            sb.append("_").append("30<");
        } else {
            sb.append("_").append("30>");
        }
        return sb.toString();
    }

    /**
     * 获取时间分钟
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getWeekendHourMin() {
        StringBuilder sb = new StringBuilder();
        Date date = new Date();
        int hour = date.getHours();
        int min = date.getMinutes();
        if (min >= 30) {
            sb.append(hour).append(":").append(30).append("-").append(hour + 1).append(":").append("00");
        } else {
            sb.append(hour).append(":").append("00").append("-").append(hour).append(":").append("30");
        }
        return sb.toString();
    }

    // 获取钱半个小时
    @SuppressWarnings("deprecation")
    public static String getWeekendHourMinBefore() {
        StringBuilder sb = new StringBuilder();
        Date date = new Date();
        int hour = date.getHours();
        int min = date.getMinutes();
        if (hour == 0) {
            // 说明是新的一天
            if (min >= 30) {
                sb.append(hour).append(":").append("00").append("-").append(hour).append(":").append("30");
            } else {
                sb.append(23).append(":").append("30").append("-").append(hour).append(":").append("00");
            }
        } else {
            if (min >= 30) {
                sb.append(hour).append(":").append("00").append("-").append(hour).append(":").append("30");
            } else {
                sb.append(hour - 1).append(":").append("30").append("-").append(hour).append(":").append("00");
            }
        }
        return sb.toString();
    }

    /**
     * 获取当前时间前半个小时的周末上头条活动的redis名字
     */
    @SuppressWarnings("deprecation")
    public static String getWeekendRedisName() {
        String oldRedisName = generateRedisName();
        Date date = new Date();
        int min = date.getMinutes();
        int hour = date.getHours();
        if (min >= 30) {
            // 说明是定时任务是30分钟这个点执行的 就将redis名字中最后一个>号变成小于号
            return (oldRedisName.substring(0, oldRedisName.length() - 1) + "<");
        } else {
            // 说明定时任务是整点 00 的时候执行任务，所以要把小时提前到上一个小时，然后<号改成大于号
            if (hour >= 10) {
                hour = hour - 1;
                return oldRedisName.substring(0, oldRedisName.length() - 6) + hour + "_" + "30>";
            } else {
                hour = hour - 1;
                return oldRedisName.substring(0, oldRedisName.length() - 6) + "_" + hour + "_" + "30>";
            }
        }
    }

    /**
     * 获取当前日期的星期5的sdf的值
     */
    public static String getFriday() {
        // 设置当前日期
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(new Date());

        // 取当前日期是星期几(week:星期几)
        int week = aCalendar.get(Calendar.DAY_OF_WEEK);
        if (week == 1) {
            week = 7;
        } else if (week == 0) {
            week = 6;
        } else {
            week -= 1;
        }
        // 取距离当前日期最近的周日与当前日期相差的天数（count：相差的天数。正数：之后的周日，负数：之前的周日）
        int count = 0;
        count = 5 - week;
        // 获取距离当前日期最近的周日日期
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK, count);

        return df.format(c.getTime()) + " 00:00:00";
    }

    /**
     * 获取本周的周日
     *
     */
    public static String getSunday() {
        // 设置当前日期
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(new Date());

        // 取当前日期是星期几(week:星期几)
        int week = aCalendar.get(Calendar.DAY_OF_WEEK);
        if (week == 1) {
            week = 7;
        } else if (week == 0) {
            week = 6;
        } else {
            week -= 1;
        }
        // 取距离当前日期最近的周日与当前日期相差的天数（count：相差的天数。正数：之后的周日，负数：之前的周日）
        int count = 0;
        count = 5 - week;
        // 获取距离当前日期最近的周日日期
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK, count + 2);
        return df.format(c.getTime()) + " 23:59:59";
    }

    /**
     * 判断当前时间是否是00点 是返回false
     */
    @SuppressWarnings("deprecation")
    public static boolean isZero() {
        Date date = new Date();
        if (date.getHours() == 0)
            return false;
        else
            return true;
    }

    public static boolean isEvent20_02() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY) >= 20 || cal.get(Calendar.HOUR_OF_DAY) < 02;
    }

    public static boolean start_end(String start, String end) {
        long curTime = System.currentTimeMillis();
        return curTime >= DateUtil.parse(start, DEFAULT_FORMAT_PATTERN).getTime() && curTime <= DateUtil.parse(end, DEFAULT_FORMAT_PATTERN).getTime();
    }

    //半小时一个key
    public static String getHourHalf() {
        String date = getDay() + ":" + getHour();
        if (getMin() < 30) {
            date = date + "min";
        } else {
            date = date + "max";
        }
        return date;
    }

    //上半个小时的key
    public static String getLastHourHalf() {
        StringBuffer sb = new StringBuffer();
        String date = getDay();
        int hour = getHour();
        if (getMin() >= 30) {
            sb.append(date).append(":").append(hour).append("min");
        } else {
            //说明是上一个点的max
            //判断是是否是昨天
            if (hour == 0) {
                sb.append(toYesterday()).append(":").append(23).append("max");
            } else {
                sb.append(date).append(":").append(hour - 1).append("max");
            }
        }
        return sb.toString();
    }


    // ------------------------------活动相关 5*60*60
    // end--------------------------------------

    public static String parseYmdhm(String dateString) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(DateUtil.parse(dateString));
    }

    public static Date getWeekStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    public static Date getWeekEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.DAY_OF_WEEK, SUNDAY);
        return cal.getTime();
    }

    public static Date getMoonStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date getMoonEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static String toStr(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static String toStr(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT_PATTERN);
        return formatter.format(date);
    }

    /**
     * 字符串转换成日期 yyyy-MM-dd HH:mm:ss
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT_PATTERN);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getMonthFirstDayByDate(Date date, String parent) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(5, 1);
        DateFormat sdf = new SimpleDateFormat(parent);
        return sdf.format(cal.getTime());
    }

    public static int getBetweenDay(Date date1, Date date2) {
        return Math.abs(getBetweenDays(date1, date2));
    }

    public static int getBetweenDays(Date date1, Date date2) {
        return ((int) (date1.getTime() / 1000L) - (int) (date2.getTime() / 1000L)) / 3600 / 24;
    }

    public static int getTheDayByDate(Date startDate, Date endDate) {
        Calendar stC = Calendar.getInstance();
        stC.setTime(startDate);
        stC.set(Calendar.HOUR_OF_DAY, 0);
        stC.set(Calendar.MINUTE, 0);
        stC.set(Calendar.SECOND, 0);
        stC.set(Calendar.MILLISECOND, 0);
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        int day = (int) ((c.getTime().getTime() - stC.getTime().getTime()) / 86400000L);
        return Math.abs(day) + 1;
    }

    public static void main(String[] args) {
        System.out.println(getBetweenDays(new Date(),new Date()));
    }

    public static int getDayBetweenDay(Date standDate, Date date) {
        Calendar calendar = Calendar.getInstance();
        //获取现在的年月日
        calendar.setTime(date);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DATE);
        //获取用户创建的年月日
        calendar.setTime(standDate);
        int createYear = calendar.get(Calendar.YEAR);
        int createMonth = calendar.get(Calendar.MONTH);
        int createDay = calendar.get(Calendar.DATE);
        //计算天数差
        int diff = 0;
        if (Objects.equals(currentYear, createYear) && Objects.equals(currentMonth, createMonth)) {
            diff = currentDay - createDay+1;
        }
        return diff;
    }


    public static int getSecondEndToDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long s = c.getTime().getTime() - System.currentTimeMillis();
        int secondInfo = (int) s / 1000;
        if (secondInfo == 0) {
            secondInfo = 86400;
        }

        return secondInfo;
    }

    /**
     * 到下一个结算日还剩多少秒
     *
     */
    public static int getSecondEndToReset(int hour) {
        Date today = getResetDate(new Date(), hour);
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, 1);
        long s = c.getTime().getTime() - System.currentTimeMillis();
        int secondInfo = (int) s / 1000;
        if (secondInfo == 0) {
            secondInfo = 86400;
        }

        return secondInfo;
    }

    public static int getSecondEndToWeek(int hour) {
        Date today = getResetDate(new Date(), hour);
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        if (c.get(Calendar.WEEK_OF_YEAR) >= 52) {
            c.add(Calendar.YEAR, 1);
            c.set(Calendar.WEEK_OF_YEAR, 1);
        } else {
            c.add(Calendar.WEEK_OF_YEAR, 1);
        }
        long s = c.getTime().getTime() - System.currentTimeMillis();
        int secondInfo = (int) s / 1000;
        if (secondInfo == 0) {
            secondInfo = 86400 * 7;
        }

        return secondInfo;
    }

    public static int getSecondEndToMonth(int hour) {
        Date today = getResetDate(new Date(), hour);
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.set(Calendar.DAY_OF_MONTH, 1);
        if (c.get(Calendar.MONTH) >= Calendar.DECEMBER) {
            c.add(Calendar.YEAR, 1);
            c.set(Calendar.MONTH, Calendar.JANUARY);
        } else {
            c.add(Calendar.MONTH, 1);
        }
        long s = c.getTime().getTime() - System.currentTimeMillis();
        int secondInfo = (int) s / 1000;
        if (secondInfo == 0) {
            secondInfo = 86400 * 7;
        }

        return secondInfo;
    }

    /**
     * 获取日期的日更新时间
     *
     * @param date 日期
     * @param hour 日更新时间
     * @return 更新时间
     */
    public static Date getResetDate(Date date, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        if (c.after(now)) {
            c.add(Calendar.DATE, -1);
        }
        return c.getTime();
    }

    /**
     * <p>标题: getCurDate<p>
     * <p>说明: <br>获取当前时间</P>
     *
     * @return 当前时间
     */
    public static Date getCurDate() {
        return new Date();
    }


    /**
     * 根据刷新时间判断当前天
     *
     */
    public static String getDayByRefresh(int hour) {
        long nowTimeStamp = System.currentTimeMillis();
        long today = nowTimeStamp - hour * 60 * 60 * 1000L;
        return toDate(new Date(today));
    }

    public static String formatDate(Date date, String patten) {
        SimpleDateFormat sm = new SimpleDateFormat(patten);
        return sm.format(date);
    }

}
