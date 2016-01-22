package net.xuele.cloudteach.web.common;

import net.xuele.cloudteach.constant.Constants;

import java.util.Calendar;
import java.util.Date;

/**
 * 用来计算最晚提交时间，1天内，3天内，5天内
 * Created by hujx on 2015/7/25 0025.
 */
public class DateEditor {

    public static DateEditor getInstance() {
        return new DateEditor();
    }

    /**
     * @param publishTime     发布时间
     * @param lastSubmitRange x天后提交
     * @return
     */
    public Date getEndDime(Date publishTime, int lastSubmitRange) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(publishTime);
        calendar.add(calendar.DATE, lastSubmitRange);
        calendar.set(calendar.HOUR_OF_DAY, 23);
        calendar.set(calendar.MINUTE, 59);
        calendar.set(calendar.SECOND, 59);

        return calendar.getTime();
    }


    public Date getPublishTime(String dateStr) {

        Date publishDate = null;

        if (dateStr == null || dateStr == "") {
            // 字符串为空或者空字符串
            publishDate = new Date();
        } else if (!dateStr.matches(Constants.NUMBER_EXPR)) {
            // 传过来的字符串存在数字以外的字符
            publishDate = new Date();
        } else {
            // 即时发布
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            // 定时发布处理
            Calendar later = Calendar.getInstance();
            later.setTime(new Date(Long.parseLong(dateStr)));
            later.set(later.HOUR_OF_DAY, 00);
            later.set(later.MINUTE, 00);
            later.set(later.SECOND, 00);

            /**
             * 1,参数发布时间=今天，即时发布，需要精确到时分秒
             * 2,参数发布时间<今天，默认已现在时间发布
             * 3,参数发布时间>今天，定时发布，
             */
            if (now.get(Calendar.YEAR) == later.get(Calendar.YEAR) && now.get(Calendar.MONTH) == later.get(Calendar.MONTH) &&
                    now.get(Calendar.DAY_OF_MONTH) == later.get(Calendar.DAY_OF_MONTH)) {
                publishDate = now.getTime();
            } else if (later.getTime().getTime() < now.getTime().getTime()) {
                publishDate = new Date();
            } else {
                publishDate = later.getTime();
            }

            if (publishDate == null) {
                publishDate = new Date();
            }
        }
        return publishDate;
    }

}
