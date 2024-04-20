/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stodo.models;

import java.security.SecureRandom;

/**
 *
 * @author Sang
 */
public class RandomString {
    public static String random(int length) {
        SecureRandom random = new SecureRandom();
        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder strBuilder = new StringBuilder(length);
        
        for(int i = 0; i < length; i++) {
            int ramdonIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(ramdonIndex);
            strBuilder.append(randomChar);
        }
        
        return strBuilder.toString();
    }
}
