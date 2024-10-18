package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ColorRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value instanceof Color) {
            // Creiamo un pannello per mostrare un piccolo quadrato colorato
            JPanel colorPanel = new JPanel();
            colorPanel.setBackground((Color) value);
            colorPanel.setPreferredSize(new Dimension(10, 10));
            return colorPanel;
        }

        return c;
    }
}
