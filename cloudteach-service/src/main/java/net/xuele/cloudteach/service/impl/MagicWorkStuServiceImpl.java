package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.StudentWorkCommunicationPageRequest;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.*;
import net.xuele.cloudteach.service.util.DateTimeUtil;
import net.xuele.cloudteach.service.util.StringUtil;
import net.xuele.cloudteach.view.BasicWorkInfoView;
import net.xuele.cloudteach.view.StudentWorkCommunicationView;
import net.xuele.cloudteach.view.WorkAnswerCommentView;
import net.xuele.common.dto.ClassInfoDTO;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import net.xuele.member.dto.UserDTO;
import net.xuele.member.service.StudentService;
import net.xuele.member.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * MagicWorkStuServiceImpl
 * 学生提分宝作业服务
 *
 * @author panglx
 * @date 2015/7/7 0002
 */
@Service
public class MagicWorkStuServiceImpl implements MagicWorkStuService {
    private static Logger logger = LoggerFactory.getLogger(MagicWorkStuServiceImpl.class);

    @Autowired
    MagicWorkService magicWorkService;
    @Autowired
    WorkTapeFilesService workTapeFilesService;

    @Autowired
    CtMagicWorkChallengeMapper ctMagicWorkChallengeMapper;//学生挑战记录表
    @Autowired
    CtMagicWorkChallengeQueMapper ctMagicWorkChallengeQueMapper;//学生挑战题目表
    @Autowired
    CtMagicWorkMapper ctMagicWorkMapper;//提分宝作业
    @Autowired
    CtMagicWorkAnswerMapper ctMagicWorkAnswerMapper;//提分宝作业学生回答
    @Autowired
    CtMagicWorkAnswerFilesMapper ctMagicWorkAnswerFilesMapper;//提分宝作业学生回答附件
    @Autowired
    CtWorkStudentGatherMapper ctWorkStudentGatherMapper;//学生作业汇总表
    @Autowired
    CtWorkStatisticsMapper ctWorkStatisticsMapper;//作业统计表
    @Autowired
    WorkStatisticsService workStatisticsService;//作业汇总统计服务
    @Autowired
    CtWorkGatherMapper ctWorkGatherMapper;//作业汇总表
    @Autowired
    CtWorkClassGatherMapper ctWorkClassGatherMapper;//作业班级汇总表
    @Autowired
    MagicQuestionService magicQuestionService;//题库服务
    @Autowired
    CtMagicBankFinishMapper magicBankFinishMapper;//学生完成状态
    @Autowired
    CtMagicWorkAnswerPraiseMapper ctMagicWorkAnswerPraiseMapper;
    @Autowired
    CtMagicWorkAnswerCommentMapper ctMagicWorkAnswerCommentMapper;
    @Autowired
    CtMagicWorkAnswerStatisticsMapper ctMagicWorkAnswerStatisticsMapper;

    @Autowired
    StudentWorkListMapper studentWorkListMapper;

    @Autowired
    CloudTeachService cloudTeachService;//云教学服务
    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;

    /**
     * 学生提交提分宝作业
     * 1.提分宝作业回答表update
     * 2.提分宝作业回答附件表insert
     * 3.挑战记录表分享状态--分享状态(2提交给老师)
     * 4.作业学生汇总表提交状态-->1
     * 5.作业统计表已提交学生数+1,版本号+1
     * 6.提分宝作业表作业完成状态判断
     *
     * @param magicWorkSubmitFormDTO
     * @throws CloudteachException
     */
    public void submitWork(MagicWorkSubmitFormDTO magicWorkSubmitFormDTO) throws CloudteachException {
        //作业id
        String workId = magicWorkSubmitFormDTO.getWorkId();
        //学生id
        String userId = magicWorkSubmitFormDTO.getUserId();
        //学校id
        String schoolId = magicWorkSubmitFormDTO.getSchoolId();
        //挑战记录id
        String challengeId = magicWorkSubmitFormDTO.getChallengeId();
        //回答内容
        String context = magicWorkSubmitFormDTO.getContext();

        // begin 修改：提交作业时，workId通过页面input取得，不需要加该check(20150822)
        /*logger.info("[校验作业是否属于该学生：]");
        if (!workStatisticsService.checkWorkOwnerStu(userId, workId, schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "：请确认该作业是否属于您自己！",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }*/
        // end

        CtMagicWork magicWork = ctMagicWorkMapper.selectByPrimaryKey(workId, schoolId);
        if (magicWork == null) {
            //查询对象为空
            throw new CloudteachException(CloudTeachErrorEnum.WORK_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.WORK_NOT_FOUND.getCode());
        } else if (magicWork.getEndTime().getTime() < new Date().getTime()) {
            //作业超过最晚提交时间
            throw new CloudteachException(CloudTeachErrorEnum.WORK_SUBMIT_FAILED.getMsg() + "：超过最晚交作业时间！",
                    CloudTeachErrorEnum.WORK_SUBMIT_FAILED.getCode());
        }
        //评分
        CtMagicWorkChallenge magicWorkChallenge = ctMagicWorkChallengeMapper.selectByPrimaryKey(challengeId, schoolId);//学生挑战记录
        if (magicWorkChallenge == null) {
            logger.info("学生挑战记录不存在");
            //查询对象不存在
            throw new CloudteachException(CloudTeachErrorEnum.CHALLENGE_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.CHALLENGE_NOT_FOUND.getCode());
        }
        int score = magicWorkChallenge.getScore();
        logger.info("学生回答附件json转DTO");
        //学生回答附件封装
        String magicWorkAnswerFilesJSON = magicWorkSubmitFormDTO.getMagicWorkAnswerFilesJSON();
        List<MagicWorkAnswerFilesDTO> magicWorkAnswerFilesDTOs = cloudTeachService.getFileDtoFromJSOM(magicWorkAnswerFilesJSON);
        logger.info("1.提分宝作业回答表update");
        //通过作业ID学生ID获得回答信息
        CtMagicWorkAnswer magicWorkAnswer = ctMagicWorkAnswerMapper.getCtMagicWorkAnswerByWorkStudentId(workId, userId, schoolId);
        if (magicWorkAnswer == null) {
            logger.info("提分宝回答表不存在当前学生记录");
            //查询对象不存在
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        } else if (magicWorkAnswer.getSubStatus() == 1) {
            //作业已经提交不能再次提交
            throw new CloudteachException(CloudTeachErrorEnum.ALREADYSUM.getMsg(),
                    CloudTeachErrorEnum.ALREADYSUM.getCode());
        }
        magicWorkAnswer.setChallengeId(challengeId);//挑战记录id
        magicWorkAnswer.setContext(context);//回答内容
        magicWorkAnswer.setScore(score);//评分
        magicWorkAnswer.setSubStatus(1);//提交状态
        magicWorkAnswer.setSubTime(new Date());//提交时间
        ctMagicWorkAnswerMapper.updateByPrimaryKey(magicWorkAnswer);
        logger.info("判断学生提交的附件是否符合提交方式");
        cloudTeachService.isRightSubFile(magicWorkAnswerFilesDTOs, magicWork.getSubImage(), magicWork.getSubTape(), magicWork.getSubVideo(),magicWork.getSubOther());

        logger.info("2.提分宝作业回答附件表insert");
        if (CollectionUtils.isNotEmpty(magicWorkAnswerFilesDTOs)){
            for (MagicWorkAnswerFilesDTO answerFilesDTO : magicWorkAnswerFilesDTOs) {
                CtMagicWorkAnswerFiles answerFiles = new CtMagicWorkAnswerFiles();
                BeanUtils.copyProperties(answerFilesDTO, answerFiles);
                answerFiles.setAnswerFileId(UUID.randomUUID().toString().replace("-", ""));
                answerFiles.setAnswerId(magicWorkAnswer.getAnswerId());
                answerFiles.setUploadTime(new Date());
                answerFiles.setSchoolId(schoolId);
                answerFiles.setStatus(1);
                ctMagicWorkAnswerFilesMapper.insert(answerFiles);
            }
        }

        logger.info("3.挑战记录表分享状态--分享状态(2提交给老师)");
        //ctMagicWorkChallengeMapper.updateShareStatus(2, challengeId, schoolId);
        magicWorkChallenge.setShareStatus(2);
        ctMagicWorkChallengeMapper.updateByPrimaryKey(magicWorkChallenge);

        logger.info("4.作业学生汇总表提交状态-->1");
        ctWorkStudentGatherMapper.updateByStuSubmit(workId, userId, schoolId);

        logger.info("5.作业统计表已提交学生数+1,版本号+1");
        int unSubNum = workStatisticsService.updateSubmitStudentNum(workId, schoolId);

        logger.info("6.提分宝作业表作业完成状态判断");
        if (unSubNum == 0) {//全部提交--完成状态改为2
            ctMagicWorkMapper.updateFinishStatus(2, workId, schoolId);
        } else {//部分完成1
            ctMagicWorkMapper.updateFinishStatus(1, workId, schoolId);
        }

    }

