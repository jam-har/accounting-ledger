# Accounting Ledger Application

A console app that stores transactions in a csv file and allows user to filter and search it.

## Description of the Project

This application acts a digital log for financial transactions. User may enter deposit or payment information 
and later view entries as an entire list or by searching a filtering. Each entry shows a few details about
what the transaction is, the source or destination of money, and when the entry was made. This is best for 
someone who may be budgeting and wants to keep up with monthly expenses or spending habits. This app might 
also work for a user without a traditional bank account or banking application.
## Features

- Add and log deposits
- Add and log payments 
- View all transactions at once
- View transactions within a selected date range
- Search a transaction by vendor

## User Stories

- As a user, I want to see a home screen with a menu to add deposits, make payments, view a ledger, and exit the application, so that I can return to same screen from anywhere in the app and choose pages to view
- As a user, I want to add deposit information and be able to later view it, so that I can keep track of when I receive money and how much 
- As a user, I want to add payment information and be able to later view it, so that I know when I have spent money and how much
- As a user, I want to see all transactions organized by date, time , description, vendor, and amount, so that I can easily navigate, view, and filter all account history
- As a user, I want to use date or vendor search filters to create a report of all entries according to parameters, so that I can narrow down and/or find specific transactions with ease
- As a user I wish to see all transaction history in reverse chronological order with payments appearing positive or negative based on the action, so that I may have record of each new and previous use of my account


## Setup

### Prerequisites

- Java 17 (Amazon Corretto), the version used in class.
- IntelliJ IDEA Community Edition: <https://www.jetbrains.com/idea/download/>

### Running the application

1. Clone this repository, or download it as a zip and unzip it.
2. In IntelliJ: File > Open, pick the project folder (the one that holds `pom.xml`), press Open.
3. Wait for IntelliJ to finish indexing (progress bar at the bottom).
4. Open `src/main/java/com/pluralsight/FinancialTracker.java`.
5. Right-click inside the file and choose Run 'FinancialTracker.main()'.

### Data and configuration

- `transactions.csv` sits at the project root, next to `pom.xml`. The program reads it at start.

## Technologies Used

- Java 17
- IntelliJ IDEA

## Demo

[Main menu](images/Menu.png)

[One feature in action: Add Deposit](images/Deposit.png) 

[Error message](images/Error.png)

## A piece of code I am proud of

<!-- Paste one short block of your own code (5 to 15 lines) between the three backticks, then write two
     or three sentences: what it does and why you chose to write it that way. This is what you show on
     demo day and what you talk about in an interview. -->

```java
    private static Transaction getTransactionDetails(Scanner scanner) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        boolean entry = false;
        System.out.println();
        System.out.println("Please enter the following information:");
        System.out.print("Description (a few words about the transaction): ");
        String description = scanner.nextLine();
        if(description.isBlank()) {
            System.out.print("Missing information. Please enter a description: ");
            description = scanner.nextLine();
        }

        System.out.print("Enter amount to deposit: ");
        Double amount = scanner.nextDouble();
        if(amount.isNaN() || amount < 0) {
            System.out.print("Please enter positive numerical values only: ");
            amount = scanner.nextDouble();
        }
        System.out.println();
        return new Transaction(date,time,description,vendor,amount);
    }
```
This is a method used when adding deposits or payments. It prompts the user to enter information, saves the entries into
a transaction object, and automatically saves the time and date. 

## Future Work

- I would complete custom searches bonus.
- Searches would consider partial words
- The user could choose date ranges
- There would be more try/catch usage 
- The code would be more efficient and refined with new concepts learned over time 
- The app would have more of a visual appeal with customization.

## Resources


- Java YearUp Fall 2026 hub: <https://raymaroun.github.io/yearup-java-fall2026/>
- Mastering Java Development Student Workbook 3a
- W3 Schools Java Tutorial: <https://www.w3schools.com/java/default.asp>
- Baeldung Java Articles: <https://www.baeldung.com/>

## Author

- **Jamison Harris**

## Thanks

- Thank you to Mr. Raymond for guiding and instructing me throughout these past weeks and this capstone.
- Thank you to Ms. Tiffany Obi for taking the time to tutor me during and after normal hours and providing lots of encouragement.
- Thank you to my class for viewing my presentation and supporting me during class.