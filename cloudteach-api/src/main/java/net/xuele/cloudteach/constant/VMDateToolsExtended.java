package net.xuele.cloudteach.constant;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by hujx on 2015/8/20 0020.
 * Velocity自带的日期工具类不能满足需求的，可以将需要的方法加入到这里
 */
public class VMDateToolsExtended {

    /**
     * 与今天对比，不精确到24小时
     * <p/>
     * 例：今天晚上-明天早上，算相差一天
     * <p/>
     * 返回今天0，
     * 昨天-1，前天-2，再往前，x天前
     * 明天1，后天2，再往后，x天后
     *
     * @param date
     * @return
     */
    public static long compareToToday(Date date) {
        // 返回值
        long ret = 0;
        if (date == null) {
            date = new Date();
        }
        // 今天的日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        long today = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(date);
        long other = calendar.get(Calendar.DAY_OF_YEAR);
        // 得到相差的天数
        ret = other - today;
        return ret;
    }

    /**
     * 与今天对比，精确到24小时
     * <p/>
     * 例：今天中午12点0分0秒-明天中午12点0分0秒，算相差一天
     * <p/>
     * 返回今天0，
     * 昨天-1，前天-2，再往前，x天前
     * 明天1，后天2，再往后，x天后
     *
     * @param date
     * @return
     */
    public static long compareToNow(Date date) {
        // 返回值
        long days = 0;
        if (date == null) {
            date = new Date();
        }
        // 今天的日期
        Date now = new Date();
        long millis = date.getTime() - now.getTime();
        // 得到相差的天数
        days = millis / (24 * 60 * 60 * 1000);
        return days;
    }

    /**
     * 比较传入时间用于当前时间的大小
     *
     * @param date
     * @return
     */
    public static int compareToCurrTime(Date date) {

        int ret = 0;
        Date now = new Date();
        if (date == null) {
            date = now;
        }

        if (now.getTime() < date.getTime()) {
            ret = 0;
        } else {
            ret = 1;
        }

        return ret;
    }

    /**
     * 返回传入参数的毫秒数
     *
     * @param date
     * @return
     */
    public static String toMillis(Date date) {

        if (date == null) {
            date = new Date();
        }
        return String.valueOf(date.getTime());
    }

}
