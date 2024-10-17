package CONTROLLER;
import DAO.*;
import ImplPostgresDAO.AlbumPostgressDAO;
import ImplPostgresDAO.GalleriaGeolocPostgresDAO;
import ImplPostgresDAO.UtentePostgressDAO;
import MODEL.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Controller {

    private UtentePostgressDAO utenteDAO;
    private AlbumPostgressDAO albumDAO;

    private JPanel albumPanel;

    private List<Foto> listafoto = new ArrayList<>();
    private List<Album> listaalbum = new ArrayList<>();
    private List<AlbumCond> listaalbumcond = new ArrayList<>();
    private List<Luogo> listaluoghi = new ArrayList<>();

    public Controller(){
        this.utenteDAO = new UtentePostgressDAO();
        this.albumDAO = new AlbumPostgressDAO();

        dumpdati();
    }

    //load dati
    private void dumpdati(){
        aggiornaDatabase();

    }

    public void aggiornaDatabase(){
        GalleriaGeolocDAO galleria = new GalleriaGeolocPostgresDAO();
        try{
            galleria.aggiornaDatabaseDAO();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }




    //funzioni per la classe utente
    public boolean registerUser(String nome, String cognome, String email, String password) throws SQLException {
        if (nome.isEmpty()|| cognome.isEmpty()|| email.isEmpty() || password.isEmpty()){
            System.out.println("Errore: tutti i campi devono essere compilati");
            return false;
        }

        if (utenteDAO.isUtentePresent(email, password)){
            System.out.println("Errore: l'email è già registrata");
            return false;
        }

        Utente nuovoUtente = new Utente(nome, cognome, email, password);
        boolean flag = utenteDAO.aggiungiutenteDAO(nome, cognome, email, password);

        if (flag){
            System.out.println("Registrazione avvenuta con successo");
            return true;
        } else{
            System.out.println("Errore durante la registrazione.");
            return false;
        }
    }

    public ArrayList<String> getAlbums (int idUtente) throws SQLException {
        System.out.println("okkkk");
        //ArrayList<Album> albums = albumDAO.getUserAlbums(idutente);

        return albumDAO.getUserAlbums(idUtente);
    }

    public boolean addNewAlbum(String nome, int idowner, boolean privacy) throws SQLException {
        if (nome.isEmpty()){
            System.out.println("non è stato inserito il nome dell'album");
            return false;
        }

        boolean flag = albumDAO.createAlbum(nome, idowner, privacy);
        return flag;
    }

}
