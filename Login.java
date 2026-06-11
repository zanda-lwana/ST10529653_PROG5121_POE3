/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */package com.mycomany.chatapp;
 
 
public class Login {
    //-----------------------------------------------
    //these variables store the details of the user
    //once a user registers , their details are saved here
    //-----------------------------------------------
    String username;
    String password;
    String phoneNumber;

    //========================================
    //1. CHECK USERNAME
    //you are required to ensure:
    //- username contains an underscore "_"
    //-username can't be more than 5 characters long 
    //-----------------------------------------------
    public boolean checkUserName( String username ) {
        {
    }
    //the username.contains("_") checks for the underscore 
    //username musn't be longer than 5 characters 
    return username.contains("_")&& username.length() <=5;
    //-------------------------------------------------------
    //2. check password complexity
    //password requirements:
    //-At least 8 characters
    //-At least one capital letter
    //-At least one number
    //-At least one special character 
    //--------------------------------------------------------
    public boolean CheckPasswordComplexity( String password ) {
        
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        for (int i = 0; i < password.length();i++){
            char c = password.charAt(i);
            
            if(Character.isUpperCase(c)){
                hasCapital = true;
            }else if (Character.isDigit(c)){
                    hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)){
                hasSpecial = true;
                        }
        }
        
        return password.length()>=8&& hasCapital && hasSpecial;
    }
    //-------------------------------------------------
    //3. check phoneNumber
    //what is expected :
    //phone number must start with +27
    // Must contain 10 digits
    //is not more than 12 characters long 
    //-------------------------------------------------------
    public boolean CheckphoneNumber (String phoneNumber){
        return phoneNumber.startsWith("+27")&& phoneNumber.length()<=12;
    }
    //-------------------------------------------------
    //4.Check registration
    //username
    //password
    //phoneNumber
    //stores data and everything required 
    //returns specific messages
    //------------------------------------------------
    public String registerUser(String username, String Password,String phoneNumber){
   
        if(checkUsername(username)){
       return " Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length";
        }
        if(checkpasswordComplexity(password)){
            return " Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter , a number, and a special charater.";
        }
        if(checkPhoneNumber(phoneNumber)){
            return" phoneNumber incorrectly formatted or does not contain international code";
        }
        this.username = username;
        this.password = password;
        this.Phonenumber = phoneNumber;
        return " User registered succesfully";
    }
    //--------------------------------------------
    //login feature
    //-----------------------------------------------
    public boolean loginUser(String username, String password){
        return this.username.equals(username)&& this.password.equals(password);
    }
    pubic string returnLoginStatus(boolean success){
        if(success){
            return "welcome" + username + "It is great to see you again.";
        }else{
            return " Username or password incorrect , please try again.";}
        
            
    
        
            
  
  
