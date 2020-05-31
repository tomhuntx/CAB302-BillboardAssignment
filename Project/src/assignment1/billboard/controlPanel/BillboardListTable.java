package assignment1.billboard.controlPanel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Create the JTable for listing billboards.
 * It has four data columns (billboard name, creator, preview button, edit button, delete button)
 */
public class BillboardListTable extends DefaultTableModel {
    private static String[] columns = {"Billboard Name", "Creator", "Preview", "Edit", "Delete"};
    private static String[][] data = {
            { "Billboard1", "Jim", "PREV", "EDIT", "DEL" },
            { "Billboard2 Longer Name 3000", "Fred",  "PREV", "EDIT", "DEL"}
    };

    private String tableName;
    private static JButton button = new JButton();
    private static JButton delButton = new JButton();
    private static JButton editButton = new JButton();

    //Below button processing code assisted from https://allaboutbasic.com/2011/02/19/jbutton-in-jtable-cell-how-to-add-assign-or-fill-up-jtable%E2%80%99s-cell-with-jbutton-and-then-add-actionlistener-to-enable-click-event-for-that-jbuttons-in-jtable-cell/
    BillboardListTable(String type)
    {
        super(data, columns);
        tableName = type;

        button.addActionListener(event -> JOptionPane.showMessageDialog(null,"You have pressed a button") );
    }

    public boolean isCellEditable(int row, int cols) {
        if (tableName.equals("billboard list")) {
            return cols != 0;
        }
        return true;
    }

    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    static class ButtonEditor extends DefaultCellEditor {
        private String label;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }
        public Object getCellEditorValue() {
            return label;
        }
    }
}