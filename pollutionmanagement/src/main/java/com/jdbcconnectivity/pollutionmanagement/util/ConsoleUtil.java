package com.jdbcconnectivity.pollutionmanagement.util;

import java.util.Scanner;

public class ConsoleUtil {

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // SAFE pause: flushes leftover input first
    public static void pause(Scanner sc) {
        System.out.println("\nPress ENTER to continue...");
        sc.nextLine(); // consume whatever is left
    }

}