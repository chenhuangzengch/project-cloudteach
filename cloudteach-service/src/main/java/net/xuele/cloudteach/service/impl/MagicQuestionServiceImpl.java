package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.domain.CtMagicBankFinish;
import net.xuele.cloudteach.domain.CtMagicQuestion;
import net.xuele.cloudteach.domain.CtMagicQuestionBank;
import net.xuele.cloudteach.dto.MagicBankFinishDTO;
import net.xuele.cloudteach.dto.MagicQuestionBankDTO;
import net.xuele.cloudteach.dto.MagicQuestionDTO;
import net.xuele.cloudteach.persist.CtMagicBankFinishMapper;
import net.xuele.cloudteach.persist.CtMagicQuestionBankMapper;
import net.xuele.cloudteach.persist.CtMagicQuestionMapper;
import net.xuele.cloudteach.service.MagicQuestionService;
import net.xuele.common.exceptions.CloudteachException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MagicQuestionServiceImpl
 * 提分宝题库服务
 * @author duzg
 * @date 2015/7/13 0002
 */
@Service
public class MagicQuestionServiceImpl implements MagicQuestionService {
    @Autowired
    private CtMagicQuestionBankMapper ctMagicQuestionBankMapper;
    @Autowired
    private CtMagicQuestionMapper ctMagicQuestionMapper;
    @Autowired
    private CtMagicBankFinishMapper ctMagicBankFinishMapper;

    /**
     * @param unitId 课程号
     * @return List<MagicQuestionBankDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据课程ID获取提分宝原题题库列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    @Override
    public List<MagicQuestionBankDTO> queryMagicQuestionBankListByUnitId(String unitId,String extraBookId) throws CloudteachException {
        List<CtMagicQuestionBank> ctMagicQuestionBanklist = ctMagicQuestionBankMapper.queryMagicQuestionBankListByUnitId(unitId,extraBookId);
        if(ctMagicQuestionBanklist==null){
            return null;
        }
        List<MagicQuestionBankDTO> magicQuestionBankDTOlist = entityMagicQuestionBankListToDtoList(ctMagicQuestionBanklist);
        return magicQuestionBankDTOlist;
    }

    /**
     * @param bankId 题库号
     * @return CtMagicQuestionBank
     * @throws net.xuele.common.exceptions.CloudteachException 根据题库ID获取提分宝原题题库信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    @Override
    public MagicQuestionBankDTO selectByPrimaryKey(String bankId) throws CloudteachException {
        CtMagicQuestionBank ctMagicQuestionBank = ctMagicQuestionBankMapper.selectByPrimaryKey(bankId);
        if(ctMagicQuestionBank==null){
            return null;
        }
        MagicQuestionBankDTO magicQuestionBankDTO = new MagicQuestionBankDTO();
        BeanUtils.copyProperties(ctMagicQuestionBank,magicQuestionBankDTO);
        return magicQuestionBankDTO;
    }

    /**
     * @param bankId 题库号
     * @return List<MagicQuestionDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据题库号获取对应原题题目列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    @Override
    public List<MagicQuestionDTO> queryMagicQuestionMasterListByBankId(String bankId) throws CloudteachException {
        List<MagicQuestionDTO> magicQuestionMasterlist = this.queryMagicQuestionListByNum(bankId, 0);
        return magicQuestionMasterlist;
    }

    /**
     * @param bankId 题库号
     * @return List<MagicQuestionDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据题库号随机获取对应衍生题题目列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    @Override
    public List<MagicQuestionDTO> queryMagicQuestionSlaveRandomListByBankId(String bankId) throws CloudteachException {
        //获取衍生题编号列表
        List<Integer> numlist = ctMagicQuestionMapper.queryMagicQuestionSlaveNumList(bankId);
        if(numlist==null || numlist.size()==0){
            return null;
        }

        //获取随机数
        int randomnumber = new Random().nextInt(numlist.size());
        //获取随机数对应的衍生题编号
        Integer orderNum = numlist.get(randomnumber);

        List<MagicQuestionDTO> magicQuestionSlavelist = this.queryMagicQuestionListByNum(bankId, orderNum.intValue());
        return magicQuestionSlavelist;
    }

    /**
     * @param bankId 题库号
     * @return List<MagicQuestionDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据题库号随机获取对应衍生题题目列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    @Override
    public List<MagicQuestionDTO> queryMagicQuestionRandomListByBankId(String bankId) throws CloudteachException {
        //获取衍生题编号列表
        List<Integer> numlist = ctMagicQuestionMapper.queryMagicQuestionSlaveNumList(bankId);
        if(numlist==null || numlist.size()==0){
            return null;
        }

        numlist.add(new Integer(0));

        //获取随机数
        int randomnumber = new Random().nextInt(numlist.size());
        //获取随机数对应的衍生题编号
        Integer orderNum = numlist.get(randomnumber);

        List<MagicQuestionDTO> magicQuestionSlavelist = this.queryMagicQuestionListByNum(bankId, orderNum.intValue());
        return magicQuestionSlavelist;
    }

    /**
     * @param bankId 题库号
     * @param orderNum 第几套衍生题（从1开始，原题=0）
     * @return List<MagicQuestionDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据衍生题套数号获取一套题列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    @Override
    public List<MagicQuestionDTO> queryMagicQuestionListByNum(String bankId,int orderNum) throws CloudteachException {
        List<CtMagicQuestion> ctMagicQuestionlist = ctMagicQuestionMapper.queryMagicQuestionListByBankId(bankId,orderNum);
        if(ctMagicQuestionlist==null){
            return null;
        }
        List<MagicQuestionDTO> magicQuestionlist = entityMagicQuestionListToDtoList(ctMagicQuestionlist);
        return magicQuestionlist;
    }

    /**
     * @param queId 题目号
     * @return MagicQuestionDTO
     * @throws net.xuele.common.exceptions.CloudteachException 根据题目号获取题目信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    @Override
    public MagicQuestionDTO queryMagicQuestion(String queId) throws CloudteachException {
        CtMagicQuestion ctMagicQuestion = ctMagicQuestionMapper.selectByPrimaryKey(queId);
        if(ctMagicQuestion == null){
            return null;
        }
        MagicQuestionDTO magicQuestionDTO = new MagicQuestionDTO();
        BeanUtils.copyProperties(ctMagicQuestion,magicQuestionDTO);
        return magicQuestionDTO;
    }

    /**
     * 查询某一题库下的题目数
     * @param bankId
     * @return
     */
    public int countByBankId(String bankId,int orderNum){

        return ctMagicQuestionMapper.countByBankId(bankId,orderNum);
    }

