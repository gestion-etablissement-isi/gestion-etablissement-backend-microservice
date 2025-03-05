package com.isi.institution.email;

import lombok.Getter;

public enum EmailTemplates {
   INSCRIPTION_CONFIRMATION("inscription-confirmation.html", "Inscription confirmation")
    ;
    @Getter
    private final String template;
    @Getter
    private final String subject;

    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
