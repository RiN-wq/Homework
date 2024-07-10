package Contacts;

import Contacts.DTO.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("Contacts")
public class AppConfig {
    @Bean
    public Contact contact(){
        return new Contact();
    }

}
