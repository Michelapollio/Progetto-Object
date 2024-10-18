package ImplPostgresDAO;

import DAO.AlbumConDAO;
import DBConnection.ConnessioneDB;
import MODEL.AlbumCond;

import java.sql.Connection;
import java.sql.PreparedStatement;
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



}
