package net.xuele.cloudteach.web.controller;

import net.xuele.cloudsr.dto.*;
import net.xuele.cloudsr.service.SRCloudDiskAreaService;
import net.xuele.cloudsr.dto.page.SRCloudDiskPageRequest;
import net.xuele.cloudteach.dto.TeacherWorkFinishDetailViewDTO;
import net.xuele.cloudteach.service.SRCloudDiskService;
import net.xuele.cloudteach.service.TeacherWorkStuService;
import net.xuele.cloudteach.web.wrapper.UserInfoWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.page.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "ctTest")
public class CloudTeachTestController {

	@Autowired
	private SRCloudDiskService sRCloudDiskService;
	@Autowired
	private TeacherWorkStuService teacherWorkStuService;
	@Autowired
	private SRCloudDiskAreaService sRCloudDiskAreaService;


	@RequestMapping("test01")
	@ResponseBody
	public Object test01() throws ParseException {
		SRCloudDiskPageRequest request = new SRCloudDiskPageRequest();
		request.setAreaId("3707");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		request.setStartDate(simpledateformat.parse("2015-01-01 00:00:00"));
		request.setEndDate(simpledateformat.parse("2015-11-27 00:00:00"));
		request.setSchoolId("16693");
		request.setUserId("1669310034");

		//PageResponse<SRSchoolResourceStatisticsDTO> dateListArea = sRCloudDiskAreaService.getSchoolResourceStatistics(request);

		//SRAreaResourceAmountDTO sRAreaResourceAmountDTO = sRCloudDiskAreaService.getAreaUnderResourceAmount(request.getStartDate(),request.getEndDate(),request.getAreaId());

		//PageResponse<SRResourceSharedDTO> obj= sRCloudDiskAreaService.getAreaUnderShared(request);

		//SRSchoolResourceAmountDTO sRSchoolResourceAmountDTO = sRCloudDiskAreaService.getSchoolUnderResourceAmount(request.getStartDate(), request.getEndDate(), request.getSchoolId(),request.getAreaId());

		//PageResponse<SRResourceSharedDTO> dateObj = sRCloudDiskAreaService.getAreaUnderShared(request);

		//SRAreaResourceAmountDTO sRAreaResourceAmountDTO = sRCloudDiskAreaService.getAreaUnderShareResourceAmount(request.getStartDate(), request.getEndDate(), request.getAreaId());

		//SRResourceStatisticsDTO sRResourceStatisticsDTO = sRCloudDiskAreaService.getAreaUnderResourceStatistics(request.getStartDate(), request.getEndDate(), request.getAreaId());

		//PageResponse<SRTeacherResourceStatisticsDTO> sRResourceStatisticsDTO = sRCloudDiskAreaService.getTeacherResourceStatistics(request);

		//PageResponse<SRResourceSharedDTO> sRResourceStatisticsDTO = sRCloudDiskAreaService.getSchoolUnderShared(request);

		//SRSchoolResourceAmountDTO sRResourceStatisticsDTO = sRCloudDiskAreaService.getSchoolUnderShareResourceAmount(request.getStartDate(), request.getEndDate(),request.getSchoolId(), request.getAreaId());

		//PageResponse<SRTeacherResourceStatisticsDTO> sRResourceStatisticsDTO = sRCloudDiskAreaService.getSchoolUnderTeacherStatistics(request);

		//PageResponse<SRResourceSharedDTO> sRResourceStatisticsDTO = sRCloudDiskAreaService.getTeacherUnderShared(request);

		//SRTeacherResourceAmountDTO sRResourceStatisticsDTO = sRCloudDiskAreaService.getTeacherUnderResourceAmount(request.getStartDate(), request.getEndDate(), request.getUserId(),request.getSchoolId(),request.getAreaId());

		SRCloudDiskExcelDTO<SRTeacherResourceStatisticsDTO>  sRResourceStatisticsDTO = sRCloudDiskAreaService.getSchoolUnderTeacherStatisticsExport(request.getStartDate(), request.getEndDate(),request.getSchoolId(), request.getAreaId());

		//TeacherWorkFinishDetailViewDTO obj = teacherWorkStuService.teacherWorkFinishDetail("2c3d03f07c9b4053acca90e2ca35d247", "10339730", "100020");

		return sRResourceStatisticsDTO;
	}


}
