package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.AfterClassWorkViewDTO;
import net.xuele.cloudteach.dto.EffectiveWorkViewDTO;
import net.xuele.cloudteach.dto.GuidanceWorkViewDTO;
import net.xuele.cloudteach.dto.page.AfterClassWorkViewPageRequest;
import net.xuele.cloudteach.dto.page.EffectiveWorkViewPageRequest;
import net.xuele.cloudteach.dto.page.GuidanceWorkViewPageRequest;
import net.xuele.cloudteach.persist.TeacherWorkManageMapper;
import net.xuele.cloudteach.service.*;
import net.xuele.cloudteach.service.util.DateTimeUtil;
import net.xuele.cloudteach.service.util.NumberFormat;
import net.xuele.cloudteach.view.AfterClassWorkView;
import net.xuele.cloudteach.view.EffectiveWorkView;
import net.xuele.cloudteach.view.GuidanceWorkView;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

/**
 * TeacherWorkManageServiceImpl
 * 教师作业管理服务
 *
 * @author duzg
 * @date 2015/7/8 0002
 */
@Service
public class TeacherWorkManageServiceImpl implements TeacherWorkManageService {
    private static Logger logger = LoggerFactory.getLogger(TeacherWorkManageServiceImpl.class);
    @Autowired
    private TeacherWorkManageMapper teacherWorkManageMapper;
    @Autowired
    private WorkStatisticsService workStatisticsService;
    @Autowired
    private MagicWorkService magicWorkService;
    @Autowired
    private SynclassWorkService synclassWorkService;
    @Autowired
    private TeacherWorkService teacherWorkService;
    @Autowired
    private TeacherCloudTeachService teacherCloudTeachService;

    @Override
    public PageResponse<EffectiveWorkViewDTO> queryEffectiveWork(EffectiveWorkViewPageRequest request) throws CloudteachException {

        String userId = request.getUserId();
        String classId = request.getClassId();
        String subjectId = request.getSubjectId();
        String workType = request.getWorkType();
        String schoolId = request.getSchoolId();
        if (request.getPublishTime() == null) {
            request.setPublishTime("0");
        }
        Long time = Long.valueOf(request.getPublishTime());
        logger.info("获取教师布置的所有进行中的作业信息,userId：" + userId + ",classId:" + classId + ",subjectId:" + subjectId + ",workType:" + workType + ",schoolId:" + schoolId + ",publishTime:" + request.getPublishTime());
        String publishTime = "";

        if (StringUtils.isEmpty(userId)) {
            throw new CloudteachException(CloudTeachErrorEnum.USERIDNOTNULL.getMsg(), CloudTeachErrorEnum.USERIDNOTNULL.getCode());
        }
        if (classId != null && "".equals(classId)) {
            classId = null;
        }
        if (subjectId != null && "".equals(subjectId)) {
            subjectId = null;
        }
        if (workType != null && "".equals(workType)) {
            workType = null;
        }
        if (time == null || time == 0) {
            Date searchBegDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(searchBegDate);
            cal.add(Calendar.DATE, Constants.MAX_PUBLISH_WORK_EXDAYS);
            publishTime = DateTimeUtil.DateToStringForMycat(cal.getTime());
        } else {
            publishTime = DateTimeUtil.DateToStringForMycat(new Date(time));
        }

        logger.info("分页获取教师作业管理进行中作业数据");
        Page page = PageUtils.buildPage(request);
        int pagesize = request.getPageSize();
        List<EffectiveWorkView> effectiveWorkViewlist = teacherWorkManageMapper.queryEffectiveWork(pagesize, page, userId, classId, subjectId, workType, schoolId, publishTime);

        //返回PageResponse
        PageResponse<EffectiveWorkViewDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        response.setRows(entityEffectiveListToDtoList(effectiveWorkViewlist));
        return response;
    }

