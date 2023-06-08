/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobda;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import controlador.controlador;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author julxo
 */
public class ProyectoBDA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new HiFiLookAndFeel());
        controlador c= new controlador();
        c.iniciar();
        //System.exit(0);
    }
    
}
