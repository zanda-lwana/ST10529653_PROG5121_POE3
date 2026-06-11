/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */package com.mycompany.chatapp;

import java.util.Scanner;
 
public class QuickChatApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login login = new Login();
        int messageCounter = 0;

        System.out.println("=== REGISTER ===");
        System.out.print("Enter username: ");
        String user = sc.nextLine();
        System.out.print("Enter password: ");
        String pass = sc.nextLine();
        System.out.print("Enter cell +27...: ");
        String cell = sc.nextLine();
        System.out.println(login.registerUser(user, pass, cell));

        System.out.println("\n=== LOGIN ===");
        System.out.print("Enter username: ");
        String loginUser = sc.nextLine();
        System.out.print("Enter password: ");
        String loginPass = sc.nextLine();
        String loginStatus = login.loginUser(loginUser, loginPass);
        System.out.println(loginStatus);

        if (loginStatus.contains("Welcome")) {
            Message.loadStoredMessages(); // Load JSON

            int choice;
            do {
                System.out.println("\n=== MESSAGE MENU ===");
                System.out.println("1. Send Message");
                System.out.println("2. Show Report");
                System.out.println("3. Search by Message ID");
                System.out.println("4. Search by Recipient");
                System.out.println("5. Longest Message");
                System.out.println("6. Total Messages");
                System.out.println("7. Delete by Hash");
                System.out.println("8. Quit");
                System.out.print("Choose option: ");
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter recipient +27...: ");
                        String recipient = sc.nextLine();
                        System.out.print("Enter message max 250 chars: ");
                        String text = sc.nextLine();

                        Message msg = new Message(messageCounter++, recipient, text);

                        String check1 = msg.checkRecipientCell();
                        if (!check1.equals("Cell phone number successfully captured.")) {
                            System.out.println(check1);
                            break;
                        }

                        String check2 = msg.checkMessageLength(text);
                        if (!check2.equals("Message ready to send.")) {
                            System.out.println(check2);
                            break;
                        }

                        System.out.println("1.Send 2.Disregard 3.Store");
                        int sendChoice = sc.nextInt();
                        sc.nextLine();
                        System.out.println(msg.sentMessage(sendChoice));
                        break;

                    case 2:
                        System.out.println(Message.printMessages());
                        break;
                    case 3:
                        System.out.print("Enter Message ID: ");
                        String id = sc.nextLine();
                        System.out.println(Message.searchByMessageID(id));
                        break;
                    case 4:
                        System.out.print("Enter Recipient +27...: ");
                        String rec = sc.nextLine();
                        System.out.println(Message.searchByRecipient(rec));
                        break;
                    case 5:
                        System.out.println(Message.displayLongestMessage());
                        break;
                    case 6:
                        System.out.println("Total messages: " + Message.returnTotalMessages());
                        break;
                    case 7:
                        System.out.print("Enter Message Hash: ");
                        String hash = sc.nextLine();
                        boolean deleted = Message.deleteByHash(hash);
                        System.out.println(deleted? "Message deleted successfully" : "Hash not found");
                        break;
                    case 8:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            } while (choice!= 8);
        }
        sc.close();
    }
}
}