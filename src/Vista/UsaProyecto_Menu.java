/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Modelo.Empleado;
import Modelo.Empresa;
import Modelo.PersonaNatural;
import Modelo.Proyecto;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan D
 */
public class UsaProyecto_Menu extends javax.swing.JFrame {
    /*definir y crear el contenedor (colecion ArrayList)*/
    
    ArrayList<Proyecto> losProyectos = new ArrayList<>();

/*consultarporyectos - + consultarTodosProyectos(datos : ArrayList<Proyecto>) : String */

    public String consultarProyectosEnCurso(ArrayList<Proyecto> datos){
        String resultado = "CONSULTA DE PROYECTOS EN CURSO\n";
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }
        boolean existe = false;
        for(Proyecto objeto : datos){
            if(objeto.getEstado().equalsIgnoreCase("EN CURSO")){
                existe = true;
                resultado += "\n" + objeto.toString();
                resultado += "\n  fecha de finalización= " + objeto.determinarFechaFinalizacion();
                resultado += "\n";
            }
        }
        if(!existe){
            resultado += "No hay proyectos con estado EN CURSO.";
        }
        return resultado;
    }
        public String consultarPrimerProyecto(ArrayList<Proyecto> datos){
        String resultado = "CONSULTA DEL PRIMER PROYECTO REGISTRADO\n";
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }
        Proyecto objeto = datos.get(0);
        resultado += "\n" + objeto.toString();
        resultado += "\n  fecha de finalización= " + objeto.determinarFechaFinalizacion();
        return resultado;
    }
       public String consultarUltimoProyecto(ArrayList<Proyecto> datos){
        String resultado = "CONSULTA DEL ÚLTIMO PROYECTO REGISTRADO\n";
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }
        Proyecto objeto = datos.get(datos.size() - 1);
        resultado += "\n" + objeto.toString();
        resultado += "\n  fecha de finalización= " + objeto.determinarFechaFinalizacion();
        return resultado;
    }
    public String consultarProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto){
        String resultado = "CONSULTA DEL PROYECTO " + codigoProyecto + "\n";
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }
        for(Proyecto objeto : datos){
            if(objeto.getCodigo().equalsIgnoreCase(codigoProyecto)){
                resultado += "\n" + objeto.toString();
                resultado += "\n  fecha de finalización= " + objeto.determinarFechaFinalizacion();
                return resultado;
            }
        }
        resultado += "No hay proyecto con el código indicado.";
        return resultado;
    }
        public String consultarTodosProyectos(ArrayList<Proyecto> datos){
        String resultado = "CONSULTA DE TODOS LOS PROYECTOS\n";
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }
        for(Proyecto objeto : datos){
            resultado += "\n" + objeto.toString();
            resultado += "\n  fecha de finalización= " + objeto.determinarFechaFinalizacion();
            resultado += "\n";
        }
        return resultado;
    }   
/*+ consultarProyectosEnCurso(datos : ArrayList<Proyecto>) : String*/
/*+ consultarPrimerProyecto(datos : ArrayList<Proyecto>) : String*/
/*+ consultarUltimoProyecto(datos : ArrayList<Proyecto>) : String*/
/*+ consultarProyectoDadoCodigo(datos : ArrayList<Proyecto>, codigoProyecto : String) : String*/
    
