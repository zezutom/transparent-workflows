package org.zezutom.crashtracker.util;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 29/04/2012
 * Time: 14:45
 *
 * Extracts information from the javax.mail.internet.MimeMessage
 */
public class MailParser {

    private MimeMessage message;

    public MailParser(MimeMessage message) {
        this.message = message;
    }

    public String getSubject() throws MessagingException {
        return message.getSubject();
    }

    public String getBody() throws IOException, MessagingException {
        MimeMultipart part = (MimeMultipart) message.getContent();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        part.getBodyPart(0).writeTo(baos);

        return new String(baos.toByteArray());
    }

    public String getRecipient() throws MessagingException {
        String recipient = null;

        Address[] recipients = message.getRecipients(Message.RecipientType.TO);

        if (recipients == null || recipients.length < 1) {
            return null;
        }

        return recipients[0].toString();
    }
}
