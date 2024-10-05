package com.giansiccardi.AppChat.controllers;

import com.giansiccardi.AppChat.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin("*")
@Slf4j
public class RealTimeChat {


    private SimpMessagingTemplate simpMessagingTemplate;


   /* @SendTo("/group/public")
    public Message reciveMeSagge(@Payload Message message){

        simpMessagingTemplate.convertAndSend("/group"+message.getChat().getId().toString(),message);
        return message;
    }*/

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public Message receiveSingleMessage(@Payload Message message) {
     log.info("mensaje recibio"+ message);
       // simpMessagingTemplate.convertAndSend("/topic/message" + message.getChat().getId().toString(), message);
        return message;
    }
}
