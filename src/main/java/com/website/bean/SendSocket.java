package com.website.bean;

import com.website.domains.TheTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class SendSocket {

    private SimpMessagingTemplate template;

    @Autowired
    public SendSocket(SimpMessagingTemplate template) {
        this.template = template;
//        this.sendCurrentTime = taskExecutor;
//        this.currentTime = taskExecutor2;
    }

    public void sendMessage(int draftId, TheTime time) {
        this.template.convertAndSend("/draft/timer/"+draftId, time);
    }

}
