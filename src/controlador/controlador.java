/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.FileDialog;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelodao.*;
import modelodto.*;
import vista.*;

/**
 *
 * @author julxo
 */
public class controlador implements MouseListener, ActionListener{
    login l;
    Principal p;
    Registro r;
    Disponibilidad d;
    PedirServicio ps;
    RegistroVehiculo rv;
    Valoracion v;
    RegServicio rs;
    ClienteDAO cdao;
    ConductorDAO codao;
    VehiculoDAO vdao;
    UsuarioDAO udao;
    Reg_ServicioDAO rsdao;
    RutaDAO rdao;
    CategoriaDAO catdao;
    ServicioDAO servdao;
    String usuario, dispcond;
    String[] datos_user;
    
    public controlador() {
        this.l=new login();
        this.p=new Principal();
        this.r=new Registro();
        this.d=new Disponibilidad();
        this.ps=new PedirServicio();
        this.rv=new RegistroVehiculo();
        this.v=new Valoracion();
        this.rs=new RegServicio();
        this.cdao=new ClienteDAO();
        this.codao=new ConductorDAO();
        this.vdao=new VehiculoDAO();
        this.udao=new UsuarioDAO();
        this.rsdao=new Reg_ServicioDAO();
        this.rdao=new RutaDAO();
        this.catdao=new CategoriaDAO();
        this.servdao=new ServicioDAO();
        this.usuario="";
        this.dispcond="";
        this.datos_user= new String[15];
        l.getBtnlogin().addActionListener(this);
        l.getLblregistrar().addMouseListener(this);
        r.getBtnregistrar().addActionListener(this);
        r.getBtnarchfoto().addActionListener(this);
        p.getBtndisponibilidad().addActionListener(this);
        p.getBtnterminarserv().addActionListener(this);
        p.getBtnregistroadm().addActionListener(this);
        d.getBtninsertar().addActionListener(this);
        d.getBtnconsultardisp().addActionListener(this);
        rv.getBtnregistrarvh().addActionListener(this);
        ps.getBtnpedirserv().addActionListener(this);
        v.getGbtn().add(v.getRbtn1());
        v.getGbtn().add(v.getRbtn2());
        v.getGbtn().add(v.getRbtn3());
        v.getGbtn().add(v.getRbtn4());
        v.getGbtn().add(v.getRbtn5());
        v.getBtnaceptar().addActionListener(this);
        p.getBtnactcliente().addActionListener(this);
        p.getBtndelcliente().addActionListener(this);
        p.getBtnactcond().addActionListener(this);
        p.getBtndelcond().addActionListener(this);
        p.getBtnvalt_servs().addActionListener(this);
        p.getBtn_cantservs_mes().addActionListener(this);
        p.getBtn_servs_clientes().addActionListener(this);
        p.getBtnvalt_mediopago().addActionListener(this);
        p.getJmisesion().addActionListener(this);
    }
    
