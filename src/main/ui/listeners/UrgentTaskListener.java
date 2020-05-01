package main.ui.listeners;


import main.ui.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UrgentTaskListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Gui.play("/Users/preetnakrnai/IdeaProjects/TodoList_Project/data/feedback.wav");
        Gui.makingUrgentTask();
    }
}
