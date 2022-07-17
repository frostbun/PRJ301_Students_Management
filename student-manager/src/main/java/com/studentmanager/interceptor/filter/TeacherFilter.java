package com.studentmanager.interceptor.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.studentmanager.model.ClassMember;

@Component
public class TeacherFilter implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (!((ClassMember)request.getAttribute("classMember")).isTeacher()) {
            response.sendRedirect("/error/404");
            return false;
        }
        return true;
    }
}
