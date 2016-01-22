package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.StudentWorkCommunicationPageRequest;
import net.xuele.cloudteach.dto.page.StudentWorkPageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

import java.util.List;

/**
 * Created by hujx on 2015/7/10 0010
 * 学生-云作业服务
 */
public interface StudentCloudWorkService {

    /**
     * 返回待完成作业了列表
     *
     * @param studentWorkPageRequest
     * @return
     * @throws net.xuele.common.exceptions.CloudteachException
     */
    PageResponse<StudentWorkListViewDTO> queryToDoWorkList(StudentWorkPageRequest studentWorkPageRequest) throws CloudteachException;

    /**
     * 返回预习列表
     *
     * @param studentWorkPageRequest
     * @return
     * @throws CloudteachException
     */
    PageResponse<StudentWorkListViewDTO> queryPrepList(StudentWorkPageRequest studentWorkPageRequest) throws CloudteachException;

    /**
     * 返回作业列表
     *
     * @param studentWorkPageRequest
     * @return
     * @throws CloudteachException
     */
    PageResponse<StudentWorkListViewDTO> queryWorkList(StudentWorkPageRequest studentWorkPageRequest) throws CloudteachException;

}
