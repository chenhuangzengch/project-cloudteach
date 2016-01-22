package net.xuele.cloudteach.constant;

/**
 * Created by cm.wang on 2015/7/17 0017.
 */
public interface Constants {

    //前端显示描述类内容最长字符串长度
    int MAX_LIST_CONTEXT_LENGTH = 60;

    //描述内容最大长度
    int MAX_CONTEXT_LENGTH = 3000;

    //评论内容最大长度
    int MAX_COMMENT_LENGTH = 500;

    //获取更多评论记录数
    int MAX_MORE_COMMENT_NUM = 100;

    //上传文件名字最大长度
    int MAX_FILE_NAME_LENGTH = 30;

    //同一作业连续发送提醒交作业通知间隔秒数
    int MIN_SECONDS_WARNSUB = 21600;

    //口语作业最多支持单词数
    int MAX_VOICE_WORK_WORDS = 15;

    //作业发布最长延迟天数
    int MAX_PUBLISH_WORK_EXDAYS = 60;

    // 课外作业
    int WORK_TYPE_EXTRA_WORK = 8;

    //板书类型
    int WORK_TYPE_VOICE_WORK = 7;

    //板书类型
    int WORK_TYPE_BLACKBOARD_PUBLISH = 6;

    //网络课件类型
    int WORK_TYPE_COURSE_REAPPEAR = 5;

    //电子作业
    int WORK_TYPE_EXERCISE_WORK = 4;

    //同步课堂作业
    int WORK_TYPE_SYNCLASS_WORK = 3;

    //同步课堂作业
    int WORK_TYPE_MAGIC_WORK = 2;

    //同步课堂作业
    int WORK_TYPE_GUIDANCE_WORK = 1;

    //我的课件
    int COURSEWARE_BELONG_TYPE_MY_APPEAR = 1;

    //发布的课件
    int COURSEWARE_BELONG_TYPE_COURSE_APPEAR = 3;

    //用户类型
    String TEACHER_TYPE = "1";

    String STUDENT_TYPE = "2";

    int WORK_TYPE = 1;

    int BLACKBOARD_TYPE = 2;

    //乐观锁机制尝试次数
    int OPTIMISTIC_LOCK_TRY_TIMES = 10;

    //提分宝作业评分描述10
    String MAGIC_WORK_SCORE_CONTEXT10 = "恭喜你！继续保持，清华北大在等你哦！";

    //提分宝作业评分描述9
    String MAGIC_WORK_SCORE_CONTEXT9 = "哇噢！可以挑战清华北大了！";

    //提分宝作业评分描述8
    String MAGIC_WORK_SCORE_CONTEXT8 = "你好聪明，加油冲击重点大学吧！";

    //提分宝作业评分描述7
    String MAGIC_WORK_SCORE_CONTEXT7 = "太棒了，可以挑战一本大学啦！";

    //提分宝作业评分描述6
    String MAGIC_WORK_SCORE_CONTEXT6 = "再次挑战，稍微努力下就能二本大学啦！";

    //提分宝作业评分描述5
    String MAGIC_WORK_SCORE_CONTEXT5 = "再多练练就可以上大学啦！";
    /**提分宝数据统计前端页面分数描述*/
    //提分宝作业评分描述10
    String STATISTICS_SCORE_CONTEXT10 = "清华北大";
    //提分宝作业评分描述9
    String STATISTICS_SCORE_CONTEXT9 = "重点大学";
    //提分宝作业评分描述8
    String STATISTICS_SCORE_CONTEXT8 = "一本大学";
    //提分宝作业评分描述7
    String STATISTICS_SCORE_CONTEXT7 = "二本大学";
    //提分宝作业评分描述6
    String STATISTICS_SCORE_CONTEXT6 = "三本大学";
    //提分宝作业评分描述5
    String STATISTICS_SCORE_CONTEXT5 = "挑战完成";



    //提醒交作业通知标题
    String WARNSUB_TITLE = "作业提醒";

    //提醒查看教师发布的板书、网络课件通知标题
    String WARNVIEW_BLACKBOARD_TITLE = "新板书";
    String WARNVIEW_COURSEWARE_TITLE = "新课件";

    //提醒交作业通知内容
    String WARNSUB_CONTENT = "赶快交作业，老师都着急啦！";

    String GUIDANCE_WORK_NAME = "预习作业";
    String MAGIC_WORK_NAME = "提分宝作业";
    String SYNCLASS_WORK_NAME = "同步课堂作业";
    String EXERCISE_WORK_NAME = "电子作业";
    String VOICE_WORK_NAME = "口语作业";
    String BLACKBOARD_WORK_NAME = "板书";
    String COURSEWARE_WORK_NAME = "网络课件";
    String EXTRA_WORK_NAME = "课外作业";

    String WARN_SUB_WORK_BUSY = "您太忙碌啦，请休息一会儿，稍后再试";

    // 页面最初加载的评论数
    int MIN_COMMENT_NUM = 4;

    // 数字的正则表达式
    final String NUMBER_EXPR = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$";

    // indentifyId
    String IDENTIFY_SCHOOL_MANAGER = "SCHOOL_MANAGER";
    String IDENTIFY_TEACHER = "TEACHER";
    String IDENTIFY_STUDENT = "STUDENT";
    String IDENTIFY_PARENT="PARENT";

    final int MAX_STUDENT_NUMBER_FOR_PUBLIC = 1000;

    //出题数
    int MAGIC2_WORK_QUEST_NUM = 8;
    //最大衍生题号sort
    int MAGIC2_WORK_MAX_SORT = 5;

   //错误率
    double MAGIC2_WORK_WRONG_QUEST_NUM = 0.2;

    //默认题目初始平均用时
    final long MAGIC2_WORK_QUEST_AVGTIME = 30000;
    //默认作业时间
    final long DEFAULT_WORK_TIME = 300000;
}
