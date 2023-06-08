package modelodao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
  private Connection conexion;

  private String bd, usuario, clave, mensaje;

    public Conexion(Connection conexion, String bd, String usuario, String clave, String mensaje) {
        this.conexion = conexion;
        this.bd = bd;
        this.usuario = usuario;
        this.clave = clave;
        this.mensaje = mensaje;
    }

    public Conexion() {
        this.conexion = null;
        this.bd = "proyfinal";
        this.usuario = "postgres";
        this.clave = "";//agregar su contraseña en caso de tenerla
        this.mensaje = "";
    }

    public void conectar(){
      try {
          Class.forName("org.postgresql.Driver");
          String ruta="jdbc:postgresql://localhost:5432/"+bd;
          System.out.println(ruta);
          conexion= DriverManager.getConnection(ruta,usuario,clave);
          mensaje="Conexión exitosa...";
      } catch (ClassNotFoundException ex) {
          mensaje="No se pudo establecer conexion...";
      } catch (SQLException ex) {
           mensaje=" No se puede conectar con MySQL...";
      }
    }
   
    @Override
    public String toString() {
        return "Conexion{" + "conexion=" + conexion + ", bd=" + bd + ", usuario=" + usuario + ", clave=" + clave + ", mensaje=" + mensaje + '}';
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
   
}

