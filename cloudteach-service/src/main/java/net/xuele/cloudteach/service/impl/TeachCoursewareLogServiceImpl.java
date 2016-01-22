package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.TeachCoursewareLogFromEnum;
import net.xuele.cloudteach.domain.CtCoursewareClassDetailLog;
import net.xuele.cloudteach.domain.CtCoursewareClassLog;
import net.xuele.cloudteach.dto.TeachCoursewareLogDTO;
import net.xuele.cloudteach.dto.TeachCoursewareLogDetailDTO;
import net.xuele.cloudteach.persist.CtCoursewareClassDetailLogMapper;
import net.xuele.cloudteach.persist.CtCoursewareClassLogMapper;
import net.xuele.cloudteach.service.TeachCoursewareLogService;
import net.xuele.common.exceptions.CloudteachException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * TeachCoursewareLogServiceImpl
 *
 * @author sunxh
 * @date 15/12/2
 */
@Service
public class TeachCoursewareLogServiceImpl implements TeachCoursewareLogService {

    @Autowired
    private CtCoursewareClassLogMapper ctCoursewareClassLogMapper;

    @Autowired
    private CtCoursewareClassDetailLogMapper ctCoursewareClassDetailLogMapper;

    private static Logger logger = LoggerFactory.getLogger(TeachCoursewareLogServiceImpl.class);

    /**
     * 列出某个课件的所有授课记录
     *
     * @param cclCoursewaresId 课件ID
     * @param schoolId         学校ID
     * @param cclClassId       班级ID
     * @return List<TeachCoursewareLogDTO>
     */
    @Override
    public List<TeachCoursewareLogDTO> listByCourseware(String cclCoursewaresId, String schoolId, String cclClassId) throws CloudteachException {
        List<TeachCoursewareLogDTO> logDTOList = new ArrayList<>();

        //assert
        Assert.notNull(cclCoursewaresId, "课件ID不能为NULL！");
        Assert.notNull(schoolId, "学校ID不能为NULL！");
        Assert.notNull(cclClassId, "班级ID不能为NULL！");
        logger.warn("收到列出授课记录请求,经校验数据合法：cclCoursewaresId={} ，schoolId={} ，cclClassId={} ", cclCoursewaresId, schoolId, cclClassId);

        //查询数据
        List<CtCoursewareClassLog> logList = ctCoursewareClassLogMapper.selectByCoursewareAndClass(cclCoursewaresId, schoolId, cclClassId);

        for (CtCoursewareClassLog ctCoursewareClassLog : logList) {
            TeachCoursewareLogDTO logDTO = toTeachCoursewareLogDTO(ctCoursewareClassLog);

            //如果开始时间等于结束时间，不返回该条数据
            //fixbug 3524 已于产品沟通沟通，如为0时候不作显示
            //@see http://chandao.neiwang.com/bug-view-3524.html
            if (logDTO.getCclBegtime() == null || logDTO.getCclEndtime() == null
                    || logDTO.getCclBegtime().equals(logDTO.getCclEndtime())) {
                continue;
            }
            logDTOList.add(logDTO);
        }

        logger.warn("授课记录查询完成：list={} ", logDTOList);

        return logDTOList;
    }

    /**
     * 根据记录ID获取一个授课记录
     * 不包括明细数据
     *
     * @param schoolId 学校ID
     * @param logId    授课记录ID
     * @return TeachCoursewareLogDTO
     */
    @Override
    public TeachCoursewareLogDTO getLogById(String schoolId, String logId) throws CloudteachException {
        TeachCoursewareLogDTO logDTO = null;

        //assert
        Assert.notNull(schoolId, "学校ID不能为NULL！");
        Assert.notNull(logId, "记录ID不能为NULL！");
        logger.warn("收到获取授课记录请求,经校验数据合法：logId={} ，schoolId={} ", logId, schoolId);

        //查询数据
        CtCoursewareClassLog log = ctCoursewareClassLogMapper.selectByPrimaryKey(schoolId, logId);
        //转换数据
        logDTO = toTeachCoursewareLogDTO(log);
        logger.warn("授课记录查询完成：logDTO={} ", logDTO);

        return logDTO;
    }

    @Override
    public List<TeachCoursewareLogDetailDTO> listDetailByLogId(String cclId, String schoolId) throws CloudteachException {
        List<TeachCoursewareLogDetailDTO> logDTOList = new ArrayList<>();

        //assert
        Assert.notNull(cclId, "授课记录ID不能为NULL！");
        Assert.notNull(schoolId, "学校ID不能为NULL！");
        logger.warn("收到列出授课记录明细请求,经校验数据合法：cclId={} ，schoolId={} ", cclId, schoolId);

        //查询数据
        List<CtCoursewareClassDetailLog> detailLogs = ctCoursewareClassDetailLogMapper.selectByLogId(cclId, schoolId);

        for (CtCoursewareClassDetailLog detailLog : detailLogs) {
            TeachCoursewareLogDetailDTO logDetailDTO = toTeachCoursewareLogDetailDTO(detailLog);
            logDTOList.add(logDetailDTO);
        }

        logger.warn("授课记录明细查询完成：list={} ", logDTOList);

        return logDTOList;
    }

