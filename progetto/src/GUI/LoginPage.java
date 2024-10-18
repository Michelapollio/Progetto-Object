package GUI;

import ImplPostgresDAO.UtentePostgressDAO;
import MODEL.Utente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoginPage extends JFrame {


    private UtentePostgressDAO utenteDAO = new UtentePostgressDAO();

    private JTextField emailfield;
    private JPasswordField passwordfield;

    UtentePostgressDAO user = new UtentePostgressDAO();

    String prova = new String("Michela");

    public LoginPage(){

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
        JLabel emaillabel = new JLabel ("EMAIL");
        emaillabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10,10,10,10);
        panel.add(emaillabel, gbc);

        //campo di imput per email
        emailfield = new JTextField();
        emailfield.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(emailfield, gbc);

        //etichetta PASSWORD
        JLabel passwordlabel = new JLabel("PASSWORD");
        emaillabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordlabel, gbc);

        //campo di imput per email
        passwordfield = new JPasswordField();
        passwordfield.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordfield, gbc);

        //pulsante annulla
        JButton cancelButton = createStyleButton("annulla");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(20,10,10,10);
        panel.add(cancelButton, gbc);

        //pulsante ok
        JButton okButton = createStyleButton("ok");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(okButton, gbc);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainApp();
                dispose();
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                String email = emailfield.getText();
                String password = passwordfield.getText();
                boolean flag;

                try {
                    flag = utenteDAO.isUtentePresent(email, password);
                    System.out.println(flag);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if (flag){
                    Utente utente = null;
                    JOptionPane.showMessageDialog(null, "Accesso effettuato con successo !");
                    try {
                        utente = user.findInfoUtente(email, password);
                        System.out.println(utente.getNome() + utente.getIdUtente() );
                    } catch (SQLException ex) {
                       throw new RuntimeException(ex);
                    }
                    try {
                        new UserGallery(utente);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }else {
                    JOptionPane.showMessageDialog(null, "Email o password errati !", "Errore", JOptionPane.ERROR_MESSAGE);
                    dispose();

                }
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

    //private boolean isUserRegistered(String email, String password){
        //private boolean userDatabase.containsKey() && userDatabase.get(email).equals(password);
    //}

}


