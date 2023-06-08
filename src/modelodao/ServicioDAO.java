/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelodao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author julxo
 */
public class ServicioDAO {
    Conexion con;
    public double consultar(int serv){
        double datosbd=0;
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos=consulta.executeQuery("select tarifa from servicio\n"
                                                + "where id_serv="+serv);
            while (datos.next()) {            
                datosbd=Double.parseDouble(datos.getObject(1).toString());
            }
            datos.close();
            con.getConexion().close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en metodo consultar de ServicioDAO...\n"+e.getMessage());
        }
        return datosbd;
    }
}
