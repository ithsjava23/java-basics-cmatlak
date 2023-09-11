package org.example;
import java.io.PrintStream;
import java.util.Locale;
import javax.swing.*;
import java.util.*;
import java.util.Scanner;
public class App {


    public static int[] prices = new int[24];

    public static void main(String[] args) {
        Locale swedishLocale = new Locale("sv", "SE");
        Locale.setDefault(swedishLocale);

        Scanner scan = new Scanner(System.in);

        boolean running = true;

        do {
            System.out.print(showMenu());
            String choice = scan.next();

            switch (choice) {
                case "1" -> inputMethod(scan);
                case "2" -> minMaxMid();
                case "3" -> sortMethod();
                case "4" -> bestChargingTimeMethod();
                case "e" -> running = false;
                case "E" -> running = false;
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
        return text;
    }


    public static void inputMethod(Scanner scan) {
        for (int hour = 0; hour < 24; hour++) {
            System.out.print("Ange elpriset per timme " + String.format("%02d-%02d", hour, hour + 1) + ": ");

            try {
                int price = Integer.parseInt(scan.next());
                prices[hour] = price;
            } catch (NumberFormatException e) {
                System.err.print("Felaktigt inmatat värde. Ange en giltig siffra. \n");
                hour--;
            }
        }
    }
       /* for (int hour = 0; hour < 24; hour++) {
            System.out.print("Ange elpriset per timme "+ (  String.format("%02d", hour) + "-" + String.format("%02d", hour + 1 ) + ": "));

            try {
                int price = Integer.parseInt(scan.next());
                prices[hour] = price;
            } catch (NumberFormatException e) {
                System.err.print("Felaktigt inmatat värde. Ange en giltig siffra. \n");
                hour--;




            }
        }

    }*/

    public static String minMaxMid() {

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int minHour = -1;
        int maxHour = -1;
        int sum = 0;

        for (int hour = 0; hour < 24; hour++) {
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
        float average = (float) sum / prices.length;

        StringBuilder minMaxMidText = new StringBuilder();
        minMaxMidText.append("Min, Max och Medelpris:\n");
        minMaxMidText.append("-----------------------\n");
        minMaxMidText.append(String.format("Lägsta pris: %02d-%02d, %d öre/kWh\n", minHour, minHour + 1, min));
        minMaxMidText.append(String.format("Högsta pris: %02d-%02d, %d öre/kWh\n", maxHour, maxHour + 1, max));
        minMaxMidText.append(String.format("Medelpris: %.2f öre/kWh\n", average));


        return minMaxMidText.toString();
    }
        // System.out.printf("Lägsta pris: %02d-%02d, %d öre\n", minHour, minHour + 1, min);
       // System.out.printf("Högsta pris: %02d-%02d, %d öre\n", maxHour, maxHour + 1, max);
       // System.out.printf("Medelpris: %.2f öre/kWh\n", average);



    public static void sortMethod() {
        Integer[] sortedHours = new Integer[24];
        Integer[] sortedPrices = new Integer[24];

        for (int i = 0; i < 24; i++) {
            sortedHours[i] = i;
            sortedPrices[i] = prices[i];
        }

        for (int i = 0; i < 24; i++) {
            for (int j = i + 1; j < 24; j++) {
                if (sortedPrices[i] < sortedPrices[j]) {

                    int tempPrice = sortedPrices[i];
                    sortedPrices[i] = sortedPrices[j];
                    sortedPrices[j] = tempPrice;


                    int tempHour = sortedHours[i];
                    sortedHours[i] = sortedHours[j];
                    sortedHours[j] = tempHour;
                }
            }
        }


        StringBuilder sortedPricesText = new StringBuilder();
        sortedPricesText.append("""
        Elpriser i fallande ordning:
        ----------------------------
        """);

        for (int i = 0; i < 24; i++) {
            int hour = sortedHours[i];
            int price = sortedPrices[i];
            sortedPricesText.append(String.format("%02d-%02d %d öre\n", hour, hour + 1, price));
        }

        System.out.println(sortedPricesText.toString());

        }


    public static void bestChargingTimeMethod() {
        int cheapestTotalValue = Integer.MAX_VALUE;
        int cheapestStartHour = -1;

        for (int startHour = 0; startHour < 21; startHour++) {
            int totalValue = 0;
            for (int i = startHour; i < startHour + 4; i++) {
                totalValue += prices[i];
            }
            if (totalValue < cheapestTotalValue) {
                cheapestTotalValue = totalValue;
                cheapestStartHour = startHour;
            }
        }

        double averageValue = (double) cheapestTotalValue / 4;

        System.out.printf(String.format("Påbörja laddning klockan %02d\n", cheapestStartHour, + 3));
        System.out.printf(String.format("Medelpris 4h: %.1f öre/kWh\n",averageValue));
    }
}