    /**
     * 学生提交提分宝作业--给移动端用
     *
     * @param magicWorkAnswerDTO 学生作业回答dto
     * @param answerFilesDTOs    学生作业回答附件列表
     * @throws CloudteachException
     */
    public void submitMagicWork(MagicWorkAnswerDTO magicWorkAnswerDTO, List<MagicWorkAnswerFilesDTO> answerFilesDTOs) throws CloudteachException {
        //作业id
        String workId = magicWorkAnswerDTO.getWorkId();
        //学生id
        String userId = magicWorkAnswerDTO.getUserId();
        //学校id
        String schoolId = magicWorkAnswerDTO.getSchoolId();
        //挑战记录id
        String challengeId = magicWorkAnswerDTO.getChallengeId();
        //回答内容
        String context = magicWorkAnswerDTO.getContext();
        // begin 修改：提交作业时，workId通过页面input取得，不需要加该check(20150822)
        /*logger.info("[校验作业是否属于该学生：]");
        if (!workStatisticsService.checkWorkOwnerStu(userId, workId, schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "：请确认该作业是否属于您自己！",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }*/
        // end
        CtMagicWork magicWork = ctMagicWorkMapper.selectByPrimaryKey(workId, schoolId);
        if (magicWork == null) {
            //查询的对象为空
            throw new CloudteachException(CloudTeachErrorEnum.WORK_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.WORK_NOT_FOUND.getCode());
        } else if (magicWork.getEndTime().getTime() < new Date().getTime()) {
            //作业超过最晚提交时间
            throw new CloudteachException(CloudTeachErrorEnum.WORK_SUBMIT_FAILED.getMsg() + "：超过最晚交作业时间！",
                    CloudTeachErrorEnum.WORK_SUBMIT_FAILED.getCode());
        }
        //评分
        CtMagicWorkChallenge magicWorkChallenge = ctMagicWorkChallengeMapper.selectByPrimaryKey(challengeId, schoolId);//学生挑战记录
        if (magicWorkChallenge == null) {
            logger.info("学生挑战记录不存在");
            //查询对象不存在
            throw new CloudteachException(CloudTeachErrorEnum.CHALLENGE_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.CHALLENGE_NOT_FOUND.getCode());
        }
        int score = magicWorkChallenge.getScore();
        logger.info("学生回答附件json转DTO");

        logger.info("1.提分宝作业回答表update");
        //通过作业ID学生ID获得回答信息
        CtMagicWorkAnswer magicWorkAnswer = ctMagicWorkAnswerMapper.getCtMagicWorkAnswerByWorkStudentId(workId, userId, schoolId);
        if (magicWorkAnswer == null) {
            logger.info("提分宝回答表不存在当前学生记录");
            //查询对象不存在
            throw new CloudteachException(CloudTeachErrorEnum.WORK_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.WORK_NOT_FOUND.getCode());
        } else if (magicWorkAnswer.getSubStatus() == 1) {
            //作业已经提交不能再次提交
            throw new CloudteachException(CloudTeachErrorEnum.ALREADYSUM.getMsg(),
                    CloudTeachErrorEnum.ALREADYSUM.getCode());
        }
        magicWorkAnswer.setChallengeId(challengeId);//挑战记录id
        magicWorkAnswer.setContext(context);//回答内容
        magicWorkAnswer.setScore(score);//评分
        magicWorkAnswer.setSubStatus(1);//提交状态
        magicWorkAnswer.setSubTime(new Date());//提交时间
        ctMagicWorkAnswerMapper.updateByPrimaryKey(magicWorkAnswer);
        logger.info("判断学生提交的附件是否符合提交方式");

        cloudTeachService.isRightSubFile(answerFilesDTOs, magicWork.getSubImage(), magicWork.getSubTape(), magicWork.getSubVideo(),magicWork.getSubOther());

        logger.info("2.提分宝作业回答附件表insert");
        if (CollectionUtils.isNotEmpty(answerFilesDTOs)){
            for (MagicWorkAnswerFilesDTO answerFilesDTO : answerFilesDTOs) {
                CtMagicWorkAnswerFiles answerFiles = new CtMagicWorkAnswerFiles();
                BeanUtils.copyProperties(answerFilesDTO, answerFiles);
                answerFiles.setAnswerFileId(UUID.randomUUID().toString().replace("-", ""));
                answerFiles.setAnswerId(magicWorkAnswer.getAnswerId());
                answerFiles.setUploadTime(new Date());
                answerFiles.setSchoolId(schoolId);
                answerFiles.setStatus(1);
                ctMagicWorkAnswerFilesMapper.insert(answerFiles);
            }
        }
        logger.info("3.挑战记录表分享状态--分享状态(2提交给老师)");
        //ctMagicWorkChallengeMapper.updateShareStatus(2, challengeId, schoolId);
        magicWorkChallenge.setShareStatus(2);
        ctMagicWorkChallengeMapper.updateByPrimaryKey(magicWorkChallenge);

        logger.info("4.作业学生汇总表提交状态-->1");
        ctWorkStudentGatherMapper.updateByStuSubmit(workId, userId, schoolId);

        logger.info("5.作业统计表已提交学生数+1,版本号+1");
        int unSubNum = workStatisticsService.updateSubmitStudentNum(workId, schoolId);

        logger.info("6.提分宝作业表作业完成状态判断");
        if (unSubNum == 0) {//全部提交--完成状态改为2
            ctMagicWorkMapper.updateFinishStatus(2, workId, schoolId);
        } else {//部分完成1
            ctMagicWorkMapper.updateFinishStatus(1, workId, schoolId);
        }

    }

