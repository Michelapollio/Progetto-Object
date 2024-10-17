package ImplPostgresDAO;

import DAO.SoggettoDAO;
import DBConnection.ConnessioneDB;
import MODEL.Soggetto;

import java.sql.*;

public class SoggettoPostgresDAO implements SoggettoDAO {

    private Connection connection;

    public SoggettoPostgresDAO(){
        try{
            connection = ConnessioneDB.getInstance().connection;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean addNewSubject(Soggetto sogg) throws SQLException{
        PreparedStatement insert;
        insert = connection.prepareStatement("INSERT INTO Soggetto (categoria) VALUES (?)");
        insert.setString(1, sogg.getCategoria());
        int result = insert.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSubject(int idsogg) throws SQLException{

        PreparedStatement delete;
        delete = connection.prepareStatement("DELETE FROM Soggetto WHERE IdSogg == ?");
        delete.setInt(1, idsogg);
        int result = delete.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }


}
