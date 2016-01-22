package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.constant.CloudTeachBankEnum;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.BankItemPageRequest;
import net.xuele.cloudteach.service.BankItemService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.form.BankItemCreateForm;
import net.xuele.cloudteach.web.form.BankItemEditForm;
import net.xuele.cloudteach.web.wrapper.BankItemRemoveWrapper;
import net.xuele.cloudteach.web.wrapper.BankItemStickyWrapper;
import net.xuele.cloudteach.web.wrapper.BankItemWithAttachmentWrapper;
import net.xuele.cloudteach.web.wrapper.BankItemWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.page.PageResponse;
import net.xuele.common.security.SessionUtil;
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
 * ElectronicQueController
 *
 * @author sunxh
 * @date 15/7/27
 */
@Controller
@RequestMapping("exercise/item")
public class ExerciseItemController {


    @Autowired
    private BankItemService bankItemService;

    private static Logger logger = LoggerFactory.getLogger(ExerciseItemController.class);


    /**
     * @param map 【请求参数】
     * @return 我的预习题
     */
    @RequestMapping(value = "index")
    public String index(HttpServletRequest request,ModelMap map) {

        /** 二级导航菜单定位 */
        request.getSession().setAttribute("lastUrl", "/teacher/initWorkPage");

        map.put("schoolName", GetSessionContentUtil.getInstance().currentSchoolName());
        map.put("userId", GetSessionContentUtil.getInstance().currentUserID());
        map.put("schoolId", GetSessionContentUtil.getInstance().currentSchoolID());
        /**
         * 获取教师所授课本是否能布置提分宝作业与同步课堂作业
         * 1小学语数英(可以布置同步课堂) 2初中数理化（可以布置提分宝） 0其他
         */
        map.put("periodType",GetSessionContentUtil.getInstance().getTeacherPeriodType());
        /**
         * 获取教师所授课本对应科目ID
         * 如果是030则显示口语作业  否则不显示
         */
        map.put("subjectId",GetSessionContentUtil.getInstance().getTeachersSubjectId());
        return "/exercise/item/index";
    }

    /**
     * @param map 【请求参数】
     * @return 我的电子作业题目
     */
    @RequestMapping(value = "new")
    public String create(ModelMap map, @RequestParam(value = "unitId", required = false) String unitId) {
        if (unitId == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }
        map.put("schoolName", GetSessionContentUtil.getInstance().currentSchoolName());
        map.put("userId", GetSessionContentUtil.getInstance().currentUserID());
        map.put("schoolId", GetSessionContentUtil.getInstance().currentSchoolID());

        map.put("unitId", unitId);
        return "/exercise/item/new";
    }

    /**
     * @param itemId 【请求参数】
     * @return 编辑我创建的电子作业题初始化
     */
    @RequestMapping(value = "editPage")
    public String editPage(ModelMap map, @RequestParam(value = "itemId", required = false) String itemId) {
        if (itemId == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }


        BankItemWithAttachmentDTO itemWithFilesDTO = bankItemService.getItemById(GetSessionContentUtil.getInstance().currentSchoolID(), itemId, CloudTeachBankEnum.ITEM_TYPE_GUIDANCE);

        map.put("item", new BankItemWithAttachmentWrapper(itemWithFilesDTO));

//        itemWithAttachmentWrapperAjaxResponse.setWrapper(new BankItemWithAttachmentWrapper(itemWithFilesDTO));

        return "/exercise/item/editPage";
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


        BankItemWithAttachmentDTO itemWithFilesDTO = bankItemService.getItemById(GetSessionContentUtil.getInstance().currentSchoolID(), itemId, CloudTeachBankEnum.ITEM_TYPE_GUIDANCE);

        map.put("user", SessionUtil.getUserSession());
        map.put("item", new BankItemWithAttachmentWrapper(itemWithFilesDTO));

        return "/exercise/item/viewPage";
    }


