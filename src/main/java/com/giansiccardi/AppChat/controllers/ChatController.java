package com.giansiccardi.AppChat.controllers;


import com.giansiccardi.AppChat.dtos.ApiResponse;
import com.giansiccardi.AppChat.dtos.GroupChatRequest;
import com.giansiccardi.AppChat.dtos.SingleChatRequest;
import com.giansiccardi.AppChat.models.Chat;
import com.giansiccardi.AppChat.models.Customer;
import com.giansiccardi.AppChat.services.ChatService;
import com.giansiccardi.AppChat.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<?>createChat(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization") String jwt) throws Exception {


        Customer reqCustomer=customerService.findCustomerByToken(jwt);

        Chat chat=chatService.creatChat(reqCustomer,singleChatRequest.userId());

        return new ResponseEntity<Chat>(chat, HttpStatus.CREATED);

    }

    @PostMapping("/group")
    public ResponseEntity<?>createChatGroup(@RequestBody GroupChatRequest groupChatRequest, @RequestHeader("Authorization") String jwt) throws Exception {


        Customer reqCustomer=customerService.findCustomerByToken(jwt);

        Chat chat=chatService.createGroup(groupChatRequest,reqCustomer);

        return new ResponseEntity<Chat>(chat, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?>findChatById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {


         Chat chat=chatService.findByChatByID(id);
        return new ResponseEntity<Chat>(chat, HttpStatus.CREATED);

    }

    @GetMapping("/customer")
    public ResponseEntity<?>findiAllchatByCustomer( @RequestHeader("Authorization") String jwt) throws Exception {
        Customer reqCustomer=customerService.findCustomerByToken(jwt);


        List<Chat>chats=chatService.findAllChatByCustomerId(reqCustomer.getId());
        return ResponseEntity.ok(chats);
    }
    @PutMapping("/{chatId}/add/{customerId}")
    public ResponseEntity<?>addCustomerToGroup(@PathVariable Long chatId ,@PathVariable Long customerId, @RequestHeader("Authorization") String jwt) throws Exception {
        Customer reqCustomer=customerService.findCustomerByToken(jwt);


        Chat chat=chatService.addCustomerToGroup(customerId,chatId,reqCustomer);
        return new ResponseEntity<Chat>(chat,HttpStatus.OK);

    }

    @PutMapping("/{chatId}/remove/{customerId}")
    public ResponseEntity<?>removeCustomerFromGroup(@PathVariable Long chatId ,@PathVariable Long customerId, @RequestHeader("Authorization") String jwt) throws Exception {
        Customer reqCustomer=customerService.findCustomerByToken(jwt);


        Chat chat=chatService.removeFromGrup(chatId,reqCustomer,customerId);
        return ResponseEntity.status(HttpStatus.OK).body("User removed from group");

    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<?>deleteGroup(@PathVariable Long chatId , @RequestHeader("Authorization") String jwt) throws Exception {
        Customer reqCustomer=customerService.findCustomerByToken(jwt);

chatService.deleteChat(chatId,reqCustomer.getId());

        ApiResponse response= new ApiResponse("group delete",true);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }




}
