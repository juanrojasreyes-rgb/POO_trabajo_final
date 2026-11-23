/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Modelo.Cliente;
import Modelo.Contacto;
import Modelo.Empleado;
import Modelo.Empresa;
import Modelo.PersonaNatural;
import Modelo.Proyecto;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan D
 */
public class UsaProyecto_Registrar_PY extends javax.swing.JFrame {
    
    ArrayList<Proyecto> losProyectos = new ArrayList<>();
    ArrayList<Cliente> losClientes = new ArrayList<>();
    
    public ArrayList<Proyecto> registrarProyecto(ArrayList<Proyecto> losProyectos){
        /*variables para control de entradas invalidas*/
        boolean entradaInvalida = false;
        String msgEntradaInvalida = "Causas:\n";
        
        String codigo = generarCodigoProyecto(losProyectos); /* año-consecutivo, lo asigna el sistema*/
        String titulo = null ;
        LocalDate fechaInicio = null ;
        String unidadDuracion = jComboBoxUnidad.getSelectedItem().toString(); // semanas, meses o años
        byte duracionEstimada = 0; 
        
        /*Validaciones*/
        try {
            titulo = validarTitulo(jTextFieldTitulo.getText());
        } catch (IllegalArgumentException ex) {
            entradaInvalida = true;
            msgEntradaInvalida += "\n" + ex.getMessage();
        }

        try {
            fechaInicio = validarFecha(jTextFieldFechaInicio.getText());
        } catch (IllegalArgumentException ex) {
            entradaInvalida = true;
            msgEntradaInvalida += "\n" + ex.getMessage();
        } 
        
        try {
            duracionEstimada = validarDuracion(unidadDuracion, jTextFieldDuracion.getText());
        } catch (IllegalArgumentException ex) {
            entradaInvalida = true;
            msgEntradaInvalida += "\n" + ex.getMessage();
        }
        
        /*cliente*/
        Cliente cliente = null;
        boolean clienteNuevo = false;
        String idCliente = jTextFieldCliId.getText().trim();
        try {
            idCliente = requerido(jTextFieldCliId.getText(), "El número de identificación del cliente");
            cliente = buscarClienteDadoID(idCliente);   // si el ID ya existe se reutiliza el cliente
            clienteNuevo = (cliente == null);
            if (clienteNuevo) {
                String nombre = requerido(jTextFieldCliNombre.getText(), "El nombre del cliente");
                String ciudad = requerido(jTextFieldCliCiudad.getText(), "La ciudad del cliente");
                if (jComboBoxTipoCliente.getSelectedIndex() == 0) {
                    // Persona natural: teléfono y correo propios
                    cliente = new PersonaNatural(idCliente, nombre, ciudad,
                            validarTelefono(jTextFieldPnTelefono.getText()),
                            validarCorreo(jTextFieldPnCorreo.getText()));
                } else {
                    // Empresa: se identifica a través de un contacto
                    Contacto contacto = new Contacto(
                            requerido(jTextFieldCoNombre.getText(), "El nombre del contacto"),
                            requerido(jTextFieldCoCargo.getText(), "El cargo del contacto"),
                            validarTelefono(jTextFieldCoTelefono.getText()),
                            validarCorreo(jTextFieldCoCorreo.getText()));
                    cliente = new Empresa(contacto, idCliente, nombre, ciudad);
                }
            }
        } catch (IllegalArgumentException ex) {
            entradaInvalida = true;
            msgEntradaInvalida += "\n" + ex.getMessage();
        }
        /* Obtener el gerente del proyecto*/
        Empleado gerente = null;
        try {
            gerente = construirEmpleado(jTextFieldGerId.getText(), jTextFieldGerNombre.getText(),
                    jTextFieldGerTitulo.getText(), jTextFieldGerAnios.getText());
        } catch (IllegalArgumentException ex) {
            entradaInvalida = true;
            msgEntradaInvalida += "\n" + ex.getMessage();
        }
        if (entradaInvalida) {
            JOptionPane.showMessageDialog(this, msgEntradaInvalida, "REGISTRO", JOptionPane.ERROR_MESSAGE);
        } else {
            /*Crear el objeto con estado inicial INICIADO*/
            Proyecto objeto = new Proyecto(codigo, titulo, fechaInicio, unidadDuracion, duracionEstimada, cliente, gerente, "INICIADO");
            /*Agregar el objeto a la colección (y el cliente a la lista de clientes si es nuevo)*/
            losProyectos.add(objeto);
            if (clienteNuevo) {
                losClientes.add(cliente);
            }
            String mensaje = "¡Operación exitosa!.\nProyecto registrado con el código " + codigo + ".";
            if (!clienteNuevo) {
                mensaje += "\nSe reutilizó el cliente ya registrado con ID " + idCliente + ".";
            }
            JOptionPane.showMessageDialog(this, mensaje, "REGISTRO", JOptionPane.INFORMATION_MESSAGE);
        }
        /*Retornar la colección*/    
             return losProyectos;   // no registrar si hay errores
        }    
        
