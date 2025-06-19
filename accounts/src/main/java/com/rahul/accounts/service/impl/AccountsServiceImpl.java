package com.rahul.accounts.service.impl;

import com.rahul.accounts.constants.AccountsConstants;
import com.rahul.accounts.dto.AccountsDto;
import com.rahul.accounts.dto.CustomerDto;
import com.rahul.accounts.entity.Accounts;
import com.rahul.accounts.entity.Customer;
import com.rahul.accounts.exception.CustomerAlreadyExistException;
import com.rahul.accounts.exception.ResourceNotFoundException;
import com.rahul.accounts.mapper.AccountsMapper;
import com.rahul.accounts.mapper.CustomerMapper;
import com.rahul.accounts.repo.AccountsRepo;
import com.rahul.accounts.repo.CustomerRepo;
import com.rahul.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepo accountsRepo;

    private CustomerRepo customerRepo;

    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        Optional<Customer> optionalCustomer = customerRepo.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent())
            throw new CustomerAlreadyExistException("Customer is already registered with given mobile number " + customerDto.getMobileNumber());

        Customer savedCustomer = customerRepo.save(customer);
        accountsRepo.save(createNewAccount(savedCustomer));

    }

    private Accounts createNewAccount(Customer customer) {

        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 100000000L + new Random().nextLong(90000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;

    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        return customerDto;

    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated = false;

        AccountsDto accountsDto = customerDto.getAccountsDto();

        if (accountsDto != null) {

            Accounts accounts = accountsRepo.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "Account Number", accountsDto.getAccountNumber().toString())
            );

            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepo.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepo.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
            );

            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepo.save(customer);

            isUpdated = true;

        }

        return isUpdated;

    }

    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        accountsRepo.deleteByCustomerId(customer.getCustomerId());
        customerRepo.deleteById(customer.getCustomerId());

        return true;

    }

}
