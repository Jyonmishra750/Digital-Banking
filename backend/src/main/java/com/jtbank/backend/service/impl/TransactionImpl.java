package com.jtbank.backend.service.impl;

import com.jtbank.backend.constant.TransactionMode;
import com.jtbank.backend.model.Account;
import com.jtbank.backend.model.Transaction;
import com.jtbank.backend.repository.AccountRepository;
import com.jtbank.backend.repository.TransactionRepository;
import com.jtbank.backend.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionImpl implements ITransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private static final String FILE = "C:\\Users\\HP\\Downloads\\MyBankStatement.pdf";

    @Async
    @Transactional
    @Override
    public void addTransaction(Transaction transaction, long accountNumber) {
        var account = getAccount(accountNumber);
        transaction.setAccount(account);

        var transactions = account.getTransactions();
        if (transactions == null) {
            transactions = new ArrayList<>();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String created = LocalDate.now().format(formatter);
        String modified = LocalDate.now().format(formatter);

        transaction.setCreatedDate(LocalDate.parse(created, formatter));
        transaction.setModifiedDate(LocalDate.parse(modified, formatter));
        transaction.setTimestamp(LocalDateTime.now());
        transactions.add(transaction);

        transactionRepository.save(transaction);
    }

    @Async
    @Override
    public CompletableFuture<List<Transaction>> getDebitedTransactions(long accountNumber, int pageNumber, int pageSize) {
        var sort = Sort.by("timestamp").descending();
        var page = PageRequest.of(pageNumber - 1, pageSize, sort);

        var result = transactionRepository
                .findByModeAndAccountAccountNumber(TransactionMode.DEBIT,
                        accountNumber, page);
        return CompletableFuture.completedFuture(result);
    }

    @Override
    public List<Transaction> getCreditedTransactions(long accountNumber, int pageNumber, int pageSize) {
        var sort = Sort.by("timestamp").descending();
        var page = PageRequest.of(pageNumber - 1, pageSize, sort);

        return transactionRepository
                .findByModeAndAccountAccountNumber(TransactionMode.CREDIT,
                        accountNumber, page);
    }

    @Override
    public List<Transaction> getTransferredTransactions(long accountNumber, int pageNumber, int pageSize) {
        var sort = Sort.by("timestamp").descending();
        var page = PageRequest.of(pageNumber - 1, pageSize, sort);

        return transactionRepository
                .findByModeAndAccountAccountNumber(TransactionMode.TRANSFER,
                        accountNumber, page);
    }

    @Async
    @Override
    public CompletableFuture<List<Transaction>> getLast5Transaction(long accountNumber) {
        var result = transactionRepository.findTop5ByAccountAccountNumberOrderByTimestampDesc(accountNumber);
        return CompletableFuture.completedFuture(result);
    }

    @Override
    public long countRecord(TransactionMode mode, long accountNumber) {
        return transactionRepository.countByModeAndAccountAccountNumber(mode, accountNumber);
    }

    @Async
    @Override
    public CompletableFuture<Long> countRecord1(TransactionMode mode, long accountNumber) {
        var result = transactionRepository.countByModeAndAccountAccountNumber(mode, accountNumber);
        return CompletableFuture.completedFuture(result);
    }

    private Account getAccount(long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow();
    }
}