    //学生在提交作业时，在截至时间内与老师未批阅前，可以修改本人提交作业中的附件，此时可以对附件进行增删的操作
    public void modifyWorkAnswer(String answerId, String schoolId, String userId, String magicWorkAnswerFilesJSON) throws CloudteachException {

        //学生回答附件封装
        List<MagicWorkAnswerFilesDTO> magicWorkAnswerFilesDTOs = new ArrayList<>();//学生回答附件
        MagicWorkAnswerFilesDTO filesDTO;
        MagicWorkAnswerFilesFormDTO filesFormDTO;
        List<MagicWorkAnswerFilesFormDTO> filesJSONList = JSON.parseArray(magicWorkAnswerFilesJSON, MagicWorkAnswerFilesFormDTO.class);
        if (filesJSONList.isEmpty()) {
            logger.info("回答附件json为空");
            //查询对象不存在
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        List<CtMagicWorkAnswerFiles> magicWorkAnswerFilesList = ctMagicWorkAnswerFilesMapper.getAnswerFileInfoByAnswerId(answerId, schoolId);
        List<MagicWorkAnswerFilesFormDTO> newFilesList = new ArrayList<>();
        BeanUtils.copyProperties(magicWorkAnswerFilesList, newFilesList);
        //TODO


    }

    /**
     * 学生分享作业回答
     *
     * @param challengeId
     * @param schoolId
     * @throws CloudteachException
     */
    public int shareWorkAnswer(String challengeId, String schoolId) throws CloudteachException {
        //查询挑战记录
        CtMagicWorkChallenge magicWorkChallenge = ctMagicWorkChallengeMapper.selectByPrimaryKey(challengeId, schoolId);
        if (magicWorkChallenge == null) {
            logger.info("学生挑战记录不存在");
            //查询对象不存在
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        if (magicWorkChallenge.getShareStatus() == 1) {
            //已经分享
            throw new CloudteachException(CloudTeachErrorEnum.ALREADYSHARED.getMsg(),
                    CloudTeachErrorEnum.ALREADYSHARED.getCode());
        }
        //挑战记录分享状态改为1（分享状态(0不分享1分享到作业交流2提交给老师)）
        magicWorkChallenge.setShareStatus(1);
        //更新挑战记录
        return ctMagicWorkChallengeMapper.updateByPrimaryKey(magicWorkChallenge);

    }

    /**
     * 开始作业时展示作业列表
     * 1.题目列表
     *
     * @param workId
     * @param bankId
     * @param userId
     * @return
     */
    public MagicQuestionViewDTO getMagicWorkItems(String workId, String bankId, String userId, String schoolId) throws CloudteachException {
        MagicQuestionViewDTO magicQuestionViewDTO = new MagicQuestionViewDTO();
        List<MagicQuestionDTO> magicQuestionDTOs = new ArrayList<>();
        if (workId == null || workId.isEmpty()) {//如果workId 为null，则为应用中心入口，随机生成题目
            //随机生成题目（包括原题）
            magicQuestionDTOs = magicQuestionService.queryMagicQuestionRandomListByBankId(bankId);
        } else {
            //查询作业
            /*CtMagicWork magicWork = ctMagicWorkMapper.selectByPrimaryKey(workId, schoolId);
            if (magicWork != null && magicWork.getEndTime().getTime() < new Date().getTime()) {
                //作业超过最晚提交时间
                throw new CloudteachException(CloudTeachErrorEnum.WORK_EDIT_FAILED.getMsg(),
                        CloudTeachErrorEnum.WORK_EDIT_FAILED.getCode());
            }*/
            //挑战次数
            int challengeTimes = ctMagicWorkChallengeMapper.selectStuCount(workId, userId, schoolId);
            if (challengeTimes > 0) {//有挑战记录
                //随机生成题目
                magicQuestionDTOs = magicQuestionService.queryMagicQuestionSlaveRandomListByBankId(bankId);
            } else if (challengeTimes == 0) {//没有挑战记录
                magicQuestionDTOs = magicQuestionService.queryMagicQuestionMasterListByBankId(bankId);
            }
        }
        if (magicQuestionDTOs == null || magicQuestionDTOs.isEmpty()) {
            //没有题目
            throw new CloudteachException(CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND.getCode());
        }
        magicQuestionViewDTO.setTotal(magicQuestionDTOs.size());//题目数
        magicQuestionViewDTO.setMagicQuestionDTOs(magicQuestionDTOs);//题目列表
        magicQuestionViewDTO.setOrderNum(magicQuestionDTOs.get(0).getOrderNum());//衍生题号
        return magicQuestionViewDTO;
    }

    /**
     * 获取提分宝题目信息(包括习题讲解)
     *
     * @param workId
     * @param bankId
     * @param userId
     * @param schoolId
     * @param type     1--pc端，2--手機端，3--其他
     * @return
     * @throws CloudteachException
     */
    public MagicWorkQueDetailDTO queryMagicQueDetail(Integer orderNum,String workId, String bankId, String userId, String schoolId, Integer type) throws CloudteachException {
        logger.info("获取提分宝题目信息(包括习题讲解)：queryMagicQueDetail");
        MagicWorkQueDetailDTO magicWorkQueDetailDTO = new MagicWorkQueDetailDTO();
        MagicQuestionViewDTO magicQuestionViewDTO = new MagicQuestionViewDTO();
        logger.info("获取提分宝题目列表");
        if (orderNum==null){
            logger.info("orderNum==null：随机获取提分宝题目");
            magicQuestionViewDTO = this.getMagicWorkItems(workId, bankId, userId, schoolId);
        }else{
            logger.info("orderNum="+orderNum+"：根据orderNum获取提分宝题目");
            List<MagicQuestionDTO> magicQuestionDTOs = magicQuestionService.queryMagicQuestionListByNum(bankId, orderNum);
            magicQuestionViewDTO.setOrderNum(magicQuestionDTOs.get(0).getOrderNum());
            magicQuestionViewDTO.setTotal(magicQuestionDTOs.size());
            magicQuestionViewDTO.setMagicQuestionDTOs(magicQuestionDTOs);
        }
        magicWorkQueDetailDTO.setOrderNum(magicQuestionViewDTO.getOrderNum());
        magicWorkQueDetailDTO.setTotal(magicQuestionViewDTO.getTotal());
        List<MagicQuestionDTO> questionDTOList = magicQuestionViewDTO.getMagicQuestionDTOs();
        logger.info("获取提分宝习题讲解信息");
        MagicQuestionVideoDTO magicQuestionVideoDTO;
        List<MagicWorkChallengeQueViewDTO> magicWorkChallengeQueViewDTOs = new ArrayList<>();
        MagicWorkChallengeQueViewDTO magicWorkChallengeQueViewDTO;
        for (MagicQuestionDTO qDto : questionDTOList) {
            magicWorkChallengeQueViewDTO = new MagicWorkChallengeQueViewDTO();
            BeanUtils.copyProperties(qDto, magicWorkChallengeQueViewDTO);
            if (qDto.getOrderNum() == 0) {
                magicQuestionVideoDTO = magicWorkService.getMagicQueVideoByQueId(qDto.getQueId(), type);
            } else {
                magicQuestionVideoDTO = magicWorkService.getMagicQueVideoByQueId(qDto.getOriginalQuestionId(), type);
            }
            if (magicQuestionVideoDTO != null) {//如果有视频讲解
                logger.info("[提分宝有视频讲解]");
                magicWorkChallengeQueViewDTO.setVideoId(magicQuestionVideoDTO.getVideoId());//视频ID
                magicWorkChallengeQueViewDTO.setVideoFileKey(magicQuestionVideoDTO.getVideoFileKey());//视频文件key
                magicWorkChallengeQueViewDTO.setVideoSuffix(magicQuestionVideoDTO.getVideoSuffix());//文件后缀
                magicWorkChallengeQueViewDTO.setVideoDesc(magicQuestionVideoDTO.getVideoDesc());//视频描述
            }
            magicWorkChallengeQueViewDTOs.add(magicWorkChallengeQueViewDTO);
        }
        magicWorkQueDetailDTO.setMagicQueViewDTOs(magicWorkChallengeQueViewDTOs);

        return magicWorkQueDetailDTO;
    }

    /**
     * 完成作业并对完答案时新增挑战记录及相关表
     * 1.挑战记录insert
     * 2.挑战题目insert
     * 3.提分宝题库学生完成状态 update/inset
     *
     * @param finishWorkFormDTO
     */
    public MagicWorkChallengeDTO finishWork(MagicWorkStuFinishWorkFormDTO finishWorkFormDTO) throws CloudteachException {
        logger.info("完成作业并对完答案时新增挑战记录及相关表：finishWork");
        String unitId = finishWorkFormDTO.getUnitId();
        String bankId = finishWorkFormDTO.getBankId();
        Integer orderNum = finishWorkFormDTO.getOrderNum();
        String workId = finishWorkFormDTO.getWorkId();
        workId = workId==null?"":workId;
        Date beginTime = finishWorkFormDTO.getBeginTime();
        Date endTime = finishWorkFormDTO.getEndTime();
        String schoolId = finishWorkFormDTO.getSchoolId();
        String userId = finishWorkFormDTO.getUserId();
        String rmQueJSON = finishWorkFormDTO.getRmQueJSON();
        //作业入口进入
        /*if (workId != null && !workId.isEmpty()) {
            //查询作业
            CtMagicWork magicWork = ctMagicWorkMapper.selectByPrimaryKey(workId, schoolId);
            if (magicWork != null && magicWork.getEndTime().getTime() < new Date().getTime()) {
                //作业超过最晚提交时间
                throw new CloudteachException(CloudTeachErrorEnum.WORK_EDIT_FAILED.getMsg(),
                        CloudTeachErrorEnum.WORK_EDIT_FAILED.getCode());
            }
        }*/
        if (rmQueJSON == null) {
            //没有对答案--参数错误
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        logger.info("rmQueJSON:"+rmQueJSON);
        JSONArray classJsonList = JSON.parseArray(rmQueJSON);
        logger.info("classJsonList:"+classJsonList);
        Map<String, Integer> map = new HashMap<>();
        int rightNum = 0;//正确题数
        for (int i = 0; i < classJsonList.size(); i++) {
            JSONObject o = classJsonList.getJSONObject(i);
            map.put(o.getString("queId"), o.getIntValue("rwStatus"));
            if (o.getIntValue("rwStatus") == 1)
                rightNum = rightNum + 1;
        }
        logger.info("提分宝答对题目数:" + rightNum);
        logger.info("1.学生挑战记录");
        CtMagicWorkChallenge magicWorkChallenge = new CtMagicWorkChallenge();
        magicWorkChallenge.setChallengeId(UUID.randomUUID().toString().replace("-", ""));
        magicWorkChallenge.setWorkId(workId);
        magicWorkChallenge.setUserId(userId);
        magicWorkChallenge.setBankId(bankId);
        magicWorkChallenge.setOrderNum(orderNum);//第一次默认原题
        magicWorkChallenge.setBeginTime(beginTime);//开始时间
        magicWorkChallenge.setEndTime(endTime);//结束时间
        magicWorkChallenge.setTotalQuenum(magicQuestionService.countByBankId(bankId, orderNum));//题目总数
        magicWorkChallenge.setRightQuenum(rightNum);//正确题数
        magicWorkChallenge.setShareStatus(0);//分享状态
        magicWorkChallenge.setSchoolId(schoolId);
        magicWorkChallenge.setStatus(1);
        logger.info("对学生提交的提分宝作业进行自动评分");
        //挑战次数
        int challengeTimes = ctMagicWorkChallengeMapper.selectStuCount(workId, userId, schoolId);
        logger.info("挑战次数challengeTimes：" + challengeTimes);
        MagicWorkChallengeDTO magicWorkChallengeDTO = new MagicWorkChallengeDTO();
        BeanUtils.copyProperties(magicWorkChallenge, magicWorkChallengeDTO);//domain转DTO
        logger.info("调用接口获取挑战分数");
        MagicWorkChallengeDTO challengeDTO = cloudTeachService.magicWorkAutoScore(magicWorkChallengeDTO, challengeTimes);
        BeanUtils.copyProperties(challengeDTO, magicWorkChallenge);//DTO转domain
        logger.info("插入学生挑战记录");
        ctMagicWorkChallengeMapper.insert(magicWorkChallenge);

        logger.info("2.挑战题目insert");
        List<MagicQuestionDTO> magicQuestionDTOs = magicQuestionService.queryMagicQuestionListByNum(bankId, orderNum);
        logger.info("挑战题目列表："+magicQuestionDTOs);
        CtMagicWorkChallengeQue magicWorkChallengeQue;
        for (MagicQuestionDTO qDto : magicQuestionDTOs) {
            magicWorkChallengeQue = new CtMagicWorkChallengeQue();
            magicWorkChallengeQue.setCqId(UUID.randomUUID().toString().replace("-", ""));
            magicWorkChallengeQue.setChallengeId(magicWorkChallenge.getChallengeId());
            magicWorkChallengeQue.setWorkId(magicWorkChallenge.getWorkId());
            magicWorkChallengeQue.setUserId(userId);
            magicWorkChallengeQue.setBankId(bankId);
            magicWorkChallengeQue.setOrderNum(orderNum);//第几套题
            magicWorkChallengeQue.setQueId(qDto.getQueId());//题目id
            magicWorkChallengeQue.setOriginalQuestionId(qDto.getOriginalQuestionId());//原题id
            logger.info("题目对错rwStatus：" + map.get(qDto.getQueId()));
            magicWorkChallengeQue.setRwStatus(map.get(qDto.getQueId()));//对错
            magicWorkChallengeQue.setSchoolId(schoolId);
            magicWorkChallengeQue.setStatus(1);
            ctMagicWorkChallengeQueMapper.insert(magicWorkChallengeQue);
        }

        logger.info("3.提分宝题库学生完成状态 update/inset");
        //查询学生完成状态记录
        List<CtMagicBankFinish> magicBankFinishes = magicBankFinishMapper.selectBankFinishStaByUserId(userId, bankId, schoolId);
        logger.info("判断是否有记录");
        if (CollectionUtils.isEmpty(magicBankFinishes)) {//没有--insert
            CtMagicBankFinish magicBankFinish = new CtMagicBankFinish();
            magicBankFinish.setFinishId(UUID.randomUUID().toString().replace("-", ""));
            magicBankFinish.setUserId(userId);//学生id
            magicBankFinish.setBankId(bankId);//题库id
            magicBankFinish.setUnitId(unitId);//课程id
            logger.info("调用服务获取课本id");
            String bookId = cloudTeachService.getUnitInfo(unitId).getBookId();
            magicBankFinish.setBookId(bookId);//课本id
            magicBankFinish.setMaxScore(magicWorkChallenge.getScore());
            magicBankFinish.setMaxScorecontext(magicWorkChallenge.getScorecontext());
            magicBankFinish.setSchoolId(schoolId);
            magicBankFinish.setFinishStatus(1);
            magicBankFinishMapper.insert(magicBankFinish);
        } else if (magicBankFinishes.get(0).getMaxScore() < magicWorkChallenge.getScore()) {
            logger.info("比较最高分：低于当前分数-update");
            //有--比较最高分：低于当前分数-update，高于当前分数，不处理
            CtMagicBankFinish magicBankFinish = magicBankFinishes.get(0);
            magicBankFinish.setMaxScore(magicWorkChallenge.getScore());
            magicBankFinish.setMaxScorecontext(magicWorkChallenge.getScorecontext());
            magicBankFinishMapper.updateByPrimaryKey(magicBankFinish);
        }
        return magicWorkChallengeDTO;//返回挑战记录
    }

    /**
     * 查询学生提交作业后的回答信息以及题目对错
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    public MagicSubmitInfoDTO queryMagicSubmitInfo(String workId, String studentId, String schoolId) throws CloudteachException {
        logger.info("[查询学生提交作业后的回答信息以及题目对错：queryMagicSubmitInfo]");
        MagicSubmitInfoDTO magicSubmitInfoDTO = new MagicSubmitInfoDTO();
        //作业回答表
        MagicWorkAnswerDTO stuAnswerInfo = new MagicWorkAnswerDTO();
        CtMagicWorkAnswer ctMagicWorkAnswer = ctMagicWorkAnswerMapper.getCtMagicWorkAnswerByWorkStudentId(workId, studentId, schoolId);
        BeanUtils.copyProperties(ctMagicWorkAnswer, stuAnswerInfo);
        //作业回答附件
        List<MagicWorkAnswerFilesDTO> stuSubmitFilesList = new ArrayList<>();
        logger.info("[获取学生提交的附件信息：]");
        List<CtMagicWorkAnswerFiles> ctMagicWorkAnswerFilesList =
                ctMagicWorkAnswerFilesMapper.getAnswerFileInfoByAnswerId(ctMagicWorkAnswer.getAnswerId(), schoolId);
        for (CtMagicWorkAnswerFiles ctMagicWorkAnswerFiles : ctMagicWorkAnswerFilesList) {
            MagicWorkAnswerFilesDTO magicWorkAnswerFilesDTO = new MagicWorkAnswerFilesDTO();
            BeanUtils.copyProperties(ctMagicWorkAnswerFiles, magicWorkAnswerFilesDTO);
            stuSubmitFilesList.add(magicWorkAnswerFilesDTO);
        }
        //挑战题目表(queId,rwStatus)
        /*MagicWorkChallengeQueDTO queDTO;
        List<MagicWorkChallengeQueDTO> queDTOList = new ArrayList<>();
        List<CtMagicWorkChallengeQue> queList = ctMagicWorkChallengeQueMapper.selectByChallengeId(ctMagicWorkAnswer.getChallengeId(), schoolId);
        for (CtMagicWorkChallengeQue que : queList) {
            queDTO = new MagicWorkChallengeQueDTO();
            BeanUtils.copyProperties(que, queDTO);
            queDTOList.add(queDTO);
        }*/
        logger.info("[挑战记录查询（成绩描述）：]");
        CtMagicWorkChallenge magicWorkChallenge = ctMagicWorkChallengeMapper.selectByPrimaryKey(ctMagicWorkAnswer.getChallengeId(), schoolId);
        magicSubmitInfoDTO.setEndTime(magicWorkChallenge.getEndTime());//完成时间
        magicSubmitInfoDTO.setScorecontext(magicWorkChallenge.getScorecontext());//成绩描述
        magicSubmitInfoDTO.setScore(magicWorkChallenge.getScore());//分数等级
        magicSubmitInfoDTO.setOrderNum(magicWorkChallenge.getOrderNum());//第几套题
        magicSubmitInfoDTO.setStuAnswerInfo(stuAnswerInfo);
        magicSubmitInfoDTO.setStuSubmitFilesList(stuSubmitFilesList);
        return magicSubmitInfoDTO;
    }

    /**
     * 查询挑战题目及回答信息
     *
     * @param challengeId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    public List<MagicWorkChallengeQueViewDTO> getChallengeAnsInfo(String bankId, Integer orderNum, String challengeId, String schoolId) throws CloudteachException {
        logger.info("[查询挑战题目及回答信息：getChallengeAnsInfo]");
        List<MagicQuestionDTO> magicQuestionDTOs = magicQuestionService.queryMagicQuestionListByNum(bankId, orderNum);
        if (magicQuestionDTOs == null) {
            throw new CloudteachException(CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND.getCode());
        }
        List<MagicWorkChallengeQueViewDTO> viewDTOs = new ArrayList<>();
        logger.info("[查询挑战题目表(queId,rwStatus)]");
        List<CtMagicWorkChallengeQue> queList = ctMagicWorkChallengeQueMapper.selectByChallengeId(challengeId, schoolId);
        MagicWorkChallengeQueViewDTO queViewDTO;
        for (MagicQuestionDTO questionDTO : magicQuestionDTOs) {
            queViewDTO = new MagicWorkChallengeQueViewDTO();
            BeanUtils.copyProperties(questionDTO, queViewDTO);
            if (CollectionUtils.isNotEmpty(queList)) {//有挑战记录
                for (int j = 0; j < magicQuestionDTOs.size(); j++) {
                    if (queViewDTO.getQueId().equals(magicQuestionDTOs.get(j).getQueId())) {
                        queViewDTO.setRwStatus(queList.get(j).getRwStatus());
                        break;
                    }
                }
            }
            viewDTOs.add(queViewDTO);
        }
        logger.info("[提分宝习题讲解信息]");
        MagicQuestionVideoDTO magicQuestionVideoDTO;
        for (MagicWorkChallengeQueViewDTO qDto : viewDTOs) {
            if (qDto.getOrderNum() == 0) {
                magicQuestionVideoDTO = magicWorkService.getMagicQueVideoByQueId(qDto.getQueId(), 2);
            } else {
                magicQuestionVideoDTO = magicWorkService.getMagicQueVideoByQueId(qDto.getOriginalQuestionId(), 2);
            }
            if (magicQuestionVideoDTO != null) {//如果有视频讲解
                logger.info("[提分宝有视频讲解]");
                qDto.setVideoId(magicQuestionVideoDTO.getVideoId());//视频ID
                qDto.setVideoFileKey(magicQuestionVideoDTO.getVideoFileKey());//视频文件key
                qDto.setVideoSuffix(magicQuestionVideoDTO.getVideoSuffix());//文件后缀
                qDto.setVideoDesc(magicQuestionVideoDTO.getVideoDesc());//视频描述
            }
        }

        return viewDTOs;
    }

    /**
     * @param studentWorkCommunicationPageRequest
     * @return
     * @throws CloudteachException
     */
    @Override
    public PageResponse<StudentWorkCommunicationViewDTO> magicWorkCommunication(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) throws CloudteachException {

        String schoolId = studentWorkCommunicationPageRequest.getSchoolId();
        String studentId = studentWorkCommunicationPageRequest.getStudentId();
        String workId = studentWorkCommunicationPageRequest.getWorkId();
        int workType = studentWorkCommunicationPageRequest.getWorkType();
        Long time = null;
        int firstReq = 0;
        Date now = new Date();
        if (studentWorkCommunicationPageRequest.getSubTime() != null) {
            time = Long.parseLong(studentWorkCommunicationPageRequest.getSubTime());
        }
        String subTime;

        if (time == null || time == 0) {
            subTime = DateTimeUtil.DateToStringForMycat(new Date());
            firstReq = 1;
        } else {
            subTime = DateTimeUtil.DateToStringForMycat(new Date(time));
        }


        Page page = PageUtils.buildPage(studentWorkCommunicationPageRequest);
        int pagesize = studentWorkCommunicationPageRequest.getPageSize();
        long records = ctMagicWorkAnswerMapper.getSubmitStuRecCount(workId, schoolId);
        logger.info("[获取已提交作业学生数：" + records + "]");

        List<StudentWorkCommunicationViewDTO> studentWorkCommunicationViewDTOList = new ArrayList<>();

        logger.info("[校验作业是否属于该学生：]");
        if (!workStatisticsService.checkWorkOwnerStu(studentId, workId, schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_IS_NOT_YOURS.getMsg(),
                    CloudTeachErrorEnum.WORK_IS_NOT_YOURS.getCode());
        }

        // 如果学生没有完成该作业，则不允许查看大家的作业
        logger.info("[判断学生是否允许进入作业交流：]");
        boolean allowed = workStatisticsService.allowToWorkCommunication(workId, studentId, now, schoolId);
        if (!allowed) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_VIEW_DISCUSSION_FAILED.getMsg(),
                    CloudTeachErrorEnum.WORK_VIEW_DISCUSSION_FAILED.getCode());
        }

        // 获取学生回答信息
        logger.info("[获取学生回答信息：]");
        List<StudentWorkCommunicationView> studentWorkCommunicationViewList =
                ctMagicWorkAnswerMapper.getBasicMagicWorkAnswerInfo(pagesize, page, workId, studentId, subTime, firstReq, schoolId);

        // 转DTO学生回答信息
        logger.info("[DOMAIN->DTO：]");
        for (StudentWorkCommunicationView studentWorkCommunicationView : studentWorkCommunicationViewList) {
            StudentWorkCommunicationViewDTO studentWorkCommunicationViewDTO = new StudentWorkCommunicationViewDTO();
            String answerId = studentWorkCommunicationView.getAnswerId();
            String userId = studentWorkCommunicationView.getUserId();
            // 获取点赞数量
            logger.info("[获取作业回答的点赞数：]");
            int praiseCount = ctMagicWorkAnswerPraiseMapper.getPraiseCountByAnswer(answerId, schoolId);

            // 获取该用户对记录的点赞信息
            logger.info("[获取当前登录用户对记录的点赞状态：]");
            int praiseStatus = 0;
            int praiseNum = ctMagicWorkAnswerPraiseMapper.getPraiseCountByUNIKEY(answerId, studentId, schoolId);
            if (praiseNum != 0) {
                praiseStatus = 1;
            }

            logger.info("[获取学生班级信息：]");
            ClassInfoDTO classInfo = studentService.queryUserClass(userId);

            // 获取已经提交的提分宝挑战记录信息
            logger.info("[获取提交的提分宝挑战记录信息：]");
            CtMagicWorkAnswer studentAnswer = ctMagicWorkAnswerMapper.selectByPrimaryKey(answerId, schoolId);
            CtMagicWorkChallenge ctMagicWorkChallenge = ctMagicWorkChallengeMapper.getMagicWorkChallengeInfo(studentAnswer.getChallengeId(), schoolId);

            // 获取作业回答附件列表
            logger.info("[获取作业回答附件列表：]");
            List<CtMagicWorkAnswerFiles> ctMagicWorkAnswerFilesList =
                    ctMagicWorkAnswerFilesMapper.getAnswerFileInfoByAnswerId(answerId, schoolId);
            // 获取作业回答评论信息
            logger.info("[获取作业回答评论信息：]");
            List<WorkAnswerCommentView> magicWorkAnswerCommNameViewList =
                    ctMagicWorkAnswerCommentMapper.getAnswerCommentsInfo(answerId, Constants.MIN_COMMENT_NUM, schoolId);

            List<MagicWorkAnswerFilesDTO> magicWorkAnswerFilesDTOList = new ArrayList<>();
            List<WorkAnswerCommentViewDTO> magicWorkAnswerCommNameViewDTOList = new ArrayList<>();
            // 附件domain->DTO
            for (CtMagicWorkAnswerFiles ctMagicWorkAnswerFiles : ctMagicWorkAnswerFilesList) {
                MagicWorkAnswerFilesDTO magicWorkAnswerFilesDTO = new MagicWorkAnswerFilesDTO();
                BeanUtils.copyProperties(ctMagicWorkAnswerFiles, magicWorkAnswerFilesDTO);
                magicWorkAnswerFilesDTOList.add(magicWorkAnswerFilesDTO);
            }
            // 评论domain->DTO
            for (WorkAnswerCommentView magicWorkAnswerCommNameView : magicWorkAnswerCommNameViewList) {
                WorkAnswerCommentViewDTO magicWorkAnswerCommNameViewDTO = new WorkAnswerCommentViewDTO();
                BeanUtils.copyProperties(magicWorkAnswerCommNameView, magicWorkAnswerCommNameViewDTO);
                magicWorkAnswerCommNameViewDTO.setContext(StringUtil.getFixedLengthContext(magicWorkAnswerCommNameViewDTO.getContext(), 0));
                magicWorkAnswerCommNameViewDTOList.add(magicWorkAnswerCommNameViewDTO);

            }

            BeanUtils.copyProperties(studentWorkCommunicationView, studentWorkCommunicationViewDTO);
            studentWorkCommunicationViewDTO.setAnswerContext(StringUtil.getFixedLengthContext(studentWorkCommunicationViewDTO.getAnswerContext(), 0));
            studentWorkCommunicationViewDTO.setPraiseCount(praiseCount);
            studentWorkCommunicationViewDTO.setPraiseStatus(praiseStatus);
            studentWorkCommunicationViewDTO.setClassInfo(classInfo.getClassName());
            //studentWorkCommunicationViewDTO.setScore(ctMagicWorkChallenge.getScore());
            studentWorkCommunicationViewDTO.setScorecontext(ctMagicWorkChallenge.getScorecontext());
            studentWorkCommunicationViewDTO.setMagicWorkAnswerFilesDTOList(magicWorkAnswerFilesDTOList);
            studentWorkCommunicationViewDTO.setWorkAnswerCommentViewDTOList(magicWorkAnswerCommNameViewDTOList);
            studentWorkCommunicationViewDTOList.add(studentWorkCommunicationViewDTO);
        }

        PageResponse<StudentWorkCommunicationViewDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(studentWorkCommunicationPageRequest, response);
        response.setRows(studentWorkCommunicationViewDTOList);
        response.setRecords(records);
        return response;
    }