    /**
     * @param bankItemPageRequest 【请求参数】
     * @return 获得用户所属的预习项
     */
    @RequestMapping(value = "pageList")
    @ResponseBody
    public AjaxResponse<PageResponse<BankItemDTO>> queryMyGuidanceItemByPage(BankItemPageRequest bankItemPageRequest) {
        AjaxResponse<PageResponse<BankItemDTO>> bankItemAjaxResponse = new AjaxResponse<>();

        //获取当前用户所属的预习项
        PageResponse<BankItemDTO> bankItemDTOList = new PageResponse<>();
//        PageResponse<BankItemDTO> bankItemDTOList = bankItemService.queryItemList(GetSessionContentUtil.getInstance().currentUserID(),GetSessionContentUtil.getInstance().currentSchoolID(),);

        bankItemAjaxResponse.setWrapper(bankItemDTOList);
        bankItemAjaxResponse.setStatus("1");//设置状态为成功
        return bankItemAjaxResponse;
    }

    /**
     * @param unitId 【请求参数】
     * @return 获得用户所属的预习项
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public AjaxResponse<List<BankItemWrapper>> queryItem(@RequestParam("unitId") String unitId) {
        AjaxResponse<List<BankItemWrapper>> bankItemAjaxResponse = new AjaxResponse<>();

        //获取当前用户所属的预习项
        List<BankItemDTO> bankItemDTOList = bankItemService.queryItemList(GetSessionContentUtil.getInstance().currentUserID(),
                GetSessionContentUtil.getInstance().currentSchoolID(), unitId, CloudTeachBankEnum.ITEM_TYPE_EXERCISE.getCode());

        List<BankItemWrapper> bankItemWrapperList = new ArrayList<>();

        //转换Wrapper
        for (BankItemDTO bankItemDTO : bankItemDTOList) {
            bankItemWrapperList.add(new BankItemWrapper(bankItemDTO));
        }

        bankItemAjaxResponse.setWrapper(bankItemWrapperList);
        bankItemAjaxResponse.setStatus("1");//设置状态为成功
        return bankItemAjaxResponse;
    }

    /**
     * @param unitId 【请求参数】
     * @return 获得用户所属的预习项
     */
    @RequestMapping(value = "listMy")
    @ResponseBody
    public AjaxResponse<List<BankItemWrapper>> queryMyItem(@RequestParam("unitId") String unitId) {
        AjaxResponse<List<BankItemWrapper>> bankItemAjaxResponse = new AjaxResponse<>();

        //获取当前用户所属的预习项
        List<BankItemDTO> bankItemDTOList = bankItemService.queryItemList(GetSessionContentUtil.getInstance().currentUserID(),
                GetSessionContentUtil.getInstance().currentSchoolID(), unitId, CloudTeachBankEnum.ITEM_TYPE_EXERCISE.getCode(), CloudTeachBankEnum.ITEM_FROM_MINE.getCode());

        List<BankItemWrapper> bankItemWrapperList = new ArrayList<>();

        //转换Wrapper
        for (BankItemDTO bankItemDTO : bankItemDTOList) {
            bankItemWrapperList.add(new BankItemWrapper(bankItemDTO));
        }

        bankItemAjaxResponse.setWrapper(bankItemWrapperList);
        bankItemAjaxResponse.setStatus("1");//设置状态为成功
        return bankItemAjaxResponse;
    }

