package main.model.saving;

import main.model.TodoList;
import main.model.exceptions.TooManyTasksException;
import main.model.tasks.TaskInterface;
import main.model.tasks.typesOfTasks.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Load implements StartUp {

    public Load() {
        //do nothing
    }

    //EFFECTS: loads the todolist by reading the saved tasks from a file
    @Override
    public void load(String fileName, TodoList todoList) throws IOException, TooManyTasksException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        for (String line : lines) {
            ArrayList<String> parts = splitOnSpace(line);
            StringBuilder sb = new StringBuilder();
            for (String s : parts) {
                if (!(s.equals("true") || s.equals("false"))) {
                    sb.append(" " + s);
                }
            }
            String removeSpace = sb.toString();
            String description = removeSpace.substring(1);
            TaskInterface newTask = new Task(description);
            try {
                todoList.addTask(newTask, 0);
                newTask.setStatus(convertStringToBoolean(parts.get(0)));


            } catch (TooManyTasksException t) {
                //This situation is not reachable
            }
        }
    }

    private Boolean convertStringToBoolean(String s) {
        return s.equals("true");
    }

    //EFFECTS: splits line on space and returns the array list of the lines
    @Override
    public ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }

}
