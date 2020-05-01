package main.model.saving;

import main.model.TodoList;
import main.model.exceptions.TooManyTasksException;

import java.io.IOException;
import java.util.ArrayList;

public interface StartUp {

    //EFFECTS: loads the todolist by reading the saved tasks from a file
    void load(String fileName, TodoList todoList) throws IOException, TooManyTasksException;

    //EFFECTS: splits line on space and returns the array list of the lines
    ArrayList<String> splitOnSpace(String line);

}
