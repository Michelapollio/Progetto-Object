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
    public boolean deleteFoto(int idFoto) throws SQLException {

        PreparedStatement deleteFoto;
        deleteFoto = connection.prepareStatement("UPDATE foto SET visibilità = 'false' WHERE idfoto = ?");
        deleteFoto.setInt(1, idFoto);

        int result = deleteFoto.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    public ArrayList<Foto> getAlbumFoto(int idalbum) throws SQLException {
        ArrayList<Foto> fotolist = new ArrayList<>();

        PreparedStatement stmt;
        stmt = connection.prepareStatement("SELECT f.idfoto, f.idutente, f.iddispositivo, f.visibilità from foto f INNER JOIN fotoalbum fa on f.idfoto = fa.idfoto where fa.idalbum = ? and f.visibilità = 'true'");
        stmt.setInt(1, idalbum);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            int idfoto = rs.getInt("idfoto");
            int iduser = rs.getInt("idutente");
            int dispositivo = rs.getInt("iddispositivo");
            boolean visibilità = rs.getBoolean("visibilità");

            Foto foto1 = new Foto(idfoto, iduser, dispositivo, visibilità);

            fotolist.add(foto1);
        }
        return fotolist;
    }

    public ArrayList<Foto> getLuogoFoto(int idluogo) throws SQLException {
        ArrayList<Foto> fotolist = new ArrayList<>();

        PreparedStatement stmt;
        stmt = connection.prepareStatement("SELECT f.idfoto, f.idutente, f.iddispositivo, f.visibilità FROM foto f INNER JOIN luogofoto lf on f.idfoto = lf.idfoto WHERE lf.idluogo = ?");
        stmt.setInt(1, idluogo);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            int idfoto = rs.getInt("idfoto");
            int iduser = rs.getInt("idutente");
            int dispositivo = rs.getInt("iddispositivo");
            boolean visibilità = rs.getBoolean("visibilità");

            Foto foto1 = new Foto(idfoto, iduser, dispositivo, visibilità);

            fotolist.add(foto1);
        }
        return fotolist;
    }

    public ArrayList<Foto> getSoggFoto(int idsogg) throws SQLException {
        ArrayList<Foto> fotolist = new ArrayList<>();

        PreparedStatement stmt;
        stmt = connection.prepareStatement("SELECT f.idfoto, f.idutente, f.iddispositivo, f.visibilità FROM foto f INNER JOIN soggettofoto sf on f.idfoto = sf.idfoto WHERE sf.idsoggetto = ?");
        stmt.setInt(1, idsogg);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            int idfoto = rs.getInt("idfoto");
            int iduser = rs.getInt("idutente");
            int dispositivo = rs.getInt("iddispositivo");
            boolean visibilità = rs.getBoolean("visibilità");

            Foto foto1 = new Foto(idfoto, iduser, dispositivo, visibilità);

            fotolist.add(foto1);
        }
        return fotolist;
    }
}
