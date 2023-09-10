package org.example;
import java.util.Locale;
import javax.swing.*;
import java.util.*;
import java.util.Scanner;
public class App {


    public static int[] prices = new int[24];

    public static void main(String[] args) {
        Locale swedishLocale = new Locale("sv","SE");
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
        return text ;
    }


    public static void inputMethod(Scanner scan) {

        for (int hour = 0; hour < 24; hour++) {
            System.out.print("Ange elpriset per timme "+ (  String.format("%02d", hour) + "-" + String.format("%02d", hour + 1 ) + ": "));

            try {
                int price = Integer.parseInt(scan.next());
                prices[hour] = price;
            } catch (NumberFormatException e) {
                System.err.print("Felaktigt inmatat värde. Ange en giltig siffra. \n");
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

        for (int hour = 0; hour < 24; hour++){
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

        System.out.printf(" Lägsta pris: " + String.format("%02d", minHour) + "-"+ (  String.format("%02d", minHour + 1)+ ", " + min + " öre/kWh\n" ));
        System.out.printf(" Högsta pris: " + String.format("%02d", maxHour) + "-"+ (  String.format("%02d", maxHour + 1)+ ", " + max + " öre/kWh\n" ));
        System.out.printf(" Medelpris: " + (String.format (average + " öre/Kwh\n")));

    }

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

        //System.out.print("Elpriser sorterade i fallande ordning: \n");
        for (int i = 0; i < 24; i++) {
            int hour = sortedHours[i];
            int price = sortedPrices[i];


            System.out.printf(String.format( "%02d-%02d %d öre \n", hour, hour + 1, price));
        }
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
        int cheapestEndHour = cheapestStartHour + 3;
        double averageValue = (double) cheapestTotalValue / 4;

        System.out.printf (String.format("Påbörja laddning klockan %02d\n", cheapestStartHour + cheapestEndHour + 1));
        System.out.printf(String.format("Medelpris 4h: %.1f öre/kWh\n",averageValue));
    }
}