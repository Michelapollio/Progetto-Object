// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import DAO.AlbumDAO;
import GUI.MainApp;
import ImplPostgresDAO.AlbumPostgressDAO;
import ImplPostgresDAO.UtentePostgressDAO;
import MODEL.Album;
import MODEL.Utente;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    AlbumPostgressDAO album = new AlbumPostgressDAO();
    static UtentePostgressDAO utente = new UtentePostgressDAO();
    public static void main(String[] args) throws SQLException {
        
        new MainApp();



    }


}