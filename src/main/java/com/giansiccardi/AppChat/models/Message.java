package com.giansiccardi.AppChat.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    private LocalDateTime timestamp;
    @ManyToOne
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;


}
