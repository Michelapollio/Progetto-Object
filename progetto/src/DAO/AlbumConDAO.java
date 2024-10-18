package DAO;
import MODEL.AlbumCond;

import java.sql.SQLException;
import java.util.List;

public interface AlbumConDAO {

    boolean createalbumCondiviso(AlbumCond albumCondiviso) throws SQLException;

    boolean deleteAlbumCondiviso(int id) throws SQLException;

    boolean aggiungiPartecipante(int idAlbum, int idUtente) throws SQLException;

    public boolean rimuoviPartecipante(int idAlbum, int idUtente) throws SQLException;

}
