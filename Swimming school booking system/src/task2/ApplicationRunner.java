package task2;

import task2.Application.Manager;
import task2.DAO.DataLoader;
import task2.UI.UserInterface;

/*Main class creating relevant objects and invoking the methods required to
start the program*/
public class ApplicationRunner {

    public static void main(String[] args) {
        // object holding the data
        DataLoader data = new DataLoader();
        // object holding the user interface
        UserInterface UI = new UserInterface();
        // object to control the data and user interface
        Manager controller = new Manager(UI, data);

        // create data instances and alternate levels of those
        data.createInstances();
        data.alternateLevels();
        
        data.assignToSessions();

        // run the program
        controller.startProgram();
    }
}
