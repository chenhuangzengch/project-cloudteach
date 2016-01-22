package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.CtBook;
import net.xuele.cloudteach.domain.CtTeacherWorkItem;
import net.xuele.cloudteach.domain.CtUnits;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.BookDTO;
import net.xuele.cloudteach.persist.CtBankItemFilesMapper;
import net.xuele.cloudteach.persist.CtBookMapper;
import net.xuele.cloudteach.persist.CtTeacherWorkItemMapper;
import net.xuele.cloudteach.persist.CtUnitsMapper;
import net.xuele.cloudteach.service.CloudTeachRedisService;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.util.StringUtil;
import net.xuele.cloudteach.view.AttachmentInfoView;
import net.xuele.cloudteach.view.SchoolSynBookView;
import net.xuele.cloudteach.view.SynUnitView;
import net.xuele.common.dto.ClassInfoDTO;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.member.dto.*;
import net.xuele.member.service.*;
import net.xuele.notify.constant.ReceiverTypeEnum;
import net.xuele.notify.dto.MessageInputDTO;
import net.xuele.notify.dto.PersonDTO;
import net.xuele.notify.service.NotifyService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by panglx on 2015/7/3 0003.
 */
@Service
public class CloudTeachServiceImpl implements CloudTeachService {

    private static Logger logger = LoggerFactory.getLogger(SynclassWorkServiceImpl.class);

    @Autowired
    private CtUnitsMapper ctUnitsMapper;

    @Autowired
    private CtBookMapper ctBookMapper;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    NotifyService notifyService;

    @Autowired
    CtBankItemFilesMapper ctBankItemFilesMapper;

    @Autowired
    StudentService studentService;

    @Autowired
    CloudTeachRedisService cloudTeachRedisService;

    @Autowired
    SchoolPeriodService schoolPeriodService;

    @Autowired
    FamilyRelationService familyRelationService;

    @Autowired
    CtTeacherWorkItemMapper ctTeacherWorkItemMapper;

    @Autowired
    ClassService classService;

    /**
     * 查询课程目录
     *
     * @param bookId
     * @return
     */
    @Override
    public List<UnitsDTO> selectUnits(String bookId) {
        List<CtUnits> ctUnits = ctUnitsMapper.selectByBookid(bookId);
        //int rows = ctUnitsMapper.selectCount(bookId);
        List<UnitsDTO> list = entityListToDtoList(ctUnits);
        //int rows = list.size();


        return list;
    }

