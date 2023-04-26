import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
public class services {
  
    public static void sendMail(String username,String usermail) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("your-email@gmail.com", "your-password");
            }
        });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("your-email@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(usermail));
        message.setSubject("Registration Confirmation");
        message.setText("Congrats "+ username+ " you're now registered in LearnHub platform where you can learn and teach for free !");
        Transport.send(message);
    }

}
