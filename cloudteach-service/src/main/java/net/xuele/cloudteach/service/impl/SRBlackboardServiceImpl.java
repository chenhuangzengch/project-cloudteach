package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.domain.CtWorkTapeFiles;
import net.xuele.cloudteach.domain.SRBlackboard;
import net.xuele.cloudteach.domain.SRSchoolStatistic;
import net.xuele.cloudteach.domain.TeacherStatistic;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.SRBlackboardPageRequest;
import net.xuele.cloudteach.persist.CtWorkGatherMapper;
import net.xuele.cloudteach.persist.CtWorkTapeFilesMapper;
import net.xuele.cloudteach.service.SRBlackboardService;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by cm.wang on 2015/8/16 0016.
 */
@Service
public class SRBlackboardServiceImpl implements SRBlackboardService{

    @Autowired
    private CtWorkGatherMapper ctWorkGatherMapper;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CtWorkTapeFilesMapper ctWorkTapeFilesMapper;

    @Override
    public Integer getBlackboardNum(SRParamDTO srParamDTO) throws CloudteachException {

        Date startTime = null;
        if(srParamDTO.getStartTime()!=null && !srParamDTO.getStartTime().isEmpty()){
            startTime = DateTimeUtil.getFormatDateFromString(srParamDTO.getStartTime().trim(),"yyyy-MM-dd HH:mm:ss", "00:00:00");
        }
        Date endTime = null;
        if(srParamDTO.getEndTime()!=null && !srParamDTO.getEndTime().isEmpty()){
            endTime = DateTimeUtil.getFormatDateFromString(srParamDTO.getEndTime().trim(),"yyyy-MM-dd HH:mm:ss","23:59:59");
        }
        int count = ctWorkGatherMapper.getBlackboardNum(startTime, endTime, srParamDTO.getAreaId(),
                srParamDTO.getSubjectId(), srParamDTO.getGrade(),srParamDTO.getSchoolId(),srParamDTO.getUserId());

        return count;
    }

