package com.website.bean;

import com.website.domains.Trade;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Random;

@Component
public class TradeManager {

    // HashMap<DraftId, HashMap<TradeIdNum, Trade>>
    private HashMap<Integer, HashMap<Integer, Trade>> map;

//    private HashMap<> userPendingTrades;

    @PostConstruct
    private void init(){
        map = new HashMap<>();
    }

    public void addProposedTrade(Trade trade, int draftId) {
        // TODO: socket to let the other player know about the proposal
        if (!map.containsKey(draftId)) {
            map.put(draftId, new HashMap<>());
        }
        Random rand = new Random();
        rand.nextInt();
//        map.get(draftId).put(Rand)
    }

    public void acceptProposedTrade(int draftId, int tradeIdNum) {
        // TODO: socket to everyone that a trade was accepted
    }

    public boolean canUserAcceptTrade(String user) {
        return true;
    }
}
