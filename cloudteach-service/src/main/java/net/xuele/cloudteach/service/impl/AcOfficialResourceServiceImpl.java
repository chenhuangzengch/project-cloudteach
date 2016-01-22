package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.domain.AcOfficialResource;
import net.xuele.cloudteach.domain.CtCloudDisk;
import net.xuele.cloudteach.domain.CtCloudDiskShareCount;
import net.xuele.cloudteach.dto.AcOfficialResourceViewDTO;
import net.xuele.cloudteach.dto.CloudDiskCollectDTO;
import net.xuele.cloudteach.dto.CloudDiskShareCountDTO;
import net.xuele.cloudteach.dto.page.AcOfficialResourcePageRequest;
import net.xuele.cloudteach.persist.AcOfficialResourceMapper;
import net.xuele.cloudteach.persist.CtCloudDiskMapper;
import net.xuele.cloudteach.service.AcOfficialResourceService;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * AcOfficialResourceServiceImpl
 * 官方资源服务
 * @author panglx
 * @date on 2015/8/17 0017.
 */
@Service
public class AcOfficialResourceServiceImpl implements AcOfficialResourceService{

	@Autowired
	AcOfficialResourceMapper acOfficialResourceMapper;

	@Autowired
	private CtCloudDiskMapper ctCloudDiskMapper;

	private static Logger logger = LoggerFactory.getLogger(AcOfficialResourceServiceImpl.class);
	/**
	 * 分页查询官方资源
	 * @param request
	 * @return
	 * @throws CloudteachException
	 */
	@Override
	public PageResponse<AcOfficialResourceViewDTO> queryAcOfficialResource(AcOfficialResourcePageRequest request) throws CloudteachException {
		String userId = request.getUserId();//用户id
		String schoolId = request.getSchoolId();//学校id
		String unitId = request.getUnitId();//课程id
		String areaId = request.getAreaId();//地区id
		Integer extType = request.getExtType();//扩展名类型
		Integer fileType = request.getFileType()==0?null:request.getFileType();
		Page page = PageUtils.buildPage(request);
		page.setPage(request.getPage());
		//调用服务查询官方资源
		List<AcOfficialResource> list = acOfficialResourceMapper.selectByPage(unitId, areaId, fileType, extType, request.getPageSize(), page);
		int records = acOfficialResourceMapper.selectCount(unitId,areaId,fileType,extType);
		//官方资源DTO
		List<AcOfficialResourceViewDTO> acOfficialResourceViewDTOList = new ArrayList<>();
		AcOfficialResourceViewDTO acOfficialResourceViewDTO;
		logger.info("查询我收藏的官方资源pid：");
		List<String> pids = ctCloudDiskMapper.queryMyCollectPid(schoolId, unitId, userId, 2, fileType, extType);
		logger.info("查询我收藏的官方资源pid："+pids);
		for (AcOfficialResource resource:list){
			acOfficialResourceViewDTO = new AcOfficialResourceViewDTO();
			BeanUtils.copyProperties(resource,acOfficialResourceViewDTO);
			//判断是否已收藏
			if (pids.contains(resource.getResourceId())){
				//收藏状态->1
				acOfficialResourceViewDTO.setCollectState(1);
			}else{
				//收藏状态->0
				acOfficialResourceViewDTO.setCollectState(0);
			}
			acOfficialResourceViewDTOList.add(acOfficialResourceViewDTO);
		}
        //返回PageResponse
		PageResponse<AcOfficialResourceViewDTO> response = new PageResponse<>();
		PageUtils.buldPageResponse(request, response);
		//资源数
		response.setRecords(records);
		//资源记录
		response.setRows(acOfficialResourceViewDTOList);
		return response;
	}

