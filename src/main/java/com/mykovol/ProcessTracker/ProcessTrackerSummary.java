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
    private JButton ButtonRefresh;

    public ProcessTrackerSummary() {
        super("ProcessTracker Summary");

        setContentPane(mainPanel);
        pack();
        //take up the default look and feel specified by windows themes
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //make the window startup position be centered
        setLocationRelativeTo(null);
        setResizable(false);

        initValues();
        setVisible(true);

        ButtonRefresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                initValues();
            }
        });
    }


    public void initValues(){
        textFieldBufferSize.setText(String.valueOf(Buffer.getBufferSize()));
        textFieldLastSync.setText(Buffer.getLastSyncTimeAgo().toMinutes()+"min ago "+Buffer.getLastSyncCount()+" elements");
    }
}

