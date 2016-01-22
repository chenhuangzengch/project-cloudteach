package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.SRCloudDiskPageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

import java.util.Date;
import java.util.List;

/**
 * SRCloudDiskService
 * 云盘资源统计服务
 *
 * @author duzg
 * @date 2015/8/5
 */
public interface SRCloudDiskService {

    /**
     * 获取区划下的资源数量
     *
     * @param startDate 统计开始日期
     * @param endDate   统计截止日期
     * @param areaId    区域ID
     * @return
     */
    SRAreaResourceAmountDTO getAreaUnderResourceAmount(Date startDate, Date endDate, String areaId) throws CloudteachException;

    /**
     * 获取区划下的分享资源数量
     *
     * @param startDate 统计开始日期
     * @param endDate   统计截止日期
     * @param areaId    区域ID
     * @return
     */
    SRAreaResourceAmountDTO getAreaUnderShareResourceAmount(Date startDate, Date endDate, String areaId) throws CloudteachException;

    /**
     * 获取区划下的最新分享（分页请求）
     *
     * @param request (统计开始日期:Date startDate,统计截止日期:Date endDate,区域ID:String areaId)
     * @return
     */
    PageResponse<SRResourceSharedDTO> getAreaUnderShared(SRCloudDiskPageRequest request) throws CloudteachException;

    /**
     * 获取区划下的资源统计
     *
     * @param startDate 统计开始日期
     * @param endDate   统计截止日期
     * @param areaId    区域ID
     * @return
     */
    SRResourceStatisticsDTO getAreaUnderResourceStatistics(Date startDate, Date endDate, String areaId) throws CloudteachException;

    /**
     * 获取区划下学校资源统计（分页请求）
     *
     * @param request (统计开始日期:Date startDate,统计截止日期:Date endDate,区域ID:String areaId)
     * @return
     */
    PageResponse<SRSchoolResourceStatisticsDTO> getSchoolResourceStatistics(SRCloudDiskPageRequest request) throws CloudteachException;

    /**
     * 获取区划下教师资源统计（分页请求）
     *
     * @param request (统计开始日期:Date startDate,统计截止日期:Date endDate,区域ID:String areaId)
     * @return
     */
    PageResponse<SRTeacherResourceStatisticsDTO> getTeacherResourceStatistics(SRCloudDiskPageRequest request) throws CloudteachException;

    /**
     * 获取学校下最新分享（分页请求）
     *
     * @param request (统计开始日期:Date startDate,统计截止日期:Date endDate,学校ID:String schoolId)
     * @return
     */
    PageResponse<SRResourceSharedDTO> getSchoolUnderShared(SRCloudDiskPageRequest request) throws CloudteachException;

    /**
     * 获取学校下的资源数量
     *
     * @param startDate 统计开始日期
     * @param endDate   统计截止日期
     * @param schoolId  学校ID
     * @return
     */
    SRSchoolResourceAmountDTO getSchoolUnderResourceAmount(Date startDate, Date endDate, String schoolId) throws CloudteachException;

    /**
     * 获取学校下的分享资源数量
     *
     * @param startDate 统计开始日期
     * @param endDate   统计截止日期
     * @param schoolId  学校ID
     * @return
     */
    SRSchoolResourceAmountDTO getSchoolUnderShareResourceAmount(Date startDate, Date endDate, String schoolId) throws CloudteachException;

    /**
     * 获取学校下的资源统计
     *
     * @param startDate 统计开始日期
     * @param endDate   统计截止日期
     * @param schoolId  学校ID
     * @return
     */
    SRResourceStatisticsDTO getSchoolUnderResourceStatistics(Date startDate, Date endDate, String schoolId) throws CloudteachException;

    /**
     * 获取学校下教师资源统计（分页请求）
     *
     * @param request (统计开始日期:Date startDate,统计截止日期:Date endDate,学校ID:String schoolId)
     * @return
     */
    PageResponse<SRTeacherResourceStatisticsDTO> getSchoolUnderTeacherStatistics(SRCloudDiskPageRequest request) throws CloudteachException;

    /**
     * 获取当前教师最新分享（分页请求）
     *
     * @param request (统计开始日期:Date startDate,统计截止日期:Date endDate,教师ID:String teacherId,学校ID:String schoolId)
     * @return
     */
    PageResponse<SRResourceSharedDTO> getTeacherUnderShared(SRCloudDiskPageRequest request) throws CloudteachException;

    /**
     * 获取教师资源数量
     *
     * @param startDate 统计开始日期
     * @param endDate   统计截止日期
     * @param teacherId 教师ID
     * @param schoolId  学校ID
     * @return
     */
    SRTeacherResourceAmountDTO getTeacherUnderResourceAmount(Date startDate, Date endDate, String teacherId, String schoolId) throws CloudteachException;


    /**
     *  获取区划下学校资源统计列表(导出Excel)
     */
    SRCloudDiskExcelDTO<SRSchoolResourceStatisticsDTO> getSchoolResourceStatisticsExport(Date startDate, Date endDate, String areaId) throws CloudteachException;

    /**
     *  获取区划下教师资源统计列表(导出Excel)
     */
    SRCloudDiskExcelDTO<SRTeacherResourceStatisticsDTO> getTeacherResourceStatisticsExport(Date startDate, Date endDate, String areaId) throws CloudteachException;

    /**
     *  学校下教师资源统计列表（导出Excel）
     */
    SRCloudDiskExcelDTO<SRTeacherResourceStatisticsDTO> getSchoolUnderTeacherStatisticsExport(Date startDate, Date endDate, String schoolId) throws CloudteachException;
}
