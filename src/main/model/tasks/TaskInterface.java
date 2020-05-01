package main.model.tasks;

import main.model.TodoList;
import main.model.exceptions.TooManyTasksException;

public interface TaskInterface {

    //Modifies: this
    //Effects: change status of task to give status
    void setStatus(Boolean status);

    //Effects: gets the name of the task
    String getDescription();

    //Effects: get the status of the task
    boolean getStatus();

    //Requires: 0 <= i <= todolist.size()
    //Modifies: this
    //Effects: sets this.todolist to todolist if the task is not already in this todolist, throws too many tasks
    //         exception if the list has more than 10 tasks
    void setTodoList(TodoList todoList, int i) throws TooManyTasksException;

    //void removeTodoList(String description) throws TaskNotFoundException;

    TodoList getList();

}
