/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */package com.mycompany.chatapp;

import org.json.JSONObject;
import java.io.*;
import java.util.*;
 
 
public class Message {
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageID;
    private String messageHash;
    private String sendStatus;

    // Part 2 arrays
    private static ArrayList<String> sentMessages = new ArrayList<>();
    private static ArrayList<String> sentRecipients = new ArrayList<>();
    private static ArrayList<String> sentHashes = new ArrayList<>();
    private static int storedThisSession = 0;
    private static ArrayList<String> storedMessages = new ArrayList<>();

    // Part 3 arrays - for search/report
    private static ArrayList<String> messageHashes = new ArrayList<>();
    private static ArrayList<String> messageIDs = new ArrayList<>();
    private static ArrayList<String> recipientList = new ArrayList<>();
    private static ArrayList<String> allMessageTexts = new ArrayList<>();

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient; // fixed typo
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000L + (long)(rand.nextDouble() * 9000000L);
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient.startsWith("+27") && recipient.length() == 12)
            return "Cell phone number successfully captured.";
        else
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength(String message) {
        if (message.length() <= 250)
            return "Message ready to send.";
        else
            return "Message exceeds 250 characters by " + (message.length() - 250) + "; please reduce the size.";
    }

    public String createMessageHash() {
        String idPart = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0? words[0].replaceAll("[^a-zA-Z0-9]", "") : "X";
        String lastWord = words.length > 0? words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "") : "X";
        return (idPart + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public String sentMessage(int option) {
        switch (option) {
            case 1: // Send
                sendStatus = "Sent";
                sentMessages.add(this.messageText);
                sentHashes.add(this.messageHash);
                sentRecipients.add(this.recipient);
                // Part 3: add to all arrays
                messageHashes.add(this.messageHash);
                messageIDs.add(this.messageID);
                recipientList.add(this.recipient);
                allMessageTexts.add(this.messageText);
                return "Message successfully sent.";

            case 2: // Disregard
                sendStatus = "Disregarded";
                return "Press 0 to delete message.";

            case 3: // Store
                sendStatus = "Stored";
                storedMessages.add(this.messageText);
                storedThisSession++;
                // Part 3: add to all arrays + save JSON
                messageHashes.add(this.messageHash);
                messageIDs.add(this.messageID);
                recipientList.add(this.recipient);
                allMessageTexts.add(this.messageText);
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option";
        }
    }

    // ========== PART 3 METHODS ==========

    public void storeMessage() {
        try {
            JSONArray jsonArray = new JSONArray();
            File file = new File("messages.json");
            if (file.exists()) {
                Scanner sc = new Scanner(file);
                String content = sc.useDelimiter("[file://\\Z").next(]\\Z").next();
                sc.close();
                if (!content.trim().isEmpty()) {
                    jsonArray = new JSONArray(content);
                }
            }

            JSONObject obj = new JSONObject();
            obj.put("messageID", this.messageID);
            obj.put("recipient", this.recipient);
            obj.put("message", this.messageText);
            obj.put("hash", this.messageHash);
            obj.put("status", this.sendStatus);
            jsonArray.put(obj);

            FileWriter fw = new FileWriter("messages.json");
            fw.write(jsonArray.toString(4));
            fw.close();
        } catch (Exception e) {
            System.out.println("Error saving message: " + e.getMessage());
        }
    }

    public static void loadStoredMessages() {
        try {
            File file = new File("messages.json");
            if (!file.exists()) return;

            Scanner sc = new Scanner(file);
            String content = sc.useDelimiter("[file://\\Z").next(]\\Z").next();
            sc.close();
            if (content.trim().isEmpty()) return;

            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                messageIDs.add(obj.getString("messageID"));
                recipientList.add(obj.getString("recipient"));
                allMessageTexts.add(obj.getString("message"));
                messageHashes.add(obj.getString("hash"));
            }
        } catch (Exception e) {
            System.out.println("Error loading messages: " + e.getMessage());
        }
    }

    public static String searchByMessageID(String id) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(id)) {
                return "Message ID: " + id + "\nRecipient: " + recipientList.get(i) + "\nMessage: " + allMessageTexts.get(i) + "\nHash: " + messageHashes.get(i);
            }
        }
        return "Message not found.";
    }

    public static String searchByRecipient(String recipient) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < recipientList.size(); i++) {
            if (recipientList.get(i).equals(recipient)) {
                result.append("Recipient: ").append(recipient).append("\nMessage: ").append(allMessageTexts.get(i)).append("\nHash: ").append(messageHashes.get(i)).append("\n---\n");
            }
        }
        return result.length() > 0? result.toString() : "No messages found for this recipient.";
    }

    public static String displayLongestMessage() {
        if (allMessageTexts.isEmpty()) return "No messages stored.";
        String longest = allMessageTexts.get(0);
        for (String msg : allMessageTexts) {
            if (msg.length() > longest.length()) longest = msg;
        }
        return "Longest message: " + longest;
    }

    public static String printMessages() {
        if (allMessageTexts.isEmpty()) return "No messages to report.";
        StringBuilder report = new StringBuilder("=== FULL REPORT ===\n");
        for (int i = 0; i < allMessageTexts.size(); i++) {
            report.append("ID: ").append(messageIDs.get(i)).append("\nRecipient: ").append(recipientList.get(i)).append("\nMessage: ").append(allMessageTexts.get(i)).append("\nHash: ").append(messageHashes.get(i)).append("\n---\n");
        }
        return report.toString();
    }

    public static int returnTotalMessages() {
        return sentMessages.size() + storedThisSession;
    }

    public static boolean deleteByHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
                messageHashes.remove(i);
                messageIDs.remove(i);
                recipientList.remove(i);
                allMessageTexts.remove(i);
                return true;
            }
        }
        return false;
    }

    public static void clearMessages() {
        sentMessages.clear();
        sentRecipients.clear();
        sentHashes.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        recipientList.clear();
        allMessageTexts.clear();
        storedThisSession = 0;
    }

    // Getters for tests
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
} 
}
