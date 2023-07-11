/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Repository.IEmailRepository;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.Email;
import model.Subscriber;

/**
 *
 * @author Lenovo
 */
public class EmailService implements IEmailRepository
{
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    private String senderEmail;
    private String senderPassword;
    
    public EmailService(
        UserTransaction utx,
        EntityManager em,
        String senderEmail,
        String senderPassword)
    {
        this.utx = utx;
        this.em = em;
        this.senderEmail = senderEmail;
        this.senderPassword = senderPassword;
    }
    
    public void sendEmail(Email email) {
        String host = "smtp.gmail.com";
        int port = 587;

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getRecipient()));
            message.setSubject(email.getSubject());
            message.setContent(email.getMessage(), "text/html");

            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean SendVerifyEmail(Email email, String htmlTemplate) {
        try
        {
            String templateContent = new String(Files.readAllBytes(Paths.get(htmlTemplate)));
            String verifyLink = "http://localhost:8080/Den_s_Toy/verify.jsp?token=" + email.getMessage();
            String htmlContent = templateContent.replace("{{verifyLink}}", verifyLink);
            
            email.setMessage(htmlContent);
            sendEmail(email);

            return true;
        }
        catch(Exception ex)
        {
            System.out.println("send email failed : " + ex.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean SendAccountSetupEmail(Email email, String htmlTemplate, String tempass) {
        try
        {
            String templateContent = new String(Files.readAllBytes(Paths.get(htmlTemplate)));
            String verifyLink = "http://localhost:8080/Den_s_Toy/verify.jsp?token=" + email.getMessage();
            String htmlContent = templateContent
                    .replace("{{VerifyLink}}", verifyLink)
                    .replace("{{TemporaryPassword}}", tempass);
            
            email.setMessage(htmlContent);
            sendEmail(email);

            return true;
        }
        catch(Exception ex)
        {
            System.out.println("send email failed : " + ex.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean SendResetPasswordEmail(Email email, String htmlTemplate) {
        try
        {
            String templateContent = new String(Files.readAllBytes(Paths.get(htmlTemplate)));
            String htmlContent = templateContent.replace("{{temporaryPassword}}", email.getMessage());
            
            email.setMessage(htmlContent);
            sendEmail(email);

            return true;
        }
        catch(Exception ex)
        {
            System.out.println("send email failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean SendSubscribeEmail(Email email) {
        try
        {
            String templateContent = new String(Files.readAllBytes(Paths.get(email.getMessage())));
            email.setMessage(templateContent);
            sendEmail(email);

            return true;
        }
        catch(Exception ex)
        {
            System.out.println("send email failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean CheckSubscriberExist(String email) {
        List<Subscriber> user = em.createNamedQuery("Subscriber.findByEmail").setParameter("email", email).getResultList();
        
        return !user.isEmpty();
    }

    @Override
    public boolean AddSubscriber(String email) {
        try
        {
            Subscriber newSubscriber = new Subscriber(email);
            
            utx.begin();
            em.persist(newSubscriber);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("Add subscriber fail : " + ex.getMessage());
        }
        return false;
    }
        
    @Override
    public boolean SendFeedbackResponseEmail(Email email) {
        try
        {
            String templateContent = new String(Files.readAllBytes(Paths.get(email.getMessage())));
            email.setMessage(templateContent);
            sendEmail(email);

            return true;
        }
        catch(Exception ex)
        {
            System.out.println("send feedback response failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean SendPurchaseEmail(Email email, String name, String htmlTemplate) {
        try
        {
            String templateContent = new String(Files.readAllBytes(Paths.get(htmlTemplate)));
            String htmlContent = templateContent
                    .replace("{{customerName}}", name)
                    .replace("{{orderID}}", email.getMessage());
            
            email.setMessage(htmlContent);
            sendEmail(email);

            return true;
        }
        catch(Exception ex)
        {
            System.out.println("send email failed : " + ex.getMessage());
        }
        return false;
    }
}
