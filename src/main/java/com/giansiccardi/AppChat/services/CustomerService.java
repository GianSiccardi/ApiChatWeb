package com.giansiccardi.AppChat.services;

import com.giansiccardi.AppChat.dtos.UpdateCustomerRequest;
import com.giansiccardi.AppChat.repository.CustomerReposityroy;
import com.giansiccardi.AppChat.config.utils.JwtProvider;
import com.giansiccardi.AppChat.models.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerReposityroy customerRepository;
    private final CustomerUserDetailsService customerUserDetailsService;
    private final PasswordEncoder passwordEncoder;


    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*public void encriptar(){
        List<Customer>usuarios=customerRepository.findAll();

        for(Customer customer : usuarios){
            String password=customer.getPassword();
            String passwordEncryp=passwordEncoder.encode(password);
            customer.setPassword(passwordEncryp);
            customerRepository.save(customer);
        }
    }*/



    public Customer registerCustomer (Customer customer) throws Exception {



       Customer emailExist= customerRepository.findByEmail(customer.getEmail());
       if(emailExist!=null){
           throw new Exception("Email ocupado");
       }

       customer.setPassword(passwordEncoder.encode(customer.getPassword()));


       return customerRepository.save(customer);
   }

    public Authentication authentication(String email, String password){
        UserDetails userDetails=customerUserDetailsService.loadUserByUsername(email);

        if(userDetails==null){
            throw new BadCredentialsException("Email invaldio");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
    }


    public Customer findCustomerById(Long id){
        Customer customer=customerRepository.findById(id).orElseThrow();
        return customer;

    }


 /*   public Customer findCustomerByEmail(String email){
        Optional<Customer>customer= Optional.ofNullable(customerRepository.findByEmail(email));

   if(customer.isPresent()){
       return customer.get();
   } else {
       throw new UsernameNotFoundException("Customer not found with email: " + email);
   }

    }*/

    public Customer findCustomerByToken(String jwt) throws Exception {
        String email= JwtProvider.getEmailFromToken(jwt);
        Customer customer=customerRepository.findByEmail(email);

        if (customer == null) {
            throw new Exception("usuario no encontrado");
        }

        return customer;
    }

    public Customer updateCustomer(Long id , UpdateCustomerRequest req){
        Customer customer=customerRepository.findById(id).orElseThrow();
        if(req.fullName()!=null){
            customer.setFullName(req.fullName());
        }
        if(req.profile_picture()!=null){
            customer.setProfile_picture(req.profile_picture());
        }
        if(req.phoNumber()!=null){
            customer.setPhoneNumber(req.phoNumber());
        }

        return customer;

    }

    public List<Customer>searchCustomer(String query){
        List<Customer>customers=customerRepository.searchCustomer(query);

        return customers;
    }
}
