package Contacts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
@Component
@Profile("init")
public class ContactInitializing {
    @Autowired
    ContactHandler contactHandler;
    @Value("${file.init.path}")
    String pathOfInitFile;
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
