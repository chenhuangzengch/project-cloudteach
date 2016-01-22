package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.dto.ParentCloudWorkPageDTO;
import net.xuele.cloudteach.dto.page.ParentCloudWorkPageRequest;
import net.xuele.cloudteach.persist.ParentCloudWorkManagerMapper;
import net.xuele.cloudteach.service.ParentCloudWorkManagerService;
import net.xuele.cloudteach.service.util.DateTimeUtil;
import net.xuele.cloudteach.service.util.StringUtil;
import net.xuele.cloudteach.view.ParentCloudWorkManagerView;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dj on 2015/7/31 0031.
 */
@Service
public class ParentCloudWorkManagerServiceImpl implements ParentCloudWorkManagerService {
    @Autowired
    ParentCloudWorkManagerMapper parentCloudWorkManagerMapper;

    @Override
    public PageResponse<ParentCloudWorkPageDTO> queryIncompleteWorkList(ParentCloudWorkPageRequest request) {
        String studentId = request.getStudentId();
        String schoolId = request.getSchoolId();
        String subjectId = request.getSubjectId();
        Integer workType = request.getWorkType();
        Long time = Long.valueOf(request.getPublishTime());

        String publishTime = "";
        if(time == null || time == 0){
            publishTime = DateTimeUtil.DateToStringForMycat(new Date());
        }else{
            publishTime = DateTimeUtil.DateToStringForMycat(new Date(time));
        }
        if (studentId == null || "".equals(studentId.trim())) {
            throw new CloudteachException(CloudTeachErrorEnum.USERIDNOTNULL.getMsg(), CloudTeachErrorEnum.USERIDNOTNULL.getCode());
        }
        //获取数据
        Page page = PageUtils.buildPage(request);
        int pageSize = request.getPageSize();
        List<ParentCloudWorkManagerView> parentCloudWorkManagerViewList = parentCloudWorkManagerMapper.queryIncompleteWorkList(pageSize, page, studentId,subjectId,workType, schoolId,publishTime);
        //返回PageResponse
        PageResponse<ParentCloudWorkPageDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        //view对象转dto对象
        List<ParentCloudWorkPageDTO> parentCloudWorkPageDTOList = new ArrayList<>();
        for(ParentCloudWorkManagerView parentCloudWorkManagerView:parentCloudWorkManagerViewList){
            ParentCloudWorkPageDTO parentCloudWorkPageDTO = new ParentCloudWorkPageDTO();
            //获取指定长度的内容
            String context = StringUtil.getFixedLengthContext(parentCloudWorkManagerView.getContext(),1);
            parentCloudWorkManagerView.setContext(context);
            BeanUtils.copyProperties(parentCloudWorkManagerView, parentCloudWorkPageDTO);
            parentCloudWorkPageDTOList.add(parentCloudWorkPageDTO);
        }
        response.setTotal(parentCloudWorkManagerViewList.size());
        response.setRows(parentCloudWorkPageDTOList);
        return response;
    }

    @Override
    public PageResponse<ParentCloudWorkPageDTO> queryWorkList(ParentCloudWorkPageRequest request) throws CloudteachException {
        String studentId = request.getStudentId();
        String schoolId = request.getSchoolId();
        String subjectId = request.getSubjectId();
        Integer workType = request.getWorkType();
        Long time = Long.valueOf(request.getPublishTime());
        String publishTime = "";
        if(time == null || time == 0){
            publishTime = DateTimeUtil.DateToStringForMycat(new Date());
        }else{
            publishTime = DateTimeUtil.DateToStringForMycat(new Date(time));
        }
        if (studentId == null || "".equals(studentId.trim())) {
            throw new CloudteachException(CloudTeachErrorEnum.USERIDNOTNULL.getMsg(), CloudTeachErrorEnum.USERIDNOTNULL.getCode());
        }
        //获取数据
        Page page = PageUtils.buildPage(request);
        int pageSize = request.getPageSize();
        List<ParentCloudWorkManagerView> parentCloudWorkManagerViewList = parentCloudWorkManagerMapper.queryWorkList(pageSize, page, studentId, subjectId, workType, schoolId,publishTime);
        //返回PageResponse
        PageResponse<ParentCloudWorkPageDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        //view对象转dto对象
        List<ParentCloudWorkPageDTO> parentCloudWorkPageDTOList = new ArrayList<>();
        for(ParentCloudWorkManagerView parentCloudWorkManagerView:parentCloudWorkManagerViewList){
            ParentCloudWorkPageDTO parentCloudWorkPageDTO = new ParentCloudWorkPageDTO();
            //获取指定长度的内容
            String context = StringUtil.getFixedLengthContext(parentCloudWorkManagerView.getContext(),1);
            parentCloudWorkManagerView.setContext(context);
            BeanUtils.copyProperties(parentCloudWorkManagerView, parentCloudWorkPageDTO);
            parentCloudWorkPageDTOList.add(parentCloudWorkPageDTO);
        }
        response.setTotal(parentCloudWorkManagerViewList.size());
        response.setRows(parentCloudWorkPageDTOList);
        return response;
    }

    @Override
    public PageResponse<ParentCloudWorkPageDTO> queryGuidanceWorkList(ParentCloudWorkPageRequest request) throws CloudteachException {
        String studentId = request.getStudentId();
        String schoolId = request.getSchoolId();
        String subjectId = request.getSubjectId();
        Integer workType = request.getWorkType();
        Long time = Long.valueOf(request.getPublishTime());
        String publishTime = "";
        if(time == null || time == 0){
            publishTime = DateTimeUtil.DateToStringForMycat(new Date());
        }else{
            publishTime = DateTimeUtil.DateToStringForMycat(new Date(time));
        }
        if (studentId == null || "".equals(studentId.trim())) {
            throw new CloudteachException(CloudTeachErrorEnum.USERIDNOTNULL.getMsg(), CloudTeachErrorEnum.USERIDNOTNULL.getCode());
        }
        //获取数据
        Page page = PageUtils.buildPage(request);
        int pageSize = request.getPageSize();
        List<ParentCloudWorkManagerView> parentCloudWorkManagerViewList = parentCloudWorkManagerMapper.queryGuidanceWorkList(pageSize, page, studentId, subjectId, workType, schoolId,publishTime);
        //返回PageResponse
        PageResponse<ParentCloudWorkPageDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        //view对象转dto对象
        List<ParentCloudWorkPageDTO> parentCloudWorkPageDTOList = new ArrayList<>();
        for(ParentCloudWorkManagerView parentCloudWorkManagerView:parentCloudWorkManagerViewList){
            ParentCloudWorkPageDTO parentCloudWorkPageDTO = new ParentCloudWorkPageDTO();
            //获取指定长度的内容
            String context = StringUtil.getFixedLengthContext(parentCloudWorkManagerView.getContext(),1);
            parentCloudWorkManagerView.setContext(context);
            BeanUtils.copyProperties(parentCloudWorkManagerView, parentCloudWorkPageDTO);
            parentCloudWorkPageDTOList.add(parentCloudWorkPageDTO);
        }
        response.setTotal(parentCloudWorkManagerViewList.size());
        response.setRows(parentCloudWorkPageDTOList);
        return response;
    }
}
