package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.CloudTeachRedisService;
import net.xuele.cloudteach.service.Magic2QuestionService;
import net.xuele.cloudteach.service.Magic2WorkService;
import net.xuele.cloudteach.service.util.JsonUtil;
import net.xuele.cloudteach.service.util.StringUtil;
import net.xuele.cloudteach.view.Magic2WorkChallengeQueAnswerView;
import net.xuele.cloudteach.view.QQuestSortPView;
import net.xuele.common.exceptions.CloudteachException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Magic2WorkServiceImpl
 *
 * @author panglx
 * @date on 2015/10/22 0022.
 */
@Service
public class Magic2WorkServiceImpl implements Magic2WorkService {

    @Autowired
    Magic2QuestionService magic2QuestionService;
    @Autowired
    CloudTeachRedisService cloudTeachRedisService;
    @Autowired
    CtMagic2WorkChallengeQueMapper ctMagic2WorkChallengeQueMapper;
    @Autowired
    CtMagic2WorkChallengeMapper ctMagic2WorkChallengeMapper;
    @Autowired
    CtMagic2WorkChallengeQueAnswerMapper ctMagic2WorkChallengeQueAnswerMapper;
    @Autowired
    CtMagic2WorkMaxPracticeMapper ctMagic2WorkMaxPracticeMapper;

    @Autowired
    CtUnitsMapper ctUnitsMapper;

    @Autowired
    QQuestFeedbackMapper questFeedbackMapper;

    @Autowired
    private QQuestMapper qQuestMapper;

    private static Logger logger = LoggerFactory.getLogger(Magic2WorkServiceImpl.class);

    /**
     * 获取课程信息
     * @param unitId
     * @return
     */
    public UnitsDTO getUnit(String unitId){

        CtUnits units = ctUnitsMapper.selectByPrimaryKey(unitId);
        UnitsDTO unitsDTO = new UnitsDTO();
        BeanUtils.copyProperties(units,unitsDTO);
        return unitsDTO;
    }

    /**
     * 获取题目列表
     * @param unitId
     * @param practiceId
     * @param schoolId
     * @param userId
     * @return
     * @throws CloudteachException
     */
    @Override
    public List<Magic2WorkQuestInfoDTO> showMagic2WorkList_old(String unitId, String practiceId, String schoolId,String userId) throws CloudteachException {
        logger.info("提分宝2开始答卷时获取题目列表：showMagic2WorkList");
        //List<QQuestViewDTO> qQuestViewDTOs =  magic2QuestionService.queryMagicOriAndDeriveQuestByUnitId(unitId, Constants.MAGIC2_WORK_QUEST_NUM);
        //最大sort
        int maxSort = Constants.MAGIC2_WORK_MAX_SORT;
        //查询最高分记录表
        Integer sort;
        CtMagic2WorkMaxPractice ctMagic2WorkMaxPractice = ctMagic2WorkMaxPracticeMapper.selectByPrimaryKey(practiceId, schoolId);
        if (ctMagic2WorkMaxPractice == null) {
            sort = 0;
        } else {
            sort = ctMagic2WorkMaxPractice.getSort() == maxSort ? 1 : ctMagic2WorkMaxPractice.getSort() + 1;
        }

        List<QQuestDTO> qQuestDTOs;
        if (sort == 0) {
            logger.info("sort == 0：挑战原题");
            //qQuestDTOs = magic2QuestionService.selectOriQuestByUnitId(unitId, Constants.MAGIC2_WORK_QUEST_NUM);

            qQuestDTOs = magic2QuestionService.selectOriQuestByUnitId2(
                    unitId,schoolId,userId,Constants.MAGIC2_WORK_QUEST_NUM,Constants.MAGIC2_WORK_WRONG_QUEST_NUM);

        } else {
            logger.info("sort = " + sort + " ：挑战衍生题");
            if (StringUtils.isNotEmpty(practiceId)) {
                logger.info("练习id practiceId = " + practiceId);
                List<String> parentIds = ctMagic2WorkChallengeQueMapper.selectOriQuestId(practiceId, schoolId);
                if (CollectionUtils.isEmpty(parentIds)) {
                    //参数错误:原题id不存在
                    throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
                }
                qQuestDTOs = magic2QuestionService.queryMagicDeriveQuestListByUnitId(unitId, sort, parentIds);

            } else {
                //参数错误
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
            }
        }
        logger.info("题目信息（题目+答案）");
        List<Magic2WorkQuestInfoDTO> magic2WorkQuestInfoDTOs = new ArrayList<>();
        Magic2WorkQuestInfoDTO magic2WorkQuestInfoDTO;
        //查询答案
        if (CollectionUtils.isNotEmpty(qQuestDTOs)) {
            for (QQuestDTO qQuestDTO : qQuestDTOs) {
                List<QAnswerDTO> qAnswerDTOs = magic2QuestionService.selectAnswerByQId(qQuestDTO.getqId());
                if (CollectionUtils.isEmpty(qAnswerDTOs)) {
                    logger.info("提分宝答案不存在:Qid="+qQuestDTO.getqId());
                    throw new CloudteachException(CloudTeachErrorEnum.QANSWERNOTFOUND.getMsg(),
                            CloudTeachErrorEnum.QANSWERNOTFOUND.getCode());
                }
                if (qQuestDTO.getType() == 2) {
                    if (qAnswerDTOs.size() < 2) {
                        // 伪造一条错误的判断题答案
                        String acontent = qAnswerDTOs.get(0).getaContent();
                        QAnswerDTO qAnswerDTO = new QAnswerDTO();
                        qAnswerDTO.setaId(UUID.randomUUID().toString().replace("-", ""));
                        qAnswerDTO.setqId(qQuestDTO.getqId());
                        qAnswerDTO.setIscorrect(0);
                        qAnswerDTO.setSortid(1);
                        if (acontent.equals("0")) {
                            qAnswerDTO.setaContent("1");
                        } else {
                            qAnswerDTO.setaContent("0");
                        }
                        qAnswerDTOs.add(qAnswerDTO);
                    }
                }
                magic2WorkQuestInfoDTO = new Magic2WorkQuestInfoDTO();
                magic2WorkQuestInfoDTO.setqQuestDTO(qQuestDTO);
                magic2WorkQuestInfoDTO.setqAnswerDTOs(qAnswerDTOs);
                magic2WorkQuestInfoDTOs.add(magic2WorkQuestInfoDTO);
            }
        } else {
            //该课程下不存在提分宝题目或者不存在对应子题目
            throw new CloudteachException("暂无提分宝题目", "NO_MAGIC2_QUESTIONS");
        }
        logger.info("返回题目信息");
        return magic2WorkQuestInfoDTOs;
    }

    /**
     * 获取题目列表-新规则
     * @param unitId
     * @param practiceId
     * @param schoolId
     * @param userId
     * @return
     * @throws CloudteachException
     */
    public List<Magic2WorkQuestInfoDTO> showMagic2WorkList(String unitId, String practiceId, String schoolId,String userId) throws CloudteachException {
        logger.info("提分宝2开始答卷时获取题目列表：showMagic2WorkList");
        List<QQuestDTO> qQuestDTOs;
        qQuestDTOs = magic2QuestionService.queryRdQuestByUnitId(
                unitId, practiceId,schoolId,userId,Constants.MAGIC2_WORK_QUEST_NUM,Constants.MAGIC2_WORK_WRONG_QUEST_NUM);

        logger.info("题目信息（题目+答案）");
        List<Magic2WorkQuestInfoDTO> magic2WorkQuestInfoDTOs = new ArrayList<>();
        Magic2WorkQuestInfoDTO magic2WorkQuestInfoDTO;
        //查询答案
        if (CollectionUtils.isNotEmpty(qQuestDTOs)) {
            for (QQuestDTO qQuestDTO : qQuestDTOs) {
                List<QAnswerDTO> qAnswerDTOs = magic2QuestionService.selectAnswerByQId(qQuestDTO.getqId());
                if (CollectionUtils.isEmpty(qAnswerDTOs)) {
                    logger.info("提分宝答案不存在:Qid="+qQuestDTO.getqId());
                    throw new CloudteachException(CloudTeachErrorEnum.QANSWERNOTFOUND.getMsg(),
                            CloudTeachErrorEnum.QANSWERNOTFOUND.getCode());
                }
                if (qQuestDTO.getType() == 2) {
                    if (qAnswerDTOs.size() < 2) {
                        // 伪造一条错误的判断题答案
                        String acontent = qAnswerDTOs.get(0).getaContent();
                        QAnswerDTO qAnswerDTO = new QAnswerDTO();
                        qAnswerDTO.setaId(UUID.randomUUID().toString().replace("-", ""));
                        qAnswerDTO.setqId(qQuestDTO.getqId());
                        qAnswerDTO.setIscorrect(0);
                        qAnswerDTO.setSortid(1);
                        if (acontent.equals("0")) {
                            qAnswerDTO.setaContent("1");
                            qAnswerDTOs.add(0,qAnswerDTO);
                        } else {
                            qAnswerDTO.setaContent("0");
                            qAnswerDTOs.add(qAnswerDTO);
                        }
                    }
                }
                magic2WorkQuestInfoDTO = new Magic2WorkQuestInfoDTO();
                magic2WorkQuestInfoDTO.setqQuestDTO(qQuestDTO);
                magic2WorkQuestInfoDTO.setqAnswerDTOs(qAnswerDTOs);
                magic2WorkQuestInfoDTOs.add(magic2WorkQuestInfoDTO);
            }
        } else {
            //该课程下不存在提分宝题目或者不存在对应子题目
            throw new CloudteachException("暂无提分宝题目", "NO_MAGIC2_QUESTIONS");
        }
        logger.info("返回题目信息");
        return magic2WorkQuestInfoDTOs;
    }

