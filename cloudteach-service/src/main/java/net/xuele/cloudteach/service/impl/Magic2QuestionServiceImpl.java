package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.CtMagic2WorkChallenge;
import net.xuele.cloudteach.domain.QAnswer;
import net.xuele.cloudteach.domain.QQuest;
import net.xuele.cloudteach.dto.QAnswerDTO;
import net.xuele.cloudteach.dto.QQuestDTO;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.CloudTeachRedisService;
import net.xuele.cloudteach.service.Magic2QuestionService;
import net.xuele.cloudteach.service.util.StringUtil;
import net.xuele.cloudteach.view.Magic2FirstQuestView;
import net.xuele.cloudteach.view.QueAndSortView;
import net.xuele.common.exceptions.CloudteachException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Magic2QuestionServiceImpl
 * 提分宝题目服务
 *
 * @author panglx
 * @date on 2015/10/21 0021.
 */
@Service
public class Magic2QuestionServiceImpl implements Magic2QuestionService {

    @Autowired
    private QQuestMapper qQuestMapper;
    @Autowired
    private QAnswerMapper qAnswerMapper;
    @Autowired
    private CtMagic2WorkChallengeQueMapper challengeQueMapper;
    @Autowired
    CloudTeachRedisService cloudTeachRedisService;
    @Autowired
    CtMagic2WorkMaxPracticeMapper ctMagic2WorkMaxPracticeMapper;
    @Autowired
    CtMagic2WorkChallengeMapper challengeMapper;

	private static Logger logger = LoggerFactory.getLogger(Magic2QuestionServiceImpl.class);
    /**
     * 根据课程id，随机查询原题中的num道题目
     *
     * @param unitId
     * @param num
     * @return
     */
    @Override
    public List<QQuestDTO> selectOriQuestByUnitId(String unitId, int num) {
        List<QQuest> qQuests = qQuestMapper.selectByUnitId(unitId, num);
        return this.qQuestsTOqQuestDTOs(qQuests);
    }

    /**
     * 根据课程id，随机查询原题中的num道题目(新规则)
     * @param unitId
     * @param schoolId
     * @param userId
     * @param num
     * @param rate
     * @return
     */
    public List<QQuestDTO> selectOriQuestByUnitId2(String unitId, String schoolId,String userId,int num,double rate) {
        /**
         * 改课程下所有原题
         */
        List<QQuest> qQuests = qQuestMapper.selectAllOriByUnitId(unitId);
        int totalNum = qQuests.size();
        /**
         * 该课程下某个用户错误率大于rate的题目
         */
        List<String> wrongOriQuestId = challengeQueMapper.selectWrongOriQuestId(schoolId,userId,unitId,rate);
        int wrongNum = wrongOriQuestId.size();
        /**
         * 该课程下某个用户挑战过的题目
         */
        List<String> chllengedQuestId = challengeQueMapper.selectChallengedQuestId(schoolId, userId, unitId);
        int challengedNum = chllengedQuestId.size();
        /**
         * 总题目数<=出题数
         */
        if (totalNum<=num){
            return this.qQuestsTOqQuestDTOs(qQuests);
        }else if (CollectionUtils.isEmpty(chllengedQuestId)){
            return this.qQuestsTOqQuestDTOs(qQuests.subList(0,num));
        }
        /**
         * 出题规则：
         * 1.优先级
         * 未练习(原题)>错题>其他
         * 错题：一个用户在一个原题及衍生题的练习中，错误次数/总完成次数<=0.2,该参数后期需可调
         * 2.同优先级的题目随机出现
         */
        List<QQuest> resultQuests = new ArrayList<>();
        resultQuests.addAll(qQuests);
        List<QQuest> challengeQuests = new ArrayList<>();//挑战过的题目
        for (Iterator it = qQuests.iterator(); it.hasNext(); ){
            QQuest quest = (QQuest)it.next();
            for (int i=0;i<challengedNum;i++){
                if (quest.getqId().equals(chllengedQuestId.get(i))){
                    challengeQuests.add(quest);
                    break;
                }
            }
        }
        /**
         * 未挑战的题目
         */
        resultQuests.removeAll(challengeQuests);
        int resultNum = resultQuests.size();
        if (resultNum>=num){
            return this.qQuestsTOqQuestDTOs(resultQuests.subList(0,num));
        }
        /**
         * 未挑战的题目 不够时从错误率高的题目中选题
         */
        List<QQuest> wrongQuests = new ArrayList<>();//挑战过的题目
        if (wrongNum != 0){
            for (Iterator it = challengeQuests.iterator(); it.hasNext(); ) {//错题
                /**
                 * 错题
                 */
                QQuest quest = (QQuest) it.next();
                for (int j=0;j<wrongNum;j++){
                    if (quest.getqId().equals(wrongOriQuestId.get(j))){
                        wrongQuests.add(quest);
                        resultQuests.add(quest);
                        resultNum = resultNum+1;
                        break;
                    }
                }
                if (resultNum == num){
                    return this.qQuestsTOqQuestDTOs(resultQuests);
                }
            }
        }

        /**
         * 挑战过的其他题目
         */
        challengeQuests.removeAll(wrongQuests);
        resultQuests.addAll(challengeQuests.subList(0, num - resultNum));
        //List<QQuest> qQuests = qQuestMapper.selectByUnitId(unitId, num);
        return this.qQuestsTOqQuestDTOs(resultQuests);
    }