    /**
     * @param unitId 【请求参数】
     * @return 获得用户所属的预习项
     */
    @RequestMapping(value = "listMyCollect")
    @ResponseBody
    public AjaxResponse<List<BankItemWrapper>> queryMyCollect(@RequestParam("unitId") String unitId) {
        AjaxResponse<List<BankItemWrapper>> bankItemAjaxResponse = new AjaxResponse<>();

        //获取当前用户所属的预习项
        List<BankItemDTO> bankItemDTOList = bankItemService.queryItemList(GetSessionContentUtil.getInstance().currentUserID(),
                GetSessionContentUtil.getInstance().currentSchoolID(), unitId, CloudTeachBankEnum.ITEM_TYPE_EXERCISE.getCode(), CloudTeachBankEnum.ITEM_FROM_COLLECT.getCode());

        List<BankItemWrapper> bankItemWrapperList = new ArrayList<>();

        //转换Wrapper
        for (BankItemDTO bankItemDTO : bankItemDTOList) {
            bankItemWrapperList.add(new BankItemWrapper(bankItemDTO));
        }

        bankItemAjaxResponse.setWrapper(bankItemWrapperList);
        bankItemAjaxResponse.setStatus("1");//设置状态为成功
        return bankItemAjaxResponse;
    }


    /**
     * @param itemCreateForm 【请求参数】
     * @return 新建电子作业题目
     */
    @RequestMapping(value = "create")
    public String addGuidanceItem(ModelMap map, BankItemCreateForm itemCreateForm) {


        String result = "/exercise/item/success";
        String msgTitle = "创建成功";
        String msgContent = "电子作业题已经成功保存的我的题目库，您可以在作业--电子作业中管理您的电子作业题";

        BankItemCreateDTO itemCreateDTO = toBankItemCreateDTO(itemCreateForm);

        try {
            //用户ID
            itemCreateDTO.setCreator(GetSessionContentUtil.getInstance().currentUserID());
            //学校ID
            itemCreateDTO.setSchoolId(GetSessionContentUtil.getInstance().currentSchoolID());
            itemCreateDTO.setItemType(CloudTeachBankEnum.ITEM_TYPE_EXERCISE.getCode());

            //调用服务新建预习项
            bankItemService.createBankItem(itemCreateDTO);
        } catch (Exception e) {
            result = "/exercise/item/failed";
            msgTitle = "创建失败";
            msgContent = "创建电子作业题时遇到一个小问题，请重新操作。";
        }

        map.put("msgTitle", msgTitle);
        map.put("msgContent", msgContent);

        return result;
    }

    /**
     * @param itemId 【请求参数】
     * @return 查看预习项
     */
    @RequestMapping(value = "view")
    @ResponseBody
    public AjaxResponse<BankItemWithAttachmentWrapper> viewPreviewItem(@RequestParam("itemId") String itemId) {
        AjaxResponse<BankItemWithAttachmentWrapper> itemWithAttachmentWrapperAjaxResponse = new AjaxResponse<>();
        //调用服务编辑我创建的预习项初始化

        BankItemWithAttachmentDTO itemWithFilesDTO = bankItemService.getItemById(GetSessionContentUtil.getInstance().currentSchoolID(), itemId, CloudTeachBankEnum.ITEM_TYPE_EXERCISE);

        itemWithAttachmentWrapperAjaxResponse.setWrapper(new BankItemWithAttachmentWrapper(itemWithFilesDTO));
        itemWithAttachmentWrapperAjaxResponse.setStatus("1");//初始化成功
        return itemWithAttachmentWrapperAjaxResponse;
    }

