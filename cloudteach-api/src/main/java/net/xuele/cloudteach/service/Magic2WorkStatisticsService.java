package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.*;

import java.util.List;

/**
 * Magic2WorkStatisticsService
 * 提分宝数据统计接口
 * @author panglx
 * @date on 2015/12/23 0023.
 */
public interface Magic2WorkStatisticsService {

	/**
	 * 按课本统计百分比
	 * @param bookId
	 * @param schoolId
	 * @param classIds
	 * @return
	 */
	public List<Magic2WorkBookRateDTO> getMagic2WorkBookRate(String bookId,String schoolId,List<String> classIds);

	/**
	 * 按课程统计百分比
	 * @param bookId
	 * @param schoolId
	 * @param classIds
	 * @return
	 */
	public List<Magic2WorkUnitsRateDTO> getMagic2WorkUnitsRate(String bookId,String schoolId,List<String> classIds);

	/**
	 * 提分宝个人统计
	 * @param schoolId
	 * @param unitId
	 * @param userId
	 * @return
	 */
	public List<Magic2WorkMyStatisticsDTO> getMagic2WorkMyStatistics(String schoolId,String unitId,String userId);

	/**
	 * 提分宝个人总计
	 * @param schoolId
	 * @param unitId
	 * @param userId
	 * @return
	 */
	public Magic2WorkClassTotalDTO getMagic2WorkStuTotal(String schoolId,String unitId,String userId);

	/**
	 * 提分宝班级统计
	 * @param schoolId
	 * @param unitId
	 * @param classId
	 * @return
	 */
	public Magic2WorkClassStatisticsDTO getMagic2WorkClassStatistics(String schoolId,String unitId,String classId);

	/**
	 * 提分宝班级总计
	 * @param schoolId
	 * @param unitId
	 * @param classId
	 * @return
	 */
	public Magic2WorkClassTotalDTO getMagic2WorkClassTotal(String schoolId,String unitId,String classId);

}

