package com.studentmanager.interceptor.parser;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.studentmanager.model.Homework;
import com.studentmanager.model.Submission;
import com.studentmanager.service.SubmissionService;

@Component
public class SubmissionParser implements HandlerInterceptor {
    @Autowired
    private SubmissionService submissionService;

    @SuppressWarnings("rawtypes")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Map pathVariables = (Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Submission submission = submissionService.getSubmission(
            Long.parseLong(pathVariables.get("sid").toString()),
            (Homework)request.getAttribute("homework")
        );
        if (submission == null) {
            response.sendRedirect("/error/404");
            return false;
        }
        request.setAttribute("submission", submission);
        return true;
    }
}
