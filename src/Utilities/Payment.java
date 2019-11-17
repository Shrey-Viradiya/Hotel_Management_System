package Utilities;

import UDexception.ExpensiveOfferException;
import UDexception.InvalidCardNumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Random;

public class Payment {
    private double Total;

    public Payment(double S) {
        Total = S;
    }

    public String generateVoucher() throws ExpensiveOfferException {
//        String Voucher = UUID.randomUUID().toString();
        Random rand = new Random();
        String Voucher = "" + rand.nextInt(1000000000);

        if (Total <= 5000) {
            throw new ExpensiveOfferException("Not enough Bill amount to provide a voucher...");
        } else if (Total > 5000 && Total < 20000) {
            Voucher += "_CarWash3000_";
            System.out.println(" \n\nYou have won a voucher code of\nFree Car Wash worth 3000 INR\nPlease Note Your Voucher Code:\n_______________________\n" + Voucher);
        } else if (Total >= 20000 & Total < 35000) {
            Voucher += "_Spa&Massage5000_";
            System.out.println(" \n\nYou have won a voucher code of\nFree Spa & Massage worth 5000 INR\nPlease Note Your Voucher Code:\n_______________________\n" + Voucher);
        } else if (Total >= 35000) {
            Voucher += "_FreeDrinksFor1Night8000_";
            System.out.println(" \n\nYou have won a voucher code of\nFree drinks for a night worth 8000 INR\nPlease Note Your Voucher Code:\n_______________________\n" + Voucher);
        }
        return Voucher;
    }

    public void doPayment() {
        System.out.println("Payment Options: ");
        System.out.println("1: Cash ");
        System.out.println("2: Card ");
        System.out.println("3: PayTM ");

        short input;
        while (true) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                input = Short.parseShort(br.readLine());
//                br.close();
                break;
            } catch (NumberFormatException | IOException ignored) {

            }
        }

        switch (input) {
            case 1:
                System.out.println("Payment By Cash Selected...");
                System.out.println("Payment Done...");
                System.out.println("Have a Great Day!");
                break;

            case 2:
                System.out.println("Payment By Card Selected...");


                while (true) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("Enter the Card No.: ");

                    try {
                        while (true) {
                            try {
                                String temp = br.readLine();
                                long CardNumber = Long.parseLong(temp);
                                if (CardNumber < 1000000000000000L) {
                                    throw new InvalidCardNumber("Please Enter a Valid Card Number");
                                }
                                break;
                            } catch (NumberFormatException ignored) {
                            }
                        }
                        System.out.println("Enter the PIN: ");
                        br.readLine();

                        System.out.println("Transaction Successful!");
                        System.out.println("Have a Great Day!");
                        break;
                    } catch (InvalidCardNumber | IOException exp) {
                        System.out.println(exp.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Input Type is Mismatched. Enter Integral values only");
                    }
                }
                break;

            case 3:
                System.out.println("Payment By PayTM Selected...\nPlease Scan QRCode...");
                System.out.println("Payment Done...");
                System.out.println("Have a Great Day!");
                break;

            default:
                System.out.println("Not Valid Choice");
                break;
        }
    }
}