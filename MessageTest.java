/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */package com.mycompany.chatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @BeforeEach
    public void setUp() {
        // Reset all static parallel arrays before each test so they don't interfere
        Message.clearMessages();
    }

    // ========== PART 2 TESTS - Validation ==========
   
    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message(0,"+27718693002","Hi Mike can you join us for dinner tonight");
        String result= 
                msg.checkMessageLength(msg.messageText);
        assertEquals(Ready to send.",msg.checkMessageLength());
    }
    @Test
    public void testMessageLengthFailure() {
        String longMessage = "a".repeat(260);
        Message msg = new Message(0,"+27718693002",longMessage);
        String result= 
                msg.checkMessageLength(longMessage);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",msg.checkMessageLength());
    }
    @Test
    public void testRecipientSuccess() {
        Message msg = new Message(0,"+27718693002","Hello");
        String result= 
                msg.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.",
        msg.checkRecipientCell());
    }
    @Test
    public void testRecipientFailure() {
        @Test
        Message msg = new Message(0,"08575975889","Hello");
        String result= 
                msg.checkRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",msg.checkRecipientCell());
    }
    @Test
    public void testMessageHash() {
        Message msg = new Message(0,"+27718693002","Hi Mike can you join us for dinner tonight");
        String hash = msg.createMessageHash();
        assertTrue(hash.contains(":0:HITONIGHT"),"Hash should end with :0:HITONIGHT");
    }
    @Test
    public void testMessageID() {
     Message msg = new Message(0,"+27718693002","Hello");
     assertTrue(msg.checkMessageID(), "Message ID should be <=10 chars");
     
     //====================Part 3 Tests - JSON + Search +Report ============
     @Test
    public void testSearchByMessageID_Success() {
        Message msg = new Message(1, "+27831234567", "Test message");
        msg.sentMessage(3); // Store it so it enters all processed arrays
       
        String result = Message.searchByMessageID(msg.getMessageID());
        assertTrue(result.contains("Test message"), "Should find message by ID");
    }

    @Test
    public void testSearchByMessageID_Fail() {
        String result = Message.searchByMessageID("9999");
        assertEquals("Message not found.", result);
    }

    @Test
    public void testSearchByRecipient_Success() {
        Message msg1 = new Message(1, "+27830000001", "Msg 1");
        Message msg2 = new Message(2, "+27830000001", "Msg 2");
        msg1.sentMessage(3);
        msg2.sentMessage(3);
       
        String result = Message.searchByRecipient("+27830000001");
        assertTrue(result.contains("Msg 1"));
        assertTrue(result.contains("Msg 2"));
    }

    @Test
    public void testDisplayLongestMessage() {
        Message msg1 = new Message(1, "+27830000001", "Short");
        Message msg2 = new Message(2, "+27830000002", "This is definitely the longest message in this test");
        msg1.sentMessage(3);
        msg2.sentMessage(3);
       
        String result = Message.displayLongestMessage();
        assertTrue(result.contains("longest message in this test"));
    }

    @Test
    public void testDeleteByHash_Success() {
        Message msg = new Message(1, "+27830000001", "Delete me");
        msg.sentMessage(1); // Send it
        String hash = msg.getMessageHash();
       
        boolean deleted = Message.deleteByHash(hash);
        assertTrue(deleted, "Should delete message with valid hash");
       
        String search = Message.searchByMessageID(msg.getMessageID());
        assertEquals("Message not found.", search);
    }

    @Test
    public void testDeleteByHash_Fail() {
        boolean deleted = Message.deleteByHash("FAKE:HASH:XYZ");
        assertFalse(deleted, "Should return false for invalid hash");
    }

    @Test
    public void testReturnTotalMessages() {
        Message msg1 = new Message(1, "+27830000001", "Sent");
        Message msg2 = new Message(2, "+27830000002", "Stored");
        msg1.sentMessage(1); // Sent
        msg2.sentMessage(3); // Stored
       
        assertEquals(2, Message.returnTotalMessages());
    }
    }
}