    @Override
    public PageResponse<AfterClassWorkViewDTO> queryAfterClassWork(AfterClassWorkViewPageRequest request) throws CloudteachException {
        String userId = request.getUserId();
        String classId = request.getClassId();
        String subjectId = request.getSubjectId();
        String workType = request.getWorkType();
        String schoolId = request.getSchoolId();
        if (StringUtils.isEmpty(request.getPublishTime())) {
            request.setPublishTime("0");
        }
        Long time = Long.valueOf(request.getPublishTime());
        logger.info("获取教师布置的作业信息,userId：" + userId + ",classId:" + classId + ",subjectId:" + subjectId + ",workType:" + workType + ",schoolId:" + schoolId + ",publishTime:" + request.getPublishTime());
        String publishTime = "";

        if (StringUtils.isEmpty(userId)) {
            throw new CloudteachException(CloudTeachErrorEnum.USERIDNOTNULL.getMsg(), CloudTeachErrorEnum.USERIDNOTNULL.getCode());
        }
        if (classId != null && "".equals(classId)) {
            classId = null;
        }
        if (subjectId != null && "".equals(subjectId)) {
            subjectId = null;
        }
        if (workType != null && "".equals(workType)) {
            workType = null;
        }
        if (time == null || time == 0) {
            Date searchBegDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(searchBegDate);
            cal.add(Calendar.DATE, Constants.MAX_PUBLISH_WORK_EXDAYS);
            publishTime = DateTimeUtil.DateToStringForMycat(cal.getTime());
        } else {
            publishTime = DateTimeUtil.DateToStringForMycat(new Date(time));
        }

        logger.info("分页获取教师作业管理课后作业数据");
        Page page = PageUtils.buildPage(request);
        int pagesize = request.getPageSize();
        List<AfterClassWorkView> afterClassWorkViewlist = teacherWorkManageMapper.queryAfterClassWork(pagesize, page, userId, classId, subjectId, workType, schoolId, publishTime);

        //返回PageResponse
        PageResponse<AfterClassWorkViewDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        response.setRows(entityAfterClassListToDtoList(afterClassWorkViewlist));
        return response;
    }

    @Override
    public PageResponse<GuidanceWorkViewDTO> queryGuidanceWork(GuidanceWorkViewPageRequest request) throws CloudteachException {
        String userId = request.getUserId();
        String classId = request.getClassId();
        String subjectId = request.getSubjectId();
        String schoolId = request.getSchoolId();
        if (StringUtils.isEmpty(request.getPublishTime())) {
            request.setPublishTime("0");
        }
        Long time = Long.valueOf(request.getPublishTime());
        logger.info("获取布置的课外作业信息,userId：" + userId + ",classId:" + classId + ",subjectId:" + subjectId + ",schoolId:" + schoolId + ",publishTime:" + request.getPublishTime());
        String publishTime = "";

        if (StringUtils.isEmpty(userId)) {
            throw new CloudteachException(CloudTeachErrorEnum.USERIDNOTNULL.getMsg(), CloudTeachErrorEnum.USERIDNOTNULL.getCode());
        }
        if (classId != null && "".equals(classId)) {
            classId = null;
        }
        if (subjectId != null && "".equals(subjectId)) {
            subjectId = null;
        }
        if (time == null || time == 0) {
            Date searchBegDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(searchBegDate);
            cal.add(Calendar.DATE, Constants.MAX_PUBLISH_WORK_EXDAYS);
            publishTime = DateTimeUtil.DateToStringForMycat(cal.getTime());
        } else {
            publishTime = DateTimeUtil.DateToStringForMycat(new Date(time));
        }

        logger.info("分页获取课外作业信息:");
        Page page = PageUtils.buildPage(request);
        int pagesize = request.getPageSize();
        List<GuidanceWorkView> guidanceWorkViewlist = teacherWorkManageMapper.queryGuidanceWork(pagesize, page, userId, classId, subjectId, schoolId, publishTime);

        //返回PageResponse
        PageResponse<GuidanceWorkViewDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        response.setRows(entityGuidanceListToDtoList(guidanceWorkViewlist));
        return response;
    }

