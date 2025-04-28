package com.rodolfocf.notification.business;

import com.rodolfocf.notification.business.dto.TaskDTO;
import com.rodolfocf.notification.infrastructure.exceptions.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    
    @Value("${send.email.sender}")
    public String sender;

    @Value("${send.email.senderName}")
    public String senderName;

    public void sendEmail(TaskDTO taskDTO){
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(new InternetAddress(sender, senderName));
            mimeMessageHelper.setTo(InternetAddress.parse(taskDTO.getClientEmail()));
            mimeMessageHelper.setSubject(("Task Notification)"));

            Context context = new Context();
            context.setVariable("taskName", taskDTO.getTaskName());
            context.setVariable("taskDate", taskDTO.getTaskDate());
            context.setVariable("description", taskDTO.getDescription());
            String template = templateEngine.process("notification", context);
            mimeMessageHelper.setText(template, true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailException("Error to send email, caused by: ", e.getCause());
        }


    }



}
