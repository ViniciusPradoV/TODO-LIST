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

    public Task createTask()  {

        File file = writerService.createTaskFileIfNotExist();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file.getPath()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int lines = 0;
        while(true) {
            try {
                if (!(reader.readLine() != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            lines ++;
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int uid = lines+1;
        String name = printAndGetService.printAndGet("Digite um nome para a tarefa");
        String description = printAndGetService.printAndGet("Digite uma descricao para a tarefa");
        String priority = printAndGetService.printAndGet("Digite uma prioridade de 1 a 5, sendo 1 a menor e 5 a maior.");
        String category = printAndGetService.printAndGet("Digite uma categoria para a tarefa: ");
        String dueDate = printAndGetService.printAndGet("Digite um prazo maximo no formato dd/mm/aaa:");
        String status = printAndGetService.printAndGet("Selecione um status para a tarefa:\n" +
                "1 - ToDo\n" +
                "2 - Doing\n" +
                "3 - Done\n");


        Task newTask = new Task(uid, name, description, priority, category, dueDate, status);


        writerService.writeTaskToFile(file,newTask);

        return newTask;
    }

    public List<Task> getTasksByPriority() throws FileNotFoundException {

        File file = writerService.createTaskFileIfNotExist();
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

        return sortByPriority(listTask);

    }

    public void printTasks() throws FileNotFoundException {

        List<Task> listTask = getTasksByPriority();
        listTask.stream().forEach(task -> System.out.println(task.toString()));
    }

    public void updateTask() throws FileNotFoundException {

        String name = printAndGetService.printAndGet("Digite o nome da tarefa a ser atualizada");
        List<Task> listTask = getTasksByPriority();
        listTask.stream().filter(task -> task.getName().equals(name));
    }

    public List<Task> sortByPriority(List<Task> listTask){

        return listTask.stream()
                .sorted(Comparator.comparing(Task::getPriority).reversed())
                .collect(Collectors.toList());

    }


}