    /**
     * @param answerId
     * @param schoolId
     * @return
     */
    @Override
    public List<WorkAnswerCommentViewDTO> getMoreComments(String answerId, String schoolId, int limitRange) {

        List<WorkAnswerCommentView> magicWorkAnswerCommNameViewList =
                ctMagicWorkAnswerCommentMapper.getAnswerCommentsInfo(answerId, limitRange, schoolId);
        List<WorkAnswerCommentViewDTO> magicWorkAnswerCommNameViewDTOList = new ArrayList<>();
        for (WorkAnswerCommentView magicWorkAnswerCommNameView : magicWorkAnswerCommNameViewList) {
            WorkAnswerCommentViewDTO magicWorkAnswerCommNameViewDTO = new WorkAnswerCommentViewDTO();
            BeanUtils.copyProperties(magicWorkAnswerCommNameView, magicWorkAnswerCommNameViewDTO);
            magicWorkAnswerCommNameViewDTO.setContext(StringUtil.getFixedLengthContext(magicWorkAnswerCommNameViewDTO.getContext(), 0));
            magicWorkAnswerCommNameViewDTOList.add(magicWorkAnswerCommNameViewDTO);

        }
        return magicWorkAnswerCommNameViewDTOList;
    }

    /**
     * 通过作业ID，学生ID，查找学生回答信息
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    @Override
    public MagicWorkAnswerDTO getStuAnswerByWorkStu(String workId, String studentId, String schoolId) {

        MagicWorkAnswerDTO stuAnswerInfo = new MagicWorkAnswerDTO();

        CtMagicWorkAnswer ctMagicWorkAnswer = ctMagicWorkAnswerMapper.getCtMagicWorkAnswerByWorkStudentId(workId, studentId, schoolId);
        if (ctMagicWorkAnswer == null) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.WORK_NOT_FOUND.getCode());
        }
        BeanUtils.copyProperties(ctMagicWorkAnswer, stuAnswerInfo);
        stuAnswerInfo.setContext(StringUtil.getFixedLengthContext(stuAnswerInfo.getContext(), 0));
        return stuAnswerInfo;
    }

    /**
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    @Override
    public BasicWorkInfoViewDTO getBasicWorkInfo(String workId, String studentId, String schoolId) throws CloudteachException {

        BasicWorkInfoViewDTO basicWorkInfoViewDTO = new BasicWorkInfoViewDTO();

        BasicWorkInfoView basicWorkInfoView = studentWorkListMapper.getBasicMagicWorkInfo(workId, studentId, schoolId);
        if (basicWorkInfoView == null) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.WORK_NOT_FOUND.getCode());
        }
        BeanUtils.copyProperties(basicWorkInfoView, basicWorkInfoViewDTO);
        return basicWorkInfoViewDTO;
    }

    /**
     * 获取某个学生提分宝作业的完成信息
     * (包含：作业基本信息，题目详细，学生回答信息，学生提交的挑战记录信息，学生提交的附件信心，学生作业统计信息)
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    @Override
    public MagicWorkFinishDetailViewDTO queryMagicWorkDetail(String workId, String studentId, String schoolId) throws CloudteachException {

        MagicWorkFinishDetailViewDTO magicWorkFinishDetailViewDTO = new MagicWorkFinishDetailViewDTO();
        BasicWorkInfoViewDTO basicWorkInfoViewDTO = new BasicWorkInfoViewDTO();
        MagicWorkAnswerDTO stuAnswerInfo = new MagicWorkAnswerDTO();
        MagicWorkChallengeDTO stuSubmitChallengeInfo = new MagicWorkChallengeDTO();
        List<MagicWorkAnswerFilesDTO> stuSubmitFilesList = new ArrayList<>();
        MagicWorkAnswerStatisticsDTO magicWorkAnswerStatisticsDTO = new MagicWorkAnswerStatisticsDTO();

        logger.info("[校验作业是否属于该学生：]");
        if (!workStatisticsService.checkWorkOwnerStu(studentId, workId, schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_IS_NOT_YOURS.getMsg(),
                    CloudTeachErrorEnum.WORK_IS_NOT_YOURS.getCode());
        }

        logger.info("[获取提分宝作业基本信息：]");
        basicWorkInfoViewDTO = this.getBasicWorkInfo(workId, studentId, schoolId);

        logger.info("[获取作业录音信息：]");
        WorkTapeFilesDTO workTapeFilesDTO = workTapeFilesService.getWorkTapeFilesByWorkId(workId, schoolId);

        logger.info("[获取提分宝题目详情：]");
        //CtMagicWork ctMagicWork = ctMagicWorkMapper.getMagicWorkByStudent(workId, studentId, schoolId);
        CtMagicWork ctMagicWork = ctMagicWorkMapper.selectByPrimaryKey(workId, schoolId);
        String bankId = ctMagicWork.getBankId();
        // 得到提分宝题目列表
        List<MagicQuestionDTO> magicQuestionDTOList = magicQuestionService.queryMagicQuestionMasterListByBankId(bankId);

        logger.info("[获取学生回答信息：]");
        stuAnswerInfo = this.getStuAnswerByWorkStu(workId, studentId, schoolId);

        logger.info("[获取学生提交的挑战信息：]");
        CtMagicWorkChallenge ctMagicWorkChallenge =
                ctMagicWorkChallengeMapper.getMagicWorkChallengeInfo(stuAnswerInfo.getChallengeId(), schoolId);
        int orderNum = 0;
        // -begin 修改：为了支持手机端为完成作业情况下查看作业详情，去掉对挑战纪录的校验（2015/08/22）
        if (ctMagicWorkChallenge != null) {
            BeanUtils.copyProperties(ctMagicWorkChallenge, stuSubmitChallengeInfo);
            orderNum = ctMagicWorkChallenge.getOrderNum();
        }
        /*if (ctMagicWorkChallenge == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "：获取不到提分宝作业学生挑战信息！",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        BeanUtils.copyProperties(ctMagicWorkChallenge, stuSubmitChallengeInfo);*/
        // -end
        List<MagicWorkChallengeQueViewDTO> magicWorkChallengeQueList = null;
        if (stuAnswerInfo.getSubStatus() == 1) {
            logger.info("[获取学生提交的提分宝题目以及题目讲解信息：]");
            magicWorkChallengeQueList = this.getChallengeAnsInfo(bankId, orderNum, stuAnswerInfo.getChallengeId(), schoolId);
        }

