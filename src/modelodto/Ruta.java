package modelodto;
// Generated 12/05/2023 08:02:19 PM by Hibernate Tools 4.3.1



/**
 * Ruta generated by hbm2java
 */
public class Ruta  implements java.io.Serializable {


     private int idRuta;
     private String dirOrigen;
     private String dirDestino;

    public Ruta() {
    }

    public Ruta(String dirDestino) {
       this.dirDestino = dirDestino;
    }
   
    public int getIdRuta() {
        return this.idRuta;
    }
    
    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }
    public String getDirOrigen() {
        return this.dirOrigen;
    }
    
    public void setDirOrigen(String dirOrigen) {
        this.dirOrigen = dirOrigen;
    }
    public String getDirDestino() {
        return this.dirDestino;
    }
    
    public void setDirDestino(String dirDestino) {
        this.dirDestino = dirDestino;
    }




}


