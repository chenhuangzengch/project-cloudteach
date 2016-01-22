package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.persist.CtMagic2WorkChallengeMapper;
import net.xuele.cloudteach.persist.CtMagic2WorkMaxPracticeMapper;
import net.xuele.cloudteach.service.Magic2WorkStatisticsService;
import net.xuele.cloudteach.service.util.DateTimeUtil;
import net.xuele.cloudteach.service.util.NumberFormat;
import net.xuele.cloudteach.view.*;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Magic2WorkStatisticsServiceImpl
 * 提分宝数据统计接口
 * @author panglx
 * @date on 2015/12/23 0023.
 */
@Service
public class Magic2WorkStatisticsServiceImpl implements Magic2WorkStatisticsService{

	private static Logger logger = LoggerFactory.getLogger(Magic2WorkStatisticsServiceImpl.class);

	@Autowired
	private CtMagic2WorkMaxPracticeMapper practiceMapper;

	@Autowired
	private CtMagic2WorkChallengeMapper challengeMapper;


	/**
	 * 按课本统计百分比
	 * @param bookId
	 * @param schoolId
	 * @param classIds
	 * @return
	 */
	@Override
	public List<Magic2WorkBookRateDTO> getMagic2WorkBookRate(String bookId, String schoolId, List<String> classIds) {

		logger.info("按课本统计百分比:getMagic2WorkBookRate（bookId="+bookId+"schoolId="+schoolId+")");
		List<Magic2WorkBookRateDTO> bookRateDTOList = new ArrayList<>();
		logger.info("调用mapper查询课本统计数据：CtMagic2WorkMaxPracticeMapper.selectBookRate");
		List<Magic2WorkBookRateView> bookRateViewList = practiceMapper.selectBookRate(bookId, schoolId, classIds);
		Magic2WorkBookRateDTO bookRateDTO;
		for (Magic2WorkBookRateView bookRateView:bookRateViewList){
			bookRateDTO = new Magic2WorkBookRateDTO();
			logger.info("view转DTO");
			BeanUtils.copyProperties(bookRateView,bookRateDTO);
			bookRateDTOList.add(bookRateDTO);
		}
		logger.info("返回对头对象");
		return bookRateDTOList;
	}

	/**
	 * 按课程统计百分比
	 * @param bookId
	 * @param schoolId
	 * @param classIds
	 * @return
	 */
	@Override
	public List<Magic2WorkUnitsRateDTO> getMagic2WorkUnitsRate(String bookId, String schoolId, List<String> classIds) {
		logger.info("按课程统计百分比:getMagic2WorkUnitsRate（bookId="+bookId+"schoolId="+schoolId+")");
		List<Magic2WorkUnitsRateDTO> unitsRateDTOList = new ArrayList<>();
		logger.info("调用mapper查询课本统计数据：CtMagic2WorkMaxPracticeMapper.selectUnitsRate");
		List<Magic2WorkUnitsRateView> unitsRateViewList = practiceMapper.selectUnitsRate(bookId, schoolId, classIds);

		Magic2WorkUnitsRateDTO unitsRateDTO = new Magic2WorkUnitsRateDTO();
		Magic2WorkBookRateDTO rateDTO;
		List<Magic2WorkBookRateDTO> temp = new ArrayList<>();
		for (Magic2WorkUnitsRateView unitsRateView:unitsRateViewList){
			rateDTO = new Magic2WorkBookRateDTO();
			if (unitsRateDTO.getUnitId()==null){//第一条记录
				unitsRateDTO.setUnitId(unitsRateView.getUnitId());
				rateDTO.setScore(unitsRateView.getScore());
				rateDTO.setRate(unitsRateView.getRate());
				temp.add(rateDTO);
				unitsRateDTO.getRatesList();
			}else{
				if (unitsRateView.getUnitId().equals(unitsRateDTO.getUnitId())){//如果unitId相同
					rateDTO.setScore(unitsRateView.getScore());
					rateDTO.setRate(unitsRateView.getRate());
					temp.add(rateDTO);
				}else{//如果unitId不相同
					unitsRateDTO.setRatesList(temp);
					unitsRateDTOList.add(unitsRateDTO);
					unitsRateDTO = new Magic2WorkUnitsRateDTO();
					temp = new ArrayList<>();
					unitsRateDTO.setUnitId(unitsRateView.getUnitId());
					rateDTO.setScore(unitsRateView.getScore());
					rateDTO.setRate(unitsRateView.getRate());
					temp.add(rateDTO);
				}
			}
		}
		unitsRateDTO.setRatesList(temp);
		unitsRateDTOList.add(unitsRateDTO);

		logger.info("返回DTO对象");
		return unitsRateDTOList;
	}

