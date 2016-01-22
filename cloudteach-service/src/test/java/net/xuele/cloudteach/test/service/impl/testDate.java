package net.xuele.cloudteach.test.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/7/7 0007.
 */
public class testDate {

    public static void main(String[] arg) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(calendar.DATE, 1);
        //设置时分秒
        calendar.set(calendar.HOUR_OF_DAY, 23);
        calendar.set(calendar.MINUTE, 59);
        calendar.set(calendar.SECOND, 59);
        date = calendar.getTime();   //这个时间就是日期往后推一天的结果

        System.out.println(format.format(date));

    }
}
