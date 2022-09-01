package services;

import modules.task.interfaces.IPrintAndGetService;

import java.util.Scanner;

public class PrintAndGetService implements IPrintAndGetService {


    @Override
    public String printAndGet(String question){
        System.out.println(question);
        Scanner input = new Scanner(System.in);

        return input.nextLine();
    }
}
