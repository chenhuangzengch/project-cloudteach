package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.domain.CtWorkTapeFiles;
import net.xuele.cloudteach.domain.SRSchoolStatistic;
import net.xuele.cloudteach.domain.SRWork;
import net.xuele.cloudteach.domain.TeacherStatistic;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.SRWorkPageRequest;
import net.xuele.cloudteach.persist.CtWorkGatherMapper;
import net.xuele.cloudteach.persist.CtWorkTapeFilesMapper;
import net.xuele.cloudteach.service.SRWorkService;
import net.xuele.cloudteach.service.util.DateTimeUtil;
import net.xuele.cloudteach.service.util.StringUtil;
import net.xuele.cloudteach.service.util.TranslateSRWork;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import net.xuele.member.dto.UserTeacherDTO;
import net.xuele.member.service.TeacherService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by cm.wang on 2015/8/12 0012.
 */
@Service
public class SRWorkServiceImpl implements SRWorkService{

    private static Logger logger = LoggerFactory.getLogger(SRWorkServiceImpl.class);

    @Autowired
    private CtWorkGatherMapper ctWorkGatherMapper;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CtWorkTapeFilesMapper ctWorkTapeFilesMapper;

    @Override
    public Integer getWorkNum(SRParamDTO srParamDTO) throws CloudteachException {

        Date startTime = null;
        if(srParamDTO.getStartTime()!=null && !srParamDTO.getStartTime().isEmpty()){
            startTime = DateTimeUtil.getFormatDateFromString(srParamDTO.getStartTime().trim(),"yyyy-MM-dd HH:mm:ss", "00:00:00");
        }
        Date endTime = null;
        if(srParamDTO.getEndTime()!=null && !srParamDTO.getEndTime().isEmpty()){
            endTime = DateTimeUtil.getFormatDateFromString(srParamDTO.getEndTime().trim(),"yyyy-MM-dd HH:mm:ss","23:59:59");
        }
        int count = ctWorkGatherMapper.getWorkNum(startTime,endTime,srParamDTO.getAreaId(),
                srParamDTO.getSubjectId(),srParamDTO.getGrade(),srParamDTO.getSchoolId(),srParamDTO.getUserId());
        return count;
    }

    @Override
    public PageResponse<SRWorkDTO> getWorkList(SRWorkPageRequest srWorkPageRequest) throws CloudteachException {

        Date startTime = null;
        if(srWorkPageRequest.getStartTime()!=null && !srWorkPageRequest.getStartTime().isEmpty()){
            startTime = DateTimeUtil.getFormatDateFromString(srWorkPageRequest.getStartTime().trim(), "yyyy-MM-dd HH:mm:ss", "00:00:00");
        }
        Date endTime = null;
        if(srWorkPageRequest.getEndTime()!=null && !srWorkPageRequest.getEndTime().isEmpty()){
            endTime = DateTimeUtil.getFormatDateFromString(srWorkPageRequest.getEndTime().trim(), "yyyy-MM-dd HH:mm:ss", "23:59:59");
        }

        Page page = PageUtils.buildPage(srWorkPageRequest);
        int pageSize = srWorkPageRequest.getPageSize();
        List<SRWorkDTO> dtoList = new ArrayList<SRWorkDTO>();

        long count = ctWorkGatherMapper.getWorkNum(startTime,endTime,srWorkPageRequest.getAreaId(),
                srWorkPageRequest.getSubjectId(),srWorkPageRequest.getGrade(),srWorkPageRequest.getSchoolId(),srWorkPageRequest.getUserId());

        if(count-1>=0) {
            //获得没有老师信息的作业数量
            List<SRWork> workList = ctWorkGatherMapper.getWorkList(startTime, endTime, srWorkPageRequest.getAreaId(),
                    srWorkPageRequest.getSubjectId(), srWorkPageRequest.getGrade(),
                    pageSize, page,srWorkPageRequest.getSchoolId(),srWorkPageRequest.getUserId());

            if(CollectionUtils.isNotEmpty(workList)) {
                Map<String, UserTeacherDTO> teacherDTOMap = new HashMap<String, UserTeacherDTO>();

                List<String> workIdList = getWorkIdList(workList);
                List<CtWorkTapeFiles> workTapeFilesList = ctWorkTapeFilesMapper.getWorkTapeFilesByWorkIdList(workIdList);

                for (SRWork srWork : workList) {
                    SRWorkDTO dto = new SRWorkDTO();
                    String userId = srWork.getUserId();
                    String workId = srWork.getWorkId();
                    String schoolId = srWork.getSchoolId();
                    UserTeacherDTO teacherDTO = null;
                    if (teacherDTOMap.containsKey(userId)) {
                        teacherDTO = teacherDTOMap.get(userId);
                    } else {
                        teacherDTO = teacherService.getTeacherInfo(userId, schoolId);
                        if (teacherDTO == null) {
                            throw new CloudteachException(CloudTeachErrorEnum.USERIDNOTNULL.getMsg() + userId, CloudTeachErrorEnum.USERIDNOTNULL.getCode());
                        }
                        teacherDTOMap.put(userId, teacherDTO);
                    }
                    BeanUtils.copyProperties(srWork, dto);
                    dto.setTeacherHeadIcon(teacherDTO.getIcon());
                    dto.setTeacherName(teacherDTO.getRealName());
                    dto.setPositionName(teacherDTO.getPositionName());
                    dto.setPublishTime(DateTimeUtil.getFormatStringDate(srWork.getPublishTime(), "yyyy-MM-dd HH:mm:ss"));

                    for(CtWorkTapeFiles files : workTapeFilesList){
                        if(workId.equals(files.getWorkId())){
                            WorkTapeFilesDTO workTapeFilesDTO = new WorkTapeFilesDTO();
                            BeanUtils.copyProperties(files, workTapeFilesDTO);
                            dto.setWorkTapeFilesDTO(workTapeFilesDTO);
                            break;
                        }
                    }

                    dtoList.add(dto);
                }
                teacherDTOMap = null;
            }
        }
        //返回PageResponse
        PageResponse<SRWorkDTO> pageResponse = new PageResponse<SRWorkDTO>();
        PageUtils.buldPageResponse(srWorkPageRequest, pageResponse);
        pageResponse.setRows(dtoList);
        pageResponse.setRecords(count);
        return pageResponse;
    }

