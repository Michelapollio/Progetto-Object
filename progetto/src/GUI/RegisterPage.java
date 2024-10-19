package GUI;

import CONTROLLER.Controller;
import MODEL.Utente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static java.awt.SystemColor.text;

public class RegisterPage extends JFrame {

    Controller gallerycontroller = new Controller();
    private JTextField firstNAmefield;
    private JTextField lastNamefield;
    private JTextField emailfield;
    private JPasswordField passwordfield;
    private JButton registerButton;
    private JButton cancelButton;

    public RegisterPage(){
        setTitle("Registrazione Utente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);
        setLocationRelativeTo(null);

        //pannello principale
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //nome
        JLabel firstnamelabel = new JLabel("Nome: ");
        firstNAmefield = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(firstnamelabel, gbc);
        gbc.gridx = 1;
        panel.add(firstNAmefield, gbc);

        //cognome
        JLabel lastnamelabel = new JLabel("Cognome: ");
        lastNamefield = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lastnamelabel, gbc);
        gbc.gridx = 1;
        panel.add(lastNamefield, gbc);

        //email
        JLabel emaillabel = new JLabel("Email: ");
        emailfield = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(emaillabel, gbc);
        gbc.gridx = 1;
        panel.add(emailfield, gbc);

        //password
        JLabel passwordlabel = new JLabel("Password: ");
        passwordfield = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordlabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordfield, gbc);

        //pulsante di registrazione
        registerButton = createStyleButton("Registrati");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        //pulsante annullamento
        cancelButton = createStyleButton("annulla");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(cancelButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    registerUser();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainApp();
                dispose();
            }
        });

        add(panel);
        setVisible(true);

    }

    private void registerUser() throws SQLException {
        String firstName = firstNAmefield.getText();
        String lastname = lastNamefield.getText();
        String email = emailfield.getText();
        String password = new String(passwordfield.getPassword());

        if (firstName.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Compilare tutti i campi");
            return;
        }

        //controlla se l'utente è già registrato
        /*if (gallerycontroller.registerUser(firstName, lastname, email, password)) {
            JOptionPane.showMessageDialog(this, "Sei già registrato!");
        } else {
            //addUserToDatabase(firstName, lastname, email, password);
            JOptionPane.showMessageDialog(this, "Registrazione avvenuta con successo");
            new UserGallery(firstName);
            dispose();
            //}

        }*/
        boolean flag = gallerycontroller.registerUser(firstName, lastname, email, password);
        if (flag){
            JOptionPane.showMessageDialog(this, "Registrazione avvenuta con successo");
            Utente nuovouser = new Utente(firstName, lastname, email,password);
            Utente nuovouser1 = gallerycontroller.getInfoUser(email, password);
            new UserGallery(nuovouser1);
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(this, "Errore durante la fase di registrazione");
            new MainApp();
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
