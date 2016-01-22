package net.xuele.cloudteach.service.util;

import net.xuele.cloudteach.domain.CtWorkStatistics;

/**
 * Created by cm.wang on 2015/7/21 0021.
 */
public class TranslateWorkStatistic {

    public static CtWorkStatistics getCtWorkStatistics(String workClassJson, int studentSum ,int classNum, String  workId,int workItemNum,
                                                       int subStudentNum,int workType,String schoolId){
        CtWorkStatistics ctWorkStatistics = new CtWorkStatistics();
        ctWorkStatistics.setWorkId(workId);
        ctWorkStatistics.setVersion(1);
        ctWorkStatistics.setSchoolId(schoolId);
        ctWorkStatistics.setWorkSubStudentNum(subStudentNum);
        ctWorkStatistics.setWorkCorrectStudentNum(0);
        return ctWorkStatistics;
    }
}
