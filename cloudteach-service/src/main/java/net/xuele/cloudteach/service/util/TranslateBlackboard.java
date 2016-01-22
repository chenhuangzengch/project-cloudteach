package net.xuele.cloudteach.service.util;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.BlackboardPublishClassDTO;
import net.xuele.cloudteach.dto.BlackboardPublishDTO;
import net.xuele.cloudteach.dto.BlackboardPublishFilesDTO;
import net.xuele.cloudteach.dto.BlackboardPublishStudentDTO;
import net.xuele.cloudteach.view.FileInfoView;
import net.xuele.cloudteach.view.UnitBookNameView;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by cm.wang on 2015/7/15 0017.
 */
public class TranslateBlackboard {


    /**
     * 获得未存在的板书发布表
     * @param blackboardPublishDTO
     * @return
     */
    public  static CtBlackboardPublish getCtBlackboardPublish(BlackboardPublishDTO blackboardPublishDTO){
        CtBlackboardPublish ctBlackboardPublish = new CtBlackboardPublish();
        if(blackboardPublishDTO != null){
            blackboardPublishDTO.setStatus(1);
            blackboardPublishDTO.setPublishTime(new Date());
            blackboardPublishDTO.setBlackboardId(UUID.randomUUID().toString().replace("-", ""));
            BeanUtils.copyProperties(blackboardPublishDTO, ctBlackboardPublish);
        }
        if(ctBlackboardPublish.getAddLessonPlan()== null){
            //默认是添加到教案里的
            ctBlackboardPublish.setAddLessonPlan(1);
        }
        return ctBlackboardPublish;
    }

    /**
     * 获得已存在的板书发布表
     * @param blackboardPublishDTO
     * @return
     */
    public  static CtBlackboardPublish getExistCtBlackboardPublish(BlackboardPublishDTO blackboardPublishDTO){
        CtBlackboardPublish ctBlackboardPublish = new CtBlackboardPublish();
        if(blackboardPublishDTO != null){
            blackboardPublishDTO.setStatus(1);
            blackboardPublishDTO.setPublishTime(new Date());
            BeanUtils.copyProperties(blackboardPublishDTO, ctBlackboardPublish);
        }
        return ctBlackboardPublish;
    }

    /**
     * 板书发布附件
     * @param blackboardPublishFilesDTOList
     * @param blackboardId 板书Id
     * @return
     */
    public  static List<CtBlackboardPublishFiles> getCtBlackboardPublishFilesList(
            List<BlackboardPublishFilesDTO> blackboardPublishFilesDTOList,String blackboardId,String schoolId){
        List<CtBlackboardPublishFiles> ctBlackboardPublishFilesList = new ArrayList<CtBlackboardPublishFiles>();
        if(blackboardPublishFilesDTOList != null){
            for(BlackboardPublishFilesDTO blackboardPublishFilesDTO : blackboardPublishFilesDTOList){
                CtBlackboardPublishFiles ctBlackboardPublishFiles = new CtBlackboardPublishFiles();
                blackboardPublishFilesDTO.setStatus(1);
                blackboardPublishFilesDTO.setSchoolId(schoolId);
                blackboardPublishFilesDTO.setBlackboardId(blackboardId);
                blackboardPublishFilesDTO.setUploadTime(new Date());
                blackboardPublishFilesDTO.setFileId(UUID.randomUUID().toString().replace("-", ""));
                BeanUtils.copyProperties(blackboardPublishFilesDTO, ctBlackboardPublishFiles);
                String fileName = ctBlackboardPublishFiles.getFileName();
                ctBlackboardPublishFiles.setFileName(StringUtil.fileNameSubstring(fileName, Constants.MAX_FILE_NAME_LENGTH)
                );
                ctBlackboardPublishFilesList.add(ctBlackboardPublishFiles);
            }
        }
        return ctBlackboardPublishFilesList;
    }

    /**
     * 板书发布班级
     * @param blackboardPublishClassDTOList
     * @param blackboardId
     * @return
     */
    public static List<CtBlackboardPublishClass> getCtBlackboardPublishClassList(
            List<BlackboardPublishClassDTO> blackboardPublishClassDTOList,String blackboardId,String schoolId){
        List<CtBlackboardPublishClass> ctBlackboardPublishClassList = new ArrayList<CtBlackboardPublishClass>();
        if(blackboardPublishClassDTOList != null){
            for(BlackboardPublishClassDTO blackboardPublishClassDTO : blackboardPublishClassDTOList){
                CtBlackboardPublishClass ctBlackboardPublishClass = new CtBlackboardPublishClass();
                blackboardPublishClassDTO.setStatus(1);
                blackboardPublishClassDTO.setSchoolId(schoolId);
                blackboardPublishClassDTO.setBlackboardId(blackboardId);
                blackboardPublishClassDTO.setBlackboardClassId(UUID.randomUUID().toString().replace("-", ""));
                BeanUtils.copyProperties(blackboardPublishClassDTO, ctBlackboardPublishClass);
                ctBlackboardPublishClassList.add(ctBlackboardPublishClass);
            }
        }
        return ctBlackboardPublishClassList;
    }


