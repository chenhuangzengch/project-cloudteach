package net.xuele.cloudteach.constant;

import net.xuele.common.exceptions.CloudteachException;

/**
 * Created by sunxh on 2015/6/21 0021.
 */
public enum CloudTeachErrorEnum {

    //<===================================================================>
    //公共部分 start
    NOTLOGIN("NOTLOGIN", "当前用户未登录"),
    OBJECTNOTFOUND("OBJECTNOTFOUND", "查找的对象不存在"),
    CANNOTSHARE("CANNOTSHARE", "不可分享"),
    ALREADYSHARED("ALREADYSHARED", "已经分享"),
    AUDITNOTPASS("AUDITNOTPASS", "审核不通过"),
    USERIDNOTNULL("USERIDNOTNULL", "用户号不能为空"),
    CHARSETERROR("CHARSETERROR", "字符集错误"),
    ILLEGALNAME("ILLEGALNAME", "您输入了非法字符"),
    PARAMERROR("PARAMERROR", "参数错误"),
    SCHOOLIDCANNOTBENULL("SCHOOLIDCANNOTBENULL", "学校不能为空"),
    USERIDCANNOTBENULL("USERIDCANNOTBENULL", "用户不能为空"),
    NOTMYSHARE("NOTMYSHARE", "不是自己分享的不能取消分享"),
    NOPERMISSION("NOPERMISSION", "无权限进行该操作"),
    DATAUNIQUENESS("DATAUNIQUENESS", "数据唯一性错误"),
    DATA_MISMATCHED("DATA_MISMATCHED", "无法匹配到相应数据"),
    OVERMAXLENGTH("OVERMAXLENGTH", "内容长度超过上限"),
    WARNSUBTIMEUNOVER("WARNSUBTIMEUNOVER", "未到提醒交作业最小间隔时间"),
    CLASSNOSTUDENT("CLASSNOSTUDENT", "该班级下无任何学生"),
    DATAERROR("DATAERROR", "数据错误"),
    SYSTEMBUSY("SYSTEMBUSY", "系统繁忙，请稍后提交"),
    ERRORDELETE("ERRORDELETE", "资源删除出错！"),
    //公共部分 end
    //<===================================================================>

    //<===================================================================>
    //提分宝2 start
    QUESTNOTFOUND("QUESTNOTFOUND", "提分宝题目不存在"),
    QANSWERNOTFOUND("QANSWERNOTFOUND", "提分宝答案不存在"),

    //提分宝2 end
    //<===================================================================>

    //<===================================================================>
    //云盘部分 start
    UPLOADFAILED("UPLOADFAILED", "上传失败"),
    CANNOTCOLLECTBYSELF("CANNOTCOLLECTBYSELF", "不能收藏自己的资源"),
    DISKNOTBELONGTOUSER("DISKNOTBELONGTOUSER", "不是该用户的资源"),
    INSERTDISKFAILED("INSERTDISKFAILED", "新增资源失败"),
    FILENOTREADY("FILENOTREADY", "预览文件还没有准备好"),
    EXTENSIONNOTSUPPORT("EXTENSIONNOTSUPPORT", "扩展名不被支持"),
    DOWNLOADTOOFREQUENT("DOWNLOADTOOFREQUENT", "你太忙碌了，请先休息一会，稍后再试"),
    FILETYPECANNOTBENULL("FILETYPECANNOTBENULL", "文件类型不能为空"),
    UNITIDCANNOTBENULL("UNITIDCANNOTBENULL", "课程不能为空"),
    USERSHARENOPRAISE("USERSHARENOPRAISE", "自己分享的不能点赞"),
    ALREADYPRAISED("ALREADYPRAISED", "已经点赞不能点赞"),
    UNPRAISED("UNPRAISED", "未点赞不能取消点赞"),
    ALREADYCILLECTED("ALREADYCILLECTED", "已经收藏"),
    UNCOLLECTED("UNCOLLECTED", "未收藏"),
    ALREADYSTICKY("ALREADYSTICKY", "资源已经置顶"),
    UNSTICKY("UNSTICKY", "资源未置顶"),
    STICKYFAILED("STICKYFAILED", "置顶失败"),

    //云盘部分 end
    //<===================================================================>


    //<===================================================================>
    //预习部分 start
    GUIDANCE_ITEM_NOT_YOURS("GUIDANCE_ITEM_NOT_YOURS", "不是该用户创建的预习项"),
    GUIDANCE_ITEM_STATUS_ERROR("GUIDANCE_ITEM_STATUS_ERROR", "预习项状态错误"),
    GUIDANCE_ITEM_CTEATE_FAILED("GUIDANCE_ITEM_CTEATE_FAILED", "预习题创建失败！"),
    GUIDANCE_ITEM_NOT_EXIST("GUIDANCE_ITEM_NOT_EXIST", "预习题不存在！"),
    GUIDANCE_ITEM_IS_COLLECT("GUIDANCE_ITEM_IS_COLLECT", "预习题来自收藏，不能编辑！"),


    //预习部分 end
    //<===================================================================>


    //<===================================================================>
    //布置作业 start
    WORK_ADD_FAILED("WORK_ADD_FAILED", "布置作业失败！"),
    WORK_CLASS_NULL("WORK_CLASS_NULL", "请选择班级！"),
    WORK_ITEM_NULL("WORK_ITEM_NULL", "请选择作业题目！"),
    WORK_DELETE_FAILED("WORK_DELETE_FAILED", "该作业已经被删除，请勿重复操作！"),
    VOICE_ENGLISH_ONLY("VOICE_ENGLISH_ONLY", "只有英语才能布置口语作业！"),
    //布置作业 end
    //<===================================================================>