    /**
     * 记录课件授课开始时间
     * 参数内容：学校ID schoolId [非空]
     * 参数内容：课件ID cclCoursewaresId [非空]
     * 参数内容：班级ID cclClassId [非空]
     * 参数内容：教师ID cclUserId [非空]
     * 参数内容：开始时间 cclBegtime [不需要传入，系统取当前时间，如果传入会被覆盖]
     * 参数内容：结束时间 cclEndtime [不需要传入，系统取当前时间，如果传入会被覆盖]
     *
     * @param teachCoursewareLogDTO 授课记录DTO
     * @return TeachCoursewareLogDTO 目前只需要用到 记录ID 用于后续信息定位关系
     */
    @Override
    public TeachCoursewareLogDTO logTeachingBegin(TeachCoursewareLogDTO teachCoursewareLogDTO) throws CloudteachException {
        //assert
        Assert.notNull(teachCoursewareLogDTO, "授课记录DTO不能为NULL！");
        Assert.notNull(teachCoursewareLogDTO.getCclId(), "打点跟踪ID不能为NULL！");
        Assert.notNull(teachCoursewareLogDTO.getCclCoursewaresId(), "课件ID不能为NULL！");
        Assert.notNull(teachCoursewareLogDTO.getSchoolId(), "学校ID不能为NULL！");
        Assert.notNull(teachCoursewareLogDTO.getCclClassId(), "班级ID不能为NULL！");
        Assert.notNull(teachCoursewareLogDTO.getCclUserId(), "用户ID不能为NULL！");
        logger.warn("收到记录授课开始请求,经校验数据合法：teachCoursewareLogDTO={} ", teachCoursewareLogDTO);

        //初始化信息
        teachCoursewareLogDTO.setCclBegtime(new Date());//开始时间为当前系统时间
        teachCoursewareLogDTO.setCclEndtime(teachCoursewareLogDTO.getCclBegtime());//结束时间为开始时间
        if (teachCoursewareLogDTO.getCclFromType() == null) {
            //默认来源为web浏览器
            teachCoursewareLogDTO.setCclFromType(TeachCoursewareLogFromEnum.FROM_WEB.getValue());
        }

        //转domain对象
        CtCoursewareClassLog ctCoursewareClassLog = toCtCoursewareClassLog(teachCoursewareLogDTO);
        logger.warn("授课记录数据初始化和转换完成：ctCoursewareClassLog={} ", ctCoursewareClassLog);

        //插入数据库
        Integer result = ctCoursewareClassLogMapper.insert(ctCoursewareClassLog);
        logger.warn("授课记录数据插入完成，插入 {} 行记录", result);

        return teachCoursewareLogDTO;
    }

    /**
     * 记录授课明细内容
     * 参数内容：学校ID schoolId [非空]
     * 参数内容：授课记录ID cclId [非空]
     * 参数内容：记录明细类型 ccdlType [非空]
     * 参数内容：记录明细内容 ccdlContext [非空]
     * 参数内容：JSONSTRING ccdlContent1 [允许空]
     * 参数内容：开始时间 cclBegtime [不需要传入，该接口不会处理开始时间]
     * 参数内容：结束时间 cclEndtime [不需要传入，系统取当前时间，如果传入会被覆盖]
     *
     * @param teachCoursewareLogDetailDTO 授课记录明细DTO
     * @return TeachCoursewareLogDetailDTO
     * @throws CloudteachException
     */
    @Override
    public TeachCoursewareLogDetailDTO writeLogDetail(TeachCoursewareLogDetailDTO teachCoursewareLogDetailDTO) throws CloudteachException {
//assert
        Assert.notNull(teachCoursewareLogDetailDTO, "授课记录DTO不能为NULL！");
        Assert.notNull(teachCoursewareLogDetailDTO.getSchoolId(), "学校ID不能为NULL！");
        Assert.notNull(teachCoursewareLogDetailDTO.getCclId(), "授课记录ID不能为NULL！");
        Assert.notNull(teachCoursewareLogDetailDTO.getCcdlType(), "记录明细类型不能为NULL！");
        Assert.notNull(teachCoursewareLogDetailDTO.getCcdlContext(), "记录明细内容不能为NULL！");
        logger.warn("收到记录授课明细请求,经校验数据合法：teachCoursewareLogDetailDTO={} ", teachCoursewareLogDetailDTO);

        //初始化信息
        teachCoursewareLogDetailDTO.setCcdlId(UUID.randomUUID().toString().replace("-", ""));
        teachCoursewareLogDetailDTO.setCcdlBegtime(new Date());//开始时间为当前系统时间
        teachCoursewareLogDetailDTO.setCcdlEndtime(teachCoursewareLogDetailDTO.getCcdlBegtime());//结束时间为开始时间
        //如果content为null，设为空字符串
        if (teachCoursewareLogDetailDTO.getCcdlContent1() == null) {
            teachCoursewareLogDetailDTO.setCcdlContent1("");
        }
        if (teachCoursewareLogDetailDTO.getCcdlContent2() == null) {
            teachCoursewareLogDetailDTO.setCcdlContent2("");
        }
        if (teachCoursewareLogDetailDTO.getCcdlContent3() == null) {
            teachCoursewareLogDetailDTO.setCcdlContent3("");
        }

        //转domain对象
        CtCoursewareClassDetailLog ctCoursewareClassDetailLog = toCtCoursewareClassDetailLog(teachCoursewareLogDetailDTO);
        logger.warn("授课记录明细初始化和转换完成：ctCoursewareClassDetailLog={} ", ctCoursewareClassDetailLog);

        //插入数据库
        Integer result = ctCoursewareClassDetailLogMapper.insert(ctCoursewareClassDetailLog);
        logger.warn("授课记录明细插入完成，插入 {} 行记录", result);

        return teachCoursewareLogDetailDTO;
    }