    /**
     * 查询课程下所有题目
     * @param unitId
     * @return
     */
    private QQuestDTO[][] selectAllQuestByUnit(String unitId){
        List<QQuest> qQuests = qQuestMapper.selectAllQuestByUnitId(unitId);
        if (CollectionUtils.isEmpty(qQuests)){
            //该课程下不存在提分宝题目或者不存在对应子题目
            throw new CloudteachException(CloudTeachErrorEnum.QUESTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.QUESTNOTFOUND.getCode());
        }
        //最大sort
        int maxSort = Constants.MAGIC2_WORK_MAX_SORT;
        int oriNum = qQuestMapper.selectOriNum(unitId);

        QQuestDTO[][] questDTOs = new QQuestDTO[oriNum][maxSort+1];
        List<QQuestDTO> qQuestDTOs = this.qQuestsTOqQuestDTOs(qQuests);

        QQuestDTO q;
        int j0=0;
        int j1=0;
        int j2=0;
        int j3=0;
        int j4=0;
        for (int i=0;i<qQuests.size();i++){
            q = qQuestDTOs.get(i);
            switch (q.getSort()){
                case 0:
                    questDTOs[i][0] = q;
                    j0=i;
                    break;
                case 1:
                    questDTOs[i-j0-1][1] = q;
                    j1=i;
                    break;
                case 2:
                    questDTOs[i-j1-1][2] = q;
                    j2=i;
                    break;
                case 3:
                    questDTOs[i-j2-1][3] = q;
                    j3=i;
                    break;
                case 4:
                    questDTOs[i-j3-1][4] = q;
                    j4=i;
                    break;
                case 5:
                    questDTOs[i-j4-1][5] = q;
                    break;
                default:
                    break;
            }
        }

        return questDTOs;

    }

    /**
     * 根据课程id，随机查询num道题目(新规则)
     * @param unitId
     * @param practiceId
     * @param schoolId
     * @param userId
     * @param num
     * @param rate
     * @return
     */
    public List<QQuestDTO> queryRdQuestByUnitId(String unitId, String practiceId,String schoolId,String userId,int num,double rate) {
        /**
         * 改课程下所有题
         */
        QQuestDTO[][] questDTOs = this.selectAllQuestByUnit(unitId);
        int colNum = questDTOs[0].length;
        /**
         * 该课程下某个用户错误率大于rate的题目
         */
        List<String> wrongOriQuestId = challengeQueMapper.selectWrongOriQuestId(schoolId, userId, unitId, rate);
        /**该用户该课程下的错误率>rate的题目数*/
        cloudTeachRedisService.set("WrongOriQuestNum:" + userId+":"+unitId, Integer.toString(wrongOriQuestId.size()),6000);
        /**
         * 该课程下某个用户挑战过的题目
         */
        List<String> challengedQuestId = challengeQueMapper.selectChallengedQuestId(schoolId, userId, unitId);
        /**
         * 该课程下某个用户挑战过的超时题目
         */
        List<QueAndSortView> overTimeQuestId = challengeQueMapper.selectOverTimeQuestId(schoolId, userId, unitId);
        if (StringUtils.isEmpty(practiceId)) {
            /**
             * practiceId为空，第一次挑战
             */
            return this.selectFirstChallengeQuest(questDTOs,wrongOriQuestId,challengedQuestId,practiceId,overTimeQuestId,num);
        }else{
            /**
             * 再次挑战
             */
            int challengeTimes = Integer.parseInt(cloudTeachRedisService.get("Magic2ChallengeTimes:" + practiceId));
            String challengeId = cloudTeachRedisService.get("Magic2ChallengeID:" + practiceId);
            logger.info("-----------挑战次数challengeTimes：----------------------"+challengeTimes);
            CtMagic2WorkChallenge challenge = challengeMapper.selectByPrimaryKey(challengeId,schoolId);
            if (challenge.getScore()>=9){
                /**重点及以上重新出题*/
                return this.selectFirstChallengeQuest(questDTOs,wrongOriQuestId,challengedQuestId,practiceId,overTimeQuestId,num);
            }else if (challengeTimes>=colNum){
                /**遍历完6次，重新出题*/
                return this.selectFirstChallengeQuest(questDTOs,wrongOriQuestId,challengedQuestId,practiceId,overTimeQuestId,num);
            }else{
                /**再次挑战*/
                return this.selectAgainChallengeQuest(schoolId,challengeId,practiceId,questDTOs);
            }
        }

    }

