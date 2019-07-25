package com.website.interceptors;

import com.website.domains.Draft;
import com.website.domains.Picks;
import com.website.domains.User;
import com.website.exception.DraftRunningException;
import com.website.exception.NotCreatorOfDraftException;
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
public class CheckIfUserCanDeleteDraft implements HandlerInterceptor {

    @Autowired
    private DraftService draftService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Draft draft = draftService.getDraft(Integer.valueOf((String)pathVariables.get("draftId")));

        if(!draft.getUserCreated().getUsername().equals(theUser.getUsername())) {
            throw new NotCreatorOfDraftException();
        }
        if (!draft.getWasRunning().equals("no")) {
            throw new DraftRunningException();
        }
        return true;
    }
}