package ImplPostgresDAO;

import DAO.AlbumConDAO;
import DBConnection.ConnessioneDB;
import MODEL.AlbumCond;
import MODEL.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumConPostgressDAO implements AlbumConDAO {

    Connection connessione;

    public AlbumConPostgressDAO(){
        try {
            connessione = ConnessioneDB.getInstance().connection;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean createalbumCondiviso(AlbumCond albumCondiviso) throws SQLException {
        PreparedStatement stmt;
        stmt = connessione.prepareStatement("INSERT INTO Album(nome, userId, privacy) VALUES (?, ?, ?)");
        stmt.setString(1, albumCondiviso.getNome());
        stmt.setInt(2, albumCondiviso.getUserId());
        stmt.setBoolean(3, albumCondiviso.getPrivacy());

        int result = stmt.executeUpdate();
        if (result == 1){
            return true;
        }
        return  false;

    }

    @Override
    public boolean deleteAlbumCondiviso(int idAlbum) throws SQLException{
        PreparedStatement stmt;
        stmt = connessione.prepareStatement("DELETE FROM Album WHERE idAlbum = ?");
        stmt.setInt(1, idAlbum);

        int result = stmt.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean aggiungiPartecipante(int idAlbum, int idUtente) throws SQLException{
        PreparedStatement stmt;
        stmt = connessione.prepareStatement("INSERT INTO AlbumUtente(idUtente, idAlbum) VALUES  (?, ?)");
        stmt.setInt(1, idUtente);
        stmt.setInt(2, idAlbum);

        int result = stmt.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean rimuoviPartecipante(int idAlbum, int idUtente) throws SQLException{
        PreparedStatement stmt;
        stmt = connessione.prepareStatement("DELETE FROM AlbumUtente WHERE idAlbum = ? AND idUtente = ?");
        stmt.setInt(1, idUtente);
        stmt.setInt(2, idAlbum);

        int result = stmt.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    public ArrayList<AlbumCond> getAllAlbumCond(int idutente) throws SQLException {
        ArrayList<AlbumCond> albumCondlist = new ArrayList<>();

        PreparedStatement stmt;
        stmt = connessione.prepareStatement("SELECT * FROM album a join albumutente ua on a.idalbum = ua.idalbum where ua.idutente = ? and a.privacy = 'true'");
        stmt.setInt(1, idutente);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            int idalbum = rs.getInt("idalbum");
            String nome = rs.getString("nome");
            int idowner = rs.getInt("idowner");
            boolean privacy = rs.getBoolean("privacy");

            AlbumCond albumCond = new AlbumCond(idalbum, nome, idowner, privacy);
            albumCondlist.add(albumCond);
        }
        return albumCondlist;

    }

    public boolean addAlbumC(int idalbum, int idutente) throws SQLException {

        PreparedStatement stmt;
        stmt = connessione.prepareStatement("INSERT INTO albumutente (idalbum , idutente) values (?, ?)");
        stmt.setInt(1, idalbum);
        stmt.setInt(2, idutente);

       int result = stmt.executeUpdate();

       if (result == 1){
           return true;
       }
        return false;
    }

    public boolean addNeWAC(String nomealbum, int idutente) throws SQLException {
        boolean privacy = true;
        PreparedStatement stmt;
        stmt = connessione.prepareStatement("INSERT INTO album (nome, idowner, privacy) values (?, ?, ?)");
        stmt.setString(1, nomealbum);
        stmt.setInt(2, idutente);
        stmt.setBoolean(3, privacy);

        int result = stmt.executeUpdate();

        if (result == 1){
            return true;
        }
        return false;
    }

    public ArrayList<Utente> getPartecipants(int idalbum) throws SQLException {
        ArrayList<Utente> partecipanti = new ArrayList<>();

        PreparedStatement stmt;
        stmt = connessione.prepareStatement("SELECT u.idutente, u.nome, u.cognome, u.email, u.password from utente u join albumutente au on u.idutente = au.idutente where au.idalbum = ?");
        stmt.setInt(1, idalbum);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            int idutente = rs.getInt("idutente");
            String nome = rs.getString("nome");
            String cognome = rs.getString("cognome");
            String email = rs.getString("email");
            String password = rs.getString("password");

            Utente utente = new Utente(nome, cognome, email, password);

            partecipanti.add(utente);


        }
        return partecipanti;


    }



}
