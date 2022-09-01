package services;

import com.google.gson.Gson;
import modules.task.interfaces.IWriterService;
import modules.task.models.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class WriterService implements IWriterService {

    public static File createTaskFileIfNotExist() {

        String path = "/home/vinicius/IdeaProjects/TODO-Backend/src/taskFiles/taskFileDB.txt";

        try {
            File file = new File(path);
            if (file.createNewFile()) {
                System.out.printf("File created \n");
            }
            else {
                System.out.printf("File already exists! \n");
            }
            return file;
        } catch (IOException ex) {
            System.out.print("Unexpected error.");
            throw new RuntimeException(ex);
        }


    }

    public void writeTaskToFile(File file, Task task){

        try {
            FileWriter fileWriter = new FileWriter(file.getPath(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            Gson jsonParser = new Gson();
            String taskToJson = jsonParser.toJson(task);
            bufferedWriter.write(taskToJson);
            bufferedWriter.newLine();
            bufferedWriter.close();
            System.out.print("\nTask written successfuly!");
        } catch (IOException e) {
            System.out.print("\nUnexpected error");
            e.printStackTrace();
        }
    }

    public void writeTaskListToFile(File file, List<Task> taskList){

        try {
            FileWriter fileWriter = new FileWriter(file.getPath());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            Gson jsonParser = new Gson();

            List<String> taskToJson = taskList.stream().map(task-> jsonParser.toJson(task)).collect(Collectors.toList());
           taskToJson.forEach(
                   task -> {
                       try {
                           bufferedWriter.write(task);
                           bufferedWriter.newLine();
                       } catch (IOException e) {
                           throw new RuntimeException(e);
                       }
                   }
           );
            bufferedWriter.close();
            System.out.print("\nTask written successfuly!");
        } catch (IOException e) {
            System.out.print("\nUnexpected error");
            e.printStackTrace();
        }
    }
}
