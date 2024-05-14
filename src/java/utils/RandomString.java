/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.security.SecureRandom;
import java.util.Random;

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
    
    public static String randomCode(int length) {
        Random random = new Random();
        String code = "";
        for(int i = 0; i < length; i++) {
            code += String.valueOf(random.nextInt(9999));
        }
        
        return code;
    }
}
