package com.jtbank.backend.controller;

import com.jtbank.backend.constant.TransactionMode;
import com.jtbank.backend.dto.DatatableDTO;
import com.jtbank.backend.mapper.TransactionMapper;
import com.jtbank.backend.model.Transaction;
import com.jtbank.backend.service.IGenerateStatementService;
import com.jtbank.backend.service.ITransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class TransactionController {
    private final ITransactionService service;

    @GetMapping("/credit")
    public DatatableDTO creditedTransactions(@RequestAttribute long accountNumber,
                                             @RequestParam(required = false, defaultValue = "1") int pageNumber,
                                             @RequestParam(required = false, defaultValue = "10") int pageSize) {
        var results = service.getCreditedTransactions(accountNumber, pageNumber, pageSize);
        var transactions = results.stream().map(TransactionMapper::dtoMapper).toList();

        var totalRecord = service.countRecord(TransactionMode.CREDIT, accountNumber);

        return new DatatableDTO(totalRecord, pageNumber, pageSize, transactions);
    }

    @GetMapping("/debit")
    public DatatableDTO debitedTransactions(@RequestAttribute long accountNumber,
                                            @RequestParam(required = false, defaultValue = "1") int pageNumber,
                                            @RequestParam(required = false, defaultValue = "10") int pageSize)
            throws Exception {
        var results = service.getDebitedTransactions(accountNumber, pageNumber, pageSize);
        var transactions = results.thenApplyAsync(result -> result.stream().map(TransactionMapper::dtoMapper).toList());

        var totalRecord = service.countRecord1(TransactionMode.DEBIT, accountNumber);

        CompletableFuture.allOf(results, transactions, totalRecord).join();

        return new DatatableDTO(totalRecord.get(), pageNumber, pageSize, transactions.get());
    }

    @GetMapping("/transfer")
    public DatatableDTO transferredTransactions(@RequestAttribute long accountNumber,
                                                @RequestParam(required = false, defaultValue = "1") int pageNumber,
                                                @RequestParam(required = false, defaultValue = "10") int pageSize) {
        var results = service.getTransferredTransactions(accountNumber, pageNumber, pageSize);
        var transactions = results.stream().map(TransactionMapper::dtoMapper).toList();

        var totalRecord = service.countRecord(TransactionMode.TRANSFER, accountNumber);

        return new DatatableDTO(totalRecord, pageNumber, pageSize, transactions);
    }
}