    /**
     * 第一次挑战组里随机取题
     * @param questDTOs
     * @param resultIds
     * @param sorts
     * @return
     */
    private List<QQuestDTO> getRdColQuest(QQuestDTO[][] questDTOs,List<String> resultIds,String practiceId,int[] sorts){
        int totalNum = questDTOs.length;
        List<QQuestDTO> resultQuests = new ArrayList<>();
        String qId;
        int col=0;
        for (int i=0;i<resultIds.size();i++){
            qId = resultIds.get(i);
            for (int row=0;row<totalNum;row++){
                if (qId.equals(questDTOs[row][0].getqId())){
                    col = sorts[i];
                    resultQuests.add(questDTOs[row][col]);
                    break;
                }
            }
        }
        /**
         * 是否重新出题
         */
        cloudTeachRedisService.set("IsMagic2FirstChallenge:" + practiceId, Integer.toString(1),60000);
        return resultQuests;
    }

    /**
     * 获取最后原题id（避免已挑战删除的题目）
     * @param questDTOs
     * @param resultIds
     * @return
     */
    private void getResultQIds(QQuestDTO[][] questDTOs,List<String> resultIds){
        int totalNum = questDTOs.length;
        String qId;
        List<String> resultIds2 = new ArrayList<>();
        for (int i=0;i<resultIds.size();i++){
            qId = resultIds.get(i);
            for (int row=0;row<totalNum;row++){
                if (qId.equals(questDTOs[row][0].getqId())){
                    resultIds2.add(qId);
                    break;
                }
            }
        }
        resultIds.retainAll(resultIds2);
    }
    /**
     * 开始挑战
     * @param questDTOs
     * @param wrongOriQuestId
     * @param challengedQuestId
     * @param overTimeQuest
     * @param num
     * @return
     */
    private List<QQuestDTO> selectFirstChallengeQuest(QQuestDTO[][] questDTOs,List<String> wrongOriQuestId,List<String> challengedQuestId,String practiceId,List<QueAndSortView> overTimeQuest,int num){
        int totalNum = questDTOs.length;
        int colNum = questDTOs[0].length;
        int wrongNum = wrongOriQuestId.size();
        List<QQuestDTO> questDTOList = new ArrayList<>();
        int overTimeNum = overTimeQuest.size();

        if (totalNum<=num) {
            /**
             * 总题目数<=出题数
             */
            if (CollectionUtils.isNotEmpty(overTimeQuest)) {//有超时题目优先出超时题目
                int[] sorts = StringUtil.getRandomNum(totalNum, colNum);
                int col = 0;
                for (int row = 0; row < totalNum; row++) {
                    col = sorts[row];
                    for (QueAndSortView q:overTimeQuest){
                        if (q.getQueId().equals(questDTOs[row][0].getqId())) {
                            col = q.getSort();
                            break;
                        }
                    }
                    questDTOList.add(questDTOs[row][col]);
                }
            } else{
                int[] sorts = StringUtil.getRandomNum(totalNum,colNum);
                int col = 0;
                for (int row=0;row<totalNum;row++){
                    col = sorts[row];
                    questDTOList.add(questDTOs[row][col]);
                }
            }
            /**
             * 是否重新出题
             */
            cloudTeachRedisService.set("IsMagic2FirstChallenge:" + practiceId, Integer.toString(1),60000);
            return questDTOList;
        }else if (CollectionUtils.isEmpty(challengedQuestId)){//未挑战过
            int[] sorts = StringUtil.getRandomNum(num,colNum);
            /**随机取num个totalNum内的数字*/
            List<Integer> rows = StringUtil.getRandomRow(num, totalNum);
            int col = 0;
            int row = 0;//行号，用于打乱题目顺序
            for (int i=0;i<num;i++){
                col = sorts[i];
                row = rows.get(i);
                questDTOList.add(questDTOs[row][col]);
            }
            /**
             * 是否重新出题
             */
            cloudTeachRedisService.set("IsMagic2FirstChallenge:" + practiceId, Integer.toString(1),60000);
            return questDTOList;
        }else{
            /**
             * 出题规则：
             * 1.优先级
             * 未练习(原题)>错题>超时题>其他
             * 错题：一个用户在一个原题及衍生题的练习中，错误次数/总完成次数<=0.2,该参数后期需可调
             * 2.同优先级的题目随机出现
             */
            List<String> resultQuests = new ArrayList<>();
            for (int i=0;i<totalNum;i++){
                resultQuests.add(questDTOs[i][0].getqId());
            }

            int[] sorts = StringUtil.getRandomNum(num,colNum);
            /**
             * 未挑战的题目ID
             */
            resultQuests.removeAll(challengedQuestId);
            int resultNum = resultQuests.size();
            if (resultNum>=num){
                return this.getRdColQuest(questDTOs,resultQuests.subList(0, num),practiceId,sorts);
            }
            /**挑战过的题目去除错题*/
            challengedQuestId.removeAll(wrongOriQuestId);
            /**
             * 未挑战的题目 不够时从错误率高的题目中选题
             */
            if (wrongNum != 0){
                /**错题去除已删除的题目*/
                this.getResultQIds(questDTOs,wrongOriQuestId);
                resultQuests.addAll(wrongOriQuestId);
                resultNum = resultQuests.size();
                if (resultNum >= num){
                    return this.getRdColQuest(questDTOs,resultQuests.subList(0, num),practiceId,sorts);
                }
            }

            /**
             * 去除错误率高的超时题
             */
            List<String> overTimeQuestId = new ArrayList<>();
            if (overTimeNum != 0 ){
                for (QueAndSortView q:overTimeQuest) {//超时题
                    overTimeQuestId.add(q.getQueId());
                }
                overTimeQuestId.removeAll(wrongOriQuestId);

                /**挑战过的题目去除超时题*/
                challengedQuestId.removeAll(overTimeQuestId);

                /**超时题去除已删除的题目*/
                this.getResultQIds(questDTOs,overTimeQuestId);
                resultQuests.addAll(overTimeQuestId);
                resultNum = resultQuests.size();
                if (resultNum >= num){
                    resultQuests = resultQuests.subList(0, num);
                    /**超时题目出题精确到那一题*/
                    int index;
                    for (QueAndSortView q:overTimeQuest){
                        index = resultQuests.indexOf(q.getQueId());
                        if (index >=0 && index <sorts.length){
                            sorts[index] = q.getSort();
                        }
                    }
                    return this.getRdColQuest(questDTOs,resultQuests,practiceId,sorts);
                }
            }

            /**
             * 挑战过的题目去除已删除的题目
             */
            this.getResultQIds(questDTOs,challengedQuestId);
            resultNum = resultQuests.size();
            resultQuests.addAll(challengedQuestId.subList(0, num - resultNum));
            /**超时题目出题精确到那一题*/
            int index;
            for (QueAndSortView q:overTimeQuest){
                index = resultQuests.indexOf(q.getQueId());
                if (index >=0 && index <sorts.length){
                    sorts[index] = q.getSort();
                }
            }
            questDTOList = this.getRdColQuest(questDTOs,resultQuests,practiceId,sorts);
            if (questDTOList.size()==num){
                return questDTOList;
            }
            return this.getRdColQuest(questDTOs,resultQuests,practiceId,sorts);
        }


    }

