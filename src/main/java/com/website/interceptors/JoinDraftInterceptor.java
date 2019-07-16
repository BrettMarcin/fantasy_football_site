package com.website.interceptors;

import com.website.domains.User;
import com.website.exception.CannotJoinDraftException;
import com.website.exception.TooManyDraftException;
import com.website.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class JoinDraftInterceptor implements HandlerInterceptor {
    @Autowired
    private DraftService draftService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        // check if user was invited and that the draft hasn't started
        if(!draftService.checkIfUserWasInvited(theUser.getUsername(), Integer.valueOf((String)pathVariables.get("draftId")))) {
            throw new CannotJoinDraftException();
        }
        return true;
    }
}
