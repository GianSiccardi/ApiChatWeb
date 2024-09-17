package com.giansiccardi.AppChat.repository;


import com.giansiccardi.AppChat.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {


    @Query("SELECT m FROM Message m JOIN m.chat c WHERE c.id = :chatId")
    public List<Message>findByChatId(@Param("chatId") Long chatId);


}


