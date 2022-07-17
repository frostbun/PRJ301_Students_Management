package com.studentmanager.interceptor.parser;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.studentmanager.model.Account;
import com.studentmanager.model.ClassMember;
import com.studentmanager.service.ClassMemberService;

@Component
public class ClassMemberParser implements HandlerInterceptor {
    @Autowired
    private ClassMemberService classMemberService;

    @SuppressWarnings("rawtypes")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Map pathVariables = (Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        ClassMember classMember = classMemberService.getClassMember(
            (Account)request.getAttribute("account"),
            Long.parseLong(pathVariables.get("cid").toString())
        );
        if (classMember == null) {
            response.sendRedirect("/error/404");
            return false;
        }
        request.setAttribute("classMember", classMember);
        request.setAttribute("classroom", classMember.getClassroom());
        return true;
    }
}
