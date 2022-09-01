package modules.task.interfaces;

import modules.task.models.Task;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IWriterService {

    public static @NotNull File createTaskFileIfNotExist(){
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
    public void writeTaskToFile(File file, Task task);
    public void writeTaskListToFile(File file, List<Task> taskList);

}
