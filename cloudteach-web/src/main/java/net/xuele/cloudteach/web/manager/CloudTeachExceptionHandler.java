package net.xuele.cloudteach.web.manager;

import net.xuele.common.exceptions.XueleLogicException;
import net.xuele.member.util.AjaxUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.PriorityOrdered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * CloudTeachExceptionHandler
 *
 * @author sunxh
 * @date 2015/6/24 0024
 */
public class CloudTeachExceptionHandler implements HandlerExceptionResolver, PriorityOrdered {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (AjaxUtil.isAjax(request)) {
            logger.error("url:" + request.getRequestURI() + "AJAX exception: ", ex);
            AjaxUtil.outputAjaxException(request, response, ex);
            return new ModelAndView();
        }
        logger.error("url:" + request.getRequestURI(), ex);
        request.setAttribute("ex", ex);
        try {
            if (ex instanceof XueleLogicException) {
                String errorInfo = "系统出现异常:" + ex.getMessage();
                errorInfo = Base64.encodeBase64URLSafeString(errorInfo.getBytes("UTF-8"));
                ModelAndView view = new ModelAndView("system/fail");
                view.addObject("errorInfo", errorInfo);
                return view;
            }
            if (ex instanceof TypeMismatchException) {
                String errorInfo = "参数类型错误，请确认输入参数是否正确";
                errorInfo = Base64.encodeBase64URLSafeString(errorInfo.getBytes("UTF-8"));
                ModelAndView view = new ModelAndView("system/fail");
                view.addObject("errorInfo", errorInfo);
                return view;
            }
            if (ex instanceof NoHandlerFoundException) {
                return new ModelAndView("system/404");
            }

        } catch (UnsupportedEncodingException e) {
        }
        return new ModelAndView("system/500");
    }


    public int getOrder() {
        return -1;
    }
}
