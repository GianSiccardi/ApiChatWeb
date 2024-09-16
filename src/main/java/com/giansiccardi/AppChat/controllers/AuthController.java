package com.giansiccardi.AppChat.controllers;


import com.giansiccardi.AppChat.config.utils.JwtProvider;
import com.giansiccardi.AppChat.dtos.AuthResponse;
import com.giansiccardi.AppChat.dtos.LoginRequest;
import com.giansiccardi.AppChat.models.Customer;
import com.giansiccardi.AppChat.services.CustomerService;
import com.giansiccardi.AppChat.services.CustomerUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;

    private final CustomerUserDetailsService customerUserDetailsService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse>registerCustomer(@RequestBody Customer customer) throws Exception {
        Customer customerRegister=customerService.registerCustomer(customer);


        Authentication authentication= new UsernamePasswordAuthenticationToken(
                customer.getEmail(),
                customer.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt= JwtProvider.generateToken(authentication);



        AuthResponse authResponse = new AuthResponse(jwt,"Registro EXISTOSO" ,true);

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login(@RequestBody LoginRequest loginRequest) {

        try {
            String email = loginRequest.email();
            String password = loginRequest.password();

            Authentication authentication = customerService.authentication(email, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = JwtProvider.generateToken(authentication);

            AuthResponse authResponse = new AuthResponse(jwt, "login EXISTOSO", true);


            return ResponseEntity.status(HttpStatus.ACCEPTED).body(authResponse);
        } catch (BadCredentialsException e) {
            AuthResponse authResponse = new AuthResponse(null, "Email o contraseña incorrectos", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse);
        } catch (UsernameNotFoundException e) {
            // Si el email no existe en la base de datos
            AuthResponse authResponse = new AuthResponse(null, "Usuario no encontrado", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(authResponse);
        }


    }
}
