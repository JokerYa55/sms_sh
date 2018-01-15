/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.util;

import rtk.interfaces.senderInterface;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author vasiliy.andricov
 */
public class emailSender implements senderInterface {

    private final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(getClass().getName());
    //private String to = "receive@abc.om";         // sender email 
    private String from = "sender@abc.com";       // receiver email 
    private String host = "127.0.0.1";            // mail server host
    private Session session = null;

    public emailSender(String host, String port, String from, String user, String password) {
        try {
            log.info("emailSender");
            this.from = from;
            this.host = host;
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.auth", "true");
            //properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            log.info("properties => " + properties);
            Authenticator auth = new emailAuthenticator("andr_vasil", "123");
            log.info("auth => " + auth);
            log.info("1111");
            this.session = Session.getDefaultInstance(properties, auth);
            //this.session = Session.getDefaultInstance(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String send(String address, String message) {
        log.info("emailSender -> send");
        String res = null;
        try {
            // создаем письмо
            MimeMessage email_message = new MimeMessage(session); // email message 
            email_message.setFrom(new InternetAddress(from)); // setting header fields  
            email_message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
            email_message.setSubject("RTK Kod"); // subject line 
            email_message.setText(message);
            // Send message 
            Transport.send(email_message);
            System.out.println("Email Sent successfully....");
            res = "Ok";
        } catch (Exception ex) {
            Logger.getLogger(emailSender.class.getName()).log(Level.SEVERE, null, ex);
            res = "Error: " + ex.getMessage();
        }
        return res;
    }
}
