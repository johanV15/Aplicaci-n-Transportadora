package modelodto;
// Generated 12/05/2023 08:02:19 PM by Hibernate Tools 4.3.1


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Conductor generated by hbm2java
 */
public class Conductor  implements java.io.Serializable {


     private long idCond;
     private String usuario;
     private String nombre;
     private String apellido;
     private String direccion;
     private String genero;
     private String nacionalidad;
     private int foto;

    public Conductor() {
    }
	
    public Conductor(long idCond, String usuario, String nombre, String apellido, String direccion, String genero, String nacionalidad) {
        this.idCond = idCond;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.genero = genero;
        this.nacionalidad = nacionalidad;
    }
    public Conductor(long idCond, String usuario, String nombre, String apellido, String direccion, String genero, String nacionalidad, int foto) {
        this.idCond = idCond;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.genero = genero;
        this.nacionalidad = nacionalidad;
        this.foto= foto;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
   
    public long getIdCond() {
        return this.idCond;
    }
    
    public void setIdCond(long idCond) {
        this.idCond = idCond;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return this.apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getGenero() {
        return this.genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getNacionalidad() {
        return this.nacionalidad;
    }
    
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    

    public String getUsuario() {
        return usuario;
    }
    
    @Override
    public String toString() {
        return "Conductor{" + "idCond=" + idCond + ", usuario=" + usuario + ", nombre=" + nombre + ", apellido=" + apellido + ", direccion=" + direccion + ", genero=" + genero + ", nacionalidad=" + nacionalidad + ", foto=" + foto + '}';
    }
    

}


