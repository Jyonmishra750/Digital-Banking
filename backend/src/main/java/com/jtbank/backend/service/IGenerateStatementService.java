package com.jtbank.backend.service;

import com.jtbank.backend.model.Transaction;

import java.util.List;

public interface IGenerateStatementService {
    List<Transaction> generateStatements(long accountNumber, String startDate, String endDate);
}
