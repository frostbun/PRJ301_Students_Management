package com.studentmanager.interceptor.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.studentmanager.service.SessionService;

@Component
public class LoggedInFilter implements HandlerInterceptor {
    @Autowired
    private SessionService session;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (!session.checkCurrentAccount()) {
            response.sendRedirect("/login?redirect=" + request.getRequestURI());
            return false;
        }
        request.setAttribute("account", session.getCurrentAccount());
        return true;
    }
}
