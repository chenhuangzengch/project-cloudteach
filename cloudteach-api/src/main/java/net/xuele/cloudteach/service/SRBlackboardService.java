package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.SRBlackboardDTO;
import net.xuele.cloudteach.dto.SRParamDTO;
import net.xuele.cloudteach.dto.SRSchoolStatisticDTO;
import net.xuele.cloudteach.dto.SRTeacherStatisticDTO;
import net.xuele.cloudteach.dto.page.SRBlackboardPageRequest;
import net.xuele.cloudteach.dto.page.SRWorkPageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

/**
 * SRBlackboardService
 * 板书服务
 *
 * @author cm.wang
 * @date 2015/8/11
 */
public interface SRBlackboardService {

    /**
     * 根据参数获取老师板书条数
     * @param srParamDTO
     * @return
     * @throws CloudteachException
     */
    Integer getBlackboardNum(SRParamDTO srParamDTO) throws CloudteachException;


    /**
     * 根据参数获取老师板书列表
     * @param srBlackboardPageRequest
     * @return
     * @throws CloudteachException
     */
    PageResponse<SRBlackboardDTO> getBlackboardList(SRBlackboardPageRequest srBlackboardPageRequest) throws  CloudteachException;

    /**
     * 学校统计(分页获取)
     * @param srBlackboardPageRequest
     * @return
     * @throws CloudteachException
     */
    PageResponse<SRSchoolStatisticDTO> getSchoolStatistic(SRBlackboardPageRequest srBlackboardPageRequest)throws CloudteachException;


    /**
     * 老师统计
     * @param srBlackboardPageRequest
     * @return
     * @throws CloudteachException
     */
    PageResponse<SRTeacherStatisticDTO> getTeacherStatistic(SRBlackboardPageRequest srBlackboardPageRequest)throws CloudteachException;
}
