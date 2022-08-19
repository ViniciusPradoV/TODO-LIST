import Application.Application;

import java.io.IOException;

public class main {

    public static void main(String[] args) throws IOException {

        int appReturn = 0;

        while(appReturn != 2){
            appReturn = Application.startApp();
        }

    }
}
