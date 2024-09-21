package com.giansiccardi.AppChat.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.giansiccardi.AppChat.enums.Authoriry;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Full name cannot be empty")
    private String fullName;
    @NotEmpty(message = "Phone number cannot be empty")
    private String phoneNumber;
    private String profile_picture;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Authoriry authoriry=Authoriry.USER;


}
