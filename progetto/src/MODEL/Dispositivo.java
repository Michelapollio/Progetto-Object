package MODEL;

import java.util.ArrayList;
import java.util.List;

public class Dispositivo {
    private int IdDispositivo;
    private int owner;


    public Dispositivo(int IdDispositivo, int idOwner){
        this.IdDispositivo = IdDispositivo;
        this.owner = idOwner;
    }

    public int getIdDispositivo() {
        return IdDispositivo;
    }

    public int getOwner(){
        return owner;
    }

    public void setIdDispositivo(int idDispositivo) {
        IdDispositivo = idDispositivo;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }


}
