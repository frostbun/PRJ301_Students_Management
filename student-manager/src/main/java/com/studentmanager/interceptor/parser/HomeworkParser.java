package com.studentmanager.interceptor.parser;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.studentmanager.model.Classroom;
import com.studentmanager.model.Homework;
import com.studentmanager.service.HomeworkService;

@Component
public class HomeworkParser implements HandlerInterceptor {
    @Autowired
    private HomeworkService homeworkService;

    @SuppressWarnings("rawtypes")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Map pathVariables = (Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Homework homework = homeworkService.getHomework(
            Long.parseLong(pathVariables.get("hid").toString()),
            (Classroom)request.getAttribute("classroom")
        );
        if (homework == null) {
            response.sendRedirect("/error/404");
            return false;
        }
        request.setAttribute("homework", homework);
        return true;
    }
}
