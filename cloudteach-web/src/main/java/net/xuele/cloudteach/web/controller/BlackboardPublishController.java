package net.xuele.cloudteach.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.service.BlackboardPublishService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.form.BlackboardPublishForm;
import net.xuele.cloudteach.web.form.BlackboardPublishUploadForm;
import net.xuele.cloudteach.web.wrapper.BlackboardPublishFilesWrapper;
import net.xuele.cloudteach.web.wrapper.BlackboardPublishWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.member.constant.IdentityIdConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * BlackboardPublishController
 * 板书
 *
 * @author cm.wang
 * @date 2015/7/16 0020
 */

@Controller
@RequestMapping(value = "/blackboardPublish")
public class BlackboardPublishController {

    @Autowired
    private BlackboardPublishService blackboardPublishService;

    private static Logger logger = LoggerFactory.getLogger(BlackboardPublishController.class);


    @RequestMapping(value = "publish")
    public String index() {
        return "blackboard/publish";
    }

    /**
     * 用户查看板书的详细信息
     *
     * @param blackboardId
     * @return
     */
    @RequestMapping(value = "/getBlackboardPublish")
    @ResponseBody
    public AjaxResponse<BlackboardPublishWrapper> getBlackboardPublish(String blackboardId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        BlackboardPublishInfoDTO infoDTO = blackboardPublishService.getBlackboardPublishById(blackboardId, schoolId, userId);

        BlackboardPublishWrapper wrapper = new BlackboardPublishWrapper();
        BeanUtils.copyProperties(infoDTO, wrapper);
        List<BlackboardPublishFilesWrapper> filesWrapperList = new ArrayList<BlackboardPublishFilesWrapper>();
        List<BlackboardPublishFilesDTO> filesDTOList = infoDTO.getFilesDTOList();
        for (BlackboardPublishFilesDTO filesDTO : filesDTOList) {
            BlackboardPublishFilesWrapper filesWrapper = new BlackboardPublishFilesWrapper();
            BeanUtils.copyProperties(filesDTO, filesWrapper);
            filesWrapperList.add(filesWrapper);
        }
        wrapper.setResources(filesWrapperList);
        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(wrapper);
        return ajaxResponse;
    }

