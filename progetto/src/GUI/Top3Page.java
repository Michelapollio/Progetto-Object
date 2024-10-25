package GUI;

import CONTROLLER.Controller;
import MODEL.Luogo;

import javax.naming.ldap.Control;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;


public class Top3Page extends JFrame {
    Controller gallerycontroller = new Controller();
    JTable top3table;

    public Top3Page() throws SQLException {
        setTitle("Top3Places");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JFrame top3Frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Top 3 Luoghi Immortalati");
        title.setFont(new Font("Arial nova", Font.BOLD, 30));
        title.setForeground(Color.DARK_GRAY);
        add(title, BorderLayout.NORTH);

        String[] column = {"", "idluogo", "nome", "latitudine", "longitudine"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        top3table = new JTable(model);

        top3table.getColumnModel().getColumn(0).setCellRenderer(new ColorRenderer());
        top3table.setBackground(Color.WHITE);
        top3table.setFont(new Font("Arial nova", Font.PLAIN, 16));
        top3table.getColumnModel().getColumn(0).setPreferredWidth(5);
        top3table.getColumnModel().getColumn(1).setPreferredWidth(5);
        top3table.getColumnModel().getColumn(2).setPreferredWidth(200);
        top3table.getColumnModel().getColumn(3).setPreferredWidth(100);
        top3table.getColumnModel().getColumn(4).setPreferredWidth(100);

        loadPlaces(model);

        JScrollPane scrollPane = new JScrollPane(top3table);
        add(scrollPane, BorderLayout.CENTER);

        JButton logoutButton = createStyleButton("close");

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        add(logoutButton, BorderLayout.SOUTH);

        setVisible(true);

    }

    private void loadPlaces (DefaultTableModel model) throws SQLException {
        ArrayList<Luogo> top3list = gallerycontroller.top3places();
        int cont = 1;

        for (Luogo luogo : top3list){
            model.addRow(new Object[]{cont,luogo.getIdLuogo(), luogo.getNome(), luogo.getLatitudine(), luogo.getLongitudine()});
            cont++;
        }
    }

    private JButton createStyleButton(String text){
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0,122,255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //arrotondamento dei bordi
        button.setPreferredSize(new Dimension(100, 40));
        button.setBorder(BorderFactory.createLineBorder(new Color(0,122,255), 2, true));
        //button.setContentAreaFilled(false);
        //button.setOpaque(true);

        return button;
    }

}
