package ImplPostgresDAO;

import DAO.LuogoDAO;
import DBConnection.ConnessioneDB;
import MODEL.Luogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public Luogo getLuogoFoto(int idfoto) throws SQLException {
        Luogo luogo = null;
        PreparedStatement stmt;
        stmt = connection.prepareStatement("SELECT l.idluogo, l.nome, l.latitudine, l.longitudine from luogo l INNER JOIN luogofoto lf on l.idluogo = lf.idluogo where lf.idfoto = ?");
        stmt.setInt(1, idfoto);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()){
            int idluogo = rs.getInt("idluogo");
            String nome = rs.getString("nome");
            float latitudine = rs.getFloat("latitudine");
            float longitudine = rs.getFloat("longitudine");

            luogo = new Luogo(idluogo, nome, latitudine, longitudine);
        }
        return luogo;
    }

    public ArrayList<Luogo> getTop3() throws SQLException {
        ArrayList<Luogo> top3places = new ArrayList<Luogo>();
        PreparedStatement stmt;
        stmt = connection.prepareStatement("SELECT idluogo, nome, latitudine, longitudine FROM top3luogipiùimmortalati");

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            int idluogo = rs.getInt("idluogo");
            String nome = rs.getString("nome");
            float latitudine = rs.getFloat("latitudine");
            float longitudine = rs.getFloat("longitudine");

            Luogo luogo = new Luogo(idluogo, nome, latitudine, longitudine);

            top3places.add(luogo);
        }
        return top3places;
    }
}
