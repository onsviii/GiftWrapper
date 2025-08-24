package com.nulp;

import java.util.Scanner;

public class MyScanner {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getNumber() {
        while (!scanner.hasNextInt()) {
            System.out.println("\nError. Enter a number.\n");
            scanner.nextLine();
        }

        int num = scanner.nextInt();

        scanner.nextLine();

        return num;
    }

    public static String getString() {
        return scanner.nextLine();
    }
}
