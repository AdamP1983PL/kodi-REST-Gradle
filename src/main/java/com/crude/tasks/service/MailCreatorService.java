package com.crude.tasks.service;

import com.crude.tasks.config.AdminConfig;
import com.crude.tasks.config.EmailMessageConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
//@AllArgsConstructor
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private EmailMessageConfig emailMessageConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;


    // TODO  (pamiętaj, aby podać swój adres url do aplikacji front-endowej):
    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("welcome_message", emailMessageConfig.getWelcomeMessage());
        context.setVariable("goodbye_message", emailMessageConfig.getGoodbyeMessage());
        context.setVariable("company_details", emailMessageConfig.getCompanyDetails());
        context.setVariable("admin_name", adminConfig.getAdminName());
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

}
