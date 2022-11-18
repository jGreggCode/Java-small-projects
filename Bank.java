/*
 * @author jGreggCode
 */

// Imports
import java.util.*;
// -------------------------------------

public class Bank {

    // Glabal variables
    static Scanner ui = new Scanner(System.in);
    static int money = 2000;
    static String defPin = "123456";
    static char choice;
    static String[] tranferDetails = new String[3];
    static String menu = "Choose your transaction!\n" +
                         "[1 / D] - Deposit        | [2 / W] - Withdraw\n" +
                         "[3 / T] - Transfer Money | [4 / B] - Balance\n" +
                         "Enter here (Ex: 2): ";
    // -------------------------------------

    // Clear screen for visualization (Optional)
    static void clear() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
    // -------------------------------------

    // Loading (Optional)
    static void loading(String message) {
        for (int i = 0; i < message.length(); i++) {
            System.out.print(message.charAt(i));
            try { // To catch the error
                Thread.sleep(30); // 30 Milliseconds
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    // -------------------------------------

    // Ask Another transaction
    static boolean anotherTrans() {

        System.out.print("Do you want another transaction [y/n]: ");
        choice = ui.next().charAt(0);

        switch (Character.toLowerCase(choice)) {
            case 'y':
                break;
            case 'n':
                System.out.println("Thank you for banking with us!");
                System.exit(0);
            default:
                System.out.println("Invalid input!");
                anotherTrans();
        }

        return true;
    }

    // Check if the user input a valid amount 
    static boolean validAmount(int amount) {

        int onlyHundreds = amount % 100;
        int minAmout = 200, maxAmount = 50000;

        if (amount < minAmout) {
            System.out.printf("Minimum amount is %,d!", minAmout);
            return false;
        } else if (amount > maxAmount) {
            System.out.printf("Maximum amount is %,d!", maxAmount);
            return false;
        } else if (onlyHundreds != 0) {
            System.out.println("Only hundreds are allowed!");
            return false;
        }

        return true;
    }
    // Verify the pin, terminate if error
    static void pinVerify() {

        // User will only have 3 tries to enter their pin
        for (int i = 2; i >= 0; i--) {
            System.out.print("Enter your pin: ");
            String pin = ui.nextLine();

            System.out.println(pin);

            // If pin is more than or less than the defPin length
            if (pin.length() < defPin.length() || pin.length() > defPin.length()) {
                clear();
                System.out.println("Pin must be 6 characters.");
            } 
            
            // If the user enter the correct pin
            else if (pin.equalsIgnoreCase(defPin)) {
                clear();
                break;
            } 
            
            // Incorrect pin
            else {
                clear();
                System.out.println("Incorrect Pin!");
            }

            // If the user entered 3 times incorrectly
            if (i == 0) {
                clear();
                System.out.println("Account Disabled, Try again later!");
                System.exit(0);
            }  
        }
        
    }
    // -------------------------------------

    // Main menu
    static void mainMenu() {
        clear();

        loading("Main menu is loading...");

        clear();

        System.out.print(menu);
        choice = ui.next().charAt(0);

        switch(Character.toLowerCase(choice)) {
            case '1': case 'd': 
                deposit();
                break;
            case '2': case 'w': 
                withdraw();
                break;
            case '3': case 't': 
                transferMoney();
                break;
            case '4': case 'b': 
                balance();
                break;
            default:
                mainMenu();
        }
    }
    // -------------------------------------

    // Deposit method
    static void deposit() {

        int amount = 0;

        System.out.println();

        System.out.print("How much would you like to deposit: ");
        amount = ui.nextInt();

        if (!validAmount(amount)) {
            deposit();
        } else {

            ui.nextLine();

            pinVerify();

            money += amount;

            System.out.printf("Your new balance is: %,d\n", money);
            System.out.println("Thank you for banking with us!");
        }

        if (anotherTrans()) {
            mainMenu();
        }
    }
    // -------------------------------------

    // Deposit method
    static void withdraw() {

        System.out.println();

        int amount = 0;

        System.out.print("How much would you like to withdraw: ");
        amount = ui.nextInt();

        if (amount > money) {
            System.out.println();
            System.out.println("Insufficient balance!");
            withdraw();
        } 

        if (!validAmount(amount)) {
            withdraw();
        } else {

            ui.nextLine();

            pinVerify();

            money -= amount;
            System.out.printf("Your remaining balance is: %,d\n", money);
            System.out.println("Thank you for banking with us!");
        }

        if (anotherTrans()) {
            mainMenu();
        }

    }
    // -------------------------------------

    // Transfer money method
    static void transferMoney() {

        System.out.println();
        
        int amount = 0;
        String name, number;
        name = number = "";

        ui.nextLine();

        System.out.print("Account Number: ");
        number = ui.nextLine();

        System.out.print("Account Name: ");
        name = ui.nextLine();

        System.out.print("Amount to transfer: ");
        amount = ui.nextInt();

        ui.nextLine();

        pinVerify();

        tranferDetails[0] = "Account Number: " + name;
        tranferDetails[1] = "Accout Name: " + number;
        tranferDetails[2] = "Amount: " + Integer.toString(amount);

        System.out.println();
        System.out.println("Transfer Details:");
        for (String details : tranferDetails) {
            System.out.println(details);
        }

        System.out.print("Is this the account details? [y/n]? ");
        choice = ui.next().charAt(0);
        switch (Character.toLowerCase(choice)) {
            case 'y':
                break;
            case 'n':
                transferMoney();
                break;
            default:
                transferMoney();
        }
        System.out.println();

        if (amount > money) {
            System.out.println();
            System.out.println("Insufficient balance!");
            transferMoney();
        } 

        if (!validAmount(amount)) {
            System.out.println();
            transferMoney();
        } else {
            money -= amount;
            System.out.printf("Your remaining balance is: %,d\n", money);
            System.out.println("Thank you for banking with us!");
        }

        if (anotherTrans()) {
            mainMenu();
        }

    }
    // -------------------------------------

    // Balance method
    static void balance() {

        System.out.println();

        ui.nextLine();

        pinVerify();

        System.out.printf("Balance: %,d\n", money);
        System.out.println("Thank you for banking with us!");

        if (anotherTrans()) {
            mainMenu();
        }

    }
    // -------------------------------------

    // Main method
    public static void main(String[] args) {
        clear();

        pinVerify();
    
        mainMenu();

    }
    // -------------------------------------
}