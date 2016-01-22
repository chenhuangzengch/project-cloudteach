package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.TeacherCloudTeachViewDTO;
import net.xuele.cloudteach.dto.page.TeacherCloudTeachViewPageRequest;
import net.xuele.cloudteach.persist.TeacherCloudTeachMapper;
import net.xuele.cloudteach.service.*;
import net.xuele.cloudteach.service.util.DateTimeUtil;
import net.xuele.cloudteach.service.util.NumberFormat;
import net.xuele.cloudteach.view.TeacherCloudTeachView;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * TeacherCloudTeachServiceImpl
 * 教师云教学管理服务
 *
 * @author duzg
 * @date 2015/7/8 0002
 */
@Service
public class TeacherCloudTeachServiceImpl implements TeacherCloudTeachService {

    private static Logger logger = LoggerFactory.getLogger(TeacherCloudTeachServiceImpl.class);

    @Autowired
    private TeacherCloudTeachMapper teacherCloudTeachMapper;
    @Autowired
    TeacherWorkService teacherWorkService;
    @Autowired
    MagicWorkService magicWorkService;
    @Autowired
    SynclassWorkService synclassWorkService;
    @Autowired
    BlackboardPublishService blackboardPublishService;
    @Autowired
    CourseReappearService courseReappearService;

    @Override
    public PageResponse<TeacherCloudTeachViewDTO> queryCloudTeachList(TeacherCloudTeachViewPageRequest request) throws CloudteachException {
        String userId = request.getUserId();
        String classId = request.getClassId();
        String schoolId = request.getSchoolId();
        String unitId = request.getUnitId();
        String subjectId = request.getSubjectId();
        Integer onlyWork = request.getOnlyWork();
        Long time = Long.valueOf(request.getPublishTime());
        String publishTime = "";

        if (StringUtils.isEmpty(userId)) {
            userId = null;
        }
        if (StringUtils.isEmpty(classId)) {
            classId = null;
        }
        if (StringUtils.isEmpty(unitId)) {
            unitId = null;
        }
        if (StringUtils.isEmpty(subjectId)) {
            subjectId = null;
        }
        if (time == null || time == 0) {
            if (request.getListType() == 2) {
                //云教学首页需要获取当前日期延后60天的时间
                Date searchBegDate = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(searchBegDate);
                cal.add(Calendar.DATE, Constants.MAX_PUBLISH_WORK_EXDAYS);
                publishTime = DateTimeUtil.DateToStringForMycat(cal.getTime());
            } else {
                //其他人课堂重现、名校课堂等取当前时间
                publishTime = DateTimeUtil.DateToStringForMycat(new Date());
            }
        } else {
            publishTime = DateTimeUtil.DateToStringForMycat(new Date(time));
        }
        if (onlyWork == null || onlyWork.intValue() != 1) {
            onlyWork = new Integer(0);
        }

        //获取数据
        Page page = PageUtils.buildPage(request);
        int pagesize = request.getPageSize();
        List<TeacherCloudTeachView> cloudTeachViewlist = teacherCloudTeachMapper.queryTeacherCloudTeach(pagesize, page, userId, classId,
                publishTime, schoolId, unitId, subjectId, onlyWork.intValue());

        if (CollectionUtils.isNotEmpty(cloudTeachViewlist)) {
            logger.info("the newest cloudTask's id:" + cloudTeachViewlist.get(0).getWorkId());
        }

        //返回PageResponse
        PageResponse<TeacherCloudTeachViewDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        response.setRows(entityCloudTeachListToDtoList(cloudTeachViewlist));
        return response;
    }

    @Override
    public int delCloudTeach(String workId, String schoolId, Integer workType) throws CloudteachException {
        logger.info("删除云教学内容,workId：" + workId + ",schoolId:" + schoolId + ",workType:" + workType);
        if (null == workType) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg() + ":作业类型不正确无法删除", CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        if (workType.intValue() == 1 || workType.intValue() == 4 || workType.intValue() == 7 || workType.intValue() == 8) {
            //预习作业、电子作业、口语作业
            logger.info("删除预习作业/电子作业/口语作业/课外作业");
            teacherWorkService.delHomework(workId, schoolId);
        } else if (workType.intValue() == 2) {
            //提分宝作业
            logger.info("删除提分宝作业");
            magicWorkService.delMagicWork(workId, schoolId);
        } else if (workType.intValue() == 3) {
            //同步课堂作业
            logger.info("删除同步课堂作业");
            synclassWorkService.delSynclassWork(workId, schoolId);
        } else if (workType.intValue() == 5) {
            //网络课件
            logger.info("删除网络课件");
            courseReappearService.delCourseReappear(workId, schoolId);
        } else if (workType.intValue() == 6) {
            //板书
            logger.info("删除板书");
            blackboardPublishService.delBlackboardPublish(workId, schoolId);
        }
        return 1;
    }

    //=======================private methods================================//
    private List<TeacherCloudTeachViewDTO> entityCloudTeachListToDtoList(List<TeacherCloudTeachView> datalist) {

        List<TeacherCloudTeachViewDTO> resList = new ArrayList<TeacherCloudTeachViewDTO>();
        for (TeacherCloudTeachView objDATA : datalist) {
            //判断作业完成状态
            if (objDATA.getWorkStudentNum().intValue() == objDATA.getWorkSubStudentNum().intValue()) {
                objDATA.setFinishStatus(2);//全部完成
            } else if (objDATA.getWorkSubStudentNum().intValue() > 0) {
                objDATA.setFinishStatus(1);//部分完成
            } else {
                objDATA.setFinishStatus(0);//未完成
            }
            //判断作业批改状态
            if (objDATA.getWorkStudentNum().intValue() == objDATA.getWorkCorrectStudentNum().intValue()) {
                objDATA.setCorrectStatus(2);//全部批改
            } else if (objDATA.getWorkCorrectStudentNum().intValue() > 0) {
                objDATA.setCorrectStatus(1);//部分批改
            } else {
                objDATA.setCorrectStatus(0);//未批改
            }
            //计算参与百分比
            if (null != objDATA.getWorkStudentNum() && objDATA.getWorkStudentNum().intValue() > 0) {
                objDATA.setWorkSubStudentPect(NumberFormat.getFloatRound(100.0 * objDATA.getWorkSubStudentNum().intValue() / objDATA.getWorkStudentNum().intValue(), 0));
            } else {
                objDATA.setWorkSubStudentPect((float) 0);
            }

            TeacherCloudTeachViewDTO objDTO = new TeacherCloudTeachViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }
}