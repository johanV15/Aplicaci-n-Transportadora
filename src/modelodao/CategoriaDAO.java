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
public class CategoriaDAO {
    Conexion con;
    public double consultar(int cat){
        double datosbd=0;
        try {
            con= new Conexion();
            con.conectar();
            Statement consulta= con.getConexion().createStatement();
            ResultSet datos=consulta.executeQuery("select tarifa from categoria\n"
                                                + "where id_cat="+cat);
            while (datos.next()) {            
                datosbd=Double.parseDouble(datos.getObject(1).toString());
            }
            datos.close();
            con.getConexion().close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en metodo consultardisp de Reg_ServicioDAO...\n"+e.getMessage());
        }
        return datosbd;
    }
}
