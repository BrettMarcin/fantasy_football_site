package com.website.interceptors;


import com.website.domain.User;
import com.website.exception.TooManyDraftException;
import com.website.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CreateDraftInterceptor implements HandlerInterceptor {

    @Autowired
    private DraftService draftService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // user can only create 2 drafts and there can only be 10 drafts created at one time
        if(draftService.checkIfCreatedTwo(theUser.getId()) || draftService.numberOfDraftIsAtLimit()) {
            throw new TooManyDraftException();
        }
        return true;
    }
}
