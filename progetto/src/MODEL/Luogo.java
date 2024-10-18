package MODEL;

public class Luogo {

    private int IdLuogo;

    private String nome;

    private float latitudine;

    private float longitudine;

    public Luogo (int IdLuogo, String nome, float latitudine, float longitudine){
        this.IdLuogo = IdLuogo;
        this.nome = nome;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public int getIdLuogo() {
        return IdLuogo;
    }

    public void setIdLuogo(int idLuogo) {
        IdLuogo = idLuogo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setLatitudine(float latitudine) {
        this.latitudine = latitudine;
    }

    public float getLatitudine() {
        return latitudine;
    }

    public void setLongitudine(float longitudine) {
        this.longitudine = longitudine;
    }

    public float getLongitudine() {
        return longitudine;
    }
}
