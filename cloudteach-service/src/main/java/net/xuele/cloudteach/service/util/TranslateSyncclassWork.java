package net.xuele.cloudteach.service.util;

import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by cm.wang on 2015/7/20 0020.
 */
public class TranslateSyncclassWork {

    public static SynclassWorkStudentDTO entitySynclassWorkStudentToDto(CtSynclassWorkStudent ctSynclassWorkStudent) {
        SynclassWorkStudentDTO studentDTO = new SynclassWorkStudentDTO();
        if (ctSynclassWorkStudent != null) {
            BeanUtils.copyProperties(ctSynclassWorkStudent, studentDTO);
        }
        return studentDTO;
    }

    public static List<SynclassWorkStudentDTO> entityStudentListToListDto(List<CtSynclassWorkStudent> studentList) {
        List<SynclassWorkStudentDTO> studentDTOList = new ArrayList<SynclassWorkStudentDTO>();
        if (studentList != null) {
            for (CtSynclassWorkStudent student : studentList) {
                studentDTOList.add(entitySynclassWorkStudentToDto(student));
            }
        }
        return studentDTOList;
    }

    public static List<SynclassWorkClassDTO> entityClassListToListDto(List<CtSynclassWorkClass> classList) {
        List<SynclassWorkClassDTO> classDTOList = new ArrayList<SynclassWorkClassDTO>();
        if (classList != null) {
            for (CtSynclassWorkClass workClass : classList) {
                SynclassWorkClassDTO classDTO = new SynclassWorkClassDTO();
                BeanUtils.copyProperties(workClass, classDTO);
                classDTOList.add(classDTO);
            }
        }
        return classDTOList;
    }

    public static List<SynclassWorkGameDTO> entityGameListToListDto(List<CtSynclassWorkGame> gameList) {
        List<SynclassWorkGameDTO> gameDTOList = new ArrayList<SynclassWorkGameDTO>();
        if (gameDTOList != null) {
            for (CtSynclassWorkGame game : gameList) {
                SynclassWorkGameDTO gameDTO = new SynclassWorkGameDTO();
                BeanUtils.copyProperties(game, gameDTO);
                gameDTOList.add(gameDTO);
            }
        }
        return gameDTOList;
    }

    public static SynclassWorkDTO entitySynclassWorkToDto(CtSynclassWork work) {
        SynclassWorkDTO workDTO = new SynclassWorkDTO();
        if (work != null) {
            BeanUtils.copyProperties(work, workDTO);
        }
        return workDTO;
    }

    public static CtSynclassWork getSynclassWorkFromDto(SynclassWorkDTO workDTO) {
        CtSynclassWork work = new CtSynclassWork();
        workDTO.setStatus(1);
        workDTO.setCreateTime(new Date());
        workDTO.setUpdateTime(new Date());
        workDTO.setFinishStatus(0);
        workDTO.setCorrectStatus(0);
        workDTO.setWorkId(UUID.randomUUID().toString().replace("-", ""));
        BeanUtils.copyProperties(workDTO, work);
        return work;
    }

    public static CtSynclassWork getExistSynclassWorkFromDto(SynclassWorkDTO workDTO) {
        CtSynclassWork work = new CtSynclassWork();
        workDTO.setUpdateTime(new Date());
        BeanUtils.copyProperties(workDTO, work);
        return work;
    }


    public static List<CtSynclassWorkClass> getCtSynclassWorkClassList(String workId, List<SynclassWorkClassDTO> classDTOList, String schoolId) {
        List<CtSynclassWorkClass> classList = new ArrayList<CtSynclassWorkClass>();
        if (classDTOList != null) {
            for (SynclassWorkClassDTO classDTO : classDTOList) {
                CtSynclassWorkClass workClass = new CtSynclassWorkClass();
                classDTO.setStatus(1);
                classDTO.setWorkId(workId);
                classDTO.setSchoolId(schoolId);
                classDTO.setWorkClassId(UUID.randomUUID().toString().replace("-", ""));
                BeanUtils.copyProperties(classDTO, workClass);
                classList.add(workClass);
            }
        }
        return classList;
    }

