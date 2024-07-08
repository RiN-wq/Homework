package Contacts.config;

import Contacts.DTO.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("Contacts")
@Configuration
@PropertySource("classpath:application.properties")
public class DefaultAppConfig {
    @Bean
    public Contact contact(){
        return new Contact();
    }
}
