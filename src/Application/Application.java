package Application;

import modules.task.services.TaskService;
import services.PrintAndGetService;
import modules.task.interfaces.IPrintAndGetService;
import services.WriterService;

import java.io.FileNotFoundException;

public class Application {

    public static int startApp() throws FileNotFoundException {
        TaskService taskService = new TaskService(new WriterService(),new PrintAndGetService());
        IPrintAndGetService printAndGetService = new PrintAndGetService();

        String userSelection = printAndGetService.printAndGet("""
                Selecione uma opcao:
                1 - Criar nova tarefa
                2 - Visualizar tarefas criadas
                3 - Atualizar tarefa\s
                4 - Deletar tarefa\s
                5 - Listar por:
                0 - Sair""");


        switch (userSelection) {
            case "1" -> taskService.writeNewTask(taskService.createTask(taskService.getTaskData(taskService.getTasksFromFyleByUid())));
            case "2" -> taskService.printTasks();
            case "3" -> taskService.writeUpdatedList(taskService.updateTask(taskService.getUpdateTaskData(), taskService.getTasksFromFileByPriority()));
            case "4" -> taskService.writeListWithRemovedTask(taskService.deleteTask(taskService.getTaskToBeDeletedData(), taskService.getTasksFromFyleByUid()));
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

