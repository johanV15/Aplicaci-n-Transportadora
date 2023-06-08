/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelodto;

import java.util.Calendar;

/**
 * La clase fecha permite generar objetos o instancias
 * a partir de los parámetros dados por dd, mm y aa
 * @author Julian Gonzalez
 * @version 1.1
 */
public class fecha {
    private int dd, aa;
    private String mm;
    
    public fecha(int dd, String mm, int aa) {
        this.dd = dd;
        this.mm = mm;
        this.aa = aa; 
    }
    public fecha() {
        Calendar fechaSistema= Calendar.getInstance();
        this.dd = fechaSistema.get(Calendar.DAY_OF_MONTH);
        this.mm = String.valueOf(fechaSistema.get(Calendar.MONTH)+1);
        this.aa = fechaSistema.get(Calendar.YEAR);
        
        
    }

    public int getAa() {
        return aa;
    }

    public void setAa(int aa) {
        this.aa = aa;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public int getDd() {
        return dd;
    }

    public void setDd(int dd) {
        this.dd = dd;
    }

    @Override
    public String toString() {
        if (Integer.parseInt(mm)<10) {
            mm="0"+mm;
        }
        return aa + "-" + mm + "-" + dd;
    }
}
