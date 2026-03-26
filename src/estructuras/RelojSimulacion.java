/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Reloj que mide el tiempo transcurrido en la simulación.
 * @version 1.0
 */
public class RelojSimulacion {
    private double tiempoActual;
    private boolean corriendo;
    private double incremento;
    
    /**
     * Constructor del reloj.
     * @param incremento Cuánto avanza el reloj por unidad
     */
    public RelojSimulacion(double incremento) {
        this.tiempoActual = 0;
        this.corriendo = false;
        this.incremento = incremento;
    }
    
    /**
     * Inicia la simulación.
     */
    public void iniciar() {
        corriendo = true;
    }
    
    /**
     * Detiene la simulación.
     */
    public void detener() {
        corriendo = false;
    }
    
    /**
     * Avanza el reloj una unidad.
     * @return Nuevo tiempo
     */
    public double tick() {
        if (corriendo) {
            tiempoActual += incremento;
        }
        return tiempoActual;
    }
    
    /**
     * Obtiene el tiempo actual.
     */
    public double getTiempo() {
        return tiempoActual;
    }
    
    /**
     * Reinicia el reloj.
     */
    public void reiniciar() {
        tiempoActual = 0;
        corriendo = true;
    }
    
    @Override
    public String toString() {
        return String.format("Tiempo: %.2f", tiempoActual);
    }
}
