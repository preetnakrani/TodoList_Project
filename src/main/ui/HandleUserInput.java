package main.ui;//package ui;
//
//import model.todolist.TodoList;
//import model.todolist.exceptions.TaskNotFoundException;
//import model.todolist.exceptions.TooManyTasksException;
//import model.todolist.saving.Save;
//import model.todolist.tasks.TaskInterface;
//import model.todolist.tasks.typesoftasks.LongTermTask;
//import model.todolist.tasks.typesoftasks.Task;
//import model.todolist.tasks.typesoftasks.UrgentTask;
//
//import java.io.IOException;
//import java.util.Scanner;
//
//public class HandleUserInput {
//
//    private Scanner input;
//    String userInput = null;
//    private boolean run;
//
//    //Effects: calls the method that will let user perform actions based on input
//    public HandleUserInput() {
//        run = true;
//    }
//
//    //Modifies: this
//    //Effects: gives user options to perform actions by inputting what the user wants to do.
//    //         lets user make new task, TodoList add task to a ToDoList or quit the application
//    public void handleInput(TodoList todoList) throws IOException {
//        while (run) {
//            userInput = null;
//            input = new Scanner(System.in);
//            System.out.println("Press: 1 to make task, 2 to remove task, 3 to see all tasks, "
//                    + "4 to change status of a task, 5 to quit");
//            userInput = getInput();
//            handleEachInput(userInput, todoList);
//        }
//    }
//
//    //Effects: returns the input by the user
//    private String getInput() {
//        while (true) {
//            if (input.hasNext()) {
//                return input.nextLine();
//            }
//        }
//    }
//
//    //Effects: handle each option of user input individually
//    private void handleEachInput(String userInput, TodoList todoList) throws IOException {
//        if (userInput.equals("1")) {
//            handleMakingTask(todoList);
//        } else if (userInput.equals("2")) {
//            handleRemovingTask(todoList);
//        } else if (userInput.equals("3")) {
//            printList(todoList);
//        } else if (userInput.equals("4")) {
//            handleChangingStatus(todoList);
//        } else if (userInput.equals("5")) {
//            handleQuit(todoList);
//        } else {
//            System.out.println("Wrong Input, Try Again!");
//            handleInput(todoList);
//        }
//    }
//
//    private void handleQuit(TodoList todoList) throws IOException {
//        Save save = new Save();
//        save.save("C:\\Users\\preet\\IdeaProjects\\cpsc_210\\project_r7i2b\\data\\Store.txt", todoList);
//        run = false;
//    }
//
//    private void handleChangingStatus(TodoList todoList) throws IOException {
//        System.out.println("Enter task description: ");
//        userInput = input.nextLine();
//        handleChangingStatusStepTwo(todoList, userInput);
//    }
//
//    private void handleChangingStatusStepTwo(TodoList todoList, String userInput) throws IOException {
//        int index = todoList.findTask(userInput);
//        if (index == -1) {
//            System.out.println("Task does not exist,Try again1");
//            handleInput(todoList);
//        } else {
//            System.out.println("Enter c to complete and i to incomplete task");
//            String s = input.nextLine();
//            if (!(s.equals("c") || s.equals("i"))) {
//                System.out.println("Incorrect Input Try Again!");
//                handleChangingStatusStepTwo(todoList, userInput);
//            } else {
//                changeStatusNow(s, todoList, userInput, index);
//            }
//
//        }
//    }
//
//    private void changeStatusNow(String s, TodoList todoList, String userInput, int index) throws IOException {
//        todoList.getList().get(index).setStatus(s.equals("true"));
//    }
//
//
//    private void printList(TodoList todoList) throws IOException {
//        for (TaskInterface ti : todoList.getList()) {
//            String s;
//            if (ti.getStatus()) {
//                s = "Completed";
//            } else {
//                s = "Incomplete";
//            }
//            String description = ti.getDescription();
//            System.out.println("Task \"" + description + "\" is " + s + ".");
//        }
//        handleInput(todoList);
//    }
//
//    private void handleRemovingTask(TodoList todoList) throws IOException {
//        System.out.println("Enter Task Description:");
//        userInput = input.nextLine();
//        try {
//            todoList.removeTask(userInput);
//        } catch (TaskNotFoundException t) {
//            System.out.println("Task Does Not Exist, try again!");
//        } finally {
//            handleInput(todoList);
//        }
//    }
//
//    //Modifies: Task
//    //Effects: makes new  type of task that user wants with user provided name
//    private void handleMakingTask(TodoList todoList) throws IOException {
//        System.out.println("Press: 1 to make task, 2 to make urgent task, 3 to make LongTermTask");
//        userInput = input.nextLine();
//        selectTypeOfTask(userInput, todoList);
//    }
//
//    //Modifies: task
//    //Effects: selects which type of task to make
//    private void selectTypeOfTask(String userInput, TodoList todoList) throws IOException {
//        if (userInput.equals("1")) {
//            makeTask(todoList);
//        } else if (userInput.equals("2")) {
//            makeUrgentTask(todoList);
//        } else if (userInput.equals("3")) {
//            makeLongTermTask(todoList);
//        } else {
//            handleInput(todoList);
//        }
//    }
//
//    private void makeTask(TodoList todoList) throws IOException {
//        try {
//            System.out.println("Type Description of Task: ");
//            userInput = input.nextLine();
//            TaskInterface newTask = new Task(userInput);
//            todoList.addTask(newTask, 0);
//            handleInput(todoList);
//        } catch (TooManyTasksException e) {
//            handleTooManyTasksException(todoList);
//        }
//    }
//
//
//    private void makeUrgentTask(TodoList todoList) throws IOException {
//        try {
//            System.out.println("Type Description of Task: ");
//            userInput = input.nextLine();
//            TaskInterface newTask = new UrgentTask(userInput);
//            //TaskInterface newTask = new UrgentTask(userInput);
//            todoList.addTask(newTask, 0);
//            handleInput(todoList);
//        } catch (TooManyTasksException e) {
//            handleTooManyTasksException(todoList);
//        }
//    }
//
//    private void makeLongTermTask(TodoList todoList) throws IOException {
//        try {
//            System.out.println("Type Description of Task: ");
//            userInput = input.nextLine();
//            TaskInterface newTask = new LongTermTask(userInput);
//            todoList.addTask(newTask, todoList.getSize() - 1);
//            handleInput(todoList);
//        } catch (TooManyTasksException e) {
//            handleTooManyTasksException(todoList);
//        }
//    }
//
//
//    private void handleTooManyTasksException(TodoList todoList) throws IOException {
//        System.out.println("Too many tasks, delete some tasks and try again!");
//        handleInput(todoList);
//    }
//
//
//
//}
