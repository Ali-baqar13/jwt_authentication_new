package com.security.jwtAuthentication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;



@Configuration
@ConfigurationProperties(prefix ="app.security.")

public class KeyConfiguration {
    private String secretKey;

    public String getSecretKey() {
        return this.secretKey;
    }

    public String setSecretKey(String secretKey){
        return this.secretKey = secretKey;
    }

    
}
