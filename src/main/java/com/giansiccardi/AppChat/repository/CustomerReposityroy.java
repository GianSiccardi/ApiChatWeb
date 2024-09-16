package com.giansiccardi.AppChat.repository;

import com.giansiccardi.AppChat.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerReposityroy extends JpaRepository<Customer,Long> {

    public Customer findByEmail(String email);

    @Query("SELECT u FROM Customer u WHERE u.fullName LIKE %:query% OR u.email LIKE %:query%")
    public List<Customer> searchCustomer(@Param("query") String query);

}
