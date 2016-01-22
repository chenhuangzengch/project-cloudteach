package net.xuele.cloudteach.test.service.impl;

import net.xuele.cloudteach.constant.ExtensionTypeUtil;
import net.xuele.cloudteach.dto.CloudDiskDTO;
import net.xuele.cloudteach.service.CloudDiskService;
import net.xuele.cloudteach.test.service.base.ServiceJunitBase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * CloudDiskServiceTest
 *
 * @author sunxh
 * @date 2015/6/25 0025
 */
public class CloudDiskServiceTest extends ServiceJunitBase {

    @Autowired
    CloudDiskService cloudDiskService;

    @Test
    public void testUploadFile(){
//        CloudDiskDTO cloudDiskDTO = new CloudDiskDTO();
//
//        cloudDiskDTO.setDiskId(UUID.randomUUID().toString().replace("-", ""));
//        cloudDiskDTO.setAddTime(new Date());
//        cloudDiskDTO.setStickyStatus(0);
//        cloudDiskDTO.setExtension("doc");
//        cloudDiskDTO.setExtIconType(ExtensionTypeUtil.getInstance().getIcon(cloudDiskDTO.getExtension()));
//        cloudDiskDTO.setAuditInstructions("");
//        cloudDiskDTO.setDescription("测试描述");
//        cloudDiskDTO.setFileUri(UUID.randomUUID().toString().replace("-",""));
//        cloudDiskDTO.setName("测试文件名");
//        cloudDiskDTO.setExtType(ExtensionTypeUtil.getInstance().getExtTypeCode(cloudDiskDTO.getExtension()));
//        cloudDiskDTO.setShareStatus(0);
//        cloudDiskDTO.setSize(123300);
//        cloudDiskDTO.setUserId("10001");
//        cloudDiskDTO.setUnitId("1000100003");
//        cloudDiskDTO.setUpdateTime(cloudDiskDTO.getAddTime());
//
//
//        //插入数据
//        cloudDiskDTO = cloudDiskService.uploadFile(cloudDiskDTO);
//
//        Assert.assertNotNull(cloudDiskDTO.getDiskId());
    }
}
