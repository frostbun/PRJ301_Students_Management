package com.studentmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.studentmanager.interceptor.filter.LoggedInFilter;
import com.studentmanager.interceptor.filter.LoggedOutFilter;
import com.studentmanager.interceptor.filter.StudentFilter;
import com.studentmanager.interceptor.filter.TeacherFilter;
import com.studentmanager.interceptor.parser.ClassMateParser;
import com.studentmanager.interceptor.parser.ClassMemberParser;
import com.studentmanager.interceptor.parser.GeneralParser;
import com.studentmanager.interceptor.parser.HomeworkParser;
import com.studentmanager.interceptor.parser.SubmissionParser;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoggedOutFilter loggedOutFilter;
    @Autowired
    private LoggedInFilter loggedinFilter;
    @Autowired
    private TeacherFilter teacherFilter;
    @Autowired
    private StudentFilter studentFilter;
    @Autowired
    private ClassMemberParser classMemberParser;
    @Autowired
    private ClassMateParser classMateParser;
    @Autowired
    private HomeworkParser homeworkParser;
    @Autowired
    private SubmissionParser submissionParser;
    @Autowired
    private GeneralParser generalParser;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggedOutFilter)
                .addPathPatterns("/login")
                .addPathPatterns("/register")
        ;
        registry.addInterceptor(loggedinFilter)
                .addPathPatterns("/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/login")
                .excludePathPatterns("/register")
                .excludePathPatterns("/error/**")
                .excludePathPatterns("/static/**")
        ;
        registry.addInterceptor(classMemberParser)
                .addPathPatterns("/classroom/*/*/**")
        ;
        registry.addInterceptor(classMateParser)
                .addPathPatterns("/classroom/*/member/*/*/**")
        ;
        registry.addInterceptor(homeworkParser)
                .addPathPatterns("/classroom/*/homework/*/*/**")
        ;
        registry.addInterceptor(submissionParser)
                .addPathPatterns("/classroom/*/homework/*/submission/*/*/**")
        ;
        registry.addInterceptor(teacherFilter)
                .addPathPatterns("/classroom/*/member/*/remove")
                .addPathPatterns("/classroom/*/member/*/promote")
                .addPathPatterns("/classroom/*/member/*/demote")
                .addPathPatterns("/classroom/*/homework/create")
                .addPathPatterns("/classroom/*/homework/*/edit")
                .addPathPatterns("/classroom/*/homework/*/delete")
                .addPathPatterns("/classroom/*/homework/*/submission")
                .addPathPatterns("/classroom/*/homework/*/submission/*/mark")
        ;
        registry.addInterceptor(studentFilter)
                .addPathPatterns("/classroom/*/homework/*/submission/create")
        ;
        registry.addInterceptor(generalParser)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**");
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
        ;
    }
}
