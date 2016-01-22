package net.xuele.cloudteach.service.util;

import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by cm.wang on 2015/7/17 0017.
 */
public class TranslateCourseReappear {

    /**
     * 获得未存在的课件发布表
     * @param courseReappearDTO
     * @return
     */
    public  static CtCourseReappear getCtCourseReappear(CourseReappearDTO courseReappearDTO){
        CtCourseReappear ctCourseReappear = new CtCourseReappear();
        if(courseReappearDTO != null){
            courseReappearDTO.setStatus(1);
            courseReappearDTO.setPublishTime(new Date());
            courseReappearDTO.setReappearId(UUID.randomUUID().toString().replace("-", ""));
            BeanUtils.copyProperties(courseReappearDTO, ctCourseReappear);
        }
        return ctCourseReappear;
    }

    /**
     * 获得已存在的课件发布表
     * @param courseReappearDTO
     * @return
     */
    public  static CtCourseReappear getExistCtCourseReappear(CourseReappearDTO courseReappearDTO){
        CtCourseReappear ctCourseReappear = new CtCourseReappear();
        if(courseReappearDTO != null){
            courseReappearDTO.setStatus(1);
            courseReappearDTO.setPublishTime(new Date());
            BeanUtils.copyProperties(courseReappearDTO, ctCourseReappear);
        }
        return ctCourseReappear;
    }

    /**
     * 课件发布课件
     * @param courseReappearCoursewaresDTOList
     * @param reappearId
     * @return
     */
    public static List<CtCourseReappearCoursewares> getCtCourseReappearCoursewares(
            List<CourseReappearCoursewaresDTO> courseReappearCoursewaresDTOList,String reappearId,String schoolId){
        List<CtCourseReappearCoursewares> courseReappearCoursewaresList = new ArrayList<CtCourseReappearCoursewares>();
        if(courseReappearCoursewaresDTOList != null){
            for(CourseReappearCoursewaresDTO dto : courseReappearCoursewaresDTOList){
                CtCourseReappearCoursewares coursewares = new CtCourseReappearCoursewares();
                dto.setReappearId(reappearId);
                dto.setSchoolId(schoolId);
                dto.setStatus(1);
                dto.setRcId(UUID.randomUUID().toString().replace("-", ""));
                BeanUtils.copyProperties(dto, coursewares);
                courseReappearCoursewaresList.add(coursewares);
            }
        }
        return courseReappearCoursewaresList;
    }

    /**
     * 获得授课课件IdList
     * @param courseReappearCoursewaresList
     * @return
     */
    public static List<String> getCoursewaresIdList(List<CtCourseReappearCoursewares> courseReappearCoursewaresList){
        List<String> coursewaresIdList = new ArrayList<String>();
        if(courseReappearCoursewaresList != null){
            for(CtCourseReappearCoursewares coursewares : courseReappearCoursewaresList){
                String coursewaresId = coursewares.getCoursewaresId();
                coursewaresIdList.add(coursewaresId);
            }
        }
        return coursewaresIdList;
    }

    public static List<CourseReappearDTO> entityCourseReappearListToDtoList(List<CtCourseReappear> ctCourseReappearList){
        List<CourseReappearDTO> courseReappearDTOList = new ArrayList<CourseReappearDTO>();
        if(ctCourseReappearList != null){
            for(CtCourseReappear courseReappear : ctCourseReappearList){
                CourseReappearDTO dto = new CourseReappearDTO();
                BeanUtils.copyProperties(courseReappear, dto);
                courseReappearDTOList.add(dto);
            }
        }
        return courseReappearDTOList;
    }

    public  static  List<CourseReappearCoursewaresDTO> entityCourseReappearCoursewaresListToDtoList(
            List<CtCourseReappearCoursewares> courseReappearCoursewaresList){
        List<CourseReappearCoursewaresDTO> coursewaresDTOList = new ArrayList<CourseReappearCoursewaresDTO>();
        if(courseReappearCoursewaresList != null){
            for(CtCourseReappearCoursewares coursewares: courseReappearCoursewaresList){
                CourseReappearCoursewaresDTO dto = new CourseReappearCoursewaresDTO();
                BeanUtils.copyProperties(coursewares, dto);
                coursewaresDTOList.add(dto);
            }
        }
        return coursewaresDTOList;
    }

    public  static TeachCoursewaresContentDTO entityTeachCoursewaresContentToDto(CtTeachCoursewaresContent coursewaresContent){
        TeachCoursewaresContentDTO dto = new TeachCoursewaresContentDTO();
        if(coursewaresContent != null)
            BeanUtils.copyProperties(coursewaresContent, dto);
        return  dto;
    }

    public static CtCourseReappearStudent getCourseReappearStudent(String userId){
        CtCourseReappearStudent ctCourseReappearStudent = new CtCourseReappearStudent();
        ctCourseReappearStudent.setStatus(1);
        ctCourseReappearStudent.setReadStatus(1);
        ctCourseReappearStudent.setReadTime(new Date());
        ctCourseReappearStudent.setUserId(userId);
        return ctCourseReappearStudent;
    }

    public  static  List<TeachCoursewaresDTO> entityCtTeachCoursewaresToDto(List<CtTeachCoursewares> teachCoursewaresList){
        List<TeachCoursewaresDTO> dtoList  = new ArrayList<TeachCoursewaresDTO>();
        for(CtTeachCoursewares coursewares : teachCoursewaresList){
            TeachCoursewaresDTO dto = new TeachCoursewaresDTO();
            BeanUtils.copyProperties(coursewares, dto);
            dtoList.add(dto);
        }
        return dtoList;
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

    public static List<String> getRcIdList(List<CtCourseReappearCoursewares> coursewaresList){
        List<String> rcIdList = new ArrayList<String>();
        for(CtCourseReappearCoursewares coursewares : coursewaresList){
            rcIdList.add(coursewares.getRcId());
        }
        return rcIdList;
    }

    public static List<CtTeachCoursewaresContent> getContent(List<CtTeachCoursewaresContent> coursewaresContentList,int belongType,
                                                             List<String> rcIdList){
        List<CtTeachCoursewaresContent> contentList = new ArrayList<CtTeachCoursewaresContent>();
        for(int i =0;i<coursewaresContentList.size();i++){
            CtTeachCoursewaresContent content = new CtTeachCoursewaresContent();
            content = coursewaresContentList.get(i);
            String rcId = rcIdList.get(i);
            content.setCoursewaresId(rcId);
            content.setBelongType(belongType);
            contentList.add(content);
        }
        return contentList;
    }

    public static List<CtWorkStudentGather> getStudentGatherList(List<CtCourseReappearStudent> studentList,String workId){
        List<CtWorkStudentGather> studentGatherList = new ArrayList<CtWorkStudentGather>();
        for(CtCourseReappearStudent student : studentList){
            CtWorkStudentGather studentGather = new CtWorkStudentGather();
            studentGather.setStudentId(student.getUserId());
            studentGather.setStatus(1);
            studentGather.setWorkStudentId(UUID.randomUUID().toString().replace("-", ""));
            studentGather.setClassId(student.getClassId());
            studentGather.setCorrectStatus(0);
            studentGather.setSubStatus(0);
            studentGather.setSchoolId(student.getSchoolId());
            studentGather.setWorkId(workId);
            studentGatherList.add(studentGather);
        }
        return studentGatherList;
    }

    public static List<String> getStudentIdList(List<CtCourseReappearStudent> studentList){
        List<String> studentIdList = new ArrayList<String>();
        for(CtCourseReappearStudent student : studentList){
            studentIdList.add(student.getUserId());
        }
        return studentIdList;
    }

}