    /**
     * 再次挑战
     * @param schoolId
     * @param challengeId
     * @param questDTOs
     * @return
     */
    private List<QQuestDTO> selectAgainChallengeQuest(String schoolId,String challengeId,String practiceId,QQuestDTO[][] questDTOs){
        int totalNum = questDTOs.length;
        List<QQuestDTO> questDTOList = new ArrayList<>();
        List<Magic2FirstQuestView> firstQuestViews = challengeQueMapper.selectFirstQuestId(schoolId, challengeId);
        Magic2FirstQuestView firstQuestView;
        int col=0;
        for (int row=0;row<totalNum;row++){
            for (int i=0;i<firstQuestViews.size();i++){
                firstQuestView = firstQuestViews.get(i);
                if (firstQuestView.getQueId().equals(questDTOs[row][0].getqId())){
                    col = firstQuestView.getSort()+1>5?0:firstQuestView.getSort()+1;
                    questDTOList.add(questDTOs[row][col]);
                    break;
                }
            }
        }
        cloudTeachRedisService.set("IsMagic2FirstChallenge:" + practiceId, Integer.toString(0),60000);
        return questDTOList;
    }

    /**
     * 查询某一题的选项
     *
     * @param qId
     * @return
     */
    @Override
    public List<QAnswerDTO> selectAnswerByQId(String qId) {
        List<QAnswer> qAnswers = qAnswerMapper.selectByQId(qId);

        return this.qAnswersTOqAnswerDTOs(qAnswers);
    }


