/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Modelo.Empleado;
import Modelo.Proyecto;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan D
 */
public class UsaProyecto_Registrar_Int extends javax.swing.JFrame {
    
    ArrayList<Proyecto> losProyectos = new ArrayList<>();
    
        String codigo ;
        String titulo ;
        LocalDate fechaInicio = null ;
        String unidadDuracion ;
        byte duracionEstimada = 0 ;
        String Estado ;

        boolean entradaInvalida = false;
        String msgEntradaInvalida = "¡Operación fallida!.\nEntrada inválida.";
        
        private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UsaProyecto_Registrar_Int.class.getName());
    /* + registrarIntegranteProyectoDadoCodigo(datos : ArrayList<Proyecto>, codigoProyecto : String) : String*/
    public String registrarIntegranteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        // 1. Verificar si la lista de proyectos está llena
        if (datos == null || datos.isEmpty()) {
            return "No hay proyectos registrados en el sistema (la lista de proyectos está vacía).";
        }

        if (codigoProyecto == null || codigoProyecto.trim().isEmpty()) {
            return "Error: Debe proporcionar un código de proyecto válido.";
        }

        // 2. Recorrer la lista de proyectos y buscar el proyecto que cumpla la condición
        Proyecto proyectoEncontrado = buscarProyectoPorCodigo(datos, codigoProyecto.trim());
        if (proyectoEncontrado == null) {
            return "No existe un proyecto registrado con el código: " + codigoProyecto;
        }

        // 3. Solicitar y validar los datos del integrante (Empleado)
        Empleado nuevoIntegrante = solicitarDatosEmpleadoInteractivo(proyectoEncontrado.getSuEquipo());
        if (nuevoIntegrante == null) {
            return "Operación cancelada o datos del integrante inválidos.";
        }

        // 4. Agregar el integrante al equipo del proyecto y finalizar
        proyectoEncontrado.agregarIntegrante(nuevoIntegrante);
        return "Integrante registrado exitosamente en el proyecto [" + proyectoEncontrado.getCodigo() + " - "
                + proyectoEncontrado.getTitulo() + "]:\n" + nuevoIntegrante.toString();
    }    
    
    // ==================== MÉTODOS AUXILIARES Y DE VALIDACIÓN (PUNTO 3) ====================

    private Proyecto buscarProyectoPorCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        for (Proyecto p : datos) {
            if (p.getCodigo().equalsIgnoreCase(codigoProyecto)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Verifica si ya existe un integrante con el mismo número de identificación dentro de un equipo.
     */
    public boolean existeIntegranteEnEquipo(ArrayList<Empleado> equipo, String numeroIdentificacion) {
        if (equipo == null || numeroIdentificacion == null) {
            return false;
        }
        for (Empleado emp : equipo) {
            if (emp.getNumeroIdentificacion().equalsIgnoreCase(numeroIdentificacion.trim())) {
                return true;
            }
        }
        return false;
    }
    private Empleado solicitarDatosEmpleadoInteractivo(ArrayList<Empleado> equipoActual) {
        String id = JOptionPane.showInputDialog(this, "Número de identificación del empleado:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Validación: El número de identificación es obligatorio.");
            return null;
        }
        id = id.trim();

        if (existeIntegranteEnEquipo(equipoActual, id)) {
            JOptionPane.showMessageDialog(this, "Validación: Ya existe un integrante con la identificación '" + id + "' en este equipo.");
            return null;
        }

        String nombre = JOptionPane.showInputDialog(this, "Nombre completo del empleado:");
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Validación: El nombre completo es obligatorio.");
            return null;
        }

        String titulo = JOptionPane.showInputDialog(this, "Título profesional del empleado:");
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Validación: El título profesional es obligatorio.");
            return null;
        }

        String expStr = JOptionPane.showInputDialog(this, "Años de experiencia (0 a 60):");
        if (expStr == null || expStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Validación: Debe ingresar los años de experiencia.");
            return null;
        }

        byte aniosExp;
        try {
            aniosExp = Byte.parseByte(expStr.trim());
            if (aniosExp < 0) {
                JOptionPane.showMessageDialog(this, "Validación: Los años de experiencia no pueden ser negativos.");
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Validación: Los años de experiencia deben ser un número entero válido (tipo byte).");
            return null;
        }

        return new Empleado(id, nombre.trim(), titulo.trim(), aniosExp);
    }    
        
    
    /**
     * Creates new form UsaProyecto_Registrar_Int
     */
    public UsaProyecto_Registrar_Int() {
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        java.awt.EventQueue.invokeLater(() -> new UsaProyecto_Registrar_Int().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
