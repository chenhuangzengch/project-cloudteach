package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.web.form.OnlineTeachParam;
import net.xuele.cloudteach.web.wrapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2015/6/15 0015.
 * 详细文档：confluence
 * http://192.168.1.249:8081/pages/viewpage.action?pageId=1770001
 *
 */

@Controller
@RequestMapping(value = "onlineTeach")
public class OnlineTeachController {

    private static Logger logger = LoggerFactory.getLogger(OnlineTeachController.class);

    /**
     * 展示flash资源页
     * @param   【请求参数】
     * @return
     * 获取该课程所有的资源信息
     */

    @RequestMapping(value = "queryAllResource")
    public String queryAllResource() {
        return "onlineTeach/queryAllResource";
    }

    /**
     * 获得教师所在班级
     * @return
     */
    @RequestMapping(value = "getTeacherClass")
    @ResponseBody
    public TeacherClassWrapper getTeacherClass(OnlineTeachParam param){
        TeacherClassWrapper wrapper = new TeacherClassWrapper();
        wrapper.setState(1);
        return wrapper;
    }


    /**
     * 获取所有资源信息
     * @param param
     * @return
     */
    @RequestMapping(value = "getFileByUnitCodeByWebC")
    @ResponseBody
    public ResourceWrapper getFileByUnitCodeByWebC(OnlineTeachParam param){
        ResourceWrapper wrapper = new ResourceWrapper();
        wrapper.setState(1);
        return wrapper;
    }


    /**
     * 获得电子课件信息
     * http://cloudteach.app.xuele.net:8080/cloudteach-web/onlineTeach/coursewareGetByID
     * response:{"bg":1,"style":1,"resources":[],"name":"","pages":[[]]}
     */

    @RequestMapping(value = "coursewareGetByID")
    @ResponseBody
    public CoursewareWrapper coursewareGetById(OnlineTeachParam param){
        CoursewareWrapper wrapper = new CoursewareWrapper();
        return wrapper;
    }


    /**
     * 获得分享的电子课本信息
     * http://cloudteach.app.xuele.net:8080/cloudteach-web/onlineTeach/coursewareGetByShare
     * response:{"bg":1,"style":1,"resources":[],"name":"","pages":[[]]}
     */

    @RequestMapping(value = "coursewareGetByShare")
    @ResponseBody
    public CoursewareWrapper coursewareGetByShare(OnlineTeachParam param){
        CoursewareWrapper wrapper = new CoursewareWrapper();
        return wrapper;
    }


    /**
     * 获得电子课本信息
     * @param param
     * @return
     */
    @RequestMapping(value = "getEBookByCoursewareId")
    @ResponseBody
    public EBookWrapper getEBookByCoursewareId(OnlineTeachParam param){
        EBookWrapper wrapper = new EBookWrapper();
        return wrapper;
    }



    /**
     * 自动保存电子课本信息
     * @param param
     * @return
     */
    @RequestMapping(value = "coursewareSetTemp")
    @ResponseBody
    public ModifyWrapper coursewareSetTemp(OnlineTeachParam param){
        ModifyWrapper wrapper = new ModifyWrapper();
        return wrapper;
    }


    /**
     * 保存电子课本信息
     * @param param
     * @return
     */
    @RequestMapping(value = "coursewareEdit")
    @ResponseBody
    public ModifyWrapper coursewareEdit(OnlineTeachParam param){
        ModifyWrapper wrapper = new ModifyWrapper();
        return wrapper;
    }


    /**
     * 获得电子作业信息
     * @param param
     * @return
     */
    @RequestMapping(value = "getSSQuanByUserIDClasss")
    @ResponseBody
    public ElectronicJobWrapper getElectronicJobByUserIDClasss(OnlineTeachParam param){
        ElectronicJobWrapper wrapper = new ElectronicJobWrapper();
        return wrapper;
    }



    /**
     * 获得讨论作业信息
     * @param param
     * @return
     */
    @RequestMapping(value = "getGLDListByUserClass")
    @ResponseBody
    public DiscussJobWrapper getDiscussJobByCoursewareId(OnlineTeachParam param){
        DiscussJobWrapper wrapper = new DiscussJobWrapper();
        return wrapper;
    }


    //==================================================================================================



}
