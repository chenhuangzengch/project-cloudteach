package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.AcOfficialResourceViewDTO;
import net.xuele.cloudteach.dto.CloudDiskCollectDTO;
import net.xuele.cloudteach.dto.CloudDiskShareCountDTO;
import net.xuele.cloudteach.dto.page.AcOfficialResourcePageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

import java.util.List;

/**
 * AsOfficialResourceService
 * 云盘官方资源
 * @author panglx
 * @date on 2015/8/17 0017.
 */
public interface AcOfficialResourceService {

	/**
	 * 分页显示官方资源
	 * @param request
	 * @return
	 * @throws CloudteachException
	 */
	public PageResponse<AcOfficialResourceViewDTO> queryAcOfficialResource(AcOfficialResourcePageRequest request) throws CloudteachException;

	/**
	 * 收藏官方资源
	 * @param resourceId
	 * @param userId
	 * @param schoolId
	 * @return
	 * @throws CloudteachException
	 */
	public CloudDiskCollectDTO collectFile(String resourceId,String userId,String schoolId) throws CloudteachException;

	/**
	 * 取消收藏
	 * @param resourceId
	 * @param userId
	 * @param schoolId
	 * @return
	 * @throws CloudteachException
	 */
	public CloudDiskCollectDTO unCollectFile(String resourceId, String userId, String schoolId)throws CloudteachException;

	/**
	 * 官方资源根据fileType统计
	 * @param unitId
	 * @param schoolId
	 * @param areaId
	 * @return
	 */
	public List<CloudDiskShareCountDTO> countByFileType(String unitId,String schoolId,String areaId);
}
