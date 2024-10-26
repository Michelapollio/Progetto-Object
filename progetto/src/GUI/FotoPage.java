package GUI;

import CONTROLLER.Controller;
import MODEL.Foto;
import MODEL.Luogo;
import MODEL.Soggetto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class FotoPage extends JFrame {
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
            //JLabel luogolabel = new JLabel();
            infoPanel.add(idlabel);
            //infoPanel.add(luogolabel);
        }
        else {
            JLabel luogolabel = new JLabel(luogo.getNome());
            luogolabel.setFont(new Font("Arial nova", Font.BOLD, 16));

            luogolabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        new LuogoPage(luogo);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            });
            infoPanel.add(idlabel);
            infoPanel.add(luogolabel);


        }


        fotoPanel.add(coloredSquare, BorderLayout.WEST);
        fotoPanel.add(infoPanel, BorderLayout.CENTER);
        fotoPanel.add(spacepanel, BorderLayout.AFTER_LINE_ENDS);

        add(fotoPanel, BorderLayout.NORTH);

        //tabella soggetti
        String[] colum = {"anteprima", "idsogg", "descrizione"};
        DefaultTableModel tableModel = new DefaultTableModel(colum, 0);
        JTable soggettiTable = new JTable(tableModel);

        soggettiTable.getColumnModel().getColumn(0).setCellRenderer(new ColorRenderer());
        soggettiTable.setBackground(Color.WHITE);
        soggettiTable.setFont(new Font("Arial nova", Font.PLAIN, 16));
        soggettiTable.getColumnModel().getColumn(0).setPreferredWidth(1);
        soggettiTable.getColumnModel().getColumn(1).setPreferredWidth(400);
        soggettiTable.getColumnModel().getColumn(2).setPreferredWidth(400);

        loadSoggettiData(tableModel);

        soggettiTable.addMouseListener(new java.awt.event.MouseAdapter(){

            public void mouseClicked(java.awt.event.MouseEvent evt){
                int selectedRow = soggettiTable.getSelectedRow();
                System.out.println(selectedRow);

                if (selectedRow != -1){
                    int idsogg = (int) soggettiTable.getValueAt(selectedRow, 1);
                    System.out.println(idsogg);
                    try {
                        new SoggPage(idsogg);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    //JOptionPane.showMessageDialog(null, "apertura foto", "foto", JOptionPane.INFORMATION_MESSAGE);
                }

                else {
                    JOptionPane.showMessageDialog(null, "nessun soggetto selezionato","errore", JOptionPane.ERROR_MESSAGE);
                }

                //}

            }
        });


        JScrollPane scrollPane = new JScrollPane(soggettiTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton deletefotoButton = createStyleButton("delete");

        JButton logoutButton = createStyleButton("close");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        add(logoutButton, BorderLayout.SOUTH);

        //impostazioni finestra
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
    private void loadSoggettiData(DefaultTableModel tableModel){
        for (Soggetto soggetto : soggetti){
            Color colorPreview = new Color((int)(Math.random() *0x1000000));
            tableModel.addRow(new Object[]{colorPreview, soggetto.getIdSogg(), soggetto.getCategoria()});
        }
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


}
