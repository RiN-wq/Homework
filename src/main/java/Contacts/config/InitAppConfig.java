package Contacts.config;

import Contacts.ContactInitializing;
import Contacts.DTO.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
@Profile("init")
public class InitAppConfig {

    @Bean
    public Contact contact(){
        return new Contact();
    }
    @Bean
    public ContactInitializing contactInitializing(){
        return new ContactInitializing();
    }
}
