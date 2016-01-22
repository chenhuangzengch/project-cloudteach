package net.xuele.cloudteach.test.service.impl;
import net.xuele.cloudteach.service.CloudDiskShareService;
import net.xuele.cloudteach.service.Magic2WorkService;
import net.xuele.cloudteach.test.service.base.ServiceJunitBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by panglx on 2015/6/30 0030.
 */
public class ShareResourceServiceTest extends ServiceJunitBase {

	@Autowired
	private CloudDiskShareService cloudDiskShareService;
	@Autowired
	Magic2WorkService magic2WorkService;
	@Test
	public void selectUserFileShareCountTest(){
/*		UserFileSharedLikeDTO userFileSharedLikeDTO = new UserFileSharedLikeDTO();
		userFileSharedLikeDTO.setShareId("102");
		userFileSharedLikeDTO.setUserId("us2");
		Populator populator = new PopulatorBuilder().build();
		UserFileShareCountDTO userFileShareCountDTO = (UserFileShareCountDTO)populator.populateBean(UserFileShareCountDTO.class);
		userFileShareCountDTO = cloudDiskShareService.selectUserFileShareCount(userFileSharedLikeDTO.getShareId());
		System.out.print(userFileShareCountDTO);*/

	}

	@Test
	public void insertUserFileShareCount(){
/*		UserFileShareCountDTO record = new UserFileShareCountDTO();
		record.setShareId("102");
		//record.setUserId("us2");
		record.setSaveTimes(0);
		record.setReadTimes(0);
		record.setDownloadTimes(0);
		record.setUfLikecount(1);
		int result = cloudDiskShareService.insertUserFileShareCount(record);
		System.out.print(result);*/
	}

	/**
	 * 分享
	 */
	@Test
	public void testShareFile(){
//		String diskId = "0dc3e657ccd747969af83b39d7a351e4";
//		Integer shareType = 1;
//		cloudDiskShareService.shareFile(diskId,shareType);
//		System.out.print(diskId);
	}

	/**
	 * 点赞
	 */
	@Test
	public void testPraise(){
//		String shareId = "share_id_12";
//		String userId = "user_id_12";
//		cloudDiskShareService.praise(shareId,userId);
//		System.out.println(shareId);
	}

	/**
	 * 取消赞
	 */
	@Test
	public void testCancelPraise(){
//		String shareId = "share_id_12";
//		String userId = "user_id_12";
//		cloudDiskShareService.cancelSharePraise(shareId,userId);
//		System.out.println(shareId);
	}

	/**
	 * 置顶
	 */
	@Test
	public void testStickyShareFile(){
		/*String shareId = "01e9edc0e9fb4d438b52d76d45239ca6";
		CloudDiskShareDTO dto = new CloudDiskShareDTO();
		dto.setShareId(shareId);
		cloudDiskShareService.stickyShareFile(dto);*/

//		String userId = "user_id_12";
//		cloudDiskShareService.cancelSharePraise(shareId,userId);
//		System.out.println(shareId);
	}

	/**
	 * 提分宝2查询题目
	 */
	@Test
	public void testMagic2SelectQuest(){
		/*String unitId = "050009001021100001001";
		String userId = "1000110043";
		String schoolId = "10001";
		String practiceId = "";


		List<Magic2WorkQuestInfoDTO> magic2WorkQuestInfoDTOs =  magic2WorkService.showMagic2WorkList(unitId,practiceId, schoolId,userId);
*/

	}
}
