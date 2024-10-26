package DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import MODEL.Foto;

public interface FotoDAO {

    public boolean deleteFoto(int idFoto) throws SQLException;
    public ArrayList<Foto> getAlbumFoto(int idalbum) throws SQLException;
    public ArrayList<Foto> getLuogoFoto(int idluogo) throws SQLException;
    public ArrayList<Foto> getSoggFoto(int idsogg) throws SQLException;
}

