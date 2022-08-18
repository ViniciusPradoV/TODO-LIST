package services;

import com.google.gson.Gson;
import modules.task.models.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterService {

    public File createTaskFileIfNotExist() {

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
}
