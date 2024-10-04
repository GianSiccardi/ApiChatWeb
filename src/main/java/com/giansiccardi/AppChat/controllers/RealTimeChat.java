package com.giansiccardi.AppChat.controllers;

import com.giansiccardi.AppChat.models.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class RealTimeChat {


    private SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/message")
    @SendTo("/group/public")
    public Message reciveMeSagge(@Payload Message message){

        simpMessagingTemplate.convertAndSend("/group"+message.getChat().getId().toString(),message);
        return message;
    }
}
