package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.constant.DiskFileTypeEnum;
import net.xuele.cloudteach.dto.CloudDiskDTO;
import net.xuele.cloudteach.service.CloudDiskService;
import net.xuele.cloudteach.web.form.CloudDiskUploadAdapterForm;
import net.xuele.cloudteach.web.form.CloudDiskUploadForm;
import net.xuele.common.ajax.AjaxResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * CloudDiskExtFLController
 *
 * @author sunxh
 * @date 2015/7/22 0022
 */

@Controller
@RequestMapping("cloudDisk/extfl")
public class CloudDiskExtFLController {

    @Autowired
    private CloudDiskService cloudDiskService;

    private static Logger logger = LoggerFactory.getLogger(CloudDiskExtFLController.class);


    /**
     * 上传资源
     *
     * @param cloudDiskUploadForm
     * @return
     */
    @RequestMapping(value = "upload")
    @ResponseBody
    public String uploadFile(CloudDiskUploadAdapterForm cloudDiskUploadForm) {
        logger.info("上传云盘资源，从Flash传入的参数为：" + cloudDiskUploadForm.toString());

        String result = "1";

        try {

            //转换数据
            CloudDiskDTO cloudDiskDTO = cloudDiskUploadForm.parseCloudDiskDTO();

            //上传
            cloudDiskService.uploadFile(cloudDiskDTO);

        } catch (Exception e) {
            result = "-1";
            logger.error("云盘上传资源时发生异常：", e);
        }

        return result.toString();
    }

    /**
     * 上传授课时产生的截屏图片资源
     *
     * @param cloudDiskUploadForm
     * @return
     */
    @RequestMapping(value = "screenshot")
    @ResponseBody
    public AjaxResponse uploadScreenshot(CloudDiskUploadForm cloudDiskUploadForm) {
        AjaxResponse ajaxResponse = new AjaxResponse();

        logger.warn("上传授课时产生的截屏图片资源，从Flash传入的参数为：" + cloudDiskUploadForm.toString());

        try {

            //封装DTO
            CloudDiskDTO cloudDiskDTO = toCloudDiskDTO(cloudDiskUploadForm);

            //资源类型默认为课程素材
            cloudDiskDTO.setFileType(DiskFileTypeEnum.MATERIAL.getValue());

            //上传文件
            cloudDiskService.uploadFile(cloudDiskDTO);

        } catch (Exception e) {
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg(e.getMessage());
            logger.error("云盘上传资源时发生异常：", e);
        }

        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 云盘上传Form对象转DTO
     *
     * @param cloudDiskUploadForm
     * @return
     */
    private CloudDiskDTO toCloudDiskDTO(CloudDiskUploadForm cloudDiskUploadForm) {
        if (cloudDiskUploadForm == null) {
            return null;
        }
        CloudDiskDTO cloudDiskDTO = new CloudDiskDTO();
        cloudDiskDTO.setSchoolId(cloudDiskUploadForm.getSchoolId());
        cloudDiskDTO.setUserId(cloudDiskUploadForm.getUserId());
        cloudDiskDTO.setUnitId(cloudDiskUploadForm.getUnitId());
        cloudDiskDTO.setFileType(cloudDiskUploadForm.getFileType());
        cloudDiskDTO.setFileUri(cloudDiskUploadForm.getFileUri());
        cloudDiskDTO.setName(cloudDiskUploadForm.getName());
        cloudDiskDTO.setExtension(cloudDiskUploadForm.getExtension());
        cloudDiskDTO.setSize(cloudDiskUploadForm.getSize());
        return cloudDiskDTO;
    }
}
