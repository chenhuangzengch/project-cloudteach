package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.ParentCloudWorkPageDTO;
import net.xuele.cloudteach.dto.page.ParentCloudWorkPageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

/**
 * Created by dj on 2015/7/31 0031.
 */
public interface ParentCloudWorkManagerService {
    /**
     * 家长查看待完成的作业
     */
    PageResponse<ParentCloudWorkPageDTO> queryIncompleteWorkList(ParentCloudWorkPageRequest request) throws CloudteachException;

    /**
     * 家长查看作业列表
     */
    PageResponse<ParentCloudWorkPageDTO> queryWorkList(ParentCloudWorkPageRequest request) throws CloudteachException;

    /**
     * 家长查看预习作业
     */
    PageResponse<ParentCloudWorkPageDTO> queryGuidanceWorkList(ParentCloudWorkPageRequest request) throws CloudteachException;

}
