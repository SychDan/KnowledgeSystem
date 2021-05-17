package knowledge.service.email_service;

import knowledge.model.Mail;

import freemarker.template.TemplateException;

import javax.mail.MessagingException;

import java.io.IOException;

public interface EmailService {
    void sendSimpleMessage(Mail mail) throws MessagingException, IOException, TemplateException;
}