    @Override
    public PageResponse<SRBlackboardDTO> getBlackboardList(SRBlackboardPageRequest srBlackboardPageRequest) throws CloudteachException {

        Date startTime = null;
        if(srBlackboardPageRequest.getStartTime()!=null && !srBlackboardPageRequest.getStartTime().isEmpty()){
            startTime = DateTimeUtil.getFormatDateFromString(srBlackboardPageRequest.getStartTime().trim(),"yyyy-MM-dd HH:mm:ss", "00:00:00");
        }
        Date endTime = null;
        if(srBlackboardPageRequest.getEndTime()!=null && !srBlackboardPageRequest.getEndTime().isEmpty()){
            endTime = DateTimeUtil.getFormatDateFromString(srBlackboardPageRequest.getEndTime().trim(),"yyyy-MM-dd HH:mm:ss", "23:59:59");
        }

        Page page = PageUtils.buildPage(srBlackboardPageRequest);
        int pageSize = srBlackboardPageRequest.getPageSize();

        List<SRBlackboardDTO> dtoList = new ArrayList<SRBlackboardDTO>();

        long count = ctWorkGatherMapper.getBlackboardNum(startTime, endTime, srBlackboardPageRequest.getAreaId(),
                srBlackboardPageRequest.getSubjectId(), srBlackboardPageRequest.getGrade(),
                srBlackboardPageRequest.getSchoolId(),srBlackboardPageRequest.getUserId());

        if(count-1>=0) {

            //获得没有老师信息的板书数量
            List<SRBlackboard> workList = ctWorkGatherMapper.getBlackboardList(startTime, endTime, srBlackboardPageRequest.getAreaId(),
                    srBlackboardPageRequest.getSubjectId(), srBlackboardPageRequest.getGrade(),
                    pageSize, page,srBlackboardPageRequest.getSchoolId(),srBlackboardPageRequest.getUserId());

            if(CollectionUtils.isNotEmpty(workList)) {


                Map<String, UserTeacherDTO> teacherDTOMap = new HashMap<String, UserTeacherDTO>();

                List<String> workIdList = getWorkIdList(workList);
                List<CtWorkTapeFiles> workTapeFilesList = ctWorkTapeFilesMapper.getWorkTapeFilesByWorkIdList(workIdList);

                for (SRBlackboard srWork : workList) {
                    SRBlackboardDTO dto = new SRBlackboardDTO();
                    String userId = srWork.getUserId();
                    String workId = srWork.getWorkId();
                    UserTeacherDTO teacherDTO = null;
                    if (teacherDTOMap.containsKey(userId)) {
                        teacherDTO = teacherDTOMap.get(userId);
                    } else {
                        teacherDTO = teacherService.getTeacherInfo(userId);
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
        PageResponse<SRBlackboardDTO> pageResponse = new PageResponse<SRBlackboardDTO>();
        PageUtils.buldPageResponse(srBlackboardPageRequest, pageResponse);
        pageResponse.setRows(dtoList);
        pageResponse.setRecords(count);
        return pageResponse;
    }

    @Override
    public PageResponse<SRSchoolStatisticDTO> getSchoolStatistic(SRBlackboardPageRequest srBlackboardPageRequest) throws CloudteachException {
        Date startTime = null;
        if(srBlackboardPageRequest.getStartTime()!=null && !srBlackboardPageRequest.getStartTime().isEmpty()){
            startTime = DateTimeUtil.getFormatDateFromString(srBlackboardPageRequest.getStartTime().trim(),"yyyy-MM-dd HH:mm:ss", "00:00:00");
        }
        Date endTime = null;
        if(srBlackboardPageRequest.getEndTime()!=null && !srBlackboardPageRequest.getEndTime().isEmpty()){
            endTime = DateTimeUtil.getFormatDateFromString(srBlackboardPageRequest.getEndTime().trim(),"yyyy-MM-dd HH:mm:ss", "23:59:59");
        }

        Page page = PageUtils.buildPage(srBlackboardPageRequest);
        int pageSize = srBlackboardPageRequest.getPageSize();

        String areaId = srBlackboardPageRequest.getAreaId();
        Integer grade = srBlackboardPageRequest.getGrade();
        String subjectId = srBlackboardPageRequest.getSubjectId();
        String userId =srBlackboardPageRequest.getUserId();
        String schoolId =srBlackboardPageRequest.getSchoolId();

        List<SRSchoolStatisticDTO> dtoList = new ArrayList<SRSchoolStatisticDTO>();

        long schoolAmount = ctWorkGatherMapper.getSchoolStatisticAmount(areaId);

        if(schoolAmount-1>=0) {

            long allBlackboardAmount = ctWorkGatherMapper.getBlackboardNum(startTime, endTime, areaId, subjectId, grade, schoolId, userId);


            //首先获得对应学校分页后的板书数量(含有学校名称跟id),然后根据schoolId，获得对应的数据
            List<SRSchoolStatistic> workNumList = ctWorkGatherMapper.getBlackboardAmount(startTime, endTime, areaId, subjectId,
                    grade, pageSize, page);

            List<String> schoolIdList = TranslateSRWork.getSchoolIdFromWorkList(workNumList);

            //老师的数量
            List<SRSchoolStatistic> teacherAmountList = ctWorkGatherMapper.getTeacherAmount(schoolIdList);

            //各个属性
            List<SRSchoolStatistic> statisticsList = ctWorkGatherMapper.getSchoolStatistic(schoolIdList);


            //然后拼接，老师数量跟属性的id可能不存在

            int rankNum = (int)page.getStart();
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
                double percent = allBlackboardAmount-1>=0? StringUtil.getDecimal(workNumList.get(i).getWorkNum() * 1.0 / allBlackboardAmount, 2):0;
                BeanUtils.copyProperties(record, dto);
                dto.setPercentage(percent);
                dto.setRankNum(++rankNum);
                dtoList.add(dto);
            }
        }

        //返回PageResponse
        PageResponse<SRSchoolStatisticDTO> pageResponse = new PageResponse<SRSchoolStatisticDTO>();
        PageUtils.buldPageResponse(srBlackboardPageRequest, pageResponse);
        pageResponse.setRows(dtoList);
        pageResponse.setRecords(schoolAmount);
        return pageResponse;
    }

    @Override
    public PageResponse<SRTeacherStatisticDTO> getTeacherStatistic(SRBlackboardPageRequest srBlackboardPageRequest) throws CloudteachException {
        Date startTime = null;
        if(srBlackboardPageRequest.getStartTime()!=null && !srBlackboardPageRequest.getStartTime().isEmpty()){
            startTime = DateTimeUtil.getFormatDateFromString(srBlackboardPageRequest.getStartTime().trim(),"yyyy-MM-dd HH:mm:ss", "00:00:00");
        }
        Date endTime = null;
        if(srBlackboardPageRequest.getEndTime()!=null && !srBlackboardPageRequest.getEndTime().isEmpty()){
            endTime = DateTimeUtil.getFormatDateFromString(srBlackboardPageRequest.getEndTime().trim(),"yyyy-MM-dd HH:mm:ss", "23:59:59");
        }

        Page page = PageUtils.buildPage(srBlackboardPageRequest);
        int pageSize = srBlackboardPageRequest.getPageSize();

        String areaId = srBlackboardPageRequest.getAreaId();
        Integer grade = srBlackboardPageRequest.getGrade();
        String subjectId = srBlackboardPageRequest.getSubjectId();
        String schoolId = srBlackboardPageRequest.getSchoolId();

        List<SRTeacherStatisticDTO> dtoList = new ArrayList<SRTeacherStatisticDTO>();

        long count = ctWorkGatherMapper.getTeacherAmout(areaId,schoolId);

        if(count-1>=0) {

            //获得根据排序后的学生id,学生作业或者板书数量以及学校名称
            List<TeacherStatistic> teacherStatisticList = ctWorkGatherMapper.getBlackboardByTeacher(startTime, endTime, areaId, subjectId,
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
        PageUtils.buldPageResponse(srBlackboardPageRequest, pageResponse);
        pageResponse.setRows(dtoList);
        pageResponse.setRecords(count);
        return pageResponse;
    }

    public static List<String> getWorkIdList(List<SRBlackboard> workList){
        List<String> workIdList = new ArrayList<String>();
        for(SRBlackboard blackboard : workList){
            workIdList.add(blackboard.getWorkId());
        }
        return workIdList;
    }
}
