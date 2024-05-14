# Digital Banking

Secure Banking application with Spring Boot and Angular.

Digital-Banking is a secure and user-friendly Internet banking platform that offers a range of essential features for managing your finances conveniently. With Digital-Banking, users can easily sign up and log in to their accounts, ensuring secure access to their banking services. The platform also provides robust OTP sending and validation processes to ensure account security during registration.

Once logged in, users can manage their accounts effectively, viewing details such as balance and transaction history. Digital-Banking allows users to transfer funds between their accounts or to other users seamlessly. Users also receive email notifications for important account activities, such as fund transfers and updates, ensuring they stay informed at all times.

One of the key features of Digital-Banking is its ability to generate account statements and download them in PDF format. Users can specify a valid date range to retrieve their account statements, providing them with a convenient way to track their financial activities. For added convenience, users can choose to have the PDF statement directly in their registered email. The account statement PDF is securely encrypted. Only the users, with a unique password derived from the last 4 digits of their registered mobile number and the last 4 digits of their account number, can access it.

With its comprehensive set of features, Digital-Banking offers a reliable and efficient banking experience, empowering users to manage their finances with ease.

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
- **Mail Notifications**: Users receive email notifications for important account activities, such as fund transfers and updates.
- **Transaction History**: Users can view their transaction history of Deposit, Withdraw and Transfer activities.
- **Generate statements and Download PDF**: Users can download their account statements in PDF format by specifying a valid date range. Additionally, users can opt to receive the PDF statement in their registered email.
- **Securely Encrypted PDF**: The password to unlock the PDF is a combination of the last four digits of the user's registered mobile number followed by the last four digits of the user's account number, ensuring that only users have access to their statement.

## Technologies Used

- **Frontend**: Angular 17, TypeScript, HTML/CSS
- **Backend**: Spring Boot, Java
- **Database**: MySQL
- **Security**: Spring Security, JWT Authentication
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
     port: 1222
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
   Backend (API): http://localhost:1222/banking

## Usage
- Register a new account or log in with existing credentials.
- Explore the dashboard to view account details and transaction history.
- Use the fund transfer feature to transfer money between accounts.
- Check your email for notifications on successful transactions and account updates.
- Download account statements in PDF format.
