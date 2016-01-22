package net.xuele.cloudteach.web.controller;

import net.xuele.common.security.SessionUtil;
import net.xuele.common.security.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2015/6/22 0022.
 */
@Controller
@RequestMapping(value = "home")
public class IndexController {

    /**
     * 根据用户状态跳转页面
     *
     * @return
     */
    @RequestMapping(value = "index")
    public String index() {
        return getFirstMenu();
    }

//    @RequestMapping(value = "init")
//    public String init() {
//        UserSession user = SessionUtil.getUserSession();
//        if (user.getStatus() == UserConstants.STATUS_NORMAL) {
//            return getFirstMenu();
//        }
//        if (IdentityIdConstants.SCHOOL_MANAGER.equals(user.getIdentityId())) {
//            return "forward:/schoolManagerInit/index";
//        } else if (IdentityIdConstants.TEACHER.equals(user.getIdentityId())) {
//            return "forward:/teacherInit/index";
//        } else if (IdentityIdConstants.STUDENT.equals(user.getIdentityId())) {
//            return "forward:/studentInit/index";
//        } else if (IdentityIdConstants.PARENT.equals(user.getIdentityId())) {
//            return "forward:/parentInit/index";
//        } else if (IdentityIdConstants.EDUCATION_MANAGER.equals(user.getIdentityId())) {
//            return "forward:/eductionManagerInit/index";
//        }else {
//            throw new MemberException("其他角色初始化功能未完成。");
//        }
//    }

    private String getFirstMenu() {
        UserSession user = SessionUtil.getUserSession();
        return "redirect:" + user.getMenu().getList().get(0).getUrl();
    }
    
}