/*Consultar el gerente "codigoProyecto" _ consultarGerenteProyectoDadoCodigo(datos : ArrayList<Proyecto>, codigoProyecto : String) : String*/
    public String consultarGerenteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        /*String codigo = (codigoProyecto == null) ? "" : codigoProyecto.trim();*//*Por que*/
        String resultado = "CONSULTA DEL GERENTE DEL PROYECTO " + codigoProyecto + "\n";
        /*String resultado = "CONSULTA DEL GERENTE DEL PROYECTO " + codigo + "\n";*/
        if(datos.isEmpty()) {
            resultado += "No hay proyectos registrados.";
            return resultado;
        }
        boolean proyectoExiste = false;   
        for(Proyecto objeto : datos){
            if(objeto.getCodigo().equalsIgnoreCase(codigoProyecto)){
                proyectoExiste = true;                
                resultado += ", Proyecto=" + objeto.getTitulo();
                resultado += ", desempeño=" + objeto.getSuGerente(); 
                resultado += "\n";
            }
        }
        if(!proyectoExiste) {
            resultado += "No hay gerente con el código indicado.";
        }
        return resultado;
    }
 /*Consultar dado "numeroIDGerente" _ + consultarProyectosDadoNumIDEGerente(datos : ArrayList<Proyecto>, numIDEGerente : String) : String*/
    public String consultarProyectosDadoNumIDEGerente(ArrayList<Proyecto> losProyectos, String numIDEGerente){
        String resultado = "CONSULTA DE PROYECTOS DADO IDE GERENTE " + numIDEGerente + "\n";
        if(losProyectos.isEmpty()) {
            resultado += "No hay gerentes registrados.";
            return resultado;
        }    
        boolean gerenteExiste = false;   
        for(Proyecto objeto : losProyectos){
            if(objeto.getSuGerente().getNumeroIdentificacion().equalsIgnoreCase(numIDEGerente)){ 
                gerenteExiste = true;                
                resultado += "\n" + objeto.toString();
                resultado += "\n  fecha de finalización= " + objeto.determinarFechaFinalizacion();
                resultado += "\n";
            }
        }
        if(!gerenteExiste) {
            resultado += "No hay gerentes con el IDE indicado.";
        }
        return resultado;
    }    
    
/*Consultar dado "numIDECliente" _ + consultarProyectosDadoNumIDECliente(datos : ArrayList<Proyecto>, numIDECliente : String) : String*/
    public String consultarProyectosDadoNumIDECliente(ArrayList<Proyecto> losProyectos, String numIDECliente){
        String resultado = "CONSULTA DE PROYECTOS DADO IDE Cliente " + numIDECliente + "\n";
        if(losProyectos.isEmpty()) {
            resultado += "No hay clientes registrados.";
            return resultado;
        }    
        boolean clienteExiste = false;   
        for(Proyecto objeto : losProyectos){
            if(objeto.getSuCliente().getNumeroIdentificacion().equalsIgnoreCase(numIDECliente)){ 
                clienteExiste = true;                
                resultado += "\n" + objeto.toString();
                resultado += "\n  fecha de finalización= " + objeto.determinarFechaFinalizacion();
                resultado += "\n";
            }
        }
        if(!clienteExiste) {
            resultado += "No hay Clientes con el IDE indicado.";
        }
        return resultado;
    }    
     
 /*Consultar dado "tipoCliente" _ + consultarProyectosDadoTipoCliente(datos : ArrayList<Proyecto>, tipoCliente : String) : String*/
    public String consultarProyectosDadoTipoCliente(ArrayList<Proyecto> losProyectos, String tipoCliente){
        String resultado = "CONSULTAR LOS CLIENTES DADA CATEGORÍA " + tipoCliente + "\n";
        if(losProyectos.isEmpty()){
            resultado += "\n lista de proyectos vacía.";
            return resultado;
        }
        boolean existe = false;
        for(Proyecto objeto : losProyectos){
            switch(tipoCliente){
                case "EMPRESA":
                    if(objeto.getSuCliente() instanceof Empresa){
                        existe = true;
                        resultado += "\n" + objeto.toString();
                        resultado += "\n  fecha de finalización= " + objeto.determinarFechaFinalizacion();
                        resultado += "\n";                    
                    }                    
                    break;
                case "PERSONANATURAL":
                    if(objeto.getSuCliente() instanceof PersonaNatural){
                        existe = true;
                        resultado += "\n" + objeto.toString();
                        resultado += "\n  fecha de finalización= " + objeto.determinarFechaFinalizacion();
                        resultado += "\n";                     
                    }                     
                    break;
                default:
                    return resultado + "\nTipo de cliente inválido. Use Empresa o PersonaNatural.";
            }
        }
        if(!existe){
            resultado += "\nNo hay proyectos con el tipo de cliente indicado.";
            
        }
        return resultado;
    }
                                                  
