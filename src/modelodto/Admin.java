package modelodto;
// Generated 12/05/2023 08:02:19 PM by Hibernate Tools 4.3.1



/**
 * Admin generated by hbm2java
 */
public class Admin  implements java.io.Serializable {


     private long idAdmin;
     private Usuario usuario;
     private String nombre;

    public Admin() {
    }

    public Admin(long idAdmin, Usuario usuario, String nombre) {
       this.idAdmin = idAdmin;
       this.usuario = usuario;
       this.nombre = nombre;
    }
   
    public long getIdAdmin() {
        return this.idAdmin;
    }
    
    public void setIdAdmin(long idAdmin) {
        this.idAdmin = idAdmin;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }




}