    /**
     * @param request
     * @return
     * @throws CloudteachException
     */
    @Override
    public PageResponse<AfterClassWorkViewDTO> queryExtraWork(AfterClassWorkViewPageRequest request) throws CloudteachException {
        String userId = request.getUserId();
        String classId = request.getClassId();
        String schoolId = request.getSchoolId();
        if (StringUtils.isEmpty(request.getPublishTime())) {
            request.setPublishTime("0");
        }
        Long time = Long.valueOf(request.getPublishTime());
        logger.info("获取教师布置的作业信息,userId：" + userId + ",classId:" + classId + ",schoolId:" + schoolId + ",publishTime:" + request.getPublishTime());
        String publishTime = "";

        if (StringUtils.isEmpty(userId)) {
            throw new CloudteachException(CloudTeachErrorEnum.USERIDNOTNULL.getMsg(), CloudTeachErrorEnum.USERIDNOTNULL.getCode());
        }
        if (classId != null && "".equals(classId)) {
            classId = null;
        }

        if (time == null || time == 0) {
            Date searchBegDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(searchBegDate);
            cal.add(Calendar.DATE, Constants.MAX_PUBLISH_WORK_EXDAYS);
            publishTime = DateTimeUtil.DateToStringForMycat(cal.getTime());
        } else {
            publishTime = DateTimeUtil.DateToStringForMycat(new Date(time));
        }

        logger.info("分页获取教师作业管理课后作业数据");
        Page page = PageUtils.buildPage(request);
        int pagesize = request.getPageSize();
        //long record = teacherWorkManageMapper.selectExtraWorkListCount(userId,classId,schoolId);
        List<AfterClassWorkView> afterClassWorkViewlist = teacherWorkManageMapper.selectExtraWorkList(pagesize, page, userId, classId, schoolId, publishTime);

        //返回PageResponse
        PageResponse<AfterClassWorkViewDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        //response.setRecords(record);
        response.setRows(entityAfterClassListToDtoList(afterClassWorkViewlist));
        return response;
    }

    /**
     * @param workId       作业ID
     * @param workType     作业类型：1预习作业，2提分神器作业，3同步课堂作业，4电子作业 7口语作业
     * @param context      评语
     * @param praiseStatus 鼓励状态：0不鼓励 1鼓励
     * @param score        评分
     * @param userId       教师用户号
     * @param schoolId     学校ID
     * @return PageResponse<GudanceWorkViewDTO>
     * 对作业ID对应的作业进行快速批改
     */
    public void quicklyCorrect(String workId, Integer workType, String context, Integer praiseStatus, Integer score, String userId, String schoolId) throws CloudteachException {
        logger.info("作业快速批改,workId：" + workId + ",workType:" + workType + ",context:" + context + ",praiseStatus:" + praiseStatus + ",score:" + score + ",userId:" + userId + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(workId)) {
            //作业号为空无法快速批改
            throw new CloudteachException(CloudTeachErrorEnum.WORKIDNOTNULL.getMsg() + ":作业ID为空无法进行快速批改", CloudTeachErrorEnum.WORKIDNOTNULL.getCode());
        }
        if (workType == null && workType.intValue() != 1 && workType.intValue() != 2
                && workType.intValue() != 3 && workType.intValue() != 4 && workType.intValue() != 7 && workType.intValue() != 8) {
            //作业类型错误
            throw new CloudteachException(CloudTeachErrorEnum.WORKTYPEERROR.getMsg() + ":该作业类型不支持快速批改", CloudTeachErrorEnum.WORKTYPEERROR.getCode());
        }
        //根据作业类型快速批改workId对应的作业
        if (1 == workType || 4 == workType || 7 == workType || 8 == workType) {
            //预习作业、电子作业、口语作业
            logger.info("预习作业/电子作业/口语作业 快速批改");
            teacherWorkService.quicklyCorrect(workId, context, praiseStatus, score, userId, schoolId);
        } else if (2 == workType) {
            //提分宝作业
            logger.info("提分宝作业 快速批改");
            magicWorkService.quicklyCorrect(workId, context, praiseStatus, userId, schoolId);
        } else if (3 == workType) {
            //同步课堂作业
            logger.info("同步课堂作业 快速批改");
            synclassWorkService.quicklyCorrect(workId, context, praiseStatus, userId, schoolId);
        }
    }

