package app.services;

import jakarta.validation.constraints.Email;
import org.springdoc.core.configuration.SpringDocUIConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public String sendEmailText(String recipient, String subject, String message){
        try{
            SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(recipient);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);
            javaMailSender.send(simpleMailMessage);
            return "Email sent";
        }catch (Exception e){
            return "Erro to send email"+e.getLocalizedMessage();
        }
    }
}
