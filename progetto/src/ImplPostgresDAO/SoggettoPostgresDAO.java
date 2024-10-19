package ImplPostgresDAO;

import DAO.SoggettoDAO;
import DBConnection.ConnessioneDB;
import MODEL.Soggetto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Soggetto> getSoggFoto(int idfoto)throws SQLException{
        ArrayList<Soggetto> soggettilist = new ArrayList<>();
        PreparedStatement stmt;
        stmt = connection.prepareStatement("SELECT s.idsoggetto, s.categoria from soggetto s INNER JOIN soggettofoto sf on s.idsoggetto = sf.idsoggetto where sf.idfoto = ?");
        stmt.setInt(1, idfoto);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            int idsogg = rs.getInt("idsoggetto");
            String categoria = rs.getString("categoria");

            Soggetto sogg = new Soggetto(idsogg, categoria);

            soggettilist.add(sogg);
        }
        return soggettilist;
    }


}