    /**
     * @param workjson     json格式参数包含了workId、workType（例如：[{"0001":1},{"0002":2},{"0003":3}]）
     * @param context      评语
     * @param praiseStatus 鼓励状态：0不鼓励 1鼓励
     * @param userId       教师用户号
     * @throws net.xuele.common.exceptions.CloudteachException 全部快速批改，如果workjson没有信息则默认快速批改userId下所有进行中的作业
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    @Override
    public String quicklyAllCorrect(String workjson, String context, Integer praiseStatus, String userId, String schoolId) throws CloudteachException, IOException {
        logger.info("作业全部快速批改,workjson：" + workjson + ",context:" + context + ",praiseStatus:" + praiseStatus + ",userId:" + userId + ",schoolId:" + schoolId);
        //记录操作失败的wordId
        StringBuffer failWorkIds = new StringBuffer();

        if (!StringUtils.isEmpty(workjson)) {
            logger.info("快速批改指定的作业集合");
            ObjectMapper objectMapper = new ObjectMapper();
            List<LinkedHashMap<String, Integer>> workIdList = new ArrayList<>();
            try {
                workIdList = objectMapper.readValue(workjson, List.class);
            } catch (Exception e) {
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":workjson参数不正确,无法进行批量作业快速批改", CloudTeachErrorEnum.PARAMERROR.getCode());
            }

            for (Map<String, Integer> map : workIdList) {
                Set<String> set = map.keySet();
                for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
                    String workId = it.next();
                    Integer workType = map.get(workId);
                    try {
                        this.quicklyCorrect(workId, workType, context, praiseStatus, 0, userId, schoolId);
                    } catch (CloudteachException e) {
                        //记录操作失败的workId
                        failWorkIds.append(workId + "|");
                    }
                }
            }
        } else {
            logger.info("快速批改教师所有进行中的作业");
            //查找该教师所有进行中的作业
            List<EffectiveWorkView> allEffectiveWorkList = teacherWorkManageMapper.queryAllEffectiveWorkByUserId(userId, schoolId);
            for (EffectiveWorkView effectiveWork : allEffectiveWorkList) {
                this.quicklyCorrect(effectiveWork.getWorkId(), effectiveWork.getWorkType(), context, praiseStatus, 0, userId, schoolId);
            }
        }

        return failWorkIds.toString();
    }

    /**
     * @param workId   作业ID
     * @param workType 作业类型：1预习作业，2提分神器作业，3同步课堂作业，4电子作业，5网络课件，6板书，7口语作业
     * @param userId   教师用户号
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据作业ID逻辑删除对应的作业信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    @Override
    public void delWork(String workId, Integer workType, String userId, String schoolId) throws CloudteachException {
        logger.info("教师删除作业,workId：" + workId + ",workType:" + workType + ",userId:" + userId + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(workId)) {
            //作业号为空无法删除
            throw new CloudteachException(CloudTeachErrorEnum.WORKIDNOTNULL.getMsg() + ":作业ID为空无法删除作业", CloudTeachErrorEnum.WORKIDNOTNULL.getCode());
        }
        if (workType == null || workType.intValue() <= 0 || workType.intValue() > 8) {
            //作业类型错误
            throw new CloudteachException(CloudTeachErrorEnum.WORKTYPEERROR.getMsg() + ":作业类型不正确无法删除作业", CloudTeachErrorEnum.WORKTYPEERROR.getCode());
        }

        teacherCloudTeachService.delCloudTeach(workId, schoolId, workType);
    }

    /**
     * @param answerId 作业学生回答ID
     * @param workType 作业类型：1预习作业，2提分神器作业，3同步课堂作业，4电子作业,7口语作业
     * @param userId   教师用户号
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 删除answerId对应的学生作业，有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    @Override
    public void delStuWork(String answerId, Integer workType, String userId, String schoolId) throws CloudteachException {
        logger.info("教师删除单个学生作业,answerId：" + answerId + ",workType:" + workType + ",userId:" + userId + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(answerId)) {
            //作业学生回答ID为空无法删除
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "学生回答ID为空无法删除学生提交的作业", CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if (workType == null || workType.intValue() <= 0 || workType.intValue() > 8) {
            //作业类型错误
            throw new CloudteachException(CloudTeachErrorEnum.WORKTYPEERROR.getMsg() + "作业类型不正确无法删除学生提交的作业", CloudTeachErrorEnum.WORKTYPEERROR.getCode());
        }
        if (1 == workType.intValue() || 4 == workType.intValue() || 7 == workType.intValue() || 8 == workType.intValue()) {
            //预习作业、电子作业、口语作业、课外作业
            logger.info("删除学生提交的预习作业/电子作业/口语作业/课外作业");
            teacherWorkService.delStuWork(answerId, userId, schoolId);
        } else if (2 == workType.intValue()) {
            //提分宝作业
            logger.info("删除学生提交的提分宝作业");
            magicWorkService.delStuAnswer(userId, answerId, schoolId);
        } else if (3 == workType.intValue()) {
            //同步课堂作业
            logger.info("删除学生提交的同步课堂作业");
            synclassWorkService.delSynclassStuWork(userId, answerId, schoolId);
        }
    }

    //=======================private methods================================//
    private List<EffectiveWorkViewDTO> entityEffectiveListToDtoList(List<EffectiveWorkView> datalist) {

        List<EffectiveWorkViewDTO> resList = new ArrayList<EffectiveWorkViewDTO>();
        for (EffectiveWorkView objDATA : datalist) {

            //计算参与百分比
            if (null != objDATA.getWorkStudentNum() && objDATA.getWorkStudentNum().intValue() > 0) {
                objDATA.setWorkSubStudentPect(NumberFormat.getFloatRound(100.0 * objDATA.getWorkSubStudentNum().intValue() / objDATA.getWorkStudentNum().intValue(), 0));
            } else {
                objDATA.setWorkSubStudentPect((float) 0);
            }
            if (objDATA.getWorkSubStudentNum().intValue() == objDATA.getWorkStudentNum().intValue()) {
                objDATA.setFinishStatus(2);
            } else if (objDATA.getWorkSubStudentNum().intValue() > 0) {
                objDATA.setFinishStatus(1);
            } else {
                objDATA.setFinishStatus(0);
            }
            if (objDATA.getWorkCorrectStudentNum().intValue() == objDATA.getWorkStudentNum().intValue()) {
                objDATA.setCorrectStatus(2);
            } else if (objDATA.getWorkCorrectStudentNum().intValue() > 0) {
                objDATA.setCorrectStatus(1);
            } else {
                objDATA.setCorrectStatus(0);
            }
            EffectiveWorkViewDTO objDTO = new EffectiveWorkViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }

    private List<AfterClassWorkViewDTO> entityAfterClassListToDtoList(List<AfterClassWorkView> datalist) {

        List<AfterClassWorkViewDTO> resList = new ArrayList<>();
        for (AfterClassWorkView objDATA : datalist) {
            //计算参与百分比
            if (null != objDATA.getWorkStudentNum() && objDATA.getWorkStudentNum().intValue() > 0) {
                objDATA.setWorkSubStudentPect(NumberFormat.getFloatRound(100.0 * objDATA.getWorkSubStudentNum().intValue() / objDATA.getWorkStudentNum().intValue(), 0));
            } else {
                objDATA.setWorkSubStudentPect((float) 0);
            }
            if (objDATA.getWorkSubStudentNum().intValue() == objDATA.getWorkStudentNum().intValue()) {
                objDATA.setFinishStatus(2);
            } else if (objDATA.getWorkSubStudentNum().intValue() > 0) {
                objDATA.setFinishStatus(1);
            } else {
                objDATA.setFinishStatus(0);
            }
            if (objDATA.getWorkCorrectStudentNum().intValue() == objDATA.getWorkStudentNum().intValue()) {
                objDATA.setCorrectStatus(2);
            } else if (objDATA.getWorkCorrectStudentNum().intValue() > 0) {
                objDATA.setCorrectStatus(1);
            } else {
                objDATA.setCorrectStatus(0);
            }
            AfterClassWorkViewDTO objDTO = new AfterClassWorkViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }

    private List<GuidanceWorkViewDTO> entityGuidanceListToDtoList(List<GuidanceWorkView> datalist) {

        List<GuidanceWorkViewDTO> resList = new ArrayList<>();
        for (GuidanceWorkView objDATA : datalist) {
            //计算参与百分比
            if (null != objDATA.getWorkStudentNum() && objDATA.getWorkStudentNum().intValue() > 0) {
                objDATA.setWorkSubStudentPect(NumberFormat.getFloatRound(100.0 * objDATA.getWorkSubStudentNum().intValue() / objDATA.getWorkStudentNum().intValue(), 0));
            } else {
                objDATA.setWorkSubStudentPect((float) 0);
            }
            if (objDATA.getWorkSubStudentNum().intValue() == objDATA.getWorkStudentNum().intValue()) {
                objDATA.setFinishStatus(2);
            } else if (objDATA.getWorkSubStudentNum().intValue() > 0) {
                objDATA.setFinishStatus(1);
            } else {
                objDATA.setFinishStatus(0);
            }
            if (objDATA.getWorkCorrectStudentNum().intValue() == objDATA.getWorkStudentNum().intValue()) {
                objDATA.setCorrectStatus(2);
            } else if (objDATA.getWorkCorrectStudentNum().intValue() > 0) {
                objDATA.setCorrectStatus(1);
            } else {
                objDATA.setCorrectStatus(0);
            }
            GuidanceWorkViewDTO objDTO = new GuidanceWorkViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }
}