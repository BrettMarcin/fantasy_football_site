package com.website.domains;
import com.website.bean.DraftTimers;
import com.website.bean.DraftUserTask;
import com.website.bean.SendSocket;
import com.website.bean.SendTimeTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Timer;

public class DraftTimer {

    // 2 minute clock to draft user
    private Timer draftUserTime;

    private int draftId;

    public DraftTimer(int draftId) {
        this.draftId = draftId;

        DraftUserTask draftUserTask = new DraftUserTask(draftId);
        draftUserTime = new Timer(true);
        draftUserTime.scheduleAtFixedRate(draftUserTask, 0, 120000);
    }


    public void draftUser() {
        System.out.println("need to implement feature to call service");
        DraftUserTask draftUserTask = new DraftUserTask(draftId);
        draftUserTime.purge();
        draftUserTime.scheduleAtFixedRate(draftUserTask, 0, 120000);
    }

    public void resetTime() {
        SendTimeTask sendTimeTask = new SendTimeTask(System.currentTimeMillis(), draftId);
//        sendCurrentTime.purge();
//        sendCurrentTime.scheduleAtFixedRate(sendTimeTask, 0,1000);
    }

}
