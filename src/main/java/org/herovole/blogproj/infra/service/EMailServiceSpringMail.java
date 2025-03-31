package org.herovole.blogproj.infra.service;

import org.herovole.blogproj.domain.EMailAddress;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.SiteInformation;
import org.herovole.blogproj.domain.adminuser.EMailService;
import org.herovole.blogproj.domain.adminuser.VerificationCode;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EMailServiceSpringMail implements EMailService {

    private final JavaMailSender mailSender;
    private final SiteInformation siteInformation;

    public EMailServiceSpringMail(JavaMailSender mailSender,
                                  SiteInformation siteInformation) {
        this.mailSender = mailSender;
        this.siteInformation = siteInformation;
    }

    @Override
    public void sendVerificationCode(IPv4Address ip, EMailAddress emailAddress, VerificationCode verificationCode) {

        String subject = "[認証コード送付] " + siteInformation.getSiteNameJp() + "/" + siteInformation.getSiteNameEn();
        String text = siteInformation.getSiteDomain() + " 管理者向け認証コードです。\n" +
                verificationCode.letterSignature();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress.letterSignature());
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}

