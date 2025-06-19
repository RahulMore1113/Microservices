package com.rahul.loans.service.impl;

import com.rahul.loans.constants.LoansConstants;
import com.rahul.loans.dto.LoansDto;
import com.rahul.loans.entity.Loans;
import com.rahul.loans.exception.LoanAlreadyExistsException;
import com.rahul.loans.exception.ResourceNotFoundException;
import com.rahul.loans.mapper.LoansMapper;
import com.rahul.loans.repo.LoansRepo;
import com.rahul.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepo loansRepo;

    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loans> optionalLoans = loansRepo.findByMobileNumber(mobileNumber);

        if (optionalLoans.isPresent())
            throw new LoanAlreadyExistsException("Loan already registered with given mobile number " + mobileNumber);

        loansRepo.save(createNewLoan(mobileNumber));

    }

    private Loans createNewLoan(String mobileNumber) {

        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

        return newLoan;

    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {

        Loans loans = loansRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );

        return LoansMapper.mapToLoansDto(loans, new LoansDto());

    }

    @Override
    public Boolean updateLoan(LoansDto loanDto) {

        Loans loans = loansRepo.findByLoanNumber(loanDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "loanNumber", loanDto.getLoanNumber())
        );

        LoansMapper.mapToLoans(loanDto, loans);
        loansRepo.save(loans);

        return true;

    }

    @Override
    public Boolean deleteLoan(String mobileNumber) {

        Loans loans = loansRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );

        loansRepo.deleteById(loans.getLoanId());

        return true;

    }

}
