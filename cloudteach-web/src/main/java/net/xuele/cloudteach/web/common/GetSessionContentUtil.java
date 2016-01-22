package net.xuele.cloudteach.web.common;

import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.common.dto.ClassInfoDTO;
import net.xuele.common.security.SessionUtil;
import net.xuele.common.security.UserSession;

import java.util.List;

/**
 * Created by panglx on 2015/7/9 0009.
 */
public class GetSessionContentUtil {
    private static GetSessionContentUtil instance = new GetSessionContentUtil();

    public GetSessionContentUtil() {
    }

    public static GetSessionContentUtil getInstance() {
        return instance;
    }

    public UserSession getUserSession() {
//		//TODO 暂时模拟session用于测试  后期需删除
//		UserSession userSession1 = new UserSession("265143510212", "123456", new ArrayList<GrantedAuthority>());
//		userSession1.setUserId("265143510212");
//		userSession1.setArea("330108");
//		userSession1.setAreaName("滨江区");
//		userSession1.setSchoolId("SchoolIDQATestsm55");
//		userSession1.setSchoolName("北京海淀门头沟小学");
//		userSession1.setBookId("030001001016100");
//		return userSession1;
//
        UserSession userSession = null;

        try {
            userSession = SessionUtil.getUserSession();

            if (null == userSession) {
                CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.NOTLOGIN);
            }
        } catch (NullPointerException e) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.NOTLOGIN);
        }
        return userSession;
    }

    /**
     * 获取当前用户ID
     *
     * @return
     */
    public String currentUserID() {
        return getUserSession().getUserId();
    }

    /**
     * 获取当前用户名称
     *
     * @return
     */
    public String currentUserName() {
        return getUserSession().getRealName();
    }

    /**
     * 获取当前登录用户类型
     *
     * @return
     */
    public String currentIdentityId() {
        return getUserSession().getIdentityId();
    }

    /**
     * 获取当前登录用户学校ID
     *
     * @return
     */
    public String currentSchoolID() {
        return getUserSession().getSchoolId();
    }

    /**
     * 获取当前登录用户班级ID
     *
     * @return
     */
    public String currentClassID() {
        return getUserSession().getClassId();
    }

    /**
     * 获取当前登录用户学校所在地区编号
     *
     * @return
     */
    public String currentAreaID() {
        return getUserSession().getArea();
    }

    /**
     * 获取当前登录用户主授课本ID
     *
     * @return
     */
    public String currentBookID() {
        return getUserSession().getBookId();
    }

    /**
     * 获取当前登录用户主授课本名称
     *
     * @return
     */
    public String currentBookName() {
        return getUserSession().getBookName();
    }

    /**
     * 获取学校名称
     *
     * @return
     */
    public String currentSchoolName() {
        return getUserSession().getSchoolName();
    }

    /**
     * 获取学校所在地区名称
     *
     * @return
     */
    public String currentAreaName() {
        return getUserSession().getAreaName() == null ? "" : getUserSession().getAreaName();
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    public String currentUserIcon() {
        return getUserSession().getIcon();
    }

    /**
     * 获取教辅id
     *
     * @return
     */
    public String currentExtraBookId() {
        return getUserSession().getExtraBookId();
    }

    /**
     * 获取教师所授课本是否能布置提分宝作业与同步课堂作业
     * 1小学语数英(可以布置同步课堂) 2初中数理化（可以布置提分宝） 0其他
     *
     * @return
     */
    public Integer getTeacherPeriodType() {
        Integer type = getUserSession().getTeacherPeriodType();
        if (type == null) {
            type = new Integer(0);
        }
        return type;
    }

    /**
     * 获取教师所授课本对应科目ID
     *
     * @return
     */
    public String getTeachersSubjectId() {
        return getUserSession().getSubjectId();
    }

    /**
     * 获取教师所授课本对应科目名称
     *
     * @return
     */
    public String getTeachersSubjectName() {
        return getUserSession().getSubjectName();
    }

    /**
     * 获取教师所授课本对应科目名称
     *
     * @return
     */
    public String getIdentifyId() {
        return getUserSession().getIdentityId();
    }

    public String getPositionName() {
        return getUserSession().getPositionName();
    }

    /**
     * 获取教师任课班级
     * @return
     */
    public List<ClassInfoDTO> getClassInfo(){
        return getUserSession().getTeacherClass();
    }
}
