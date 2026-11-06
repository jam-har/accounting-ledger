package com.pluralsight;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


/*
 * Capstone skeleton – personal finance tracker.
 * ------------------------------------------------
 * File format  (pipe-delimited)
 *     yyyy-MM-dd|HH:mm:ss|description|vendor|amount
 * A deposit has a positive amount; a payment is stored
 * as a negative amount.
 *
 * TODO: create the Transaction class in this package first.
 *       One object = one line of the file. Decide its fields from
 *       the file format above, add a constructor and getters.
 *       This class does not compile until Transaction exists.
 */
public class FinancialTracker {

    /* ------------------------------------------------------------------
       Shared data and formatters
       ------------------------------------------------------------------ */
    private static final ArrayList<Transaction> transactions = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATETIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern(TIME_PATTERN);
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
   // private static String description;
   // private static String vendor;
   // private static Double amount;


    /* ------------------------------------------------------------------
       Main menu
       ------------------------------------------------------------------ */
    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        //System.out.println(transactions);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D" -> addDeposit(scanner);
                case "P" -> addPayment(scanner);
                case "L" -> ledgerMenu(scanner);
                case "X" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
        scanner.close();
    }

    /* ------------------------------------------------------------------
       File I/O
       ------------------------------------------------------------------ */

    /**
     * Load transactions from FILE_NAME.
     * • If the file doesn’t exist, create an empty one so that future writes succeed.
     * • Each line looks like: date|time|description|vendor|amount
     *
     * @return
     */
    public static ArrayList<Transaction> loadTransactions(String fileName) {
        // TODO: create file if it does not exist, then read each line,
        //       parse the five fields, build a Transaction object,
        //       and add it to the transactions list.
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME));
            bufferedReader.readLine();                                  // first line is the header: read it and drop it

            String csvLine;
            while ((csvLine = bufferedReader.readLine()) != null) {
                String[] tokens = csvLine.split("\\|");//ArrayList<String> tokens = new A
                LocalDate date = parseDate(tokens[0]); //or tokens.set(o)
                LocalTime time = parseTime(tokens[1]);
                String description = tokens[2];
                 String  vendor = tokens[3];
               Double amount = parseDouble(tokens[4]);


                // every token is text: convert the number

                // One object per line, built from the three values

                transactions.add(new Transaction(date, time, description, vendor, amount));
            }
            bufferedReader.close();
        } catch (Exception ex) {
            System.out.println("Something went wrong!");
        }
        return transactions;
    }

    /* ------------------------------------------------------------------
       Add new transactions
       ------------------------------------------------------------------ */

    /**
     * Prompt for ONE date+time string in the format
     * "yyyy-MM-dd HH:mm:ss", plus description, vendor, amount.
     * Validate that the amount entered is positive.
     * Store the amount as-is (positive) and append to the file.
     */
    private static void addDeposit(Scanner scanner) {
        // TODO]
        Transaction transaction = getTransactionDetails(scanner);
        scanner.nextLine();
        saveAttributes(transaction);
        System.out.println("Thank you for your entry!");


    }

    /**
     * Same prompts as addDeposit.
     * Amount must be entered as a positive number,
     * then converted to a negative amount before storing.
     */
    private static void addPayment(Scanner scanner) {
        // TODO
        Transaction paymentTransaction = getTransactionDetails(scanner);
        paymentTransaction.setAmount(paymentTransaction.getAmount() * -1);
        scanner.nextLine();
        saveAttributes(paymentTransaction);
        System.out.println("Thank you for your entry!");
    }


    /* ------------------------------------------------------------------
       Ledger menu
       ------------------------------------------------------------------ */
    private static void ledgerMenu(Scanner scanner) {
        transactions.sort(Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());

        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A" -> displayLedger();
                case "D" -> displayDeposits();
                case "P" -> displayPayments();
                case "R" -> reportsMenu(scanner);
                case "H" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    /* ------------------------------------------------------------------
       Display helpers: show data in neat columns
       ------------------------------------------------------------------ */
    private static void displayLedger() {
        columnView();
//
//        for (int i = 0; i < transactions.size(); i++) {
//            System.out.println(transactions.get(i).toString());//HELP
//            /* TODO – print all transactions in column format, newest first */
//        }

     //   transactions.sort(Comparator.comparing(Transaction::getDate).reversed());

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private static void displayDeposits() { /* TODO – only amount > 0               */
     //   transactions.sort(Comparator.comparing(Transaction::getDate).reversed());
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
            }
        }

    }

    private static void displayPayments() { /* TODO – only amount < 0*/
     //   transactions.sort(Comparator.comparing(Transaction::getDate).reversed());
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
            }
        }
    }

    /* ------------------------------------------------------------------
       Reports menu
       ------------------------------------------------------------------ */
    private static void reportsMenu(Scanner scanner) {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            //System.out.println("6) Custom Search");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> {
                    start = start.withDayOfMonth(1);
                    filterTransactionsByDate(start, end);
                    /* TODO – month-to-date report */
                }
                case "2" -> {/* TODO – previous month report */
                    start = start.minusMonths(1).withDayOfMonth(1);
                    end = end.minusMonths(1).withDayOfMonth(end.minusMonths(1).lengthOfMonth());
                    filterTransactionsByDate(start,end);
                }
                case "3" -> {/* TODO – year-to-date report   */
                    start = start.withDayOfYear(1);
                    filterTransactionsByDate(start, end);
                }
                case "4" -> {/* TODO – previous year report  */
                    start = start.minusYears(1).withDayOfYear(1);
                    end = end.minusYears(1).withDayOfYear(end.minusYears(1).lengthOfYear());
                    filterTransactionsByDate(start, end);
                }
                case "5" -> {/* TODO – prompt for vendor then report */
                    System.out.println("Enter vendor: ");
                    String vendor = scanner.nextLine();
                    filterTransactionsByVendor(vendor);
                }
                //case "6" -> customSearch(scanner);
                case "0" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    /* ------------------------------------------------------------------
       Reporting helpers
       ------------------------------------------------------------------ */




    private static void filterTransactionsByDate(LocalDate start, LocalDate end) {
        // TODO – iterate transactions, print those within the range
        //if(todayDate.after(historyDate) && todayDate.before(futureDate)) {
        //    // In between
        //}
            boolean results = false;

            for (Transaction transaction: transactions) {

                if (transaction.getDate().equals(start) || transaction.getDate().isAfter(start)) {
                    if (transaction.getDate().isBefore(end) || transaction.getDate().equals(end)) {
                        System.out.println(transaction);
                        results = true;
                    }

                }
            }
            if (!results) {
                System.out.println("No match found");
            } return;

    }


    private static void filterTransactionsByVendor(String vendor) {
        // TODO – iterate transactions, print those with matching vendor
        boolean results = false;
        for (Transaction transaction : transactions) {
            if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println(transaction);
                results = true;
                }
        }
        if (!results) {
            System.out.println("No match found");
        } return;

    }