	/**
	 * 提分宝个人统计
	 * @param schoolId
	 * @param unitId
	 * @param userId
	 * @return
	 */
	@Override
	public List<Magic2WorkMyStatisticsDTO> getMagic2WorkMyStatistics(String schoolId, String unitId, String userId) {
		logger.info("提分宝个人统计:getMagic2WorkMyStatistics（unitId="+unitId+"schoolId="+schoolId+"userId="+userId+")");
		List<Magic2WorkMyStatisticsDTO> myStatisticsDTOList = new ArrayList<>();
		logger.info("调用mapper查询课本统计数据：CtMagic2WorkChallengeMapper.selectMyStatistics");
		List<Magic2WorkMyStatisticsView> myStatisticsViewList = challengeMapper.selectMyStatistics(schoolId, unitId, userId);
		Magic2WorkMyStatisticsDTO myStatisticsDTO;
		for (Magic2WorkMyStatisticsView myStatisticsView:myStatisticsViewList){
			myStatisticsDTO = new Magic2WorkMyStatisticsDTO();
			logger.info("view转DTO");
			BeanUtils.copyProperties(myStatisticsView,myStatisticsDTO);
			/**时间戳转时长*/
			myStatisticsDTO.setpTime(DateTimeUtil.getTsToDateString(myStatisticsView.getpTime()));
			myStatisticsDTO.setcTime(DateTimeUtil.getTsToDateString(myStatisticsView.getcTime()));
			myStatisticsDTO.setRate(NumberFormat.divide(myStatisticsView.getrNum(), myStatisticsView.gettNum(), "0%"));
			switch(myStatisticsView.getScore()){
				case 5:
					myStatisticsDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT5);
					break;
				case 6:
					myStatisticsDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT6);
					break;
				case 7:
					myStatisticsDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT7);
					break;
				case 8:
					myStatisticsDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT8);
					break;
				case 9:
					myStatisticsDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT9);
					break;
				case 10:
					myStatisticsDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT10);
					break;
				default:
					myStatisticsDTO.setScoreContext("");
					break;
			}
			myStatisticsDTOList.add(myStatisticsDTO);
		}
		return myStatisticsDTOList;
	}

	/**
	 * 提分宝个人总计
	 * @param schoolId
	 * @param unitId
	 * @param userId
	 * @return
	 */
	public Magic2WorkClassTotalDTO getMagic2WorkStuTotal(String schoolId,String unitId,String userId){
		logger.info("提分宝班级总计:getMagic2WorkStuTotal（unitId="+unitId+"，schoolId="+schoolId+"，userId="+userId+")");
		Magic2WorkClassTotalDTO classTotalDTO = new Magic2WorkClassTotalDTO();
		logger.info("调用mapper查询课本统计数据：CtMagic2WorkChallengeMapper.selectClassTotal");
		Magic2WorkClassTotalView stuTotal = challengeMapper.selectStuTotal(schoolId, unitId, userId);
		logger.info("view转DTO");
		BeanUtils.copyProperties(stuTotal, classTotalDTO);
		/**时间戳转时长*/
		classTotalDTO.setTotalTime(DateTimeUtil.getTsToDateString(stuTotal.getTotalTime()));
		classTotalDTO.setAvgTime(DateTimeUtil.getTsToDateString(stuTotal.getAvgTime()));

		if (stuTotal.gettNum()==0){
			classTotalDTO.setRate("0%");
		}else{
			classTotalDTO.setRate(NumberFormat.divide(stuTotal.getrNum(), stuTotal.gettNum(), "0%"));
		}
		return classTotalDTO;

	}

	/**
	 * 提分宝班级统计
	 * @param schoolId
	 * @param unitId
	 * @param classId
	 * @return
	 */
	@Override
	public Magic2WorkClassStatisticsDTO getMagic2WorkClassStatistics(String schoolId, String unitId, String classId) {
		logger.info("提分宝班级统计:getMagic2WorkClassStatistics（unitId="+unitId+"schoolId="+schoolId+"classId="+classId+")");
		Magic2WorkClassStatisticsDTO classStatisticsDTO = new Magic2WorkClassStatisticsDTO();
		List<StudentStatisticsDTO> studentStatisticsDTOs = new ArrayList<>();
		logger.info("调用mapper查询课本统计数据：CtMagic2WorkChallengeMapper.selectClassTotal");
		List<Magic2WorkClassStatisticsView> classStatisticsViewList = challengeMapper.selectClassStatistics(schoolId, unitId, classId);
		ScoreRateDTO scoreRateDTO;
		List<ScoreRateDTO> scoreRateDTOList = new ArrayList<>();
		StudentStatisticsDTO studentStatisticsDTO;
		Map<Integer,Integer> map = new HashedMap();
		/**挑战过的学生总数*/
		int total = 0;
		for (Magic2WorkClassStatisticsView classStatisticsView:classStatisticsViewList){
			studentStatisticsDTO = new StudentStatisticsDTO();
			BeanUtils.copyProperties(classStatisticsView,studentStatisticsDTO);
			/**用于计算每个等级对应的学生数*/
			if (classStatisticsView.getScore() != 0){
				/**时间戳转时长*/
				studentStatisticsDTO.setpTime(DateTimeUtil.getTsToDateString(classStatisticsView.getpTime()));
				studentStatisticsDTO.setAvgTime(DateTimeUtil.getTsToDateString(classStatisticsView.getAvgTime()));
				switch(classStatisticsView.getScore()){
					case 5:
						studentStatisticsDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT5);
						studentStatisticsDTO.setRate(NumberFormat.divide(classStatisticsView.getrNum(), classStatisticsView.gettNum(), "0%"));
						break;
					case 6:
						studentStatisticsDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT6);
						studentStatisticsDTO.setRate(NumberFormat.divide(classStatisticsView.getrNum(), classStatisticsView.gettNum(), "0%"));
						break;
					case 7:
						studentStatisticsDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT7);
						studentStatisticsDTO.setRate(NumberFormat.divide(classStatisticsView.getrNum(), classStatisticsView.gettNum(), "0%"));
						break;
					case 8:
						studentStatisticsDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT8);
						studentStatisticsDTO.setRate(NumberFormat.divide(classStatisticsView.getrNum(), classStatisticsView.gettNum(), "0%"));
						break;
					case 9:
						studentStatisticsDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT9);
						studentStatisticsDTO.setRate(NumberFormat.divide(classStatisticsView.getrNum(), classStatisticsView.gettNum(), "0%"));
						break;
					case 10:
						studentStatisticsDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT10);
						studentStatisticsDTO.setRate(NumberFormat.divide(classStatisticsView.getrNum(), classStatisticsView.gettNum(), "0%"));
						break;
					default:
						studentStatisticsDTO.setRate("");
						break;
				}
				int score = classStatisticsView.getScore();
				total += 1;
				if (map.containsKey(score)){
					map.put(score,map.get(score)+1);
				}else{
					map.put(score,1);
				}
			}else{
				studentStatisticsDTO.setRate("");
				studentStatisticsDTO.setScoreContext("未挑战");
			}

			studentStatisticsDTOs.add(studentStatisticsDTO);
		}
		logger.info("计算每个等级对应的百分比");
		for (int i=5;i<=10;i++){
			if (map.containsKey(i)){
				scoreRateDTO = new ScoreRateDTO();
				scoreRateDTO.setNum(map.get(i));
				scoreRateDTO.setScore(i);
				scoreRateDTO.setRate(NumberFormat.divide(map.get(i), total, "0%"));

			}else{
				scoreRateDTO = new ScoreRateDTO();
				scoreRateDTO.setNum(0);
				scoreRateDTO.setScore(i);
				scoreRateDTO.setRate("0%");
			}
			switch(i){
				case 5:
					scoreRateDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT5);
					break;
				case 6:
					scoreRateDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT6);
					break;
				case 7:
					scoreRateDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT7);
					break;
				case 8:
					scoreRateDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT8);
					break;
				case 9:
					scoreRateDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT9);
					break;
				case 10:
					scoreRateDTO.setScoreContext(Constants.STATISTICS_SCORE_CONTEXT10);
					break;
				default:
					scoreRateDTO.setScoreContext("");
					break;
			}
			scoreRateDTOList.add(scoreRateDTO);
		}
		logger.info("返回对象");
		classStatisticsDTO.setScoreRateDTOs(scoreRateDTOList);
		classStatisticsDTO.setStudentStatisticsDTOs(studentStatisticsDTOs);
		return classStatisticsDTO;
	}

	/**
	 * 提分宝班级总计
	 * @param schoolId
	 * @param unitId
	 * @param classId
	 * @return
	 */
	@Override
	public Magic2WorkClassTotalDTO getMagic2WorkClassTotal(String schoolId, String unitId, String classId) {
		logger.info("提分宝班级总计:getMagic2WorkClassTotal（unitId="+unitId+"，schoolId="+schoolId+"，classId="+classId+")");
		Magic2WorkClassTotalDTO classTotalDTO = new Magic2WorkClassTotalDTO();
		logger.info("调用mapper查询课本统计数据：CtMagic2WorkChallengeMapper.selectClassTotal");
		Magic2WorkClassTotalView classTotalView = challengeMapper.selectClassTotal(schoolId, unitId, classId);
		logger.info("view转DTO");
		BeanUtils.copyProperties(classTotalView, classTotalDTO);
		/**时间戳转时长*/
		classTotalDTO.setTotalTime(DateTimeUtil.getTsToDateString(classTotalView.getTotalTime()));
		classTotalDTO.setAvgTime(DateTimeUtil.getTsToDateString(classTotalView.getAvgTime()));

		if (classTotalView.gettNum() != 0){
			classTotalDTO.setRate(NumberFormat.divide(classTotalView.getrNum(), classTotalView.gettNum(), "0%"));
		}else{
			classTotalDTO.setRate("0%");
		}

		return classTotalDTO;
	}


}
