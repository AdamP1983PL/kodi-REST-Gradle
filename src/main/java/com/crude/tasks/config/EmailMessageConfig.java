package com.crude.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class EmailMessageConfig {

    @Value("${company.details}")
    private String companyDetails;

    @Value("${welcome.message}")
    private String welcomeMessage;

    @Value("${goodbye.message}")
    private String goodbyeMessage;

}
