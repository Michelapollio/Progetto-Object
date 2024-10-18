package ImplPostgresDAO;
import DAO.GalleriaGeolocDAO;
import DBConnection.ConnessioneDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class GalleriaGeolocPostgresDAO implements GalleriaGeolocDAO{

    private Connection connction;

    public GalleriaGeolocPostgresDAO(){
        try{
            connction = ConnessioneDB.getInstance().connection;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    };

    @Override
    public void getListUtenti(ArrayList<Integer> idUtentilist, ArrayList<String> nomilist, ArrayList<String> cognomilist, ArrayList<String> emailList, ArrayList<String> passworsList) {
        try{
            PreparedStatement selectListaUtenti;
            selectListaUtenti = connction.prepareStatement("SELECT * FROM Utente ORDER BY idUtente");
            ResultSet rs = selectListaUtenti.executeQuery();
            while(rs.next()){
                int idUtente = rs.getInt("IdUtente");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String email = rs.getString("email");
                String password = rs.getString("password");

                idUtentilist.add(idUtente);
                nomilist.add(nome);
                cognomilist.add(cognome);
                emailList.add(email);
                passworsList.add(password);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getListFoto(ArrayList<Integer> idFotolist, ArrayList<Date> dataScattolist, ArrayList<Boolean> StatoList, ArrayList<Integer> idOwnersList, ArrayList<Integer> idDispositiviList) {
        try{
            PreparedStatement selectListaUtenti;
            selectListaUtenti = connction.prepareStatement("SELECT * FROM Foto ORDER BY idFoto");
            ResultSet rs = selectListaUtenti.executeQuery();
            while(rs.next()){
                int idFoto = rs.getInt("idFoto");
                Date dataScatto = rs.getDate("dataScatto");
                Boolean stato = rs.getBoolean("stato");
                int idUtente = rs.getInt("idUtente");
                int idDispositivo = rs.getInt("idDispositivo");

                idFotolist.add(idFoto);
                dataScattolist.add(dataScatto);
                StatoList.add(stato);
                idOwnersList.add(idUtente);
                idDispositiviList.add(idDispositivo);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getListLuoghi(ArrayList<Integer> idLuoghilist, ArrayList<String> nomiList, ArrayList<Float> latitudiniList, ArrayList<Float> longitudiniList) {
        try{
            PreparedStatement selectListaUtenti;
            selectListaUtenti = connction.prepareStatement("SELECT * FROM Luogo ORDER BY idLuogo");
            ResultSet rs = selectListaUtenti.executeQuery();
            while(rs.next()){
                int idLuogo = rs.getInt("idLuogo");
                String nome = rs.getString("nome");
                Float latitudine = rs.getFloat("latitudine");
                Float longitudine = rs.getFloat("longitudine");

                idLuoghilist.add(idLuogo);
                nomiList.add(nome);
                latitudiniList.add(latitudine);
                longitudiniList.add(longitudine);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getListSoggetti(ArrayList<Integer> idSoggettiList, ArrayList<String> categorieList) {
        try{
            PreparedStatement selectListaUtenti;
            selectListaUtenti = connction.prepareStatement("SELECT * FROM Soggetto ORDER BY idSogg");
            ResultSet rs = selectListaUtenti.executeQuery();
            while(rs.next()){
                int idSoggetto = rs.getInt("idSogg");
                String categoria = rs.getString("categoria");


                idSoggettiList.add(idSoggetto);
                categorieList.add(categoria);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getListDispositivi(ArrayList<Integer> idDispositiviList, ArrayList<Integer> idOwnersList) {
        try{
            PreparedStatement selectListaUtenti;
            selectListaUtenti = connction.prepareStatement("SELECT * FROM Dispositivo ORDER BY idDispositivo");
            ResultSet rs = selectListaUtenti.executeQuery();
            while(rs.next()){
                int idDispositivo = rs.getInt("idDispositivo");
                int idOwner = rs.getInt("idOwner");

                idDispositiviList.add(idDispositivo);
                idOwnersList.add(idOwner);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getListAlbum(ArrayList<Integer> idAlbumList, ArrayList<String> nomiAlbumList, ArrayList<Integer> idOwnersList, ArrayList<Boolean> privacyList) {
        try{
            PreparedStatement selectListaUtenti;
            selectListaUtenti = connction.prepareStatement("SELECT * FROM Album ORDER BY idAlbum");
            ResultSet rs = selectListaUtenti.executeQuery();
            while(rs.next()){
                int idAlbum = rs.getInt("idAlbum");
                String nome = rs.getString("nome");
                int idOwner = rs.getInt("idOwner");
                Boolean privacy = rs.getBoolean("privacy");

                idAlbumList.add(idAlbum);
                nomiAlbumList.add(nome);
                idOwnersList.add(idOwner);
                privacyList.add(privacy);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void aggiornaDatabaseDAO() throws SQLException {
        try{
            PreparedStatement aggData;
            connction.prepareStatement("SELECT update_database();");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
