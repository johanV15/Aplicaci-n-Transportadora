/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelodao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelodto.TelCliente;

/**
 *
 * @author julxo
 */
public class TelClienteDAO {
    Conexion con;
    public String insertar(TelCliente t){
        String mensaje="";
        try {
            con=new Conexion();
            con.conectar();
            PreparedStatement consulta = null;
            String comando= "insert into tel_cliente values(?,?)";
            consulta=con.getConexion().prepareStatement(comando);
            consulta.setLong(1,t.getId_cliente());
            consulta.setLong(2,t.getTelefono());
            consulta.execute();
            mensaje="Registro exitoso...";
            consulta.close();
            con.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error en metodo insertar de TelClienteDAO...\n"+ex.getMessage();
        }
        return mensaje;  
    }
}
