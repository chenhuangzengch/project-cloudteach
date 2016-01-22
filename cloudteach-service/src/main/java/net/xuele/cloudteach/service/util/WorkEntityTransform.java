package net.xuele.cloudteach.service.util;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.view.*;
import net.xuele.member.util.ClassNameUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 实体转换工具类
 * Created by duzg on 2015/7/18 0002.
 */
public class WorkEntityTransform {

    /**
     * @param datalist
     * 作业回答信息List<domain>转换成List<DTO>
     */
    public static List<WorkAnswerViewDTO> entityWorkAswViewListToDtoList(List<WorkAnswerView> datalist) {
        if(datalist==null){
            return null;
        }
        List<WorkAnswerViewDTO> resList = new ArrayList<>();
        for (WorkAnswerView objDATA : datalist) {
            WorkAnswerViewDTO objDTO = new WorkAnswerViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            objDTO.setFileViewList(entityWorkAnswerFileListToDtoList(objDATA.getFileViewList()));
            objDTO.setCommentViewList(entityWorkAnswerCommentListToDtoList(objDATA.getCommentViewList()));
            objDTO.setPraiseViewList(entityWorkAnswerPraiseListToDtoList(objDATA.getPraiseViewList()));
            resList.add(objDTO);
        }
        return resList;
    }
    /**
     * @param datalist
     * 作业回答附件信息List<domain>转换成List<DTO>
     */
    public static List<WorkAnswerFileViewDTO> entityWorkAnswerFileListToDtoList(List<WorkAnswerFileView> datalist) {
        if(datalist==null){
            return null;
        }
        List<WorkAnswerFileViewDTO> resList = new ArrayList<>();
        for (WorkAnswerFileView objDATA : datalist) {
            WorkAnswerFileViewDTO objDTO = new WorkAnswerFileViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }
    /**
     * @param datalist
     * 作业回答评论List<domain>转换成List<DTO>
     */
    public static List<WorkAnswerCommentViewDTO> entityWorkAnswerCommentListToDtoList(List<WorkAnswerCommentView> datalist) {
        if(datalist==null){
            return null;
        }
        List<WorkAnswerCommentViewDTO> resList = new ArrayList<>();
        for (WorkAnswerCommentView objDATA : datalist) {
            WorkAnswerCommentViewDTO objDTO = new WorkAnswerCommentViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }
    /**
     * @param datalist
     * 作业回答点赞List<domain>转换成List<DTO>
     */
    public static List<WorkAnswerPraiseViewDTO> entityWorkAnswerPraiseListToDtoList(List<WorkAnswerPraiseView> datalist) {
        if(datalist==null){
            return null;
        }
        List<WorkAnswerPraiseViewDTO> resList = new ArrayList<>();
        for (WorkAnswerPraiseView objDATA : datalist) {
            WorkAnswerPraiseViewDTO objDTO = new WorkAnswerPraiseViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }
    /**
     * @param datalist
     * 作业发布班级信息List<domain>转换成List<DTO>
     */
    public static List<WorkClassViewDTO> entityWorkClassViewListToDtoList(List<WorkClassView> datalist) {
        if(datalist==null){
            return null;
        }
        List<WorkClassViewDTO> resList = new ArrayList<>();
        for (WorkClassView objDATA : datalist) {
            WorkClassViewDTO objDTO = new WorkClassViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            objDTO.setClassName(ClassNameUtil.getFullName(objDATA.getYears(),objDATA.getCodeSharing()));
            resList.add(objDTO);
        }
        return resList;
    }

    /**
     * @param datalist
     * 作业下未提交回答学生信息List<domain>转换成List<DTO>
     */
    public static List<WorkUnSubStudentViewDTO> entityWorkUnSubStudentListToDtoList(List<WorkUnSubStudentView> datalist) {
        if(datalist==null){
            return null;
        }
        List<WorkUnSubStudentViewDTO> resList = new ArrayList<>();
        for (WorkUnSubStudentView objDATA : datalist) {
            WorkUnSubStudentViewDTO objDTO = new WorkUnSubStudentViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }
}
