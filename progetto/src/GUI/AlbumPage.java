package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.jar.JarEntry;

public class AlbumPage extends JFrame {
    public AlbumPage(String albumName) {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(albumName);
        titleLabel.setFont(new Font("Arial nova", Font.BOLD, 30));
        titleLabel.setForeground(Color.DARK_GRAY);

        panel.add(titleLabel, BorderLayout.NORTH);


        setVisible(true);
    }


}
