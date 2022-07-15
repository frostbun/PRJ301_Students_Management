package com.studentmanager.interceptor;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.studentmanager.service.SessionService;

@Component
public class ClassmemberInterceptor implements HandlerInterceptor {
    @Autowired
    private SessionService session;

    @SuppressWarnings("rawtypes")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Map pathVariables = (Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long cid = Long.parseLong(pathVariables.get("cid").toString());
        if (!session.checkCurrentClassMember(cid)) {
            response.sendRedirect("/error/404");
            return false;
        }
        return true;
    }
}
