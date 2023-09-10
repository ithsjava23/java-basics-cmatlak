package org.example;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class App {
    public static Scanner scan = new Scanner(System.in);
    public static int[] prices = new int[24];

    public static void main(String[] args) {
        boolean running = true;

        do {
            System.out.print(showMenu());
            String choice = scan.next();

            switch (choice) {
                case "1" -> inputMethod();
                case "2" -> minMaxMid();
                case "3" -> sortMethod();
                case "4" -> bestChargingTimeMethod();
                case "e", "E" -> running = false;
                default -> System.out.println("Ogiltigt val. Försök igen.");
            }
        } while (running);

       System.out.println("Programmet har avslutats.");
    }

    public static String showMenu() {
       String text = """
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                e. Avsluta    
                """;
        return text ;
    }


    public static void inputMethod() {
        for (int hour = 0; hour < 23; hour++) {
            System.out.print("Ange elpriset per timme "+ (  String.format("%02d", hour) + "-" + String.format("%02d", hour + 1 ) + ": "));

            try {
                int price = Integer.parseInt(scan.next());
                prices[hour] = price;
            } catch (NumberFormatException e) {
                System.err.println("Felaktigt inmatat värde. Ange en giltig siffra.");
                hour--;




            }
        }

    }

    public static void minMaxMid() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int minHour = -1;
        int maxHour = -1;
        int sum = 0;

        for (int hour = 0; hour < 23; hour++){
            int price = prices[hour];
            if (price < min) {
                min = price;
                minHour = hour;
            }

            if (price > max) {
                max = price;
                maxHour = hour;
            }

        sum += price;
        }
        double average = (double) sum / prices.length;

        System.out.print (" Lägsta pris: " + (  String.format("%02d", minHour) + "-"+ (  String.format("%02d", minHour + 1)+ ", " + min + " öre/kWh\n" )));
        System.out.print (" Högsta pris:  " +(  String.format("%02d", maxHour) + "-"+ (  String.format("%02d", maxHour + 1)+ ", " + max + " öre/kWh\n" )));
        System.out.print (" Medelpris:  " + average + " öre/Kwh\n");
    }

    public static void sortMethod() {
        Integer[] sortedHours = new Integer[24];
        Integer[] sortedPrices = new Integer[24];

        for (int i = 0; i < 23; i++) {
            sortedHours[i] = i;
            sortedPrices[i] = prices[i];
        }

        // Sortera i fallande ordning baserat på priserna
        for (int i = 0; i < 23; i++) {
            for (int j = i + 1; j < 23; j++) {
                if (sortedPrices[i] < sortedPrices[j]) {
                    // Byt plats på priserna
                    int tempPrice = sortedPrices[i];
                    sortedPrices[i] = sortedPrices[j];
                    sortedPrices[j] = tempPrice;

                    // Byt plats på tiderna
                    int tempHour = sortedHours[i];
                    sortedHours[i] = sortedHours[j];
                    sortedHours[j] = tempHour;
                }
            }
        }

        System.out.println("Elpriser sorterade i fallande ordning:");
        for (int i = 0; i < 23; i++) {
            int hour = sortedHours[i];
            int price = sortedPrices[i];
            System.out.println(String.format("%02d-%02d: %d öre/kWh", hour, hour + 1, price));
        }
    }


    public static void bestChargingTimeMethod() {
        // Implementera bestChargingTimeMethod här
    }
}