package com.rahul.loans.service;

import com.rahul.loans.dto.LoansDto;

public interface ILoansService {

    void createLoan(String mobileNumber);

    LoansDto fetchLoan(String mobileNumber);

    Boolean updateLoan(LoansDto loanDto);

    Boolean deleteLoan(String mobileNumber);

}