    /**
     * 提分宝提交答卷
     *
     * @param magic2WorkSubmitFormDTO
     * @return
     */
    public Magic2WorkChallengeDTO submitMagic2WorkChallenge_old(Magic2WorkSubmitFormDTO magic2WorkSubmitFormDTO) {
        logger.info("确认交卷时新增挑战记录及相关表：submitMagicWorkChallenge");
        Date endTime = new Date();//完成时间
        /**挑战用时*/
        long time = magic2WorkSubmitFormDTO.getEndTime().getTime()
                - magic2WorkSubmitFormDTO.getBeginTime().getTime();
        if (time<0){//时间差小于0
            logger.error(time+"开始挑战时间不能大于完成挑战时间！");
            time = -time;
        }
        String practiceId = magic2WorkSubmitFormDTO.getPracticeId();//练习id
        String unitId = magic2WorkSubmitFormDTO.getUnitId();//课程id
        Integer sort = magic2WorkSubmitFormDTO.getSort() == null ? 0 : magic2WorkSubmitFormDTO.getSort();//第几套题
        Integer rNum = 0;//回答正确题数
        Integer totalNum = magic2WorkSubmitFormDTO.getTotalNum();//总题数
        int rw;//回答对错
        /**
         * 学生回答json
         * 题目id：queId；原题id：parentId；题目用时：queTime；回答对错：rw；答案id：aIds
         */
        String ansQueJSON = magic2WorkSubmitFormDTO.getAnsQueJSON();
        logger.info("学生回答json ansQueJSON= " + ansQueJSON);
        Date beginTime = new Date(endTime.getTime()-time);//开始时间

        String schoolId = magic2WorkSubmitFormDTO.getSchoolId();//学校id
        String userId = magic2WorkSubmitFormDTO.getUserId();//用户id
        String identityId = magic2WorkSubmitFormDTO.getIdentityId();//用户身份标识

        /**
         * 学生回答JSON转对象
         */
        logger.error(ansQueJSON);
        List<Magic2StuAnsJsonDTO> magic2StuAnsJsonDTOs = JsonUtil.getModifiedMagic2AnsInfo(ansQueJSON);
        if (CollectionUtils.isEmpty(magic2StuAnsJsonDTOs)){
            //参数错误
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        logger.info("1、提分宝2挑战记录表insert");
        CtMagic2WorkChallenge magic2WorkChallenge = new CtMagic2WorkChallenge();
        magic2WorkChallenge.setChallengeId(UUID.randomUUID().toString().replace("-", ""));

        if (sort == 0) {
            logger.info("原题： 练习id = 挑战id");
            magic2WorkChallenge.setPracticeId(magic2WorkChallenge.getChallengeId());
        } else {
            if (StringUtils.isEmpty(practiceId)) {
                //参数错误
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
            }
            magic2WorkChallenge.setPracticeId(practiceId);
        }
        magic2WorkChallenge.setUnitId(unitId);
        magic2WorkChallenge.setUserId(userId);
        magic2WorkChallenge.setSort(sort);
        magic2WorkChallenge.setBeginTime(beginTime);
        magic2WorkChallenge.setEndTime(endTime);

        magic2WorkChallenge.setTotalQuenum(totalNum);//挑战题目数
        magic2WorkChallenge.setShareStatus(0);
        magic2WorkChallenge.setStatus(1);
        magic2WorkChallenge.setSchoolId(schoolId);
        magic2WorkChallenge.setPort(magic2WorkSubmitFormDTO.getPort());//挑战端口


        logger.info("2、提分宝学生挑战题目表insert");
        CtMagic2WorkChallengeQue ctMagic2WorkChallengeQue;

        for (Magic2StuAnsJsonDTO jsonDTO : magic2StuAnsJsonDTOs) {
            //循环每道题目
            ctMagic2WorkChallengeQue = new CtMagic2WorkChallengeQue();
            ctMagic2WorkChallengeQue.setCqId(UUID.randomUUID().toString().replace("-", ""));
            ctMagic2WorkChallengeQue.setChallengeId(magic2WorkChallenge.getChallengeId());
            ctMagic2WorkChallengeQue.setPracticeId(magic2WorkChallenge.getPracticeId());
            ctMagic2WorkChallengeQue.setUserId(magic2WorkChallenge.getUserId());
            ctMagic2WorkChallengeQue.setSort(sort);
            ctMagic2WorkChallengeQue.setQueId(jsonDTO.getQueId());
            ctMagic2WorkChallengeQue.setParentId(jsonDTO.getParentId());
            ctMagic2WorkChallengeQue.setQueTime(jsonDTO.getQueTime());
            ctMagic2WorkChallengeQue.setSchoolId(schoolId);
            ctMagic2WorkChallengeQue.setStatus(1);

            logger.info("3、提分宝学生挑战答案表批量insert");
            List<QAnswerDTO> qAnswerDTOs = magic2QuestionService.selectAnswerByQId(jsonDTO.getQueId());
            List<String> aIds = jsonDTO.getaIds();
            if (CollectionUtils.isEmpty(aIds) || CollectionUtils.isEmpty(qAnswerDTOs)){
                logger.error(jsonDTO.getQueId()+"：学生回答列表可能为空");
                throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
            }
            List<Magic2WorkChallengeQueAnswerView> magic2WorkChallengeQueAnswerViews = new ArrayList<>();
            Magic2WorkChallengeQueAnswerView queAnswerView;
            //根据题目类型判断答案
            rw=0;
            int isr = 0;
            //region if...else...根据不同的题目类型对答案
            if(jsonDTO.getType()==3) {
                logger.info("jsonDTO.getType()==" + jsonDTO.getType() + "：填空题");
                String[] ans;
                String qans;//选项
                String rans = "||";//正确答案
                Map<String, Integer> map = new HashMap<>();
                /**理论上qAnswerDTOs虞aIds的长度相同，不同时取小的长度*/
                int size = qAnswerDTOs.size() <= aIds.size()?qAnswerDTOs.size():aIds.size();
                //region for循环-选项循环
                for (int i = 0; i < size; i++) {//选项循环
                    queAnswerView = new Magic2WorkChallengeQueAnswerView();
                    BeanUtils.copyProperties(qAnswerDTOs.get(i), queAnswerView);
                    queAnswerView.setIsstuans(0);
                    isr = isr + 1;//错误次数
                    /**题库选项转为半角*/
                    String aContent = StringUtil.toDBC(queAnswerView.getaContent());
                    if (aContent.indexOf("||") > 0) {// A||B||C，不能重复
                        /**用户回答转为半角*/
                        String aId = "||" + StringUtil.toDBC(aIds.get(i)) + "||";
                        map.put(aId, i);//学生回答，位置
                        qans = "||" + aContent + "||";
                        if (qans.indexOf(aId) >= 0) {//正确答案，比较是否重复
                            if (rans.indexOf(aId) < 0) {//不重复
                                queAnswerView.setIsstuans(1);
                                isr = isr - 1;//错误次数
                            }
                            rans = rans + StringUtil.toDBC(aIds.get(i)) + "||";
                        }
                        /**学生回答内容存入数据库不转换为半角*/
                        queAnswerView.setScontent(aIds.get(i));
                    } else {
                        String aId = StringUtil.toDBC(aIds.get(i));
                        ans = aContent.split("\\|");
                        for (String s : ans) {
                            if (aId.equals(s)) {
                                queAnswerView.setIsstuans(1);
                                isr = isr - 1;//错误次数
                                break;
                            }
                        }
                        //学生回答内容
                        queAnswerView.setScontent(aIds.get(i));
                    }
                    magic2WorkChallengeQueAnswerViews.add(queAnswerView);
                }
                //endregion
            } else{
                logger.info("jsonDTO.getType()=="+jsonDTO.getType()+"：选择题/判断题");
                for (QAnswerDTO answerDTO : qAnswerDTOs) {
                    queAnswerView = new Magic2WorkChallengeQueAnswerView();
                    BeanUtils.copyProperties(answerDTO, queAnswerView);
                    queAnswerView.setIsstuans(0);
                    for (String aId : aIds) {
                        if (queAnswerView.getaId().equals(aId)){
                            queAnswerView.setIsstuans(1);
                            break;
                        }
                    }
                    if (queAnswerView.getIscorrect() != queAnswerView.getIsstuans()){
                        isr = isr+1;//错误次数
                    }
                    magic2WorkChallengeQueAnswerViews.add(queAnswerView);
                }
            }
            //endregion
            if (isr==0){
                rNum = rNum+1;//正确题数
                rw=1;
            }
            jsonDTO.setRw(rw);
            ctMagic2WorkChallengeQue.setRw(rw);
            /**
             * 添加挑战题目记录
             */
            ctMagic2WorkChallengeQueMapper.insert(ctMagic2WorkChallengeQue);
            //批量插入学生回答答案信息
            ctMagic2WorkChallengeQueAnswerMapper.batchInsert(ctMagic2WorkChallengeQue.getCqId(),
                    ctMagic2WorkChallengeQue.getChallengeId(),
                    userId,
                    jsonDTO.getQueId(),
                    schoolId,
                    magic2WorkChallengeQueAnswerViews);
        }

        int result;
        /**
         * 挑战记录表
         */
        magic2WorkChallenge.setRightQuenum(rNum);//答对题目数
        logger.info("提分宝挑战次数");
        Magic2WorkChallengeDTO magic2WorkChallengeDTO = new Magic2WorkChallengeDTO();
        BeanUtils.copyProperties(magic2WorkChallenge, magic2WorkChallengeDTO);
        logger.info("调用接口自动获取挑战成绩");
        magic2WorkChallengeDTO = this.magic2WorkAutoScore(identityId,magic2WorkChallengeDTO, magic2StuAnsJsonDTOs, rNum);
        BeanUtils.copyProperties(magic2WorkChallengeDTO, magic2WorkChallenge);
        /**插入挑战记录*/
        result = ctMagic2WorkChallengeMapper.insert(magic2WorkChallenge);
        if (result != 1) {
            throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
        }

        logger.info("4、提分宝2练习最高分表insert/update");
        //region 提分宝2练习最高分表insert/update
        if (sort == 0) {//insert
            logger.info("insert");
            CtMagic2WorkMaxPractice ctMagic2WorkMaxPractice = new CtMagic2WorkMaxPractice();
            ctMagic2WorkMaxPractice.setPracticeId(magic2WorkChallenge.getPracticeId());
            ctMagic2WorkMaxPractice.setUnitId(unitId);
            ctMagic2WorkMaxPractice.setUserId(userId);
            ctMagic2WorkMaxPractice.setChallengeId(magic2WorkChallenge.getChallengeId());
            ctMagic2WorkMaxPractice.setMaxScore(magic2WorkChallenge.getScore());
            ctMagic2WorkMaxPractice.setMaxScoreContext(magic2WorkChallenge.getScoreContext());
            ctMagic2WorkMaxPractice.setLastTime(magic2WorkChallenge.getBeginTime());
            ctMagic2WorkMaxPractice.setSort(sort);
            ctMagic2WorkMaxPractice.setSchoolId(schoolId);
            result = ctMagic2WorkMaxPracticeMapper.insert(ctMagic2WorkMaxPractice);
            if (result != 1) {
                throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
            }
        } else {//update
            logger.info("update");
            CtMagic2WorkMaxPractice ctMagic2WorkMaxPractice = ctMagic2WorkMaxPracticeMapper.selectByPrimaryKey(practiceId, schoolId);
            if (ctMagic2WorkMaxPractice == null) {
                //查找的对象不存在
                throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
            }
            //比较最高分
            if (ctMagic2WorkMaxPractice.getMaxScore() < magic2WorkChallenge.getScore()) {//更新分数
                ctMagic2WorkMaxPractice.setChallengeId(magic2WorkChallenge.getChallengeId());
                ctMagic2WorkMaxPractice.setMaxScore(magic2WorkChallenge.getScore());
                ctMagic2WorkMaxPractice.setMaxScoreContext(magic2WorkChallenge.getScoreContext());
            }
            ctMagic2WorkMaxPractice.setLastTime(magic2WorkChallenge.getBeginTime());
            ctMagic2WorkMaxPractice.setSort(sort);
            result = ctMagic2WorkMaxPracticeMapper.updateByPrimaryKey(ctMagic2WorkMaxPractice);
            if (result != 1) {
                throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
            }
        }
        //endregion

        logger.info("返回magic2WorkChallengeDTO");
        return magic2WorkChallengeDTO;
    }

    /**
     * 提交提分宝答卷app-新规则
     * @param magic2WorkSubmitFormDTO
     * @return
     */
    public Magic2WorkChallengeDTO submitMagic2WorkChallenge(Magic2WorkSubmitFormDTO magic2WorkSubmitFormDTO) {
        logger.info("确认交卷时新增挑战记录及相关表：submitMagicWorkChallenge");
        Date endTime = new Date();//完成时间
        /**挑战用时*/
        long time = magic2WorkSubmitFormDTO.getEndTime().getTime()
                - magic2WorkSubmitFormDTO.getBeginTime().getTime();
        if (time<0){//时间差小于0
            logger.error(time+"开始挑战时间不能大于完成挑战时间！");
            time = -time;
        }
        String practiceId = magic2WorkSubmitFormDTO.getPracticeId();//练习id
        String unitId = magic2WorkSubmitFormDTO.getUnitId();//课程id
        Integer rNum = 0;//回答正确题数
        Integer totalNum = magic2WorkSubmitFormDTO.getTotalNum();//总题数
        int rw;//回答对错
        /**
         * 学生回答json
         * 题目id：queId；原题id：parentId；题目用时：queTime；回答对错：rw；答案id：aIds
         */
        String ansQueJSON = magic2WorkSubmitFormDTO.getAnsQueJSON();
        Date beginTime = new Date(endTime.getTime()-time);//开始时间

        String schoolId = magic2WorkSubmitFormDTO.getSchoolId();//学校id
        String userId = magic2WorkSubmitFormDTO.getUserId();//用户id
        String identityId = magic2WorkSubmitFormDTO.getIdentityId();//用户身份标识

        /**
         * 学生回答JSON转对象
         */
        logger.error(ansQueJSON);
        List<Magic2StuAnsJsonDTO> magic2StuAnsJsonDTOs = JsonUtil.getModifiedMagic2AnsInfo(ansQueJSON);
        if (CollectionUtils.isEmpty(magic2StuAnsJsonDTOs)){
            //参数错误
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        logger.info("1、提分宝2挑战记录表insert");
        CtMagic2WorkChallenge magic2WorkChallenge = new CtMagic2WorkChallenge();
        magic2WorkChallenge.setChallengeId(UUID.randomUUID().toString().replace("-", ""));

        if (StringUtils.isEmpty(practiceId)){
            logger.info("第一次挑战： 练习id = 挑战id");
            magic2WorkChallenge.setPracticeId(magic2WorkChallenge.getChallengeId());
        }else{
            magic2WorkChallenge.setPracticeId(practiceId);
        }
        magic2WorkChallenge.setUnitId(unitId);
        magic2WorkChallenge.setUserId(userId);
        magic2WorkChallenge.setSort(10);
        magic2WorkChallenge.setBeginTime(beginTime);
        magic2WorkChallenge.setEndTime(endTime);

        magic2WorkChallenge.setTotalQuenum(totalNum);//挑战题目数
        magic2WorkChallenge.setShareStatus(0);
        magic2WorkChallenge.setStatus(1);
        magic2WorkChallenge.setSchoolId(schoolId);
        magic2WorkChallenge.setPort(magic2WorkSubmitFormDTO.getPort());//挑战端口

        logger.info("2、提分宝学生挑战题目表insert");
        CtMagic2WorkChallengeQue ctMagic2WorkChallengeQue;

        /**
         * 用于判断手机端是否有传sort值
         */
        Boolean isNew = false;
        if (ansQueJSON.contains("sort")){
            isNew = true;
        }
        //region magic2StuAnsJsonDTOs循环
        for (Magic2StuAnsJsonDTO jsonDTO : magic2StuAnsJsonDTOs) {
            //循环每道题目
            ctMagic2WorkChallengeQue = new CtMagic2WorkChallengeQue();
            ctMagic2WorkChallengeQue.setCqId(UUID.randomUUID().toString().replace("-", ""));
            ctMagic2WorkChallengeQue.setChallengeId(magic2WorkChallenge.getChallengeId());
            ctMagic2WorkChallengeQue.setPracticeId(magic2WorkChallenge.getPracticeId());
            ctMagic2WorkChallengeQue.setUserId(magic2WorkChallenge.getUserId());
            ctMagic2WorkChallengeQue.setSort(jsonDTO.getSort());
            ctMagic2WorkChallengeQue.setParentId(jsonDTO.getParentId());
            /**如果手机端有传sort，则取传过来的sort*/
            if (!isNew){
                /**否则去题目表中查询sort*/
                QQuestSortPView q = qQuestMapper.selectSortAndPIdByQId(jsonDTO.getQueId(),unitId);
                ctMagic2WorkChallengeQue.setSort(q.getSort());
                ctMagic2WorkChallengeQue.setParentId(q.getParentId());
            }
            ctMagic2WorkChallengeQue.setQueId(jsonDTO.getQueId());
            ctMagic2WorkChallengeQue.setQueTime(jsonDTO.getQueTime());
            ctMagic2WorkChallengeQue.setSchoolId(schoolId);
            /**超时题目判断*/
            this.overTimeJudge(ctMagic2WorkChallengeQue);

            logger.info("3、提分宝学生挑战答案表批量insert");
            List<QAnswerDTO> qAnswerDTOs = magic2QuestionService.selectAnswerByQId(jsonDTO.getQueId());
            List<String> aIds = jsonDTO.getaIds();
            if (CollectionUtils.isEmpty(aIds) || CollectionUtils.isEmpty(qAnswerDTOs)){
                logger.error(jsonDTO.getQueId()+"：学生回答列表可能为空");
                throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
            }
            List<Magic2WorkChallengeQueAnswerView> magic2WorkChallengeQueAnswerViews = new ArrayList<>();
            Magic2WorkChallengeQueAnswerView queAnswerView;
            //根据题目类型判断答案
            rw=0;
            int isr = 0;
            //region if...else...根据不同的题目类型对答案
            if(jsonDTO.getType()==3) {
                logger.info("jsonDTO.getType()==" + jsonDTO.getType() + "：填空题");
                String[] ans;
                String qans;//选项
                String rans = "||";//正确答案
                Map<String, Integer> map = new HashMap<>();
                /**理论上qAnswerDTOs虞aIds的长度相同，不同时取小的长度*/
                int size = qAnswerDTOs.size() <= aIds.size()?qAnswerDTOs.size():aIds.size();
                //region for循环-选项循环
                for (int i = 0; i < size; i++) {//选项循环
                    queAnswerView = new Magic2WorkChallengeQueAnswerView();
                    BeanUtils.copyProperties(qAnswerDTOs.get(i), queAnswerView);
                    queAnswerView.setIsstuans(0);
                    isr = isr + 1;//错误次数
                    /**题库选项转为半角*/
                    String aContent = StringUtil.toDBC(queAnswerView.getaContent());
                    if (aContent.indexOf("||") > 0) {// A||B||C，不能重复
                        /**用户回答转为半角*/
                        String aId = "||" + StringUtil.toDBC(aIds.get(i)) + "||";
                        map.put(aId, i);//学生回答，位置
                        qans = "||" + aContent + "||";
                        if (qans.indexOf(aId) >= 0) {//正确答案，比较是否重复
                            if (rans.indexOf(aId) < 0) {//不重复
                                queAnswerView.setIsstuans(1);
                                isr = isr - 1;//错误次数
                            }
                            rans = rans + StringUtil.toDBC(aIds.get(i)) + "||";
                        }
                        /**学生回答内容存入数据库不转换为半角*/
                        queAnswerView.setScontent(aIds.get(i));
                    } else {
                        String aId = StringUtil.toDBC(aIds.get(i));
                        ans = aContent.split("\\|");
                        for (String s : ans) {
                            if (aId.equals(s)) {
                                queAnswerView.setIsstuans(1);
                                isr = isr - 1;//错误次数
                                break;
                            }
                        }
                        //学生回答内容
                        queAnswerView.setScontent(aIds.get(i));
                    }
                    magic2WorkChallengeQueAnswerViews.add(queAnswerView);
                }
                //endregion
            } else{
                logger.info("jsonDTO.getType()=="+jsonDTO.getType()+"：选择题/判断题");
                for (QAnswerDTO answerDTO : qAnswerDTOs) {
                    queAnswerView = new Magic2WorkChallengeQueAnswerView();
                    BeanUtils.copyProperties(answerDTO, queAnswerView);
                    queAnswerView.setIsstuans(0);
                    for (String aId : aIds) {
                        if (queAnswerView.getaId().equals(aId)){
                            queAnswerView.setIsstuans(1);
                            break;
                        }
                    }
                    if (queAnswerView.getIscorrect() != queAnswerView.getIsstuans()){
                        isr = isr+1;//错误次数
                    }
                    magic2WorkChallengeQueAnswerViews.add(queAnswerView);
                }
            }
            //endregion
            if (isr==0){
                rNum = rNum+1;//正确题数
                rw=1;
            }
            jsonDTO.setRw(rw);
            ctMagic2WorkChallengeQue.setRw(rw);
            /**
             * 添加挑战题目记录
             */
            ctMagic2WorkChallengeQueMapper.insert(ctMagic2WorkChallengeQue);
            //批量插入学生回答答案信息
            ctMagic2WorkChallengeQueAnswerMapper.batchInsert(ctMagic2WorkChallengeQue.getCqId(),
                    ctMagic2WorkChallengeQue.getChallengeId(),
                    userId,
                    jsonDTO.getQueId(),
                    schoolId,
                    magic2WorkChallengeQueAnswerViews);
        }
        //endregion
        int result;
        /**
         * 挑战记录表
         */
        magic2WorkChallenge.setRightQuenum(rNum);//答对题目数
        Magic2WorkChallengeDTO magic2WorkChallengeDTO = new Magic2WorkChallengeDTO();
        BeanUtils.copyProperties(magic2WorkChallenge, magic2WorkChallengeDTO);
        logger.info("调用接口自动获取挑战成绩");
        magic2WorkChallengeDTO = this.magic2WorkAutoScore(identityId,magic2WorkChallengeDTO, magic2StuAnsJsonDTOs,  rNum);
        /**插入挑战记录*/
        magic2WorkChallenge.setScore(magic2WorkChallengeDTO.getScore());
        magic2WorkChallenge.setScoreContext(magic2WorkChallengeDTO.getScoreContext());
        result = ctMagic2WorkChallengeMapper.insert(magic2WorkChallenge);
        if (result != 1) {
            throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
        }
        logger.info("4、提分宝2练习最高分表insert/update");
        //region 提分宝2练习最高分表insert/update
        if (StringUtils.isEmpty(practiceId)) {//insert
            logger.info("insert");
            CtMagic2WorkMaxPractice ctMagic2WorkMaxPractice = new CtMagic2WorkMaxPractice();
            ctMagic2WorkMaxPractice.setPracticeId(magic2WorkChallengeDTO.getPracticeId());
            ctMagic2WorkMaxPractice.setUnitId(unitId);
            ctMagic2WorkMaxPractice.setUserId(userId);
            ctMagic2WorkMaxPractice.setChallengeId(magic2WorkChallengeDTO.getChallengeId());
            ctMagic2WorkMaxPractice.setMaxScore(magic2WorkChallengeDTO.getScore());
            ctMagic2WorkMaxPractice.setMaxScoreContext(magic2WorkChallengeDTO.getScoreContext());
            ctMagic2WorkMaxPractice.setLastTime(magic2WorkChallengeDTO.getBeginTime());
            ctMagic2WorkMaxPractice.setSort(10);
            ctMagic2WorkMaxPractice.setSchoolId(schoolId);
            result = ctMagic2WorkMaxPracticeMapper.insert(ctMagic2WorkMaxPractice);
            if (result != 1) {
                throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
            }
            cloudTeachRedisService.set("Magic2ChallengeTimes:" + magic2WorkChallenge.getPracticeId(), Integer.toString(0),60000);
        } else {//update
            logger.info("update");
            CtMagic2WorkMaxPractice ctMagic2WorkMaxPractice = ctMagic2WorkMaxPracticeMapper.selectByPrimaryKey(practiceId, schoolId);
            if (ctMagic2WorkMaxPractice == null) {
                //查找的对象不存在
                throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
            }
            //比较最高分
            if (ctMagic2WorkMaxPractice.getMaxScore() < magic2WorkChallengeDTO.getScore()) {//更新分数
                ctMagic2WorkMaxPractice.setChallengeId(magic2WorkChallengeDTO.getChallengeId());
                ctMagic2WorkMaxPractice.setMaxScore(magic2WorkChallengeDTO.getScore());
                ctMagic2WorkMaxPractice.setMaxScoreContext(magic2WorkChallengeDTO.getScoreContext());
            }
            ctMagic2WorkMaxPractice.setLastTime(magic2WorkChallengeDTO.getBeginTime());
            result = ctMagic2WorkMaxPracticeMapper.updateByPrimaryKey(ctMagic2WorkMaxPractice);
            if (result != 1) {
                throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
            }

        }
        //endregion

        /**
         * 挑战次数+1
         */
        if (cloudTeachRedisService.get("IsMagic2FirstChallenge:" + magic2WorkChallenge.getPracticeId()).equals("1")){
            cloudTeachRedisService.set("Magic2ChallengeTimes:" + magic2WorkChallenge.getPracticeId(), Integer.toString(1),60000);
        }else{
            int challengeTimes = Integer.parseInt(cloudTeachRedisService.get("Magic2ChallengeTimes:" + magic2WorkChallenge.getPracticeId()));
            cloudTeachRedisService.set("Magic2ChallengeTimes:" + magic2WorkChallenge.getPracticeId(), Integer.toString(challengeTimes+1),60000);
        }
        logger.info("-----------挑战次数challengeTimes：----------------------"+cloudTeachRedisService.get("Magic2ChallengeTimes:" + magic2WorkChallenge.getPracticeId()));
        /**
         * 记录本次挑战id，用于下次出题用
         */
        cloudTeachRedisService.set("Magic2ChallengeID:" + magic2WorkChallenge.getPracticeId(), magic2WorkChallenge.getChallengeId(),60000);
        logger.info("返回magic2WorkChallengeDTO");
        return magic2WorkChallengeDTO;
    }

    /**
     * 提交提分宝答卷pc-新规则
     * @param magic2WorkSubmitFormDTO
     * @return
     */
    public Magic2WorkChallengeDTO submitMagic2WorkChallengePC(Magic2WorkSubmitFormDTO magic2WorkSubmitFormDTO) {
        logger.info("确认交卷时新增挑战记录及相关表：submitMagicWorkChallenge");
        Date endTime = new Date();//完成时间
        /**挑战用时*/
        long time = magic2WorkSubmitFormDTO.getEndTime().getTime()
                - magic2WorkSubmitFormDTO.getBeginTime().getTime();
        if (time<0){//时间差小于0
            logger.error(time+"开始挑战时间不能大于完成挑战时间！");
            time = -time;
        }
        String practiceId = magic2WorkSubmitFormDTO.getPracticeId();//练习id
        String unitId = magic2WorkSubmitFormDTO.getUnitId();//课程id
        Integer rNum = 0;//回答正确题数
        Integer totalNum = magic2WorkSubmitFormDTO.getTotalNum();//总题数
        int rw;//回答对错
        /**
         * 学生回答json
         * 题目id：queId；原题id：parentId；题目用时：queTime；回答对错：rw；答案id：aIds
         */
        String ansQueJSON = magic2WorkSubmitFormDTO.getAnsQueJSON();
        Date beginTime = new Date(endTime.getTime()-time);//开始时间

        String schoolId = magic2WorkSubmitFormDTO.getSchoolId();//学校id
        String userId = magic2WorkSubmitFormDTO.getUserId();//用户id
        String identityId = magic2WorkSubmitFormDTO.getIdentityId();//用户身份标识

        /**
         * 学生回答JSON转对象
         */
        logger.error(ansQueJSON);
        List<Magic2StuAnsJsonDTO> magic2StuAnsJsonDTOs = JsonUtil.getModifiedMagic2AnsInfo(ansQueJSON);
        if (CollectionUtils.isEmpty(magic2StuAnsJsonDTOs)){
            //参数错误
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        logger.info("1、提分宝2挑战记录表insert");
        CtMagic2WorkChallenge magic2WorkChallenge = new CtMagic2WorkChallenge();
        magic2WorkChallenge.setChallengeId(UUID.randomUUID().toString().replace("-", ""));

        if (StringUtils.isEmpty(practiceId)){
            logger.info("第一次挑战： 练习id = 挑战id");
            magic2WorkChallenge.setPracticeId(magic2WorkChallenge.getChallengeId());
        }else{
            magic2WorkChallenge.setPracticeId(practiceId);
        }
        magic2WorkChallenge.setUnitId(unitId);
        magic2WorkChallenge.setUserId(userId);
        magic2WorkChallenge.setSort(10);
        magic2WorkChallenge.setBeginTime(beginTime);
        magic2WorkChallenge.setEndTime(endTime);

        magic2WorkChallenge.setTotalQuenum(totalNum);//挑战题目数
        magic2WorkChallenge.setShareStatus(0);
        magic2WorkChallenge.setStatus(1);
        magic2WorkChallenge.setSchoolId(schoolId);
        magic2WorkChallenge.setPort(magic2WorkSubmitFormDTO.getPort());//挑战端口

        logger.info("2、提分宝学生挑战题目表insert");
        CtMagic2WorkChallengeQue ctMagic2WorkChallengeQue;

        List<Magic2SubmitJsonDTO> submitJsonDTOList = new ArrayList<>();
        Magic2SubmitJsonDTO submitJsonDTO;
        List<String> correctAId;
        //region magic2StuAnsJsonDTOs循环
        for (Magic2StuAnsJsonDTO jsonDTO : magic2StuAnsJsonDTOs) {
            //循环每道题目
            ctMagic2WorkChallengeQue = new CtMagic2WorkChallengeQue();
            ctMagic2WorkChallengeQue.setCqId(UUID.randomUUID().toString().replace("-", ""));
            ctMagic2WorkChallengeQue.setChallengeId(magic2WorkChallenge.getChallengeId());
            ctMagic2WorkChallengeQue.setPracticeId(magic2WorkChallenge.getPracticeId());
            ctMagic2WorkChallengeQue.setUserId(magic2WorkChallenge.getUserId());
            ctMagic2WorkChallengeQue.setSort(jsonDTO.getSort());
            ctMagic2WorkChallengeQue.setQueId(jsonDTO.getQueId());
            ctMagic2WorkChallengeQue.setParentId(jsonDTO.getParentId());
            ctMagic2WorkChallengeQue.setQueTime(jsonDTO.getQueTime());
            ctMagic2WorkChallengeQue.setSchoolId(schoolId);
            /**超时题目判断*/
            this.overTimeJudge(ctMagic2WorkChallengeQue);

            logger.info("3、提分宝学生挑战答案表批量insert");
            List<QAnswerDTO> qAnswerDTOs = magic2QuestionService.selectAnswerByQId(jsonDTO.getQueId());
            List<String> aIds = jsonDTO.getaIds();
            if (CollectionUtils.isEmpty(aIds) || CollectionUtils.isEmpty(qAnswerDTOs)){
                logger.error(jsonDTO.getQueId()+"：学生回答列表可能为空");
                throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
            }
            List<Magic2WorkChallengeQueAnswerView> magic2WorkChallengeQueAnswerViews = new ArrayList<>();
            Magic2WorkChallengeQueAnswerView queAnswerView;
            //根据题目类型判断答案
            rw=0;
            int isr = 0;
            //region if...else...根据不同的题目类型对答案
            if(jsonDTO.getType()==3) {
                logger.info("jsonDTO.getType()==" + jsonDTO.getType() + "：填空题");
                String[] ans;
                String qans;//选项
                String rans = "||";//正确答案
                Map<String, Integer> map = new HashMap<>();
                /**理论上qAnswerDTOs虞aIds的长度相同，不同时取小的长度*/
                int size = qAnswerDTOs.size() <= aIds.size()?qAnswerDTOs.size():aIds.size();
                //region for循环-选项循环
                for (int i = 0; i < size; i++) {//选项循环
                    queAnswerView = new Magic2WorkChallengeQueAnswerView();
                    BeanUtils.copyProperties(qAnswerDTOs.get(i), queAnswerView);
                    queAnswerView.setIsstuans(0);
                    isr = isr + 1;//错误次数
                    /**题库选项转为半角*/
                    String aContent = StringUtil.toDBC(queAnswerView.getaContent());
                    if (aContent.indexOf("||") > 0) {// A||B||C，不能重复
                        /**用户回答转为半角*/
                        String aId = "||" + StringUtil.toDBC(aIds.get(i)) + "||";
                        map.put(aId, i);//学生回答，位置
                        qans = "||" + aContent + "||";
                        if (qans.indexOf(aId) >= 0) {//正确答案，比较是否重复
                            if (rans.indexOf(aId) < 0) {//不重复
                                queAnswerView.setIsstuans(1);
                                isr = isr - 1;//错误次数
                            }
                            rans = rans + StringUtil.toDBC(aIds.get(i)) + "||";
                        }
                        /**学生回答内容存入数据库不转换为半角*/
                        queAnswerView.setScontent(aIds.get(i));
                    } else {
                        String aId = StringUtil.toDBC(aIds.get(i));
                        ans = aContent.split("\\|");
                        for (String s : ans) {
                            if (aId.equals(s)) {
                                queAnswerView.setIsstuans(1);
                                isr = isr - 1;//错误次数
                                break;
                            }
                        }
                        //学生回答内容
                        queAnswerView.setScontent(aIds.get(i));
                    }
                    magic2WorkChallengeQueAnswerViews.add(queAnswerView);
                }
                //endregion
            } else{
                logger.info("jsonDTO.getType()=="+jsonDTO.getType()+"：选择题/判断题");
                for (QAnswerDTO answerDTO : qAnswerDTOs) {
                    queAnswerView = new Magic2WorkChallengeQueAnswerView();
                    BeanUtils.copyProperties(answerDTO, queAnswerView);
                    queAnswerView.setIsstuans(0);
                    for (String aId : aIds) {
                        if (queAnswerView.getaId().equals(aId)){
                            queAnswerView.setIsstuans(1);
                            break;
                        }
                    }
                    if (queAnswerView.getIscorrect() != queAnswerView.getIsstuans()){
                        isr = isr+1;//错误次数
                    }
                    magic2WorkChallengeQueAnswerViews.add(queAnswerView);
                }
            }
            //endregion
            if (isr==0){
                rNum = rNum+1;//正确题数
                rw=1;
            }
            jsonDTO.setRw(rw);
            ctMagic2WorkChallengeQue.setRw(rw);
            /**
             * 添加挑战题目记录
             */
            ctMagic2WorkChallengeQueMapper.insert(ctMagic2WorkChallengeQue);
            //批量插入学生回答答案信息
            ctMagic2WorkChallengeQueAnswerMapper.batchInsert(ctMagic2WorkChallengeQue.getCqId(),
                    ctMagic2WorkChallengeQue.getChallengeId(),
                    userId,
                    jsonDTO.getQueId(),
                    schoolId,
                    magic2WorkChallengeQueAnswerViews);
            /**返回对答案结果*/
            //region 返回对答案结果:submitJsonDTOList
            submitJsonDTO = new Magic2SubmitJsonDTO();
            submitJsonDTO.setQueId(jsonDTO.getQueId());
            submitJsonDTO.setRw(jsonDTO.getRw());
            submitJsonDTO.setType(jsonDTO.getType());

            correctAId = new ArrayList<>();
            if (jsonDTO.getType()==11||jsonDTO.getType()==12){//选择题存正确答案排序码
                for(QAnswerDTO answerDTO : qAnswerDTOs){
                    if (answerDTO.getIscorrect() == 1){
                        correctAId.add(answerDTO.getSortid().toString());
                    }
                }
            }else{//其他题目存正确答案内容
                for(QAnswerDTO answerDTO : qAnswerDTOs){
                    if (answerDTO.getIscorrect() == 1){
                        correctAId.add(answerDTO.getaContent());
                    }
                }
            }
            submitJsonDTO.setaIds(correctAId);
            submitJsonDTOList.add(submitJsonDTO);
            //endregion
        }
        //endregion
        int result;
        /**
         * 挑战记录表
         */
        magic2WorkChallenge.setRightQuenum(rNum);//答对题目数
        logger.info("提分宝挑战次数");

        Magic2WorkChallengeDTO magic2WorkChallengeDTO = new Magic2WorkChallengeDTO();
        BeanUtils.copyProperties(magic2WorkChallenge, magic2WorkChallengeDTO);
        logger.info("调用接口自动获取挑战成绩");
        magic2WorkChallengeDTO = this.magic2WorkAutoScore(identityId,magic2WorkChallengeDTO, magic2StuAnsJsonDTOs, rNum);
        /**插入挑战记录*/
        magic2WorkChallenge.setScore(magic2WorkChallengeDTO.getScore());
        magic2WorkChallenge.setScoreContext(magic2WorkChallengeDTO.getScoreContext());
        result = ctMagic2WorkChallengeMapper.insert(magic2WorkChallenge);
        if (result != 1) {
            throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
        }
        logger.info("4、提分宝2练习最高分表insert/update");
        //region 提分宝2练习最高分表insert/update
        if (StringUtils.isEmpty(practiceId)) {//insert
            logger.info("insert");
            CtMagic2WorkMaxPractice ctMagic2WorkMaxPractice = new CtMagic2WorkMaxPractice();
            ctMagic2WorkMaxPractice.setPracticeId(magic2WorkChallengeDTO.getPracticeId());
            ctMagic2WorkMaxPractice.setUnitId(unitId);
            ctMagic2WorkMaxPractice.setUserId(userId);
            ctMagic2WorkMaxPractice.setChallengeId(magic2WorkChallengeDTO.getChallengeId());
            ctMagic2WorkMaxPractice.setMaxScore(magic2WorkChallengeDTO.getScore());
            ctMagic2WorkMaxPractice.setMaxScoreContext(magic2WorkChallengeDTO.getScoreContext());
            ctMagic2WorkMaxPractice.setLastTime(magic2WorkChallengeDTO.getBeginTime());
            ctMagic2WorkMaxPractice.setSort(10);
            ctMagic2WorkMaxPractice.setSchoolId(schoolId);
            result = ctMagic2WorkMaxPracticeMapper.insert(ctMagic2WorkMaxPractice);
            if (result != 1) {
                throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
            }
            cloudTeachRedisService.set("Magic2ChallengeTimes:" + magic2WorkChallenge.getPracticeId(), Integer.toString(0),60000);
         } else {//update
            logger.info("update");
            CtMagic2WorkMaxPractice ctMagic2WorkMaxPractice = ctMagic2WorkMaxPracticeMapper.selectByPrimaryKey(practiceId, schoolId);
            if (ctMagic2WorkMaxPractice == null) {
                //查找的对象不存在
                throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
            }
            //比较最高分
            if (ctMagic2WorkMaxPractice.getMaxScore() < magic2WorkChallengeDTO.getScore()) {//更新分数
                ctMagic2WorkMaxPractice.setChallengeId(magic2WorkChallengeDTO.getChallengeId());
                ctMagic2WorkMaxPractice.setMaxScore(magic2WorkChallengeDTO.getScore());
                ctMagic2WorkMaxPractice.setMaxScoreContext(magic2WorkChallengeDTO.getScoreContext());
            }
            ctMagic2WorkMaxPractice.setLastTime(magic2WorkChallengeDTO.getBeginTime());
            result = ctMagic2WorkMaxPracticeMapper.updateByPrimaryKey(ctMagic2WorkMaxPractice);
            if (result != 1) {
                throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
            }

        }
        //endregion
        //提交返回json
        String submitJson = JsonUtil.getMagic2SubmitJson(submitJsonDTOList);
        magic2WorkChallengeDTO.setSubmitJson(submitJson);

        /**
         * 挑战次数+1
         */
        if (cloudTeachRedisService.get("IsMagic2FirstChallenge:" + magic2WorkChallenge.getPracticeId()).equals("1")){
            cloudTeachRedisService.set("Magic2ChallengeTimes:" + magic2WorkChallenge.getPracticeId(), Integer.toString(1),60000);
        }else{
            int challengeTimes = Integer.parseInt(cloudTeachRedisService.get("Magic2ChallengeTimes:" + magic2WorkChallenge.getPracticeId()));
            cloudTeachRedisService.set("Magic2ChallengeTimes:" + magic2WorkChallenge.getPracticeId(), Integer.toString(challengeTimes+1),60000);
        }
        logger.info("-----------挑战次数challengeTimes：----------------------"+cloudTeachRedisService.get("Magic2ChallengeTimes:" + magic2WorkChallenge.getPracticeId()));
        /**
         * 记录本次挑战id，用于下次出题用
         */
        cloudTeachRedisService.set("Magic2ChallengeID:" + magic2WorkChallenge.getPracticeId(), magic2WorkChallenge.getChallengeId(),60000);
        logger.info("返回magic2WorkChallengeDTO");
        return magic2WorkChallengeDTO;
    }

    /**
     * 获取提分宝练习最高分
     * @param practiceId
     * @return
     */
    public Magic2WorkMaxPracticeDTO getMagic2WorkMaxPractice(String practiceId,String schoolId){
        if (StringUtils.isNotEmpty(practiceId)){
            Magic2WorkMaxPracticeDTO magic2WorkMaxPracticeDTO = new Magic2WorkMaxPracticeDTO();
            CtMagic2WorkMaxPractice ctMagic2WorkMaxPractice = ctMagic2WorkMaxPracticeMapper.selectByPrimaryKey(practiceId,schoolId);
            if (ctMagic2WorkMaxPractice == null){
                return null;
            }else{
                BeanUtils.copyProperties(ctMagic2WorkMaxPractice,magic2WorkMaxPracticeDTO);
                return magic2WorkMaxPracticeDTO;
            }
        }else{
            return null;
        }

    }

    /**
     * 获取某一课程下的挑战记录
     * @param schoolId
     * @param unitId
     * @param userId
     * @return
     */
    public List<Magic2WorkChallengeDTO> getChallengeListByUnitId(String schoolId, String unitId, String userId) {
        List<CtMagic2WorkChallenge> challengeList = ctMagic2WorkChallengeMapper.selectChallengeList(schoolId, unitId, userId);

        List<Magic2WorkChallengeDTO> practiceDTOs = new ArrayList<>();
        Magic2WorkChallengeDTO practiceDTO;
        for (CtMagic2WorkChallenge challenge : challengeList) {
            practiceDTO = new Magic2WorkChallengeDTO();
            BeanUtils.copyProperties(challenge, practiceDTO);
            practiceDTOs.add(practiceDTO);
        }

        return practiceDTOs;
    }

    /**
     * 报告问题时插入问题反馈表记录
     * @param questFeedbackDTO
     * @return
     */
    public int insertQuestFeedback(QQuestFeedbackDTO questFeedbackDTO,String challengeId,String schoolId){
        int qType = questFeedbackDTO.getqType();
        /**获取课本id(bookId)*/
        UnitsDTO unitsDTO = this.getUnit(questFeedbackDTO.getUnitId());
        questFeedbackDTO.setBookId(unitsDTO.getBookId());
        /**反馈类型可能多个，多个反馈类型分为多条记录*/
        String fbType = questFeedbackDTO.getFbType().toString();
        char[] chars = fbType.toCharArray();
        List<QQuestFeedback> questFeedbacks1 = new ArrayList<>();
        QQuestFeedback questFeedback1;
        for (char c:chars){
            questFeedback1 = new QQuestFeedback();
            BeanUtils.copyProperties(questFeedbackDTO,questFeedback1);
            int fType = Character.getNumericValue(c);
            if ( fType< 9){
                questFeedback1.setFbType(fType);
                questFeedback1.setFbContent("");
            }else{
                questFeedback1.setFbType(fType);
            }
            questFeedbacks1.add(questFeedback1);
        }
        List<QQuestFeedback> questFeedbacks = new ArrayList<>();//总记录
        /**多条反馈记录*/
        for (QQuestFeedback q:questFeedbacks1) {
            //题目类型为填空题
            if (qType == 3) {
                logger.info("/**填空题且反馈类型为应该接受我的答案：错误的答案增加记录*/");
                if (q.getFbType()==1){
                    /**查询挑战题目答案*/
                    List<CtMagic2WorkChallengeQueAnswer> challengeQueAnswers =
                            ctMagic2WorkChallengeQueAnswerMapper.selectQueAnswer(schoolId, challengeId, questFeedbackDTO.getQueId());
                    QQuestFeedback questFeedback;
                    for (CtMagic2WorkChallengeQueAnswer queAnswer : challengeQueAnswers) {
                        //错误的答案
                        if (queAnswer.getIsstuans() != queAnswer.getIscorrect()){
                            questFeedback = new QQuestFeedback();
                            BeanUtils.copyProperties(q, questFeedback);
                            questFeedback.setHandleTime(new Date(0));
                            questFeedback.setHandleUserId("");
                            questFeedback.setHandleUserName("");
                            questFeedback.setaId(queAnswer.getaId());
                            questFeedback.setUserAnswer(queAnswer.getScontent());
                            questFeedback.setStatus(0);
                            questFeedbacks.add(questFeedback);
                        }
                    }
                }else{
                    logger.info("/**填空题其他反馈类型为一条记录*/");
                    QQuestFeedback questFeedback = new QQuestFeedback();
                    BeanUtils.copyProperties(q, questFeedback);
                    questFeedback.setFbId(UUID.randomUUID().toString().replace("-", ""));
                    questFeedback.setaId("");
                    questFeedback.setUserAnswer("");
                    questFeedback.setHandleTime(new Date(0));
                    questFeedback.setHandleUserId("");
                    questFeedback.setHandleUserName("");
                    questFeedback.setStatus(0);
                    questFeedbacks.add(questFeedback);
                }
            } else {
                logger.info("/**其他题目为一条记录*/");
                QQuestFeedback questFeedback = new QQuestFeedback();
                BeanUtils.copyProperties(q, questFeedback);
                questFeedback.setFbId(UUID.randomUUID().toString().replace("-", ""));
                questFeedback.setaId("");
                questFeedback.setUserAnswer("");
                questFeedback.setHandleTime(new Date(0));
                questFeedback.setHandleUserId("");
                questFeedback.setHandleUserName("");
                questFeedback.setStatus(0);
                questFeedbacks.add(questFeedback);
            }
        }
        if (CollectionUtils.isEmpty(questFeedbacks)){
            return 0;
        }else{
            /**插入记录数小于0报错*/
            int result = questFeedbackMapper.batchInsert(questFeedbacks);
            if (result < 1) {
                throw new CloudteachException(CloudTeachErrorEnum.INSERTDISKFAILED.getMsg(), CloudTeachErrorEnum.INSERTDISKFAILED.getCode());
            }
            /**
             * 更改题目状态（0：作废| 1：启用|2：已检查）
             */
            if (fbType.contains("2") || fbType.contains("3") || fbType.contains("4")) {
                QQuest qQuest = qQuestMapper.selectByPrimaryKey(questFeedbackDTO.getQueId());
                magic2QuestionService.updateStatus(questFeedbackDTO.getQueId(),1);
                if (qQuest.getSort()>0 && qQuest.getParentId() != null){
                    /**衍生题对应的原题状态改为1*/
                    magic2QuestionService.updateStatus(qQuest.getParentId(),1);
                }
            }
            return result;
        }

    }

    /**
     * 超时题目判断
     * @param ctMagic2WorkChallengeQue
     */
    private void overTimeJudge(CtMagic2WorkChallengeQue ctMagic2WorkChallengeQue){
        String avTimeStr = cloudTeachRedisService.get("Magic2AvTime:" + ctMagic2WorkChallengeQue.getQueId());
        if (com.alibaba.dubbo.common.utils.StringUtils.isEmpty(avTimeStr)) {
            avTimeStr = "0";
        }
        long avTime = Long.parseLong(avTimeStr);
        if (avTime==0){
            avTime = Constants.MAGIC2_WORK_QUEST_AVGTIME;
        }
        if (ctMagic2WorkChallengeQue.getQueTime()>2*avTime){//完成时间大于平均时间两倍时，记为超时题
            ctMagic2WorkChallengeQue.setStatus(2);
        }else{
            ctMagic2WorkChallengeQue.setStatus(1);
        }
    }
    /**
     * 自动获取提分宝评分
     *
     * @param magic2WorkChallenge
     * @param magic2StuAnsJsonDTOList
     * @return
     */
    private Magic2WorkChallengeDTO magic2WorkAutoScore(String identityId,Magic2WorkChallengeDTO magic2WorkChallenge,
                                                       List<Magic2StuAnsJsonDTO> magic2StuAnsJsonDTOList,
                                                       int totalRightNum) {
        if (CollectionUtils.isEmpty(magic2StuAnsJsonDTOList)) {
            return null;
        }

        //系统平均用时（毫秒）
        long totalAvTime = 0;
        //本次挑战用时
        long thisTime = 0;
        //答对题数
        int rightNum = 0;
        //题目总用时
        long totalQTime = 0;

        //region 题目循环
        for (Magic2StuAnsJsonDTO magic2StuAnsJsonDTO : magic2StuAnsJsonDTOList) {
            //redis获取题目对应系统平均用时
            String avTimeStr = cloudTeachRedisService.get("Magic2AvTime:" + magic2StuAnsJsonDTO.getQueId());
            //redis获取题目对应累计挑战次数
            String timesStr = cloudTeachRedisService.get("Magic2Times:" + magic2StuAnsJsonDTO.getQueId());
            if (com.alibaba.dubbo.common.utils.StringUtils.isEmpty(avTimeStr)) {
                avTimeStr = "0";
            }
            if (com.alibaba.dubbo.common.utils.StringUtils.isEmpty(timesStr)) {
                timesStr = "0";
            }
            long avTime = Long.parseLong(avTimeStr);
            if (avTime == 0) {
                //初始化为30000ms
                avTime = Constants.MAGIC2_WORK_QUEST_AVGTIME;
            }
            int times = Integer.parseInt(timesStr);

            totalAvTime = totalAvTime + avTime;
            long qTime = magic2StuAnsJsonDTO.getQueTime();
            totalQTime = totalQTime + qTime;
            // 20151101,按照产品要求,错误题目花费的时间不进入计算
            if (magic2StuAnsJsonDTO.getRw() == 1) {
                thisTime = thisTime + qTime;
            } else {
                thisTime = thisTime + avTime;
            }

            long newAvtime = (avTime * times + thisTime) / (times + 1);
            /**
             * 如果是学生
             */
            if (Constants.IDENTIFY_STUDENT.equals(identityId)) {
                //设置redis题目对应系统平均用时
                cloudTeachRedisService.set("Magic2AvTime:" + magic2StuAnsJsonDTO.getQueId(), Long.toString(newAvtime), 60000);
                //设置redis题目对应累计挑战次数
                cloudTeachRedisService.set("Magic2Times:" + magic2StuAnsJsonDTO.getQueId(), Integer.toString(times + 1), 60000);
            }

        }
        //endregion
        rightNum = totalRightNum;
        //时间权数
        float wt = 1f * totalAvTime / thisTime;
        if (wt > 1) {
            wt = 1f;
        }
        //正确率
        float aRate = 0f;
        aRate = 1f * rightNum / magic2StuAnsJsonDTOList.size();

        float wa = aRate * wt;

        int score = 0;
        String scoreContext = "";
        /**错误率>rate的题目数*/
        int wrongOriQuestNum = Integer.parseInt(cloudTeachRedisService.
                get("WrongOriQuestNum:" + magic2WorkChallenge.getUserId() + ":" + magic2WorkChallenge.getUnitId()));
        /**查询该课程下该用户挑战最高分*/
        CtMagic2WorkMaxPractice practice = ctMagic2WorkMaxPracticeMapper.
                selectByUnitIdAndUserId(magic2WorkChallenge.getUnitId(), magic2WorkChallenge.getSchoolId(), magic2WorkChallenge.getUserId());
        int challengeTimes = ctMagic2WorkChallengeMapper.selectCount(magic2WorkChallenge.getUnitId(),magic2WorkChallenge.getUserId(), magic2WorkChallenge.getSchoolId());
        //region 新规则
        if (practice != null) {
            /**新规则：3.清华北大评价必须每一组题目都至少做过一遍，无错题率大于0.2的题目组*/
            if (wa > 0.9f && wa <= 1f && wrongOriQuestNum == 0 && challengeTimes > 6) {
                score = 10;
            } else if (wa > 0.8f) {
                score = 9;
            } else if (wa > 0.7f) {
                score = 8;
            } else if (wa > 0.6f) {
                score = 7;
            } else if (wa > 0.5f) {
                score = 6;
            } else {
                score = 5;
            }
            /**新规则：2.每次挑战获得的评价最多只能比最高评价高1级*/
            if (score > practice.getMaxScore() + 1) {
                score = practice.getMaxScore() + 1;
            }
        }else {
            /**第一次挑战等级为5*/
            score = 5;
        }
        //endregion

        //region 获取成绩描述
            switch (score) {
                case 10:
                    scoreContext = Constants.MAGIC_WORK_SCORE_CONTEXT10;
                    break;
                case 9:
                    scoreContext = Constants.MAGIC_WORK_SCORE_CONTEXT9;
                    break;
                case 8:
                    scoreContext = Constants.MAGIC_WORK_SCORE_CONTEXT8;
                    break;
                case 7:
                    scoreContext = Constants.MAGIC_WORK_SCORE_CONTEXT7;
                    break;
                case 6:
                    scoreContext = Constants.MAGIC_WORK_SCORE_CONTEXT6;
                    break;
                case 5:
                    scoreContext = Constants.MAGIC_WORK_SCORE_CONTEXT5;
                    break;
                default:
                    scoreContext = Constants.MAGIC_WORK_SCORE_CONTEXT5;
            }
            //endregion

            magic2WorkChallenge.setScore(score);
            magic2WorkChallenge.setScoreContext(scoreContext);

            //region 结果建议级别
            //错误题数
            int wQNum = magic2WorkChallenge.getTotalQuenum() - magic2WorkChallenge.getRightQuenum();
            //结果建议级别
            if (wQNum > 0 && totalQTime > totalAvTime) {
                magic2WorkChallenge.setLevel(1);
            } else if (wQNum > 0) {
                magic2WorkChallenge.setLevel(2);
            } else if (totalQTime > totalAvTime) {
                magic2WorkChallenge.setLevel(3);
            } else if (challengeTimes < 3) {
                magic2WorkChallenge.setLevel(3);
            } else {
                magic2WorkChallenge.setLevel(0);
            }
            //endregion
            return magic2WorkChallenge;
    }

}
