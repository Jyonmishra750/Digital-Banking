# Digital Banking

Secure Banking application with Spring Boot and Angular.

This application provides a powerful tool for bank operations such as holding up accounting information in the database and also able to keep daily banking transactions. This application also helps add details of new customers such as account no; It also has features for deletion and modification of existing customers and provides fast data searching and calculating data with less time. This software helps reduce the clerical work of the staff since almost all of the work can be done by the software.

The software provides Separate security levels for the staff and the administrator. This software holds a well-defined RDBMS database for storing data in the bank, which can handle and frequently use large amounts of data. The system can connect with the printers and scanners, which helps the user get full advantage of the software, such as giving up an annual report or another report in a printable format.

## Technology Used
- JDK 17
- Spring boot 3.2
- Angular 17
- MySQL

## Features

- **User Authentication**: Users can sign up and log in to their accounts securely.
- **OTP Send and Validation**: Users will receive an OTP while registering and validate that OTP for successful registration.
- **Account Management**: Users can view their account details, including balance and transaction history.
- **Fund Transfers**: Users can transfer funds between their accounts or to other users.
- **Transaction History**: Users can view their transaction history and filter transactions by date or type.
- **Mail Notifications**: Users receive email notifications for important account activities, such as fund transfers and updates.
- **Download Account Statement in PDF Format**: Users can download their account statements between a valid date range in PDF Format.

## Technologies Used

- **Frontend**: Angular 17, TypeScript, HTML/CSS
- **Backend**: Spring Boot, Java
- **Database**: MySQL
- **Security**: Spring Security, JWT Authentication
- **Java MailSender**: Mail Sending Service
- **ITextPdf**: PDF Download
- **API Documentation**: Swagger

## Setup

### Prerequisites

- Node.js and npm installed for Angular development
- Java and Maven installed for Spring Boot development
- MySQL installed for database storage

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/Jyonmishra750/Digital-Banking.git
   cd Digital-Banking
2. Frontend setup (Angular)

   ```sh
   cd frontend
   npm install
   npm start

3. Backend setup (Spring boot)

   ```sh
   cd backend
   mvn spring-boot:run

4. Application Configuration

   Configuring the Database

   The `application.yml` file can be used to configure the database connection. to configure MySQL:

   ```yaml
   server:
     port: 1221
     servlet:
       context-path: "/banking"
   spring:
     datasource:
       url: jdbc:mysql://localhost/digital_banking_db
       username: [your username]
       password: [ your password]
     mail:
       host: smtp.gmail.com
       port: 587
       username: [Your Email]
       password: [Email Password]
       properties:
         mail:
           smtp:
             auth: true
             starttls:
               enable: true


4. Access the applications:
   
   ```sh
   Frontend: http://localhost:4200
   Backend (API): http://localhost:1221/banking

## Usage
- Register a new account or log in with existing credentials.
- Explore the dashboard to view account details and transaction history.
- Use the fund transfer feature to transfer money between accounts.
- Check your email for notifications on successful transactions and account updates.
