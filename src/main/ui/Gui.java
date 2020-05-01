package main.ui;

import main.model.TodoList;
import main.model.exceptions.TooManyTasksException;
import main.model.saving.Load;
import main.model.saving.Save;
import main.model.tasks.TaskInterface;
import main.model.tasks.typesOfTasks.LongTermTask;
import main.model.tasks.typesOfTasks.Task;
import main.model.tasks.typesOfTasks.UrgentTask;
import main.network.Weather;
import main.ui.listeners.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

public class Gui {

    //static JFrame frame;
    static String weather;
    static TodoList todoList;

    public static void main(String[] args) {

        Weather w = new Weather();
        weather = null;
        try {
            weather = w.printWeather();
        } catch (IOException io) {
            // do nothing
        }

        todoList = new TodoList();
        Load load = new Load();
        try {
            load.load("/Users/preetnakrnai/IdeaProjects/TodoList_Project/data/savedList.txt", todoList);
            //load.load("C:\\Users\\preet\\IdeaProjects\\cpsc_210\\project_r7i2b\\data\\Store.txt", todoList);
        } catch (IOException | TooManyTasksException ex) {
            // not reached
        }
        Collections.reverse(todoList.getList());
        runTodoList();

    }

    private static void runTodoList() {

        JFrame frame = new JFrame("Welcome to your TodoList");


        JButton start = new JButton("Start TodoList");
        //frame.getContentPane().add(start);
        start.setBounds(250,250,500,500);
        frame.add(start); // Adds Button to content pane of frame
        start.addActionListener(new StartPress());
        frame.setSize(1000,1000);
        frame.setLayout(null);
        frame.setVisible(true);

    }

    public static void afterStart() {
        JFrame frame = new JFrame("Current Weather: " + weather);
        JLabel label = new JLabel("Tasks:");
        label.setBounds(10, 10, 300, 50);
        frame.add(label);
        int x = 10;
        int y = 100;
        int w = 990;
        int h = 40;
        for (TaskInterface ti : todoList.getList()) {
            JButton task = taskButton(x, y, w, h, todoList, ti);
            frame.add(task);
            y = y + 50;
        }
        helperToAfterStart(frame);
    }

