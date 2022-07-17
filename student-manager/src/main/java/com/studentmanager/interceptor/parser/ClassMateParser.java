package com.studentmanager.interceptor.parser;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.studentmanager.model.ClassMember;
import com.studentmanager.model.Classroom;
import com.studentmanager.service.ClassMemberService;

@Component
public class ClassMateParser implements HandlerInterceptor {
    @Autowired
    private ClassMemberService classMemberService;

    @SuppressWarnings("rawtypes")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Map pathVariables = (Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        ClassMember classMate = classMemberService.getClassMember(
            pathVariables.get("username").toString(),
            (Classroom)request.getAttribute("classroom")
        );
        if (classMate == null) {
            response.sendRedirect("/error/404");
            return false;
        }
        request.setAttribute("classMate", classMate);
        return true;
    }
}
