package org.example.app.ui;

import lombok.AllArgsConstructor;
import org.example.app.persistence.entity.AgendamentoEntity;
import org.example.app.persistence.entity.CachorroEntity;
import org.example.app.persistence.entity.ServiceEntity;
import org.example.app.persistence.entity.TutorEntity;
import org.example.app.service.AgendamentoService;
import org.example.app.service.CachorroService;
import org.example.app.service.ServiceService;

import static org.example.app.persistence.config.ConnectionConfig.getConnection;

import java.security.Principal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class OperationMenu {

    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private final TutorEntity tutorEntity;

    public void execute() throws SQLException {
        while(true) {
            System.out.println("Escolha o que fazer a seguir: ");
            var option = -1;

            System.out.println("1 - Cadastrar novo cachorro");
            System.out.println("2 - Listar seus cachorros cadastrados");
            System.out.println("3 - Agendar um servico");
            System.out.println("4 - Excluir um cachorro");
            System.out.println("5 - Listar agendamentos");
            System.out.println("6 - Deletar agendamento");
            System.out.println("7 - Sair");
            option = scanner.nextInt();

            switch (option){
                case 1 -> createNewDog();
                case 2 -> listDogs();
                case 3 -> scheduleService();
                case 4 -> deleteDog();
                case 5 -> scheduleList();
                case 6 -> deleteSchedule();
                case 7 -> {
                    return;
                }
                default -> System.out.println("Opção invalida");
            }
        }
    }

    public void createNewDog() throws SQLException{
        var entity = new CachorroEntity();

        System.out.println("Digite o nome do seu cachorro: ");
        entity.setName(scanner.next());

        System.out.println("Digite a idade do seu cachorro: ");
        entity.setAge(scanner.nextInt());

        try(var connection = getConnection()){
            var service = new CachorroService(connection);
            service.create(entity, tutorEntity.getId());
            System.out.println("Cachorro cadastrado!!!");
        } catch (SQLException ex) {
            System.out.println("Erro ao cadastar cachorro, tente mais tarde");
            System.out.println(ex.getMessage());
        }
    }

    public void deleteDog() throws SQLException {
        try(var connection = getConnection()) {
            System.out.println("Informe o id do cachorro a ser excluido: ");
            var dogId = scanner.nextLong();

            var service = new CachorroService(connection);
            var result = service.delete(dogId, tutorEntity.getId());

            if (!result) {
                System.out.println("Falha ao deletar cachorro!!!");
                return;
            }

            System.out.println("Cachorro excluido com sucesso!!!");
        }
    }

    public void scheduleService() throws SQLException {
        try(var connection = getConnection()) {

            var cachorroService = new CachorroService(connection);
            var cachorros = cachorroService.findAllByTutorId(tutorEntity.getId());

            if(cachorros.isEmpty()) {
                System.out.println("Nenhum cachorro cadastrado!!!");
                return;
            }

            CachorroEntity selectedDog = null;
            while(true) {
                System.out.println("Escolha o cachorro que ira receber o servico");

                for (var c : cachorros) {
                    System.out.println(c.getId() + " - " + c.getName());
                }
                var option = scanner.nextLong();

                selectedDog = cachorros.stream()
                        .filter(c -> c.getId().equals(option))
                        .findFirst()
                        .orElse(null);

                if (selectedDog == null) {
                    System.out.println("Opção invalida tente novamente");
                    continue;
                }

                break;
            }

            var serviceServic = new ServiceService(connection);
            var services = serviceServic.getAllServices();

            ServiceEntity selectedService = null;
            while(true) {
                System.out.println("Escolha o servico desejado");
                for (var s: services) {
                    System.out.println(s.getId() + " - " + s.getName() + " Preco: " + s.getPrice());
                }
                var option = scanner.nextLong();

                selectedService = services.stream()
                        .filter( s -> s.getId().equals(option))
                        .findFirst()
                        .orElse(null);

                if (selectedService == null) {
                    System.out.println("Opção invalida, tente novamente");
                    continue;
                }

                break;
            }

            String input = "";
            Date date = null;
            while (true) {
                System.out.println("Informe a data e hora que deseja agendar no seguinte formato (YYYY-MM-DD HH:MM:SS): ");
                input = scanner.nextLine();

                try{
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    date = format.parse(input);
                    break;
                } catch (ParseException ex) {
                    System.out.println("Formanto invalido, Tente novamente");
                }
            }

            var agendamento = new AgendamentoEntity();
            agendamento.setServiceEntity(selectedService);
            agendamento.setCachorroEntity(selectedDog);
            agendamento.setStatus("PENDENTE");
            agendamento.setDate(new Timestamp(date.getTime()));

            var agendamentoService = new AgendamentoService(connection);
            agendamentoService.create(agendamento);

            System.out.println("Agendamento feito com sucesso!!!");
        }
    }

    public void listDogs() throws SQLException{
        try(var connection = getConnection()) {
            var service = new CachorroService(connection);
            var cachorros = service.findAllByTutorId(tutorEntity.getId());

            if (cachorros.isEmpty()) {
                System.out.println("Nenhum cachorro cadastrado");
                return;
            }

            for (var c :  cachorros) {
                System.out.println("Nome: " + c.getName() + " Idade: " + c.getAge() + " ID: " + c.getId());
            }
        }
    }

    public List<AgendamentoEntity> scheduleList() throws SQLException{
        try (var connection = getConnection()){
            var service = new AgendamentoService(connection);
            var agendamentos = service.getAllByTutorId(tutorEntity.getId());

            if (agendamentos.isEmpty()) {
                System.out.println("Nenhum agendamento encontrado");
                return List.of();
            }

            for (var a : agendamentos) {
                System.out.println("ID: " + a.getId());
                System.out.println("Cachorro: " + a.getCachorroEntity().getName());
                System.out.println("Cachorro ID: " + a.getCachorroEntity().getId());
                System.out.println("Servico: " + a.getServiceEntity().getName());
                System.out.println("Preco: " + a.getServiceEntity().getPrice());
                System.out.println("Status: " + a.getStatus());
                System.out.println("Data: " + a.getDate());
            }

            return agendamentos;
        }
    }

    public void deleteSchedule() throws SQLException {
        try(var connection = getConnection()) {
            var agendamentos = scheduleList();
            if (agendamentos == null) {
                System.out.println("Nenhum agendamento encontrado");
                return;
            }
            AgendamentoEntity agendamentoSelected = new AgendamentoEntity();
            while (true) {

                System.out.println("Digite o ID do agendamento que deseja excluir: ");
                var option = scanner.nextLong();

                agendamentoSelected = agendamentos.stream()
                        .filter(a -> a.getId().equals(option))
                        .findFirst()
                        .orElse(null);

                if (agendamentoSelected == null) {
                    System.out.println("Agendamento invalido");
                    return;
                }

                break;
            }

            var agendamentoService = new AgendamentoService(connection);

            var deleted = agendamentoService.delete(agendamentoSelected.getId());

            if (!deleted) {
                System.out.println("Falha ao deletar");
                return;
            }

            System.out.println("Agendamento excluido com sucesso!!!");
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir agendamento");
            ex.printStackTrace();
        }

    }
}
