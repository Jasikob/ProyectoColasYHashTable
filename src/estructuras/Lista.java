/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Implementación de una lista doblemente enlazada.
 * Permite operaciones de inserción, eliminación y búsqueda de elementos.
 * 
 * @param <T> Tipo de elementos almacenados en la lista
 */
public class Lista<T> {
    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamaño;
    
    /**
     * Constructor que crea una lista vacía.
     */
    public Lista() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }
    
    /**
     * Agrega un elemento al final de la lista.
     * 
     * @param dato El dato a agregar
     */
    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.setSiguiente(nuevo);
            nuevo.setAnterior(cola);
            cola = nuevo;
        }
        tamaño++;
    }
    
    /**
     * Agrega un elemento al inicio de la lista.
     * 
     * @param dato El dato a agregar
     */
    public void agregarAlInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            nuevo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevo);
            cabeza = nuevo;
        }
        tamaño++;
    }
    
    /**
     * Elimina la primera ocurrencia de un dato en la lista.
     * 
     * @param dato El dato a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    public boolean eliminar(T dato) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                if (actual == cabeza && actual == cola) {
                    cabeza = null;
                    cola = null;
                } else if (actual == cabeza) {
                    cabeza = cabeza.getSiguiente();
                    cabeza.setAnterior(null);
                } else if (actual == cola) {
                    cola = cola.getAnterior();
                    cola.setSiguiente(null);
                } else {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                }
                tamaño--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
    
    /**
     * Elimina el elemento en una posición específica.
     * 
     * @param indice La posición del elemento a eliminar (0 = primero)
     * @return El dato eliminado, o null si el índice es inválido
     */
    public T eliminarPorIndice(int indice) {
        if (indice < 0 || indice >= tamaño) return null;
        
        Nodo<T> actual;
        if (indice < tamaño / 2) {
            actual = cabeza;
            for (int i = 0; i < indice; i++) actual = actual.getSiguiente();
        } else {
            actual = cola;
            for (int i = tamaño - 1; i > indice; i--) actual = actual.getAnterior();
        }
        
        T dato = actual.getDato();
        
        if (actual == cabeza && actual == cola) {
            cabeza = null;
            cola = null;
        } else if (actual == cabeza) {
            cabeza = cabeza.getSiguiente();
            cabeza.setAnterior(null);
        } else if (actual == cola) {
            cola = cola.getAnterior();
            cola.setSiguiente(null);
        } else {
            actual.getAnterior().setSiguiente(actual.getSiguiente());
            actual.getSiguiente().setAnterior(actual.getAnterior());
        }
        
        tamaño--;
        return dato;
    }
    
    /**
     * Verifica si la lista contiene un dato específico.
     * 
     * @param dato El dato a buscar
     * @return true si el dato existe, false en caso contrario
     */
    public boolean contiene(T dato) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(dato)) return true;
            actual = actual.getSiguiente();
        }
        return false;
    }
    
    /**
     * Obtiene un elemento por su posición.
     * 
     * @param indice La posición del elemento (0 = primero)
     * @return El dato en esa posición, o null si el índice es inválido
     */
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamaño) return null;
        
        Nodo<T> actual;
        if (indice < tamaño / 2) {
            actual = cabeza;
            for (int i = 0; i < indice; i++) actual = actual.getSiguiente();
        } else {
            actual = cola;
            for (int i = tamaño - 1; i > indice; i--) actual = actual.getAnterior();
        }
        return actual.getDato();
    }
    
    /**
     * Obtiene el número de elementos en la lista.
     * 
     * @return Tamaño de la lista
     */
    public int getTamaño() { 
        return tamaño; 
    }
    
    /**
     * Verifica si la lista está vacía.
     * 
     * @return true si la lista está vacía, false en caso contrario
     */
    public boolean estaVacia() { 
        return cabeza == null; 
    }
    
    /**
     * Obtiene el primer elemento de la lista sin eliminarlo.
     * 
     * @return El primer elemento, o null si la lista está vacía
     */
    public T getPrimero() { 
        return cabeza != null ? cabeza.getDato() : null; 
    }
    
    /**
     * Obtiene el último elemento de la lista sin eliminarlo.
     * 
     * @return El último elemento, o null si la lista está vacía
     */
    public T getUltimo() { 
        return cola != null ? cola.getDato() : null; 
    }
    
    /**
     * Convierte la lista a un arreglo de objetos.
     * 
     * @return Arreglo con todos los elementos de la lista
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] arreglo = (T[]) new Object[tamaño];
        Nodo<T> actual = cabeza;
        int i = 0;
        while (actual != null) {
            arreglo[i++] = actual.getDato();
            actual = actual.getSiguiente();
        }
        return arreglo;
    }
    
    /**
     * Elimina todos los elementos de la lista.
     */
    public void vaciar() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }
    
    /**
     * Retorna una representación en cadena de la lista.
     * 
     * @return String con los elementos de la lista
     */
    @Override
    public String toString() {
        if (estaVacia()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> actual = cabeza;
        while (actual != null) {
            sb.append(actual.getDato());
            if (actual.getSiguiente() != null) sb.append(", ");
            actual = actual.getSiguiente();
        }
        sb.append("]");
        return sb.toString();
    }
}

