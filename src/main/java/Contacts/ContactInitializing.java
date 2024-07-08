package Contacts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
@Component
public class ContactInitializing {
    String pathOfInitFile =
            "C:\\Users\\Study\\IdeaProjects\\Contacts\\src\\main\\resources\\default-contacts.txt";
    @Autowired
    public ContactInitializing(ContactHandler contactHandler){
        this.contactHandler = contactHandler;
    }
    public void initAllContacts(){

        try {

            Reader reader = new FileReader(pathOfInitFile);

            BufferedReader bufReader = new BufferedReader(reader);

            String line = bufReader.readLine();

            while (line != null){

                contactHandler.addNewContact(line.split(";"));

                line = bufReader.readLine();

            }

            System.out.println("Контакты были успешно проинициализированы.");

        }catch (FileNotFoundException e){
            System.err.println("Инициализирующий файл по указанному пути не был найден.");
        }catch (IOException e){
            System.err.println("Ошибка ввода - вывода.");
        }

    }
}
