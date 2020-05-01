package main.model.observer;

import main.model.TodoList;
import main.model.tasks.TaskInterface;

import java.util.Observable;
import java.util.Observer;

public class ListChecker implements Observer {
    @Override
    public void update(Observable o, Object arg) {

        int completed = 0;
        int incomplete = 0;
        TodoList theList = (TodoList) o;
        for (TaskInterface ti : theList.getList()) {
            if (ti.getStatus()) {
                completed++;
            } else {
                incomplete++;
            }
        }

        System.out.println("You have a total of \"" + (completed + incomplete) + "\" task(s). \"" + completed
                + "\" Task(s) have been completed and \"" + incomplete
                + "\" task(s) are yet to be completed!\"");

    }

}


