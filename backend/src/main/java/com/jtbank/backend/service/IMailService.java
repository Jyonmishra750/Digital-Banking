package com.jtbank.backend.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface IMailService {
    void sendOtp(String name, String email, String otp) throws UnsupportedEncodingException;

    void sendRegisterSuccessfulMessage(String name, String email) throws UnsupportedEncodingException;

    void sendLoginSuccessfulMessage(String name, String email) throws UnsupportedEncodingException;

    void sendDepositSuccessfulMessage(String name, String email, double amount, double updatedBalance, Long accountNumber) throws UnsupportedEncodingException;

    void sendWithdrawalSuccessfulMessage(String name, String email, double amount, double updatedBalance, Long accountNumber) throws UnsupportedEncodingException;

    void sendTransferSuccessfulMessage(String name, String email, double amount, double updatedBalance, Long accountNumber, Long receiverAccountNumber) throws UnsupportedEncodingException;

    void sendMoneyReceivedSuccessfulMessage(String name, String email, long accountNumber, long sender, double balance) throws UnsupportedEncodingException;

    void sendProfileUpdateSuccessfulMessage(String name, String email) throws UnsupportedEncodingException;

    void sendLoanSuccessfulMessage(String name, String email) throws UnsupportedEncodingException;

    void sendStatementPdf(String name, String email, String attachment) throws UnsupportedEncodingException, MessagingException;
}
