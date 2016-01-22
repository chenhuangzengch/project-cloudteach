package net.xuele.cloudteach.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by duzg on 2015/8/1 0001.
 */
public class DateTimeUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    public static String DateToStringForMycat(Date date) {
        if (null != date) {
            return simpleDateFormat.format(date);
        }
        return null;
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
            e.printStackTrace();
        }
        return formatDate;
    }

    /**
     * 获得所需要的日期格式 date+time
     *
     * @param date   只有 yyyy-MM-dd
     * @param format yyyy-MM-dd HH:mm:ss
     * @param time   HH:mm:ss
     * @return
     */
    public static Date getFormatDateFromString(String date, String format, String time) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date formatDate = null;
        try {
            formatDate = formatter.parse(date + " " + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDate;
    }

    public static String getFormatStringDate(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
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

    public static void main(String[] args) {

        //Date begtime = new Date();
//        String endtime = "2015-08-20 20:14:29";
        //Date begdate = getFormatDate(begtime, "yyyy-MM-dd HH:mm:ss");
//        Date enddate = getFormatDateFromString(endtime, "yyyy-MM-dd HH:mm:ss");
//        System.out.println((enddate.getTime() - begdate.getTime()) / 1000);
//
//
       //System.out.println(begdate.toString());
//        System.out.println(getDayEnd(new Date()));
//        System.out.println((getDayEnd(new Date()).getTime()-(new Date()).getTime())/1000);
        //System.out.println(getDateHM(new Date()));

        //System.out.println(getTsToDateString(172l));

        List<String> list=new ArrayList<String>();
        list.add("保护环境");     //向列表中添加数据
        list.add("爱护地球");     //向列表中添加数据
        list.add(0,"从我做起");     //在第1+1个元素的位置添加数据
        //通过循环输出列表中的内容
        for(int i=0;i<list.size();i++){
            System.out.println(i+":"+list.get(i));
        }
    }


    /**
     * 获取一天的开始
     *
     * @param now
     * @return
     */
    public static Date getDayBegin(Date now) {
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        return c.getTime();
    }

    /**
     * 获取一天的结束
     *
     * @param now
     * @return
     */
    public static Date getDayEnd(Date now) {
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 获取传入时间对应的星期一的日期
     *
     * @param now
     * @return
     */
    public static Date getWeekBegin(Date now) {

        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.set(Calendar.DAY_OF_WEEK, c.getActualMinimum(Calendar.DAY_OF_WEEK));
        // 国内一周以星期一作为开始
        c.add(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        return c.getTime();
    }

    /**
     * 获取传入时间对应的星期天的日期时间
     *
     * @param now
     * @return
     */
    public static Date getWeekEnd(Date now) {

        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.set(Calendar.DAY_OF_WEEK, c.getActualMaximum(Calendar.DAY_OF_WEEK));
        // 国内一周以星期天作为结束
        c.add(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 获取传入时间对应的月初日期
     *
     * @param now
     * @return
     */
    public static Date getMonthBegin(Date now) {

        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        return c.getTime();
    }

    /**
     * 获取传入时间对应的月末日期
     *
     * @param now
     * @return
     */
    public static Date getMonthEnd(Date now) {

        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    public static final String FORMAT_DATE_HM="yyyyMMddHHmm";

    /**
     * 获得时间字符串:yyyyMMddHHmm
     * @param date
     * @return
     */
    public static String getDateHM(Date date){
        SimpleDateFormat formate = new SimpleDateFormat(FORMAT_DATE_HM);
        return formate.format(date);
    }

    /**
     * 获取当前时间字符串，精确到秒
     *
     * @return
     */
    public static String getDateStr() {
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        return longSdf.format(c.getTime());
    }

    /**
     * 时间戳转时长
     * @param ts
     * @return
     */
    public static String getTsToDateString(Long ts) {
        long hour = ts/(60*60);
        long minute = (ts - hour*60*60)/(60);
        long second = (ts - hour*60*60 - minute*60);
        if(second >= 60 )
        {
            second = second % 60;
            minute+=second/60;
        }
        if(minute >= 60)
        {
            minute = minute %60;
            hour += minute/60;
        }
        String sh = "";
        String sm="";
        String ss="";
        if(hour <10)
        {
            sh = "0" + String.valueOf(hour);
        }else
        {
            sh = String.valueOf(hour);
        }
        if(minute <10)
        {
            sm = "0" + String.valueOf(minute);
        }else
        {
            sm = String.valueOf(minute);
        }
        if(second <10)
        {
            ss = "0" + String.valueOf(second);
        }else
        {
            ss = String.valueOf(second);
        }

        return sh + ":" + sm + ":" + ss;
    }
}
