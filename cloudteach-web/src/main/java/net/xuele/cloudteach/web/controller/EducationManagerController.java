package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import net.xuele.cloudteach.constant.CloudTeachBankEnum;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.dto.AfterClassWorkViewDTO;
import net.xuele.cloudteach.dto.BankItemWithAttachmentDTO;
import net.xuele.cloudteach.dto.CloudDiskReferDTO;
import net.xuele.cloudteach.dto.SubjectGatherViewDTO;
import net.xuele.cloudteach.dto.page.AfterClassWorkViewPageRequest;
import net.xuele.cloudteach.service.*;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.wrapper.AfterClassWorkViewWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.dto.ClassInfoDTO;
import net.xuele.common.page.PageResponse;
import net.xuele.common.security.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujx on 2015/10/19 0019.
 * 教务管理-课外作业
 */
@Controller
@RequestMapping(value = "workmanage/manager")
public class EducationManagerController {

    private static Logger logger = LoggerFactory.getLogger(EducationManagerController.class);

    @Autowired
    private TeacherWorkManageService teacherWorkManageService;
    @Autowired
    private WorkStatisticsService workStatisticsService;
    @Autowired
    private CloudTeachService cloudTeachService;
    @Autowired
    private TeacherWorkService teacherWorkService;
    @Autowired
    BankItemService bankItemService;

    /**
     * @param map 【请求参数】
     * @return 我的预习题
     */
    @RequestMapping(value = "extralist")
    public String extralist(HttpServletRequest request, ModelMap map) {
        logger.info("[课外作业题目列表初始化：]");
        return "workmanage/maneger/extraList";
    }

        /**
     * @param itemId 【请求参数】
     * @return 编辑我创建的预习题初始化
     */
    @RequestMapping(value = "viewPage")
    public String viewPage(ModelMap map, @RequestParam(value = "itemId", required = false) String itemId) {
        if (itemId == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

        BankItemWithAttachmentDTO itemWithFilesDTO = bankItemService.getItemById(schoolId, itemId, CloudTeachBankEnum.FILE_TYPE_TEACHERQUE);

        map.put("user", SessionUtil.getUserSession());
        map.put("item", itemWithFilesDTO);

        return "workmanage/maneger/viewPage";
    }
    /**
     * 作业列表初始化
     *
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "initExtraWorkList")
    public String initExtraWorkList(ModelMap map, AfterClassWorkViewPageRequest request) {
        logger.info("[已发布课外作业列表初始化：]");
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        logger.info("作业列表初始化,userId:" + userId + ",schoolId:" + schoolId);
        map.put("currPage", "extra");

        return "workmanage/maneger/extraWorkList";
    }

    /**
     * @param request 【请求参数】
     * @return 分页获取课后作业信息（默认显示全部班级，主授课目下的所有作业，排序方式：按作业状态排序（进行中、定时未发布、其他））
     */
    @RequestMapping(value = "extraWorkList")
    @ResponseBody
    public AjaxResponse<PageResponse<AfterClassWorkViewWrapper>> queryMyExtraWork(AfterClassWorkViewPageRequest request) {
        logger.info("[获取已发布课外作业列表：]");
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        request.setUserId(userId);
        request.setSchoolId(schoolId);
        logger.info("分页获取课后作业信息,userId:" + userId + ",schoolId:" + schoolId);
        //返回对象
        AjaxResponse<PageResponse<AfterClassWorkViewWrapper>> ajaxResponse = new AjaxResponse<>();

        //调用服务查询出数据存放在DTO中
        PageResponse<AfterClassWorkViewDTO> extraWorkPageResponse = teacherWorkManageService.queryExtraWork(request);
        //数据转换目标PageResponse<wrapper>对象
        PageResponse<AfterClassWorkViewWrapper> extraWorkViewPageResponse = new PageResponse<>();
        //数据转换中间wrapper对象
        List<AfterClassWorkViewWrapper> extraWorkViewWrapperlist = new ArrayList<>();

        //转换Wrapper
        for (AfterClassWorkViewDTO acwvDTO : extraWorkPageResponse.getRows()) {
            AfterClassWorkViewWrapper wrapperObj = new AfterClassWorkViewWrapper(acwvDTO);
            wrapperObj.setUserName(GetSessionContentUtil.getInstance().currentUserName());//设置用户名称
            wrapperObj.setUserIconUrl(GetSessionContentUtil.getInstance().currentUserIcon());//设置用户头像
            extraWorkViewWrapperlist.add(wrapperObj);
        }
        BeanUtils.copyProperties(extraWorkPageResponse, extraWorkViewPageResponse);
        extraWorkViewPageResponse.setRows(extraWorkViewWrapperlist);

        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(extraWorkViewPageResponse);
        return ajaxResponse;
    }

    /**
     * @param map [请求参数]
     * @return
     */
    @RequestMapping(value = "tonewpublish")
    public String toNewPublish(ModelMap map) {
        logger.info("[workmanage/maneger/tonewpublish:新建并发布课外作业初始化]");
        return "/workmanage/maneger/newandpublish";
    }


    /**
     * @param map
     * @param itemId
     * @return
     */
    @RequestMapping(value = "toeditpublish")
    public String toEditPublish(ModelMap map, @RequestParam(value = "itemList", required = false) String itemId) {
        logger.info("[workmanage/maneger/toeditpublish:编辑并发布课外作业初始化]");
        if (itemId == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }
        BankItemWithAttachmentDTO itemWithFilesDTO = bankItemService.getItemById(GetSessionContentUtil.getInstance().currentSchoolID(), itemId, CloudTeachBankEnum.FILE_TYPE_TEACHERQUE);


        List<CloudDiskReferDTO> fileList = itemWithFilesDTO.getAttachmentList();
        String fileJson = "";
        // 将已有的附件转化成json格式传给页面
        if (CollectionUtils.isNotEmpty(fileList)) {
            StringBuffer sb = new StringBuffer();
            for (CloudDiskReferDTO file : fileList) {
                sb.append("{'fileName':'" + file.getName() + "',");
                sb.append("'extension':'" + file.getExtension() + "',");
                sb.append("'size':'" + file.getSize() + "',");
                sb.append("'uri':'" + file.getFileUri() + "',");
                sb.append("'type':'" + file.getFileType() + "'},");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            fileJson = sb.toString();
        }

        map.put("item", itemWithFilesDTO);
        map.put("fileJson", fileJson);
        return "/workmanage/maneger/editandpublish";
    }
}
