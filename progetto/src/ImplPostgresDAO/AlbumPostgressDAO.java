package ImplPostgresDAO;

import DAO.AlbumDAO;
import DBConnection.ConnessioneDB;
import MODEL.Album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlbumPostgressDAO implements AlbumDAO {

    Connection connection;


    public AlbumPostgressDAO(){
        try{
            connection = ConnessioneDB.getInstance().connection;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean createAlbum(String titolo, int idOwner, boolean privacy) throws SQLException{
        PreparedStatement stmt;
        stmt = connection.prepareStatement("INSERT INTO Album(nome, idowner, privacy) VALUES (?, ?, ?)");
        stmt.setString(1, titolo);
        stmt.setInt(2, idOwner);
        stmt.setBoolean(3, privacy);

        int result = stmt.executeUpdate();
        if (result == 1){
            return true;
        }
        return  false;
    }

    @Override
    public boolean deleteAlbum(int idAlbum) throws SQLException{

        PreparedStatement stmt;
        stmt = connection.prepareStatement("DELETE FROM Album WHERE idAlbum = ?");
        stmt.setInt(1, idAlbum);

        int result = stmt.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }


    @Override
    public boolean aggiungiFotoAlbum(int idAlbum, int idFoto) throws SQLException{
        PreparedStatement stmt;
        stmt = connection.prepareStatement("INSERT INTO fotoalbum(idUtente, idFoto) VALUES  (?, ?)");
        stmt.setInt(1, idAlbum);
        stmt.setInt(2, idFoto);

        int result = stmt.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean rimuoviFotoAlbum(int idAlbum, int idFoto) throws SQLException {

        PreparedStatement stmt;
        stmt = connection.prepareStatement("DELETE FROM fotoalbum WHERE idAlbum = ? AND idFoto = ?");
        stmt.setInt(1, idAlbum);
        stmt.setInt(2, idFoto);

        int result = stmt.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    public ArrayList<String> getUserAlbums(int idUtente) throws SQLException {

        ArrayList<String> albums = new ArrayList<>();

        PreparedStatement stmt;
        stmt = connection.prepareStatement("SELECT nome FROM Album WHERE idowner = ?");
        stmt.setInt(1, idUtente);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            System.out.println("album creato");
            //int id = rs.getInt("idalbum");
            String nome = rs.getString("nome");
            //int idutente = rs.getInt("idowner");
            //boolean privacy = rs.getBoolean("privacy");


            //Album albumCorr = new Album(id, nome, idutente, privacy);
            System.out.println("album creato");
            albums.add(nome);
            System.out.println("aggiunto album");
        }
        return albums;
    }

    public int getIdAlbum(String nome, int idowner) throws SQLException {

        int idalbum = -1;
        PreparedStatement stmt;
        stmt = connection.prepareStatement("SELECT idalbum FROM album WHERE idowner = ? and nome = ?");
        stmt.setInt(1, idowner);
        stmt.setString(2, nome);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()){
            idalbum = rs.getInt("idalbum");
        }


        return idalbum;

    }

}
