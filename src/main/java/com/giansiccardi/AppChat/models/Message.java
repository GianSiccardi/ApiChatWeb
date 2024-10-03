package com.giansiccardi.AppChat.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference//Indica que esta propiedad no debe ser serializada; es decir, no se incluirá en la salida JSON. Esto evita la recursión infinita al serializar el objeto.Se coloca en el lado "muchos" de la relación.
    private Chat chat;


}
