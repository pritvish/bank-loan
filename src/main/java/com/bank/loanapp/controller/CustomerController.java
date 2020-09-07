package com.bank.loanapp.controller;

import com.bank.loanapp.entity.Customer;
import com.bank.loanapp.entity.Loan;
import com.bank.loanapp.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping("/customer")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        Integer customerId = customerService.addCustomer(customer);
        logger.info("Added a new customer with customerId: "+customerId);
        return new ResponseEntity<String>("Added a new customer with customerId: "+customerId, HttpStatus.CREATED);
    }

    @GetMapping("/loans/{loanType}")
    public ResponseEntity<List<Customer>> getCustomersByLoanType(@PathVariable String loanType) throws Exception {
        List<Customer> customersByLoanType = customerService.getCustomersByLoanType(loanType);
        if (!customersByLoanType.isEmpty()) {
            logger.info("Customers retrieved for the given loan type");
            return new ResponseEntity<List<Customer>>(customersByLoanType, HttpStatus.OK);
        } else {
            throw new Exception("Service.NO_LOAN_FOUND");
        }
    }

    @GetMapping("/status/{customerId}")
    public ResponseEntity<String> checkLoanAllotment(@PathVariable Integer customerId) {
        Integer loanId = customerService.checkLoanAllotment(customerId);
        if (loanId.equals(0)){
            logger.info("Customer does not has any loan");
            return new ResponseEntity<>("Customer does not has any loan", HttpStatus.OK);
        } else if (loanId.equals(-1)) {
            logger.info("Check customer id / not a valid customer");
            return new ResponseEntity<>("Check customer id / not a valid customer", HttpStatus.OK);
        } else {
            logger.info("Customer has loan bearing load id: "+loanId);
            return new ResponseEntity<>("Customer has loan bearing load id: "+loanId, HttpStatus.OK);
        }
    }

    @PostMapping("/loans/loan")
    public ResponseEntity<String> sanctionLoan(Customer customer) {
        Integer loanId = customerService.sanctionLoan(customer);
        if (!loanId.equals(0)) {
            logger.info("Loan created with loan: "+loanId+" for customer with customer id: "+customer.getCustomerId());
            return new ResponseEntity<>("Loan created with loan: "+loanId+" for customer with customer id: "+customer.getCustomerId(),HttpStatus.CREATED);
        } else {
            logger.info("Customer not present in database");
            return new ResponseEntity<>("Customer not present in database ",HttpStatus.OK);
        }
    }

}
