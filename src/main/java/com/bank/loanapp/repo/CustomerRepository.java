package com.bank.loanapp.repo;

import com.bank.loanapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Query(nativeQuery = true,value = "select * from loan.customer c join loan.loan l on l.loan_id=c.loan_loan_id where l.loan_type = ?1")
    List<Customer> getCustomersByLoanType(String loanType);

}