    /**
     * 记录课件授课进行状态
     * 参数内容：学校ID schoolId [非空]
     * 参数内容：授课记录ID cclId [非空]
     * 参数内容：课件ID cclCoursewaresId [允许空]
     * 参数内容：班级ID cclClassId [允许空]
     * 参数内容：教师ID cclUserId [允许空]
     * 参数内容：开始时间 cclBegtime [不需要传入，该接口不会处理开始时间]
     * 参数内容：结束时间 cclEndtime [不需要传入，系统取当前时间，如果传入会被覆盖]
     *
     * @param teachCoursewareLogDTO 授课记录DTO
     * @return TeachCoursewareLogDTO
     * @throws CloudteachException
     */
    @Override
    public TeachCoursewareLogDTO logTeaching(TeachCoursewareLogDTO teachCoursewareLogDTO) throws CloudteachException {
        //assert
        Assert.notNull(teachCoursewareLogDTO, "授课记录DTO不能为NULL！");
        Assert.notNull(teachCoursewareLogDTO.getCclId(), "授课记录ID不能为NULL！");
        Assert.notNull(teachCoursewareLogDTO.getSchoolId(), "学校ID不能为NULL！");
        logger.warn("收到记录授课结束请求,经校验数据合法：teachCoursewareLogDTO={} ", teachCoursewareLogDTO);

        //DTO数据预处理
        teachCoursewareLogDTO.setCclEndtime(new Date());//结束时间为当前时间

        //转domain对象
        CtCoursewareClassLog ctCoursewareClassLog = toCtCoursewareClassLog(teachCoursewareLogDTO);
        logger.warn("授课记录数据初始化和转换完成：ctCoursewareClassLog={} ", ctCoursewareClassLog);

        //更新数据
        Integer result = ctCoursewareClassLogMapper.updateLogEnd(ctCoursewareClassLog);
        logger.warn("授课记录数据插入完成，插入 {} 行记录", result);

        return teachCoursewareLogDTO;
    }


    //================================================private 方法=================================================

    /**
     * 授课记录DTO转domain对象
     *
     * @param teachCoursewareLogDTO 授课记录DTO
     * @return CtCoursewareClassLog
     */
    private CtCoursewareClassLog toCtCoursewareClassLog(TeachCoursewareLogDTO teachCoursewareLogDTO) {
        if (teachCoursewareLogDTO == null) {
            return null;
        }
        CtCoursewareClassLog ctCoursewareClassLog = new CtCoursewareClassLog();
        ctCoursewareClassLog.setCclId(teachCoursewareLogDTO.getCclId());
        ctCoursewareClassLog.setCclCoursewaresId(teachCoursewareLogDTO.getCclCoursewaresId());
        ctCoursewareClassLog.setCclUserId(teachCoursewareLogDTO.getCclUserId());
        ctCoursewareClassLog.setCclClassId(teachCoursewareLogDTO.getCclClassId());
        ctCoursewareClassLog.setSchoolId(teachCoursewareLogDTO.getSchoolId());
        ctCoursewareClassLog.setCclBegtime(teachCoursewareLogDTO.getCclBegtime());
        ctCoursewareClassLog.setCclEndtime(teachCoursewareLogDTO.getCclEndtime());
        ctCoursewareClassLog.setCclFromType(teachCoursewareLogDTO.getCclFromType());
        return ctCoursewareClassLog;
    }

