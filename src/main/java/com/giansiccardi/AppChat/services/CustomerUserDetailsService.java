package com.giansiccardi.AppChat.services;


import com.giansiccardi.AppChat.enums.Authoriry;
import com.giansiccardi.AppChat.repository.CustomerReposityroy;
import com.giansiccardi.AppChat.models.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

private final CustomerReposityroy customerReposityroy;

    @Override
    public UserDetails loadUserByUsername(String emial) throws UsernameNotFoundException {
       Customer user = customerReposityroy.findByEmail(emial);

       if(user==null){
           throw new UsernameNotFoundException("Usuario no encontrado");
       }

        List<GrantedAuthority> authorityList= new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(Authoriry.USER.name()));
        User userDetails= new User(user.getEmail(),user.getPassword(),authorityList);

        return userDetails;
    }



}