/*Consultar Equipos _ + consultarEquipoProyectoDadoCodigo(datos : ArrayList<Proyecto>, codigoProyecto : String) : String*/
    public String consultarEquipoProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        // 1. Verificar si la lista está llena
        if (datos == null || datos.isEmpty()) {
            return "No hay proyectos registrados en el sistema (la lista de proyectos está vacía).";
        }

        if (codigoProyecto == null || codigoProyecto.trim().isEmpty()) {
            return "Error: Debe indicar un código de proyecto válido.";
        }

        // 2. Recorrer la lista de proyectos y buscar el proyecto que cumpla la condición
        for (Proyecto proyecto : datos) {
            if (proyecto.getCodigo().equalsIgnoreCase(codigoProyecto.trim())) {
                StringBuilder sb = new StringBuilder();
                sb.append("========================================================\n");
                sb.append("PROYECTO: ").append(proyecto.getCodigo()).append(" - ").append(proyecto.getTitulo()).append("\n");
                sb.append("========================================================\n");

                ArrayList<Empleado> equipo = proyecto.getSuEquipo();
                if (equipo == null || equipo.isEmpty()) {
                    sb.append("El proyecto aún no tiene integrantes registrados en su equipo.\n");
                } else {
                    sb.append("INTEGRANTES DEL EQUIPO (").append(equipo.size()).append("):\n");
                    for (int i = 0; i < equipo.size(); i++) {
                        sb.append(i + 1).append(". ").append(equipo.get(i).toString()).append("\n");
                    }
                }
                return sb.toString();
            }
        }

        return "No se encontró ningún proyecto con el código: " + codigoProyecto;
    }   
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UsaProyecto_Menu.class.getName());
    /***********************************************************************************/
/*+ leerArchivoDeObjetosProyectos() : ArrayList<Proyecto>*/
    public void leerArchivoObjetos (ArrayList<Proyecto> listaPrincipal){
        String nombreArchivo = "datosProyectos.obj";
        ObjectInputStream entrada = null;
        ArrayList<Proyecto> listaNueva = new ArrayList<>();
    if(!listaPrincipal.isEmpty()) {listaPrincipal = new ArrayList<>();}
    try{
        entrada = new ObjectInputStream(new FileInputStream(nombreArchivo));
        listaNueva = (ArrayList<Proyecto>) entrada.readObject();
        listaPrincipal.addAll (listaNueva);
    } catch(Exception ex){
        JOptionPane.showMessageDialog(null,"Operacion fallida: error recuperado datos desde archivo","lectura de archivo", JOptionPane.ERROR_MESSAGE);
    }finally{
        try{
            entrada.close();
            JOptionPane.showMessageDialog(null, "Operacion exitosa, archivo leido correctamente.", "LECTURA DE ARCHIVO", JOptionPane.INFORMATION_MESSAGE);    
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Operacion fallida: error cerrando archivo.","LECTURA DE ARCHIVO2", JOptionPane.ERROR_MESSAGE);
        }
    }
}
/*+ leerArchivoDeObjetosClientes() : ArrayList<Cliente>*/

    /*Eliminar*/
