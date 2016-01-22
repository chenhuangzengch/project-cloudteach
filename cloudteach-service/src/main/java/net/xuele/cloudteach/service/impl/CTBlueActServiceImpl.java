package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.dto.BlueStudentSubWorkDTO;
import net.xuele.cloudteach.dto.BlueTeacherCorrectWorkDTO;
import net.xuele.cloudteach.persist.CTBlueActMapper;
import net.xuele.cloudteach.service.CTBlueActService;
import net.xuele.cloudteach.view.BlueStudentSubWorkView;
import net.xuele.cloudteach.view.BlueTeacherCorrectWorkView;
import net.xuele.common.exceptions.CloudteachException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hux on 2015/11/2 0002.
 * 蓝色感恩月活动服务
 */
@Service
public class CTBlueActServiceImpl implements CTBlueActService {

    @Autowired
    CTBlueActMapper ctBlueActMapper;

    /**
     * 时间段内，教师批改的所有预习/电子作业的数量
     *
     * @param teacherId
     * @param begin
     * @param end
     * @return
     * @throws CloudteachException
     */
    @Override
    public List<BlueTeacherCorrectWorkDTO> getCorrectWork(String teacherId, Date begin, Date end, String schoolId) throws CloudteachException {

        List<BlueTeacherCorrectWorkDTO> ldto = new ArrayList<>();
        List<BlueTeacherCorrectWorkView> ldom = ctBlueActMapper.selectCorrectWork(teacherId, begin, end, schoolId);
        for (BlueTeacherCorrectWorkView dom : ldom) {
            BlueTeacherCorrectWorkDTO dto = new BlueTeacherCorrectWorkDTO();
            BeanUtils.copyProperties(dom, dto);
            ldto.add(dto);
        }
        return ldto;
    }

    /**
     * 时间段内，教师布置并且学生提交的所有预习/电子作业的数量
     *
     * @param teacherId
     * @param begin
     * @param end
     * @return
     * @throws CloudteachException
     */
    @Override
    public List<BlueStudentSubWorkDTO> getSubedWork(String teacherId, Date actBegin, Date begin, Date end, String schoolId) throws CloudteachException {

        List<BlueStudentSubWorkDTO> ldto = new ArrayList<>();
        List<BlueStudentSubWorkView> ldom = ctBlueActMapper.selectSubedWork(teacherId, actBegin, begin, end, schoolId);
        for (BlueStudentSubWorkView dom : ldom) {
            BlueStudentSubWorkDTO dto = new BlueStudentSubWorkDTO();
            BeanUtils.copyProperties(dom, dto);
            ldto.add(dto);
        }
        return ldto;
    }

    /**
     * 时间段内，学生提交的所有预习/电子作业的数量
     *
     * @param studentId
     * @param begin
     * @param end
     * @return
     * @throws CloudteachException
     */
    @Override
    public List<BlueStudentSubWorkDTO> getStudentSubedWork(String studentId, Date begin, Date end, String schoolId) throws CloudteachException {

        List<BlueStudentSubWorkDTO> ldto = new ArrayList<>();
        List<BlueStudentSubWorkView> ldom = ctBlueActMapper.selectStudentSubedWork(studentId, begin, end, schoolId);
        for (BlueStudentSubWorkView dom : ldom) {
            BlueStudentSubWorkDTO dto = new BlueStudentSubWorkDTO();
            BeanUtils.copyProperties(dom, dto);
            ldto.add(dto);
        }
        return ldto;
    }
}
