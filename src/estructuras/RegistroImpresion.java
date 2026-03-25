/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Registro que se almacena en la cola de impresión.
 * No contiene información del propietario.
 * @author Equipo
 * @version 1.0
 */
public class RegistroImpresion implements Comparable<RegistroImpresion> {
    private String nombreDocumento;
    private int tamanio;
    private String tipo;
    private double tiempoEtiqueta; // Tiempo ajustado por prioridad
    
    /**
     * Constructor de RegistroImpresion.
     * @param documento Documento original
     * @param tiempoBase Tiempo del reloj al enviar
     * @param factorPrioridad Factor de ajuste por prioridad
     */
    public RegistroImpresion(Documento documento, double tiempoBase, double factorPrioridad) {
        this.nombreDocumento = documento.getNombre();
        this.tamanio = documento.getTamanio();
        this.tipo = documento.getTipo();
        this.tiempoEtiqueta = tiempoBase * factorPrioridad;
    }
    
    /**
     * Constructor para crear registros modificados (para eliminación).
     */
    public RegistroImpresion(String nombreDoc, int tamanio, String tipo, double tiempo) {
        this.nombreDocumento = nombreDoc;
        this.tamanio = tamanio;
        this.tipo = tipo;
        this.tiempoEtiqueta = tiempo;
    }
    
    // Getters
    public String getNombreDocumento() { return nombreDocumento; }
    public int getTamanio() { return tamanio; }
    public String getTipo() { return tipo; }
    public double getTiempoEtiqueta() { return tiempoEtiqueta; }
    
    /**
     * Cambia la etiqueta de tiempo (para eliminar documentos).
     */
    public void setTiempoEtiqueta(double tiempo) {
        this.tiempoEtiqueta = tiempo;
    }
    
    /**
     * Implementación de Comparable para el montículo.
     * Menor tiempo = mayor prioridad.
     */
    @Override
    public int compareTo(RegistroImpresion otro) {
        return Double.compare(this.tiempoEtiqueta, otro.tiempoEtiqueta);
    }
    
    @Override
    public String toString() {
        return nombreDocumento + " [t=" + String.format("%.2f", tiempoEtiqueta) + "]";
    }
}
