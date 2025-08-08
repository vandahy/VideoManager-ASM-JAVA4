package com.java04.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailUtils {

    public static void sendEmail(String to, String subject, String content) throws MessagingException {
        String from = "nghiantts00708@fpt.edu.vn"; // Tài khoản Gmail gửi
        String password = "bvbblggmkgekbauf"; // Mật khẩu ứng dụng Gmail

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(to)
        );
        message.setSubject(subject);
        message.setText(content);

        Transport.send(message);
    }
}

