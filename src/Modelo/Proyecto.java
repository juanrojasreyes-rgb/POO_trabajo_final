/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.io.Serializable;
import java.util.ArrayList ;
import java.time.LocalDate;


/**
 *
 * @author Gabriela imbachii
 */
public class Proyecto implements Serializable {
        
    private String codigo ;
    private String titulo ;
    private LocalDate fechaInicio ;
    private String unidadDuracion ;
    private byte duracionEstimada ;
    private Cliente suCliente ;
    private Empleado suGerente ;
    private ArrayList<Empleado> suEquipo ;
    private String Estado ;

    public Proyecto(String codigo, String titulo, LocalDate fechaInicio, String unidadDuracion, byte duracionEstimada, Cliente suCliente, Empleado suGerente, ArrayList<Empleado> suEquipo, String Estado) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.unidadDuracion = unidadDuracion;
        this.duracionEstimada = duracionEstimada;
        this.suCliente = suCliente;
        this.suGerente = suGerente;
        this.suEquipo = suEquipo;
        this.Estado = Estado;
    }

    public Proyecto(String codigo, String titulo, LocalDate fechaInicio, String unidadDuracion, byte duracionEstimada, Cliente suCliente, Empleado suGerente, String Estado) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.unidadDuracion = unidadDuracion;
        this.duracionEstimada = duracionEstimada;
        this.suCliente = suCliente;
        this.suGerente = suGerente;
        this.Estado = Estado;
        this.suEquipo = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public String getUnidadDuracion() {
        return unidadDuracion;
    }

    public byte getDuracionEstimada() {
        return duracionEstimada;
    }

    public Cliente getSuCliente() {
        return suCliente;
    }

    public Empleado getSuGerente() {
        return suGerente;
    }

    public ArrayList<Empleado> getSuEquipo() {
        return suEquipo;
    }

    public String getEstado() {
        return Estado;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setUnidadDuracion(String unidadDuracion) {
        this.unidadDuracion = unidadDuracion;
    }

    public void setDuracionEstimada(byte duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public void setSuCliente(Cliente suCliente) {
        this.suCliente = suCliente;
    }

    public void setSuGerente(Empleado suGerente) {
        this.suGerente = suGerente;
    }

    public void setSuEquipo(ArrayList<Empleado> suEquipo) {
        this.suEquipo = suEquipo;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    @Override
    public String toString() {
        return "Proyecto{" 
                + "codigo=" + codigo 
                + ", titulo=" + titulo 
                + ", fechaInicio=" + fechaInicio 
                + ", unidadDuracion=" + unidadDuracion 
                + ", duracionEstimada=" + duracionEstimada 
                + ", suCliente=" + suCliente 
                + ", suGerente=" + suGerente 
                + ", suEquipo=" + suEquipo 
                + ", Estado=" + Estado 
                + '}';
    }
    
    public LocalDate determinarFechaFinalizacion () {
        if (fechaInicio == null || unidadDuracion == null ){ 
            return null ;   
        }
        switch (unidadDuracion.trim().toLowerCase()) {
            case "semanas" :
                    return fechaInicio.plusWeeks(duracionEstimada) ;
                case "meses" :
                    return fechaInicio.plusMonths(duracionEstimada) ;
                case "años" :
                    return fechaInicio.plusYears(duracionEstimada);
                default:
                    return fechaInicio;
                            
            }
            
        }
    public void agregarIntegrante (Empleado nuevoIntegrante) {
            if (this.suEquipo == null) {
                this.suEquipo = new ArrayList<>();
                }
            if (nuevoIntegrante != null) {
                this.suEquipo.add(nuevoIntegrante); 
            }
    }
    
    public Empleado eliminarIntegrante(String numeroIdentificacion) {
        if (this.suEquipo == null || numeroIdentificacion == null){
            return null;
        }
        String idBuscado = numeroIdentificacion.trim();
        for (int i = 0; i < this.suEquipo.size(); i++) {
            Empleado integrante = this.suEquipo.get(i);
            if (integrante.getNumeroIdentificacion().equalsIgnoreCase(numeroIdentificacion.trim())) {
                return this.suEquipo.remove(i);
                
            }
        }
        return null;
    }
    
}