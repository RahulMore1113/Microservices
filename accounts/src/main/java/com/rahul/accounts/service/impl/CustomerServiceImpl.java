package com.rahul.accounts.service.impl;

import com.rahul.accounts.dto.AccountsDto;
import com.rahul.accounts.dto.CardsDto;
import com.rahul.accounts.dto.CustomerDetailsDto;
import com.rahul.accounts.dto.LoansDto;
import com.rahul.accounts.entity.Accounts;
import com.rahul.accounts.entity.Customer;
import com.rahul.accounts.exception.ResourceNotFoundException;
import com.rahul.accounts.mapper.AccountsMapper;
import com.rahul.accounts.mapper.CustomerMapper;
import com.rahul.accounts.repo.AccountsRepo;
import com.rahul.accounts.repo.CustomerRepo;
import com.rahul.accounts.service.ICustomerService;
import com.rahul.accounts.service.client.CardsFeignClient;
import com.rahul.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepo accountsRepo;

    private CustomerRepo customerRepo;

    private CardsFeignClient cardsFeignClient;

    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {

        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;

    }

}