    public static List<CtBlackboardPublishClass> getCtBlackboardPublishClassListByClassList(
            List<String> classList,String blackboardId,String schoolId){
        List<CtBlackboardPublishClass> ctBlackboardPublishClassList = new ArrayList<CtBlackboardPublishClass>();
        for(String classId : classList){
            CtBlackboardPublishClass ctBlackboardPublishClass = new CtBlackboardPublishClass();
            ctBlackboardPublishClass.setStatus(1);
            ctBlackboardPublishClass.setBlackboardId(blackboardId);
            ctBlackboardPublishClass.setClassId(classId);
            ctBlackboardPublishClass.setSchoolId(schoolId);
            ctBlackboardPublishClass.setBlackboardClassId(UUID.randomUUID().toString().replace("-", ""));
            ctBlackboardPublishClassList.add(ctBlackboardPublishClass);
        }
        return ctBlackboardPublishClassList;
    }

    /**
     * 板书发布学生
     * @param blackboardPublishStudentDTOList
     * @param blackboardId
     * @return
     */
    public  static List<CtBlackboardPublishStudent> getCtBlackboardPublishStudentList(
            List<BlackboardPublishStudentDTO> blackboardPublishStudentDTOList,String blackboardId){
        List<CtBlackboardPublishStudent> ctBlackboardPublishStudentList = new ArrayList<CtBlackboardPublishStudent>();
        for(BlackboardPublishStudentDTO blackboardPublishStudentDTO : blackboardPublishStudentDTOList){
            CtBlackboardPublishStudent ctBlackboardPublishStudent = new CtBlackboardPublishStudent();
            blackboardPublishStudentDTO.setStatus(1);
            blackboardPublishStudentDTO.setBlackboardId(blackboardId);
            blackboardPublishStudentDTO.setBlackboardUserId(UUID.randomUUID().toString().replace("-", ""));
            BeanUtils.copyProperties(blackboardPublishStudentDTO, ctBlackboardPublishStudent);
            ctBlackboardPublishStudentList.add(ctBlackboardPublishStudent);
        }
        return ctBlackboardPublishStudentList;
    }

    /**
     * 板书entityList转dto
     * @param ctBlackboardPublishList
     * @return
     */
    public static List<BlackboardPublishDTO> entityBlackboardPublishListToDtoList(List<CtBlackboardPublish> ctBlackboardPublishList){
        List<BlackboardPublishDTO> blackboardPublishDTOList = new ArrayList<BlackboardPublishDTO>();
        for(CtBlackboardPublish ctBlackboardPublish : ctBlackboardPublishList){
            BlackboardPublishDTO blackboardPublishDTO = new BlackboardPublishDTO();
            BeanUtils.copyProperties(ctBlackboardPublish, blackboardPublishDTO);
            blackboardPublishDTOList.add(blackboardPublishDTO);
        }
        return blackboardPublishDTOList;
    }

    /**
     * 板书entity转dto
     * @param ctBlackboardPublish
     * @return
     */
    public static BlackboardPublishDTO entityBlackboardPublishToDto(CtBlackboardPublish ctBlackboardPublish){
        BlackboardPublishDTO blackboardPublishDTO = new BlackboardPublishDTO();
        BeanUtils.copyProperties(ctBlackboardPublish, blackboardPublishDTO);
        return blackboardPublishDTO;
    }

    public static CtBlackboardPublishFiles entityBlackboardPublishFilesFromDto(
            BlackboardPublishFilesDTO blackboardPublishFilesDTO){
        CtBlackboardPublishFiles files = new CtBlackboardPublishFiles();
        blackboardPublishFilesDTO.setFileId(UUID.randomUUID().toString().replace("-", ""));
        blackboardPublishFilesDTO.setUploadTime(new Date());
        blackboardPublishFilesDTO.setStatus(1);
        blackboardPublishFilesDTO.setBlackboardId("");
        BeanUtils.copyProperties(blackboardPublishFilesDTO, files);
        return files;
    }

    public  static CtBlackboardPublishStudent getCtBlackboardPublishStudent(String userId, String blackboardId,String schoolId){
        CtBlackboardPublishStudent ctBlackboardPublishStudent = new CtBlackboardPublishStudent();
        ctBlackboardPublishStudent.setBlackboardId(blackboardId);
        ctBlackboardPublishStudent.setUserId(userId);
        ctBlackboardPublishStudent.setSchoolId(schoolId);
        ctBlackboardPublishStudent.setReadStatus(1);
        ctBlackboardPublishStudent.setReadTime(new Date());
        return ctBlackboardPublishStudent;
    }

    public static List<String>getClassList(List<BlackboardPublishClassDTO> blackboardPublishClassDTOList){
        List<String> list = new ArrayList<String>();
        for(BlackboardPublishClassDTO dto : blackboardPublishClassDTOList){
            String classId = dto.getClassId();
            list.add(classId);
        }
        return list;
    }

