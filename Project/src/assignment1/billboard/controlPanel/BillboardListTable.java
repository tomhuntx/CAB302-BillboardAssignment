package assignment1.billboard.controlPanel;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

/**
 * Create the JTable for listing billboards.
 * It has four data columns (billboard name, creator, preview button, edit button, delete button)
 */
public class BillboardListTable extends DefaultTableModel {
    private static String[] columns = {"Billboard Name", "Creator", "Preview", "Edit", "Delete"};
    private static String[][] data = {
            { "Billboard1", "Jim", "PREV", "EDIT", "DEL" },
            { "Billboard2 Longer Name 3000", "Fred",  "PREV", "EDIT", "DEL"},
            { "Billboard3", "Another name here",  "PREV", "EDIT", "DEL"},
            { "Billboard4", "A fourth dude",  "PREV", "EDIT", "DEL"}
    };

    private String tableName;
    private static JButton currentButton = new JButton();
    private static JButton prevButton = new JButton();
    private static JButton delButton = new JButton();
    private static JButton editButton = new JButton();

    BillboardListTable(String type) {
        super(data, columns);
        tableName = type;
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
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            currentButton.setText(label);

            for( ActionListener al : currentButton.getActionListeners() ) {
                currentButton.removeActionListener( al );
            }
            switch (label) {
                case "PREV":
                    currentButton.addActionListener(event -> JOptionPane.showMessageDialog(null,"You have pressed preview button") );

                    break;
                case "EDIT":
                    currentButton.addActionListener(event -> JOptionPane.showMessageDialog(null,"You have pressed edit button") );
                    break;
                case "DEL":
                    currentButton.addActionListener(ae -> {
                        // Check that selected row exists
                        if (table.getRowCount() > 0) {
                            int selectedRow = table.getSelectedRow();
                            if(selectedRow != -1) {
                                // Remove selected row
                                DefaultTableModel model = (DefaultTableModel) table.getModel();
                                int input = JOptionPane.showConfirmDialog(null,
                                        "Are you sure you want to delete " + model.getValueAt(row, 0), "Billboard Deletion", JOptionPane.OK_CANCEL_OPTION);
                                if (input == JOptionPane.YES_OPTION) {
                                    model.removeRow(selectedRow);
                                }
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Please select a billboard to delete.",
                                    "Attention", JOptionPane.INFORMATION_MESSAGE);
                        }

                    });

                    break;
                default:
                    currentButton.addActionListener(event -> JOptionPane.showMessageDialog(null,"Error") );
            }

            return currentButton;
        }
        public Object getCellEditorValue() {
            return label;
        }
    }
}