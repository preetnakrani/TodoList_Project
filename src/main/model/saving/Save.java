package main.model.saving;

import main.model.TodoList;
import main.model.tasks.TaskInterface;

import java.io.IOException;
import java.io.PrintWriter;


public class Save implements ShutDown {

    public Save() {
        // do nothing
    }

    @Override
    public void save(String fileName, TodoList todoList) throws IOException {

        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        for (TaskInterface ti : todoList.getList()) {
            writer.println(ti.getStatus() + " " + ti.getDescription());
        }
        writer.close();

    }

}