    public static List<BlackboardPublishFilesDTO>getFileList(List<CtBlackboardPublishFiles> filesList){
        List<BlackboardPublishFilesDTO> filesDTOList = new ArrayList<BlackboardPublishFilesDTO>();
        for(CtBlackboardPublishFiles files : filesList){
            BlackboardPublishFilesDTO filesDTO = new BlackboardPublishFilesDTO();
            BeanUtils.copyProperties(files, filesDTO);
            filesDTOList.add(filesDTO);
        }
        return filesDTOList;
    }


    public static CtWorkStatistics getCtWorkStatistics(String  workId, int subStudentNum,String schoolId){
        CtWorkStatistics ctWorkStatistics = new CtWorkStatistics();
        ctWorkStatistics.setWorkId(workId);
        ctWorkStatistics.setVersion(1);
        ctWorkStatistics.setSchoolId(schoolId);
        ctWorkStatistics.setWorkSubStudentNum(subStudentNum);
        ctWorkStatistics.setWorkViewStudentNum(0);
        ctWorkStatistics.setWorkCorrectStudentNum(0);
        return ctWorkStatistics;
    }

    public static CtWorkGather getCtWorkGather(String workClassJson, int studentSum ,int classNum, String  workId,int workItemNum,
                                               int workType,String schoolId, String files,String userId,
                                               UnitBookNameView unitBookNameView,String content,Date publishTime,Date endTime){
        CtWorkGather ctWorkGather = new CtWorkGather();
        ctWorkGather.setWorkId(workId);
        ctWorkGather.setUserId(userId);
        ctWorkGather.setUnitName(unitBookNameView!=null?unitBookNameView.getUnitName():"");
        ctWorkGather.setSubjectName(unitBookNameView!=null?unitBookNameView.getBookSubjectName():"");
        ctWorkGather.setUnitId(unitBookNameView!=null?unitBookNameView.getUnitId():"");
        ctWorkGather.setSubjectId(unitBookNameView!=null?unitBookNameView.getBookSubjectId():"");
        ctWorkGather.setContext(content);
        ctWorkGather.setFiles(files);
        ctWorkGather.setPublishTime(publishTime);
        ctWorkGather.setWorkType(workType);
        ctWorkGather.setWorkItemNum(workItemNum);
        ctWorkGather.setWorkClassNum(classNum);
        ctWorkGather.setWorkClassJson(workClassJson);
        ctWorkGather.setWorkStudentNum(studentSum);
        ctWorkGather.setSchoolId(schoolId);
        ctWorkGather.setStatus(1);
        ctWorkGather.setEndTime(endTime);
        return ctWorkGather;
    }

    public static List<CtWorkClassGather> getWorkClassGather(List<String> classIdList,String workId,String schoolId){
        List<CtWorkClassGather> classGatherList =  new ArrayList<CtWorkClassGather>();
        for(String classId : classIdList){
            CtWorkClassGather classGather = new CtWorkClassGather();
            classGather.setWorkClassId(UUID.randomUUID().toString().replace("-", ""));
            classGather.setClassId(classId);
            classGather.setSchoolId(schoolId);
            classGather.setStatus(1);
            classGather.setWorkId(workId);
            classGatherList.add(classGather);
        }
        return classGatherList;
    }

    public static List<FileInfoView> getFileInfoViewList (List<BlackboardPublishFilesDTO> filesList){
        List<FileInfoView> fileInfoViewList = new ArrayList<FileInfoView>();
        for(BlackboardPublishFilesDTO files : filesList){
            FileInfoView infoView  = new FileInfoView();
            infoView.setId(files.getFileId());
            infoView.setExt(files.getExtension());
            infoView.setName(StringUtil.fileNameSubstring(files.getFileName(), Constants.MAX_FILE_NAME_LENGTH));
            infoView.setUrl(files.getUrl());
            infoView.setType(6);
            fileInfoViewList.add(infoView);
        }
        return fileInfoViewList;
    }

    public static List<CtWorkStudentGather> getStudentGatherList(List<CtBlackboardPublishStudent> studentList,String workId){
        List<CtWorkStudentGather> studentGatherList = new ArrayList<CtWorkStudentGather>();
        for(CtBlackboardPublishStudent student : studentList){

            CtWorkStudentGather studentGather = new CtWorkStudentGather();
            studentGather.setSchoolId(student.getSchoolId());
            studentGather.setSubStatus(0);
            studentGather.setCorrectStatus(0);
            studentGather.setWorkId(workId);
            studentGather.setClassId(student.getClassId());
            studentGather.setStatus(1);
            studentGather.setWorkStudentId(UUID.randomUUID().toString().replace("-", ""));
            studentGather.setStudentId(student.getUserId());
            studentGatherList.add(studentGather);
        }
        return studentGatherList;
    }

    public static List<String> getStudentIdList(List<CtBlackboardPublishStudent> studentList){
        List<String> studentIdList = new ArrayList<String>();
        for(CtBlackboardPublishStudent student : studentList){
            studentIdList.add(student.getUserId());
        }
        return studentIdList;
    }

}
