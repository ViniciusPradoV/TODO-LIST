package modules.task.services;

import com.google.gson.Gson;
import modules.task.models.Task;
import services.PrintAndGetService;
import services.WriterService;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {

    PrintAndGetService printAndGetService = new PrintAndGetService();
    WriterService writerService = new WriterService();
    File file = writerService.createTaskFileIfNotExist();

    BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));

    Gson jsonParser = new Gson();

    public TaskService() throws FileNotFoundException {
    }

    public void createTask() {

        List<Task> listTask = getTasksByUid();
        Task highestUidTask = listTask.get(0);
        int uid = highestUidTask.getUid()+1;
        String name = printAndGetService.printAndGet("Digite um nome para a tarefa");
        String description = printAndGetService.printAndGet("Digite uma descricao para a tarefa");
        String priority = printAndGetService.printAndGet("Digite uma prioridade de 1 a 5, sendo 1 a menor e 5 a maior.");
        String category = printAndGetService.printAndGet("Digite uma categoria para a tarefa: ");
        String dueDate = printAndGetService.printAndGet("Digite um prazo maximo no formato dd/mm/aaa:");
        String status = printAndGetService.printAndGet("""
                Selecione um status para a tarefa:
                1 - ToDo
                2 - Doing
                3 - Done
                """);


        Task newTask = new Task(uid, name, description, priority, category, dueDate, status);


        writerService.writeTaskToFile(file,newTask);

    }

    public List<Task> getTasksByPriority() {

        List<Task> listTask = new ArrayList<>();

        while(true){
            try {
                String taskString = reader.readLine();
                if(taskString == null) break;
                Task task = jsonParser.fromJson(taskString, Task.class);
                listTask.add(task);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return sortByPriority(listTask);

    }

    public List<Task> getTasksByUid() {

        Gson jsonParser = new Gson();
        List<Task> listTask = new ArrayList<>();

        while(true){
            try {
                String taskString = reader.readLine();
                if(taskString == null) break;
                Task task = jsonParser.fromJson(taskString, Task.class);
                listTask.add(task);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return sortByUid(listTask);

    }

    public void printTasks() {

        List<Task> listTask = getTasksByPriority();
        listTask.forEach(task -> System.out.println(task.toString()));
    }

    public void updateTask() {

        String uid = printAndGetService.printAndGet("Digite a ID unica da tarefa a ser atualizada");
        List<Task> listTask = getTasksByPriority();
        List<Task> listWithRemovedTask = listTask.stream().filter(task -> task.getUid() != Integer.parseInt(uid)).collect(Collectors.toList());

        String name = printAndGetService.printAndGet("Digite um novo nome para tarefa");
        String description = printAndGetService.printAndGet("Digite uma nova descricao para a tarefa");
        String priority = printAndGetService.printAndGet("Digite uma nova prioridade de 1 a 5, sendo 1 a menor e 5 a maior.");
        String category = printAndGetService.printAndGet("Digite uma nova categoria para a tarefa: ");
        String dueDate = printAndGetService.printAndGet("Digite um novo prazo maximo no formato dd/mm/aaa:");
        String status = printAndGetService.printAndGet("Selecione um novo status para a tarefa:\n");

        listWithRemovedTask.add(new Task(Integer.parseInt(uid), name, description, priority, category, dueDate, status));

        writerService.writeTaskListToFile(file, sortByPriority(listWithRemovedTask));


    }

    public void deleteTask() {

        String uid = printAndGetService.printAndGet("Digite a ID unica da tarefa a ser deletada");
        List<Task> listTask = getTasksByPriority();
        List<Task> listWithRemovedTask = listTask.stream().filter(task -> task.getUid() != Integer.parseInt(uid)).collect(Collectors.toList());
        writerService.writeTaskListToFile(file, sortByPriority(listWithRemovedTask));
    }

    public List<Task> sortByPriority(List<Task> listTask){

        return listTask.stream()
                .sorted(Comparator.comparing(Task::getPriority).reversed())
                .collect(Collectors.toList());

    }

    public List<Task> sortByUid(List<Task> listTask){

        return listTask.stream()
                .sorted(Comparator.comparing(Task::getUid).reversed())
                .collect(Collectors.toList());

    }

    public void listBy(){

        String listBy = printAndGetService.printAndGet(
                """
                        Selecione uma opcao:
                        1 - Categoria
                        2 - Prioridade
                        3 - Status\s
                        """);


        switch (listBy) {
            case "1" -> listByCategory();
            case "2" -> listByPriority();
            case "3" -> listByStatus();
            default -> System.out.println("Nenhuma opcao selecionada!");
        }
    }

    public void listByCategory(){

        String listBy = printAndGetService.printAndGet(
                "Digite a categoria das tarefas a serem listadas:");

        List<Task> listTask = getTasksByPriority();

        listTask.stream().filter(task -> task.getCategory().equals(listBy)).collect(Collectors.toList()).forEach(System.out::println);

    }

    public void listByPriority(){

        String listBy = printAndGetService.printAndGet(
                "Selecione a prioridade das tarefas listadas, de 1 a 5:");

        List<Task> listTask = getTasksByPriority();

        listTask.stream().filter(task -> task.getPriority().equals(listBy)).collect(Collectors.toList()).forEach(task -> System.out.println(task.toString()));

    }

    public void listByStatus(){

        String listBy = printAndGetService.printAndGet("""
                Selecione um status para as tarefas a serem listadas:
                1 - ToDo
                2 - Doing
                3 - Done
                """);

        List<Task> listTask = getTasksByPriority();

        listTask.stream().filter(task -> task.getStatus().equals(listBy)).collect(Collectors.toList()).forEach(System.out::println);

    }

}
