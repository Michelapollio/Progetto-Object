package ImplPostgresDAO;

import DAO.DispositivoDAO;
import DBConnection.ConnessioneDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DispositivoPostgressDAO implements DispositivoDAO {

    Connection connection;
    public DispositivoPostgressDAO(){
        try {
            connection = ConnessioneDB.getInstance().connection;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean createDispositivo( String tipologia) throws SQLException {

        PreparedStatement stmt;
        stmt = connection.prepareStatement("INSERT INTO Dispositivo(idDispositivo, tipologia) VALUES  (?)");
        stmt.setString(1, tipologia);

        int rs = stmt.executeUpdate();
        if (rs == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteDispositivo(int idDispositivo) throws SQLException {

        PreparedStatement stmt;
        stmt = connection.prepareStatement("DELETE FROM Dispositivo WHERE IdDispositvo = ?");
        stmt.setInt(1, idDispositivo);

        int rs = stmt.executeUpdate();
        if (rs == 1){
            return true;
        }
        return false;
    }
}
