package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.CoursewaresResponseDTO;
import net.xuele.cloudteach.dto.TeachCoursewaresShareDTO;
import net.xuele.cloudteach.dto.page.TeachCoursewaresShareRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

/**
 * 分享的授课课件服务
 * Created by panglx on 2015/7/17 0017.
 */
public interface TeachCoursewaresShareService {

	/**
	 * 分页获取分享的授课课件信息
	 * @param request
	 * @return
	 * @throws CloudteachException
	 */
	public PageResponse<TeachCoursewaresShareDTO> queryTCoursewaresShareResource(TeachCoursewaresShareRequest request) throws CloudteachException;

	/**
	 * 分享课件点赞
	 * @param shareId
	 * @param creator
	 * @return
	 * @throws CloudteachException  有可能抛出异常 【已经点赞不能点赞】，【自己分享的不能点赞】
	 */
	int praise(String shareId,String creator) throws  CloudteachException;

	/**
	 * 分享课件取消赞
	 * @param shareId
	 * @param creator
	 * @return
	 * @throws CloudteachException 有可能抛出异常 【未点赞不能取消点赞】
	 */
	int cancelPraise(String shareId,String creator) throws  CloudteachException;

	/**
	 * 分享课件收藏
	 * @param shareId
	 * @param creator
	 * @return
	 * @throws CloudteachException 可能抛出的异常 【不能收藏自己的资源】【已经收藏】
	 */
	String collectItem(String shareId,String creator,String schoolId) throws  CloudteachException;

	/**
	 * 分享课件取消收藏
	 * @param shareId
	 * @param creator
	 * @return
	 * @throws CloudteachException  可能抛出的异常 【未收藏】
	 */
	String cancelCollect(String shareId,String creator,String schoolId) throws  CloudteachException;

	/**
	 * 取消分享
	 * @param shareId
	 * @param schoolId
	 * @throws CloudteachException
	 */
	public String cancelShare(String shareId,String creator,String schoolId) throws CloudteachException;

	/**
	 * 分享的课件预览
	 * @param shareId
	 * @param currentUserId
	 * @return
	 * @throws CloudteachException
	 */
	public CoursewaresResponseDTO preview(String shareId, String currentUserId) throws CloudteachException;
}
