package MODEL;

import java.util.Date;


public class Foto {

    private int idfoto;
    private Boolean stato;
    private Date dataScatto;
    private int idUser;
    private int IdDispositivo;
    private boolean visibilità;
    Utente utente;

    public Foto(int idfoto, int idUser, int idDispositivo, boolean visibilità){
        this.idfoto = idfoto;
        this.idUser = idUser;
        this.IdDispositivo = idDispositivo;
        this.visibilità = visibilità;
    }

    public int getIdfoto() {
        return idfoto;
    }

    public void setIdfoto(int idfoto) {
        this.idfoto = idfoto;
    }

    public void setDataScatto(Date dataScatto) {
        this.dataScatto = dataScatto;
    }

    public Date getDataScatto() {
        return dataScatto;
    }

    public Boolean getStato() {
        return stato;
    }

    public void setStato(Boolean stato) {
        this.stato = stato;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdDispositivo() {
        return IdDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        IdDispositivo = idDispositivo;
    }

    public boolean getVisibilità() {
        return visibilità;
    }
    public void setVisibilità(boolean visibilità) {
        this.visibilità = visibilità;
    }
}