/*+ eliminarIntegranteProyectoDadoCodigo(datos : ArrayList<Proyecto>, codigoProyecto : String) : String*/ 
        private String pedirDato(String mensaje){
        String dato = JOptionPane.showInputDialog(this, mensaje);
        if(dato == null || dato.isBlank()){
            return null;
        }
        return dato.trim();
    }
    public String eliminarIntegranteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto){
        String resultado = "ELIMINAR INTEGRANTE DEL PROYECTO " + codigoProyecto + "\n";
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }
        for(Proyecto objeto : datos){
            if(objeto.getCodigo().equalsIgnoreCase(codigoProyecto)){
                if(objeto.getSuEquipo() == null || objeto.getSuEquipo().isEmpty()){
                    resultado += "El proyecto no tiene integrantes en su equipo.";
                    return resultado;
                }
                String id = pedirDato("Número de identificación del integrante a eliminar:");
                if(id == null){
                    resultado += "Operación cancelada, identificación vacía.";
                    return resultado;
                }
                Empleado eliminado = objeto.eliminarIntegrante(id);
                if(eliminado == null){
                    resultado += "El empleado " + id + " no pertenece al equipo del proyecto.";
                } else{
                    resultado += "Integrante eliminado:\n" + eliminado;
                }
                return resultado;
            }
        }
        resultado += "No hay proyecto con el código indicado.";
        return resultado;
    }
    /**
     * Creates new form UsaProyecto_Menu
     */
    public UsaProyecto_Menu() {
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

        jButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaResultados = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldFiltro = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton.setText("1. Registrar un proyecto");
        jButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton.addActionListener(this::jButtonRegistrarUnProyecto);

        jButton2.setText("2. Consultar todos los Proyectos");
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(this::jButtonConsultarTodosLosProyectos);

        jButton3.setText("3. Consultar los proyectos en curso");
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(this::jButtonConsultarLosProyectosEnCurso);

        jButton4.setText("4. Consultar el primer proyecto registrado");
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(this::jButtonConsultarElPrimerProyectoRegistrado);

        jButton5.setText("5. Consultar el último proyecto registrado");
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(this::jButtonConsultarElUltimoProyectoRegistrado);

        jButtonSalir.setText("Salir");
        jButtonSalir.addActionListener(this::jButtonSalirActionPerformed);

        jButton1.setText("6. Consultar el gerente de un proyecto");
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(this::jButtonConsultarElGerenteDeUnProyecto);

        jButton6.setText("7. Consultar el equipo de un proyecto");
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.addActionListener(this::jButtonConsultarElEquipoDeUnProyecto);

        jButton7.setText("8. Consultar los proyectos de un gerente");
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton7.addActionListener(this::jButtonConsultarLosProyectosDeUnGerente);

        jButton8.setText(" 9. Consultar los proyectos de un cliente");
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton8.addActionListener(this::jButtonConsultarLosProyectosDeUnCliente);

        jButton9.setText("10. Registra un integrante a un proyecto");
        jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton9.addActionListener(this::jButtonRegistraUnIntegranteaAUnProyecto);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Menu");

        jButton10.setText("11. Registra el equipo a un proyecto");
        jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton10.addActionListener(this::jButtonRegistraElEquipoAUnProyecto);

        jButton11.setText("12. Elimina un integrante de un proyecto");
        jButton11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton11.addActionListener(this::jButtonEliminaUnIntegranteDeUnProyecto);

        jButton12.setText("13. Genera un archivo de objetos de todos los proyectos");
        jButton12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton12.addActionListener(this::jButtonGeneraUnArchivoDeObjetosDeTodosLosProyectos);

        jButton13.setText("14. Genera un archivo de objetos de todos los clientes");
        jButton13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton13.addActionListener(this::jButtonGeneraUnArchivoDeObjetosDeTodosLosClientes);

        jButton14.setText("15. Lee un archivo de objetos de todos los proyectos");
        jButton14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton14.addActionListener(this::jButtonLeeUnArchivoDeObjetosDeTodosLosProyectos);

        jButton15.setText("16. Lee un archivo de objetos de todos los clientes");
        jButton15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton15.addActionListener(this::jButtonLeeUnArchivoDeObjetosDeTodosLosClientes);

        jLabel2.setText("Consultas");

        jTextAreaResultados.setColumns(20);
        jTextAreaResultados.setRows(5);
        jScrollPane1.setViewportView(jTextAreaResultados);

        jLabel3.setText("Filtro");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSalir)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
    JOptionPane.showMessageDialog(null, "El programa se cerrara, gracias por utilizar nuestro servicios.");
    System.exit(0);
