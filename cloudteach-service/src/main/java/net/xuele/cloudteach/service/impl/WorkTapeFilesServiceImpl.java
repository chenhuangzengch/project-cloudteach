package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.domain.CtWorkTapeFiles;
import net.xuele.cloudteach.dto.WorkTapeFilesDTO;
import net.xuele.cloudteach.persist.CtWorkTapeFilesMapper;
import net.xuele.cloudteach.service.WorkTapeFilesService;
import net.xuele.common.exceptions.CloudteachException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * WorkTapeFilesServiceImpl
 * 作业教师录音附件服务
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
@Service
public class WorkTapeFilesServiceImpl implements WorkTapeFilesService {

    @Autowired
    private CtWorkTapeFilesMapper ctWorkTapeFilesMapper;

    @Override
    public int removeWorkTapeFile(String fileId, String schoolId) throws CloudteachException {
        return ctWorkTapeFilesMapper.removeWorkTapeFile(fileId, schoolId);
    }

    @Override
    public int updateWorkTapeFile(WorkTapeFilesDTO workTapeFilesDTO) throws CloudteachException {
        CtWorkTapeFiles ctWorkTapeFiles = new CtWorkTapeFiles();
        BeanUtils.copyProperties(workTapeFilesDTO, ctWorkTapeFiles);
        return ctWorkTapeFilesMapper.updateWorkTapeFile(ctWorkTapeFiles);
    }

    @Override
    public int insertCloudDiskShare(WorkTapeFilesDTO workTapeFilesDTO) {
        CtWorkTapeFiles ctWorkTapeFiles = new CtWorkTapeFiles();
        BeanUtils.copyProperties(workTapeFilesDTO, ctWorkTapeFiles);
        return ctWorkTapeFilesMapper.insert(ctWorkTapeFiles);
    }

    @Override
    public WorkTapeFilesDTO queryWorkTapeFileByFileID(String fileId, String schoolId) throws CloudteachException {
        CtWorkTapeFiles ctWorkTapeFiles = ctWorkTapeFilesMapper.selectByPrimaryKey(fileId, schoolId);
        if (ctWorkTapeFiles == null) {
            return null;
        } else {
            WorkTapeFilesDTO workTapeFilesDTO = new WorkTapeFilesDTO();
            BeanUtils.copyProperties(ctWorkTapeFiles, workTapeFilesDTO);
            return workTapeFilesDTO;
        }
    }

    @Override
    public List<WorkTapeFilesDTO> queryListByWorkId(String workId, String schoolId) throws CloudteachException {
        List<CtWorkTapeFiles> tapeFileList = ctWorkTapeFilesMapper.queryListByWorkId(workId, schoolId);
        if (tapeFileList == null) {
            return null;
        } else {
            return entityWorkTapeFilesListToDtoList(tapeFileList);
        }
    }

    @Override
    public WorkTapeFilesDTO getWorkTapeFilesByWorkId(String workId, String schoolId) throws CloudteachException {

        CtWorkTapeFiles ctWorkTapeFiles = ctWorkTapeFilesMapper.getWorkTapeFilesByWorkId(workId, schoolId);
        WorkTapeFilesDTO workTapeFilesDTO = new WorkTapeFilesDTO();
        if (ctWorkTapeFiles != null) {
            BeanUtils.copyProperties(ctWorkTapeFiles, workTapeFilesDTO);
            return workTapeFilesDTO;
        } else {
            return null;
        }
        //return workTapeFilesDTO;
    }

    private List<WorkTapeFilesDTO> entityWorkTapeFilesListToDtoList(List<CtWorkTapeFiles> datalist) {

        List<WorkTapeFilesDTO> resList = new ArrayList<WorkTapeFilesDTO>();
        for (CtWorkTapeFiles objDATA : datalist) {
            WorkTapeFilesDTO objDTO = new WorkTapeFilesDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }
}