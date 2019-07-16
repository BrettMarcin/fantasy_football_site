package com.website.interceptors;

import com.website.domains.User;
import com.website.domains.api_specific.DraftHasStarted;
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
public class CheckIfDraftHasStartedInterceptor implements HandlerInterceptor {
    @Autowired
    private DraftService draftService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        DraftHasStarted draftHasStarted = draftService.checkIfDraftHasStarted(Integer.valueOf((String)pathVariables.get("draftId")));
        if(draftHasStarted.isDraftHasStarted()) {
            throw new NotCreatorOfDraftException();
        }
        return true;
    }
}
