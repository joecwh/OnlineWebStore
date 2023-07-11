import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class EmailServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String senderEmail = "chowwh-am21@student.tarc.edu.my"; // Replace with your Gmail email address
        String senderPassword = "joechow6813"; // Replace with your Gmail password

        String recipientEmail = "cjoe_54@hotmail.com";
//                request.getParameter("recipientEmail");
        String subject = "testing";
//                request.getParameter("subject");
        String messageText = "hello world, hello joe";
//                request.getParameter("message");

        // SMTP server configuration
        String smtpHost = "smtp.gmail.com";
        int smtpPort = 587;

        // SMTP properties
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create a Session with SMTP authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

            // Set the email subject and body
            message.setSubject(subject);
            message.setText(messageText);

            // Send the email
            Transport.send(message);

            response.getWriter().println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            response.getWriter().println("Failed to send email. Please check your SMTP configuration.");
        }
    }
}
