package Contacts.DTO;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
@Data
public class Contact {
    String fullName;
    String phoneNumber;
    String email;

    @Override
    public String toString(){
        return fullName + " | " + phoneNumber + " | " + email + "\n";
    }
}
