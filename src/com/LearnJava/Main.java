package com.LearnJava;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();
        CinemaApp cinema = new CinemaApp(rows, seatsPerRow);
        cinema.cinemaMenu(scanner);

    }

}