package com.website.bean;

import com.website.config.BeanUtil;
import com.website.domains.TheTime;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.persistence.EntityManager;
import java.util.TimerTask;

public class SendTimeTask extends TimerTask {

    private long lastTime;

    private int draftId;

    private SimpMessagingTemplate template;

//    @Autowired
//    public SendTimeTask(SimpMessagingTemplate template) {
//        this.template = template;
//    }

    @Override
    public void run() {
        SendSocket socket = BeanUtil.getBean(SendSocket.class);
        socket.sendMessage(draftId, new TheTime(System.currentTimeMillis() - lastTime));
//        this.template.convertAndSend("/draft/timer/"+draftId, new TheTime(System.currentTimeMillis() - lastTime));
//        template.sendMessage(draftId, new TheTime(System.currentTimeMillis() - lastTime));
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public SendTimeTask(long lastTime, int draftId) {
        this.lastTime = lastTime;
        this.draftId = draftId;
    }
}
