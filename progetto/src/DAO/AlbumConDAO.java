package DAO;
import MODEL.AlbumCond;
import MODEL.Utente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface AlbumConDAO {

    boolean createalbumCondiviso(AlbumCond albumCondiviso) throws SQLException;

    boolean deleteAlbumCondiviso(int id) throws SQLException;

    boolean aggiungiPartecipante(int idAlbum, int idUtente) throws SQLException;

    public boolean rimuoviPartecipante(int idAlbum, int idUtente) throws SQLException;

    public ArrayList<AlbumCond> getAllAlbumCond(int idutente) throws SQLException;
    public boolean addAlbumC(int idalbum, int idutente) throws SQLException;
    public ArrayList<Utente> getPartecipants(int idalbum) throws SQLException;

}