        logger.info("[获取学生提交的附件信息：]");
        List<CtMagicWorkAnswerFiles> ctMagicWorkAnswerFilesList =
                ctMagicWorkAnswerFilesMapper.getAnswerFileInfoByAnswerId(stuAnswerInfo.getAnswerId(), schoolId);
        for (CtMagicWorkAnswerFiles ctMagicWorkAnswerFiles : ctMagicWorkAnswerFilesList) {
            MagicWorkAnswerFilesDTO magicWorkAnswerFilesDTO = new MagicWorkAnswerFilesDTO();
            BeanUtils.copyProperties(ctMagicWorkAnswerFiles, magicWorkAnswerFilesDTO);
            stuSubmitFilesList.add(magicWorkAnswerFilesDTO);
        }

        logger.info("[获取学生回答统计信息：]");
        CtMagicWorkAnswerStatistics ctMagicWorkAnswerStatistics = ctMagicWorkAnswerStatisticsMapper.selectByPrimaryKey(stuAnswerInfo.getAnswerId(), schoolId);
        if (ctMagicWorkAnswerStatistics == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "：获取不到提分宝作业学生回答统计信息！",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        BeanUtils.copyProperties(ctMagicWorkAnswerStatistics, magicWorkAnswerStatisticsDTO);

        logger.info("[获取学生信息：]");
        UserDTO stuInfo = userService.getByUserId(studentId);

        magicWorkFinishDetailViewDTO.setBasicMagicWorkInfo(basicWorkInfoViewDTO);
        magicWorkFinishDetailViewDTO.setWorkTapeFilesDTO(workTapeFilesDTO);
        magicWorkFinishDetailViewDTO.setMagicQuestionDTOList(magicQuestionDTOList);
        magicWorkFinishDetailViewDTO.setStuAnswerInfo(stuAnswerInfo);
        magicWorkFinishDetailViewDTO.setStuSubmitChallengeInfo(stuSubmitChallengeInfo);
        magicWorkFinishDetailViewDTO.setStuSubmitMQA(magicWorkChallengeQueList);
        magicWorkFinishDetailViewDTO.setStuSubmitFilesList(stuSubmitFilesList);
        magicWorkFinishDetailViewDTO.setMagicWorkAnswerStatisticsDTO(magicWorkAnswerStatisticsDTO);
        magicWorkFinishDetailViewDTO.setStuInfo(stuInfo);

        return magicWorkFinishDetailViewDTO;
    }

