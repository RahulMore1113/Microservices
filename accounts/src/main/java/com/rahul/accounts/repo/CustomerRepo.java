package com.rahul.accounts.repo;

import com.rahul.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Optional<Customer> findByMobileNumber(String mobileNumber);

}