    //只针对学生
    public static CtSynclassWorkPlay getSynclassWorkPlayFromDto(SynclassWorkPlayDTO playDTO, CtSynclassWorkPlay oldPlay) {
        CtSynclassWorkPlay play = new CtSynclassWorkPlay();
        play.setUpdateTime(new Date());
        play.setUpdateUserId(playDTO.getUserId());
        play.setPlayId(oldPlay.getPlayId());
        play.setSchoolId(oldPlay.getSchoolId());
        play.setMaxScore(playDTO.getMaxScore());
        /* 学情新增游戏花费时间 */
        play.setSpendtime(playDTO.getSpendtime());
        int maxScore = oldPlay.getMaxScore();
        int playTime = oldPlay.getPlayTimes();
        if (play.getMaxScore() < maxScore) {
            play.setMaxScore(maxScore);
        }
        if (playTime == 0) {
            play.setSubTime(new Date());
            play.setFinishStatus(1);
        }
        play.setPlayTimes(playTime + 1);
        return play;
    }

    public static CtSynclassWorkAnswerComment getSynclassWorkAnswerCommentFromDto(SynclassWorkAnswerCommentDTO commentDTO) {
        CtSynclassWorkAnswerComment comment = new CtSynclassWorkAnswerComment();
        if (commentDTO.getCommentId() == null) {
            commentDTO.setCommentId(UUID.randomUUID().toString().replace("-", ""));
            commentDTO.setStatus(1);
        }
        commentDTO.setCommentTime(new Date());
        BeanUtils.copyProperties(commentDTO, comment);
        return comment;
    }

    public static CtSynclassWorkAnswerPraise getSynclassWorkAnswerPraiseFromDto(SynclassWorkAnswerPraiseDTO praiseDTO) {
        CtSynclassWorkAnswerPraise praise = new CtSynclassWorkAnswerPraise();
        praiseDTO.setStatus(1);
        praiseDTO.setPraiseTime(new Date());
        BeanUtils.copyProperties(praiseDTO, praise);
        return praise;
    }

    public static List<CtSynclassWorkGame> getCtSynclassWorkGameList(String workId, List<SynclassWorkGameDTO> gameDTOList, String schoolId) {
        List<CtSynclassWorkGame> gameList = new ArrayList<CtSynclassWorkGame>();
        if (gameDTOList != null) {
            for (SynclassWorkGameDTO gameDTO : gameDTOList) {
                CtSynclassWorkGame game = new CtSynclassWorkGame();
                gameDTO.setWorkGameId(UUID.randomUUID().toString().replace("-", ""));
                gameDTO.setWorkId(workId);
                gameDTO.setSchoolId(schoolId);
                gameDTO.setStatus(1);
                BeanUtils.copyProperties(gameDTO, game);
                gameList.add(game);
            }
        }
        return gameList;
    }

    public static void getComparedClassList(List<CtSynclassWorkClass> classList, List<CtSynclassWorkClass> oldClassList,
                                            List<CtSynclassWorkClass> existClassList, List<CtSynclassWorkClass> delClassList,
                                            List<CtSynclassWorkClass> insertClassList) {
        if (oldClassList == null) {
            insertClassList = classList;
        } else {
            for (CtSynclassWorkClass workClass : classList) {
                int flag = 0;
                for (CtSynclassWorkClass oldWorkClass : oldClassList) {
                    if (workClass.getClassId().equals(oldWorkClass.getClassId())) {
                        existClassList.add(oldWorkClass);
                        flag = 1;
                        continue;
                    }
                }
                if (flag == 0)
                    insertClassList.add(workClass);
            }
            if (existClassList.size() == 0) {
                delClassList = oldClassList;
            } else {
                for (CtSynclassWorkClass oldWorkClass : oldClassList) {
                    int flag = 0;
                    for (CtSynclassWorkClass existWorkClass : existClassList) {
                        if (existWorkClass.getClassId().equals(oldWorkClass.getClassId())) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        delClassList.add(oldWorkClass);
                    }
                }
            }
        }
    }

