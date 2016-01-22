package net.xuele.cloudteach.service.util;

import net.xuele.cloudteach.domain.SRSchoolStatistic;
import net.xuele.cloudteach.domain.TeacherStatistic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cm.wang on 2015/8/15 0015.
 */
public class TranslateSRWork {

    public static  List<String> getSchoolIdFromWorkList(List<SRSchoolStatistic> workNumList){
        List<String> schoolIdList = new ArrayList<String>();
        for(SRSchoolStatistic statistic: workNumList){
            schoolIdList.add(statistic.getSchoolId());
        }
        return schoolIdList;
    }

    public static  List<String> getUserIdFromWorkList(List<TeacherStatistic> teacherStatisticList){
        List<String> userIdList = new ArrayList<String>();
        for(TeacherStatistic statistic: teacherStatisticList){
            userIdList.add(statistic.getTeacherId());
        }
        return userIdList;
    }
}
