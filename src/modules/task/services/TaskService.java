package modules.task.services;

import com.google.gson.Gson;
import modules.task.interfaces.IWriterService;
import modules.task.models.Task;
import modules.task.interfaces.IPrintAndGetService;
import org.jetbrains.annotations.NotNull;
import services.WriterService;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {

    IPrintAndGetService printAndGetService;
    IWriterService writerService;

    public TaskService(IWriterService writerService, IPrintAndGetService printAndGetService) throws FileNotFoundException {
        this.printAndGetService = printAndGetService;
        this.writerService = writerService;
    }

    Gson jsonParser = new Gson();



    public Task createTask(Task newTask) {

        return newTask;

    }

    public Task getTaskData(List<Task> listTask) {
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

        return new Task(uid, name, description, priority, category, dueDate, status);
    }

    public void writeNewTask(Task newTask) {
        File file = WriterService.createTaskFileIfNotExist();
        writerService.writeTaskToFile(file, newTask);
    }

    public List<Task> getTasksFromFileByPriority() throws FileNotFoundException {

        File file = WriterService.createTaskFileIfNotExist();
        BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));

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

    public List<Task> getTasksFromFyleByUid() throws FileNotFoundException {

        File file = WriterService.createTaskFileIfNotExist();
        BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));

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

    public void printTasks() throws FileNotFoundException {

        List<Task> listTask = getTasksFromFileByPriority();
        listTask.forEach(task -> System.out.println(task.toString()));
    }

    public List<Task> updateTask(Task updatedTask, List<Task> listTask)  {

       List<Task> listWithRemovedTask = listTask.stream().filter(task -> task.getUid() != updatedTask.getUid()).collect(Collectors.toList());

       listWithRemovedTask.add(updatedTask);

        return listWithRemovedTask;

    }

    @NotNull
    public Task getUpdateTaskData() {
        String uid = printAndGetService.printAndGet("Digite a ID unica da tarefa a ser atualizada");
        String name = printAndGetService.printAndGet("Digite um novo nome para tarefa");
        String description = printAndGetService.printAndGet("Digite uma nova descricao para a tarefa");
        String priority = printAndGetService.printAndGet("Digite uma nova prioridade de 1 a 5, sendo 1 a menor e 5 a maior.");
        String category = printAndGetService.printAndGet("Digite uma nova categoria para a tarefa: ");
        String dueDate = printAndGetService.printAndGet("Digite um novo prazo maximo no formato dd/mm/aaa:");
        String status = printAndGetService.printAndGet("Selecione um novo status para a tarefa:\n");

        return new Task(Integer.parseInt(uid), name, description, priority, category, dueDate, status);
    }

    public void writeUpdatedList(List<Task> listWithUpdatedTask) {

        File file = WriterService.createTaskFileIfNotExist();

        writerService.writeTaskListToFile(file, sortByPriority(listWithUpdatedTask));
    }

    public List<Task> deleteTask(int uidTaskToDelete,List<Task> listTask) {
        return listTask.stream().filter(task -> task.getUid() != uidTaskToDelete).collect(Collectors.toList());
    }

    public int getTaskToBeDeletedData() {
        return Integer.parseInt(printAndGetService.printAndGet("Digite a UID da task a ser deletada:"));
    }

    public void writeListWithRemovedTask(List<Task> listWithRemovedTask) {
        File file = WriterService.createTaskFileIfNotExist();

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

    public void listBy() throws FileNotFoundException {

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

    public void listByCategory() throws FileNotFoundException {

        String listBy = printAndGetService.printAndGet(
                "Digite a categoria das tarefas a serem listadas:");

        List<Task> listTask = getTasksFromFileByPriority();

        listTask.stream().filter(task -> task.getCategory().equals(listBy)).collect(Collectors.toList()).forEach(System.out::println);

    }

    public void listByPriority() throws FileNotFoundException {

        String listBy = printAndGetService.printAndGet(
                "Selecione a prioridade das tarefas listadas, de 1 a 5:");

        List<Task> listTask = getTasksFromFileByPriority();

        listTask.stream().filter(task -> task.getPriority().equals(listBy)).collect(Collectors.toList()).forEach(task -> System.out.println(task.toString()));

    }

    public void listByStatus() throws FileNotFoundException {

        String listBy = printAndGetService.printAndGet("""
                Selecione um status para as tarefas a serem listadas:
                1 - ToDo
                2 - Doing
                3 - Done
                """);

        List<Task> listTask = getTasksFromFileByPriority();

        listTask.stream().filter(task -> task.getStatus().equals(listBy)).collect(Collectors.toList()).forEach(System.out::println);

    }

}
