package com.jtbank.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jtbank.backend.constant.TransactionMode;
import com.jtbank.backend.model.helper.Auditing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionMode mode;
    private LocalDateTime timestamp;
    @CreatedDate
    private LocalDate createdDate;
    @LastModifiedDate
    private LocalDate modifiedDate;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_number", referencedColumnName = "accountNumber")
    @JsonBackReference
    private Account account;
    private long receiverAccountNumber;
}
