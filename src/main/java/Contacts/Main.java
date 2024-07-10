package Contacts;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Scanner scanner = new Scanner(System.in);

        while (true){
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            selectCommand(context, scanner);
        }
    }

    public static void selectCommand(ApplicationContext context, Scanner scanner){

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
                context.getBean(ContactHandler.class).saveAllContactsInFile();
                break;
            case "init":
                checkProfileForInit(context);
                break;
            case "stop":
                return;
            default:
                System.err.println("Неверно задана команда.");
        }
    }

    public static void checkProfileForInit(ApplicationContext context){

        if (context.getBean(ContactHandler.class).isInitProfileSelected()){
            context.getBean(ContactInitializing.class).initAllContacts();
        } else {
            System.err.println("Значение профиля не равно \"init\"");
        }

    }
}
