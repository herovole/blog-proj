package org.herovole.blogproj.infra.service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.Builder;
import org.herovole.blogproj.domain.EMailAddress;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.SiteInformation;
import org.herovole.blogproj.domain.adminuser.EMailService;
import org.herovole.blogproj.domain.adminuser.VerificationCode;
import org.herovole.blogproj.domain.time.Timestamp;

import java.io.IOException;
import java.util.Properties;

@Builder
public class EMailServiceAmazonSES implements EMailService {

    private final String smtpHost;
    private final String smtpPort;
    private final String smtpUser;
    private final String smtpPassword;
    private final String mailFrom;
    private final SiteInformation siteInformation;


    @Override
    public void sendVerificationCode(IPv4Address ip, EMailAddress emailAddress, VerificationCode verificationCode) throws IOException {

        try {

            Properties props = new Properties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", smtpPort);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(smtpUser, smtpPassword);
                }
            });

            String subject = "[認証コード送付] " + siteInformation.getSiteNameJp() + "/" + siteInformation.getSiteNameEn();
            String text = siteInformation.getSiteDomain() + " 管理者向け認証コードです。\n" +
                    verificationCode.letterSignature() + "\n" +
                    "IP : " + ip.toRegularFormat() + "\n" +
                    "発送時刻 : " + Timestamp.now().letterSignatureFrontendDisplay() + "\n";

            // Create Email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFrom));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress.memorySignature()));
            message.setSubject(subject);
            message.setText(text);


            // Send Email
            Transport.send(message);

        } catch (MessagingException e) {
            throw new IOException(e);
        }

    }
}

