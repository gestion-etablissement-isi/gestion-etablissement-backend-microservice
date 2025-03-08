package com.isi.institution.cours;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.config")
public class CoursConfig {
    private String coursUrl;

    public String getCoursUrl() {
        return coursUrl;
    }

    public void setCoursUrl(String coursUrl) {
        this.coursUrl = coursUrl;
    }
}
