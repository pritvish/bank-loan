package com.bank.loanapp.service;

import com.bank.loanapp.entity.Customer;
import com.bank.loanapp.entity.Loan;
import com.bank.loanapp.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    final String HOME_LOAN = "HomeLoan";

    public List<Customer> getCustomersByLoanType(String loanType) {
        List<Customer> customers = customerRepository.getCustomersByLoanType(loanType);
        List<Customer> updatedCustomers = new ArrayList<>();
        if (!customers.isEmpty()) {
//            customers.stream().forEach(customer -> customer.setEmi(Math.ceil(customer.getLoan().getLoanAmount() +
//                    ((customer.getLoan().getLoanAmount() * customer.getLoan().getTerm() * customer.getLoan().getInterestRate())
//                            / 100) / (customer.getLoan().getTerm() * 12))));
            for (Customer c : customers) {
                c.setEmi(Math.ceil(c.getLoan().getLoanAmount() +
                        ((c.getLoan().getLoanAmount() * c.getLoan().getTerm() * c.getLoan().getInterestRate())
                                / 100) / (c.getLoan().getTerm() * 12)));
                updatedCustomers.add(c);
            }
        } else {
            updatedCustomers = Collections.emptyList();
        }

        return updatedCustomers;
    }

    public Integer checkLoanAllotment(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            if (customer.get().getLoan() != null) {
                return customer.get().getLoan().getLoanId();
            } else {
                return 0;
            }
        } else {
             return -1;
        }

    }

    public Integer sanctionLoan(Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findById(customer.getCustomerId());
        Loan loan = null;
        if (existingCustomer.isPresent()) {
            loan = customer.getLoan();
            if (customer.getLoan().getLoanType().equals(HOME_LOAN)) {
                loan.setInterestRate(13.0);
                loan.setTerm(15);
                existingCustomer.get().setLoan(loan);
                Customer savedCustomer = customerRepository.save(existingCustomer.get());
                return savedCustomer.getLoan().getLoanId();
            } else {
                loan.setInterestRate(9.0);
                loan.setTerm(5);
                existingCustomer.get().setLoan(loan);
                Customer savedCustomer = customerRepository.save(existingCustomer.get());
                return savedCustomer.getLoan().getLoanId();
            }
        } else {
            return 0;
        }

    }

    public Integer addCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer.getCustomerId();
    }


}