    @Override
    public PageResponse<SRSchoolStatisticDTO> getSchoolStatistic(SRWorkPageRequest srWorkPageRequest) throws CloudteachException {

        Date startTime = null;
        if(srWorkPageRequest.getStartTime()!=null && !srWorkPageRequest.getStartTime().isEmpty()){
            startTime = DateTimeUtil.getFormatDateFromString(srWorkPageRequest.getStartTime().trim(), "yyyy-MM-dd HH:mm:ss", "00:00:00");
        }
        Date endTime = null;
        if(srWorkPageRequest.getEndTime()!=null && !srWorkPageRequest.getEndTime().isEmpty()){
            endTime = DateTimeUtil.getFormatDateFromString(srWorkPageRequest.getEndTime().trim(), "yyyy-MM-dd HH:mm:ss", "23:59:59");
        }

        Page page = PageUtils.buildPage(srWorkPageRequest);
        int pageSize = srWorkPageRequest.getPageSize();

        String areaId = srWorkPageRequest.getAreaId();
        Integer grade = srWorkPageRequest.getGrade();
        String subjectId = srWorkPageRequest.getSubjectId();
        String userId = srWorkPageRequest.getUserId();
        String schoolId = srWorkPageRequest.getSchoolId();
        List<SRSchoolStatisticDTO> dtoList = new ArrayList<SRSchoolStatisticDTO>();

        long schoolAmount = ctWorkGatherMapper.getSchoolStatisticAmount(areaId);
        if((schoolAmount-1)>=0) {

            long allWorkAmount = ctWorkGatherMapper.getWorkNum(startTime, endTime, areaId, subjectId, grade, schoolId, userId);
            //首先获得对应学校分页后的任务数量(含有学校名称跟id),然后根据schoolId，获得对应的数据
            List<SRSchoolStatistic> workNumList = ctWorkGatherMapper.getWorkAmount(startTime, endTime, areaId, subjectId,
                    grade, pageSize, page);

            List<String> schoolIdList = TranslateSRWork.getSchoolIdFromWorkList(workNumList);

            //老师的数量
            List<SRSchoolStatistic> teacherAmountList = ctWorkGatherMapper.getTeacherAmount(schoolIdList);

            //各个属性
            List<SRSchoolStatistic> statisticsList = ctWorkGatherMapper.getSchoolStatistic(schoolIdList);

            int rankNum = (int)page.getStart();
            //然后拼接，老师数量跟属性的id可能不存在
            for (int i = 0; i < workNumList.size(); i++) {
                SRSchoolStatistic record = workNumList.get(i);
                SRSchoolStatisticDTO dto = new SRSchoolStatisticDTO();
                String id = record.getSchoolId();
                for (SRSchoolStatistic schoolStatistic : teacherAmountList) {
                    if (id.equals(schoolStatistic.getSchoolId())) {
                        record.setTeacherNum(schoolStatistic.getTeacherNum());
                        break;
                    }
                }
                for (SRSchoolStatistic statistic : statisticsList) {
                    if (id.equals(statistic.getSchoolId())) {
                        record.setHeadmasterName(statistic.getHeadmasterName());
                        break;
                    }
                }
                record.setWorkNum(workNumList.get(i).getWorkNum());
                double percent = allWorkAmount-1>=0? StringUtil.getDecimal(workNumList.get(i).getWorkNum() * 1.0 /allWorkAmount,2):0;
                BeanUtils.copyProperties(record, dto);
                dto.setPercentage(percent);
                dto.setRankNum(++rankNum);
                dtoList.add(dto);
            }
        }

        //返回PageResponse
        PageResponse<SRSchoolStatisticDTO> pageResponse = new PageResponse<SRSchoolStatisticDTO>();
        PageUtils.buldPageResponse(srWorkPageRequest, pageResponse);
        pageResponse.setRows(dtoList);
        pageResponse.setRecords(schoolAmount);
        return pageResponse;

    }

