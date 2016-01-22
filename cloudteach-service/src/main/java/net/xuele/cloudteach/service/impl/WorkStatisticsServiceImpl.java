package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.CtWorkClassGather;
import net.xuele.cloudteach.domain.CtWorkGather;
import net.xuele.cloudteach.domain.CtWorkStatistics;
import net.xuele.cloudteach.domain.CtWorkStudentGather;
import net.xuele.cloudteach.dto.SubjectGatherViewDTO;
import net.xuele.cloudteach.dto.WorkStatisticsDTO;
import net.xuele.cloudteach.dto.WorkStatisticsViewDTO;
import net.xuele.cloudteach.dto.WorkStudentGatherDTO;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.WorkStatisticsService;
import net.xuele.cloudteach.service.util.NumberFormat;
import net.xuele.cloudteach.view.SubjectGatherView;
import net.xuele.cloudteach.view.WorkStatisticsView;
import net.xuele.common.exceptions.CloudteachException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * WorkStatisticsServiceImpl
 * 作业汇总统计服务
 *
 * @author duzg
 * @date 2015/7/8 0002
 */
@Service
public class WorkStatisticsServiceImpl implements WorkStatisticsService {

    private static Logger logger = LoggerFactory.getLogger(WorkStatisticsServiceImpl.class);

    @Autowired
    private CtWorkStatisticsMapper ctWorkStatisticsMapper;
    @Autowired
    CtWorkGatherMapper ctWorkGatherMapper;
    @Autowired
    CtWorkStudentGatherMapper ctWorkStudentGatherMapper;
    @Autowired
    CtWorkClassGatherMapper ctWorkClassGatherMapper;
    @Autowired
    StudentWorkListMapper studentWorkListMapper;
    @Autowired
    CtTeacherWorkItemAnswerMapper ctTeacherWorkItemAnswerMapper;
    @Autowired
    CtMagicWorkAnswerMapper ctMagicWorkAnswerMapper;
    @Autowired
    CtSynclassWorkStudentMapper ctSynclassWorkStudentMapper;

    @Override
    public WorkStatisticsDTO queryWorkStatisticsByWorkId(String workId, String schoolId) throws CloudteachException {
        CtWorkStatistics ctWorkStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(workId, schoolId);
        if (ctWorkStatistics == null) {
            return null;
        }
        WorkStatisticsDTO workStatisticsDTO = new WorkStatisticsDTO();
        BeanUtils.copyProperties(ctWorkStatistics, workStatisticsDTO);
        return workStatisticsDTO;
    }

    @Override
    public int updateWorkStatistics(WorkStatisticsDTO workStatisticsDTO) throws CloudteachException {
        if (workStatisticsDTO == null) {
            return 0;
        }
        CtWorkStatistics ctWorkStatistics = new CtWorkStatistics();
        BeanUtils.copyProperties(workStatisticsDTO, ctWorkStatistics);
        return ctWorkStatisticsMapper.updateByPrimaryKey(ctWorkStatistics);
    }

    @Override
    public int addWorkStatistics(WorkStatisticsDTO workStatisticsDTO) throws CloudteachException {
        if (workStatisticsDTO == null) {
            return 0;
        }
        CtWorkStatistics ctWorkStatistics = new CtWorkStatistics();
        BeanUtils.copyProperties(workStatisticsDTO, ctWorkStatistics);
        return ctWorkStatisticsMapper.insert(ctWorkStatistics);
    }

