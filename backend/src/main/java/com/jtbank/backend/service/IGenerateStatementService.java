package com.jtbank.backend.service;

import com.jtbank.backend.model.Transaction;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IGenerateStatementService {
    List<Transaction> generateStatements(long accountNumber, String startDate, String endDate);

    void generateStatementAndSendAttachment(long accountNumber, String startDate, String endDate) throws MessagingException, UnsupportedEncodingException;
}
