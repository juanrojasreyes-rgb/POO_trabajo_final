/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Juan D
 */
public final class PersonaNatural extends Cliente {
    
    private String numeroTelefono;
    private String correoElectronico;
    
    /* conector*/
    
    public PersonaNatural(String numeroIdentificacion, String nombre, String nombreCiudad,
                          String numeroTelefono, String correoElectronico) {
        super(numeroIdentificacion, nombre, nombreCiudad); /* <<supper>> */
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
    }
    
    /* set y get*/

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    
    /* toString*/ 

    @Override
    public String toString() {
        return "Tipo de cliente: Persona Natural"
                +super.toString()
                +"PersonaNatural{" + "numeroTelefono=" + numeroTelefono 
                + ", correoElectronico=" + correoElectronico 
                + '}';
    }
    
    
    
}