    /**
     * 修改作业统计信息中的已批改学生数
     *
     * @param workId              作业ID
     * @param schoolId            学校ID
     * @param unCorrectStudentNum 未批改学生数
     * @return int
     * @throws net.xuele.common.exceptions.CloudteachException
     */
    @Override
    public void updateCorrectStudentNum(String workId, String schoolId, int unCorrectStudentNum) throws CloudteachException {
        CtWorkGather ctWorkGather = ctWorkGatherMapper.selectByPrimaryKey(workId, schoolId);
        if (ctWorkGather == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        int tryTimes = 1;
        int i = 0;
        while (i <= 0 && tryTimes <= Constants.OPTIMISTIC_LOCK_TRY_TIMES) {
            CtWorkStatistics ctWorkStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(workId, schoolId);
            if (ctWorkStatistics == null) {
                logger.info("work Id:" + workId + " has not work Statistics");
                throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
            }
            ctWorkStatistics.setWorkCorrectStudentNum(ctWorkGather.getWorkStudentNum().intValue() - unCorrectStudentNum);
            i = ctWorkStatisticsMapper.updateByPrimaryKey(ctWorkStatistics);
            tryTimes++;
        }
        if (tryTimes > Constants.OPTIMISTIC_LOCK_TRY_TIMES) {
            //超过乐观锁最大尝试次数，更新失败
            throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
        }
    }

    /**
     * @param workId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    @Override
    public int updateViewStudentNum(String workId, String schoolId) throws CloudteachException {
        int tryTimes = 1;
        int i = 0;
        CtWorkStatistics ctWorkStatistics = new CtWorkStatistics();
        while (i <= 0 && tryTimes <= Constants.OPTIMISTIC_LOCK_TRY_TIMES) {
            ctWorkStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(workId, schoolId);
            if (ctWorkStatistics == null) {
                throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
            }
            ctWorkStatistics.setWorkViewStudentNum(ctWorkStatistics.getWorkViewStudentNum() + 1);
            i = ctWorkStatisticsMapper.updateByPrimaryKey(ctWorkStatistics);
            tryTimes++;
        }
        if (tryTimes > Constants.OPTIMISTIC_LOCK_TRY_TIMES) {
            //超过乐观锁最大尝试次数，更新失败
            throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
        }
        return 1;
    }

    /**
     * 更新已提交学生数（用于单个学生提交作业后）
     *
     * @param workId
     * @param schoolId
     * @throws CloudteachException
     */
    @Override
    public int updateSubmitStudentNum(String workId, String schoolId) throws CloudteachException {
        CtWorkGather ctWorkGather = ctWorkGatherMapper.selectByPrimaryKey(workId, schoolId);
        if (ctWorkGather == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        List<CtWorkStudentGather> studentGatherList = ctWorkStudentGatherMapper.getWorkStudentGatherByWorkId(workId, schoolId);
        if (CollectionUtils.isEmpty(studentGatherList)) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        int tryTimes = 1;
        int i = 0;
        CtWorkStatistics ctWorkStatistics = new CtWorkStatistics();
        while (i <= 0 && tryTimes <= Constants.OPTIMISTIC_LOCK_TRY_TIMES) {
            ctWorkStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(workId, schoolId);
            if (ctWorkStatistics == null) {
                throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
            }
            int newSubStudentNum = ctWorkStatistics.getWorkSubStudentNum() + 1;
            // 提交学生人数，不能超过作业学生总人数
            if (newSubStudentNum > studentGatherList.size()) {
                newSubStudentNum = studentGatherList.size();
            }
            ctWorkStatistics.setWorkSubStudentNum(newSubStudentNum);
            i = ctWorkStatisticsMapper.updateByPrimaryKey(ctWorkStatistics);
            tryTimes++;
        }
        if (tryTimes > Constants.OPTIMISTIC_LOCK_TRY_TIMES) {
            //超过乐观锁最大尝试次数，更新失败
            throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
        }
        return ctWorkGather.getWorkStudentNum().intValue() - ctWorkStatistics.getWorkSubStudentNum();
    }

    /**
     * 更新已提交学生数与已批改学生数（用于教师删除单个学生作业后）
     *
     * @param workStudentGatherDTO 被删除学生作业汇总信息
     * @throws CloudteachException
     */
    public void updateByDelStuWork(WorkStudentGatherDTO workStudentGatherDTO) throws CloudteachException {
        logger.info("删除学生作业后修改作业汇总信息");
        if (workStudentGatherDTO == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        int tryTimes = 1;
        int i = 0;
        while (i <= 0 && tryTimes <= Constants.OPTIMISTIC_LOCK_TRY_TIMES) {
            CtWorkStatistics ctWorkStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(workStudentGatherDTO.getWorkId(), workStudentGatherDTO.getSchoolId());
            if (ctWorkStatistics == null) {
                throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
            }
            if (ctWorkStatistics.getWorkSubStudentNum().intValue() > 0) {
                ctWorkStatistics.setWorkSubStudentNum(ctWorkStatistics.getWorkSubStudentNum() - 1);
            }
            if (workStudentGatherDTO.getCorrectStatus() != null && workStudentGatherDTO.getCorrectStatus().intValue() == 1) {
                //如果是已批改则批改人数-1
                if (ctWorkStatistics.getWorkCorrectStudentNum().intValue() > 0) {
                    ctWorkStatistics.setWorkCorrectStudentNum(ctWorkStatistics.getWorkCorrectStudentNum() - 1);
                }
            }
            i = ctWorkStatisticsMapper.updateByPrimaryKey(ctWorkStatistics);
            tryTimes++;
        }
        if (tryTimes > Constants.OPTIMISTIC_LOCK_TRY_TIMES) {
            //超过乐观锁最大尝试次数，更新失败
            throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
        }
    }

    /**
     * 获取教师已布置作业的所有科目类型
     *
     * @param userId   教师用户ID
     * @param schoolId 学校ID
     * @return
     */
    @Override
    public List<SubjectGatherViewDTO> queryTeacherWorkSubjectList(String userId, String schoolId) throws CloudteachException {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        List<SubjectGatherView> subjectViewList = ctWorkGatherMapper.queryTeacherWorkSubjectList(userId, schoolId);
        if (subjectViewList == null) {
            return null;
        }
        List<SubjectGatherViewDTO> subjectViewDTOList = new ArrayList<>();

        for (SubjectGatherView subjectGatherView : subjectViewList) {
            SubjectGatherViewDTO subjectGatherViewDTO = new SubjectGatherViewDTO();
            BeanUtils.copyProperties(subjectGatherView, subjectGatherViewDTO);
            subjectViewDTOList.add(subjectGatherViewDTO);
        }

        return subjectViewDTOList;
    }

    /**
     * 获取班级对应已布置作业的所有科目类型
     *
     * @param classId  班级ID
     * @param schoolId 学校ID
     * @return
     */
    @Override
    public List<SubjectGatherViewDTO> queryClassWorkSubjectList(String classId, String schoolId) throws CloudteachException {
        if (StringUtils.isEmpty(classId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        List<SubjectGatherView> subjectViewList = ctWorkGatherMapper.queryClassWorkSubjectList(classId, schoolId);
        if (subjectViewList == null) {
            return null;
        }
        List<SubjectGatherViewDTO> subjectViewDTOList = new ArrayList<>();

        for (SubjectGatherView subjectGatherView : subjectViewList) {
            SubjectGatherViewDTO subjectGatherViewDTO = new SubjectGatherViewDTO();
            BeanUtils.copyProperties(subjectGatherView, subjectGatherViewDTO);
            subjectViewDTOList.add(subjectGatherViewDTO);
        }

        return subjectViewDTOList;
    }

    /**
     * 获取学生对应已布置作业的所有科目类型
     *
     * @param studentId 学生用户ID
     * @param schoolId  学校ID
     * @return
     */
    @Override
    public List<SubjectGatherViewDTO> queryStudentWorkSubjectList(String studentId, String schoolId) throws CloudteachException {
        if (StringUtils.isEmpty(studentId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        List<SubjectGatherView> subjectViewList = ctWorkGatherMapper.queryStudentWorkSubjectList(studentId, schoolId);
        if (subjectViewList == null) {
            return null;
        }
        List<SubjectGatherViewDTO> subjectViewDTOList = new ArrayList<>();

        for (SubjectGatherView subjectGatherView : subjectViewList) {
            SubjectGatherViewDTO subjectGatherViewDTO = new SubjectGatherViewDTO();
            BeanUtils.copyProperties(subjectGatherView, subjectGatherViewDTO);
            subjectViewDTOList.add(subjectGatherViewDTO);
        }

        return subjectViewDTOList;
    }

    @Override
    public WorkStatisticsViewDTO selectWorkStatistics(String workId, String schoolId) {
        WorkStatisticsView workStatisticsView = ctWorkStatisticsMapper.selectWorkStatistics(workId, schoolId);
        if (workStatisticsView == null) {
            return null;
        }
        WorkStatisticsViewDTO workStatisticsViewDTO = new WorkStatisticsViewDTO();
        BeanUtils.copyProperties(workStatisticsView, workStatisticsViewDTO);
        //计算参与百分比
        if (null != workStatisticsViewDTO.getWorkStudentNum() && workStatisticsViewDTO.getWorkStudentNum().intValue() > 0) {
            workStatisticsViewDTO.setWorkSubStudentPect(NumberFormat.getFloatRound(100 * workStatisticsViewDTO.getWorkSubStudentNum().intValue() / workStatisticsViewDTO.getWorkStudentNum().intValue(), 2));
        } else {
            workStatisticsViewDTO.setWorkSubStudentPect((float) 0);
        }
        return workStatisticsViewDTO;
    }

    @Override
    public int getWorkClassStudentNum(String workId, String classId, String schoolId) {
        return ctWorkStudentGatherMapper.getWorkClassStudentNum(workId, classId, schoolId);
    }

    /**
     * @param workId   作业Id
     * @param classId  班级ID
     * @param schoolId 学校ID
     * @return 0提醒太频繁  1操作成功
     * 记录教师对某个作业最后一次发送提醒通知的时间
     */
    @Override
    public int updateLastWarnTime(String workId, String classId, String schoolId) throws CloudteachException {
        logger.info("验证发送提醒交作业通知时间是否受限制");
        List<CtWorkClassGather> classList = ctWorkClassGatherMapper.getWorkClassGatherByClassId(workId, classId, schoolId);
        if (classList == null || classList.size() == 0) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg() + ":作业班级汇总信息不存在，无法发送通知", CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        if (classList.size() > 1) {
            throw new CloudteachException(CloudTeachErrorEnum.DATAERROR.getMsg() + ":作业班级汇总信息不正确，无法发送通知", CloudTeachErrorEnum.DATAERROR.getCode());
        }
        CtWorkClassGather ctWorkClassGather = classList.get(0);
        if (ctWorkClassGather.getLastWarnTime() != null) {
            Date nowtime = new Date();
            if ((nowtime.getTime() - ctWorkClassGather.getLastWarnTime().getTime()) / 1000 < Constants.MIN_SECONDS_WARNSUB) {
                return 0;
            }
        }
        logger.info("记录最新一次发送提醒交作业通知时间");
        ctWorkClassGather.setLastWarnTime(new Date());
        ctWorkClassGatherMapper.updateByPrimaryKey(ctWorkClassGather);
        return 1;
    }

    /**
     * 校验该作业是否是学生自己的
     *
     * @param studentId
     * @param workId
     * @param schoolId
     * @return
     */
    @Override
    public boolean checkWorkOwnerStu(String studentId, String workId, String schoolId) {

        boolean ret = true;
        // 通过作业ID，获取作业汇总表信息
        CtWorkGather workGather = ctWorkGatherMapper.selectByPrimaryKey(workId, schoolId);
        // 先判断是否存在该作业
        if (workGather == null) {
            ret = false;
        } else {
            // 通过学生ID，作业Id，获取学生作业汇总表信息
            CtWorkStudentGather workStudentGather = ctWorkStudentGatherMapper.getWorkStudentGatherByWorkIdAndUserId(workId, studentId, schoolId);
            if (workStudentGather == null) {
                ret = false;
            }
        }
        return ret;
    }

    /**
     * 校验该作业是不是老师布置的
     *
     * @param teacherId
     * @param workId
     * @param schoolId
     * @return
     */
    @Override
    public boolean checkWorkOwnerTch(String teacherId, String workId, String schoolId) {

        boolean ret = true;
        // 通过作业ID，获取作业汇总表信息
        CtWorkGather workGather = ctWorkGatherMapper.selectByPrimaryKey(workId, schoolId);
        // 先判断是否存在该作业
        if (workGather == null) {
            ret = false;
        } else {
            String userId = workGather.getUserId();
            // 获取的userId是否和当前教师ID一致
            if (!userId.equals(teacherId)) {
                ret = false;
            }
        }
        return ret;
    }

    /**
     * 通过workId，查出该作业对应的所有班级，已班级ID为key，班级名称为value存入map，供其他方法调用
     *
     * @param workId
     * @return
     */
    @Override
    public Map<String, String> classIdInfoMap(String workId, String schoolId) {

        Map<String, String> map = new HashMap<>();
        // 获取作业汇总表信息
        CtWorkGather ctWorkGather = ctWorkGatherMapper.selectByPrimaryKey(workId, schoolId);
        if (ctWorkGather != null) {
            String classJSON = ctWorkGather.getWorkClassJson();
            if (classJSON != null) {
                JSONArray array = JSON.parseArray(classJSON);
                if (array.size() > 0 && !CollectionUtils.isEmpty(array)) {
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        String classId = obj.getString("classId");
                        String className = obj.getString("className");
                        map.put(classId, className);
                    }
                }
            }
        } else {
            map.put("", "");
        }
        return map;
    }

    /**
     * 通过workId，查出该作业对应的所有班级，已学生ID为key，班级名称为value存入map，供其他方法调用
     *
     * @param workId
     * @return
     */
    @Override
    public Map<String, String> classUserInfoMap(String workId, String schoolId) {

        Map<String, String> stuMap = new HashMap<>();
        Map<String, String> classMap = this.classIdInfoMap(workId, schoolId);

        // 获取作业学生汇总表
        List<CtWorkStudentGather> workStudentGatherList = ctWorkStudentGatherMapper.getWorkStudentGatherByWorkId(workId, schoolId);
        if (workStudentGatherList.size() > 0 && CollectionUtils.isEmpty(workStudentGatherList)) {
            for (CtWorkStudentGather workStudentGather : workStudentGatherList) {
                String studentId = workStudentGather.getStudentId();
                String classId = workStudentGather.getClassId();
                if (classMap.containsKey(classId)) {
                    stuMap.put(studentId, classMap.get(classId));
                }
            }
        } else {
            stuMap.put("", "");
        }
        return stuMap;
    }

    /**
     * @param workId
     * @param date
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    @Override
    public boolean overdue(String workId, Date date, String schoolId) throws CloudteachException {

        boolean ret = false;
        CtWorkGather workGather = ctWorkGatherMapper.selectByPrimaryKey(workId, schoolId);
        if (workGather != null) {
            Date endTime = workGather.getEndTime();
            if (endTime.getTime() < date.getTime()) {
                ret = true;
            }
        }
        return ret;
    }

    /**
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    @Override
    public boolean allowToWorkCommunication(String workId, String studentId, Date date, String schoolId) throws CloudteachException {

        boolean ret = false;

        int subStatus = 0;

        CtWorkStudentGather studentGather = ctWorkStudentGatherMapper.getWorkStudentGatherByWorkIdAndUserId(workId, studentId, schoolId);
        if (studentGather != null) {
            subStatus = studentGather.getSubStatus();
            if (subStatus == 1 || (subStatus == 0 && (this.overdue(workId, date, schoolId)))) {
                ret = true;
            }
        }
        return ret;
    }

    /**
     * @param workId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    @Override
    public long submitStuCount(String workId, String schoolId) throws CloudteachException {

        return ctWorkStudentGatherMapper.getSubmitStuCount(workId, schoolId);
    }

    /**
     * 通过作业ID，班级ID，获取学生作业信息
     *
     * @param workId
     * @param classId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    @Override
    public List<WorkStudentGatherDTO> queryStuFinishedState(String workId, String classId, String schoolId) throws CloudteachException {

        List<WorkStudentGatherDTO> stuGatherDTOList = new ArrayList<>();

        List<CtWorkStudentGather> stuGatherDOMAINList = ctWorkStudentGatherMapper.getStuGatherByWorkClass(workId, classId, schoolId);
        for (CtWorkStudentGather stuGatherDOMAIN : stuGatherDOMAINList) {
            WorkStudentGatherDTO stuGatherDTO = new WorkStudentGatherDTO();
            if (stuGatherDOMAIN != null) {
                BeanUtils.copyProperties(stuGatherDOMAIN, stuGatherDTO);
                stuGatherDTOList.add(stuGatherDTO);
            }
        }
        return stuGatherDTOList;
    }

    /**
     * 学生进入作业详情页的时候，对查看状态查看时间进行初始化
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    @Override
    public void initStudentGatherStuView(String workId, String studentId, String schoolId) throws CloudteachException {

        logger.info("[校验参数:]");
        if (workId == null || studentId == null || schoolId == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "作业ID，用户ID，学校ID都不为空！",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        logger.info("[获取学生汇总表信息:]");
        CtWorkStudentGather studentGather = ctWorkStudentGatherMapper.getWorkStudentGatherByWorkIdAndUserId(workId, studentId, schoolId);
        if (studentGather == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "获取不到学生汇总信息！",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        int subStatus = studentGather.getSubStatus();
        int viewStatus = studentGather.getViewStatus();

        if (viewStatus == 0) {
            logger.info("[初始化学生查看状态:]");
            int i = 0;
            studentGather.setViewStatus(1);
            studentGather.setViewTime(new Date());
            i = ctWorkStudentGatherMapper.updateByPrimaryKey(studentGather);

            if (i > 0) {
                logger.info("[更新作业统计表，已查看作业学生数:]");
                this.updateViewStudentNum(workId, schoolId);
            }
        }

        if (subStatus == 0 && viewStatus == 1) {
            logger.info("[作业未提交，更新查看作业时间:]");
            studentGather.setViewTime(new Date());
            ctWorkStudentGatherMapper.updateByPrimaryKey(studentGather);
        }

    }
}