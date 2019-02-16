package com.website.scheduler;

import com.website.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AppScheduler {

    @Autowired
    private DraftService draftService;

    // 3 hours
    @Scheduled(fixedRate = 10800000)
    public void myScheduler() {
        draftService.checkIfDraftsHasExpired();
    }

}
