package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.service.LearningInfoWorkService;
import net.xuele.cloudteach.web.common.CloudTeachEncryptUtil;
import net.xuele.cloudteach.web.wrapper.*;
import net.xuele.common.ajax.AjaxResponse;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujx on 2015/9/23 0023.
 * 测试专用，请勿使用在业务方面
 */
@Controller
@RequestMapping(value = "test")
public class zTestController {
    private static Logger logger = LoggerFactory.getLogger(TeachCoursewaresExtWorkSRController.class);
    @Autowired
    LearningInfoWorkService learningInfoWorkService;

}
