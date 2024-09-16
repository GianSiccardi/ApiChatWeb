package com.giansiccardi.AppChat.repository;


import com.giansiccardi.AppChat.models.Chat;
import com.giansiccardi.AppChat.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepositroy extends JpaRepository<Chat,Long> {


    @Query("SELECT c FROM Chat c JOIN c.customers u WHERE u.id = :customerId")
    public List<Chat> findChatByCustomerId(@Param("customerId") Long customerId);



    @Query("SELECT c FROM Chat c WHERE c.isGroup = false AND :customer MEMBER OF c.customers AND :reqCustomer MEMBER OF c.customers")
    public Chat findSingleChatByCustomerIds(@Param("customer") Customer customer, @Param("reqCustomer") Customer reqCustomer);

}
