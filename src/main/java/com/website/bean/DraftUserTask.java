package com.website.bean;

import com.website.config.BeanUtil;
import com.website.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Timer;
import java.util.TimerTask;

public class DraftUserTask extends TimerTask {

    private Timer sendCurrentTime;

    private SendTimeTask sendTimeTask;

    private int draftId;

    private int numberTimeRan;


    public DraftUserTask(int draftId) {
        this.draftId = draftId;
        sendTimeTask = new SendTimeTask(System.currentTimeMillis(), draftId);
        sendCurrentTime = new Timer(true);
        sendCurrentTime.scheduleAtFixedRate(sendTimeTask, 0,1000);
        numberTimeRan = 0;
    }

    public void resetLocalTimer() {
        SendTimeTask sendTimeTask = new SendTimeTask(System.currentTimeMillis(), draftId);
        sendCurrentTime.purge();
        sendCurrentTime.cancel();
        sendCurrentTime = new Timer(true);
        sendCurrentTime.scheduleAtFixedRate(sendTimeTask, 0,1000);
    }

    public void stopTimer() {
        sendTimeTask.cancel();
        sendCurrentTime.purge();
        sendCurrentTime.cancel();
    }

    @Override
    public void run() {
        if (numberTimeRan > 0) {
            sendTimeTask.setLastTime(System.currentTimeMillis());
//            stopTimer();
//            SendTimeTask sendTimeTask = new SendTimeTask(System.currentTimeMillis(), draftId);
//            sendCurrentTime = new Timer(true);
//            sendCurrentTime.scheduleAtFixedRate(sendTimeTask, 0,1000);
            DraftService draftService = BeanUtil.getBean(DraftService.class);
            draftService.draftHighestRankedPlayer(draftId);
        }
        numberTimeRan++;
    }
}
