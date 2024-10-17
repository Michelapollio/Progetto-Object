package MODEL;

public class Utente {

    private int IdUtente;

    private String nome;
    private String cognome;
    private String password;
    private String email;


    public Utente(String nome, String cognome, String email, String password){
        //this.IdUtente = Id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public Utente(int idUtente, String nome, String cognome, String email, String password){
        this.IdUtente = idUtente;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }



    public int getIdUtente(){
        return IdUtente;
    }

    public void setIdUtente(int idUtente) {
        IdUtente = idUtente;
    }

    public String getNome(){
        return nome;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCognome() {
        return cognome;
    }
}


