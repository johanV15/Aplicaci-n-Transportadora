/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 *
 * @author julxo
 */
public class Disponibilidad extends javax.swing.JFrame {

    /**
     * Creates new form Disponibilidad
     */
    public Disponibilidad() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btninsertar = new javax.swing.JButton();
        cmbtiposervcons = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnconsultardisp = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Disponibilidad de Servicio");

        btninsertar.setText("Pedir Servicio");

        cmbtiposervcons.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pasajeros", "Alimentos" }));

        jLabel1.setText("Tipo de servicio");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnconsultardisp.setText("Aceptar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cmbtiposervcons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(btnconsultardisp)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btninsertar)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(cmbtiposervcons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addComponent(btnconsultardisp))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(btninsertar)))
                        .addGap(0, 54, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBtninsertar() {
        return btninsertar;
    }

    public void setBtninsertar(JButton btninsertar) {
        this.btninsertar = btninsertar;
    }

    public JButton getBtnconsultardisp() {
        return btnconsultardisp;
    }

    public void setBtnconsultardisp(JButton btnconsultardisp) {
        this.btnconsultardisp = btnconsultardisp;
    }

    public JComboBox<String> getCmbtiposervcons() {
        return cmbtiposervcons;
    }

    public void setCmbtiposervcons(JComboBox<String> cmbtiposervcons) {
        this.cmbtiposervcons = cmbtiposervcons;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnconsultardisp;
    private javax.swing.JButton btninsertar;
    private javax.swing.JComboBox<String> cmbtiposervcons;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
