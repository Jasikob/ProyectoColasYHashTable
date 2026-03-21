/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Nodo para lista doblemente enlazada.
 * Cada nodo contiene un dato y referencias al nodo anterior y siguiente.
 * 
 * @param <T> Tipo de dato almacenado en el nodo
 */
public class Nodo<T> {
    private T dato;
    private Nodo<T> anterior;
    private Nodo<T> siguiente;
    
    /**
     * Constructor que crea un nodo con un dato específico.
     * 
     * @param dato El dato a almacenar en el nodo
     */
    public Nodo(T dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }
    
    /**
     * Obtiene el dato almacenado en el nodo.
     * 
     * @return El dato del nodo
     */
    public T getDato() { 
        return dato; 
    }
    
    /**
     * Establece el dato almacenado en el nodo.
     * 
     * @param dato El nuevo dato
     */
    public void setDato(T dato) { 
        this.dato = dato; 
    }
    
    /**
     * Obtiene el nodo anterior.
     * 
     * @return El nodo anterior, o null si es el primero
     */
    public Nodo<T> getAnterior() { 
        return anterior; 
    }
    
    /**
     * Establece el nodo anterior.
     * 
     * @param anterior El nuevo nodo anterior
     */
    public void setAnterior(Nodo<T> anterior) { 
        this.anterior = anterior; 
    }
    
    /**
     * Obtiene el nodo siguiente.
     * 
     * @return El nodo siguiente, o null si es el último
     */
    public Nodo<T> getSiguiente() { 
        return siguiente; 
    }
    
    /**
     * Establece el nodo siguiente.
     * 
     * @param siguiente El nuevo nodo siguiente
     */
    public void setSiguiente(Nodo<T> siguiente) { 
        this.siguiente = siguiente; 
    }
    
    /**
     * Retorna una representación en cadena del nodo.
     * 
     * @return El dato convertido a String
     */
    @Override
    public String toString() {
        return dato.toString();
    }
}