	/**
	 * 收藏资源
	 * @param resourceId
	 * @param userId
	 * @param schoolId
	 * @return
	 * @throws CloudteachException
	 */
	@Override
	public CloudDiskCollectDTO collectFile(String resourceId,String userId,String schoolId) throws CloudteachException {
		//判断资源是否已收藏
		if (ctCloudDiskMapper.isMyCollect(resourceId, userId,schoolId) > 0) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.ALREADYCILLECTED);
		}
		//取出该文件，插入到disk表
		CtCloudDisk ctCloudDisk = new CtCloudDisk();
		AcOfficialResource resource = acOfficialResourceMapper.selectByPrimaryKey(resourceId);

		ctCloudDisk.setDiskId(UUID.randomUUID().toString().replace("-", ""));  //ID
		ctCloudDisk.setName(resource.getName());                       //文件名
		ctCloudDisk.setDescription(resource.getDescription());         //描述
		ctCloudDisk.setSize(resource.getSize());                       //大小
		ctCloudDisk.setUserId(userId);                                         //用户ID
		ctCloudDisk.setUnitId(resource.getUnitId());                   //单元ID
		ctCloudDisk.setFilePk(resource.getFilePk());                   //文件唯一性编号
		ctCloudDisk.setFileType(resource.getFileType());               //资源类型
		ctCloudDisk.setFileUri(resource.getFileUri());                 //HDFS资源URI
		ctCloudDisk.setExtension(resource.getExtention());             //扩展名
		ctCloudDisk.setExtType(resource.getExtType());                 //扩展名类型
		ctCloudDisk.setExtIconType(resource.getExtIconType());         //图标类型
		ctCloudDisk.setAddTime(resource.getAddTime());                 //收藏时间
		ctCloudDisk.setUpdateTime(ctCloudDisk.getAddTime());                   //修改时间
		ctCloudDisk.setShareTime(resource.getShareTime());             //分享时间
		ctCloudDisk.setCollectStatus(2);                                       //来自官方资源
		ctCloudDisk.setCreator(resource.getUserId());                  //创建者
		ctCloudDisk.setPid(resource.getResourceId());                     //源ID
		ctCloudDisk.setShareStatus(0);                                         //私有
		ctCloudDisk.setStatus(1);                                              //有效
		ctCloudDisk.setStickyStatus(0);                                        //非置顶
		ctCloudDisk.setSchoolId(schoolId);                                     //学校id
		ctCloudDisk.setAuditInstructions("");                                  //审核说明

		int result = ctCloudDiskMapper.insert(ctCloudDisk);
		if (1 < result) {
			//插入失败异常
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.INSERTDISKFAILED);
		}
		//返回结果
		CloudDiskCollectDTO cloudDiskCollectDTO = new CloudDiskCollectDTO();
		BeanUtils.copyProperties(ctCloudDisk, cloudDiskCollectDTO);
		return cloudDiskCollectDTO;
	}

	/**
	 * 取消收藏
	 * @param resourceId
	 * @param userId
	 * @param schoolId
	 * @return
	 * @throws CloudteachException
	 */
	@Override
	public CloudDiskCollectDTO unCollectFile(String resourceId, String userId, String schoolId) throws CloudteachException {
		CloudDiskCollectDTO cloudDiskCollectDTO = new CloudDiskCollectDTO();
		List<CtCloudDisk> diskList = ctCloudDiskMapper.queryMyCollect(resourceId, userId,schoolId);
		//判断是否自己的收藏资源
		if (CollectionUtils.isEmpty(diskList) ) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.DISKNOTBELONGTOUSER);
		}
		for (CtCloudDisk ctCloudDisk:diskList){
			ctCloudDisk.setStatus(0);//删除
			int result = ctCloudDiskMapper.updateByPrimaryKey(ctCloudDisk);
			if ( result <1) {
				//取消收藏异常
				CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.INSERTDISKFAILED);
			}
		}
		BeanUtils.copyProperties(diskList.get(0), cloudDiskCollectDTO);
		return cloudDiskCollectDTO;

	}

	/**
	 * 根据fileType分类计数
	 * @param unitId
	 * @param schoolId
	 * @param areaId
	 * @return
	 */
	public List<CloudDiskShareCountDTO> countByFileType(String unitId,String schoolId,String areaId){
		logger.info("根据fileType分类计数：countByFileType");
		List<CtCloudDiskShareCount> shareCountList = acOfficialResourceMapper.countByFileType(unitId,schoolId,areaId);
		List<CloudDiskShareCountDTO> countDTOs = new ArrayList<>();
		logger.info("如果shareCountList不为空，重新给初始化countDTOs赋值");
		Map<Integer, CtCloudDiskShareCount> amountDTOMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(shareCountList)){
			for (CtCloudDiskShareCount shareCount : shareCountList) {
				amountDTOMap.put(shareCount.getFileType(), shareCount);
			}
		}
		int total=0;
		CloudDiskShareCountDTO countDTO;
		logger.info("给countDTOs赋值");
		for (int i = 1; i < 7; i++) {
			countDTO = new CloudDiskShareCountDTO();
			countDTO.setFileType(i);
			Integer num = amountDTOMap.get(i) == null ? 0 : amountDTOMap.get(i).getNum();
			countDTO.setNum(num);
			countDTOs.add(countDTO);
			total += countDTO.getNum();
		}
		logger.info("全部计数total："+total);
		countDTO = new CloudDiskShareCountDTO();
		countDTO.setFileType(0);
		countDTO.setNum(total);
		countDTOs.add(countDTO);
		return countDTOs;
	}
}