    public static void getComparedGameList(List<CtSynclassWorkGame> gameList, List<CtSynclassWorkGame> oldGameList,
                                           List<CtSynclassWorkGame> existGameList, List<CtSynclassWorkGame> delGameList) {
        for (CtSynclassWorkGame oldGame : oldGameList) {
            int flag = 0;
            for (CtSynclassWorkGame game : gameList) {
                if (game.getGameId().equals(oldGame.getGameId())) {
                    existGameList.add(oldGame);
                    flag = 1;
                    continue;
                }
            }
            if (flag == 0)
                delGameList.add(oldGame);
        }
    }

    public static List<String> getClassList(List<CtSynclassWorkClass> classList) {
        List<String> classIdList = new ArrayList<String>();
        for (CtSynclassWorkClass workClass : classList) {
            classIdList.add(workClass.getClassId());
        }
        return classIdList;
    }

    public static CtSynclassWorkStudent getInitStudent(CtSynclassWorkStudent oldStudent) {
        CtSynclassWorkStudent student = new CtSynclassWorkStudent();
        BeanUtils.copyProperties(oldStudent, student);
        student.setWorkUserId(UUID.randomUUID().toString().replace("-", ""));
        student.setContext("");
        student.setSubStatus(0);
        student.setCorrectStatus(0);
        student.setSubTime(null);
        student.setCorrectTime(null);
        return student;
    }

    public static CtSynclassWorkStudentStatistics getInitStudentStatistics(String schoolId) {
        CtSynclassWorkStudentStatistics studentStatistics = new CtSynclassWorkStudentStatistics();
        studentStatistics.setCommentTimes(0);
        studentStatistics.setPraiseTimes(0);
        studentStatistics.setSchoolId(schoolId);
        studentStatistics.setStatus(1);
        return studentStatistics;
    }

    public static List<String> getWorkUserIdList(List<CtSynclassWorkStudent> studentList) {
        List<String> workUserIdList = new ArrayList<String>();
        for (CtSynclassWorkStudent student : studentList) {
            workUserIdList.add(student.getWorkUserId());
        }
        return workUserIdList;
    }

    public static List<CtWorkClassGather> getWorkClassGather(List<String> classIdList, String workId, String schoolId) {
        List<CtWorkClassGather> classGatherList = new ArrayList<CtWorkClassGather>();
        for (String classId : classIdList) {
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

    public static List<CtSynclassWorkPlay> getPlayList(List<CtSynclassWorkGame> gameList, List<CtSynclassWorkStudent> studentList) {

        List<CtSynclassWorkPlay> workPlayList = new ArrayList<CtSynclassWorkPlay>();
        for (CtSynclassWorkGame game : gameList) {
            for (CtSynclassWorkStudent student : studentList) {
                CtSynclassWorkPlay play = new CtSynclassWorkPlay();
                play.setMaxScore(0);
                play.setPlayTimes(0);
                play.setSchoolId(student.getSchoolId());
                play.setWorkGameId(game.getWorkGameId());
                play.setFinishStatus(0);
                play.setPlayId(UUID.randomUUID().toString().replace("-", ""));
                play.setStatus(1);
                play.setUserId(student.getUserId());
                play.setUpdateUserId(student.getUserId());
                play.setWorkUserId(student.getWorkUserId());
                play.setWorkId(game.getWorkId());
                play.setUpdateTime(new Date());
                play.setSpendtime(0);
                workPlayList.add(play);
            }
        }
        return workPlayList;
    }

    public static List<CtWorkStudentGather> getStudentGatherList(List<CtSynclassWorkStudent> studentList, String workId) {
        List<CtWorkStudentGather> studentGatherList = new ArrayList<CtWorkStudentGather>();
        for (CtSynclassWorkStudent student : studentList) {
            CtWorkStudentGather studentGather = new CtWorkStudentGather();
            studentGather.setWorkStudentId(UUID.randomUUID().toString().replace("-", ""));
            studentGather.setStatus(1);
            studentGather.setStudentId(student.getUserId());
            studentGather.setClassId(student.getClassId());
            studentGather.setCorrectStatus(0);
            studentGather.setSubStatus(0);
            studentGather.setWorkId(workId);
            studentGather.setSchoolId(student.getSchoolId());
            studentGatherList.add(studentGather);
        }
        return studentGatherList;
    }

}
