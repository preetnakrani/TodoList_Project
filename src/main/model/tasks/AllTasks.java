package main.model.tasks;

import main.model.TodoList;
import main.model.exceptions.*;

public abstract class AllTasks implements TaskInterface {

    private String description;
    private boolean status;
    private TodoList todoList;

    public AllTasks(String description) {
        this.description = description;
        status = false;
        //System.out.println("Instantiated" + description);
    }

    //Modifies: this
    //Effects: change status of task to give status, also notify observers that the list has changed
    public void setStatus(Boolean status) {
        this.status = status;
        todoList.setChanges();
        todoList.notifyObservers();
    }

    //Effects: gets the name of the task
    public String getDescription() {
        return description;
    }

    //Effects: get the status of the task
    public boolean getStatus() {
        return status;
    }

    //Requires: 0 <= i <= todolist.size()
    //Modifies: this
    //Effects: sets this.todolist to todolist if the task is not already in this todolist, throws too many tasks
    //         exception if the list has more than 10 tasks
    public void setTodoList(TodoList todoList, int i) throws TooManyTasksException {

        if (todoList == null || !todoList.equals(this.todoList)) {
            this.todoList = todoList;
            todoList.addTask(this, i);
        }

    }

//    //Modifies: this
//    //Effects: removes the todoList if the the todolist is not already null
//    public void removeTodoList(String description) throws TaskNotFoundException {
//
//        if (!(todoList == null)) {
//            todoList.removeTask(description);
//            todoList = null;
//        }
//
//    }

    public TodoList getList() {
        return todoList;
    }

}
