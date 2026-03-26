/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Clase principal que coordina la simulación de la cola de impresión.
 * @version 1.0
 */
public class Simulador {
    private TablaHash<String, Usuario> usuarios;
    private MonticuloBinario<RegistroImpresion> colaImpresion;
    private RelojSimulacion reloj;
    private TablaHash<String, Lista<RegistroImpresion>> documentosEnColaPorUsuario;
    
    /**
     * Constructor del simulador.
     */
    public Simulador() {
        this.usuarios = new TablaHash<>();
        this.colaImpresion = new MonticuloBinario<>();
        this.reloj = new RelojSimulacion(1.0);
        this.documentosEnColaPorUsuario = new TablaHash<>();
        this.reloj.iniciar();
    }
    
    /**
     * Agrega un nuevo usuario.
     */
    public boolean agregarUsuario(String nombre, String tipo) {
        if (!usuarios.contiene(nombre)) {
            Usuario usuario = new Usuario(nombre, tipo);
            usuarios.put(nombre, usuario);
            documentosEnColaPorUsuario.put(nombre, new Lista<>());
            return true;
        }
        return false;
    }
    
    /**
     * Elimina un usuario (solo sus documentos no enviados).
     */
    public boolean eliminarUsuario(String nombre) {
        if (usuarios.contiene(nombre)) {
            Usuario usuario = usuarios.get(nombre);
            
            // Eliminar documentos creados que no están en cola
            Lista<Documento> docsCreados = usuario.getDocumentosCreados();
            for (int i = docsCreados.getTamaño() - 1; i >= 0; i--) {
                Documento doc = docsCreados.obtener(i);
                if (!doc.isEnCola()) {
                    docsCreados.eliminarPorIndice(i);
                }
            }
            
            usuarios.remove(nombre);
            documentosEnColaPorUsuario.remove(nombre);
            return true;
        }
        return false;
    }
    
    /**
     * Obtiene un usuario por su nombre.
     */
    public Usuario getUsuario(String nombre) {
        return usuarios.get(nombre);
    }
    
    /**
     * Obtiene todos los usuarios.
     */
    public Lista<Usuario> getUsuarios() {
        Lista<Usuario> lista = new Lista<>();
        Lista<String> claves = usuarios.getClaves();
        System.out.println("Claves en tabla hash: " + claves.getTamaño());

        for (int i = 0; i < claves.getTamaño(); i++) {
            String clave = claves.obtener(i);
            Usuario u = usuarios.get(clave);
            if (u != null) {
                lista.agregar(u);
            }
        }
        return lista;
    }
    
    /**
     * Crea un documento para un usuario.
     */
    public boolean crearDocumento(String nombreUsuario, String nombreDoc, int tamanio, String tipoDoc) {
        Usuario usuario = usuarios.get(nombreUsuario);
        if (usuario != null) {
            usuario.crearDocumento(nombreDoc, tamanio, tipoDoc);
            return true;
        }
        return false;
    }
    
    /**
     * Elimina un documento no enviado de un usuario.
     */
    public boolean eliminarDocumento(String nombreUsuario, String nombreDoc) {
        Usuario usuario = usuarios.get(nombreUsuario);
        if (usuario != null) {
            return usuario.eliminarDocumento(nombreDoc);
        }
        return false;
    }
    
    /**
     * Envía un documento a la cola de impresión.
     */
    public boolean enviarACola(String nombreUsuario, String nombreDoc, boolean prioritario) {
        Usuario usuario = usuarios.get(nombreUsuario);
        if (usuario == null) return false;
        
        // Buscar el documento
        Lista<Documento> docs = usuario.getDocumentosCreados();
        Documento documento = null;
        for (int i = 0; i < docs.getTamaño(); i++) {
            Documento d = docs.obtener(i);
            if (d.getNombre().equals(nombreDoc) && !d.isEnCola()) {
                documento = d;
                break;
            }
        }
        
        if (documento == null) return false;
        
        // Avanzar el reloj
        double tiempoActual = reloj.tick();
        
        // Calcular etiqueta de tiempo con prioridad
        double factor = prioritario ? usuario.getFactorPrioridad() : 1.0;
        RegistroImpresion registro = new RegistroImpresion(documento, tiempoActual, factor);
        
        // Insertar en cola
        colaImpresion.insertar(registro);
        
        // Registrar en tabla hash para búsqueda rápida
        Lista<RegistroImpresion> docsUsuario = documentosEnColaPorUsuario.get(nombreUsuario);
        if (docsUsuario == null) {
            docsUsuario = new Lista<>();
            documentosEnColaPorUsuario.put(nombreUsuario, docsUsuario);
        }
        docsUsuario.agregar(registro);
        
        // Marcar documento como enviado
        usuario.marcarDocumentoEnCola(documento);
        
        return true;
    }
    
