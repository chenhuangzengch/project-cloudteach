package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.WorkTapeFilesDTO;
import net.xuele.common.exceptions.CloudteachException;

import java.util.List;

/**
 * WorkTapeFilesService
 * 作业教师录音附件服务
 *
 * @author duzg
 * @date 2015/7/12 0002
 */
public interface WorkTapeFilesService {


    /**
     * @param fileId   录音ID
     * @param schoolId 学校ID
     * @return int
     * @throws net.xuele.common.exceptions.CloudteachException 逻辑删除录音文件，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    int removeWorkTapeFile(String fileId, String schoolId) throws CloudteachException;

    /**
     * @param workTapeFilesDTO 录音信息
     * @return int
     * @throws net.xuele.common.exceptions.CloudteachException 修改录音信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    int updateWorkTapeFile(WorkTapeFilesDTO workTapeFilesDTO) throws CloudteachException;

    /**
     * @param workTapeFilesDTO 录音信息
     * @return int
     * @throws net.xuele.common.exceptions.CloudteachException 新增录音信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    int insertCloudDiskShare(WorkTapeFilesDTO workTapeFilesDTO);

    /**
     * @param fileId   录音ID
     * @param schoolId 学校ID
     * @return WorkTapeFilesDTO
     * @throws net.xuele.common.exceptions.CloudteachException 根据ID获取录音信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    WorkTapeFilesDTO queryWorkTapeFileByFileID(String fileId, String schoolId) throws CloudteachException;

    /**
     * @param workId   作业ID
     * @param schoolId 学校ID
     * @return List<WorkTapeFilesDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据作业ID获取录音信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    List<WorkTapeFilesDTO> queryListByWorkId(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    WorkTapeFilesDTO getWorkTapeFilesByWorkId(String workId, String schoolId) throws CloudteachException;
}