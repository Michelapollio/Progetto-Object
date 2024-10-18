package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AlbumDAO {
    boolean createAlbum(String titolo, int idOwner, boolean privacy) throws SQLException;

    boolean deleteAlbum(int id) throws SQLException;

    public boolean aggiungiFotoAlbum(int idAlbum, int idFoto) throws SQLException;

    public boolean rimuoviFotoAlbum(int idAlbum, int idFoto) throws SQLException;

    public ArrayList<String> getUserAlbums(int idUtente) throws SQLException;

}
