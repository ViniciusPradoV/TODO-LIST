import modules.task.services.TaskService;

import java.io.IOException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) throws IOException {

        TaskService taskService = new TaskService();
        Scanner input = new Scanner(System.in);

            System.out.println("Selecione uma opcao:\n" +
                    "1 - Criar nova tarefa\n" +
                    "2 - Visualizar tarefas criadas\n" +
                    "3 - Atualizar tarefa \n" +
                    "4 - Deletar tarefa \n" +
                    "0 - Sair");

            String userSelection = input.nextLine();

            switch (userSelection) {
                case "1" -> taskService.createTask();
                case "2" -> taskService.printTasks();
                case "3" -> taskService.updateTask();
                case "4" -> taskService.deleteTask();
                default -> System.out.println("Nenhuma opcao selecionada!");
            }

    }


}
