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

        //Aggiunta degli album al pannello
        /*for (String album: albums){
            JButton albumButton = new JButton(album);
            albumButton.setFont(new Font("Arial", Font.PLAIN, 18));
            albumButton.setBackground(new Color(172, 174, 178));
            albumButton.setFocusPainted(false);
            albumButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            albumButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new AlbumPage(album);
                }
            });
            albumsPanel.add(albumButton);
        }*/

        //aggiunta album forse mo funziona
        ArrayList<String> userAlbums = gallerycontroller.getAlbums(utente.getIdUtente());
        System.out.println(userAlbums);

        if (userAlbums.isEmpty()){
            JOptionPane.showMessageDialog(this, "Nessun album trovato");
        } else{
            albumsPanel.removeAll();

            int col = 0;
            for (String album : userAlbums){
                JPanel albumBox = createAlbumBox(album);
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

                new NewAlbumCreation(utente.getIdUtente());


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

    private JPanel createAlbumBox(String albumNAme){
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
                new AlbumPage(albumNAme);
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

    private void showalbumDialog(){
        JComboBox<String> visibityBox;
        JDialog addAlbumDialog = new JDialog(this, "Aggiundi Album", true);
        addAlbumDialog.setSize(300, 150);
        addAlbumDialog.setLocationRelativeTo(this);
        addAlbumDialog.setLayout(new GridLayout(3,1));

        JTextField albumNameField = new JTextField();
        addAlbumDialog.add(new JLabel("Nome dell'album: "));
        addAlbumDialog.add(albumNameField);

        String[] visibilityOption = {"Pubblico, Privato"};
        visibityBox = new JComboBox<>(visibilityOption);



        //pannello per i pulsanti
        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Crea");
        JButton cancelButton = new JButton("Annulla");

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        addAlbumDialog.add(buttonPanel);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newAlbumName = albumNameField.getText().trim();

                JOptionPane.showMessageDialog(null, "creo nuovo album con nome" +newAlbumName);
                JButton newAlbumButton = new JButton(newAlbumName);
                newAlbumButton.setFont(new Font("Arial", Font.PLAIN, 18));
                newAlbumButton.setBackground(new Color(240,240,240));
                newAlbumButton.setForeground(Color.BLACK);
                newAlbumButton.setFocusPainted(false);
                newAlbumButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                newAlbumButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new AlbumPage(newAlbumName);
                    }
                });

                JPanel albumPanel = (JPanel) getContentPane().getComponent(0);
                albumPanel.add(newAlbumButton);
                albumPanel.revalidate();
                albumPanel.repaint();
                addAlbumDialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAlbumDialog.dispose();
            }
        });


       addAlbumDialog.setVisible(true);

    }


}
