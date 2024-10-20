package MODEL;

import java.util.List;

public class AlbumCond {
    private int idalbumcon;
    private String nome;
    private int userId;
    private boolean privacy;


    public AlbumCond(int idalbumcon, String nome, int userId, boolean privacy) {
        this.idalbumcon = idalbumcon;
        this.nome = nome;
        this.userId = userId;
        this.privacy = privacy;

    }

    public boolean getPrivacy(){
        return privacy;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public int getIdalbumcon() {
        return idalbumcon;
    }

    public void setIdalbumcon(int idalbumcon) {
        this.idalbumcon = idalbumcon;
    }
}