    /**
     * @param itemId 【请求参数】
     * @return 编辑我创建的电子作业题目初始化
     */
    @RequestMapping(value = "initEdit")
    @ResponseBody
    public AjaxResponse<BankItemWithAttachmentWrapper> initEditGuidanceItem(@RequestParam("itemId") String itemId) {
        AjaxResponse<BankItemWithAttachmentWrapper> itemWithAttachmentWrapperAjaxResponse = new AjaxResponse<>();
        //调用服务编辑我创建的预习项初始化

        BankItemWithAttachmentDTO itemWithFilesDTO = bankItemService.getItemById(GetSessionContentUtil.getInstance().currentSchoolID(), itemId, CloudTeachBankEnum.ITEM_TYPE_EXERCISE);

        //收藏的电子作业题目不能编辑
        if (itemWithFilesDTO.getIsCollect() != null && itemWithFilesDTO.getIsCollect() == 1) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_IS_COLLECT);
        }

        itemWithAttachmentWrapperAjaxResponse.setWrapper(new BankItemWithAttachmentWrapper(itemWithFilesDTO));
        itemWithAttachmentWrapperAjaxResponse.setStatus("1");//初始化成功
        return itemWithAttachmentWrapperAjaxResponse;
    }

    /**
     * 编辑我创建的电子作业题目
     *
     * @param itemEditForm 【请求参数】
     * @return
     */
    @RequestMapping(value = "edit")
    public String editGuidanceItem(ModelMap map, BankItemEditForm itemEditForm) {


        String result = "/exercise/item/success";
        String msgTitle = "编辑成功";
        String msgContent = "电子作业题已经成功保存的我的题目库，您可以在作业--电子作业中管理您的电子作业题";

        BankItemEditDTO itemEditDTO = toBankItemEditDTO(itemEditForm);

        try {
            bankItemService.editItem(itemEditDTO, GetSessionContentUtil.getInstance().currentSchoolID(), GetSessionContentUtil.getInstance().currentUserID());

        } catch (Exception e) {
            result = "/exercise/item/failed";
            msgTitle = "编辑失败";
            msgContent = "编辑电子作业题时遇到一个小问题，请重新操作。";
        }

        map.put("msgTitle", msgTitle);
        map.put("msgContent", msgContent);

        return result;
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


    /**
     * @param itemId 【请求参数】
     * @return 置顶我的预习项
     */
    @RequestMapping(value = "sticky")
    @ResponseBody
    public AjaxResponse stickyPreviewItem(@RequestParam("itemId") String itemId) {
        AjaxResponse<BankItemStickyWrapper> topGuidanceItemWrapperAjaxResponse = new AjaxResponse<>();

        //调用服务置顶我的预习项
        bankItemService.stickyItem(GetSessionContentUtil.getInstance().currentSchoolID(), itemId, GetSessionContentUtil.getInstance().currentUserID());

        BankItemStickyWrapper itemStickyWrapper = new BankItemStickyWrapper();
        itemStickyWrapper.setItemId(itemId);
        itemStickyWrapper.setStickStatus(1);
        topGuidanceItemWrapperAjaxResponse.setWrapper(itemStickyWrapper);

        topGuidanceItemWrapperAjaxResponse.setStatus("1");//置顶成功
        return topGuidanceItemWrapperAjaxResponse;
    }

    /**
     * @param itemId 【请求参数】
     * @return 取消置顶我的预习项
     */
    @RequestMapping(value = "cancelSticky")
    @ResponseBody
    public AjaxResponse cancelStikyGuidanceItem(@RequestParam("itemId") String itemId) {

        AjaxResponse<BankItemStickyWrapper> topGuidanceItemWrapperAjaxResponse = new AjaxResponse<>();

        //调用服务置顶我的预习项
        bankItemService.cancelStickyItem(GetSessionContentUtil.getInstance().currentSchoolID(), itemId, GetSessionContentUtil.getInstance().currentUserID());

        BankItemStickyWrapper itemStickyWrapper = new BankItemStickyWrapper();
        itemStickyWrapper.setItemId(itemId);
        itemStickyWrapper.setStickStatus(0);
        topGuidanceItemWrapperAjaxResponse.setWrapper(itemStickyWrapper);

        topGuidanceItemWrapperAjaxResponse.setStatus("1");//置顶成功
        return topGuidanceItemWrapperAjaxResponse;
    }

    /**
     * 我的预习项分享
     *
     * @param itemId    预习项号
     * @param shareType 分享范围
     * @return 分享预习项
     */
    @RequestMapping(value = "share")
    @ResponseBody
    public AjaxResponse sharePreviewItem(@RequestParam("itemId") String itemId, @RequestParam("shareType") Integer shareType) {
        AjaxResponse ajaxResponse = new AjaxResponse<>();
        UserShareInfoDTO userShareInfoDTO = new UserShareInfoDTO();
        userShareInfoDTO.setItemId(itemId);
        userShareInfoDTO.setShareType(shareType);
        userShareInfoDTO.setUserId(GetSessionContentUtil.getInstance().currentUserID());
        userShareInfoDTO.setUserName(GetSessionContentUtil.getInstance().currentUserName());
        userShareInfoDTO.setSchoolId(GetSessionContentUtil.getInstance().currentSchoolID());
        userShareInfoDTO.setSchoolName(GetSessionContentUtil.getInstance().currentSchoolName());
        userShareInfoDTO.setAreaId(GetSessionContentUtil.getInstance().currentAreaID());
        userShareInfoDTO.setAreaName(GetSessionContentUtil.getInstance().currentAreaName());

        //调用服务分享预习项
        bankItemService.shareItem(userShareInfoDTO);
        ajaxResponse.setStatus("1");//分享成功
        return ajaxResponse;
    }

    /**
     * 取消收藏电子作业题目
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "cancelCollect")
    @ResponseBody
    public AjaxResponse sharePreviewItem(@RequestParam("itemId") String itemId) {
        AjaxResponse ajaxResponse = new AjaxResponse<>();

        //调用服务分享预习项
        bankItemService.cancelCollectItem(GetSessionContentUtil.getInstance().currentSchoolID(), GetSessionContentUtil.getInstance().currentUserID(), itemId);
        ajaxResponse.setStatus("1");//分享成功
        return ajaxResponse;
    }


//    ===================================private=======================================

    /**
     * createForm转DTO
     *
     * @param itemCreateForm
     * @return
     */
    private BankItemCreateDTO toBankItemCreateDTO(BankItemCreateForm itemCreateForm) {
        if (itemCreateForm == null) {
            return null;
        }
        BankItemCreateDTO bankItemCreateDTO = new BankItemCreateDTO();
        bankItemCreateDTO.setUnitId(itemCreateForm.getUnitId());
        bankItemCreateDTO.setSubImage(itemCreateForm.getSubImage());
        bankItemCreateDTO.setSubTape(itemCreateForm.getSubTape());
        bankItemCreateDTO.setSubVideo(itemCreateForm.getSubVideo());
        bankItemCreateDTO.setSubOther(itemCreateForm.getSubOther());
        bankItemCreateDTO.setContext(itemCreateForm.getContext());
        bankItemCreateDTO.setDiskIds(itemCreateForm.getDiskIds());
        return bankItemCreateDTO;
    }

    /**
     * editForm转DTO
     *
     * @param itemEditForm
     * @return
     */
    private BankItemEditDTO toBankItemEditDTO(BankItemEditForm itemEditForm) {
        if (itemEditForm == null) {
            return null;
        }
        BankItemEditDTO bankItemEditDTO = new BankItemEditDTO();
        bankItemEditDTO.setItemId(itemEditForm.getItemId());
        bankItemEditDTO.setCreator(itemEditForm.getCreator());
        bankItemEditDTO.setSchoolId(itemEditForm.getSchoolId());
        bankItemEditDTO.setUnitId(itemEditForm.getUnitId());
        bankItemEditDTO.setItemType(itemEditForm.getItemType());
        bankItemEditDTO.setSubImage(itemEditForm.getSubImage());
        bankItemEditDTO.setSubTape(itemEditForm.getSubTape());
        bankItemEditDTO.setSubVideo(itemEditForm.getSubVideo());
        bankItemEditDTO.setSubOther(itemEditForm.getSubOther());
        bankItemEditDTO.setContext(itemEditForm.getContext());
        bankItemEditDTO.setDiskIds(itemEditForm.getDiskIds());
        return bankItemEditDTO;
    }

}
