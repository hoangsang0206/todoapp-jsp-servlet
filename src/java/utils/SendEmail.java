/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Sang
 */
public class SendEmail {
    private static final String EMAIL = "hoangsang.todolist@gmail.com";
    private static final String PASSWORD = "kpuulispxovabpdr";
    
    public static boolean sendEmail(String emailToSend, String message) {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587"); //TLS: 587; SSL: 465
            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.starttls.enable", true);
            
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL, PASSWORD);
                }
            };
            
            Session session = Session.getInstance(properties, auth);
            
            MimeMessage msg = new MimeMessage(session);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailToSend, false));
            msg.setSubject("Xác nhận Email");
//            msg.setSentDate(new Date());
            msg.setContent(message, "text/html; charset=UTF-8");
            
            Transport.send(msg);

            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
}
