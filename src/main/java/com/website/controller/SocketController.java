package com.website.controller;

import com.website.domains.Message;
import com.website.domains.OutputMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Controller
public class SocketController {

    private SimpMessagingTemplate template;

    @Autowired
    public SocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat/{chatId}")
    public void send(@DestinationVariable String chatId, Message message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        OutputMessage msg = new OutputMessage(message.getFrom(), message.getText(), time);
        this.template.convertAndSend("/draft/chatRoom/"+chatId, msg);
    }
}
