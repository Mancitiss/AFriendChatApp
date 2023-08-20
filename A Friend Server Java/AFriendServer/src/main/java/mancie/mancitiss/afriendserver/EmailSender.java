package mancie.mancitiss.afriendserver;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    private static final Properties GMAIL_PROPERTIES;

    static {
        GMAIL_PROPERTIES = new Properties();
        GMAIL_PROPERTIES.put("mail.smtp.auth", "true");
        GMAIL_PROPERTIES.put("mail.smtp.host", "smtp.gmail.com");
        GMAIL_PROPERTIES.put("mail.smtp.port", "587");
        GMAIL_PROPERTIES.put("mail.smtp.starttls.enable", "true");
    }

    public static Properties getGmailProperties() {
        return GMAIL_PROPERTIES;
    }

    public static void main(String[] args) {

        Session session = Session.getInstance(GMAIL_PROPERTIES,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(System.getenv("afriendusername"), System.getenv("afriendpass"));
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("vovuongthanhtu@gmail.com"));
            message.setSubject("Testing JavaMail API");
            message.setText("This is a test email sent using JavaMail API.");

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendEmail(String email, String subject, String body) {
        try {

            Session session = Session.getInstance(GMAIL_PROPERTIES,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(System.getenv("afriendusername"), System.getenv("afriendpass"));
                }
            });

            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully to " + email);
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
