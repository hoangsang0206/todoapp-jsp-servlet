/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Sang
 */
public class FormatLocalDateTime {
    public static String format(LocalDateTime datetime) {
        return datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    public static String formatSQL(LocalDateTime datetime) {
        return datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
