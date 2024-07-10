package Contacts;

import Contacts.DTO.Contact;
import org.springframework.beans.factory.annotation.Value;
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
    private static final String ENGLISH_FULL_NAME_REGEX = "([a-z\\-']+ ){2}[a-z\\-']+";
    private static final String RUSSIAN_FULL_NAME_REGEX = "([а-я]+ ){2}[а-я]+";
    private static final String PHONE_NUMBER_REGEX = "\\+[0-9]+";
    private static final String EMAIL_REGEX = "[a-z0-9]+@([a-z0-9\\-]+\\.)*[a-z]+";
    List<Contact> setOfContacts = new ArrayList<>();
    @Value("${file.save.path}")
    String pathOfSavingFile;
    @Value("${spring.profiles.active}")
    String profile;

    public ContactHandler(Contact contact) {
        this.contact = contact;
    }

    public void printAllContacts() {

        if (setOfContacts.isEmpty()) {
            System.err.println("Контактов не найдено.");
            return;
        }

        for (Contact specificContact : setOfContacts) {
            System.out.print(specificContact);
        }

    }

    public void addNewContact(String[] contactInformationArray) {

        if (contactInformationArray.length != 3) {
            System.err.println("Данные введены не согласно формату.");
            System.err.println(contact);
            return;
        }
        contactInformationArray[0] = contactInformationArray[0].toLowerCase();
        contactInformationArray[1] = setPhoneToNormalForm(contactInformationArray[1]);
        contactInformationArray[2] = contactInformationArray[2].toLowerCase();

        String errors = checkContactCorrect(contactInformationArray);

        if (errors.isBlank()) {
            contact = new Contact();
            contact.setFullName(contactInformationArray[0].toLowerCase());
            contact.setPhoneNumber(contactInformationArray[1]);
            contact.setEmail(contactInformationArray[2].toLowerCase());
            setOfContacts.add(contact);
            System.out.println("Контакт был успешно добавлен");
        } else {
            System.err.println(errors);
        }

    }

    public void deleteContactByEmail(String email) {

        if (setOfContacts.removeIf(contact -> contact.getEmail().equals(email))) {
            System.out.println("Контакт был успешно удалён.");
        } else {
            System.err.println("Контакт с указанной почтой не был найден.");
        }

    }

    public String checkContactCorrect(String[] contactDataList) {

        if (setOfContacts.stream().anyMatch(contact ->
                contact.getEmail().equals(contactDataList[2].toLowerCase()) ||
                        contact.getPhoneNumber().equals(contactDataList[1]))) {
            return "Такой контакт уже существует.";
        }

        String errors = "";

        if (!contactDataList[0].matches(ENGLISH_FULL_NAME_REGEX)
                &&  !contactDataList[0].matches(RUSSIAN_FULL_NAME_REGEX)) {
            errors += ("Ошибка ввода Ф.И.О.\n");
            System.err.println(contactDataList[0]);
        }

        if (!contactDataList[1].matches(PHONE_NUMBER_REGEX)) {
            errors += ("Ошибка ввода телефонного номера.\n");
            System.err.println(contactDataList[1]);
        }

        if (!contactDataList[2].matches(EMAIL_REGEX)) {
            errors += ("Ошибка ввода почты.\n");
            System.err.println(contactDataList[2]);
        }

        return errors;

    }

    private String setPhoneToNormalForm(String phoneNumber) {

        phoneNumber = phoneNumber
                .replaceAll("\\+", "")
                .replaceAll(" ", "")
                .replaceAll("-", "")
                .replaceAll("\\(", "")
                .replaceAll("\\)", "");

        return "+" + phoneNumber;

    }

    public void saveAllContactsInFile() {

        try {

            Writer writer = new FileWriter(pathOfSavingFile);

            CharArrayWriter buffer = new CharArrayWriter();

            for (Contact currentContact : setOfContacts) {

                buffer.append(currentContact.toString().replaceAll(" \\| ", ";"));

                if (buffer.size() >= 100) {
                    buffer.writeTo(writer);
                    buffer.reset();
                }
            }
            buffer.writeTo(writer);

            buffer.flush();
            writer.flush();

            writer.close();

            System.out.println("Данные успешно добавлены в файл.");
        } catch (IOException e) {
            System.err.println("Ошибка ввода - вывода.");
        }
    }

    public boolean isInitProfileSelected(){

        return profile.equals("init");

    }

}