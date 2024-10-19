package GUI;

import CONTROLLER.Controller;
import MODEL.Foto;
import MODEL.Luogo;
import MODEL.Soggetto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class FotoPage extends JFrame {
    //private Foto foto;
    private Luogo luogo;
    private ArrayList<Soggetto> soggetti;
    Controller gallerycontroller = new Controller();

    public FotoPage(int idfoto) throws SQLException {
        luogo = gallerycontroller.getFotoLuogo(idfoto);
        soggetti = gallerycontroller.gettSoggFoto(idfoto);

        setupUI(idfoto);

    }

    private void setupUI(int idfoto){
        setTitle("Foto: "+ idfoto);
        setLayout(new BorderLayout());

        //finto riquadro foto
        JPanel fotoPanel = new JPanel();
        fotoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        fotoPanel.setLayout(new BorderLayout());

        //spazio
        JPanel spacepanel = new JPanel();
        spacepanel.setBorder(BorderFactory.createEmptyBorder(2, 1, 10, 1));
        spacepanel.setLayout(new CardLayout());

        //quadrato colorato
        JPanel coloredSquare = new JPanel();
        coloredSquare.setBackground(new Color((int) Math.random() * 0x1000000));
        coloredSquare.setPreferredSize(new Dimension(100, 100));

        //pannello info foto
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        //etichetta per id foto
        JLabel idlabel = new JLabel("ID Foto: "+ idfoto);
        idlabel.setFont(new Font("Arial nova", Font.BOLD, 16));

        //etichetta per il luogo di scatto
        if (luogo.getNome().isEmpty()){
            JLabel luogolabel = new JLabel();
            infoPanel.add(idlabel);
            infoPanel.add(luogolabel);
        }
        else {
            JLabel luogolabel = new JLabel(luogo.getNome());
            luogolabel.setFont(new Font("Arial nova", Font.BOLD, 16));
            infoPanel.add(idlabel);
            infoPanel.add(luogolabel);
        }


        fotoPanel.add(coloredSquare, BorderLayout.WEST);
        fotoPanel.add(infoPanel, BorderLayout.CENTER);
        fotoPanel.add(spacepanel, BorderLayout.AFTER_LINE_ENDS);

        add(fotoPanel, BorderLayout.NORTH);

        //tabella soggetti
        String[] colum = {"Presente in foto"};
        DefaultTableModel tableModel = new DefaultTableModel(colum, 0);
        JTable soggettiTable = new JTable(tableModel);

        loadSoggettiData(tableModel);

        JScrollPane scrollPane = new JScrollPane(soggettiTable);
        add(scrollPane, BorderLayout.CENTER);

        //impostazioni finestra
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
    private void loadSoggettiData(DefaultTableModel tableModel){
        for (Soggetto soggetto : soggetti){
            tableModel.addRow(new Object[]{soggetto.getCategoria()});
        }
    }


}
