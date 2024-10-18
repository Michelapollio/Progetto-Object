package DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import MODEL.Foto;

public interface FotoDAO {
    public boolean aggiungiFotoDAO(Date dataScatto, Boolean stato, int idOwner, int idDispositivo) throws SQLException;

    public boolean deleteFoto(int idFoto) throws SQLException;


    boolean aggiungiTag(int idUtente, int idFoto) throws SQLException;

    boolean rimuoviTag(int idUtente, int idFoto) throws SQLException;

    boolean aggiungiSogettoFoto(int idSogg, int idFoto) throws SQLException;

    boolean eliminaSogettoFoto(int idSogg, int idFoto) throws SQLException;

    boolean aggiungiLuogoFoto(int idLuogo, int idFoto) throws SQLException;

    boolean deleteLuogoFoto(int idLuogo, int idFoto) throws SQLException;

    public ArrayList<Foto> getAlbumFoto(int idalbum) throws SQLException;
}

