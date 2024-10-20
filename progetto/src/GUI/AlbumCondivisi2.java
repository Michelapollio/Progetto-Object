package GUI;

import CONTROLLER.Controller;
import MODEL.Foto;
import MODEL.Utente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.jar.JarEntry;

public class AlbumCondivisi2 extends JFrame {
    private JTable fotoTable;
    private JTable partecipantiTable;
    Controller gallerycontroller = new Controller();

    public AlbumCondivisi2(String albumname, Utente utente) throws SQLException {
        setTitle(albumname);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        int idalbum = gallerycontroller.getIdAlbum(albumname, utente.getIdUtente());

        JLabel title = new JLabel(albumname);
        title.setFont(new Font("Arial nova", Font.BOLD, 30));
        title.setForeground(Color.DARK_GRAY);
        add(title, BorderLayout.NORTH);

        String[] columnsfoto = {"anteprima", "idfoto", "dispositivo"};
        DefaultTableModel fotomodel = new DefaultTableModel(columnsfoto, 0);
        fotoTable = new JTable(fotomodel);

        fotoTable.getColumnModel().getColumn(0).setCellRenderer(new ColorRenderer());
        fotoTable.setBackground(Color.WHITE);
        fotoTable.setFont(new Font("Arial nova", Font.PLAIN, 16));
        fotoTable.getColumnModel().getColumn(0).setPreferredWidth(1);
        fotoTable.getColumnModel().getColumn(1).setPreferredWidth(400);
        fotoTable.getColumnModel().getColumn(2).setPreferredWidth(400);

        fotoTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fotoTable.addMouseListener(new java.awt.event.MouseAdapter(){

            public void mouseClicked(java.awt.event.MouseEvent evt){
                int selectedRow = fotoTable.getSelectedRow();
                System.out.println(selectedRow);

                if (selectedRow != -1){
                    int idfoto = (int) fotoTable.getValueAt(selectedRow, 1);
                    System.out.println(idfoto);
                    try {
                        new FotoPage(idfoto);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }

                else {
                    JOptionPane.showMessageDialog(null, "nessuna foto selezionata","errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        loadPhotos(fotomodel, idalbum);
        JScrollPane fotoScrollPane = new JScrollPane(fotoTable);

        String[] columnpartecipants = {"", "nome", "cognome"};
        DefaultTableModel partModel = new DefaultTableModel(columnpartecipants, 0);
        partecipantiTable = new JTable(partModel);

        partecipantiTable.getColumnModel().getColumn(0).setCellRenderer(new ColorRenderer());
        partecipantiTable.setBackground(Color.WHITE);
        partecipantiTable.setFont(new Font("Arial nova", Font.PLAIN, 16));
        partecipantiTable.getColumnModel().getColumn(0).setPreferredWidth(1);
        partecipantiTable.getColumnModel().getColumn(1).setPreferredWidth(400);
        partecipantiTable.getColumnModel().getColumn(2).setPreferredWidth(400);


        loadUser(partModel, idalbum);
        JScrollPane partScrollPane = new JScrollPane(partecipantiTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, fotoScrollPane, partScrollPane);
        splitPane.setDividerLocation(400);
        add(splitPane, BorderLayout.CENTER);

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
    private void loadPhotos(DefaultTableModel model, int idalbum) throws SQLException {
        ArrayList<Foto> albumFoto = gallerycontroller.getFotoAlbum(idalbum);


        for (Foto foto : albumFoto){
            Color colorPreview = new Color((int)(Math.random() *0x1000000));

            model.addRow(new Object[]{colorPreview, foto.getIdfoto(), foto.getIdDispositivo()});
        }
    }
    private void loadUser(DefaultTableModel model, int idalbum) throws SQLException {
        ArrayList<Utente> partecipantiList = gallerycontroller.getAllPartecipants(idalbum);
        int count = 1;

        for (Utente utente : partecipantiList){

            model.addRow(new Object[]{count, utente.getNome(), utente.getCognome()});
            count++;
        }
    }
    private JButton createStyleButton(String text){
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
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
