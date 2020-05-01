package main.ui;//package ui;
//
//
//import model.todolist.TodoList;
//import model.todolist.saving.Load;
//import network.Weather;
//
//import java.io.IOException;
//
//public class Main {
//
//    private static HandleUserInput consoleInput;
//
//    private static void runTodoList() throws IOException {
//        System.out.println("Welcome to your TodoList!");
//        //System.out.println("Do Not Have Spaces in Names!");
//        Weather w = new Weather();
//        w.printWeather();
//        TodoList newToDo = new TodoList();
//        Load load = new Load();
//        load.load("C:\\Users\\preet\\IdeaProjects\\cpsc_210\\project_r7i2b\\data\\Store.txt", newToDo);
//        consoleInput = new HandleUserInput();
//        consoleInput.handleInput(newToDo);
//        System.out.println("The main method has been executed!");
//    }
//
//    public static void main(String[] args) throws IOException {
//        runTodoList();
//    }
//}
