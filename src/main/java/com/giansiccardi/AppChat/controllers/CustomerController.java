package com.giansiccardi.AppChat.controllers;

import com.giansiccardi.AppChat.dtos.ApiResponse;
import com.giansiccardi.AppChat.dtos.UpdateCustomerRequest;
import com.giansiccardi.AppChat.models.Customer;
import com.giansiccardi.AppChat.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @GetMapping
    public ResponseEntity<?>getCustomerProfile(@RequestHeader("Authorization")String token) throws Exception {

        Customer customer=customerService.findCustomerByToken(token);

        if(customer==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el usuario");
        }
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }


    @GetMapping("/{query}")
    public ResponseEntity<?>searchCustomer(@PathVariable String q){
        List<Customer>customers=customerService.searchCustomer(q);


        return new ResponseEntity<List<Customer>>(customers,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?>upadteCustomerProfile(@RequestBody UpdateCustomerRequest request , @RequestHeader("Authorization")String token) throws Exception {
        Customer customer= customerService.findCustomerByToken(token);

        customerService.updateCustomer(customer.getId(),request);
        ApiResponse response = new ApiResponse("Usuario actualizado con extio",true);
       return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }
}
