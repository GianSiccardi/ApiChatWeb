package com.giansiccardi.AppChat.controllers;


import com.giansiccardi.AppChat.dtos.ApiResponse;
import com.giansiccardi.AppChat.dtos.SendMessageRequest;
import com.giansiccardi.AppChat.models.Customer;
import com.giansiccardi.AppChat.models.Message;
import com.giansiccardi.AppChat.services.CustomerService;
import com.giansiccardi.AppChat.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessaController {
    private final MessageService messageService;
    private final CustomerService customerService;


    @PostMapping()
    public ResponseEntity<Message>sendMessage(@RequestBody SendMessageRequest sendMessageRequest , @RequestHeader("Authorization")String jwt) throws Exception {
        Customer customer=customerService.findCustomerByToken(jwt);
        SendMessageRequest req= new SendMessageRequest(customer.getId(),sendMessageRequest.chatId(),sendMessageRequest.content());

        Message message=messageService.sendMessage(req);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<?>getChatsMessages( @PathVariable Long chatId, @RequestHeader("Authorization")String jwt) throws Exception {
        Customer customer=customerService.findCustomerByToken(jwt);

        List<Message> messages=messageService.getChatMessages(chatId,customer);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }


    @DeleteMapping("/{messageId}")
    public ResponseEntity<?>deleteMessages( @PathVariable Long messageId, @RequestHeader("Authorization")String jwt) throws Exception {
        Customer customer=customerService.findCustomerByToken(jwt);

        messageService.deleteMessage(messageId,customer);
        ApiResponse apiResponse= new ApiResponse("message delete",true);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
