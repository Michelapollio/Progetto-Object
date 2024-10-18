package ImplPostgresDAO;

import DAO.FotoDAO;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

import DBConnection.ConnessioneDB;
import MODEL.Foto;

public class FotoPostgressDAO implements FotoDAO {

    private Connection connection;

    public FotoPostgressDAO(){
        try{
            connection = ConnessioneDB.getInstance().connection;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean aggiungiFotoDAO(Date dataScatto, Boolean stato, int idOwner, int idDispositivo) throws SQLException {
        PreparedStatement insertFoto;
        insertFoto = connection.prepareStatement("INSERT INTO Foto (idFoto, DataScatto, Stato, idOwner, idDispositivo) VALUES ( ?, ?, ?, ?)" );
        insertFoto.setDate(1, dataScatto);
        insertFoto.setBoolean(2, stato);
        insertFoto.setInt(3, idOwner);
        insertFoto.setInt(4, idDispositivo);

        int result = insertFoto.executeUpdate();

        if (result == 1){
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteFoto(int idFoto) throws SQLException {

        PreparedStatement deleteFoto;
        deleteFoto = connection.prepareStatement("DELETE FROM Foto WHERE idFoto = ?");
        deleteFoto.setInt(1, idFoto);

        int result = deleteFoto.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean aggiungiTag(int idUtente, int idFoto) throws SQLException{
        PreparedStatement stmt;
        stmt = connection.prepareStatement("INSERT INTO tag(idUtente, idFoto) VALUES  (?, ?)");
        stmt.setInt(1, idUtente);
        stmt.setInt(2, idFoto);

        int result = stmt.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean rimuoviTag(int idUtente, int idFoto) throws SQLException{
        PreparedStatement stmt;
        stmt = connection.prepareStatement("DELETE FROM Tag WHERE idFoto = ? AND idUtente = ?");
        stmt.setInt(1, idFoto);
        stmt.setInt(2, idUtente);

        int result = stmt.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean aggiungiSogettoFoto(int idSogg, int idFoto) throws SQLException{
        PreparedStatement stmt;
        stmt = connection.prepareStatement("INSERT INTO soggettofoto(idSogg, idFoto) VALUES  (?, ?)");
        stmt.setInt(1, idSogg);
        stmt.setInt(2, idFoto);

        int result = stmt.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminaSogettoFoto(int idSogg, int idFoto) throws SQLException{
        PreparedStatement stmt;
        stmt = connection.prepareStatement("DELETE FROM soggettofoto WHERE idSogg = ? AND idFoto = ?");
        stmt.setInt(1, idSogg);
        stmt.setInt(2, idFoto);

        int result = stmt.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean aggiungiLuogoFoto(int idLuogo, int idFoto) throws SQLException{
        PreparedStatement stmt;
        stmt = connection.prepareStatement("INSERT INTO soggettofoto(idLuogo, idFoto) VALUES  (?, ?)");
        stmt.setInt(1, idLuogo);
        stmt.setInt(2, idFoto);

        int result = stmt.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteLuogoFoto(int idLuogo, int idFoto) throws SQLException{
        PreparedStatement stmt;
        stmt = connection.prepareStatement("DELETE FROM soggettofoto WHERE idLuogo = ? AND idFoto = ?");
        stmt.setInt(1, idLuogo);
        stmt.setInt(2, idFoto);

        int result = stmt.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    public ArrayList<Foto> getAlbumFoto(int idalbum) throws SQLException {
        ArrayList<Foto> fotolist = new ArrayList<>();

        PreparedStatement stmt;
        stmt = connection.prepareStatement("SELECT f.idfoto, f.idutente, f.iddispositivo from foto f INNER JOIN fotoalbum fa on f.idfoto = fa.idfoto where fa.idalbum = ?");
        stmt.setInt(1, idalbum);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            int idfoto = rs.getInt("idfoto");
            int iduser = rs.getInt("idutente");
            int dispositivo = rs.getInt("iddispositivo");

            Foto foto1 = new Foto(idfoto, iduser, dispositivo);

            fotolist.add(foto1);
        }
        return fotolist;
    }


}
