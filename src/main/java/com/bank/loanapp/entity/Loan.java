package com.bank.loanapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Loan {

    @Id
    private Integer loanId;
    private Double loanAmount;
    private Double interestRate;
    private Integer term;
    private String loanType;
}
