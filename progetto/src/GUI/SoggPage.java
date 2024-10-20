package GUI;

import CONTROLLER.Controller;
import MODEL.Foto;
import MODEL.Luogo;
import MODEL.Soggetto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class SoggPage extends JFrame {

    Soggetto sogg;
    private JTable fotoTable;
    Controller gallerycontroller = new Controller();

    public SoggPage(int idsogg) throws SQLException {
        sogg = gallerycontroller.getSoggInfo(idsogg);

        setTitle(sogg.getCategoria());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JFrame luogoFrame = new JFrame();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel(sogg.getCategoria());
        title.setFont(new Font("Arial nova", Font.BOLD, 30));
        title.setForeground(Color.DARK_GRAY);
        add(title, BorderLayout.NORTH);

        String[] column = {"Anteprima", "idfoto", "dispositivo"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        fotoTable = new JTable(model);

        fotoTable.getColumnModel().getColumn(0).setCellRenderer(new ColorRenderer());
        fotoTable.setBackground(Color.WHITE);
        fotoTable.setFont(new Font("Arial nova", Font.PLAIN, 16));
        fotoTable.getColumnModel().getColumn(0).setPreferredWidth(1);
        fotoTable.getColumnModel().getColumn(1).setPreferredWidth(400);
        fotoTable.getColumnModel().getColumn(2).setPreferredWidth(400);

        fotoTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fotoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = fotoTable.getSelectedRow();
                System.out.println(selectedRow);

                if (selectedRow != -1) {
                    int idfoto = (int) fotoTable.getValueAt(selectedRow, 1);
                    System.out.println(idfoto);
                    try {
                        new FotoPage(idfoto);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    //JOptionPane.showMessageDialog(null, "apertura foto", "foto", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "nessuna foto selezionata", "errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loadPhotos(model, sogg.getIdSogg());

        JScrollPane scrollPane = new JScrollPane(fotoTable);
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

    private void loadPhotos(DefaultTableModel model, int idsogg) throws SQLException {
        ArrayList<Foto> albumFoto = gallerycontroller.getFotoSogg(idsogg);

        for (Foto foto : albumFoto) {
            Color colorPreview = new Color((int) (Math.random() * 0x1000000));

            model.addRow(new Object[]{colorPreview, foto.getIdfoto(), foto.getIdDispositivo()});
        }
    }

    private JButton createStyleButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 122, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //arrotondamento dei bordi
        button.setPreferredSize(new Dimension(100, 40));
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 122, 255), 2, true));
        //button.setContentAreaFilled(false);
        //button.setOpaque(true);

        return button;
    }
}
