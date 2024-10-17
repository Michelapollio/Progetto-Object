package DAO;

import java.sql.SQLException;

public interface DispositivoDAO {

    boolean createDispositivo( String tipologia) throws SQLException;

    boolean deleteDispositivo(int idDispositivo) throws SQLException;


}
