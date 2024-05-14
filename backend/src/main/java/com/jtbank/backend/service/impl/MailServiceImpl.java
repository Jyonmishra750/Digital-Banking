package com.jtbank.backend.service.impl;

import com.jtbank.backend.service.IMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements IMailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public void sendOtp(String name, String email, String otp) throws UnsupportedEncodingException {
        SimpleMailMessage message = new SimpleMailMessage();
        InternetAddress from = new InternetAddress("digitalbanking.mcaproject@gmail.com", "Digital-Banking Team");
        String subject = "OTP for Registration";
        String body = "Dear, "+name+"\n" +
                "\n" +
                "Thank you for registering with Digital-Banking. Your OTP (One Time Password) for registration is: ["+otp+
                "] \n Please enter this OTP in the form to complete your registration process. " +
                "\n\n If you did not request this OTP, please ignore this email or contact to our " +
                "customer support team at "+sender+
                "\nDigital Banking Team";
        message.setFrom(String.valueOf(from));
        message.setSubject(subject);
        message.setText(body);
        message.setTo(email);
        javaMailSender.send(message);
    }

    @Override
    public void sendRegisterSuccessfulMessage(String name, String email) throws UnsupportedEncodingException {
        SimpleMailMessage message = new SimpleMailMessage();
        InternetAddress from = new InternetAddress("digitalbanking.mcaproject@gmail.com", "Digital-Banking Team");
        String subject = "Welcome to Digital-Banking";
        String body = "Dear, "+name+"\n" +
                "\n" +
                "Thank you for choosing 'Digital-Banking' for your banking needs. We are delighted to inform you that" +
                " your online banking account has been successfully created on "+LocalDateTime.now().format(dateTimeFormatter())+"\n\n"+
                "For security reasons, we recommend that you change your password upon logging in for the " +
                "first time."+"\n\n"+
                "If you have any questions or need assistance, please do not hesitate to contact to our " +
                "customer support team at "+sender+".\n\nWelcome aboard, and thank you for banking with us!\n" +
                "\n" +
                "Best regards,\n" +
                "Digital Banking Team";
        message.setFrom(String.valueOf(from));
        message.setSubject(subject);
        message.setText(body);
        message.setTo(email);
        javaMailSender.send(message);
    }

    @Override
    public void sendLoginSuccessfulMessage(String name, String email) throws UnsupportedEncodingException {
        SimpleMailMessage message = new SimpleMailMessage();
        InternetAddress from = new InternetAddress("digitalbanking.mcaproject@gmail.com", "Digital-Banking Team");
        String subject = "Login Alert";
        String body = "Dear, "+name+"\n" +
                "\n" +
                "We are pleased to inform you that you have successfully logged in to your banking account on "+
                LocalDateTime.now().format(dateTimeFormatter())+"\n\n"+
                "If you did not perform this action or suspect any unauthorized access to your account, please contact"+
                " to our customer support team at "+sender+" immediately.\n\n"+
                "Thank you for choosing Digital-Banking.\nBest regards,\n" +
                "Digital-Banking Team";
        message.setFrom(String.valueOf(from));
        message.setSubject(subject);
        message.setText(body);
        message.setTo(email);
        javaMailSender.send(message);
    }

    @Override
    public void sendDepositSuccessfulMessage(String name, String email, double amount, double updatedBalance, Long accountNumber) throws UnsupportedEncodingException {
        SimpleMailMessage message = new SimpleMailMessage();
        InternetAddress from = new InternetAddress("digitalbanking.mcaproject@gmail.com", "Digital-Banking Team");
        String subject = "New Transaction Alert";
        String body = "Dear, "+name+"\n" +
                "\n" +
                "We are writing to confirm that the amount "+amount+" has been successfully deposited into your " +
                "account (A/c No.- "+accountNumber+") on "+ LocalDateTime.now().format(dateTimeFormatter()) +
                ". Your new balance is "+updatedBalance+".\n\n"+
                "If you did not perform this action or suspect any unauthorized access to your account, please contact" +
                " to our customer support team at "+sender+" immediately.\n\n"+
                "Best regards,\n" +
                "Digital-Banking Team";
        message.setFrom(String.valueOf(from));
        message.setSubject(subject);
        message.setText(body);
        message.setTo(email);
        javaMailSender.send(message);
    }

    @Override
    public void sendWithdrawalSuccessfulMessage(String name, String email, double amount, double updatedBalance, Long accountNumber) throws UnsupportedEncodingException {
        SimpleMailMessage message = new SimpleMailMessage();
        InternetAddress from = new InternetAddress("digitalbanking.mcaproject@gmail.com", "Digital-Banking Team");
        String subject = "New Transaction Alert";
        String body = "Dear, "+name+"\n" +
                "\n" +
                "We are writing to confirm that the withdrawal amount of "+amount+" has been successfully processed from your " +
                "account (A/c No.- "+accountNumber+") on "+ LocalDateTime.now().format(dateTimeFormatter()) +
                ". Your current balance is "+updatedBalance+".\n\n"+
                "If you did not perform this action or suspect any unauthorized access to your account, please contact" +
                " to our customer support team at "+sender+" immediately.\n\n"+
                "Thank you for choosing Digital-Banking.\nBest regards,\n" +
                "Digital-Banking Team";
        message.setFrom(String.valueOf(from));
        message.setSubject(subject);
        message.setText(body);
        message.setTo(email);
        javaMailSender.send(message);
    }

    @Override
    public void sendTransferSuccessfulMessage(String name, String email, double amount, double updatedBalance, Long accountNumber, Long receiverAccountNumber) throws UnsupportedEncodingException {
        SimpleMailMessage message = new SimpleMailMessage();
        InternetAddress from = new InternetAddress("digitalbanking.mcaproject@gmail.com", "Digital-Banking Team");
        String subject = "New Transaction Alert";
        String body = "Dear, "+name+"\n" +
                "\n" +
                "We are writing to confirm that a balance of "+amount+" has been successfully transferred from your " +
                "account (A/c No.- "+accountNumber+") on "+ LocalDateTime.now().format(dateTimeFormatter()) +
                " to A/c No.-"+receiverAccountNumber+". Your current balance is "+updatedBalance+".\n\n"+
                "If you did not perform this action or suspect any unauthorized access to your account, please contact" +
                " to our customer support team at "+sender+" immediately.\n\n"+
                "Thank you for choosing Digital-Banking.\nBest regards,\n" +
                "Digital-Banking Team";
        message.setFrom(String.valueOf(from));
        message.setSubject(subject);
        message.setText(body);
        message.setTo(email);
        javaMailSender.send(message);
    }

    @Override
    public void sendMoneyReceivedSuccessfulMessage(String name, String email, long accountNumber, long sender, double balance) throws UnsupportedEncodingException {
        SimpleMailMessage message = new SimpleMailMessage();
        InternetAddress from = new InternetAddress("digitalbanking.mcaproject@gmail.com", "Digital-Banking Team");
        String subject = "New Transaction Alert";
        String body = "Dear, "+name+"\n" +
                "\n" +
                "You have received an amount of "+balance+" in your " +
                "account (A/c No.- "+accountNumber+") from A/c No.- "+sender+" on "+ LocalDateTime.now().format(dateTimeFormatter()) +"\n"+
                "Please, log in your account to view your updated balance.\n\nIf you have any queries, please contact" +
                " to our customer support team at "+sender+"\n\n"+
                "Best regards,\n" +
                "Digital-Banking Team";
        message.setFrom(String.valueOf(from));
        message.setSubject(subject);
        message.setText(body);
        message.setTo(email);
        javaMailSender.send(message);
    }


    @Override
    public void sendProfileUpdateSuccessfulMessage(String name, String email) throws UnsupportedEncodingException {
        SimpleMailMessage message = new SimpleMailMessage();
        InternetAddress from = new InternetAddress("digitalbanking.mcaproject@gmail.com", "Digital-Banking Team");
        String subject = "Profile Updated Successfully";
        String body = "Dear, "+name+"\n" +
                "\n" +
                "We are pleased to inform you that your profile and personal details have been successfully updated in " +
                "our system. Your information is now up-to-date and accurate."+"\n\n"+
                "If you did not perform this action or suspect any unauthorized access to your account, please contact " +
                "our customer support team at "+sender+" immediately.\n\n"+
                "Thank you for choosing Digital-Banking.\nBest regards,\n" +
                "Digital-Banking Team";
        message.setFrom(String.valueOf(from));
        message.setSubject(subject);
        message.setText(body);
        message.setTo(email);
        javaMailSender.send(message);
    }

    @Override
    public void sendLoanSuccessfulMessage(String name, String email) throws UnsupportedEncodingException {
        SimpleMailMessage message = new SimpleMailMessage();
        InternetAddress from = new InternetAddress("digitalbanking.mcaproject@gmail.com", "Digital-Banking Team");
        String subject = "Loan Application sent Successfully";
        String body = "Dear, "+name+"\n" +
                "\n" +
                "We are pleased to inform you that your loan application form has been successfully submitted. " +
                "Our team will now review your application and assess your eligibility for the requested loan amount.\n" +
                "\n" +
                "Please note that the processing time may vary depending on the complexity of your application. " +
                "We will keep you updated on the status of your application via email.\n" +
                "\n" +
                "If you have any questions or need further assistance, please do not hesitate to contact our customer " +
                "support team at " + sender +
                "\n\n" +
                "Thank you for choosing our services. We look forward to serving you.\nBest regards,\n"+
                "Digital-Banking Team";
        message.setFrom(String.valueOf(from));
        message.setSubject(subject);
        message.setText(body);
        message.setTo(email);
        javaMailSender.send(message);
    }

    @Override
    public void sendStatementPdf(String name, String email, String attachment) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress("digitalbanking.mcaproject@gmail.com", "Digital-Banking Team"));
        helper.setTo(email);
        helper.setSubject("Account Statement");

        // Set the email body
        String body = "Dear " + name + ",\n\n" +
                "Please find attached the PDF file containing your account statement. " +
                "The file is password protected for security reasons. " +
                "Your password is the last 4 digits of your registered mobile number followed by the last 4 digits of your account number." +
                "\n\n" +
                "If you have any questions or need further assistance, please do not hesitate to contact our customer support team at \n" + sender +
                "\n\n" +
                "Thank you for choosing our services. We look forward to serving you.\n" +
                "Digital-Banking Team";
        helper.setText(body);

        // Add the attachment
        FileSystemResource file = new FileSystemResource(new File(attachment));
        helper.addAttachment("Account-Statement.pdf", file);

        // Send the email
        javaMailSender.send(message);
    }
}
