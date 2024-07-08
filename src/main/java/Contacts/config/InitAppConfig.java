package Contacts.config;

import Contacts.ContactInitializing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Profile("init")
public class InitAppConfig {
    @Bean
    public ContactInitializing contactInitializing(){
        return new ContactInitializing();
    }
}
