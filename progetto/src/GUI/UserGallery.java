package GUI;

import CONTROLLER.Controller;
import DAO.AlbumDAO;
import ImplPostgresDAO.AlbumPostgressDAO;
import MODEL.Album;
import MODEL.Utente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserGallery extends JFrame {


    Controller gallerycontroller = new Controller();

    public UserGallery(Utente utente) throws SQLException {

        setTitle("Galleria Personale");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JFrame mainframe = new JFrame();

        //pannello principale
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        //titolo
        JLabel titleLabel = new JLabel("Galleria fotografica di "+ utente.getNome()/*,SwingConstants.CENTER*/);
        titleLabel.setFont(new Font("Arial nova", Font.BOLD, 30));
        titleLabel.setForeground(Color.DARK_GRAY);
        panel.add(titleLabel, BorderLayout.NORTH);

        //pannello per gli album
        JPanel albumsPanel = new JPanel();
        albumsPanel.setLayout(new GridBagLayout());
        albumsPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20,20,20,20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;

        //aggiunta album al pannello
        ArrayList<String> userAlbums = gallerycontroller.getAlbums(utente.getIdUtente());
        System.out.println(userAlbums);

        if (userAlbums.isEmpty()){
            JOptionPane.showMessageDialog(this, "Nessun album trovato");
        } else{
            albumsPanel.removeAll();

            int col = 0;
            for (String album : userAlbums){
                JPanel albumBox = createAlbumBox(album, utente);
                albumsPanel.add(albumBox, gbc);

                col++;
                if (col == 3){
                    col = 0;
                    gbc.gridx = 0;
                    gbc.gridy++;
                }
                else {
                    gbc.gridx++;
                }
            }

            albumsPanel.revalidate();
            albumsPanel.repaint();
        }


        //panel.add(albumsPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(albumsPanel), BorderLayout.CENTER);


        //pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); //allinea a sinistra
        buttonPanel.setBackground(Color.WHITE);

        JButton addAlbumButton = createStyleButton("Aggiungi Album");

        addAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new NewAlbumCreation(utente);


            }
        });

        albumsPanel.revalidate();
        albumsPanel.repaint();
        buttonPanel.add(addAlbumButton);

        JButton logoutButton = createStyleButton("logout");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0,0,0,0);
        buttonPanel.add(logoutButton, gbc);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainApp();
                dispose();
            }
        });

        panel.add(buttonPanel, BorderLayout.SOUTH);

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
        nameLabel.setFont(new Font("Arial nova", Font.PLAIN, 14));
        nameLabel.setForeground(Color.GRAY);


        albumBox.add(nameLabel, BorderLayout.SOUTH);


        albumBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        albumBox.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                try {
                    new AlbumPage(albumNAme, utente);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return albumBox;

    }

    private Color generateRandomColor(){
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        return new Color(red, green, blue);
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