    /**
     * @param workId
     * @param bankId
     * @param entrance 1,作业入口，2，应用中心入口
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    @Override
    public List<MagicWorkChallengeDTO> magicWorkChallengeRecord(String workId, String studentId, String bankId, int entrance, String schoolId) throws CloudteachException {

        if (entrance == 1 && workId == null) {
            // 入口是作业时，作业ID不能为空
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        if (entrance == 2 && bankId == null) {
            // 入口是应用中心时，题库ID不能为空
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        // 获取提分宝挑战记录信息
        List<CtMagicWorkChallenge> ctMagicWorkChallengeList = ctMagicWorkChallengeMapper.getMagicWorkChallengeRecordByWork(workId, studentId, bankId, schoolId);
        List<MagicWorkChallengeDTO> magicWorkChallengeDTOList = new ArrayList<>();

        for (CtMagicWorkChallenge ctMagicWorkChallenge : ctMagicWorkChallengeList) {
            MagicWorkChallengeDTO magicWorkChallengeDTO = new MagicWorkChallengeDTO();
            BeanUtils.copyProperties(ctMagicWorkChallenge, magicWorkChallengeDTO);
            magicWorkChallengeDTOList.add(magicWorkChallengeDTO);
        }

        return magicWorkChallengeDTOList;
    }

    @Override
    public void delWorkAnswer(String answerId) {

    }

    @Override
    public void praise(String answerId, String userId, String schoolId, int userType) {
        logger.info("[学生对以下回答点赞, 回答ID: " + answerId + "]");
        magicWorkService.praise(answerId, userId, schoolId, 2);
    }

    @Override
    public void cancelPraise(String answerId, String userId, String schoolId, int userType) {
        logger.info("[学生取消对以下回答的点赞, 回答ID: " + answerId + "]");
        magicWorkService.unpraise(answerId, userId, schoolId, 2);
    }

    @Override
    public String comment(String answerId, String userId, String schoolId, String context, int userType) {
        logger.info("[学生对以下回答评论, 回答ID: " + answerId + "], 评论内容: " + context + "]");
        return magicWorkService.comment(answerId, userId, schoolId, context, 2);
    }

    @Override
    public void delComment(String commentId, String userId, String schoolId) {
        logger.info("[学生删除对以下回答的评论, 评论ID: +" + commentId + "]");
        magicWorkService.delStuCommont(commentId, userId, schoolId);
    }

    /**
     * 根据answerId获取学生作业回答信息
     *
     * @param answerId
     * @param schoolId
     * @return
     */
    @Override
    public MagicWorkAnswerDTO getMagicWorkAnswerByAnswerId(String answerId, String schoolId) {
        CtMagicWorkAnswer ctMagicWorkAnswer = ctMagicWorkAnswerMapper.selectByPrimaryKey(answerId, schoolId);
        if (ctMagicWorkAnswer == null) {
            return null;
        }
        MagicWorkAnswerDTO magicWorkAnswerDTO = new MagicWorkAnswerDTO();
        BeanUtils.copyProperties(ctMagicWorkAnswer, magicWorkAnswerDTO);
        return magicWorkAnswerDTO;
    }

    /**
     * 获取某个用户对应某个学生作业的点赞状态信息
     *
     * @param answerId 提分宝作业回答ID
     * @param userId   点赞用户ID
     * @param schoolId 学校ID
     * @return
     */
    @Override
    public MagicWorkAnswerPraiseDTO getMagicWorkStuPraiseStatusByUserId(String answerId, String userId, String schoolId) {
        CtMagicWorkAnswerPraise ctMagicWorkAnswerPraise = ctMagicWorkAnswerPraiseMapper.selectByPrimaryKey(answerId, userId, schoolId);
        if (ctMagicWorkAnswerPraise == null) {
            return null;
        }
        MagicWorkAnswerPraiseDTO magicWorkAnswerPraiseDTO = new MagicWorkAnswerPraiseDTO();
        BeanUtils.copyProperties(ctMagicWorkAnswerPraise, magicWorkAnswerPraiseDTO);
        return magicWorkAnswerPraiseDTO;
    }
}