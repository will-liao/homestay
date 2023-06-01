package com.will.homestay.filter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SensitiveWordsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        MyRequestWrapper myRequestWrapper = new MyRequestWrapper(req);
        String comment = req.getParameter("comment");
        String replaced= comment;
        if (StringUtils.isNotBlank(comment)) {
            replaced = comment.replaceAll("日|操|草|傻|中国|共产党|国家|台独|黄色|赌博|嫖娼", "*");
            //修改请求参数
            myRequestWrapper.addParameter("comment", replaced);
        }
        chain.doFilter(myRequestWrapper, response);
    }
}
