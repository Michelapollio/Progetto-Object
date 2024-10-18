package ImplPostgresDAO;

import DAO.UtenteDAO;
import DBConnection.ConnessioneDB;
import MODEL.Utente;

import java.sql.*;

public class UtentePostgressDAO implements UtenteDAO {

    private Connection connection;

    public UtentePostgressDAO(){
        try{
            connection = ConnessioneDB.getInstance().connection;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean aggiungiutenteDAO(String nome, String cognome, String email, String password) throws SQLException {

        PreparedStatement insertUser;
        insertUser = connection.prepareStatement("INSERT INTO Utente( Nome, Cognome, Email, Password) VALUES (?, ?, ?, ?)");
        insertUser.setString(1, nome);
        insertUser.setString(2, cognome);
        insertUser.setString(3, email);
        insertUser.setString(4, password);

        int result = insertUser.executeUpdate();

        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminaUtenteDAO(int idUtente) throws SQLException {
        PreparedStatement deleteUser;
        deleteUser = connection.prepareStatement("DELETE FROM Utente WHERE IdUtente = ?");
        deleteUser.setInt(1, idUtente);

        int result = deleteUser.executeUpdate();
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean modificaUtente(String nome, String cognome, String email, String password) throws SQLException {

        PreparedStatement insertUsert;
        insertUsert = connection.prepareStatement("UPDATE Utente SET  nome = ?, cognome = ?, email = ?, password = ?");
        insertUsert.setString(1, nome);
        insertUsert.setString(2, cognome);
        insertUsert.setString(3, email);
        insertUsert.setString(4, password);

        int rs = insertUsert.executeUpdate();
        if(rs>0){
            return true;
        }
        return false;
    }


    //aggiungere funzione che controlla v
    public boolean isUtentePresent(String email, String password) throws SQLException{
        boolean isPresent = false;

        try{
            PreparedStatement stmt = connection.prepareStatement("SELECT COUNT (*) FROM Utente WHERE email = ? and password = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                isPresent = rs.getInt(1)>0;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return isPresent;
    }

    public Utente findInfoUtente (String email, String password) throws SQLException {
        Utente user = null;
        String nome;
        String cognome;
        int idutente;


        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT idutente, nome, cognome, email, password FROM Utente WHERE email = ? and password = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idutente = rs.getInt("idutente");
                nome = rs.getString("nome");
                cognome = rs.getString("cognome");

                user = new Utente(idutente, nome, cognome, email, password);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }


}
