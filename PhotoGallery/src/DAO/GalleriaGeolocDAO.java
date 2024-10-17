package DAO;


import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface GalleriaGeolocDAO {

    // funzioni per il caricamento dei dati

    public void getListUtenti(ArrayList<Integer> idUtentilist,  ArrayList<String> nomilist, ArrayList<String> cognomilist, ArrayList<String> emailList, ArrayList<String> passworsList);

    public void getListFoto(ArrayList<Integer> idFotolist, ArrayList<Date> dataScattolist, ArrayList<Boolean> StatoList, ArrayList<Integer> idOwnersList, ArrayList<Integer> idDispositiviList);

    public void getListLuoghi(ArrayList<Integer> idLuoghilist, ArrayList<String> nomiList, ArrayList<Float> latitudiniList, ArrayList<Float> longitudiniList);
    public void getListSoggetti(ArrayList<Integer> idSoggettiList, ArrayList<String> categorieList);
    public void getListDispositivi(ArrayList<Integer> idDispositiviList, ArrayList<Integer> idOwnersList);
    public void getListAlbum(ArrayList<Integer> idAlbumList, ArrayList<String> nomiAlbumList, ArrayList<Integer> idOwnersList, ArrayList<Boolean> privacyList);
    public void aggiornaDatabaseDAO() throws SQLException;




}
