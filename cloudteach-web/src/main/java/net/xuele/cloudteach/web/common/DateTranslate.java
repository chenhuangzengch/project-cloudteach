package net.xuele.cloudteach.web.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by cm.wang on 2015/7/24 0024.
 */
public class DateTranslate {

    private static Logger logger = LoggerFactory.getLogger(DateTranslate.class);

    /**
     * 计算最晚提交时间
     *
     * @param date      发布时间
     * @param commitDay 几天后
     * @return
     */
    public static Date getLastCommitDate(Date date, int commitDay) {
        //用来计算最晚提交时间，1天内，3天内，5天内
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.add(calendar.DATE, commitDay);
        return calendar.getTime();
    }

    /**
     * 跟当前时间进行比较
     *
     * @param date
     * @param format
     * @return
     */
    public static int compareNowDate(Date date, String format) {
        Date now = new Date();
        int result = 0;
        if (getFormatDate(date, format).getTime() > getFormatDate(now, format).getTime())
            result = 1;
        return result;
    }

    /**
     * 获得所需要的日期格式
     *
     * @param date
     * @param format yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getFormatDate(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date formatDate = null;
        try {
            formatDate = df.parse(getFormatStringDate(date, format));
        } catch (Exception e) {
            logger.info("日期转换出错" + e.getMessage());
        }
        return formatDate;
    }

    public static String getFormatStringDate(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
            return df.format(date);
    }

    public static String getTsToDateString(Long ts, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(new Date(ts));
        return date;
    }

    /**
     * 获得所需要的日期格式
     *
     * @param date
     * @param format yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getFormatDateFromString(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date formatDate = null;
        try {
            formatDate = formatter.parse(date);
        } catch (ParseException e) {
            logger.info("日期转换失败:" + e.getMessage());
        }
        return formatDate;
    }


    public static void main(String[] args) {
        Date date = getLastCommitDate(new Date(), 4);
        System.out.println(getFormatDateFromString("2015-8-3 12:12:12:15", "yyyy-MM-dd HH:mm:ss"));
        System.out.println(getFormatStringDate(getFormatDateFromString("2015-8-3 12:12:12:15", "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd"));
        System.out.print(compareNowDate(date, "yyyy-MM-dd"));
        System.out.println(Math.round(1.563));
    }

    /**
     * String格式时间戳转date
     *
     * @param time
     * @return
     */
    public static Date stringTimeToDate(String time, String formatter) {
        SimpleDateFormat format = new SimpleDateFormat(formatter);
        Long times = Long.parseLong(time.isEmpty() ? "0" : time);

        String d = format.format(times);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
