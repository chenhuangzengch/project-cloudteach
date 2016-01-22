package net.xuele.cloudteach.web.common;

import com.alibaba.dubbo.common.utils.StringUtils;
import net.xuele.cloudteach.dto.BookDTO;
import net.xuele.cloudteach.service.CloudTeachRedisService;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.common.security.SessionUtil;
import net.xuele.member.constant.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import java.awt.event.MouseListener;

/**
 * Created by Administrator on 2015/6/25 0025.
 * layout 拦截器
 * 处理layout 公共部分的数据收集，top,left,footer等
 */
public class LayoutInterceptor implements WebRequestInterceptor {

    @Autowired
    CloudTeachService cloudTeachService;

    @Autowired
    CloudTeachRedisService cloudTeachRedisService;

    @Override
    public void preHandle(WebRequest request) throws Exception {

    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        if (null != model) {
            String bookName = "";
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            if( authentication != null) {
                bookName = GetSessionContentUtil.getInstance().currentBookName();
                if (StringUtils.isEmpty(bookName)) {
                    String bookId = GetSessionContentUtil.getInstance().currentBookID();
                    if(!StringUtils.isEmpty(bookId)){
                        BookDTO bookDTO = cloudTeachService.selectEditionAndSubject(bookId);
                        if (bookDTO != null) {
                            bookName = bookDTO.getBookName();
                        }
                    }
                }

                String scUnitId = cloudTeachRedisService.get("ScTeacherUnitId:" + SessionUtil.getUserSession().getUserId());
                String ctUnitId = cloudTeachRedisService.get("CtTeacherUnitId:" + SessionUtil.getUserSession().getUserId());

                model.put("scUnitId",scUnitId);
                model.put("ctUnitId",ctUnitId);
            }




            model.put("ctx",request.getContextPath());
            model.put("smallSrc", UserConstants.ICON_URL + "24x24_");
            model.put("normalSrc", UserConstants.ICON_URL + "48x48_");
            model.put("bigSrc", UserConstants.ICON_URL + "96x96_");
            model.put("bookName", bookName);

            //填充头部页面碎片
            fillHeadFrameset(request, model);

            //填充左侧菜单部分
            fillLeftBarFrameset(request, model);

            //填充底部页面碎片
            fillFootFrameset(request, model);

            //填充其他碎片
            fillOtherFrameset(request, model);
        }


    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {

    }


    //===============================private helper==========================================//

    private void fillOtherFrameset(WebRequest request, ModelMap model) {

    }

    private void fillFootFrameset(WebRequest request, ModelMap model) {
        if (null != model) {
//            model.put("test", "this is a footer attribute");
        }
    }

    private void fillLeftBarFrameset(WebRequest request, ModelMap model) {

    }

    private void fillHeadFrameset(WebRequest request, ModelMap model) {

    }
}
