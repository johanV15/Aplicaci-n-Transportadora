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
import javax.swing.JOptionPane;
import modelodto.*;

/**
 *
 * @author julxo
 */
public class VehiculoDAO {
    Conexion con;
    
    public String insertar(Vehiculo v){
    String mensaje="";
        try {
            con=new Conexion();
            PreparedStatement consulta = null;
            con.conectar();
            String comando= "insert into vehiculo values(?,?,?,?)";
            consulta=con.getConexion().prepareStatement(comando);
            consulta.setString(1,v.getPlaca());
            consulta.setInt(2,v.getModelo());
            consulta.setLong(3,v.getIdCond());
            consulta.setInt(4,v.getIdServ());
            consulta.execute();
            mensaje="Registro exitoso...";
            consulta.close();
            con.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error en metodo insertar de VehiculoDAO...\n"+ex.getMessage();
        }
        return mensaje;  
    }
    public String verificarcond(int id){
        String datosbd="";
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos=consulta.executeQuery("select id_cond from vehiculo where id_cond="+id);
            ResultSetMetaData campos=datos.getMetaData();
            while(datos.next()){
                datosbd+=datos.getObject(1);
            }
            datos.close();
            con.getConexion().close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error en metodo insertar de VehiculoDAO...\n"+ex.getMessage());
        }
        return datosbd;
    }
}
