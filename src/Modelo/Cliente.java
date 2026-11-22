/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;

/**
 *
 * @author Juan D and Gabriela imbachi
 */
public abstract class Cliente implements Serializable { 
    
    protected String numeroIdentificacion;
    protected String nombre;
    protected String nombreCiudad;

    /* Constructor*/
    
    public Cliente(String numeroIdentificacion, String nombre, String nombreCiudad) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombre = nombre;
        this.nombreCiudad = nombreCiudad;
    }
    
    /* get y set*/

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    /* toString*/
    
    @Override
    public String toString() {
        return "Cliente{" 
                + "numeroIdentificacion=" + numeroIdentificacion 
                + ", nombre=" + nombre 
                + ", nombreCiudad=" + nombreCiudad 
                + '}';
    }
    
    
    
    
}//fin class
