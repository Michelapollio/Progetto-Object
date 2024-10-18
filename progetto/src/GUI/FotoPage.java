package GUI;

import MODEL.Foto;
import MODEL.Soggetto;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FotoPage extends JFrame {
    private Foto foto;
    private String luogo;
    private ArrayList<Soggetto> soggetti;

    public FotoPage(Foto foto, ArrayList<Soggetto> soggetti, String luogo){
        this.foto = foto;
        this.soggetti = soggetti;
        this.luogo = luogo;

        setupUI();
    }

    private void setupUI(){
        setTitle("Foto: "+foto.getIdfoto());
        setLayout(new BorderLayout());

        //finto riquadro foto
        JPanel fotoPanel = new JPanel();
        fotoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        fotoPanel.setLayout(new BorderLayout());

        //quadrato colorato
        JPanel coloredSquare = new JPanel();
        coloredSquare.setBackground(new Color((int) Math.random() * 0x1000000));
        coloredSquare.setPreferredSize(new Dimension(100, 100));

        //pannello info foto
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        //etichetta per id foto
        JLabel idlabel = new JLabel("ID Foto: "+ foto.getIdfoto());
        idlabel.setFont(new Font("Arial nova", Font.BOLD, 16));

        //etichetta per il luogo di scatto
        JLabel luogolabel = new JLabel(luogo);
        luogolabel.setFont(new Font("Arial nova", Font.BOLD, 16));

        infoPanel.add(idlabel);
        infoPanel.add(luogolabel);

        fotoPanel.add(coloredSquare, BorderLayout.WEST);
        fotoPanel.add(infoPanel, BorderLayout.CENTER);

        add(fotoPanel, BorderLayout.NORTH);

        //tabella soggetti
        String[] colum = {"Presente in foto"};
    }


}
