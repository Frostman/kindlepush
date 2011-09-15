package ru.frostman.kindle.kindlepush.util;

import ru.frostman.kindle.kindlepush.config.KindlePushConfig;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.*;
import java.io.File;
import java.util.Properties;

import static ru.frostman.kindle.kindlepush.KindlePush.getTraySupport;

/**
 * @author slukjanov aka Frostman
 */
public class Mail {

    public static void send(File file, boolean convert) {
        try {
            KindlePushConfig config = KindlePushConfig.get();

            Address[] to = new Address[]{
                    new InternetAddress(config.getFromMailLogin()),
                    new InternetAddress(config.getKindleMail())
            };

            Properties props = prepareProperties();

            Session mailSession = Session.getDefaultInstance(props);
            //mailSession.setDebug(true);
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(convert ? "convert" : "");
            message.addRecipients(Message.RecipientType.TO, to);
            message.addFrom(new Address[]{new InternetAddress(config.getFromMailLogin())});

            MimeBodyPart body = new MimeBodyPart();
            body.setContent("", "text/plain");

            addAttachment(file, message, body);
            sendMessage(config, mailSession, message);

            getTraySupport().showMessage("KindlePush", "Successfully sent to Kindle: " + file.getAbsolutePath(),
                    TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            //todo remove
            throw new RuntimeException(e);
        }
    }

    private static Properties prepareProperties() {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.auth", "true");

        return props;
    }

    private static void sendMessage(KindlePushConfig config, Session mailSession, MimeMessage message) throws MessagingException {
        Transport transport = mailSession.getTransport();
        transport.connect("smtp.gmail.com", 465, config.getFromMailLogin(), config.getFromMailPassword());
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }

    private static void addAttachment(File file, MimeMessage message, MimeBodyPart body) throws MessagingException {
        MimeBodyPart attachment = new MimeBodyPart();
        FileDataSource dataSource = new FileDataSource(file);
        attachment.setDataHandler(new DataHandler(dataSource));
        attachment.setFileName(file.getName());
        attachment.setDisposition(MimeBodyPart.ATTACHMENT);
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(body);
        multipart.addBodyPart(attachment);
        message.setContent(multipart);
    }

}
