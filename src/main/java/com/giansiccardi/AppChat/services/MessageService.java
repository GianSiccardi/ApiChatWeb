package com.giansiccardi.AppChat.services;


import com.giansiccardi.AppChat.dtos.SendMessageRequest;
import com.giansiccardi.AppChat.models.Chat;
import com.giansiccardi.AppChat.models.Customer;
import com.giansiccardi.AppChat.models.Message;
import com.giansiccardi.AppChat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final CustomerService customerService;
    private final ChatService chatService;

public Message sendMessage(SendMessageRequest sendMessageRequest) throws Exception {
    Customer customer=customerService.findCustomerById(sendMessageRequest.customerId());
    Chat chat=chatService.findByChatByID(sendMessageRequest.chatId());

    Message message= new Message();
    message.setChat(chat);
    message.setCustomer(customer);
    message.setContent(sendMessageRequest.content());
    message.setTimestamp(LocalDateTime.now());

    return messageRepository.save(message);
}

public List<Message>getChatMessages(Long id ,Customer reqCustomer) throws Exception {

    Chat chat=chatService.findByChatByID(id);
    if(!chat.getCustomers().contains(reqCustomer)){
    throw new RuntimeException("you are no releted to this chat ");
    }

    List<Message>messages=messageRepository.findByChatId(chat.getId());
    return messages;
}

public Message findMessageById(Long messageId){
    Optional<Message>otp=messageRepository.findById(messageId);

    if(otp.isPresent()){
        return otp.get();
    }
    throw new RuntimeException("messat not found");
}

public void deleteMessage(Long messageId, Customer reqCustomer){
    Message message=findMessageById(messageId);
   if(message.getCustomer().getId().equals(reqCustomer.getId())){

       messageRepository.deleteById(messageId);
   }
    throw new RuntimeException("you cant deleter this message ");

}



}
