package com.studentmanager.interceptor.parser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class GeneralParser implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String page = request.getParameter("page");
        if (page == null) page = "1";
        String query = request.getParameter("query");
        if (query == null) query = "";
        request.setAttribute("page", page);
        request.setAttribute("query", query);
        request.setAttribute("error", request.getParameter("error"));
        return true;
    }
}
