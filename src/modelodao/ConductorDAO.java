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
import javax.swing.table.DefaultTableModel;
import modelodto.*;

/**
 *
 * @author julxo
 */
public class ConductorDAO {
    Conexion con;
    public String insertar(Conductor c){
    String mensaje="";
        try {
            con=new Conexion();
            PreparedStatement consulta = null;
            con.conectar();
            String comando= "insert into conductor values(?,?,?,?,?,?,?,?)";
            consulta=con.getConexion().prepareStatement(comando);
            consulta.setLong(1,c.getIdCond());
            consulta.setString(2,c.getNombre());
            consulta.setString(3,c.getApellido());
            consulta.setString(4,c.getDireccion());
            consulta.setString(5,c.getGenero());
            consulta.setString(6,c.getNacionalidad());
            consulta.setInt(7, c.getFoto());
            consulta.setString(8,c.getUsuario());
            consulta.execute();
            mensaje="Registro exitoso...";
            consulta.close();
            con.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error en metodo insertar de ConductorDAO...\n"+ex.getMessage();
        }
        return mensaje;  
    }
    public DefaultTableModel consultar(){
        DefaultTableModel tabla= new DefaultTableModel();
        String datosbd="";
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos=consulta.executeQuery("select * from conductor");
            ResultSetMetaData campos=datos.getMetaData();
            for (int i = 0; i < campos.getColumnCount(); i++) {
                tabla.addColumn(campos.getColumnName(i+1));
            }
            while(datos.next()){
             Object fila[]=new Object[campos.getColumnCount()];
                for (int i = 0; i < campos.getColumnCount(); i++) {
                   fila[i]=datos.getObject(i+1);
                }
                tabla.addRow(fila);
            }
            datos.close();
            con.getConexion().close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en metodo consultar de ConductorDAO...\n"+ex.getMessage());
        }
        return tabla;
    }
    public String consultarid(){
        String datosbd="";
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos= consulta.executeQuery("select id_cond from conductor");
            while(datos.next()){
                   datosbd+=datos.getObject(1);
                   
            }
            datos.close();
            con.getConexion().close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return datosbd;
    }
    public int foto(String ruta){
        int datosbd=0;
        try {
            con= new Conexion();
            con.conectar();
            Statement cons= con.getConexion().createStatement();
            ResultSet datos= cons.executeQuery("select lo_import('"+ruta+"')");
            ResultSetMetaData campos=datos.getMetaData();
            if(datos.next()){
                datosbd=Integer.parseInt(datos.getObject(1).toString());
            }
            datos.close();
            con.getConexion().close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al intentar insertar foto...\n"+ex.getMessage());
        }
        return datosbd;
    }
    public int traerfoto(long cond){
        int msg=0;
        try {
            String ruta="C:/Users/julxo/OneDrive/Documents/NetBeansProjects/ProyectoBDA/src/img/cond.png";
            con= new Conexion();
            con.conectar();
            Statement cons= con.getConexion().createStatement();
            cons.executeQuery("select lo_export((select foto from conductor where id_cond="+cond+"),'"+ruta+"')");
            con.getConexion().close();
            msg=1;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al intentar exportar foto de usuario...\n"+ex.getMessage());
        }
        return msg;
    }
    public String actualizar(Conductor c){
    String mensaje="";
        try {
            Conexion con=new Conexion();
            PreparedStatement consulta = null;
            con.conectar();
            String comando=
            "update conductor set nombre=?, apellido=?, direccion=?, genero='"+c.getGenero()+"', nacionalidad=?"+
            " where id_cond="+c.getIdCond();
            consulta=con.getConexion().prepareStatement(comando);
            consulta.setString(1,c.getNombre());
            consulta.setString(2,c.getApellido());
            consulta.setString(3,c.getDireccion());
            consulta.setString(4,c.getNacionalidad());
            consulta.execute();
            mensaje="Registro actualizado exitosamente...";
            consulta.close();
            con.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error al intentar actualizar datos del conductor...\n"+ex.getMessage();
        }
      return mensaje;  
    }
    
    public String eliminar(String user){
        String mensaje=""; 
        try {
            Conexion con=new Conexion();
            PreparedStatement consulta = null;
            con.conectar();
            String comando=
            "delete from usuario where usuario='"+user+"'";
            consulta=con.getConexion().prepareStatement(comando);
            consulta.execute();
            mensaje="Registro eliminado exitosamente...";
            consulta.close();
            con.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error al intentar eliminar el conductor...\n"+ex.getMessage();
        }
      return mensaje;  
    }
}
