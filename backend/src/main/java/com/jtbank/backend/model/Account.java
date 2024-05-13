package com.jtbank.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jtbank.backend.constant.AccountStatus;
import com.jtbank.backend.constant.AccountType;
import com.jtbank.backend.model.helper.Auditing;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "customers")
public class Account extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountSlNo;
    @Column(unique = true)
    private long accountNumber;
    @Column(name = "customer_name")
    private String accountHolderName;
    @Column(nullable = false)
    private String contactNumber;
    private String profilePicture;
    @Lob
    private String aboutCustomer;
    private double accountBalance;
    @Embedded
    private Credential credential;
    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transaction> transactions;
    @Column(name = "Otp")
    private String otp;
    @Column(name = "OtpCreationTime")
    private LocalDateTime otpCreationTime;
    @Column(name = "OtpExpirationTime")
    private LocalDateTime otpExpirationTime;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}