    /**
     * 根据题目id查询题目详细信息
     * @param queId
     * @return
     * @throws CloudteachException
     */
    public MagicQuestionDTO selectDetailQue(String queId)throws CloudteachException{
        MagicQuestionDTO magicQuestionDTO = this.queryMagicQuestion(queId);//题目信息
        return  magicQuestionDTO;
    }

    /**
     * 根据学生用户ID、题库ID查询完成状态
     * @param userId
     * @param bankId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    @Override
    public MagicBankFinishDTO queryBankFinishStaByUserId(String userId, String bankId, String schoolId) throws CloudteachException {
        List<CtMagicBankFinish> ctMagicBankFinishList = ctMagicBankFinishMapper.selectBankFinishStaByUserId(userId, bankId, schoolId);
        if(ctMagicBankFinishList == null){
            return null;
        }else if(ctMagicBankFinishList.size() > 1){
            throw new CloudteachException(CloudTeachErrorEnum.DATAUNIQUENESS.getMsg(),CloudTeachErrorEnum.DATAUNIQUENESS.getCode());
        }
        MagicBankFinishDTO magicBankFinishDTO = new MagicBankFinishDTO();
        BeanUtils.copyProperties(ctMagicBankFinishList.get(0),magicBankFinishDTO);
        return magicBankFinishDTO;
    }

    //=======================private methods================================//
    private List<MagicQuestionBankDTO> entityMagicQuestionBankListToDtoList(List<CtMagicQuestionBank> datalist) {

        List<MagicQuestionBankDTO> resList = new ArrayList<MagicQuestionBankDTO>();
        for (CtMagicQuestionBank objDATA : datalist) {
            MagicQuestionBankDTO objDTO = new MagicQuestionBankDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }

    private List<MagicQuestionDTO> entityMagicQuestionListToDtoList(List<CtMagicQuestion> datalist) {

        List<MagicQuestionDTO> resList = new ArrayList<MagicQuestionDTO>();
        for (CtMagicQuestion objDATA : datalist) {
            MagicQuestionDTO objDTO = new MagicQuestionDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }
}