package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.BookDTO;
import net.xuele.common.dto.ClassInfoDTO;
import net.xuele.member.dto.*;

import java.util.List;

/**
 * Created by panglx on 2015/7/3 0003.
 * 云教学公用部分service
 */
public interface CloudTeachService {
    /**
     * 获取课程目录
     *
     * @param bookId
     * @return
     */
    List<UnitsDTO> selectUnits(String bookId);

    /**
     * 根据bookid取 课本信息
     *
     * @param bookId
     * @return
     */
    BookDTO selectEditionAndSubject(String bookId);

    /**
     * 获取课程树
     *
     * @param bookId
     * @return
     */
    List<UnitRecordDTO> selectUnitTree(String bookId);

    /**
     * 根据教师用户号获取所有授课课本
     *
     * @param userId
     * @return
     */
    List<CtBookDTO> selectTeacherBookList(String userId);

    /**
     * 根据教师用户号获取所有授课班级
     *
     * @param userId
     * @return
     */
    List<ClassInfoDTO> selectTeacherClassList(String userId);

    /**
     * 学校管理员获取全部班级列表
     *
     * @param userId
     * @return
     */
    List<ClassInfoDTO> queryAllClassList(String userId, String schoolId);

    /**
     * 根据教师用户号获取某个年级所有授课班级
     *
     * @param userId   教师用户ID
     * @param gradeNum 年级
     * @return
     */
    List<ClassInfoDTO> selectTeacherGradeClassList(String userId, int gradeNum);

    /**
     * 根据教师用户号获取所有授课科目
     *
     * @param userId
     * @return
     */
    List<SummaryDTO> selectTeacherSubjectList(String userId);

    /**
     * 发送提醒交作业通知
     *
     * @param studentlist 通知对象—学生信息
     * @param title       通知标题
     * @param content     通知内容
     * @param userId      教师ID
     * @param userIcon    教师头像
     * @return
     */
    void sendWarnSubNotify(List<String> studentlist, String title, String content, String userId, String userIcon);

    /**
     * 根据班级号获取所有学生
     *
     * @param classId
     * @return
     */
    List<StudentManagerDTO> selectClassStudentList(String classId);

    /**
     * 根据学校ID获取学校对应所有的同步课堂课本信息
     *
     * @param schoolId 学校ID
     * @return
     */
    List<SchoolSynBookViewDTO> getSchoolSynBookList(String schoolId);

    /**
     * 根据课本ID获取课程信息
     *
     * @param bookId 课本ID
     * @return
     */
    List<SynUnitViewDTO> getSynUnitByBookId(String bookId);

    /**
     * 获取教师对应同步课堂课本信息
     *
     * @param userId   用户ID
     * @param schoolId 学校ID
     * @return
     */
    List<SchoolSynBookViewDTO> getUserSchoolSynBookList(String userId, String schoolId);

    /**
     * 将作业所带的附件封装成json格式
     *
     * @param itemId
     * @return
     */
    String AttachmentInfoJson(String itemId, int fileType, String schoolId);

    /**
     * 根据课程id获取课程信息
     *
     * @param unitId
     * @return
     */
    UnitsDTO getUnitInfo(String unitId);

    /**
     * 从班级信息列表中提取出所有年级信息
     *
     * @param teachClassList
     * @return
     */
    List<Integer> collectGradeFromClassList(List<ClassInfoDTO> teachClassList);

    /**
     * @param userId   教师ID
     * @param unitId   课程ID
     * @param unitType 课程类型 1云教学  2同步课堂
     *                 将教师当前选中的课程设置到redis中
     */
    void setTeacherCurrentUnit(String userId, String unitId, int unitType);

    /**
     * 对学生提交的提分宝作业进行自动评分
     *
     * @param magicWorkChallengeDTO 挑战记录
     * @param challengeTimes        历史挑战次数
     * @return
     */
    MagicWorkChallengeDTO magicWorkAutoScore(MagicWorkChallengeDTO magicWorkChallengeDTO, int challengeTimes);

    /**
     * 学生提交作业时前端json格式附件转附件dto
     *
     * @param magicWorkAnswerFilesJSON
     * @return
     */
    List<MagicWorkAnswerFilesDTO> getFileDtoFromJSOM(String magicWorkAnswerFilesJSON);

    /**
     * 判断学生提交的附件是否符合提交方式
     *
     * @param magicWorkAnswerFilesDTOs
     * @param mSubImage
     * @param mSubTape
     * @param mSubVideo
     * @return
     */
    Boolean isRightSubFile(List<MagicWorkAnswerFilesDTO> magicWorkAnswerFilesDTOs, int mSubImage, int mSubTape, int mSubVideo, int mSubOther);

    /**
     * 验证学生提交教师作业上传附件是否符合提交方式
     *
     * @param stuSubmitFileList
     * @param workId
     * @return
     */
    boolean checkStuTeacherWorkSubFiles(List<TeacherWorkItemAnswerFileDTO> stuSubmitFileList, String workId, String schoolId);

    /**
     * 获取家长对应孩子列表
     *
     * @param userId
     * @return
     */
    List<StudentDTO> getChildrenList(String userId);

    /**
     * 根据单元ID获得对应的书本信息
     *
     * @param unitId
     * @return
     */
    BookDTO getBookByUnit(String unitId);
}
