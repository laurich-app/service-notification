package org.laurichapp.servicenotification.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class BeanConfiguration {
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.port}")
    private Integer springMailPort;

    @Value("${spring.mail.host}")
    private String springMailHost;

    @Value("${spring.mail.username}")
    private String springMailUsername;

    @Value("${spring.mail.password}")
    private String springMailPassword;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean startTlsEnable;

    @Value("${spring.mail.properties.mail.smtp.ssl.trust}")
    private String sslTrust;

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", smtpAuth);
        properties.put("mail.smtp.starttls.enable", startTlsEnable);
        properties.put("mail.smtp.ssl.trust", sslTrust);
        return properties;
    }

    @PostConstruct
    public void init() {
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(this.springMailHost);
        javaMailSender.setPort(this.springMailPort);
        javaMailSender.setUsername(this.springMailUsername);
        javaMailSender.setPassword(this.springMailPassword);
        javaMailSender.setJavaMailProperties(this.getMailProperties());
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        return javaMailSender;
    }
}
