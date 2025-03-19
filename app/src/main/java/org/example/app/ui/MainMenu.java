package org.example.app.ui;

import org.example.app.exception.EmailAlreadyRegistered;
import org.example.app.persistence.entity.TutorEntity;
import org.example.app.service.TutorService;

import static org.example.app.persistence.config.ConnectionConfig.getConnection;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private TutorEntity loggedInUser = null;

    public void execute() throws SQLException {
        System.out.println("Bem vindo a PetLandia! escolha uma das opções abaixo: ");
        var option = -1;
        while(true) {
            System.out.println("1 - Se cadastrar");
            System.out.println("2 - Acessar sua conta");
            System.out.println("3 - Sair");
            option = scanner.nextInt();
            switch (option) {
                case 1 -> createAccount();
                case 2 -> accessAccount();
                case 3 -> System.exit(0);
                default -> System.out.println("Opção invalida, tente novamente:");
            }
        }
    }

    public void createAccount() throws SQLException {
        var entity = new TutorEntity();
        System.out.println("Informe seu nome: ");
        entity.setName(scanner.next());

        System.out.println("Informe seu melhor email: ");
        entity.setEmail(scanner.next());

        String phoneNumber;
        while(true) {
            System.out.println("Informe seu numero de telefone: ");
            phoneNumber = scanner.next();
            if(phoneNumber.length() == 11 && phoneNumber.matches("[0-9]+")){
                break;
            } else {
                System.out.println("Informe um telefone valido (11 digitos, apenas numeros): ");
            }
        }

        entity.setPhoneNumber(phoneNumber);

        try(var connection = getConnection()) {
            var service = new TutorService(connection);
            service.create(entity);
            System.out.println("Conta criada com sucesso!!!");
        } catch (EmailAlreadyRegistered ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (SQLException ex){
            System.out.println("Erro ao criar conta, tente novamente mais tarde");
            ex.printStackTrace();
        }
    }

    public void accessAccount() throws SQLException {
        System.out.println("Digite seu email cadastrado: ");
        var email = scanner.next();

        try(var connection = getConnection()) {
            var service = new TutorService(connection);

            var user = service.findByEmail(email);

            if(user.isEmpty()) {
                System.out.println("Usuario não existe");
                return;
            }

            loggedInUser = user.get();

            System.out.println("Bem vindo " + loggedInUser.getName() + "!");

            new OperationMenu().execute();
        }
    }
}
