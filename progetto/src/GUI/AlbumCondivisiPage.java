package GUI;

import CONTROLLER.Controller;
import MODEL.AlbumCond;
import MODEL.Utente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import static javax.swing.JOptionPane.*;

public class AlbumCondivisiPage extends JFrame {
    Controller gallerycontroller = new Controller();

    public AlbumCondivisiPage(Utente utente) throws SQLException {
        setTitle("Album Condivisi con me");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JFrame mainframe = new JFrame();

        //pannello principale
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Album Condivisi con "+ utente.getNome());
        titleLabel.setFont(new Font("Arial nova", Font.BOLD, 30));
        titleLabel.setForeground(Color.DARK_GRAY);
        panel.add(titleLabel, BorderLayout.NORTH);

        //pannello per gli album
        JPanel albumPanel = new JPanel();
        albumPanel.setLayout(new GridBagLayout());
        albumPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;

        ArrayList<AlbumCond> albumConds = gallerycontroller.getAllAlbumCond(utente.getIdUtente());

        albumPanel.removeAll();
        int col = 0;
        for (AlbumCond albumc : albumConds){
            JPanel albumBox = createAlbumBox(albumc.getNome(), utente);
            albumPanel.add(albumBox, gbc);

            col++;
            if (col == 3){
                col = 0;
                gbc.gridx = 0;
                gbc.gridy++;
            }else{
                gbc.gridx++;
            }
        }
        albumPanel.revalidate();
        albumPanel.repaint();

        panel.add(new JScrollPane(albumPanel), BorderLayout.CENTER);

        //pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);

        JButton addAlbumCondiviso = createStyleButton("Aggiungi Album Condiviso");


        addAlbumCondiviso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = false;
                boolean flag2 = false;
                String nomealbum = JOptionPane.showInputDialog("inserisci nome album condiviso");

                try {
                    flag = gallerycontroller.addNewAlbumCondiviso(nomealbum, utente.getIdUtente());
                    int idalbum = gallerycontroller.getIdAlbum(nomealbum, utente.getIdUtente());
                    flag2 = gallerycontroller.addAlbumCondiviso(idalbum, utente.getIdUtente());

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (flag){
                    JOptionPane.showMessageDialog(null, "Album Condiviso aggiunto con successo", "ok", INFORMATION_MESSAGE);
                    setVisible(false);
                    try {
                        new AlbumCondivisiPage(utente);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "impossibile creare album condiviso", "errore", ERROR_MESSAGE);
                }


            }
        });

        JButton partecipaButton = createStyleButton("partecipa");

        partecipaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = false;
                int idalbum = Integer.parseInt(JOptionPane.showInputDialog("inserisci identificativo album condiviso"));
                try {
                    flag = gallerycontroller.addAlbumCondiviso(idalbum, utente.getIdUtente());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if (flag){
                    JOptionPane.showMessageDialog(null, "Ora partecipi all'album", "ok", INFORMATION_MESSAGE);
                    setVisible(false);
                    try {
                        new AlbumCondivisiPage(utente);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Impossibile accedere all'album", "errore", ERROR_MESSAGE);

                }

            }
        });

        buttonPanel.add(addAlbumCondiviso);
        buttonPanel.add(partecipaButton);

        JButton logoutButton = createStyleButton("home");
        logoutButton.setLayout(new FlowLayout());

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel bottomRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomRightPanel.add(logoutButton, gbc);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomRightPanel, BorderLayout.SOUTH);
        add(panel);

        setVisible(true);

    }

    private JPanel createAlbumBox(String albumNAme, Utente utente){
        JPanel albumBox = new JPanel();
        albumBox.setLayout(new BorderLayout());
        albumBox.setPreferredSize(new Dimension(120,120));
        albumBox.setBackground(generateRandomColor());
        albumBox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));

        //nome dell'album
        JLabel nameLabel = new JLabel(albumNAme, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial nova", Font.BOLD, 14));
        nameLabel.setForeground(Color.GRAY);


        albumBox.add(nameLabel, BorderLayout.SOUTH);


        albumBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        albumBox.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                try {
                    new AlbumCondivisi2(albumNAme, utente);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return albumBox;

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

    private Color generateRandomColor(){
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        return new Color(red, green, blue);
    }

}
