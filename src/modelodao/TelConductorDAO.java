/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelodao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelodto.*;

/**
 *
 * @author julxo
 */
public class TelConductorDAO {
    Conexion con;
    public String insertar(TelConductor t){
        String mensaje="";
        try {
            con=new Conexion();
            con.conectar();
            PreparedStatement consulta = null;
            String comando= "insert into tel_conductor values(?,?)";
            consulta=con.getConexion().prepareStatement(comando);
            consulta.setLong(1,t.getId_cond());
            consulta.setLong(2,t.getTelefono());
            consulta.execute();
            mensaje="Registro exitoso...";
            consulta.close();
            con.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error en metodo insertar de TelConductorDAO...\n"+ex.getMessage();
        }
        return mensaje;  
    }
}
