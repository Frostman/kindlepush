package ru.frostman.kindle.kindlepush.util;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

/**
 * @author slukjanov aka Frostman
 */
public class Mail {
    private static final String username = "kindle-push-noreply@frostman.ru";
    private static final String password = "asdasdkAD#ekhf3";

    public static void send(String toAddr, File file, boolean convert) {
        try {
            Address[] to = new Address[]{
                    //new InternetAddress("me+checker-noreply-copy@frostman.ru"),
                    new InternetAddress(toAddr)
            };

            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtps.host", "smtp.gmail.com");
            props.put("mail.smtps.auth", "true");

            Session mailSession = Session.getDefaultInstance(props);
            //mailSession.setDebug(true);
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(convert ? "convert" : "");
            message.addRecipients(Message.RecipientType.TO, to);
            message.addFrom(new Address[]{new InternetAddress(username)});

            MimeBodyPart body = new MimeBodyPart();
            body.setContent("", "text/plain");

            // add attachment
            MimeBodyPart attachment = new MimeBodyPart();
            FileDataSource dataSource = new FileDataSource(file);
            attachment.setDataHandler(new DataHandler(dataSource));
            attachment.setFileName(file.getName());
            attachment.setDisposition(MimeBodyPart.ATTACHMENT);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);
            multipart.addBodyPart(attachment);
            message.setContent(multipart);

            Transport transport = mailSession.getTransport();
            transport.connect("smtp.gmail.com", 465, username, password);
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (Exception e) {
            //todo remove
            throw new RuntimeException(e);
        }
    }

}
