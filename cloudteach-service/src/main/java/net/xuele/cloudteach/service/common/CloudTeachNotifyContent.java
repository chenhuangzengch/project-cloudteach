package net.xuele.cloudteach.service.common;

import net.xuele.cloudteach.constant.Constants;

/**
 * 云教学组装通知内容
 * Created by duzg on 2015/8/21 0021.
 */
public class CloudTeachNotifyContent {

    /**
     * 组装教师提交交作业通知内容
     *
     * @param teacherName 教师名字
     * @param workType    作业类型
     * @return
     */
    public static String getWarnSubContent(String teacherName, int workType) {
        if (workType < 1 || workType > 8) {
            return "";
        }
        String warnSubContent = "";
        String workTypeName = "";
        switch (workType) {
            case 1:
                workTypeName = Constants.GUIDANCE_WORK_NAME;
                break;
            case 2:
                workTypeName = Constants.MAGIC_WORK_NAME;
                break;
            case 3:
                workTypeName = Constants.SYNCLASS_WORK_NAME;
                break;
            case 4:
                workTypeName = Constants.EXERCISE_WORK_NAME;
                break;
            case 5:
                workTypeName = Constants.COURSEWARE_WORK_NAME;
                break;
            case 6:
                workTypeName = Constants.BLACKBOARD_WORK_NAME;
                break;
            case 7:
                workTypeName = Constants.VOICE_WORK_NAME;
                break;
            case 8:
                workTypeName = Constants.EXTRA_WORK_NAME;
                break;
        }
        if (workType == 5 || workType == 6) {
            warnSubContent = teacherName + "发布了一份" + workTypeName + ",赶紧去学习吧！";
        } else {
            warnSubContent = teacherName + "给你布置了一份" + workTypeName + ",赶紧去做作业吧！";
        }
        return warnSubContent;
    }

}
