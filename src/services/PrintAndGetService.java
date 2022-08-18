package services;

import java.util.Scanner;

public class PrintAndGetService {


    public String printAndGet(String question){
        System.out.println(question);
        Scanner input = new Scanner(System.in);
        String get = input.nextLine();

        return get;
    }
}
