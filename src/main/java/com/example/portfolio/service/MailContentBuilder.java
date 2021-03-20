package com.example.portfolio.service;


import lombok.AllArgsConstructor;
import org.hibernate.sql.Template;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.net.ContentHandler;

@Service
@AllArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;
    String build(String message){
        Context context=new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);

    }
}
