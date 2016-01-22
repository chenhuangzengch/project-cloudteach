package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.SRParamDTO;
import net.xuele.cloudteach.dto.SRSchoolStatisticDTO;
import net.xuele.cloudteach.dto.SRTeacherStatisticDTO;
import net.xuele.cloudteach.dto.SRWorkDTO;
import net.xuele.cloudteach.dto.page.SRWorkPageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

import java.util.Date;
import java.util.List;

/**
 * SRWorkService
 * 作业服务
 *
 * @author cm.wang
 * @date 2015/8/11
 */
public interface SRWorkService {

    /**
     * 根据参数获取老师作业条数
     * @param srParamDTO
     * @return
     * @throws CloudteachException
     */
     Integer getWorkNum(SRParamDTO srParamDTO) throws CloudteachException;


    /**
     * 根据参数获取老师作业列表
     * @param srWorkPageRequest
     * @return
     * @throws CloudteachException
     */
    PageResponse<SRWorkDTO> getWorkList(SRWorkPageRequest srWorkPageRequest) throws  CloudteachException;


    /**
     * 学校统计(分页获取)
     * @param srWorkPageRequest
     * @return
     * @throws CloudteachException
     */
    PageResponse<SRSchoolStatisticDTO> getSchoolStatistic(SRWorkPageRequest srWorkPageRequest)throws CloudteachException;


    /**
     * 老师统计
     * @param srWorkPageRequest
     * @return
     * @throws CloudteachException
     */
    PageResponse<SRTeacherStatisticDTO> getTeacherStatistic(SRWorkPageRequest srWorkPageRequest)throws CloudteachException;
}