// TODO add your handling code here:
    }//GEN-LAST:event_jButtonSalirActionPerformed

    private void jButtonRegistrarUnProyecto(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarUnProyecto
       UsaProyecto_Registrar_PY frameRegistrar = new UsaProyecto_Registrar_PY();
       frameRegistrar.losProyectos = this.losProyectos;
       frameRegistrar.setVisible(true);

// TODO add your handling code here:
    }//GEN-LAST:event_jButtonRegistrarUnProyecto

    private void jButtonConsultarTodosLosProyectos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarTodosLosProyectos
        jTextAreaResultados.setText(consultarTodosProyectos(losProyectos));        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConsultarTodosLosProyectos

    private void jButtonConsultarLosProyectosEnCurso(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarLosProyectosEnCurso
        jTextAreaResultados.setText(consultarProyectosEnCurso(losProyectos));        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConsultarLosProyectosEnCurso

    private void jButtonConsultarElPrimerProyectoRegistrado(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarElPrimerProyectoRegistrado
        jTextAreaResultados.setText(consultarPrimerProyecto(losProyectos));        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConsultarElPrimerProyectoRegistrado

    private void jButtonConsultarElUltimoProyectoRegistrado(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarElUltimoProyectoRegistrado
        jTextAreaResultados.setText(consultarUltimoProyecto(losProyectos));        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConsultarElUltimoProyectoRegistrado

    private void jButtonConsultarElGerenteDeUnProyecto(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarElGerenteDeUnProyecto
     String filtroCodigo = jTextFieldFiltro.getText().trim();

    if(filtroCodigo.isEmpty()){
        JOptionPane.showMessageDialog(this, "Operación inválida, filtro vacío.",
                "ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
    } else{
        jTextAreaResultados.setText(consultarGerenteProyectoDadoCodigo(losProyectos, filtroCodigo));
    }   // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConsultarElGerenteDeUnProyecto

    private void jButtonConsultarElEquipoDeUnProyecto(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarElEquipoDeUnProyecto
    String filtroCodigo = jTextFieldFiltro.getText().trim();

    if(filtroCodigo.isEmpty()){
        JOptionPane.showMessageDialog(this, "Operación inválida, filtro vacío.",
                "ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
    } else{
        jTextAreaResultados.setText(consultarEquipoProyectoDadoCodigo(losProyectos, filtroCodigo));
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConsultarElEquipoDeUnProyecto

    private void jButtonConsultarLosProyectosDeUnGerente(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarLosProyectosDeUnGerente
        if(jTextFieldFiltro.getText().isBlank() || jTextFieldFiltro.getText().isBlank()){
            JOptionPane.showMessageDialog(null,"Operación inválida, filtro vacío.", "ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
        } else{
                jTextAreaResultados.setText(consultarProyectosDadoNumIDEGerente(losProyectos, jTextFieldFiltro.getText()));
                jTextFieldFiltro.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConsultarLosProyectosDeUnGerente

    private void jButtonConsultarLosProyectosDeUnCliente(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarLosProyectosDeUnCliente
        if(jTextFieldFiltro.getText().isBlank() || jTextFieldFiltro.getText().isBlank()){
            JOptionPane.showMessageDialog(null,"Operación inválida, filtro vacío.", "ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
        } else{
                jTextAreaResultados.setText(consultarProyectosDadoNumIDECliente(losProyectos, jTextFieldFiltro.getText()));
                jTextFieldFiltro.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConsultarLosProyectosDeUnCliente

    private void jButtonRegistraUnIntegranteaAUnProyecto(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistraUnIntegranteaAUnProyecto
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRegistraUnIntegranteaAUnProyecto

    private void jButtonRegistraElEquipoAUnProyecto(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistraElEquipoAUnProyecto
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRegistraElEquipoAUnProyecto

    private void jButtonEliminaUnIntegranteDeUnProyecto(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminaUnIntegranteDeUnProyecto
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEliminaUnIntegranteDeUnProyecto

    private void jButtonGeneraUnArchivoDeObjetosDeTodosLosProyectos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGeneraUnArchivoDeObjetosDeTodosLosProyectos
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonGeneraUnArchivoDeObjetosDeTodosLosProyectos

    private void jButtonGeneraUnArchivoDeObjetosDeTodosLosClientes(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGeneraUnArchivoDeObjetosDeTodosLosClientes
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonGeneraUnArchivoDeObjetosDeTodosLosClientes

    private void jButtonLeeUnArchivoDeObjetosDeTodosLosProyectos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLeeUnArchivoDeObjetosDeTodosLosProyectos
        JOptionPane.showMessageDialog(null,"Leyendo archivo....");
        leerArchivoObjetos(losProyectos);// TODO add your handling code here:
    }//GEN-LAST:event_jButtonLeeUnArchivoDeObjetosDeTodosLosProyectos

    private void jButtonLeeUnArchivoDeObjetosDeTodosLosClientes(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLeeUnArchivoDeObjetosDeTodosLosClientes
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonLeeUnArchivoDeObjetosDeTodosLosClientes

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new UsaProyecto_Menu().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaResultados;
    private javax.swing.JTextField jTextFieldFiltro;
    // End of variables declaration//GEN-END:variables
}
