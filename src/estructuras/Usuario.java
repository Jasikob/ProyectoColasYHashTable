/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
/**
 * Representa un usuario del sistema con su tipo y prioridad.
 * @version 1.0
 */
public class Usuario {
    private String nombre;
    private String tipo; // prioridad_alta, prioridad_media, prioridad_baja
    private int prioridad; // 1: alta, 2: media, 3: baja
    private Lista<Documento> documentosCreados;
    private Lista<Documento> documentosEnCola;
    
    /**
     * Constructor de Usuario.
     * @param nombre Nombre del usuario
     * @param tipo Tipo de usuario
     */
    public Usuario(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.prioridad = calcularPrioridad(tipo);
        this.documentosCreados = new Lista<>();
        this.documentosEnCola = new Lista<>();
    }
    
    /**
     * Calcula el valor numérico de prioridad según el tipo.
     */
    private int calcularPrioridad(String tipo) {
        switch(tipo.toLowerCase()) {
            case "prioridad_alta": return 1;
            case "prioridad_media": return 2;
            case "prioridad_baja": return 3;
            default: return 2; // Prioridad media por defecto
        }
    }
    
    /**
     * Obtiene el factor de ajuste de tiempo según prioridad.
     * @return Factor multiplicador (menor = más prioritario)
     */
    public double getFactorPrioridad() {
        switch(prioridad) {
            case 1: return 0.5;  // Alta prioridad: reduce tiempo a la mitad
            case 2: return 1.0;  // Prioridad media: tiempo normal
            case 3: return 2.0;  // Baja prioridad: duplica tiempo
            default: return 1.0;
        }
    }
    
    /**
     * Crea un nuevo documento para este usuario.
     * @param nombreDoc Nombre del documento
     * @param tamanio Tamaño en páginas
     * @param tipoDoc Tipo de documento
     */
    public void crearDocumento(String nombreDoc, int tamanio, String tipoDoc) {
        Documento doc = new Documento(nombreDoc, tamanio, tipoDoc, this);
        documentosCreados.agregar(doc);
    }
    
    /**
     * Elimina un documento que no ha sido enviado a cola.
     * @param nombreDoc Nombre del documento
     * @return true si se eliminó
     */
    public boolean eliminarDocumento(String nombreDoc) {
        for (int i = 0; i < documentosCreados.getTamaño(); i++) {
            Documento doc = documentosCreados.obtener(i);
            if (doc.getNombre().equals(nombreDoc) && !doc.isEnCola()) {
                return documentosCreados.eliminar(doc);
            }
        }
        return false;
    }
    
    // Getters y Setters
    public String getNombre() { 
        return nombre; 
    }
    public String getTipo() { 
        return tipo; 
    }
    public int getPrioridad() { 
        return prioridad; 
    }
    public Lista<Documento> getDocumentosCreados() { 
        return documentosCreados; 
    }
    public Lista<Documento> getDocumentosEnCola() { 
        return documentosEnCola; 
    }
    
    /**
     * Marca un documento como enviado a la cola.
     */
    public void marcarDocumentoEnCola(Documento doc) {
        doc.setEnCola(true);
        documentosEnCola.agregar(doc);
    }
    
    @Override
    public String toString() {
        return nombre + " (" + tipo + ")";
    }
}
