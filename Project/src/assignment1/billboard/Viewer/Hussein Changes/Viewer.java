package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;

import static jdk.internal.net.http.common.Utils.close;


public class Viewer extends JFrame{


    public Viewer(){

        JFrame frame = new JFrame("Viewer GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 100));
        frame.setLocation(new Point(200, 200));
        frame.pack();
        frame.setVisible(true);

        // Create and set up the window.

        // Display the window.

        // full screen

        JPanel BillboardPlaceHolder = new JPanel();
        frame.add(BillboardPlaceHolder);


        // do action listener
        //mouse click

        //works
         ClickListener clickListener = new ClickListener(frame);
         BillboardPlaceHolder.addMouseListener(clickListener);


         // esc bar pressed:
        // Does not work
        PressListener presslistener = new PressListener();
        BillboardPlaceHolder.addKeyListener(presslistener);


        // Does not work
        //BillboardPlaceHolder.addKeyListener(new KeyListener() {

        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);


//        // decorate the frame and make it visible
//        setTitle("Address Book");
//        setMinimumSize(new Dimension(400, 300));
//        pack();
//        setVisible(true);


//        Add many methods to draw UI from here.....


    }






        public static void main(String[] args) {
            //Schedule a job for the event-dispatching thread:
            //creating and showing this application's GUI.
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new Viewer();
                }
            });



        // every 15 seconds? Timer
        Timer timer = new Timer();
        // do DATA Exchange
        // Draw XML
        for(;;) {
            Timer.schedule(new Show_Billboard(), 0, 15000);
        }

    }
}