    /**
     * 获取课程树
     *
     * @param bookId
     * @return
     */
    public List<UnitRecordDTO> selectUnitTree(String bookId) {
        List<CtUnits> ctUnits = ctUnitsMapper.selectByBookid(bookId);
        if (ctUnits == null) {//查找的对象不存在-没有课程
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        //int rows = ctUnitsMapper.selectCount(bookId);
        List<UnitsDTO> list = entityListToDtoList(ctUnits);
        int rows = list.size();

        List<UnitRecordDTO> unitTrees = new ArrayList<UnitRecordDTO>();
        UnitRecordDTO unitsRecord = null;//单元
        List<UnitsDTO> unitsDTOs = new ArrayList<UnitsDTO>();//课程
        for (UnitsDTO r : list) {
            if (r.getUnitType() == 1) {
                // 上一个unitsRecord插入到list
                if (unitsRecord != null) {
                    unitsRecord.setLessons(unitsDTOs);
                    unitTrees.add(unitsRecord);
                }
                // 更新当前unitsRecord
                unitsRecord = new UnitRecordDTO();
                unitsDTOs = new ArrayList<UnitsDTO>();
                unitsRecord.setUnit(r);
            } else {
                unitsDTOs.add(r);
            }
        }
        unitsRecord.setLessons(unitsDTOs);
        unitTrees.add(unitsRecord);
        return unitTrees;
    }

    /**
     * 根据教师用户号获取所有授课课本
     *
     * @param userId
     * @return
     */
    @Override
    public List<CtBookDTO> selectTeacherBookList(String userId) {
        return teacherService.queryBookDTO(userId);
    }

    /**
     * 根据教师用户号获取所有授课班级
     *
     * @param userId
     * @return
     */
    @Override
    public List<ClassInfoDTO> selectTeacherClassList(String userId) {
        List<ClassInfoDTO> teachercClassList = teacherService.queryTeacherClass(userId);
        return teachercClassList;
    }

    /**
     * 学校管理员获取全部班级列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<ClassInfoDTO> queryAllClassList(String userId, String schoolId) {
        ClassDTO classDTO = new ClassDTO();
        classDTO.setSchoolId(schoolId);
        List<ClassInfoDTO> allClassList = new ArrayList<>();
        List<ClassInfoDTO> allClassList1 = classService.queryAllClass(userId);
        List<ClassStudentDTO> classList = classService.queryClasses(classDTO);

        for (ClassStudentDTO c1 : classList) {
            for (ClassInfoDTO c2 : allClassList1) {
                if (c1.getClassId().equals(c2.getClassId())) {
                    ClassInfoDTO c3 = new ClassInfoDTO();
                    BeanUtils.copyProperties(c2, c3);
                    c3.setStudentCount(c1.getStudentNumber());
                    allClassList.add(c3);
                }
            }
        }

//        for (ClassStudentDTO c1 : classList) {
//            ClassInfoDTO classInfoDTO = new ClassInfoDTO();
//            classInfoDTO.setClassId(c1.getClassId());
//            classInfoDTO.setClassName(c1.getName());
//            classInfoDTO.setAliasName(c1.getAliasName());
//            classInfoDTO.setGradeName(c1.getGradeName());
//            classInfoDTO.setStudentCount(c1.getStudentNumber());
//            classInfoDTO.setChargeId(c1.getChargeId());
//            classInfoDTO.setChargeName(c1.getChargeName());
//            classInfoDTO.setYear(c1.getYear());
//            classInfoDTO.setmImage(c1.getmImage());
//            classInfoDTO.setSchoolId(schoolId);
//            allClassList.add(classInfoDTO);
//        }

        return allClassList;
    }

    /**
     * 根据教师用户号获取某个年级所有授课班级
     *
     * @param userId   教师用户ID
     * @param gradeNum 年级
     * @return
     */
    @Override
    public List<ClassInfoDTO> selectTeacherGradeClassList(String userId, int gradeNum) {
        List<ClassInfoDTO> teachercClassList = teacherService.queryTeacherClass(userId);
        List<ClassInfoDTO> resClassList = new ArrayList<>();
        for (ClassInfoDTO classObj : teachercClassList) {
            if (classObj.getGradeNum() == gradeNum) {
                resClassList.add(classObj);
            }
        }
        return resClassList;
    }


    /**
     * 根据教师用户号获取所有授课科目
     *
     * @param userId
     * @return
     */
    @Override
    public List<SummaryDTO> selectTeacherSubjectList(String userId) {
        List<CtBookDTO> booklist = teacherService.queryBookDTO(userId);
        Map subjectmap = new HashMap<String, String>();
        List<SummaryDTO> summaryList = new ArrayList<>();

        for (CtBookDTO book : booklist) {
            if (subjectmap.get(book.getSubjectId()) != null) {
                subjectmap.put(book.getSubjectId(), book.getSubjectName());

                SummaryDTO summary = new SummaryDTO();
                summary.setSummaryCode(book.getSubjectId());
                summary.setSummaryName(book.getSubjectName());
                summaryList.add(summary);
            }
        }

        return summaryList;
    }

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
    @Override
    public void sendWarnSubNotify(List<String> studentlist, String title, String content, String userId, String userIcon) {
        logger.info("发送提醒交作业通知");
        if (studentlist != null && studentlist.size() > 0) {
            MessageInputDTO messageInputDTO = new MessageInputDTO();

            List<PersonDTO> personDTOList = new ArrayList<>();
            //通知接收人
            for (String studentUserId : studentlist) {
                PersonDTO personDTO = new PersonDTO();
                personDTO.setId(studentUserId);
                personDTO.setType(ReceiverTypeEnum.STUDENT);
                personDTOList.add(personDTO);
            }
            //设置通知内容
            messageInputDTO.setTitle(title);//通知标题
            messageInputDTO.setContent(content);//通知内容
            messageInputDTO.setContentType(4);//通知类型
            int randomnumber = new Random().nextInt(999999);
            messageInputDTO.setSenderId(Integer.toString(randomnumber));//发送人:随机数，防止被过滤
            messageInputDTO.setSendType(0);//发送类型
            //messageInputDTO.setIconUrl(userIcon);//发送人头像
            messageInputDTO.setPersonList(personDTOList);//接收人

            notifyService.sendMessage(messageInputDTO);
        }
    }

    @Override
    public List<StudentManagerDTO> selectClassStudentList(String classId) {
        return studentService.queryClassStudents(classId);
    }

    /**
     * 根据学校ID获取学校对应所有的同步课堂课本信息
     *
     * @param schoolId 学校ID
     * @return
     */
    @Override
    public List<SchoolSynBookViewDTO> getSchoolSynBookList(String schoolId) {
        List<SchoolSynBookView> schoolSynBookList = ctUnitsMapper.querySchoolSynBookListBySchoolId(schoolId);
        if (schoolSynBookList == null) {
            return null;
        }
        List<SchoolSynBookViewDTO> schoolSynBookDTOList = new ArrayList<>();
        for (SchoolSynBookView domainObj : schoolSynBookList) {
            SchoolSynBookViewDTO dtoObj = new SchoolSynBookViewDTO();
            BeanUtils.copyProperties(domainObj, dtoObj);
            schoolSynBookDTOList.add(dtoObj);
        }
        return schoolSynBookDTOList;
    }

    /**
     * 根据课本ID获取课程信息
     *
     * @param bookId 课本ID
     * @return
     */
    @Override
    public List<SynUnitViewDTO> getSynUnitByBookId(String bookId) {
        List<SynUnitView> synUnitList = ctUnitsMapper.querySynUnitListByBookId(bookId);
        if (synUnitList == null) {
            return null;
        }
        List<SynUnitViewDTO> synUnitDTOList = new ArrayList<>();
        for (SynUnitView domainObj : synUnitList) {
            SynUnitViewDTO dtoObj = new SynUnitViewDTO();
            BeanUtils.copyProperties(domainObj, dtoObj);
            synUnitDTOList.add(dtoObj);
        }
        return synUnitDTOList;
    }

    /**
     * 获取教师对应同步课堂课本信息
     *
     * @param userId   用户ID
     * @param schoolId 学校ID
     * @return
     */
    @Override
    public List<SchoolSynBookViewDTO> getUserSchoolSynBookList(String userId, String schoolId) {
        //获取教师所在学校的所有同步课堂课本信息
        List<SchoolSynBookViewDTO> schoolSynBookList = getSchoolSynBookList(schoolId);
        if (schoolSynBookList == null) {
            return null;
        }
        //获取教师授课课本信息
        List<CtBookDTO> ctBookList = selectTeacherBookList(userId);
        if (ctBookList == null) {
            return null;
        }
        List<SchoolSynBookViewDTO> userSynBookList = new ArrayList<>();
        for (SchoolSynBookViewDTO synBookObj : schoolSynBookList) {
            for (CtBookDTO ctBookObj : ctBookList) {
                //如果科目、年级、学期一样则此同步课堂课本属于该教师
                if (synBookObj.getSubjectId().intValue() == Integer.parseInt(ctBookObj.getSubjectId())
                        && synBookObj.getGradeNum().intValue() == ctBookObj.getGrade().intValue()
                        && synBookObj.getSemester().intValue() == ctBookObj.getSemester().intValue()) {
                    userSynBookList.add(synBookObj);
                }
            }
        }
        return userSynBookList;
    }

    /**
     * 根据bookid取 课本信息
     *
     * @param bookId
     * @return
     */
    @Override
    public BookDTO selectEditionAndSubject(String bookId) {

       /*EditionAndSubjectView editionAndSubjectView = ctBookMapper.selectByBookId(bookId);
        EditionAndSubjectDTO editionAndSubjectDTO = new EditionAndSubjectDTO();
        BeanUtils.copyProperties(editionAndSubjectView,editionAndSubjectDTO);
        return editionAndSubjectDTO;*/
        CtBook book = ctBookMapper.selectByPrimaryKey(bookId);
        BookDTO bookDTO = new BookDTO();
        if (book == null) {
            return null;
        }
        BeanUtils.copyProperties(book, bookDTO);
        return bookDTO;
    }

    @Override
    public String AttachmentInfoJson(String itemId, int fileType, String schoolId) {

        String attachmentJson = null;
        ObjectMapper objectMapper = new ObjectMapper();

        List<AttachmentInfoView> attachmentInfoViewList = ctBankItemFilesMapper.attachmentInfoForJson(itemId, fileType, schoolId);

        try {
            attachmentJson = objectMapper.writeValueAsString(attachmentInfoViewList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return attachmentJson;

    }

    /**
     * domain数组对象转为DTO数组对象
     *
     * @param unitsList
     * @return
     */
    private List<UnitsDTO> entityListToDtoList(List<CtUnits> unitsList) {

        List<UnitsDTO> unitsDTOList = new ArrayList<UnitsDTO>();
        for (CtUnits mRes : unitsList) {
            UnitsDTO unitsDTO = new UnitsDTO();
            BeanUtils.copyProperties(mRes, unitsDTO);
            unitsDTOList.add(unitsDTO);
        }
        return unitsDTOList;
    }

    /**
     * 根据课程id获取课程信息
     *
     * @param unitId
     * @return
     */
    public UnitsDTO getUnitInfo(String unitId) {
        CtUnits units = ctUnitsMapper.selectByPrimaryKey(unitId);
        UnitsDTO unitsDTO = new UnitsDTO();
        if (units == null) {
            return null;
        }
        BeanUtils.copyProperties(units, unitsDTO);
        return unitsDTO;
    }

    /**
     * 根据班级信息获取年级信息
     *
     * @param teachClassList
     * @return
     */
    @Override
    public List<Integer> collectGradeFromClassList(List<ClassInfoDTO> teachClassList) {
        if (teachClassList == null || teachClassList.size() < 1) {
            return null;
        }
        Set<Integer> gradeSet = new HashSet();
        for (ClassInfoDTO classDTO : teachClassList) {
            gradeSet.add(new Integer(classDTO.getGradeNum()));
        }

        Integer[] gradeArr = new Integer[gradeSet.size()];
        gradeSet.toArray(gradeArr);
        List<Integer> gradeList = new ArrayList();

        for (int i = 0; i < gradeArr.length - 1; i++) {
            for (int j = i + 1; j < gradeArr.length; j++) {
                int temp;
                if (gradeArr[i].intValue() > gradeArr[j].intValue()) {
                    temp = gradeArr[j];
                    gradeArr[j] = gradeArr[i];
                    gradeArr[i] = temp;
                }
            }
        }
        gradeList = Arrays.asList(gradeArr);
        return gradeList;
    }

    /**
     * @param userId   教师ID
     * @param unitId   课程ID
     * @param unitType 课程类型 1云教学  2同步课堂
     *                 将教师当前选中的课程设置到redis中
     */
    public void setTeacherCurrentUnit(String userId, String unitId, int unitType) throws CloudteachException {
        if (unitType == 1) {
            String ctUnitId = cloudTeachRedisService.get("CtTeacherUnitId:" + userId);
            if (unitId != null && !unitId.equals(ctUnitId)) {
                cloudTeachRedisService.set("CtTeacherUnitId:" + userId, unitId);
            }
        } else if (unitType == 2) {
            String scUnitId = cloudTeachRedisService.get("ScTeacherUnitId:" + userId);
            if (unitId != null && !unitId.equals(scUnitId)) {
                cloudTeachRedisService.set("ScTeacherUnitId:" + userId, unitId);
            }
        }
    }

    /**
     * 对学生提交的提分宝作业进行自动评分
     *
     * @param magicWorkChallengeDTO 挑战记录
     * @param challengeTimes        历史挑战次数
     * @return
     */
    @Override
    public MagicWorkChallengeDTO magicWorkAutoScore(MagicWorkChallengeDTO magicWorkChallengeDTO, int challengeTimes) {
        int score = 0;
        String scoreContext = "";

        //redis获取系统平均用时
        String totalAvTimeStr = cloudTeachRedisService.get("CloudTeach:Magic:TotalAvTime:" + magicWorkChallengeDTO.getBankId());
        //String totalAvTimeStr = "1000";
        //redis获取累计挑战次数
        String totalTimesStr = cloudTeachRedisService.get("CloudTeach:Magic:TotalTimes:" + magicWorkChallengeDTO.getBankId());
        //String totalTimesStr = "10";
        if (StringUtils.isEmpty(totalAvTimeStr)) {
            totalAvTimeStr = "0";
        }
        if (StringUtils.isEmpty(totalTimesStr)) {
            totalTimesStr = "0";
        }
        //系统平均用时（秒）
        int totalAvTime = Integer.parseInt(totalAvTimeStr);
        //系统累计挑战次数
        int totalTimes = Integer.parseInt(totalTimesStr);
        //本次挑战用时
        int thisTime = (int) ((magicWorkChallengeDTO.getEndTime().getTime() - magicWorkChallengeDTO.getBeginTime().getTime()) / 1000);

        //新的系统平均用时
        int newTotalAvtime = (int) (totalAvTime * totalTimes + thisTime) / (totalTimes + 1);
        //新的累计挑战次数
        int newTotalTimes = totalTimes + 1;

        cloudTeachRedisService.set("CloudTeach:Magic:TotalTimes:" + magicWorkChallengeDTO.getBankId(), Integer.toString(newTotalTimes));
        cloudTeachRedisService.set("CloudTeach:Magic:TotalAvTime:" + magicWorkChallengeDTO.getBankId(), Integer.toString(newTotalAvtime));


        //时间权数
        float wt = 1f * totalAvTime / thisTime;
        if (wt > 1) {
            wt = 1f;
        }
        //次数权数
        float wb = 1;
        if (challengeTimes == 0) {
            //本次是第一次挑战
            wb = 0.5f;
        } else if (challengeTimes == 1) {
            //本次是第二次挑战
            wb = 0.8f;
        }
        //正确率
        float aRate = 0f;
        if (magicWorkChallengeDTO.getTotalQuenum().intValue() > 0) {
            aRate = 1f * magicWorkChallengeDTO.getRightQuenum().intValue() / magicWorkChallengeDTO.getTotalQuenum().intValue();
        }

        float wa = aRate * wt * wb;
        //计算评分
        if (wa > 0.9f && wa <= 1f) {
            score = 10;
        } else if (wa > 0.8f && wa <= 0.9f) {
            score = 9;
        } else if (wa > 0.7f && wa <= 0.8f) {
            score = 8;
        } else if (wa > 0.6f && wa <= 0.7f) {
            score = 7;
        } else if (wa > 0.5f && wa <= 0.6f) {
            score = 6;
        } else {
            score = 5;
        }
        //获取成绩描述
        switch (score) {
            case 10:
                scoreContext = Constants.MAGIC_WORK_SCORE_CONTEXT10;
                break;
            case 9:
                scoreContext = Constants.MAGIC_WORK_SCORE_CONTEXT9;
                break;
            case 8:
                scoreContext = Constants.MAGIC_WORK_SCORE_CONTEXT8;
                break;
            case 7:
                scoreContext = Constants.MAGIC_WORK_SCORE_CONTEXT7;
                break;
            case 6:
                scoreContext = Constants.MAGIC_WORK_SCORE_CONTEXT6;
                break;
            case 5:
                scoreContext = Constants.MAGIC_WORK_SCORE_CONTEXT5;
                break;
            default:
                scoreContext = Constants.MAGIC_WORK_SCORE_CONTEXT5;
        }
        magicWorkChallengeDTO.setScore(score);
        magicWorkChallengeDTO.setScorecontext(scoreContext);
        return magicWorkChallengeDTO;
    }


    /**
     * 学生提交作业时前端json格式附件转附件dto
     *
     * @param magicWorkAnswerFilesJSON
     * @return
     */
    public List<MagicWorkAnswerFilesDTO> getFileDtoFromJSOM(String magicWorkAnswerFilesJSON) {
        if (StringUtils.isEmpty(magicWorkAnswerFilesJSON) || magicWorkAnswerFilesJSON == null) {
            return null;
        }
        List<MagicWorkAnswerFilesDTO> magicWorkAnswerFilesDTOs = new ArrayList<>();//学生回答附件
        MagicWorkAnswerFilesDTO filesDTO;
        MagicWorkAnswerFilesFormDTO filesFormDTO;
        List<MagicWorkAnswerFilesFormDTO> filesJSONList = JSON.parseArray(magicWorkAnswerFilesJSON, MagicWorkAnswerFilesFormDTO.class);
        if (filesJSONList.isEmpty() || filesJSONList == null) {
            return null;
        }
        for (int i = 0; i < filesJSONList.size(); i++) {
            filesFormDTO = filesJSONList.get(i);
            filesDTO = new MagicWorkAnswerFilesDTO();
            filesDTO.setUrl(filesFormDTO.getUri());//HDFS文件url
            //文件名--截取相应长度
            filesDTO.setFileName(StringUtil.fileNameSubstring(filesFormDTO.getFileName(), Constants.MAX_FILE_NAME_LENGTH));
            filesDTO.setExtension(filesFormDTO.getExtension());//扩展名
            filesDTO.setSize(filesFormDTO.getSize());//文件大小
            filesDTO.setFileType(Integer.parseInt(filesFormDTO.getType()));//文件类型
            magicWorkAnswerFilesDTOs.add(filesDTO);
        }
        return magicWorkAnswerFilesDTOs;
    }


    /**
     * 判断学生提交的附件是否符合提交方式
     *
     * @param magicWorkAnswerFilesDTOs
     * @param mSubImage
     * @param mSubTape
     * @param mSubVideo
     * @return
     */
    public Boolean isRightSubFile(List<MagicWorkAnswerFilesDTO> magicWorkAnswerFilesDTOs, int mSubImage, int mSubTape, int mSubVideo, int mSubOther) {
        int subImage = 0;//图片
        int subTape = 0;//声音
        int subVideo = 0;//视频

        /*if (CollectionUtils.isEmpty(magicWorkAnswerFilesDTOs) && mSubImage == 0 && mSubVideo == 0 && mSubTape == 0) {
            return true;
        } else if (mSubImage == 0 && mSubVideo == 0 && mSubTape == 0 && CollectionUtils.isNotEmpty(magicWorkAnswerFilesDTOs)) {
            //附件个数不对
            throw new CloudteachException(CloudTeachErrorEnum.TOMANYFILES.getMsg(),
                    CloudTeachErrorEnum.TOMANYFILES.getCode());
        }*/
        if (mSubImage + mSubTape + mSubVideo > 0 && CollectionUtils.isEmpty(magicWorkAnswerFilesDTOs)) {
            //提交方式不对
            throw new CloudteachException(CloudTeachErrorEnum.ERRORSUBMIT.getMsg(),
                    CloudTeachErrorEnum.ERRORSUBMIT.getCode());
        } else {
            if (CollectionUtils.isNotEmpty(magicWorkAnswerFilesDTOs)) {
                for (MagicWorkAnswerFilesDTO answerFilesDTO : magicWorkAnswerFilesDTOs) {
                    //扩展名类型(0其他,1ppt,2word,3视频,4录音,5图片)
                    //String extType = ExtensionTypeUtil.getInstance().getExtType(answerFilesDTO.getExtension());
                    int fileType = answerFilesDTO.getFileType();
                    //附件类型1图片，2录音，3视频
                    if (fileType == 3) {
                        //answerFilesDTO.setFileType(3);
                        subVideo = subVideo + 1;
                    } else if (fileType == 2) {
                        //answerFilesDTO.setFileType(2);
                        subTape = subTape + 1;
                    } else if (fileType == 1) {
                        //answerFilesDTO.setFileType(1);
                        subImage = subImage + 1;
                    }
                }
            }

        }
        //"判断学生提交的附件是否符合提交方式"
        if ((mSubVideo == 1 && subVideo > 1)
                || (mSubTape == 1 && subTape > 1)
                ) {
            //提交方式不对
            throw new CloudteachException(CloudTeachErrorEnum.ERRORSUBMIT.getMsg(),
                    CloudTeachErrorEnum.ERRORSUBMIT.getCode());
        } else if ((mSubImage == 1 && mSubVideo == 1 && mSubTape == 1 && subImage + subTape + subVideo > 9) ||
                (mSubOther == 1 && subImage + subTape + subVideo > 9)) {
            //附件个数不对
            throw new CloudteachException(CloudTeachErrorEnum.TOMANYFILES.getMsg(),
                    CloudTeachErrorEnum.TOMANYFILES.getCode());
        }
        return true;
    }

    /**
     * 验证学生提交教师作业上传附件是否符合提交方式
     *
     * @param stuSubmitFileList
     * @param workId
     * @return
     */
    @Override
    public boolean checkStuTeacherWorkSubFiles(List<TeacherWorkItemAnswerFileDTO> stuSubmitFileList, String workId, String schoolId) {

        boolean retValue = true;

        // 学生提交方式
        int stuSubImage = 0;
        int stuSubTape = 0;
        int stuSubVideo = 0;

        // 获取教师题目信息
        CtTeacherWorkItem ctTeacherWorkItem = ctTeacherWorkItemMapper.getTeacherWorkItemByWorkId(workId, schoolId);
        // 获取作业要求的提交方式
        int subImage = ctTeacherWorkItem.getSubImage();
        int subTape = ctTeacherWorkItem.getSubTape();
        int subVideo = ctTeacherWorkItem.getSubVideo();

        if (CollectionUtils.isEmpty(stuSubmitFileList) && subImage + subTape + subVideo > 0) {
            // 提交方式要求带附件，学生没提交附件
            retValue = false;
        }

        // 获取附件类型信息
        for (TeacherWorkItemAnswerFileDTO answerFilesDTO : stuSubmitFileList) {

            int fileType = answerFilesDTO.getFileType();
            //附件类型1图片，2录音，3视频
            if (fileType == 1) {
                //answerFilesDTO.setFileType(3);
                stuSubImage = stuSubImage + 1;
            } else if (fileType == 2) {
                //answerFilesDTO.setFileType(2);
                stuSubTape = stuSubTape + 1;
            } else if (fileType == 3) {
                //answerFilesDTO.setFileType(1);
                stuSubVideo = stuSubVideo + 1;
            }
        }

        // 验证附件匹配
        if (subImage == 1 && stuSubImage == 0 || subTape == 1 && stuSubTape == 0 || subVideo == 1 && stuSubVideo == 0) {
            // 提交方式不匹配
            retValue = false;
        }
        // 录音附件和视频附件只能各自添加1个
        if (stuSubTape > 1 || stuSubVideo > 1) {
            // 录音或者视频超过1个
            retValue = false;
        }
        // 附件总数最多9个
        if (stuSubImage + stuSubTape + stuSubVideo > 9) {
            // 附件个数不对
            retValue = false;
        }

        return retValue;
    }

    /**
     * 获取家长对应孩子列表
     *
     * @param userId
     * @return
     */
    public List<StudentDTO> getChildrenList(String userId) {
        return familyRelationService.queryKidInfo(userId);
    }

    /**
     * 根据单元ID获得对应的书本信息
     *
     * @param unitId
     * @return
     */
    @Override
    public BookDTO getBookByUnit(String unitId) {

        BookDTO book = null;

        if (unitId == null) {
            book = null;
        } else {
            CtUnits ctUnits = ctUnitsMapper.selectByPrimaryKey(unitId);
            if (ctUnits != null) {
                CtBook ctBook = ctBookMapper.selectByBookId(ctUnits.getBookId());
                if (ctBook != null) {
                    book = new BookDTO();
                    BeanUtils.copyProperties(ctBook, book);
                }
            }
        }
        return book;
    }
}
