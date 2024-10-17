package DAO;

import MODEL.Utente;

import java.sql.SQLException;

public interface UtenteDAO {

    public boolean aggiungiutenteDAO( String nome, String cognome, String email, String password) throws SQLException;
    public boolean eliminaUtenteDAO(int idUtente)throws SQLException;
    public boolean modificaUtente( String nome, String cognome, String email, String password) throws SQLException;

    public Utente findInfoUtente (String email, String password) throws SQLException;


}