    /**
     * 顺序获取原题对应的衍生题
     *
     * @param unitId
     * @return
     * @throws CloudteachException
     */
    @Override
    public List<QQuestDTO> queryMagicDeriveQuestListByUnitId(String unitId, int sort, List<String> parentIds) throws CloudteachException {

        List<QQuest> qQuests = qQuestMapper.selectChildByUnitId(unitId, sort, parentIds);

        return this.qQuestsTOqQuestDTOs(qQuests);
    }

    /**
     * 查询衍生题最大sort
     * @param unitId
     * @return
     */
/*    public int queryQQuestMaxSort(String unitId){
        int maxSort = qQuestMapper.queryQQuestMaxSort(unitId);

        return maxSort;
    }*/


    /**
     * 更改题目状态：0：作废| 1：启用|2：已检查
     * @param qId
     * @param status
     * @return
     */
    public int updateStatus(String qId,int status){
        return qQuestMapper.updateStatus(qId,status);
    }

    /**
     * 题目domain对象转DTO
     *
     * @param qQuests
     * @return
     */
    private List<QQuestDTO> qQuestsTOqQuestDTOs(List<QQuest> qQuests) {
        List<QQuestDTO> qQuestDTOs = new ArrayList<>();
        QQuestDTO qQuestDTO;
        for (QQuest qQuest : qQuests) {
            qQuestDTO = new QQuestDTO();
            BeanUtils.copyProperties(qQuest, qQuestDTO);
            qQuestDTOs.add(qQuestDTO);
        }
        return qQuestDTOs;
    }

    /**
     * 题目答案domain对象转DTO
     *
     * @param qAnswers
     * @return
     */
    private List<QAnswerDTO> qAnswersTOqAnswerDTOs(List<QAnswer> qAnswers) {
        List<QAnswerDTO> qAnswerDTOs = new ArrayList<>();
        QAnswerDTO qAnswerDTO;
        for (QAnswer qAnswer : qAnswers) {
            qAnswerDTO = new QAnswerDTO();
            BeanUtils.copyProperties(qAnswer, qAnswerDTO);
            qAnswerDTOs.add(qAnswerDTO);
        }
        return qAnswerDTOs;
    }
/**
 * 获取 原题及其对应的所有衍生题
 *
 * @param unitId
 * @return
 * @throws CloudteachException
 */
/*    public List<QQuestViewDTO> queryMagicOriAndDeriveQuestByUnitId(String unitId, int num) throws CloudteachException {

		logger.info("[校验传入参数unitId: {}]", unitId);
		if (StringUtils.isEmpty(unitId)) {
			throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":UnitId不能为空!",
					CloudTeachErrorEnum.PARAMERROR.getCode());
		}
		List<QQuestViewDTO> dtoList = new ArrayList<>();
		// 获取题目列表
		List<QQuestView> viewList = qQuestMapper.selectFullQuestByUnitId(unitId, num);
		logger.info("[对象转化]");
		for (QQuestView qv : viewList) {
			QQuestViewDTO qd = new QQuestViewDTO();
			BeanUtils.copyProperties(qv, qd);
			List<QQuest> qlist1 = qv.getDeriveQuests();
			List<QQuestDTO> qlist2 = new ArrayList<>();
			for (QQuest q1 : qlist1) {
				QQuestDTO q2 = new QQuestDTO();
				BeanUtils.copyProperties(q1, q2);
				qlist2.add(q2);
			}
			qd.setDeriveQuests(qlist2);
			dtoList.add(qd);
		}
		return dtoList;
    }*/
}
