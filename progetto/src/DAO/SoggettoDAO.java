package DAO;

import MODEL.Soggetto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SoggettoDAO {

    boolean addNewSubject(Soggetto sogg) throws SQLException;

    boolean deleteSubject(int idsogg) throws SQLException;

    public ArrayList<Soggetto> getSoggFoto(int idfoto)throws SQLException;

}
