package com.studentmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.studentmanager.interceptor.AccountInterceptor;
import com.studentmanager.interceptor.ClassmemberInterceptor;
import com.studentmanager.interceptor.GuestInterceptor;
import com.studentmanager.interceptor.HomeworkInterceptor;
import com.studentmanager.interceptor.StudentInterceptor;
import com.studentmanager.interceptor.SubmissionInterceptor;
import com.studentmanager.interceptor.TeacherInterceptor;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private GuestInterceptor guestInterceptor;
    @Autowired
    private AccountInterceptor accountInterceptor;
    @Autowired
    private ClassmemberInterceptor classmemberInterceptor;
    @Autowired
    private HomeworkInterceptor homeworkInterceptor;
    @Autowired
    private SubmissionInterceptor submissionInterceptor;
    @Autowired
    private TeacherInterceptor teacherInterceptor;
    @Autowired
    private StudentInterceptor studentInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(guestInterceptor)
                .addPathPatterns("/login")
                .addPathPatterns("/register")
        ;
        registry.addInterceptor(accountInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/login")
                .excludePathPatterns("/register")
                .excludePathPatterns("/static/**")
        ;
        registry.addInterceptor(classmemberInterceptor)
                .addPathPatterns("/classroom/*/**")
                .excludePathPatterns("/classroom/create")
                .excludePathPatterns("/classroom/join")
        ;
        registry.addInterceptor(homeworkInterceptor)
                .addPathPatterns("/classroom/*/homework/*/**")
        ;
        registry.addInterceptor(submissionInterceptor)
                .addPathPatterns("/classroom/*/homework/*/submission/*/**")
        ;
        registry.addInterceptor(teacherInterceptor)
                .addPathPatterns("/classroom/*/member/*/remove")
                .addPathPatterns("/classroom/*/member/*/promote")
                .addPathPatterns("/classroom/*/member/*/demote")
                .addPathPatterns("/classroom/*/homework/create")
                .addPathPatterns("/classroom/*/homework/*/edit")
                .addPathPatterns("/classroom/*/homework/*/delete")
                .addPathPatterns("/classroom/*/homework/*/submission")
                .addPathPatterns("/classroom/*/homework/*/submission/*/mark")
        ;
        registry.addInterceptor(studentInterceptor)
                .addPathPatterns("/classroom/*/homework/*/submission/create")
        ;
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
        ;
    }
}
