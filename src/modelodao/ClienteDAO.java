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
import modelodto.Cliente;

/**
 *
 * @author julxo
 */
public class ClienteDAO {
    Conexion con;
    
    public String insertar(Cliente c){
    String mensaje="";
        try {
            con=new Conexion();
            PreparedStatement consulta = null;
            con.conectar();
            String comando= "insert into cliente values(?,?,?,?,?,?,?)";
            consulta=con.getConexion().prepareStatement(comando);
            consulta.setLong(1,c.getIdCliente());
            consulta.setString(2,c.getNombre());
            consulta.setString(3,c.getApellido());
            consulta.setString(4,c.getDireccion());
            consulta.setString(5,c.getGenero());
            consulta.setString(6,c.getNacionalidad());
            consulta.setString(7,c.getUsuario());
            consulta.execute();
            mensaje="Registro exitoso...";
            consulta.close();
            con.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error en metodo insertar de ClienteDAO...\n"+ex.getMessage();
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
            ResultSet datos=consulta.executeQuery("select * from cliente");
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
            JOptionPane.showMessageDialog(null, "Error en metodo consultar de ClienteDAO...\n"+ex.getMessage());
        }
        return tabla;
    }
    public String actualizar(Cliente c){
    String mensaje="";
        try {
            Conexion con=new Conexion();
            PreparedStatement consulta = null;
            con.conectar();
            String comando=
            "update cliente set nombre=?, apellido=?, direccion=?, genero='"+c.getGenero()+"', nacionalidad=?"+
            " where id_cliente="+c.getIdCliente();
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
           mensaje="Error al intentar actualizar datos del cliente...\n"+ex.getMessage();
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
           mensaje="Error al intentar eliminar el cliente...\n"+ex.getMessage();
        }
      return mensaje;  
    }
}
