/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Implementación de una tabla hash para búsqueda O(1).
 * @param <K> Tipo de la clave
 * @param <V> Tipo del valor
 * @version 1.0
 */
public class TablaHash<K, V> {
    
    /**
     * Clase interna para las entradas de la tabla.
     */
    private static class Entrada<K, V> {
        K clave;
        V valor;
        boolean activo; // Para manejar eliminaciones
        
        Entrada(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
            this.activo = true;
        }
    }
    
    @SuppressWarnings("unchecked")
    private Entrada<K, V>[] tabla;
    private int tamanio;
    private int capacidad;
    private static final double FACTOR_CARGA = 0.75;
    
    /**
     * Constructor con capacidad inicial.
     */
    @SuppressWarnings("unchecked")
    public TablaHash(int capacidadInicial) {
        this.capacidad = capacidadInicial;
        // SOLUCIÓN: Crear arreglo de Object y luego convertirlo
        this.tabla = (Entrada<K, V>[]) new Entrada[capacidad];
        this.tamanio = 0;
    }
    
    /**
     * Constructor por defecto.
     */
    public TablaHash() {
        this(10);
    }
    
    /**
     * Función hash simple.
     */
    private int hash(K clave) {
        return Math.abs(clave.hashCode()) % capacidad;
    }
    
    /**
     * Inserta o actualiza un valor.
     */
    public void put(K clave, V valor) {
        if ((double) tamanio / capacidad >= FACTOR_CARGA) {
            rehash();
        }
        
        int indice = hash(clave);
        int original = indice;
        
        // Búsqueda lineal para encontrar posición
        do {
            if (tabla[indice] == null || !tabla[indice].activo) {
                tabla[indice] = new Entrada<>(clave, valor);
                tamanio++;
                return;
            }
            
            if (tabla[indice].clave.equals(clave) && tabla[indice].activo) {
                tabla[indice].valor = valor; // Actualizar
                return;
            }
            
            indice = (indice + 1) % capacidad;
        } while (indice != original);
    }
    
    /**
     * Obtiene un valor por su clave.
     */
    public V get(K clave) {
        int indice = hash(clave);
        int original = indice;
        
        do {
            if (tabla[indice] == null) {
                return null;
            }
            
            if (tabla[indice].activo && tabla[indice].clave.equals(clave)) {
                return tabla[indice].valor;
            }
            
            indice = (indice + 1) % capacidad;
        } while (indice != original);
        
        return null;
    }
    
    /**
     * Elimina una entrada por su clave.
     */
    public V remove(K clave) {
        int indice = hash(clave);
        int original = indice;
        
        do {
            if (tabla[indice] == null) {
                return null;
            }
            
            if (tabla[indice].activo && tabla[indice].clave.equals(clave)) {
                V valor = tabla[indice].valor;
                tabla[indice].activo = false;
                tamanio--;
                return valor;
            }
            
            indice = (indice + 1) % capacidad;
        } while (indice != original);
        
        return null;
    }
    
    /**
     * Verifica si existe una clave.
     */
    public boolean contiene(K clave) {
        return get(clave) != null;
    }
    
    /**
     * Rehashing: expande la tabla cuando el factor de carga es alto.
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        Entrada<K, V>[] old = tabla;
        capacidad *= 2;
        tabla = (Entrada<K, V>[]) new Entrada[capacidad];
        tamanio = 0;
        
        for (Entrada<K, V> entrada : old) {
            if (entrada != null && entrada.activo) {
                put(entrada.clave, entrada.valor);
            }
        }
    }
    
    /**
     * Obtiene todas las claves.
     */
    public Lista<K> getClaves() {
        Lista<K> claves = new Lista<>();
        for (Entrada<K, V> entrada : tabla) {
            if (entrada != null && entrada.activo) {
                claves.agregar(entrada.clave);
            }
        }
        return claves;
    }
    
    /**
     * Obtiene el tamaño actual.
     */
    public int getTamanio() {
        return tamanio;
    }
    
    /**
     * Verifica si la tabla está vacía.
     */
    public boolean estaVacia() {
        return tamanio == 0;
    }
    
    /**
     * Limpia la tabla.
     */
    public void limpiar() {
        @SuppressWarnings("unchecked")
        Entrada<K, V>[] nueva = (Entrada<K, V>[]) new Entrada[capacidad];
        tabla = nueva;
        tamanio = 0;
    }
}