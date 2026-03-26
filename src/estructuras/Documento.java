/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Representa un documento creado por un usuario.
 * @version 1.0
 */
public class Documento {
    private String nombre;
    private int tamanio; // en páginas
    private String tipo; // pdf, doc, txt, etc.
    private Usuario propietario;
    private boolean enCola;
    
    /**
     * Constructor de Documento.
     */
    public Documento(String nombre, int tamanio, String tipo, Usuario propietario) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.tipo = tipo;
        this.propietario = propietario;
        this.enCola = false;
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public int getTamanio() { return tamanio; }
    public String getTipo() { return tipo; }
    public Usuario getPropietario() { return propietario; }
    public boolean isEnCola() { return enCola; }
    public void setEnCola(boolean enCola) { this.enCola = enCola; }
    
    @Override
    public String toString() {
        return nombre + " (" + tamanio + " páginas, " + tipo + ")";
    }
}
