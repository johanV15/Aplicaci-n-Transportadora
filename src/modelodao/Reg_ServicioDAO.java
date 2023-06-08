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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelodto.*;

/**
 *
 * @author julxo
 */
public class Reg_ServicioDAO {
        Conexion con;
        
    public DefaultTableModel consultar(String usuario, long id){
        DefaultTableModel tabla= new DefaultTableModel();
        String datosbd="";
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos;
            if(usuario=="admin"){
                datos= consulta.executeQuery("select * from servs_admin_vw");
            }else if(usuario=="cliente"){
                datos= consulta.executeQuery("select * from servs_cliente_vw where identificacion="+id);
            }else{
                
                datos= consulta.executeQuery("select * from servs_cond_vw where identificacion="+id);
            }
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
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return tabla;
    }
    public String consultardisp(int serv){
        String datosbd="";
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            /*ResultSet datos=consulta.executeQuery("select c.id_cond from conductor c\n" +
                                                "inner join vehiculo v using (id_cond)\n" +
                                                "where (c.id_cond not in (select id_cond from reg_servicio)\n" +
                                                "and (v.id_serv="+serv+" or v.id_serv=3))\n" +
                                                "or (c.id_cond not in (select id_cond from reg_servicio\n" +
                                                                        "where estado = 'En camino')\n" +
                                                "and (id_serv="+serv+" or id_serv=3))\n"+
                                                "order by random() limit 1");*/
            ResultSet datos=consulta.executeQuery("select cond_disponible("+serv+")");
            while (datos.next()) {            
                   datosbd+=datos.getObject(1);
            }
            datos.close();
            con.getConexion().close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en metodo consultardisp de Reg_ServicioDAO...\n"+e.getMessage());
        }
        return datosbd;
    }
    public DefaultTableModel consultar_ultserv(String usuario, long id){
        DefaultTableModel tabla= new DefaultTableModel();
        String datosbd="";
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos;
            if(usuario=="cliente"){
                datos= consulta.executeQuery("select * from servs_cliente_vw\n"+
                                             "where identificacion="+id+" order by nroregistro desc");
            }else{
                datos= consulta.executeQuery("select * from servs_cond_vw\n"+
                                             "where identificacion="+id+" order by nroregistro desc");
            }
            ResultSetMetaData campos=datos.getMetaData();
            for (int i = 0; i < campos.getColumnCount(); i++) {
                tabla.addColumn(campos.getColumnName(i+1));
            }
            //while (datos.next()) {
            if (datos.next()) {
                
                Object fila[]=new Object[campos.getColumnCount()];
                for (int i = 0; i < campos.getColumnCount(); i++) {
                   fila[i]=datos.getObject(i+1);
                }
                tabla.addRow(fila);
            }
            datos.close();
            con.getConexion().close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return tabla;
    }
    public String actualizar_estado(long id){
        String mensaje="";
        try {
            con=new Conexion();
            PreparedStatement consulta = null;
            con.conectar();
            String comando="update reg_servicio set estado='Terminado'\n"+
                           "where id_cond="+id;
            consulta=con.getConexion().prepareStatement(comando);
            consulta.execute();
            mensaje="Registro actualizado exitosamente...";
            consulta.close();
            con.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error al intentar actualizar...\n"+ex;
        }
      return mensaje;  
    }
    public int consultar_valoracion(int reg){
        int datosbd=0;
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos=consulta.executeQuery("select * from reg_servicio\n" +
                                                "where id_reg="+reg+"\n"+
                                                "and estado='Terminado'\n" +
                                                "and valoracion is null");
            if (datos.next()) {            
                   datosbd=1;
            }
            datos.close();
            con.getConexion().close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en metodo consultar_valoracionp de Reg_ServicioDAO...\n"+e.getMessage());
        }
        return datosbd;
    }
    public String insertar(RegServicio rs, double[] val){
        String mensaje="";
        try {
            con=new Conexion();
            PreparedStatement consulta = null;
            con.conectar();
            String comando= "insert into reg_servicio ("
                    + "id_cond, id_cliente, id_serv, val_servicio, medio_pago, fecha, id_cat, id_ruta, estado)"
                    + "values(?,?,?,calc_valorserv("+val[0]+","+val[1]+")"
                    + ",'"+rs.getMedioPago()+"',?,?,?,'"+rs.getEstado()+"')";
            consulta=con.getConexion().prepareStatement(comando);
            consulta.setLong(1,rs.getId_cond());
            consulta.setLong(2,rs.getId_cliente());
            consulta.setInt(3,rs.getId_serv());
            consulta.setString(4,rs.getFecha());
            consulta.setInt(5,rs.getIdCat());
            consulta.setInt(6,rs.getIdRuta());
            consulta.execute();
            mensaje="Servicio pedido exitosamente...";
            consulta.close();
            con.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error al intentar Pedir el servicio...\n"+ex.getMessage();
        }
      return mensaje;  
    }
    public String add_valoracion(int reg, int val){
        String mensaje="";
        try {
            con=new Conexion();
            PreparedStatement consulta = null;
            con.conectar();
            String comando="update reg_servicio set valoracion='"+val+"'\n"+
                           "where id_reg="+reg;
            consulta=con.getConexion().prepareStatement(comando);
            consulta.execute();
            mensaje="Valoración registrada correctamente, gracias";
            consulta.close();
            con.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error al intentar registrar su valoración\n"+ex.getMessage();
        }
      return mensaje;  
    }
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //CONSULTAS ADMIN
    public DefaultTableModel consultar_valt_servs(){
        DefaultTableModel tabla= new DefaultTableModel();
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos=consulta.executeQuery("select * from valor_total_servicios()");
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
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return tabla;
    }
    public DefaultTableModel consultar_cant_servicios_mes(){
        DefaultTableModel tabla= new DefaultTableModel();
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos=consulta.executeQuery("select * from cant_servicios_mes()");
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
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return tabla;
    }
     public DefaultTableModel consultar_servs_solicitados_xtiempo(String fch1, String fch2){
        DefaultTableModel tabla= new DefaultTableModel();
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos=consulta.executeQuery("select * from servs_solicitados_xtiempo('"+fch1+"','"+fch2+"')");
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
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return tabla;
    }
     public DefaultTableModel consultar_valor_servs_mediopago(){
        DefaultTableModel tabla= new DefaultTableModel();
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos=consulta.executeQuery("select * from valor_servs_mediopago()");
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
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return tabla;
    }
}
