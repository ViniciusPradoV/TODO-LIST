package Application;

import modules.task.services.TaskService;
import services.PrintAndGetService;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Application {

    public static int startApp() throws FileNotFoundException {
        TaskService taskService = new TaskService();
        PrintAndGetService printAndGetService = new PrintAndGetService();

        String userSelection = printAndGetService.printAndGet("Selecione uma opcao:\n" +
                "1 - Criar nova tarefa\n" +
                "2 - Visualizar tarefas criadas\n" +
                "3 - Atualizar tarefa \n" +
                "4 - Deletar tarefa \n" +
                "5 - Listar por:\n" +
                "0 - Sair");


        switch (userSelection) {
            case "1" -> taskService.createTask();
            case "2" -> taskService.printTasks();
            case "3" -> taskService.updateTask();
            case "4" -> taskService.deleteTask();
            case "5" -> taskService.listBy();
            case "0" -> {
                return 2;
            }
            default -> {
                return 0;
            }
        }

        return 0;
    }
}

