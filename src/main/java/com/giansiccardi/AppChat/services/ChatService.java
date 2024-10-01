package com.giansiccardi.AppChat.services;


import com.giansiccardi.AppChat.dtos.GroupChatRequest;
import com.giansiccardi.AppChat.models.Chat;
import com.giansiccardi.AppChat.models.Customer;
import com.giansiccardi.AppChat.repository.ChatRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepositroy chatRepositroy;
    private final CustomerService customerService;

    public Chat creatChat(Customer reqCustomer, Long customerId2){
       Customer customer=customerService.findCustomerById(customerId2);
       Chat isChatExit=chatRepositroy.findSingleChatByCustomerIds(customer,reqCustomer);

       if(isChatExit!=null){
           return isChatExit;
       }
       Chat chat= new Chat();
       chat.setCreatedBy(reqCustomer);
       chat.getCustomers().add(customer);
       chat.getCustomers().add(reqCustomer);
       chat.setGroup(false);
        return chatRepositroy.save(chat);
    }

    public Chat findByChatByID(Long chatId) throws Exception {
        Optional<Chat> chat= Optional.of(chatRepositroy.findById(chatId).orElseThrow());
        if(chat.isPresent()){
            return  chat.get();
        }
        throw new Exception("Chat no encontrado con el id " + chat.get().getId());


    }

   public List<Chat> findAllChatByCustomerId(Long id ){
        Customer customer=customerService.findCustomerById(id);

        List<Chat>chats=chatRepositroy.findChatByCustomerId(customer.getId());

        return chats;

   }

   public Chat createGroup(GroupChatRequest req,Customer reqCustomer){
        Chat group= new Chat();
        group.setGroup(true);
        group.setChat_image(req.chat_image());
        group.setChat_name(req.chat_name());
        group.setCreatedBy(reqCustomer);
        group.getAdmins().add(reqCustomer);
        for(Long customerId:req.customersIds()){
            Customer customer=customerService.findCustomerById(customerId);
            group.getCustomers().add(customer);
        }

        return group;

   }

    public Chat addCustomerToGroup(Long id, Long chatId, Customer reqCustomer) {
        Optional<Chat> opt = chatRepositroy.findById(chatId);
        if (opt.isEmpty()) {
            throw new RuntimeException("Chat not found with ID: " + chatId);
        }

        Chat chat = opt.get();
        Customer customer = customerService.findCustomerById(id);


        if (!chat.getAdmins().contains(reqCustomer)) {
            throw new RuntimeException("You are not an admin and cannot add users to the chat.");
        }

        chat.getCustomers().add(customer);
        chatRepositroy.save(chat);

        return chat;
    }


    public Chat renameGroup (Long chatId, String name , Customer reqCustomer){
        Optional<Chat> opt = chatRepositroy.findById(chatId);
      if(opt.isPresent()){
          Chat chat=opt.get();
          if(chat.getCustomers().contains(reqCustomer)){
              chat.setChat_name(name);
              return chatRepositroy.save(chat);
          }
          throw new RuntimeException("You are not member of this group");
      }
        throw new RuntimeException("Chat not found with ID: " + chatId);
    }

    public Chat removeFromGrup(Long chatId, Customer reqCustomer ,Long customerId){
        Optional<Chat> opt = chatRepositroy.findById(chatId);

        Customer customer=customerService.findCustomerById(customerId);

        if(opt.isPresent()){
            Chat chat=opt.get();
            if(chat.getAdmins().contains(reqCustomer) ){
               chat.getCustomers().remove(customer);
               return chatRepositroy.save(chat);
            } else if (chat.getCustomers().contains(reqCustomer)) {
               if(customer.getId().equals(reqCustomer.getId())){
                   chat.getCustomers().remove(customer);
                   return chatRepositroy.save(chat);
               }
            }
            throw new RuntimeException("You are not member of this group");

        }
        throw new RuntimeException("Chat not found with ID: " + chatId);

    }


    public void deleteChat (Long chatId ,Long customerId){
        Optional<Chat> opt = chatRepositroy.findById(chatId);

        if(opt.isPresent()){
            Chat chat=opt.get();
            chatRepositroy.deleteById(chat.getId());
        }
    }

}
