package ImplPostgresDAO;

import DAO.LuogoDAO;
import DBConnection.ConnessioneDB;
import MODEL.Luogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LuogoPostgressDAO implements LuogoDAO {

    private Connection connection;
    public LuogoPostgressDAO(){
        try {
            connection = ConnessioneDB.getInstance().connection;
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean createLuogo(String nome, float latitudine, float longitudine) throws SQLException {
        PreparedStatement newLuogo;
        newLuogo = connection.prepareStatement("INSERT INTO luogo(nome, latitudine, longitudine) VALUES (?,?,?)");
        newLuogo.setString(1, nome);
        newLuogo.setFloat(2, latitudine);
        newLuogo.setFloat(3, longitudine);

        int result = newLuogo.executeUpdate();
        if (result == 1){
            return true;
        }

        return false;
    }


    @Override
    public boolean deleteLuogo(int idLuogo) throws SQLException {

        PreparedStatement deleteLuogo;
        deleteLuogo = connection.prepareStatement("DELETE FROM luogo WHERE idluogo = ?");
        deleteLuogo.setInt(1, idLuogo);
        int result = deleteLuogo.executeUpdate();

        if (result == 1){
            return true;
        }
        return false;
    }
}
