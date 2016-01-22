package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.TeachchooseDTO;
import net.xuele.cloudteach.dto.page.TeachchoosePageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

/**
 * TeachchooseService
 * 聊城活动课件服务
 *
 * @author hushengguo
 * @date 2015/11/4
 */
public interface TeachchooseService {

	/**
	 * 获取本校某时间段原创课件列表
	 * @param request
	 * @return
	 * @throws CloudteachException
	 */
	public PageResponse<TeachchooseDTO> queryTeachchooseListByPage(TeachchoosePageRequest request) throws CloudteachException;

}
