package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.TeachchoosePageRequest;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.TeachCoursewaresService;
import net.xuele.cloudteach.service.TeachchooseService;
import net.xuele.cloudteach.service.util.StringUtil;
import net.xuele.cloudteach.view.TeachchooseView;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * TeachchooseServiceImpl
 * 聊城活动课件服务
 *
 * @author hushengguo
 * @date 2015/11/5
 */
@Service
public class TeachchooseServiceImpl implements TeachchooseService {

	@Autowired
	CtTeachchooseMapper ctTeachchooseMapper;

	@Override
	public PageResponse<TeachchooseDTO> queryTeachchooseListByPage(TeachchoosePageRequest request) throws CloudteachException {
		String schoolId = request.getSchoolId();
		String subjectId = request.getSubjectId();
		Integer grade = request.getGrade();
		String startTime = request.getStartTime();
		String endTime = request.getEndTime();

		Page page = PageUtils.buildPage(request);
		int pageSize = request.getPageSize();
		List<TeachchooseView> teachchooseViewList = ctTeachchooseMapper.queryTeachchooseListByPage(pageSize, page, schoolId, subjectId, grade, startTime, endTime);
		Long records = ctTeachchooseMapper.queryTeachchooseCount(schoolId, subjectId, grade, startTime, endTime);

		PageResponse<TeachchooseDTO> response = new PageResponse<>();
		PageUtils.buldPageResponse(request, response);
		List<TeachchooseDTO> teachchooseDTOList = new ArrayList<TeachchooseDTO>();
		for(TeachchooseView view : teachchooseViewList){
			TeachchooseDTO dto = new TeachchooseDTO();
			BeanUtils.copyProperties(view, dto);
			teachchooseDTOList.add(dto);
		}
		response.setRecords(records);
		response.setRows(teachchooseDTOList);
		return response;
	}
}