    //<===============================授课课件====================================>

    COURSEWARES_CONTENT_NOT_EXIST("COURSEWARES_CONTENT_NOT_EXIST", "课件内容为空"),
    TEACH_COURSEWARES_NOT_FOUND("TEACH_COURSEWARES_NOT_FOUND", "课件不存在或已删除！"),
    TEACH_COURSEWARES_NOT_YOURS("TEACH_COURSEWARES_NOT_YOURS", "课件不属于当前用户！"),
    TEACH_COURSEWARES_ALREADY_SHARED("TEACH_COURSEWARES_ALREADY_SHARED", "课件已经被分享过！"),
    TEACH_COURSEWARES_IS_COLLECT("TEACH_COURSEWARES_IS_COLLECT", "课件来自收藏，不可分享！"),
    TEACH_COURSEWARES_NOT_COLLECT("TEACH_COURSEWARES_NOT_COLLECT", "不是收藏的课件"),

    //<===============================授课课件 end====================================>

    //<===============================板书====================================>

    BLACKBOARD_CONTENT_NOT_EXIST("BLACKBOARD_CONTENT_NOT_EXIST", "板书内容为空"),
    BLACKBOARD_File_NOT_EXIST("BLACKBOARD_File_NOT_EXIST", "附件不能为空"),

    //<===============================板书 end====================================>

    //<===============================同步课堂====================================>

    SYNCLASS_CONTENT_NOT_EXIST("SYNCLASS_CONTENT_NOT_EXIST", "同步课堂内容为空"),

    SYNCLASS_GAME_NULL("SYNCLASS_GAME_NULL", "同步课堂分配游戏异常"),

    //<===============================同步课堂 end====================================>


    //<===============================题库====================================>

    BANK_ITEM_NOT_FOUND("BANK_ITEM_NOT_FOUND", "题目不存在"),
    BANK_ITEM_CTEATE_FAILED("BANK_ITEM_CTEATE_FAILED", "题目创建失败！"),
    BANK_ITEM_NOT_EXIST("BANK_ITEM_NOT_EXIST", "题目不存在！"),
    BANK_ITEM_IS_COLLECT("BANK_ITEM_IS_COLLECT", "题目来自收藏，不能编辑！"),
    BANK_ITEM_NOT_YOURS("BANK_ITEM_NOT_YOURS", "题目不属于当前用户！"),
    BANK_ITEM_ALREADY_SHARED("BANK_ITEM_ALREADY_SHARED", "题目已分享，不可重复分享！"),
    BANK_ITEM_UNSHARED("BANK_ITEM_UNSHARED", "题目未分享成功，不可取消分享！"),

    //<===============================题库 end====================================>


    //<===================================================================>
    //教师作业管理 start
    WORKTYPEERROR("WORKTYPEERROR", "作业类型错误"),
    WORKIDNOTNULL("WORKIDNOTNULL", "作业号不能为空"),
    WORKIDNOTDELETE("WORKIDNOTDELETE", "改作业不是当前教师发布，没有删除权限"),
    FAILDELSTUANS("FAILDELSTUANS", "删除学生作业失败"),
    //教师作业管理 end
    //<===================================================================>
    //<===================================================================>
    //学生作业管理 start
    ERRORSUBMIT("ERRORSUBMIT", "提交方式错误"),
    TOMANYFILES("TOMANYFILES", "回答附件超过规定数量（一共9个：其中视频、声音最多1个，图片最多9个）"),
    ALREADYSUM("ALREADYSUM", "该作业已经提交！"),
    WORK_EDIT_FAILED("WORK_EDIT_FAILED", "已超过最晚交作业时间，该作业不能进行修改！"),
    WORK_VIEW_DISCUSSION_FAILED("WORK_VIEW_DISCUSSION_FAILED", "请先完成该作业！"),
    WORK_SUBMIT_FAILED("WORK_SUBMIT_FAILED", "作业提交失败！"),
    WORK_NOT_FOUND("WORK_NOT_FOUND", "找不到该作业，可能已被删除！"),
    WORK_IS_NOT_YOURS("WORK_IS_NOT_YOURS", "请确认该作业是否属于您自己！"),
    ANSWER_STATISTICS_NOT_FOUND("ANSWER_STATISTICS_NOT_FOUND", "找不到回答统计信息，可能已被删除！"),
    CHALLENGE_NOT_FOUND("CHALLENGE_NOT_FOUND", "找不到挑战纪录！"),
    //学生作业管理 end
    //<===================================================================>

    ;
    // 成员变量
    private String code;
    private String msg;

    // 构造方法
    private CloudTeachErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 获取errMsg
    public static String getMsg(String code) {
        for (CloudTeachErrorEnum c : CloudTeachErrorEnum.values()) {
            if (c.getCode() == code) {
                return c.msg;
            }
        }
        return null;
    }

    //抛出cloudteachException入口
    public static void throwException(CloudTeachErrorEnum cloudTeachErrorEnum) throws CloudteachException {
        throw new CloudteachException(cloudTeachErrorEnum.getMsg(), cloudTeachErrorEnum.getCode());
    }

    //抛出cloudteachException入口
    public static void throwException(CloudTeachErrorEnum cloudTeachErrorEnum, String msg) throws CloudteachException {
        throw new CloudteachException(cloudTeachErrorEnum.getMsg() + " -- " + msg, cloudTeachErrorEnum.getCode());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static void main(String[] args) {
        System.out.println(CloudTeachErrorEnum.getMsg("NOTLOGIN"));
    }
}
