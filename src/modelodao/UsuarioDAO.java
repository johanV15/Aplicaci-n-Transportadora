/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelodao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.swing.JOptionPane;
import modelodto.*;

/**
 *
 * @author julxo
 */
public class UsuarioDAO {
    Conexion con;
    public int validar(String user, String pass, String tipo){
        int val=0;
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos= consulta.executeQuery("select * from usuario"+
                                                    " where usuario='"+user+"' and pass='"+pass+"'"+
                                                    " and usuario in (select usuario from "+tipo+")");
            while (datos.next()) {                
                val=1;
            }
            datos.close();
            con.getConexion().close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en metodo validar de UsuarioDAO...\n"+e.getMessage());
        }
        return val;
    }
    public String traerusuario(String tipo, String user){
        String datouser="";
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos= consulta.executeQuery("select * from "+tipo+
                                                    " where usuario='"+user+"'");
            ResultSetMetaData campos=datos.getMetaData();
            while (datos.next()) {            
                for (int i = 0; i < campos.getColumnCount(); i++) {
                   datouser+=datos.getObject(i+1)+";";
                }
            }
            datos.close();
            con.getConexion().close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en metodo traerusuario de UsuarioDAO...\n"+e.getMessage());
        }
        return datouser;
    }
    public String insertar(Usuario u){
        String mensaje="";
        try {
            con=new Conexion();
            con.conectar();
            PreparedStatement consulta = null;
            String comando= "insert into usuario values(?,?)";
            consulta=con.getConexion().prepareStatement(comando);
            consulta.setString(1,u.getUsuario());
            consulta.setString(2,u.getPass());
            consulta.execute();
            mensaje="Registro exitoso...";
            consulta.close();
            con.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error en metodo insertar de UsuarioDAO...\n"+ex;
        }
        return mensaje;  
    }
}
/*
public String consultar(){
        String datosbd="";
        DefaultTableModel plantilla= new DefaultTableModel();
        Conexionbd con= new Conexionbd();
        try {
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos= consulta.executeQuery("select * from productos");
            ResultSetMetaData campos=datos.getMetaData();
            while(datos.next()){
                for (int i = 0; i < campos.getColumnCount(); i++) {
                   datosbd+=datos.getObject(i+1)+";";
                }
                datosbd+="\n";
            }
            datos.close();
            con.getConexion().close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return datosbd;
    }
    
    public String insertarprod(String aux){
        String mensaje="";
        try {
            Conexionbd conexion=new Conexionbd();
            PreparedStatement consulta = null;
            conexion.conectar();
            String comando= "insert into productos values(?,?,?,?,?)";
            consulta=conexion.getConexion().prepareStatement(comando);
            consulta.setString(1,addp.getPr().getCod());
            consulta.setString(2,addp.getPr().getNombre());
            consulta.setDouble(3,addp.getPr().getPrecio());
            consulta.setInt(4,addp.getPr().getCant());
            consulta.setString(5,aux);
            consulta.execute();
            mensaje="Registro exitoso...";
            consulta.close();
            conexion.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error al intentar insertar...\n"+ex;
        }
      return mensaje;  
    }
    
    public String actualizar(String aux){
        String mensaje="";
        try {
            Conexionbd conexion=new Conexionbd();
            PreparedStatement consulta = null;
            conexion.conectar();
            String comando=
            "update productos set nombre=?, precio=?, cantidad=?, fecha_vencimiento=?"+
            " where codigo= '"+addp.getPr().getCod()+"'";
            consulta=conexion.getConexion().prepareStatement(comando);
            consulta.setString(1,addp.getPr().getNombre());
            consulta.setDouble(2,addp.getPr().getPrecio());
            consulta.setInt(3,addp.getPr().getCant());
            consulta.setString(4,aux);
            consulta.execute();
            mensaje="Registro actualizado exitosamente...";
            consulta.close();
            conexion.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error al intentar actualizar...\n"+ex;
        }
      return mensaje;  
    }
    
    public String eliminar(){
        String mensaje=""; 
        try {
            Conexionbd conexion=new Conexionbd();
            PreparedStatement consulta = null;
            conexion.conectar();
            String comando=
            "delete from productos where codigo= '"+addp.getPr().getCod()+"'";
            consulta=conexion.getConexion().prepareStatement(comando);
            consulta.execute();
            mensaje="Registro eliminado exitosamente...";
            consulta.close();
            conexion.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error al intentar eliminar...\n"+ex;
        }
      return mensaje;  
    }

    public String insertarventa(){
      String mensaje=""; 
        try {
            Conexionbd conexion=new Conexionbd();
            PreparedStatement consulta = null;
            conexion.conectar();
            String comando= "insert into ventas (codigo,nombre,precio,cantidad,fecha_venta) values(?,?,?,?,?)";
            consulta=conexion.getConexion().prepareStatement(comando);
            consulta.setString(1,v.getListaventas().get(v.getListaventas().size()-1).getPr().getCod());
            consulta.setString(2,v.getListaventas().get(v.getListaventas().size()-1).getPr().getNombre());
            consulta.setDouble(3,v.getListaventas().get(v.getListaventas().size()-1).getPr().getPrecio());
            consulta.setInt(4,v.getListaventas().get(v.getListaventas().size()-1).getPr().getCant());
            consulta.setString(5,v.getFchventa().toString());
            consulta.execute();
            mensaje="Venta guardada...";
            consulta.close();
            conexion.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error al intentar insertar...\n"+ex;
        }
      return mensaje;    
    }

    public String insertargasto(gastos g){
        String mensaje=""; 
        try {
            Conexionbd conexion=new Conexionbd();
            PreparedStatement consulta = null;
            conexion.conectar();
            String comando= "insert into gastos (descripcion,valor) values(?,?)";
            consulta=conexion.getConexion().prepareStatement(comando);
            consulta.setString(1,g.getGasto());
            consulta.setDouble(2,g.getValor());
            consulta.execute();
            mensaje="Registro exitoso...";
            consulta.close();
            conexion.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error al intentar insertar...\n"+ex;
        }
        return mensaje; 
    }

    public String actualizarventa(agregarproducto prod){
        String mensaje="";
        try {
            Conexionbd conexion=new Conexionbd();
            PreparedStatement consulta = null;
            conexion.conectar();
            String comando=
            "update productos set cantidad=?"+
            " where codigo= '"+prod.getPr().getCod()+"'";
            consulta=conexion.getConexion().prepareStatement(comando);
            consulta.setInt(1,prod.getPr().getCant());
            consulta.execute();
            mensaje="Registro actualizado exitosamente...";
            consulta.close();
            conexion.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error al intentar actualizar...\n"+ex;
        }
      return mensaje;  
    } 



List<Object[]>lista=sql.list();
        for (Object[] obj : lista) {
            a=new Usuario();
            a.setUsuario(obj[0].toString());
            a.setPass(obj[1].toString());
        }

*/