//    private static void customSearch(Scanner scanner) {
//        // TODO – prompt for any combination of date range, description,
//        //        vendor, and exact amount, then display matches
//    }

    /* ------------------------------------------------------------------
       Utility parsers (you can reuse in many places)
       ------------------------------------------------------------------ */
    private static LocalDate parseDate(String s) {
        /* TODO – return LocalDate or null */
        return LocalDate.parse(s, DATE_FMT);
    }
    private static LocalTime parseTime(String s) {
        /* TODO – return LocalDate or null */
        return LocalTime.parse(s, TIME_FMT);
    }

    private static Double parseDouble(String s) {
        /* TODO – return Double   or null */

        return Double.parseDouble(s);
    }
    private static void columnView() {
        System.out.printf("%-20s|%-20s|%-50s|%-30s|%-20s", "Date", "Time", "Description", "Vendor", "Amount" + "\n");
        System.out.printf("%-100s", "---------------------------------------------------------------------------------------------------------------" + "\n");
        System.out.println();

/*
//        System.out.printf("%-20s|%-20s|%-50s|%-30s|%-20s", "Date", "Time", "Description", "Vendor", "Amount");
//        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
//        for (int i=0; i < transactions.size(); i++){
//            System.out.printf("%20s|%20s|%50s|%30s|%20.2f%n", transactions.get(i).getDate(), transactions.get(i).getTime(), transactions.get(i).getDescription(), transactions.get(i).getVendor(), transactions.get(i).getAmount());
*/
        //}

    }
    private static void saveAttributes(Transaction transaction) {

        String d = transaction.getDate().format(DATE_FMT);
        String t = transaction.getTime().format(TIME_FMT);

        String printFormat = String.format("%s|%s|%s|%s|%.2f", d, t, transaction.getDescription(),
                transaction.getVendor(), transaction.getAmount());

        transactions.add(transaction);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(printFormat + "\n");
        } catch (Exception e) {
            System.out.println("Error");
        }
        return;
    }
    private static Transaction getTransactionDetails(Scanner scanner) {

        //get the current date and time for the transaction
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);

        // begin asking the user for transaction details
        System.out.println("Please enter the following information:");
        System.out.print("Description (a few words about the transaction): ");
       String description = scanner.nextLine();
        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount to deposit: ");
        Double amount = scanner.nextDouble();
        if(amount.isNaN() || amount < 0) {
            System.out.println("Please enter positive numerical values only: ");
            // scanner.nextLine();
            amount = scanner.nextDouble();
        }

        //once we've collected all the transaction details
        //we can build and return our transaction


        return new Transaction(date,time,description,vendor,amount);
    }

}