    /**
     * 授课记录明细DTO转domain对象
     *
     * @param teachCoursewareLogDetailDTO 授课记录明细DTO
     * @return CtCoursewareClassDetailLog
     */
    private CtCoursewareClassDetailLog toCtCoursewareClassDetailLog(TeachCoursewareLogDetailDTO teachCoursewareLogDetailDTO) {
        if (teachCoursewareLogDetailDTO == null) {
            return null;
        }
        CtCoursewareClassDetailLog ctCoursewareClassDetailLog = new CtCoursewareClassDetailLog();
        ctCoursewareClassDetailLog.setCcdlId(teachCoursewareLogDetailDTO.getCcdlId());
        ctCoursewareClassDetailLog.setCclId(teachCoursewareLogDetailDTO.getCclId());
        ctCoursewareClassDetailLog.setCcdlType(teachCoursewareLogDetailDTO.getCcdlType());
        ctCoursewareClassDetailLog.setCcdlContext(teachCoursewareLogDetailDTO.getCcdlContext());
        ctCoursewareClassDetailLog.setCcdlBegtime(teachCoursewareLogDetailDTO.getCcdlBegtime());
        ctCoursewareClassDetailLog.setCcdlEndtime(teachCoursewareLogDetailDTO.getCcdlEndtime());
        ctCoursewareClassDetailLog.setCcdlContent1(teachCoursewareLogDetailDTO.getCcdlContent1());
        ctCoursewareClassDetailLog.setCcdlContent2(teachCoursewareLogDetailDTO.getCcdlContent2());
        ctCoursewareClassDetailLog.setCcdlContent3(teachCoursewareLogDetailDTO.getCcdlContent3());
        ctCoursewareClassDetailLog.setSchoolId(teachCoursewareLogDetailDTO.getSchoolId());
        return ctCoursewareClassDetailLog;
    }

    /**
     * 授课记录domain对象转DTO
     *
     * @param ctCoursewareClassLog 授课记录domain对象
     * @return TeachCoursewareLogDTO
     */
    private TeachCoursewareLogDTO toTeachCoursewareLogDTO(CtCoursewareClassLog ctCoursewareClassLog) {
        if (ctCoursewareClassLog == null) {
            return null;
        }
        TeachCoursewareLogDTO teachCoursewareLogDTO = new TeachCoursewareLogDTO();
        teachCoursewareLogDTO.setCclId(ctCoursewareClassLog.getCclId());
        teachCoursewareLogDTO.setCclCoursewaresId(ctCoursewareClassLog.getCclCoursewaresId());
        teachCoursewareLogDTO.setCclUserId(ctCoursewareClassLog.getCclUserId());
        teachCoursewareLogDTO.setCclClassId(ctCoursewareClassLog.getCclClassId());
        teachCoursewareLogDTO.setSchoolId(ctCoursewareClassLog.getSchoolId());
        teachCoursewareLogDTO.setCclBegtime(ctCoursewareClassLog.getCclBegtime());
        teachCoursewareLogDTO.setCclEndtime(ctCoursewareClassLog.getCclEndtime());
        teachCoursewareLogDTO.setCclFromType(ctCoursewareClassLog.getCclFromType());
        return teachCoursewareLogDTO;
    }

    /**
     * 授课记录明细domain对象转DTO
     *
     * @param detailLog 授课记录明细domain对象
     * @return TeachCoursewareLogDetailDTO
     */
    private TeachCoursewareLogDetailDTO toTeachCoursewareLogDetailDTO(CtCoursewareClassDetailLog detailLog) {
        if (detailLog == null) {
            return null;
        }
        TeachCoursewareLogDetailDTO teachCoursewareLogDetailDTO = new TeachCoursewareLogDetailDTO();
        teachCoursewareLogDetailDTO.setCcdlId(detailLog.getCcdlId());
        teachCoursewareLogDetailDTO.setCclId(detailLog.getCclId());
        teachCoursewareLogDetailDTO.setCcdlType(detailLog.getCcdlType());
        teachCoursewareLogDetailDTO.setCcdlContext(detailLog.getCcdlContext());
        teachCoursewareLogDetailDTO.setCcdlBegtime(detailLog.getCcdlBegtime());
        teachCoursewareLogDetailDTO.setCcdlEndtime(detailLog.getCcdlEndtime());
        teachCoursewareLogDetailDTO.setCcdlContent1(detailLog.getCcdlContent1());
        teachCoursewareLogDetailDTO.setCcdlContent2(detailLog.getCcdlContent2());
        teachCoursewareLogDetailDTO.setCcdlContent3(detailLog.getCcdlContent3());
        teachCoursewareLogDetailDTO.setSchoolId(detailLog.getSchoolId());
        return teachCoursewareLogDetailDTO;
    }

}
