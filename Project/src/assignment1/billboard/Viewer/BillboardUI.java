package assignment1.billboard.Viewer;



import assignment1.billboard.Server.DBConnection;

import javax.swing.*;

public class BillboardUI extends JFrame {

    DBConnection data;

    /**
     * Constructor sets up user interface, adds listeners and displays.
     *
     * @param data The underlying data/model class the UI needs.
     */
    public BillboardUI(DBConnection data) {
        this.data = data;

        /* FROM PRAC 7
        initUI();
        checkListSize();

        // add listeners to interactive components
        addButtonListeners(new ButtonListener());
        addNameListListener(new NameListListener());
        addClosingListener(new ClosingListener());

        // decorate the frame and make it visible
        setTitle("Address Book");
        setMinimumSize(new Dimension(400, 300));
        pack();
        setVisible(true);


        Add many methods to draw UI from here.....
        */
    }
}
