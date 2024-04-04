package org.laurichapp.servicenotification.services;

import org.springframework.beans.factory.annotation.Autowired;

class TestEmailServiceImpl extends TestEmailService {
    private final EmailService emailService;

    public TestEmailServiceImpl(@Autowired EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @Override
    public EmailService getInstance() {
        return this.emailService;
    }
}
