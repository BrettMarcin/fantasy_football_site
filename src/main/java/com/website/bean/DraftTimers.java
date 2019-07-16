package com.website.bean;

import com.website.domains.DraftTimer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class DraftTimers {

    private HashMap<Integer, DraftTimer> map;

    @PostConstruct
    private void init(){
        map = new HashMap<>();
    }

    public void createNewTimer(int draftId) {
        map.put(draftId, new DraftTimer(draftId));
    }

    public DraftTimer getTimer(int draftId) {
        return map.get(draftId);
    }

    public void resetTimer(int draftId) {
        map.get(draftId).resetTime();
    }
}
