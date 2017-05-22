package com.mykovol.ProcessTracker;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by VMykolaichuk on 5/14/2017.
 */
public class ProcessTrackerSummary extends JFrame{

    private JTextField textFieldBufferSize;
    private JPanel mainPanel;
    private JTextField textFieldLastSync;

    private static ProcessTrackerSummary ourInstance = new ProcessTrackerSummary();

    static ProcessTrackerSummary getInstance() {
        return ourInstance;
    }

    public ProcessTrackerSummary() {
        super("ProcessTracker Summary");

        setContentPane(mainPanel);
        pack();
        //take up the default look and feel specified by windows themes
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

    }

    public void showForm(){
        //make the window startup position be centered
        setLocationRelativeTo(null);
        setResizable(false);

        initValues();
        setVisible(true);
        toFront();
    }


    public void initValues(){
        textFieldBufferSize.setText(String.valueOf(Buffer.getBufferSize()));
        textFieldLastSync.setText(Buffer.getLastSyncTimeAgo().toMinutes()+"min ago "+Buffer.getLastSyncCount()+" elements");
    }
}

