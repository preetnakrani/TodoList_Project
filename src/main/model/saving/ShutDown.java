package main.model.saving;

import main.model.TodoList;

import java.io.IOException;

public interface ShutDown {

    //EFFECTS: saves the tasks on a files with the first part being the the status and the second part
    //         being the description
    void save(String fileName, TodoList todoList) throws IOException;

}
