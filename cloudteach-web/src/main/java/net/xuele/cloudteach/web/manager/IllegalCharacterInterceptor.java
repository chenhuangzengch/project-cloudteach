package net.xuele.cloudteach.web.manager;

import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.exceptions.MemberException;
import net.xuele.member.constant.DownloadUrlConstants;
import net.xuele.member.constant.UserConstants;
import net.xuele.member.util.AjaxUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * 非法字符拦截（只拦截html特殊字符）
 * Created by sunxinhe on 15/8/27.
 */
public class IllegalCharacterInterceptor implements HandlerInterceptor {
//    //    Pattern p = Pattern.compile("^[0-9a-zA-Z\\u4E00-\\u9FA5@\\-_,.]*$");
//    private static final String illegalChars = "\'\"&<>";
//
//
//    private boolean isIllegal(String s) {
////        Matcher matcher = p.matcher(s);
////        return !matcher.matches();
//        return StringUtils.containsAny(s, illegalChars);
//    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isAjax = false;
        if (AjaxUtil.isAjax(request)) {
            isAjax = true;
        } else {
            request.setAttribute("smallSrc", UserConstants.ICON_URL + "24x24_");
            request.setAttribute("normalSrc", UserConstants.ICON_URL + "48x48_");
            request.setAttribute("bigSrc", UserConstants.ICON_URL + "96x96_");
            request.setAttribute("ctx", request.getContextPath());
            request.setAttribute("androidAppUrl", DownloadUrlConstants.ANDROID_APP_URL);
            request.setAttribute("iosAppUrl", DownloadUrlConstants.IOS_APP_URL);
        }
        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
        for (Map.Entry<String, String[]> stringEntry : entrySet) {
            String[] value = stringEntry.getValue();
            if (value == null || value.length == 0) {
                continue;
            }
            for (int i = 0, l = value.length; i < l; i++) {
                String s = value[i];
//                if (StringUtils.isEmpty(s)) {
//                    continue;
//                }
//                value[i] = s.trim();
//                if (isIllegal(s)) {
//                    if (isAjax) {
//                        throw new CloudteachException("不接受特殊字符" + illegalChars);
//                    } else {
//                        value[i] = "";
//                    }
//                }
                if (StringUtils.isEmpty(s)) {
                    continue;
                }
                value[i] = StringEscapeUtils.escapeHtml4(s.trim());
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}