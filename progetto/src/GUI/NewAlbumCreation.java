package GUI;

import CONTROLLER.Controller;
import ImplPostgresDAO.AlbumPostgressDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class NewAlbumCreation extends JFrame {
    private JTextField albumNamefield;
    private AlbumPostgressDAO album;
    private JComboBox<String> visibilityBox;
    Controller galleryController = new Controller();

    public NewAlbumCreation(int idOwner){
        //Configurazione della finsetra
        setTitle("Accesso utente già registrato");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //etichettta EMAIL
        JLabel nomeAlbum = new JLabel ("Nome: ");
        nomeAlbum.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10,10,10,10);
        panel.add(nomeAlbum, gbc);

        //campo di imput per email
        albumNamefield = new JTextField();
        albumNamefield.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(albumNamefield, gbc);

        //etichetta PASSWORD
        JLabel privacyLabel = new JLabel("Privacy:");
        privacyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(privacyLabel, gbc);

        //privacy
        String[] visibilityOptions = {"Pubblico", "Privato"};
        visibilityBox = new JComboBox<>(visibilityOptions);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(visibilityBox, gbc);

        //pulsante annulla
        JButton cancelButton = createStyleButton("annulla");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(20,10,10,10);
        panel.add(cancelButton, gbc);

        //pulsante ok
        JButton okButton = createStyleButton("crea");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(okButton, gbc);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createAlbum(idOwner);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(panel);
        setVisible(true);

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

    private void createAlbum(int idOwner) throws SQLException {
        String albumName = albumNamefield.getText();
        String visibility = (String) visibilityBox.getSelectedItem();

        if (albumName.isEmpty()){
            JOptionPane.showMessageDialog(this, "inserisci un nome per il tuo album");
        }
        boolean isVisible = visibility.equals("Pubblico");

        boolean success = galleryController.addNewAlbum(albumName, idOwner, isVisible);

        if (success){
            JOptionPane.showMessageDialog(this, "Album aggiunto con successo", "successo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(this, "Errore durante la creazione dell'album", "Errore", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
}
