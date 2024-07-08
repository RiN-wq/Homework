package Contacts;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ContactHandler contactHandler;
        ContactInitializing contactInitializing;
        Scanner scanner = new Scanner(System.in);

        while (true){
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Введите команду:");
            String input = scanner.nextLine().toLowerCase().trim();

            switch (input.split(" ")[0]){
                case "print":
                    context.getBean(ContactHandler.class).printAllContacts();
                    break;
                case "add":
                    context.getBean(ContactHandler.class)
                            .addNewContact(input.replaceAll("add ", "").split(";"));
                    break;
                case "delete":
                    context.getBean(ContactHandler.class)
                            .deleteContactByEmail(input.replaceAll("delete ",""));
                    break;
                case "file":
                    context.getBean(ContactHandler.class)
                            .saveAllContactsInFile(input.replaceAll("file ",""));
                    break;
                case "init":
                    context.getBean(ContactInitializing.class).initAllContacts();
                    break;
                case "stop":
                    return;
                default:
                    System.err.println("Неверно задана команда.");
            }
        }
    }
}
