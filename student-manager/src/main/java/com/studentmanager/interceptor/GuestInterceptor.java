package com.studentmanager.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.studentmanager.service.SessionService;

@Component
public class GuestInterceptor implements HandlerInterceptor {
    @Autowired
    private SessionService session;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (session.checkCurrentAccount()) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
