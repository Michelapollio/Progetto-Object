package ImplPostgresDAO;

import DAO.FotoDAO;

import java.sql.*;

import DBConnection.ConnessioneDB;

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


}
