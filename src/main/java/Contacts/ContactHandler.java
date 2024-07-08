package Contacts;

import Contacts.DTO.Contact;
import org.springframework.stereotype.Component;

import java.io.CharArrayWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Component
public class ContactHandler {
    private Contact contact;
    private static final String fullNameRegex = ".+";
    private static final String phoneNumberRegex = ".+";
    private static final String emailRegex = ".+";
    List<Contact> setOfContacts = new ArrayList<>();

    public ContactHandler(Contact contact){
        this.contact = contact;
    }

    public void printAllContacts(){

        if (setOfContacts.isEmpty()){
            System.err.println("Контактов не найдено.");
            return;
        }

        for (Contact specificContact : setOfContacts) {
            System.out.print(specificContact);
        }

    }

    public void addNewContact(String[] contactInformationArray){

        String errors = checkContactCorrect(contactInformationArray);

        if (errors.isBlank()){
            contact = new Contact();
            contact.setFullName(contactInformationArray[0].toLowerCase());
            contact.setPhoneNumber(setPhoneToNormalForm(contactInformationArray[1]));
            contact.setEmail(contactInformationArray[2].toLowerCase());
            setOfContacts.add(contact);
            System.out.println("Контакт был успешно добавлен");
        } else {
            System.err.println(errors);
        }

    }

    public void deleteContactByEmail(String email){

        if (setOfContacts.removeIf(contact -> contact.getEmail().equals(email))){
            System.out.println("Контакт был успешно удалён.");
        }else {
            System.err.println("Контакт с указанной почтой не был найден.");
        }

    }

    public String checkContactCorrect(String[] contactDataList){

        if (contactDataList.length != 3){
            return "Данные введены не согласно формату.";
        }

        if (setOfContacts.size() == 1){
            if (setOfContacts.stream().anyMatch(contactADF ->
                    contactADF.getEmail().equals(contactDataList[2].toLowerCase()) ||
                            contactADF.getPhoneNumber().equals(setPhoneToNormalForm(contactDataList[1])))){
                return "Такой контакт уже существует.";
            }
        }


        String errors = "";

        if (!contactDataList[0].matches(fullNameRegex)){
            errors += ("Ошибка ввода Ф.И.О.\n");
        }

        if (!contactDataList[1].matches(phoneNumberRegex)){
            errors += ("Ошибка ввода телефонного номера.\n");
        }

        if (!contactDataList[2].matches(emailRegex)){
            errors += ("Ошибка ввода почты.\n");
        }

        return errors;

    }

    private String setPhoneToNormalForm(String phoneNumber){

        phoneNumber = phoneNumber
                .replaceAll("\\+","")
                .replaceAll(" ","")
                .replaceAll("-","")
                .replaceAll("\\(","")
                .replaceAll("\\)","");

        return phoneNumber = "+" + phoneNumber;

    }

    public void saveAllContactsInFile(String path){

        try {

            Writer writer = new FileWriter(path);

            CharArrayWriter buffer = new CharArrayWriter();

            for (Contact currentContact : setOfContacts) {

                buffer.append(currentContact.toString().replaceAll(" \\| ", ";"));

                if (buffer.size() >= 100){
                    buffer.writeTo(writer);
                    buffer.reset();
                }
            }
            buffer.writeTo(writer);

            buffer.flush();
            writer.flush();

            writer.close();

            System.out.println("Данные успешно добавлены в файл.");
        } catch (IOException e){
            System.err.println("Ошибка ввода - вывода.");
        }
    }

}