package net.xuele.cloudteach.service.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TeacherWorkGetherAspect
 *      教师作业汇总拦截器
 *          汇总教师作业相关的数据的创建和更新，
 *          汇总学生对作业的操作
 *          目前这些数据用于提供云教学首页展示
 * @author sunxh
 * @date 15/7/27
 */
@Aspect
public class TeacherWorkGetherAspect {

    private static Logger logger = LoggerFactory.getLogger(TeacherWorkGetherAspect.class);

    /**
     *
     * @throws java.lang.Throwable
     */
    @AfterReturning(value = "execution(net.xuele.cloudteach.service.TeacherWorkAddService.addHomework(..))",returning = "returnValue")
    public void afterReturning(JoinPoint jp,Object returnValue) throws Throwable {
        //TODO 拦截老师创建作业service
        logger.info(jp.getTarget().toString());
        logger.info(returnValue.toString());
    }

    //TODO 拦截老师删除、批改作业；学生提交作业的service
}
