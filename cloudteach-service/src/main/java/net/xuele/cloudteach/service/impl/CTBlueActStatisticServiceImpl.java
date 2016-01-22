package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.persist.CTBlueActStatisticMapper;
import net.xuele.cloudteach.service.CTBlueActStatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * CTBlueActStatisticServiceImpl
 *      用于蓝色感恩月活动定时统计的服务
 * @author sunxh
 * @date 15/11/2
 */
@Service
public class CTBlueActStatisticServiceImpl implements CTBlueActStatisticService {

    @Autowired
    private CTBlueActStatisticMapper ctBlueActStatisticMapper;

    private static Logger logger = LoggerFactory.getLogger(CTBlueActStatisticServiceImpl.class);


    /**
     * 学校学生完成作业次数
     * @param schoolId  学校ID
     * @param start     开始时间
     * @param end       结束时间
     * @return  Long 次数
     */
    @Override
    public Long stuFinishWorkTimes(String schoolId, Date start, Date end) {

        Long times = ctBlueActStatisticMapper.stuFinishWorkTimes(schoolId,start,end);

        if (times == null){
            times = 0L;
        }

        logger.warn("查询学校学生完成作业次数：schoolId={}; start={}; end={}.",schoolId,start,end);

        return times;
    }

    /**
     * 学校教师布置作业次数
     * @param schoolId  学校ID
     * @param start     开始时间
     * @param end       结束时间
     * @return  Long 次数
     */
    @Override
    public Long teacherPublishWorkTimes(String schoolId, Date start, Date end) {

        Long times = ctBlueActStatisticMapper.teacherPublishWorkTimes(schoolId, start, end);

        if (times == null){
            times = 0L;
        }

        logger.warn("查询学校教师布置作业次数：schoolId={}; start={}; end={}.",schoolId,start,end);

        return times;
    }

    /**
     * 学校教师批改作业次数
     * @param schoolId  学校ID
     * @param start     开始时间
     * @param end       结束时间
     * @return  Long 次数
     */
    @Override
    public Long teacherCorrectWorkTimes(String schoolId, Date start, Date end) {

        Long times = ctBlueActStatisticMapper.teacherCorrectWorkTimes(schoolId, start, end);

        if (times == null){
            times = 0L;
        }

        logger.warn("查询学校教师批改作业次数：schoolId={}; start={}; end={}.",schoolId,start,end);

        return times;
    }
}
