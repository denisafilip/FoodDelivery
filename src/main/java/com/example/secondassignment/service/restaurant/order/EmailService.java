package com.example.secondassignment.service.restaurant.order;

import com.example.secondassignment.model.Food;
import com.example.secondassignment.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EmailService {

    private final JavaMailSender emailSender = getJavaMailSender();

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("foodpandanoreply1@gmail.com");
        mailSender.setPassword("Parola_123");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("foodpandanoreply1@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public String generateEmailContent(Order order) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ordered foods:").append("\n");
        for (Food f : order.getFoods()) {
            stringBuilder.append(f).append("\n");
        }
        stringBuilder.append("Total price: ").append(order.getTotal()).append("\n");
        stringBuilder.append("Customer address: ").append(order.getCustomer().getAddress().getStreet())
                .append(", ").append(order.getCustomer().getAddress().getNumber());
        // specials details that the user wanted to add
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public void sendOrderEmail(Order order) {
        String content = generateEmailContent(order);
        String subject = "Order " + order.getIdOrder();
        sendSimpleMessage(order.getRestaurant().getAdministrator().getEmail(), subject, content);
    }
}