    /**
     * Libera la impresora (elimina el mínimo de la cola).
     * @return El registro impreso
     */
    public RegistroImpresion liberarImpresora() {
        if (colaImpresion.estaVacio()) {
            return null;
        }
        
        RegistroImpresion impreso = colaImpresion.eliminarMin();
        
        // Eliminar de la tabla hash de seguimiento
        Lista<String> usuariosNombres = usuarios.getClaves();
        for (int i = 0; i < usuariosNombres.getTamaño(); i++) {
            String nombre = usuariosNombres.obtener(i);
            Lista<RegistroImpresion> docsUsuario = documentosEnColaPorUsuario.get(nombre);
            if (docsUsuario != null) {
                for (int j = 0; j < docsUsuario.getTamaño(); j++) {
                    RegistroImpresion reg = docsUsuario.obtener(j);
                    if (reg.getNombreDocumento().equals(impreso.getNombreDocumento()) &&
                        reg.getTiempoEtiqueta() == impreso.getTiempoEtiqueta()) {
                        docsUsuario.eliminarPorIndice(j);
                        break;
                    }
                }
            }
        }
        
        return impreso;
    }
    
    /**
     * Elimina un documento específico de la cola (cambiando su prioridad a -infinito).
     */
    public boolean eliminarDeCola(String nombreUsuario, String nombreDoc) {
        Lista<RegistroImpresion> docsUsuario = documentosEnColaPorUsuario.get(nombreUsuario);
        if (docsUsuario == null) return false;
        
        RegistroImpresion registro = null;
        for (int i = 0; i < docsUsuario.getTamaño(); i++) {
            RegistroImpresion r = docsUsuario.obtener(i);
            if (r.getNombreDocumento().equals(nombreDoc)) {
                registro = r;
                break;
            }
        }
        
        if (registro == null) return false;
        
        double tiempoOriginal = registro.getTiempoEtiqueta();
        registro.setTiempoEtiqueta(-Double.MAX_VALUE); // "Infinito negativo"
        
        docsUsuario.eliminar(registro);
        
        return true;
    }
    
    /**
     * Obtiene la cola de impresión como arreglo (para vista secuencial).
     */
    public RegistroImpresion[] getColaComoArreglo() {
        int tamanio = colaImpresion.getTamanio();
        RegistroImpresion[] resultado = new RegistroImpresion[tamanio + 1];

        for (int i = 1; i <= tamanio; i++) {
            resultado[i] = colaImpresion.obtener(i);
        }

        return resultado;
    }
    
    /**
     * Obtiene la representación como árbol de la cola.
     */
    public String getColaComoArbol() {
        if (colaImpresion.estaVacio()) {
            return "Cola vacía";
        }
        return colaImpresion.toTreeString();
    }
    
    /**
     * Obtiene los documentos de un usuario que están en cola.
     */
    public Lista<RegistroImpresion> getDocumentosEnColaDe(String nombreUsuario) {
        Lista<RegistroImpresion> docs = documentosEnColaPorUsuario.get(nombreUsuario);
        return docs != null ? docs : new Lista<>();
    }
    
    /**
     * Obtiene el tiempo actual de simulación.
     */
    public double getTiempoActual() {
        return reloj.getTiempo();
    }
    
    /**
     * Avanza el reloj manualmente.
     */
    public void avanzarReloj() {
        reloj.tick();
    }
    
    public TablaHash<String, Usuario> getTablaUsuarios() {
        return usuarios;
    }
}
