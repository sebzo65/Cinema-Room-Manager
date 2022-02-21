package com.LearnJava;

import java.util.Arrays;
import java.util.Scanner;

public class CinemaApp {

    private static final int NORMAL_TICKET_PRICE = 10;
    private static final int REDUCED_TICKET_PRICE = 8;
    private final int rows;
    private final int seatsPerRow;
    private final int reducedTicketPriceRows;
    private final int normalTicketPriceRows;
    private final char[][] seats;
    private boolean isFinished;
    private static int numPurchasedTickets;
    private static int currentIncome;
    private int totalProfit;
    private boolean stayLoop;

    public CinemaApp(int rows, int seatsPerRow) {
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        this.reducedTicketPriceRows = rows * seatsPerRow <= 60 ? 0 : rows / 2 + rows % 2;
        this.normalTicketPriceRows = rows - this.reducedTicketPriceRows;
        this.seats = initSeats(rows, seatsPerRow);
        this.isFinished = false;
        this.totalProfit = getTotalProfit();
        this.stayLoop = true;
    }

    public void callMenu() {
        System.out.printf("%n1. Show the seats%n");
        System.out.printf("2. Buy a ticket%n");
        System.out.println("3. Statistics");
        System.out.printf("0. Exit%n");
    }

    public void callStats() {
        System.out.printf("%nNumber of purchased tickets: %d%n", numPurchasedTickets);
        System.out.printf("%nPercentage: %.2f%%%n", calculatePercentageOfSeats());
        System.out.printf("%nCurrent Income: $%d%n", currentIncome);
        System.out.printf("%nTotal Income: $%d%n", getTotalProfit());
    }

    public void cinemaMenu (Scanner scanner) {
        while (isFinished == false) {
            callMenu();
            int userSelect = scanner.nextInt();
            if (userSelect == 1) {
                printCinema();
            } else if (userSelect == 2) {
                checkSeatValidity(scanner);
            } else if (userSelect == 3) {
                callStats();
            } else {
                isFinished = true;
            }
        }

    }

    private float calculatePercentageOfSeats() {
        System.out.println("rows: " + rows);
        System.out.println("seatsPerRow: " + seatsPerRow);
        System.out.println("Result: " + (numPurchasedTickets / (rows * seatsPerRow)) * 100);
        return ((float)numPurchasedTickets / (float)(rows * seatsPerRow)) * 100;

    }

    public void checkSeatValidity(Scanner scanner) {
        System.out.println("Enter a row number:");
        int rowN = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int colN = scanner.nextInt();

        if (rowN > rows || colN > seatsPerRow) {
            System.out.println("Wrong Input!");
            checkSeatValidity(scanner);
        }
        if (rowN <= 0 || colN <= 0) {
            System.out.println("Wrong Input!");
            checkSeatValidity(scanner);
        }

        boolean check = buyTicket(rowN, colN);
        System.out.println("Check: " + check);
        if (check == false) {
            printErrorAlreadyPurchased();
            checkSeatValidity(scanner);
        } else {
            buyTicket(rowN, colN);
            printTicketPrice(rowN, colN);
        }
    }

    public void printTotalProfit() {
        System.out.printf("Total income:\n$%d\n", getTotalProfit());
    }

    public void printCinema() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nCinema:\n  ");
        for (int i = 1; i <= seatsPerRow; i++) {
            sb.append(i).append(" ");
        }
        sb.append("\n");
        for (int i = 0; i < rows; i++) {
            sb.append(i + 1).append(" ");
            for (int j = 0; j < seatsPerRow; j++) {
                sb.append(seats[i][j]).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    private void printTicketPrice(int row, int seatNumber) {
        int ticketPrice = row > normalTicketPriceRows ? REDUCED_TICKET_PRICE : NORMAL_TICKET_PRICE;
        currentIncome += ticketPrice;
        System.out.printf("Ticket price: $%d\n", ticketPrice);
    }
    private boolean buyTicket(int row, int seatNumber) {

        if (seats[row - 1][seatNumber - 1] == 'S') {
            seats[row - 1][seatNumber - 1] = 'B';
            numPurchasedTickets += 1;
            return true;
        }
        return false;
    }

    private void printErrorAlreadyPurchased() {
        System.out.printf("%nThat ticket has already been purchased!%n");
    }

    private char[][] initSeats(int rows, int seatsPerRow) {
        char[][] seats = new char[rows][seatsPerRow];
        for (char[] row : seats) {
            Arrays.fill(row, 'S');
        }
        return seats;
    }

    private int getTotalProfit() {
        return (NORMAL_TICKET_PRICE * normalTicketPriceRows +
                REDUCED_TICKET_PRICE * reducedTicketPriceRows) * seatsPerRow;
    }

}
