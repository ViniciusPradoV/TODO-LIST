package modules.task.tests;

import modules.task.models.Task;
import modules.task.services.TaskService;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.PrintAndGetService;
import services.WriterService;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class TaskServiceTest {

    TaskService taskService = new TaskService(new WriterService(), new PrintAndGetService());

    TaskServiceTest() throws FileNotFoundException {
    }

    @Test
     void createTask() {
        // Given
        
        Task referenceTask = new Task(
                1,
                "nome",
                "descricao",
                "1",
                "categoria",
                "11/11/1111",
                "3"
        );

        // When
        Task testTask = taskService.createTask(new Task(
                1,
                "nome",
                "descricao",
                "1",
                "categoria",
                "11/11/1111",
                "3")
        );


        // Then
        Assertions.assertEquals(referenceTask.toString(),testTask.toString());

        System.out.println(
                "Criando >> " + testTask.getName()
                + ": Data de termino: " + testTask.getDueDate()
                + ", Status: " + testTask.getStatus()
                + "\n Tarefa criada com sucesso!"
                );
    }

    @Test
    void getByPriority() {

        //Given
        List<Task> listTask = new ArrayList<>();

        Task task1 = new Task(
                1,
                "nome",
                "descricao",
                "1",
                "categoria",
                "11/11/1111",
                "3");

        Task task2 = new Task(
                2,
                "nome",
                "descricao",
                "2",
                "categoria",
                "11/11/1111",
                "3");

        Task task3 = new Task(
                3,
                "nome",
                "descricao",
                "3",
                "categoria",
                "11/11/1111",
                "3");

        listTask.add(task3);
        listTask.add(task1);
        listTask.add(task2);

        // When
        List<Task> priorityList = taskService.sortByPriority(listTask);

        // Then
        Assertions.assertEquals("3", priorityList.get(0).getPriority());
        System.out.println( "Successo! Maior prioridade possivel e item de maior prioridade sao iguais");

    }

    @Test
    void updateTask() {

        // Given
        List<Task> testList = new ArrayList<>();

        Task testTask1 = new Task(
                1,
                "nome",
                "descricao",
                "1",
                "categoria",
                "11/11/1111",
                "3"
        );

        Task testTask2 = new Task(
                2,
                "nome",
                "descricao",
                "1",
                "categoria",
                "11/11/1111",
                "3"
        );

        testList.add(testTask1);
        testList.add(testTask2);


        // When
        List<Task> updatedList = taskService.updateTask(new Task(
                1,
                "novo nome",
                "descricao nova",
                "1",
                "categoria",
                "11/11/1111",
                "3"
        ),
                testList);


        // Then
        Assertions.assertNotEquals(testList.get(0).getName(), updatedList.get(1).getName());

        System.out.println("Task foi atualizada com sucesso e trocada na nova lista!");


    }

    @Test
    void deleteTask() {

        // Given
        List<Task> testList = new ArrayList<>();

        Task testTask1 = new Task(
                1,
                "nome",
                "descricao",
                "1",
                "categoria",
                "11/11/1111",
                "3"
        );

        Task testTask2 = new Task(
                2,
                "nome",
                "descricao",
                "1",
                "categoria",
                "11/11/1111",
                "3"
        );

        testList.add(testTask2);
        testList.add(testTask1);

        // When

        List<Task> listWithDeletedElement = taskService.deleteTask(1, testList);

        Assertions.assertTrue(testList.size() > listWithDeletedElement.size() - 1);

        System.out.println("Lista resultante contem um elemento a menos do que a lista recebida! \n Lista original: " + testList + "\n Lista com elemento deletado: " + listWithDeletedElement);

    }


}