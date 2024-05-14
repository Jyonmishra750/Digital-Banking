package com.jtbank.backend.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jtbank.backend.model.Transaction;
import com.jtbank.backend.repository.TransactionRepository;
import com.jtbank.backend.service.IAccountService;
import com.jtbank.backend.service.IGenerateStatementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerateStatementServiceImpl implements IGenerateStatementService {

    private final TransactionRepository transactionRepository;
    private final IAccountService service;
    private static final String FILE = "C:\\Users\\HP\\Downloads\\";

    @Override
    public List<Transaction> generateStatements(long accountNumber, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate startDateForStatement = LocalDate.parse(startDate);
        LocalDate endDateForStatement = LocalDate.parse(endDate);
        String formattedStartDate = startDateForStatement.format(formatter1);
        String formattedEndDate = endDateForStatement.format(formatter1);
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter).plusDays(1);

        List<Transaction> transactionList = transactionRepository.findAll()
                .stream()
                .filter(transaction -> transaction.getAccount().getAccountNumber() == accountNumber)
                .filter(transaction -> {
                    LocalDate transactionDate = transaction.getCreatedDate();
                    return !transactionDate.isBefore(start) && !transactionDate.isAfter(end);
                })
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .toList();
        String fileName = "MyStatement_"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd_HH-mm-ss"))
                + ".pdf";
        String filePath = FILE + fileName;
        var account = service.getAccount(accountNumber);

        String mob = account.getContactNumber();
        String acNumberStr = String.valueOf(accountNumber);
        String lastFourDigitsMobile = mob.substring(mob.length() - 4);
        String lastFourDigitsAccount = acNumberStr.substring(acNumberStr.length() - 4);

        String pdfPassword = lastFourDigitsMobile + lastFourDigitsAccount;

        Rectangle statementSize = new Rectangle(PageSize.A4);
        Document document = new Document(statementSize);
        log.info("Setting the size of Document");
        try {
            OutputStream outputStream = new FileOutputStream(filePath);
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            writer.setEncryption(
                    pdfPassword.getBytes(),
                    pdfPassword.getBytes(),
                    PdfWriter.ALLOW_PRINTING,
                    PdfWriter.ENCRYPTION_AES_128);
            document.open();
        } catch (FileNotFoundException | DocumentException e) {
            throw new RuntimeException(e);
        }

        Image image;
        try {
            String imgPath = "E:\\PROJECTS\\Digital Banking Application\\backend\\profile-pictures\\icon.png";
            image = Image.getInstance(imgPath);
        } catch (BadElementException | IOException e) {
            throw new RuntimeException(e);
        }
        image.scaleToFit(70, 70);
        PdfPTable bankInfoTable = new PdfPTable(2);
        PdfPCell logo = new PdfPCell(image);
        logo.setBorder(0);
        PdfPCell bankName = new PdfPCell(new Phrase("Digital-Banking", FontFactory.getFont(FontFactory.HELVETICA, 45, Font.BOLD)));
        bankName.setBorder(0);
        bankName.setHorizontalAlignment(bankName.ALIGN_CENTER);

        bankInfoTable.addCell(logo);
        bankInfoTable.addCell(bankName);

        float[] columnWidths = { 1f, 4f }; // Logo column width, Bank name column width
        try {
            bankInfoTable.setWidths(columnWidths);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        PdfPTable customerInfoTable = new PdfPTable(1);
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);

        Phrase datePhrase = new Phrase();
        datePhrase.add(new Chunk("Statement Generated on: ", boldFont));
        datePhrase.add(new Chunk(LocalDate.now().format(formatter1)));

        Phrase namePhrase = new Phrase();
        namePhrase.add(new Chunk("Name: ", boldFont));
        namePhrase.add(new Chunk(account.getAccountHolderName()));

        Phrase addressPhrase = new Phrase();
        addressPhrase.add(new Chunk("Address: ", boldFont));
        addressPhrase.add(new Chunk(account.getAddress().getCity()
                + ", "+account.getAddress().getState()
                + ", "+account.getAddress().getCountry()
                + ", "+account.getAddress().getZipcode()));

        Phrase emailPhrase = new Phrase();
        emailPhrase.add(new Chunk("Email: ", boldFont));
        emailPhrase.add(new Chunk(account.getCredential().getAccountEmail()));

        Phrase accountNumberPhrase = new Phrase();
        accountNumberPhrase.add(new Chunk("Acc. Number: ", boldFont));
        accountNumberPhrase.add(new Chunk(String.valueOf(account.getAccountNumber())));

        Phrase accountTypePhrase = new Phrase();
        accountTypePhrase.add(new Chunk("Acc. Type: ", boldFont));
        accountTypePhrase.add(new Chunk(String.valueOf(account.getAccountType())));

        Phrase accountStatusPhrase = new Phrase();
        accountStatusPhrase.add(new Chunk("Acc. Status: ", boldFont));
        accountStatusPhrase.add(new Chunk(String.valueOf(account.getStatus())));

        Phrase balancePhrase = new Phrase();
        balancePhrase.add(new Chunk("Balance: ", boldFont));
        balancePhrase.add(new Chunk(String.format("%.2f", account.getAccountBalance())));
        balancePhrase.add(new Chunk(" (as on "+LocalDate.now().format(formatter1)+")"));

        Phrase statementRangePhrase = new Phrase();
        statementRangePhrase.add(new Chunk("ACCOUNT STATEMENT FROM "));
        statementRangePhrase.add(new Chunk(formattedStartDate, boldFont));
        statementRangePhrase.add(new Chunk(" TO "));
        statementRangePhrase.add(new Chunk(formattedEndDate, boldFont));

        PdfPCell statementDate = new PdfPCell(new Phrase(datePhrase));
        statementDate.setPaddingTop(20);
        statementDate.setBorder(0);
        PdfPCell name = new PdfPCell(namePhrase);
        name.setBorder(0);
        PdfPCell address = new PdfPCell(new Phrase(addressPhrase));
        address.setBorder(0);
        PdfPCell email = new PdfPCell(new Phrase(emailPhrase));
        email.setBorder(0);
        PdfPCell accountNo = new PdfPCell(new Phrase(accountNumberPhrase));
        accountNo.setBorder(0);
        PdfPCell type = new PdfPCell(new Phrase(accountTypePhrase));
        type.setBorder(0);
        PdfPCell status = new PdfPCell(new Phrase(accountStatusPhrase));
        status.setBorder(0);
        PdfPCell totalBalance = new PdfPCell(new Phrase(balancePhrase));
        totalBalance.setBorder(0);
        totalBalance.setPaddingBottom(10);
        PdfPCell space1 = new PdfPCell();
        PdfPCell statement = new PdfPCell(new Phrase(statementRangePhrase));
        statement.setBorder(0);
        statement.setPaddingTop(10);
        statement.setPaddingBottom(10);

        customerInfoTable.addCell(statementDate);
        customerInfoTable.addCell(name);
        customerInfoTable.addCell(email);
        customerInfoTable.addCell(address);
        customerInfoTable.addCell(accountNo);
        customerInfoTable.addCell(type);
        customerInfoTable.addCell(status);
        customerInfoTable.addCell(totalBalance);
        customerInfoTable.addCell(space1);
        customerInfoTable.addCell(statement);


        PdfPTable transactionTable = new PdfPTable(4);
        PdfPCell txnDate = new PdfPCell(new Phrase("DATE",
                FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
        txnDate.setBorderWidth(1);
        txnDate.setBorderColor(BaseColor.BLACK);
        txnDate.setBackgroundColor(BaseColor.BLUE);
        txnDate.setHorizontalAlignment(txnDate.ALIGN_CENTER);

        PdfPCell txnId = new PdfPCell(new Phrase("Txn. ID",
                FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
        txnId.setBorderWidth(1);
        txnId.setBorderColor(BaseColor.BLACK);
        txnId.setBackgroundColor(BaseColor.BLUE);
        txnId.setHorizontalAlignment(txnId.ALIGN_CENTER);

        PdfPCell txnAmount = new PdfPCell(new Phrase("AMOUNT",
                FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
        txnAmount.setBorderWidth(1);
        txnAmount.setBorderColor(BaseColor.BLACK);
        txnAmount.setBackgroundColor(BaseColor.BLUE);
        txnAmount.setHorizontalAlignment(txnAmount.ALIGN_CENTER);

        PdfPCell txnType = new PdfPCell(new Phrase("TYPE",
                FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
        txnType.setBorderWidth(1);
        txnType.setBorderColor(BaseColor.BLACK);
        txnType.setBackgroundColor(BaseColor.BLUE);
        txnType.setHorizontalAlignment(txnType.ALIGN_CENTER);


        transactionTable.addCell(txnDate);
        transactionTable.addCell(txnId);
        transactionTable.addCell(txnAmount);
        transactionTable.addCell(txnType);

        for (Transaction transaction : transactionList) {
            transactionTable.addCell(new Phrase(transaction.getCreatedDate().format(formatter1)));
            transactionTable.addCell(new Phrase(transaction.getTransactionId()));
            transactionTable.addCell(new Phrase(String.format("%.2f", transaction.getAmount())));
            transactionTable.addCell(new Phrase(String.valueOf(transaction.getMode())));
        }

        try {
            document.add(bankInfoTable);
            document.add(customerInfoTable);
            document.add(transactionTable);
            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }



        return transactionList;
    }
}