    private static void helperToAfterStart(JFrame frame) {
        JButton quit = new JButton("Quit");
        quit.setBounds(800, 800, 100, 50);
        quit.addActionListener(new QuittingListener());
        JButton addTask = new JButton("Make a new Task");
        addTask.setBounds(300, 800, 300, 100);
        addTask.addActionListener(new MakingTask());
        frame.add(quit);
        frame.add(addTask);
        frame.setSize(1000, 1000);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private static JButton taskButton(int x, int y, int w, int h, TodoList todoList, TaskInterface ti) {
        String status = getStatusString(ti);

        JButton task = new JButton(ti.getDescription() + ": " + status + "!");
        task.setBounds(x, y, w, h);
        if (ti.getStatus()) {
            task.setBackground(Color.GREEN);
            task.setOpaque(true);
        } else {
            task.setBackground(Color.RED);
            task.setOpaque(true);
        }
        task.addActionListener(new TaskActionListner() {
            public void actionPerformed(ActionEvent ae) {
                Gui.play("/Users/preetnakrnai/IdeaProjects/TodoList_Project/data/feedback.wav");
                clickingATask(todoList, ti);
            }
        });
        return task;

    }

    private static String getStatusString(TaskInterface ti) {
        String status;
        if (ti.getStatus()) {
            status = "completed";
        } else {
            status = "in-complete";
        }
        return status;
    }

    private static void clickingATask(TodoList todoList, TaskInterface ti) {
        JFrame frame = new JFrame("Task");
        JLabel task = new JLabel(ti.getDescription() + " is " + getStatusString(ti) + "!");
        task.setBounds(100, 100, 990, 100);
        frame.add(task);
        JButton toShow;
        if (ti.getStatus()) {
            toShow = new JButton("in-complete");
            toShow.setBackground(Color.ORANGE);
            task.setOpaque(true);
        } else {
            toShow = new JButton("complete");
            toShow.setBackground(Color.ORANGE);
            task.setOpaque(true);
        }
        helperToClickingTask(todoList, ti, frame, toShow);

    }

    private static void helperToClickingTask(TodoList todoList, TaskInterface ti, JFrame frame, JButton toShow) {
        toShow.setBounds(100, 300, 300, 100);
        toShow.addActionListener(new ChangingStatus() {
            public void actionPerformed(ActionEvent ae) {
                Gui.play("/Users/preetnakrnai/IdeaProjects/TodoList_Project/data/feedback.wav");
                Boolean oldStatus = ti.getStatus();
                ti.setStatus(!oldStatus);
                afterStart();
            }
        });

        JButton goBack = helperToHelperToClickingTaskTwo();

        helperToHelperToClickingTask(todoList, ti, frame, toShow, goBack);
    }

    private static JButton helperToHelperToClickingTaskTwo() {
        JButton goBack = new JButton("Go back to todoList");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.play("/Users/preetnakrnai/IdeaProjects/TodoList_Project/data/feedback.wav");
                afterStart();
            }
        });
        goBack.setBounds(700,300,200,100);
        return goBack;
    }

    private static void helperToHelperToClickingTask(TodoList d, TaskInterface ti, JFrame s, JButton t, JButton g) {
        JButton remove = new JButton("Delete");
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play("/Users/preetnakrnai/IdeaProjects/TodoList_Project/data/delete.wav");
                d.removeTask(ti);
                afterStart();
            }
        });
        remove.setBounds(100,700, 200, 100);

        s.add(remove);
        s.add(g);
        s.add(t);
        s.setSize(1000, 1000);
        s.setLayout(null);
        s.setVisible(true);
    }

    public static void makingTask() {
        JFrame frame = new JFrame("Make a new Task");
        JButton task = new JButton("Make new Task");
        task.setBounds(100, 100, 200, 100);
        JButton urgentTask = new JButton("Make new UrgentTask");
        urgentTask.setBounds(200, 200, 200, 100);
        JButton longTermTask = new JButton("Make new LongTermTask");
        longTermTask.setBounds(300, 300, 200, 100);
        task.addActionListener(new TaskListener());
        urgentTask.addActionListener(new UrgentTaskListener());
        longTermTask.addActionListener(new LongTermTaskListener());
        frame.add(task);
        frame.add(urgentTask);
        frame.add(longTermTask);
        frame.setSize(1000, 1000);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void makeTask() {
        JFrame frame = new JFrame("Making Task");
        JLabel enter = new JLabel("Enter description of Task:");
        enter.setBounds(100, 100, 300, 100);
        JTextField field = new JTextField(900);
        field.setBounds(50, 200, 900, 200);
        JButton make = new JButton("Make");
        make.setBounds(50, 700, 300, 100);
        helperToMakeTaskActionLIstener(field, make);
        helperToMakeTask(frame, enter, field, make);
    }

    private static void helperToMakeTaskActionLIstener(JTextField field, JButton make) {
        make.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.play("/Users/preetnakrnai/IdeaProjects/TodoList_Project/data/feedback.wav");
                if (field.getText().isEmpty()) {
                    afterStart();
                } else {
                    TaskInterface newTask = new Task(field.getText());
                    try {
                        todoList.addTask(newTask, 0);
                        afterStart();
                    } catch (TooManyTasksException ex) {
                        afterStart();
                    }
                }

            }
        });
    }

    private static void helperToMakeTask(JFrame frame, JLabel enter, JTextField field, JButton make) {
        frame.add(make);
        frame.add(enter);
        frame.add(field);
        frame.setSize(1000, 1000);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void makeLongTermTask() {
        JFrame frame = new JFrame("Making Long Term Task");
        JLabel enter = new JLabel("Enter description of Task:");
        enter.setBounds(100, 100, 300, 100);
        JTextField field = new JTextField(900);
        field.setBounds(50, 200, 900, 200);
        JButton make = new JButton("Make");
        make.setBounds(50, 700, 300, 100);
        helperToMakeLongTermTaskActionListener(field, make);
        helperToMakeTask(frame, enter, field, make);
    }

    private static void helperToMakeLongTermTaskActionListener(JTextField field, JButton make) {
        make.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.play("/Users/preetnakrnai/IdeaProjects/TodoList_Project/data/feedback.wav");
                if (field.getText().isEmpty()) {
                    afterStart();
                } else {
                    TaskInterface newTask = new LongTermTask(field.getText());
                    try {
                        todoList.addTask(newTask, todoList.getSize());
                        afterStart();
                    } catch (TooManyTasksException ex) {
                        afterStart();
                    }
                }
            }
        });
    }

    public static void makingUrgentTask() {
        JFrame frame = new JFrame("Making UrgentTask");
        JLabel enter = new JLabel("Enter description of Task:");
        enter.setBounds(100, 100, 300, 100);
        JTextField field = new JTextField(900);
        field.setBounds(50, 200, 900, 200);
        JButton make = new JButton("Make");
        make.setBounds(50, 700, 300, 100);
        helperToMakingUrgentTaskActionListener(field, make);
        helperToMakeTask(frame, enter, field, make);
    }

    private static void helperToMakingUrgentTaskActionListener(JTextField field, JButton make) {
        make.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.play("/Users/preetnakrnai/IdeaProjects/TodoList_Project/data/feedback.wav");
                if (field.getText().isEmpty()) {
                    afterStart();
                } else {
                    TaskInterface newTask = new UrgentTask(field.getText());
                    try {
                        todoList.addTask(newTask, 0);
                        afterStart();
                    } catch (TooManyTasksException ex) {
                        afterStart();
                    }
                }
            }
        });
    }

    public static void quitting() {
        JFrame frame = new JFrame("You have quit, please close the window!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel quit = new JLabel("You have quit,  please close the window.");
        Save save = new Save();
        try {
            save.save("/Users/preetnakrnai/IdeaProjects/TodoList_Project/data/savedList.txt", todoList);
        } catch (IOException i) {
            //  not reached
        }
        quit.setBounds(100,100,300,100);
        frame.add(quit);
        frame.setSize(1000, 1000);
        frame.setLayout(null);
        frame.setVisible(true);
        closingTheWindow();
    }

    private static void closingTheWindow() {
        try {
            Thread.sleep(500);
            System.exit(0);
        } catch (InterruptedException e) {
            // un reached
        }
    }

    // taken from https://alvinalexander.com/java/java-audio-example-java-au-play-sound

    public static void play(String fileName) {
        try {
            InputStream inputStream = new FileInputStream(new File(fileName));
            AudioStream audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
        } catch (Exception e) {
            // taken from a website
        }
    }

}