    @Override
    public PageResponse<SRTeacherStatisticDTO> getTeacherStatistic(SRWorkPageRequest srWorkPageRequest)throws CloudteachException{

        Date startTime = null;
        if(srWorkPageRequest.getStartTime()!=null && !srWorkPageRequest.getStartTime().isEmpty()){
            startTime = DateTimeUtil.getFormatDateFromString(srWorkPageRequest.getStartTime().trim(), "yyyy-MM-dd HH:mm:ss", "00:00:00");
        }
        Date endTime = null;
        if(srWorkPageRequest.getEndTime()!=null && !srWorkPageRequest.getEndTime().isEmpty()){
            endTime = DateTimeUtil.getFormatDateFromString(srWorkPageRequest.getEndTime().trim(), "yyyy-MM-dd HH:mm:ss", "23:59:59");
        }

        Page page = PageUtils.buildPage(srWorkPageRequest);
        int pageSize = srWorkPageRequest.getPageSize();

        String areaId = srWorkPageRequest.getAreaId();
        Integer grade = srWorkPageRequest.getGrade();
        String subjectId = srWorkPageRequest.getSubjectId();
        String schoolId = srWorkPageRequest.getSchoolId();
        List<SRTeacherStatisticDTO> dtoList = new ArrayList<SRTeacherStatisticDTO>();

        long count = ctWorkGatherMapper.getTeacherAmout(areaId,schoolId);

        if(count-1>=0) {

            //获得根据排序后的学生id,学生作业或者板书数量以及学校名称
            List<TeacherStatistic> teacherStatisticList = ctWorkGatherMapper.getWorkByTeacher(startTime, endTime, areaId, subjectId,
                    grade, pageSize, page,schoolId);

            int rankNum = (int)page.getStart();
            for (TeacherStatistic teacherStatistic : teacherStatisticList) {
                SRTeacherStatisticDTO dto = new SRTeacherStatisticDTO();
                String id = teacherStatistic.getSchoolId();
                String userId = teacherStatistic.getTeacherId();
                TeacherStatistic teacherSubjectName= ctWorkGatherMapper.getSubjectByUser(userId, id);
                if(teacherSubjectName == null){
                    teacherStatistic.setSubjectName("");
                    teacherStatistic.setTeacherName("");
                }
                else{
                    teacherStatistic.setSubjectName(teacherSubjectName.getSubjectName());
                    teacherStatistic.setTeacherName(teacherSubjectName.getTeacherName());
                }

                BeanUtils.copyProperties(teacherStatistic, dto);
                dto.setRankNum(++rankNum);
                dtoList.add(dto);
            }
        }



        //返回PageResponse
        PageResponse<SRTeacherStatisticDTO> pageResponse = new PageResponse<SRTeacherStatisticDTO>();
        PageUtils.buldPageResponse(srWorkPageRequest, pageResponse);
        pageResponse.setRows(dtoList);
        pageResponse.setRecords(count);
        return pageResponse;
    }

    public static List<String> getWorkIdList(List<SRWork> workList){
        List<String> workIdList = new ArrayList<String>();
        for(SRWork srWork : workList){
            workIdList.add(srWork.getWorkId());
        }
        return workIdList;
    }
}
