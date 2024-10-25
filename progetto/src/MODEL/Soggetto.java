package MODEL;

import java.util.ArrayList;
import java.util.List;

public class Soggetto {
    private int IdSogg;
    private String categoria;

    private List<Soggetto> listasogetti = new ArrayList<>();

    public Soggetto(int IdSogg, String categoria){
        this.IdSogg = IdSogg;
        this.categoria=categoria;
    }

    public void aggiungiSogetto(Soggetto sogg){
        this.listasogetti.add(sogg);
    }

    public int getIdSogg(){
        return IdSogg;
    }

    public String getCategoria(){
        return categoria;
    }

    public List<Soggetto> getListasogetti() {
        return listasogetti;
    }

    public void setIdSogg(int idSogg) {
        IdSogg = idSogg;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


}
