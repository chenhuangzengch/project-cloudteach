package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.constant.CloudTeachBankEnum;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.BankItemDTO;
import net.xuele.cloudteach.dto.BankItemWithAttachmentDTO;
import net.xuele.cloudteach.dto.CloudDiskDTO;
import net.xuele.cloudteach.service.BankItemService;
import net.xuele.cloudteach.service.CloudDiskService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.wrapper.BankItemRemoveWrapper;
import net.xuele.cloudteach.web.wrapper.BankItemWithAttachmentWrapper;
import net.xuele.cloudteach.web.wrapper.BankItemWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.security.SessionUtil;
import net.xuele.member.constant.IdentityIdConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Created by hujx on 2015/10/14 0014.
 * 课外作业题目管理
 */
@Controller
@RequestMapping("extra/item")
public class ExtraItemController {

    private static Logger logger = LoggerFactory.getLogger(ExerciseItemController.class);

    @Autowired
    private BankItemService bankItemService;
    @Autowired
    private CloudDiskService cloudDiskService;

    /**
     * @param map 【请求参数】
     * @return 我的预习题
     */
    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, ModelMap map) {

        /** 二级导航菜单定位 */
        request.getSession().setAttribute("lastUrl", "/teacher/initWorkPage");

        map.put("schoolName", GetSessionContentUtil.getInstance().currentSchoolName());
        map.put("userId", GetSessionContentUtil.getInstance().currentUserID());
        map.put("schoolId", GetSessionContentUtil.getInstance().currentSchoolID());
        /**
         * 获取教师所授课本是否能布置提分宝作业与同步课堂作业
         * 1小学语数英(可以布置同步课堂) 2初中数理化（可以布置提分宝） 0其他
         */
        map.put("periodType", GetSessionContentUtil.getInstance().getTeacherPeriodType());
        /**
         * 获取教师所授课本对应科目ID
         * 如果是030则显示口语作业  否则不显示
         */
        map.put("subjectId", GetSessionContentUtil.getInstance().getTeachersSubjectId());
        return "/extra/item/index";
    }

    /**
     * @param itemId 【请求参数】
     * @return 编辑我创建的预习题初始化
     */
    @RequestMapping(value = "viewPage")
    public String viewPage(ModelMap map, @RequestParam(value = "itemId", required = false) String itemId) {

        String url = "/extra/item/viewPage";

        if (itemId == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String identifyId = GetSessionContentUtil.getInstance().getIdentifyId();

        BankItemWithAttachmentDTO itemWithFilesDTO = bankItemService.getItemById(schoolId, itemId, CloudTeachBankEnum.FILE_TYPE_TEACHERQUE);

        map.put("user", SessionUtil.getUserSession());
        map.put("item", itemWithFilesDTO);

        if (IdentityIdConstants.SCHOOL_MANAGER.equals(identifyId)) {
            url = "/workmanage/maneger/viewPage";
        }

        return url;
    }

    /**
     * 获取教师主授科目下所有云盘资源
     *
     * @return
     */
    @RequestMapping(value = "allresource")
    @ResponseBody
    public AjaxResponse<List<CloudDiskDTO>> queryAllDiskResource() {

        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String subjectId = GetSessionContentUtil.getInstance().getTeachersSubjectId();

        AjaxResponse<List<CloudDiskDTO>> AjaxResponse = new AjaxResponse<>();

        List<CloudDiskDTO> allResource = cloudDiskService.getAllResourceBySubject(subjectId, userId, schoolId);
        AjaxResponse.setWrapper(allResource);
        AjaxResponse.setStatus("1");
        return AjaxResponse;
    }

    /**
     * 获取该科目下所有的课外作业题目的列表
     *
     * @return
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public AjaxResponse<List<BankItemWrapper>> queryExtraItem() {

        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //String subjectId = GetSessionContentUtil.getInstance().getTeachersSubjectId();
        //if (subjectId == null) {
        //    subjectId = "";
        //}

        AjaxResponse<List<BankItemWrapper>> bankItemAjaxResponse = new AjaxResponse<>();
        // 获取当前用户主授科目下所有课外作业题目列表
        List<BankItemDTO> bankItemDTOList = bankItemService.queryItemList(userId,
                schoolId, "", CloudTeachBankEnum.ITEM_TYPE_EXTRA.getCode());

        List<BankItemWrapper> bankItemWrapperList = new ArrayList<>();
        //转换Wrapper
        for (BankItemDTO bankItemDTO : bankItemDTOList) {
            bankItemWrapperList.add(new BankItemWrapper(bankItemDTO));
        }

        bankItemAjaxResponse.setWrapper(bankItemWrapperList);
        bankItemAjaxResponse.setStatus("1");
        return bankItemAjaxResponse;
    }

    /**
     * @param itemId 【请求参数】
     * @return 删除我创建的电子作业题目
     */
    @RequestMapping(value = "remove")
    @ResponseBody
    public AjaxResponse<BankItemRemoveWrapper> removePreviewItem(@RequestParam("itemId") String itemId) {
        AjaxResponse<BankItemRemoveWrapper> itemRemoveWrapperAjaxResponse = new AjaxResponse<>();
        //调用服务删除我创建的预习项
        bankItemService.removeItem(GetSessionContentUtil.getInstance().currentSchoolID(), itemId, GetSessionContentUtil.getInstance().currentUserID());

        BankItemRemoveWrapper itemRemoveWrapper = new BankItemRemoveWrapper();
        itemRemoveWrapper.setItemId(itemId);
        itemRemoveWrapper.setStatus(0);
        itemRemoveWrapperAjaxResponse.setWrapper(itemRemoveWrapper);

        itemRemoveWrapperAjaxResponse.setStatus("1");//删除成功
        return itemRemoveWrapperAjaxResponse;
    }
}