    /* registro y actualización de proyectos */

    /*Generar el código del proyecto: año-consecutivo (el consecutivo inicia en 1 cada año)*/
    public String generarCodigoProyecto(ArrayList<Proyecto> datos) {
        String prefijo = LocalDate.now().getYear() + "-";
        int mayor = 0;
        for (Proyecto objeto : datos) {
            String codigo = objeto.getCodigo();
            if (codigo != null && codigo.startsWith(prefijo)) {
                try {
                    int consecutivo = Integer.parseInt(codigo.substring(prefijo.length()));
                    if (consecutivo > mayor) {
                        mayor = consecutivo;
                    }
                } catch (NumberFormatException ex) {
                    // código con otro formato: no se tiene en cuenta
                }
            }
        }
        return prefijo + String.format("%03d", mayor + 1);
    }
/*apoyos*/
    private Proyecto buscarProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        if (datos == null || codigoProyecto == null) {
            return null;
        }
        String buscado = codigoProyecto.trim();
        for (Proyecto objeto : datos) {
            if (objeto != null && objeto.getCodigo() != null
                    && objeto.getCodigo().equalsIgnoreCase(buscado)) {
                return objeto;
            }
        }
        return null;
    }
    private Cliente buscarClienteDadoID(String numeroIdentificacion) {
        if (numeroIdentificacion == null) {
            return null;
        }
        String buscado = numeroIdentificacion.trim();
        for (Cliente objeto : losClientes) {
            if (objeto != null && objeto.getNumeroIdentificacion() != null
                    && objeto.getNumeroIdentificacion().equalsIgnoreCase(buscado)) {
                return objeto;
            }
        }
        return null;
    }    
    
