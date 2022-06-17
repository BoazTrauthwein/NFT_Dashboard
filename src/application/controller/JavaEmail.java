package application.controller;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class JavaEmail
{
    Session mailSession;
    String title = "";
    String msg = "";
    String toEmails[] =  { "brechersivan@gmail.com" };
    public static void main(String args[]) throws AddressException, MessagingException
    {
        JavaEmail javaEmail = new JavaEmail();
        javaEmail.setMailServerProperties();
        javaEmail.setTitle("NFT_DASHBOARD");
        javaEmail.setMessage("Haide Brecher!");
        javaEmail.draftEmailMessage();
        javaEmail.sendEmail();
    }
    
    public static void sendMailNow(String msg, String[] EmailRecipient)
    {
    	JavaEmail javaEmail = new JavaEmail();
        javaEmail.setMailServerProperties();
        javaEmail.setTitle("NFT_DASHBOARD");
        javaEmail.setRecipients(EmailRecipient);
        javaEmail.setMessage(msg);
        
        try {
			javaEmail.draftEmailMessage();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			javaEmail.sendEmail();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void setTitle(String title) {
    	this.title = title; //"NFT_DASHBOARD";
    }
    
    public void setMessage(String msg) {
    	this.msg = msg; // "Haide Brecher!"
    }
    
    public void setRecipients(String[] to)
    {
    	this.toEmails = to;
    }
 
    private void setMailServerProperties()
    {
        Properties emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", "587");
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        emailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailSession = Session.getDefaultInstance(emailProperties, null);
    }
 
    private MimeMessage draftEmailMessage() throws AddressException, MessagingException
    {
        //this.toEmails = { "brechersivan@gmail.com" };
        String emailSubject = this.title;
        String emailBody = this.msg;
        MimeMessage emailMessage = new MimeMessage(mailSession);
        /**
         * Set the mail recipients
         * */
        for (int i = 0; i < toEmails.length; i++)
        {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
        }
        emailMessage.setSubject(emailSubject);
        /**
         * If sending HTML mail
         * */
       // emailMessage.setContent(emailBody, "text/html");
        emailMessage.setContent(emailBody,  "text/plain; charset=UTF-8");
        //msg.setContent(message, "text/plain; charset=UTF-8");
        /**
         * If sending only text mail
         * */
        //emailMessage.setText(emailBody);// for a text email
        return emailMessage;
    }
 
    private void sendEmail() throws AddressException, MessagingException
    {
        /**
         * Sender's credentials
         * */
        String fromUser = "sivanlove31@gmail.com";
        String fromUserEmailPassword = "hiwrsvocyydqmshj";
 
        String emailHost = "smtp.gmail.com";
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        /**
         * Draft the message
         * */
        MimeMessage emailMessage = draftEmailMessage();
        /**
         * Send the mail
         * */
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }
}