    public void iniciar(){
        l.setLocationRelativeTo(null);
        p.setLocationRelativeTo(null);
        r.setLocationRelativeTo(null);
        d.setLocationRelativeTo(null);
        ps.setLocationRelativeTo(null);
        v.setLocationRelativeTo(null);
        l.setTitle("BDA");
        l.setVisible(true);
        d.getBtninsertar().setEnabled(false);
        //ps.getBtnpedirserv().setEnabled(false);
        r.getLblfotocond().setVisible(false);
        r.getBtnarchfoto().setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(l.getBtnlogin())){           
            UsuarioDAO udao= new UsuarioDAO();
            if (l.getJcbtipousuario().getSelectedItem().equals("Seleccionar...")) {
                usuario="admin";
            }else if (l.getJcbtipousuario().getSelectedItem().equals("Cliente")) {
                usuario="cliente";
            }else{
                usuario="conductor";
            }
            int validar=udao.validar(l.getTxtusuario().getText(),
                                        l.getJppass().getText(),
                                        usuario);
            String aux=udao.traerusuario(usuario, l.getTxtusuario().getText());
            datos_user = aux.split(";");
            if (validar==1) {
                l.setVisible(false);
                p.setVisible(true);
                p.getTablageneral().setModel(rsdao.consultar(usuario, Long.parseLong(datos_user[0])));
                l.getLblincorrecto().setText("");
                limpiar();
                if (usuario=="admin") {
                    p.getLbltitulopedir().setVisible(false);
                    p.getBtndisponibilidad().setVisible(false);
                    //p.getBtninsertar().setVisible(false);
                    p.getLblbienvenido().setText("Bienvenid@ "+datos_user[1]);
                    p.getJminombre().setText("");
                    p.getJmusuario().setText(datos_user[1]);
                    p.getJmiuser().setText(datos_user[2]);
                    
                    p.getTablaclientes().setModel(cdao.consultar());
                    p.getTablaconducts().setModel(codao.consultar());
                    p.getLblultserv().setVisible(false);
                    p.getScrollultsev().setVisible(false);

                    r.getJcbtipouser().addItem("Conductor");
                    p.getBtnterminarserv().setVisible(false);
                    p.getjTabbedPane1().add(p.getPanel2());
                    p.getjTabbedPane1().add(p.getPanel3());
                    p.getjTabbedPane1().addTab("Usuarios", p.getPanel2());
                    p.getjTabbedPane1().addTab("Consultas", p.getPanel3());
                    p.getJmifoto().setIcon(null);
                }else if (usuario=="cliente"){
                    p.getLblbienvenido().setText("Bienvenid@ "+datos_user[1]+" "+datos_user[2]);
                    p.getJmusuario().setText(datos_user[1]);
                    p.getJminombre().setText(datos_user[1]+" "+datos_user[2]);
                    p.getJmiuser().setText(datos_user[6]);
                    p.getjTabbedPane1().remove(p.getPanel2());
                    p.getjTabbedPane1().remove(p.getPanel3());
                    p.getBtnterminarserv().setVisible(false);
                    p.getLblultserv().setVisible(true);
                    p.getTblultserv().setModel(rsdao.consultar_ultserv(usuario, Long.parseLong(datos_user[0])));
                    if (p.getTblultserv().getValueAt(0, 7).toString().equalsIgnoreCase("En camino")) {
                        p.getBtndisponibilidad().setEnabled(false);
                    }
                    int reg=rsdao.consultar_valoracion(Integer.parseInt(p.getTblultserv().getValueAt(0, 0).toString()));
                    if (reg==1) {
                        v.setVisible(true);
                    }
                    p.getScrollultsev().setVisible(true);
                    p.getJmifoto().setIcon(null);
                    p.getLblultserv().setText("ÚLTIMO SERVICIO PEDIDO");
                }else{
                    p.getLbltitulotabla().setText("Servicios Realizados");
                    p.getLblbienvenido().setText("Bienvenid@ "+datos_user[1]+" "+datos_user[2]);
                    p.getJminombre().setText(datos_user[1]+" "+datos_user[2]);
                    p.getJmusuario().setText(datos_user[1]);
                    p.getJmiuser().setText(datos_user[7]);
                    
                    int img=codao.traerfoto(Integer.parseInt(datos_user[0]));
                    if (img==1) {
                        ImageIcon imgoriginal = new ImageIcon("src/img/cond.png");
                        Image imgajustada = imgoriginal.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                        imgoriginal= new ImageIcon(imgajustada);
                        p.getJmifoto().setIcon(imgoriginal);
                    }                   
                    p.getjTabbedPane1().remove(p.getPanel2());
                    p.getjTabbedPane1().remove(p.getPanel3());
                    p.getLblultserv().setVisible(true);
                    p.getLblultserv().setText("ÚLTIMO SERVICIO REALIZADO/EN CURSO");
                    vdao= new VehiculoDAO();
                    String datosbd=vdao.verificarcond(Integer.parseInt(datos_user[0]));
                    if (datosbd.isEmpty()) {
                        p.getLbltitulopedir().setText("Registrar Vehiculo");
                        p.getBtndisponibilidad().setText("Registrar"); 
                    }else{
                        p.getLbltitulopedir().setVisible(false);
                        p.getBtndisponibilidad().setVisible(false);
                    }
                    p.getTblultserv().setModel(rsdao.consultar_ultserv(usuario, Long.parseLong(datos_user[0])));
                    if (p.getTblultserv().getValueAt(0,9).toString().equalsIgnoreCase("En camino")) {
                       p.getBtnterminarserv().setVisible(true);
                    }
                    p.getScrollultsev().setVisible(true);
                }
            }else{
                l.getLblincorrecto().setText("Usuario o Contraseña incorrectos");
                limpiar();
            }
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if (e.getSource().equals(p.getBtnregistroadm())) {
            r.setVisible(true);
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(r.getBtnregistrar())){
            String msg="";
            switch (r.getJcbtipouser().getSelectedIndex()) {
                case 1:
                    Usuario u=new Usuario();
                    udao= new UsuarioDAO();
                    
                    if (r.getJppass().getText().equals(r.getJppassconfirm().getText())) {
                        u.setUsuario(r.getTxtusuario().getText());
                        u.setPass(r.getJppass().getText()); 
                        msg=udao.insertar(u);
                    }else{
                        JOptionPane.showMessageDialog(r, "Las contraseñas no coinciden"); 
                    }         
                    if (msg.equalsIgnoreCase("Registro exitoso...")) {
                        Cliente c=new Cliente();
                        cdao= new ClienteDAO();
                        c.setIdCliente(Long.parseLong(r.getTxtid().getText()));
                        c.setUsuario(u.getUsuario());
                        c.setNombre(r.getTxtnom().getText());
                        c.setApellido(r.getTxtapell().getText());
                        c.setDireccion(r.getTxtdirecc().getText());
                        c.setGenero((String) r.getJcbgenero().getSelectedItem());
                        c.setNacionalidad(r.getTxtnac().getText());
                        System.out.println(cdao.insertar(c));
                        
                        TelCliente t=new TelCliente();
                        TelClienteDAO tcdao=new TelClienteDAO();
                        t.setId_cliente(c.getIdCliente());
                        t.setTelefono(Long.parseLong(r.getTxttel().getText()));
                        System.out.println(tcdao.insertar(t));
                        if (!r.getTxttel2().getText().equalsIgnoreCase("")) {
                            t=new TelCliente();
                            t.setId_cliente(c.getIdCliente());
                            t.setTelefono(Long.parseLong(r.getTxttel2().getText()));
                            System.out.println(tcdao.insertar(t));
                            }
                        JOptionPane.showMessageDialog(p, "Usuario Registrado correctamente");
                        p.getTablaclientes().setModel(cdao.consultar());
                        r.setVisible(false);
                        limpiar();
                    }else{
                        System.out.println(msg);
                    }
                    break;
                case 2:
                    Usuario u2=new Usuario();
                    UsuarioDAO udao2= new UsuarioDAO();
                    
                    if (r.getJppass().equals(r.getJppassconfirm())) {
                        u2.setUsuario(r.getTxtusuario().getText());
                        u2.setPass(r.getJppass().getText());
                        msg=udao2.insertar(u2);
                    }else{
                        JOptionPane.showMessageDialog(r, "Las contraseñas no coinciden"); 
                    }  
                    
                    if (msg.equalsIgnoreCase("Registro exitoso...")) {
                        Conductor c2=new Conductor();
                        codao= new ConductorDAO();
                        c2.setIdCond(Long.parseLong(r.getTxtid().getText()));
                        c2.setUsuario(u2.getUsuario());
                        c2.setNombre(r.getTxtnom().getText());
                        c2.setApellido(r.getTxtapell().getText());
                        c2.setDireccion(r.getTxtdirecc().getText());
                        c2.setGenero((String) r.getJcbgenero().getSelectedItem());
                        c2.setNacionalidad(r.getTxtnac().getText());
                        c2.setFoto(codao.foto(r.getLblarchfoto().getText()));
                        System.out.println(codao.insertar(c2));

                        TelConductor t2=new TelConductor();
                        TelConductorDAO tcodao=new TelConductorDAO();
                        t2.setId_cond(c2.getIdCond());
                        t2.setTelefono(Long.parseLong(r.getTxttel().getText()));
                        System.out.println(tcodao.insertar(t2));
                        if (!r.getTxttel2().getText().equalsIgnoreCase("")) {
                            t2=new TelConductor();
                            t2.setId_cond(c2.getIdCond());
                            t2.setTelefono(Long.parseLong(r.getTxttel2().getText()));
                            System.out.println(tcodao.insertar(t2));
                        }
                        JOptionPane.showMessageDialog(p, "Usuario Registrado correctamente");
                        p.getTablaconducts().setModel(codao.consultar());
                        r.setVisible(false);
                        limpiar();
                    }else{
                        System.out.println(msg);
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(null,"Seleccionar tipo de usuario...");
            }
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(rv.getBtnregistrarvh())){
            Vehiculo v= new Vehiculo();
            v.setPlaca(rv.getTxtplaca().getText());
            v.setModelo(Integer.parseInt(rv.getTxtmodelo().getText()));
            v.setIdCond(Long.parseLong(datos_user[0]));
            v.setIdServ(rv.getCmbtiposerv().getSelectedIndex()+1);
            vdao= new VehiculoDAO();
            JOptionPane.showMessageDialog(p,vdao.insertar(v));
            rv.setVisible(false);
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------

        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        //IMPLEMENTAR VALORACION
        if(e.getSource().equals(p.getBtnterminarserv())){
            JOptionPane.showMessageDialog(p, rsdao.actualizar_estado(Long.parseLong(datos_user[0])));
            p.getTablageneral().setModel(rsdao.consultar(usuario, Long.parseLong(datos_user[0])));
            p.getTblultserv().setModel(rsdao.consultar_ultserv(usuario, Long.parseLong(datos_user[0])));
            p.getBtnterminarserv().setVisible(false);
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(p.getBtndisponibilidad())){
            if (usuario=="cliente") {
                d.setVisible(true);
            }else{
                rv.setVisible(true);
            }  
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(d.getBtnconsultardisp())){
            try {
                int aux=d.getCmbtiposervcons().getSelectedIndex()+1;
                rsdao = new Reg_ServicioDAO();
                dispcond=rsdao.consultardisp(aux);
                System.out.println("conductor disponible: "+dispcond);
                if (!dispcond.equals("null")) {
                    JOptionPane.showMessageDialog(p, "Se ha verificado la disponibilidad...");
                    d.getBtninsertar().setEnabled(true);
                }else{
                    JOptionPane.showMessageDialog(p, "No se ha encontrando disponibilidad para el servicio "+
                                                  d.getCmbtiposervcons().getSelectedItem());
                    d.getBtninsertar().setEnabled(false);
                }
            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(p, "No hay datos para cargar...\n"+ex.getMessage());
            }
        }
        
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        //PENDIENTE
        if(e.getSource().equals(d.getBtninsertar())){
            ps.setVisible(true);
            d.setVisible(false);
            ps.getCmbtiposervadd().addItem(d.getCmbtiposervcons().getSelectedItem().toString());
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(ps.getBtnpedirserv())){
            fecha fch=new fecha();
            double[]val=new double[2];
            int idruta=rdao.validar_ruta(datos_user[3]);
            if (idruta==0) {
                Ruta r=new Ruta();
                rdao.insertar(datos_user[3]);
                idruta=rdao.validar_ruta(datos_user[3]); 
            }
            rs=new RegServicio();
            rs.setId_cond(Long.parseLong(dispcond));
            rs.setId_cliente(Long.parseLong(datos_user[0]));
            rs.setId_serv(d.getCmbtiposervcons().getSelectedIndex()+1);
            rs.setMedioPago(ps.getCmbmediopago().getSelectedItem().toString());
            rs.setFecha(fch.toString());
            rs.setIdCat(ps.getCmbcat().getSelectedIndex()+1);
            rs.setIdRuta(idruta);
            rs.setEstado("En camino");
            JOptionPane.showMessageDialog(ps, rs.toString());
            //ps.getTxtvalorserv().setText(String.valueOf(servdao.consultar(rs.getId_serv())));
            //ps.getTxtvalorcat().setText(String.valueOf(catdao.consultar(rs.getIdCat())));
            val[0]=Integer.parseInt(ps.getTxtvalorserv().getText());
            val[1]=Integer.parseInt(ps.getTxtvalorcat().getText());
            JOptionPane.showMessageDialog(p, rsdao.insertar(rs,val));
            p.getTablageneral().setModel(rsdao.consultar(usuario, Long.parseLong(datos_user[0])));
            p.getTblultserv().setModel(rsdao.consultar_ultserv(usuario, Long.parseLong(datos_user[0])));
            ps.setVisible(false);
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(true){
            
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(v.getBtnaceptar())){
            int val=0;
            if (v.getRbtn1().isSelected()) {
                val=1;
            }
            if (v.getRbtn2().isSelected()) {
                val=2;
            }
            if (v.getRbtn3().isSelected()) {
                val=3;
            }
            if (v.getRbtn4().isSelected()) {
                val=4;
            }
            if (v.getRbtn5().isSelected()) {
                val=5;
            }
            JOptionPane.showMessageDialog(p, rsdao.add_valoracion(Integer.parseInt(p.getTblultserv().getValueAt(0, 0).toString()), val));
            v.setVisible(false);
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(r.getBtnarchfoto())){
            /*JFileChooser fc = new JFileChooser();
            int resp = fc.showOpenDialog(r);
            if (resp == JFileChooser.APPROVE_OPTION) {
                File arch = fc.getSelectedFile();
                r.getLblarchfoto().setText(arch.getName());
            }*/
            //Código para abrir una ventana
            FileDialog arch;
            arch = new FileDialog(r, "Insertar foto",FileDialog.LOAD);
            arch.setVisible(true);
            if(arch.getFile()!=null){
               String directorio = arch.getDirectory();
               String nombreArchivo =arch.getFile(); 
               String ruta = directorio + nombreArchivo;
               r.getLblarchfoto().setText(ruta);
            }
            else{
               System.out.println("No Seleccionó Archivo"); 
            }
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(p.getBtnactcliente())){
            int fila=p.getTablaclientes().getSelectedRow();
            if(fila==-1){
                JOptionPane.showMessageDialog(p, "Fila no seleccionada...");
            }else{
                Cliente c=new Cliente();
                c.setIdCliente(Long.parseLong(p.getTablaclientes().getValueAt(fila, 0).toString()));
                c.setNombre(p.getTablaclientes().getValueAt(fila, 1).toString());
                c.setApellido(p.getTablaclientes().getValueAt(fila, 2).toString());
                c.setDireccion(p.getTablaclientes().getValueAt(fila, 3).toString());
                c.setGenero(p.getTablaclientes().getValueAt(fila, 4).toString());
                c.setNacionalidad(p.getTablaclientes().getValueAt(fila, 5).toString());
                JOptionPane.showMessageDialog(p,cdao.actualizar(c));
                p.getTablaclientes().setModel(cdao.consultar());
                fila=-1;
            }
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(p.getBtndelcliente())){
            int fila=p.getTablaclientes().getSelectedRow();
            if(fila==-1){
                JOptionPane.showMessageDialog(p, "Fila no seleccionada...");
            }else{
                String user=p.getTablaclientes().getValueAt(fila, 6).toString();
                JOptionPane.showMessageDialog(p,cdao.eliminar(user));
                p.getTablaclientes().setModel(cdao.consultar());
                fila=-1;
            }
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(p.getBtnactcond())){
            int fila=p.getTablaconducts().getSelectedRow();
            if(fila==-1){
                JOptionPane.showMessageDialog(p, "Fila no seleccionada...");
            }else{
                Conductor co=new Conductor();
                co.setIdCond(Long.parseLong(p.getTablaconducts().getValueAt(fila, 0).toString()));
                co.setNombre(p.getTablaconducts().getValueAt(fila, 1).toString());
                co.setApellido(p.getTablaconducts().getValueAt(fila, 2).toString());
                co.setDireccion(p.getTablaconducts().getValueAt(fila, 3).toString());
                co.setGenero(p.getTablaconducts().getValueAt(fila, 4).toString());
                co.setNacionalidad(p.getTablaconducts().getValueAt(fila, 5).toString());
                JOptionPane.showMessageDialog(p,codao.actualizar(co));
                p.getTablaconducts().setModel(codao.consultar());
                fila=-1;
            }
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(p.getBtndelcond())){
            int fila=p.getTablaconducts().getSelectedRow();
            if(fila==-1){
                JOptionPane.showMessageDialog(p, "Fila no seleccionada...");
            }else{
                String user=p.getTablaconducts().getValueAt(fila, 7).toString();
                JOptionPane.showMessageDialog(p,codao.eliminar(user));
                p.getTablaconducts().setModel(codao.consultar());
                fila=-1;
            }
        }
        if(e.getSource().equals(p.getJmisesion())){
            p.setVisible(false);
            l.setVisible(true);
            limpiar();
            usuario="";
            datos_user=new String[15];
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        //PARTE ADMIN
        if(e.getSource().equals(p.getBtnvalt_servs())){
           p.getTblvalt_servs().setModel(rsdao.consultar_valt_servs());
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(p.getBtn_cantservs_mes())){
            p.getTblcantservs().setModel(rsdao.consultar_cant_servicios_mes());
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(p.getBtn_servs_clientes())){
            try {
                SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd");
                String fch1=formato.format(p.getDate1().getDate());
                String fch2=formato.format(p.getDate2().getDate());
                p.getTblservs_clientes().setModel(
                rsdao.consultar_servs_solicitados_xtiempo(fch1, fch2)
                );
                //JOptionPane.showMessageDialog(p, "fecha 1:"+fch1+"\nfecha 2: "+fch2);
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(p, "Error al insertar fecha\n"+ex.getMessage());
            }
            
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
        if(e.getSource().equals(p.getBtnvalt_mediopago())){
            p.getTblvalt_mediopago().setModel(rsdao.consultar_valor_servs_mediopago());
        }
        //--------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------
    }
    public void limpiar(){
        for (Object limp: l.getjPanel1().getComponents()) {
            if(limp instanceof JTextField)
                ((JTextField)limp).setText("");
        }
        for (Object limp: p.getPanel2().getComponents()) {
            if(limp instanceof JTextField)
                ((JTextField)limp).setText("");
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
         if(e.getSource().equals(l.getLblregistrar())){
             r.setVisible(true);
             r.getTxttel2().setVisible(true);
         }  
    }
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
