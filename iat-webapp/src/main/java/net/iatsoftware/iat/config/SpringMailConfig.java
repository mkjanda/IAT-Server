/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.config;

/**
 *
 * @author michael
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Order(4)
@Configuration
@PropertySource("classpath:email/email-config.properties")
public class SpringMailConfig implements ApplicationContextAware {
    
    private final String JAVA_MAIL_FILE = "classpath:email/java-mail-config.properties";
    private ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        applicationContext = ctx;
    }
    
    @Value("${mail.server.host}")
    private String mailServerHost;
    
    @Value("${mail.server.protocol}")
    private String mailServerProtocol;
    
    @Value("${mail.server.port}")
    private int mailServerPort;
    
    @Value("${mail.server.username}")
    private String mailServerUsername;
    
    @Value("${mail.server.password}")
    private String mailServerPassword;
    
    @Bean
    public JavaMailSender mailSender() throws java.io.IOException {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailServerHost);
        mailSender.setPort(mailServerPort);
        mailSender.setProtocol(mailServerProtocol);
        mailSender.setUsername(mailServerUsername);
        mailSender.setPassword(mailServerPassword);
        
        final Properties javaMailProperties = new Properties();
        javaMailProperties.load(applicationContext.getResource(JAVA_MAIL_FILE).getInputStream());
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }
}
