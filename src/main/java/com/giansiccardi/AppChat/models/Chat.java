package com.giansiccardi.AppChat.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chat_name;
    private String chat_image;
    @Column(name="is_group")
    private boolean isGroup;

    @ManyToMany
    private Set<Customer>admins= new HashSet<>();

    @ManyToOne
    private Customer createdBy;

    @ManyToMany
    private Set<Customer> customers= new HashSet<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messageList= new ArrayList<>();

}