/* Validaciones: lanzan IllegalArgumentException con el mensaje que se muestra al usuario */

    private static String requerido(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " es obligatorio.");
        }
        return valor.trim();
    }

    private static String validarTitulo(String valor) {
        String titulo = requerido(valor, "El título");
        if (titulo.length() < 20 || titulo.length() > 100) {
            throw new IllegalArgumentException("El título debe tener entre 20 y 100 caracteres (actual: " + titulo.length() + ").");
        }
        return titulo;
    }

    private static LocalDate validarFecha(String valor) {
        try {
            return LocalDate.parse(requerido(valor, "La fecha de inicio"));
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("La fecha de inicio debe ser válida y tener el formato aaaa-mm-dd (ej. 2026-02-15).");
        }
    }

    private static byte validarDuracion(String unidad, String valor) {
        int duracion;
        try {
            duracion = Integer.parseInt(requerido(valor, "La duración estimada"));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("La duración estimada debe ser un número entero.");
        }
        boolean valida;
        String regla;
        switch (unidad) {
            case "semanas":
                valida = duracion >= 1 && duracion <= 3;
                regla = "entre 1 y 3 semanas";
                break;
            case "meses":
                valida = (duracion >= 1 && duracion <= 11) || (duracion >= 13 && duracion <= 23)
                        || (duracion >= 25 && duracion <= 35);
                regla = "entre 1 y 11, 13 y 23, o 25 y 35 meses";
                break;
            default:
                valida = duracion >= 1 && duracion <= 3;
                regla = "entre 1 y 3 años";
                break;
        }
        if (!valida) {
            throw new IllegalArgumentException("Duración inválida: debe estar " + regla + ".");
        }
        return (byte) duracion;
    }

    private static String validarTelefono(String valor) {
        String telefono = requerido(valor, "El número de teléfono");
        String soloDigitos = telefono.replaceAll("\\D", "");
        if (!Pattern.compile("[0-9+\\-\\s()]+").matcher(telefono).matches()
                || soloDigitos.length() < 7 || soloDigitos.length() > 15) {
            throw new IllegalArgumentException("El número de teléfono no es válido (7 a 15 dígitos).");
        }
        return telefono;
    }

    private static String validarCorreo(String valor) {
        String correo = requerido(valor, "El correo electrónico");
        if (!Pattern.compile("[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+").matcher(correo).matches()) {
            throw new IllegalArgumentException("El correo electrónico no es válido (ej. nombre@dominio.com).");
        }
        return correo;
    }

    private static Empleado construirEmpleado(String id, String nombre, String titulo, String anios) {
        String numeroId = requerido(id, "El número de identificación");
        String nombreCompleto = requerido(nombre, "El nombre completo");
        String tituloProfesional = requerido(titulo, "El título profesional");
        int experiencia;
        try {
            experiencia = Integer.parseInt(requerido(anios, "Los años de experiencia"));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Los años de experiencia deben ser un número entero.");
        }
        if (experiencia < 0 || experiencia > 60) {
            throw new IllegalArgumentException("Los años de experiencia deben estar entre 0 y 60.");
        }
        return new Empleado(numeroId, nombreCompleto, tituloProfesional, (byte) experiencia);
    }
  
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UsaProyecto_Registrar_PY.class.getName());

    /*
     * Método para limpiar las entradas del registro (equivalente a inicializarEntradasRegistro
     * de UsaCliente). Se llama al iniciar y después de un registro exitoso.
     */
    public void inicializarEntradasRegistro() {
        jTextFieldTitulo.setText("");
        jTextFieldFechaInicio.setText(LocalDate.now().toString()); // fecha de hoy como sugerencia
        jTextFieldDuracion.setText("");
        jComboBoxUnidad.setSelectedIndex(0);
       
    /***Constructor***/

    } 
    
    /**
     * Creates new form UsaProyecto_Registrar_PY
     */
    public UsaProyecto_Registrar_PY() {
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldTitulo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldFechaInicio = new javax.swing.JTextField();
        jTextFieldDuracion = new javax.swing.JTextField();
        jComboBoxUnidad = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jButtonRegistrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldCliId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldCliNombre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldCliCiudad = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jComboBoxTipoCliente = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldPnTelefono = new javax.swing.JTextField();
        jTextFieldPnCorreo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldCoNombre = new javax.swing.JTextField();
        jTextFieldCoCargo = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldCoTelefono = new javax.swing.JTextField();
        jTextFieldCoCorreo = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextFieldGerId = new javax.swing.JTextField();
        jTextFieldGerNombre = new javax.swing.JTextField();
        jTextFieldGerTitulo = new javax.swing.JTextField();
        jTextFieldGerAnios = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Titulo");

        jLabel2.setText("20 a 100 caracteres");

        jLabel4.setText("Fecha de inicio");

        jLabel3.setText("Duración estimada");

        jComboBoxUnidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "...", "semanas", "Item 3", "Item 4" }));
        jComboBoxUnidad.addActionListener(this::jComboBoxUnidadActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBoxUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel5.setText("Proyecto");

        jButtonRegistrar.setText("Registrar");
        jButtonRegistrar.addActionListener(this::jButtonRegistrarActionPerformed);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setText("Cliente");

        jLabel7.setText("Numero de identificacion");

        jLabel8.setText("Nombre");

        jLabel9.setText("Ciudad");

        jLabel10.setText("Tipo de cliente");

        jComboBoxTipoCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setText("Telefono");

        jLabel12.setText("Correo electronico");

        jLabel13.setText("Nombre completo del contacto");

        jLabel14.setText("Cargo del contacto");

        jLabel15.setText("Telefono del contacto");

        jLabel16.setText("Correo electronico del contacto");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldCliId)
                            .addComponent(jTextFieldCliNombre)
                            .addComponent(jTextFieldCliCiudad)
                            .addComponent(jComboBoxTipoCliente, 0, 225, Short.MAX_VALUE)
                            .addComponent(jTextFieldPnTelefono)
                            .addComponent(jTextFieldPnCorreo)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel16)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldCoNombre)
                            .addComponent(jTextFieldCoCargo)
                            .addComponent(jTextFieldCoTelefono)
                            .addComponent(jTextFieldCoCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(5, 5, 5)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jTextFieldCliNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jTextFieldCliCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxTipoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jTextFieldPnTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jTextFieldPnCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jTextFieldCoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTextFieldCoCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jTextFieldCoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 29, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCoCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)))))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel17.setText("Gerente del proyecto");

        jLabel18.setText("Numero de identificacion");

        jLabel19.setText("Nombre completo");

        jLabel20.setText("Titulo profesional");

        jLabel21.setText("Años de experiencia");

        jTextFieldGerId.setText("jTextField2");

        jTextFieldGerNombre.setText("jTextField3");

        jTextFieldGerTitulo.setText("jTextField4");

        jTextFieldGerAnios.setText("jTextField5");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldGerId, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(jTextFieldGerNombre)
                    .addComponent(jTextFieldGerTitulo)
                    .addComponent(jTextFieldGerAnios))
                .addContainerGap(147, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextFieldGerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextFieldGerNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextFieldGerTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextFieldGerAnios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonRegistrar))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonRegistrar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarActionPerformed
        int antes = losProyectos.size();
        losProyectos = registrarProyecto(losProyectos);
         if (losProyectos.size() > antes) {
         JOptionPane.showMessageDialog(null, "Se guardo.");
         this.dispose();
            }
         
    }//GEN-LAST:event_jButtonRegistrarActionPerformed

    private void jComboBoxUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxUnidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxUnidadActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new UsaProyecto_Registrar_PY().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonRegistrar;
    private javax.swing.JComboBox<String> jComboBoxTipoCliente;
    private javax.swing.JComboBox<String> jComboBoxUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextFieldCliCiudad;
    private javax.swing.JTextField jTextFieldCliId;
    private javax.swing.JTextField jTextFieldCliNombre;
    private javax.swing.JTextField jTextFieldCoCargo;
    private javax.swing.JTextField jTextFieldCoCorreo;
    private javax.swing.JTextField jTextFieldCoNombre;
    private javax.swing.JTextField jTextFieldCoTelefono;
    private javax.swing.JTextField jTextFieldDuracion;
    private javax.swing.JTextField jTextFieldFechaInicio;
    private javax.swing.JTextField jTextFieldGerAnios;
    private javax.swing.JTextField jTextFieldGerId;
    private javax.swing.JTextField jTextFieldGerNombre;
    private javax.swing.JTextField jTextFieldGerTitulo;
    private javax.swing.JTextField jTextFieldPnCorreo;
    private javax.swing.JTextField jTextFieldPnTelefono;
    private javax.swing.JTextField jTextFieldTitulo;
    // End of variables declaration//GEN-END:variables
}
