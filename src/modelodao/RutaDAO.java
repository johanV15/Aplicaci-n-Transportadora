/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelodao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import modelodto.Ruta;

/**
 *
 * @author julxo
 */
public class RutaDAO {
    Conexion con;
    
    public int validar_ruta(String dir){
        int datosbd=0;
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos=consulta.executeQuery("select id_ruta from ruta\n"
                                                 + "where dir_destino='"+dir+"'");
            if (datos.next()) {  
                datosbd=Integer.parseInt(datos.getObject(1).toString());
            }
            datos.close();
            con.getConexion().close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en metodo validar_ruta de RutaDAO...\n"+e.getMessage());
        }
        return datosbd;
    }
    public String insertar(String dir){
        String mensaje="";
        try {
            con=new Conexion();
            con.conectar();
            PreparedStatement consulta = null;
            String comando= "insert into ruta (dir_destino) values(?)";
            consulta=con.getConexion().prepareStatement(comando);
            consulta.setString(1,dir);
            consulta.execute();
            mensaje="Registro exitoso...";
            consulta.close();
            con.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error en metodo insertar de RutaDAO...\n"+ex;
        }
        return mensaje;  
    }
}
