/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Sang
 */
public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime> {
    private DateTimeFormatter formatter;
    
    public LocalDateTimeAdapter() {
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    @Override
     public JsonElement serialize(LocalDateTime t, Type type, JsonSerializationContext jsc) {
         return new JsonPrimitive(formatter.format(t));
    }
}
