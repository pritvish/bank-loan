package com.bank.loanapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer customerId;
    private String customerName;
    private Long mobile;
    private Double emi;
    @OneToOne(targetEntity = Loan.class, cascade = CascadeType.ALL)
    private Loan loan;
}
