package com.website.config;

import com.website.interceptors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private CreateDraftInterceptor createDraftInterceptor;

    @Autowired
    private JoinDraftInterceptor joinDraftInterceptor;

    @Autowired
    private InviteMoreUsersInterceptor inviteMoreUsersInterceptor;

    @Autowired
    private CheckIfDraftOwnerInterceptor checkIfDraftOwnerInterceptor;

    @Autowired
    private CheckIfUserIsPicking checkIfUserIsPicking;

    @Autowired
    private CheckIfUserCanDeleteDraft checkIfUserCanDeleteDraft;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createDraftInterceptor).addPathPatterns("/api/createDraft");
        registry.addInterceptor(joinDraftInterceptor).addPathPatterns("/api/joinDraft/*");
        registry.addInterceptor(inviteMoreUsersInterceptor).addPathPatterns("/api/invitedMoreUsers/*");
        registry.addInterceptor(checkIfDraftOwnerInterceptor).addPathPatterns("/api/startDraft/*");
        registry.addInterceptor(checkIfDraftOwnerInterceptor).addPathPatterns("/api/resumeDraft/*");
        registry.addInterceptor(checkIfUserCanDeleteDraft).addPathPatterns("/api/deleteDraft/*");
        registry.addInterceptor(checkIfUserIsPicking).addPathPatterns("/api/pickPlayer/*");
//        registry.addInterceptor(checkIfDraftHasStartedInterceptor).addPathPatterns("/api/invitedMoreUsers/*");
    }
}
