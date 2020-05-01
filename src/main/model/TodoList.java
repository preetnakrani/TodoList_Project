package main.model;

import main.model.exceptions.TaskNotFoundException;
import main.model.exceptions.TooManyTasksException;
import main.model.observer.ListChecker;
import main.model.tasks.TaskInterface;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;

public class TodoList extends Observable {

    private ArrayList<TaskInterface> theList;

    //EFFECTS: adds listchecker as an observer and instantiates the todoList
    public TodoList() {
        theList = new ArrayList<>();
        addObserver(new ListChecker());
    }


    //Modifies: this
    //Effects: adds a task to the list if it is not in it
    public void addTask(TaskInterface task, int i) throws TooManyTasksException {

        if (!(theList.contains(task))) {
            tooManyTaskHelper();
            theList.add(i, task);
            task.setTodoList(this, i);
            setChanged();
            notifyObservers();
        }

    }

    private void tooManyTaskHelper() throws TooManyTasksException {
        if (theList.size() >= 10) {
            throw new TooManyTasksException();
        }
    }

    //Modifies: this
    //Effects: removes the specified task from the list and throws an exception if the task is not found
    public void removeTask(String description) throws TaskNotFoundException {
        int index = findTask(description);
        if (index == -1) {
            throw new TaskNotFoundException();
        }
        if (theList.contains(theList.get(index))) {
            TaskInterface theTask = theList.get(index);
            //theList.get(index).removeTodoList(description);
            theList.remove(index);
            System.out.println("removed" + theTask.getDescription());
            setChanged();
            notifyObservers();
        }
    }

    //Modifies: this
    //Effects: removes given task
    public void removeTask(TaskInterface ti) {
        theList.remove(ti);
    }

    //Effects: find the task and returns its index on the list, and the task not found then returns -1
    public int findTask(String description) {
        int index = -1;
        for (int i = 0; i < theList.size(); i++) {
            if (description.equals(theList.get(i).getDescription())) {
                index = i;
            }
        }
        return index;
    }

    public int getSize() {
        return theList.size();
    }



    // overriding equals and hashcode -------------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TodoList)) {
            return false;
        }
        TodoList todoList = (TodoList) o;
        return Objects.equals(theList, todoList.theList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theList);
    }


    public ArrayList<TaskInterface> getList() {
        return theList;
    }

    //EFFECTS: helps other classes call set changed
    public void setChanges() {
        setChanged();
    }

}
