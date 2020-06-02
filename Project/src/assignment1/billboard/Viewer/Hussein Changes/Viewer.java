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
        System.out.println("made it");

        JFrame frame = new JFrame("Viewer GUI");
        //JLabel label = new JLabel("Hello world. This is a GUI application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().add(label);
        frame.setPreferredSize(new Dimension(300, 100));
        frame.setLocation(new Point(200, 200));
        frame.pack();
        frame.setVisible(true);

        // from previous

        // Create and set up the window.

        // Display the window.


        ///

        // full screen

        JPanel BillboardPlaceHolder = new JPanel();
        frame.add(BillboardPlaceHolder);



        // do action listener
        //mouse click

        //works
       //  ClickListener clickListener = new ClickListener(frame);
       //  BillboardPlaceHolder.addMouseListener(clickListener);


         // esc bar pressed: Does not work
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



        // add listeners to interactive components
        // addButtonListeners(new ButtonListener());

//        addNameListListener(new NameListListener());
//        addClosingListener(new ClosingListener());
//
//        // decorate the frame and make it visible
//        setTitle("Address Book");
//        setMinimumSize(new Dimension(400, 300));
//        pack();
//        setVisible(true);


//        Add many methods to draw UI from here.....


    }




    public static void main(String[] args) {

        // initialise GUI
        //invokelater
        new Viewer();






        // every 15 seconds? Timer
        Timer timer = new Timer();
        // do DATA Exchange
        // Draw XML
        for(;;) {
            Timer.schedule(new Show_Billboard(), 0, 15000);
        }

    }
}


