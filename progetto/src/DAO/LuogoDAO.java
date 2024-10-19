package DAO;

import MODEL.Luogo;

import java.sql.SQLException;

public interface LuogoDAO {

    boolean createLuogo( String nome, float latitudine, float longitudine) throws SQLException;

    boolean deleteLuogo(int idLuogo) throws SQLException;

    public Luogo getLuogoFoto(int idfoto) throws SQLException;


}
