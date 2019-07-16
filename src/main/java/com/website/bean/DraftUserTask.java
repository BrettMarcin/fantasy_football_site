package com.website.bean;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Timer;
import java.util.TimerTask;

public class DraftUserTask extends TimerTask {

    private Timer sendCurrentTime;

    private int draftId;


    public DraftUserTask(int draftId) {
        this.draftId = draftId;
        SendTimeTask sendTimeTask = new SendTimeTask(System.currentTimeMillis(), draftId);
        sendCurrentTime = new Timer(true);
        sendCurrentTime.scheduleAtFixedRate(sendTimeTask, 0,1000);
    }

    public void resetLocalTimer() {
        SendTimeTask sendTimeTask = new SendTimeTask(System.currentTimeMillis(), draftId);
        sendCurrentTime.purge();
        sendCurrentTime.cancel();
        sendCurrentTime = new Timer(true);
        sendCurrentTime.scheduleAtFixedRate(sendTimeTask, 0,1000);
    }

    @Override
    public void run() {
        resetLocalTimer();
        System.out.println("Drafting user!");
    }
}