    /**
     * 用于适配flash上传组件，获取返回结果，封装成BlackboardPublishFilesDTO
     *
     * @param blackboardPublishUploadForm
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "flashUpload")
    public AjaxResponse uploadAdapter(BlackboardPublishUploadForm blackboardPublishUploadForm) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //如果是tomcat 转字符
        if (blackboardPublishUploadForm.getClass().getResource("/org/apache/catalina/startup/Bootstrap.class") != null) {
            try {
                blackboardPublishUploadForm.setName(new String(blackboardPublishUploadForm.getName().getBytes("ISO-8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.CHARSETERROR);
            }
        }

        BlackboardPublishFilesDTO filesDTO = blackboardPublishUploadForm.parseBlackboardPublishFilesDTO(schoolId);//转换结构
        String result = "1";

        //插入数据
        try {
            blackboardPublishService.uploadFile(filesDTO);
        } catch (Exception e) {
            result = "-1";
        }
        ajaxResponse.setStatus(result);
        return ajaxResponse;
    }

    @RequestMapping(value = "/addBlackboardPublish")
    public String addBlackboardPublish(ModelMap map, BlackboardPublishForm blackboardPublishForm) {

        String classJson = blackboardPublishForm.getClassJson();
        String filesJson = blackboardPublishForm.getFileJson();

        String context = blackboardPublishForm.getContext();
        //内容不为空

        if (context == null) {
            blackboardPublishForm.setContext("");
//            throw new CloudteachException(CloudTeachErrorEnum.BLACKBOARD_CONTENT_NOT_EXIST.getMsg(),
//                    CloudTeachErrorEnum.BLACKBOARD_CONTENT_NOT_EXIST.getCode());
        }

        //班级不为空
        if (classJson == null || classJson.equals("[]")) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_CLASS_NULL.getMsg(),
                    CloudTeachErrorEnum.WORK_CLASS_NULL.getCode());
        }

        //附件不为空
        if (filesJson == null || filesJson.equals("[]")) {
            throw new CloudteachException(CloudTeachErrorEnum.BLACKBOARD_File_NOT_EXIST.getMsg(),
                    CloudTeachErrorEnum.BLACKBOARD_File_NOT_EXIST.getCode());
        }


        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String userName = GetSessionContentUtil.getInstance().currentUserName();
        String userIcon = GetSessionContentUtil.getInstance().currentUserIcon();
        String identifyId = GetSessionContentUtil.getInstance().getIdentifyId();
        if (IdentityIdConstants.SCHOOL_MANAGER.equals(identifyId)) {
            userName = "学校管理员";
        } else {
            userName = userName + "老师";
        }
        BlackboardPublishInfoDTO blackboardPublishInfoDTO = new BlackboardPublishInfoDTO();
        BlackboardPublishDTO blackboardPublishDTO = new BlackboardPublishDTO();
        BeanUtils.copyProperties(blackboardPublishForm, blackboardPublishDTO);
        blackboardPublishDTO.setSchoolId(schoolId);
        blackboardPublishDTO.setUserId(userId);


        List<BlackboardPublishClassDTO> blackboardPublishClassDTOList = new ArrayList<BlackboardPublishClassDTO>();
        for (String classId : blackboardPublishForm.getClassList()) {
            BlackboardPublishClassDTO classDTO = new BlackboardPublishClassDTO();
            classDTO.setClassId(classId);
            blackboardPublishClassDTOList.add(classDTO);
        }
        Object jsonArray = JSONArray.parse(filesJson);
        List<BlackboardPublishFilesDTO> filesDTOList = JSON.parseArray(jsonArray.toString(), BlackboardPublishFilesDTO.class);

        blackboardPublishInfoDTO.setBlackboardPublishDTO(blackboardPublishDTO);
        blackboardPublishInfoDTO.setBlackboardPublishClassDTOList(blackboardPublishClassDTOList);
        blackboardPublishInfoDTO.setClassJson(classJson);
        blackboardPublishInfoDTO.setFilesDTOList(filesDTOList);

        String url = null;
        String response = null;
        try {
            response = blackboardPublishService.addBlackboardPublish(blackboardPublishInfoDTO, null, userName, userIcon);
        } catch (CloudteachException e) {
            return "/blackboard/fail";
        }
        if ("".equals(response)) {
            url = "/blackboard/success";
        } else {
            url = "/blackboard/fail";
            map.put("error", response);
        }
        return url;
    }

    @RequestMapping(value = "/editBlackboardPublish")
    @ResponseBody
    public AjaxResponse editBlackboardPublish(BlackboardPublishForm blackboardPublishForm) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String classJson = blackboardPublishForm.getClassJson();
        String filesJson = blackboardPublishForm.getFileJson();

        BlackboardPublishInfoDTO blackboardPublishInfoDTO = new BlackboardPublishInfoDTO();
        BlackboardPublishDTO blackboardPublishDTO = new BlackboardPublishDTO();
        BeanUtils.copyProperties(blackboardPublishForm, blackboardPublishDTO);
        blackboardPublishDTO.setSchoolId(schoolId);
        blackboardPublishDTO.setUserId(userId);
//        List<String> fileKeyList = blackboardPublishForm.getFileKeyList();
        List<BlackboardPublishClassDTO> blackboardPublishClassDTOList = new ArrayList<BlackboardPublishClassDTO>();
        for (String classId : blackboardPublishForm.getClassList()) {
            BlackboardPublishClassDTO classDTO = new BlackboardPublishClassDTO();
            classDTO.setClassId(classId);
            blackboardPublishClassDTOList.add(classDTO);
        }
        blackboardPublishInfoDTO.setBlackboardPublishDTO(blackboardPublishDTO);
        blackboardPublishInfoDTO.setBlackboardPublishClassDTOList(blackboardPublishClassDTOList);

        Object jsonArray = JSONArray.parse(filesJson);
        List<BlackboardPublishFilesDTO> filesDTOList = JSON.parseArray(jsonArray.toString(), BlackboardPublishFilesDTO.class);

        blackboardPublishInfoDTO.setFilesDTOList(filesDTOList);
        blackboardPublishInfoDTO.setClassJson(classJson);
//        BlackboardPublishInfoDTO blackboardPublishInfoDTO = new BlackboardPublishInfoDTO();
//        BlackboardPublishDTO blackboardPublishDTO = new BlackboardPublishDTO();
//        blackboardPublishDTO.setContext("我爱温州，i");
//        blackboardPublishDTO.setUserId(userId);
//        blackboardPublishDTO.setBlackboardId("f2b0bb063c634d8eb53f60d1ab5ec366");
//        blackboardPublishDTO.setUnitId("0002");
//        blackboardPublishDTO.setSchoolId(schoolId);
//        List<BlackboardPublishFilesDTO> blackboardPublishFilesDTOList = new ArrayList<BlackboardPublishFilesDTO>();
//        for(int i=0;i<4;i++){
//            BlackboardPublishFilesDTO dto = new BlackboardPublishFilesDTO();
//            dto.setUrl("www.baidu.com");
//            dto.setFileName("中国温州"+i);
//            blackboardPublishFilesDTOList.add(dto);
//        }
//        List<BlackboardPublishClassDTO> blackboardPublishClassDTOList = new ArrayList<BlackboardPublishClassDTO>();
//        BlackboardPublishClassDTO dto = new BlackboardPublishClassDTO();
//        dto.setClassId("4d48d98dfb0d4bbcad5f637d8ecec7e4");
//        blackboardPublishClassDTOList.add(dto);
//        BlackboardPublishClassDTO dto1 = new BlackboardPublishClassDTO();
//        dto1.setClassId("08d7779434a048c7830bf99687c20769");
//        blackboardPublishClassDTOList.add(dto1);
//        blackboardPublishInfoDTO.setBlackboardPublishDTO(blackboardPublishDTO);
//        blackboardPublishInfoDTO.setBlackboardPublishClassDTOList(blackboardPublishClassDTOList);
//        blackboardPublishInfoDTO.setBlackboardPublishFilesDTOList(blackboardPublishFilesDTOList);

        blackboardPublishService.editBlackboardPublish(blackboardPublishInfoDTO);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    @RequestMapping(value = "/delBlackboardPublish")
    @ResponseBody
    public AjaxResponse delBlackboardPublish(String blackboardId) {
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        blackboardPublishService.delBlackboardPublish(blackboardId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }
}
