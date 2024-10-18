package GUI;

import CONTROLLER.Controller;
import DAO.AlbumDAO;
import MODEL.Foto;
import MODEL.Utente;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.jar.JarEntry;

public class AlbumPage extends JFrame {

    private JTable fotoTable;

    Controller gallerycontroller = new Controller();
    public AlbumPage(String albumName, Utente utente) throws SQLException {

        int idalbum = gallerycontroller.getIdAlbum(albumName, utente.getIdUtente());

        /*setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        setTitle(albumName);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 6, 10, 10));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(albumName);
        titleLabel.setFont(new Font("Arial nova", Font.BOLD, 30));
        titleLabel.setForeground(Color.DARK_GRAY);

        panel.add(titleLabel, BorderLayout.NORTH);

        ArrayList<Integer> albumphotos = gallerycontroller.getFotoAlbum(idalbum);
        if (albumphotos.isEmpty()){
            JOptionPane.showMessageDialog(this, "nessuna foto trovata");
        } else{
            panel.removeAll();

            int col = 0;
            for (Integer foto : albumphotos){
                JPanel fotoBox = createPhotoBox();
                panel.add(fotoBox);

            }
        }
        JButton logoutButton = new JButton("home");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new UserGallery(utente);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(logoutButton, BorderLayout.SOUTH);


        setVisible(true);
    }

    private JPanel createPhotoBox(int idalbum){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel photoLabel = new JLabel(idalbum, Swin)
    }*/

        String[] column = {"Anteprima", "idfoto", "Dispositivo"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        fotoTable = new JTable(model);

        //prima colonna
        fotoTable.getColumnModel().getColumn(0).setCellRenderer(new ColorRenderer());
        fotoTable.setBackground(Color.WHITE);
        fotoTable.setFont(new Font("Arial nova", Font.PLAIN, 16));
        fotoTable.getColumnModel().getColumn(0).setPreferredWidth(1);
        fotoTable.getColumnModel().getColumn(1).setPreferredWidth(400);
        fotoTable.getColumnModel().getColumn(2).setPreferredWidth(400);

        fotoTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fotoTable.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                JOptionPane.showMessageDialog(null, "apertura foto");
            }
        });

        loadPhotos(model, idalbum);

        //aggiungiamo la tabella nello scrollPane
        JScrollPane scrollPane = new JScrollPane(fotoTable);
        add(scrollPane, BorderLayout.CENTER);

        //JButton logoutButton = new JButton("home");
        JButton logoutButton = createStyleButton("HOME");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new UserGallery(utente);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(logoutButton, BorderLayout.SOUTH);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        setVisible(true);
    }

    private void loadPhotos(DefaultTableModel model, int idalbum) throws SQLException {
        ArrayList<Foto> albumFoto = gallerycontroller.getFotoAlbum(idalbum);

        for (Foto foto : albumFoto){
            Color colorPreview = new Color((int)(Math.random() *0x1000000));

            model.addRow(new Object[]{colorPreview, foto.getIdfoto(), foto.getIdDispositivo()});
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

