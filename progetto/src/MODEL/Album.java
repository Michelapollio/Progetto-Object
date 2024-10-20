package MODEL;

import java.util.Date;

public class Album {

    private int IdAlbum;

    private String nome;

    private int userId;

    private boolean privacy;

    public Album(String nome, int userId, boolean privacy){
        //this.IdAlbum = IdAlbum;
        this.nome = nome;
        this.userId = userId;
        this.privacy = privacy;
    }

    public boolean isPrivate() {
        return privacy;
    }

    public int getIdAlbum() {
        return IdAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        IdAlbum = idAlbum;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public boolean getPrivacy(){
        return privacy;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
