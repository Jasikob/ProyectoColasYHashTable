/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Implementación de un montículo binario (min-heap) usando arreglo.
 * @param <T> Tipo de elementos (deben ser Comparables)
 * @author Equipo
 * @version 1.0
 */
public class MonticuloBinario<T extends Comparable<T>> {
    private T[] elementos;
    private int tamanio;
    private static final int CAPACIDAD_INICIAL = 10;
    
    /**
     * Constructor del montículo.
     */
    @SuppressWarnings("unchecked")
    public MonticuloBinario() {
        elementos = (T[]) new Comparable[CAPACIDAD_INICIAL];
        tamanio = 0;
    }
    
    /**
     * Inserta un elemento en el montículo.
     * @param elemento Elemento a insertar
     */
    public void insertar(T elemento) {
        if (tamanio == elementos.length - 1) {
            expandirCapacidad();
        }
        
        // Colocar al final y flotar
        tamanio++;
        elementos[tamanio] = elemento;
        flotar(tamanio);
    }
    
    /**
     * Elimina y retorna el elemento mínimo (raíz).
     * @return Elemento mínimo
     */
    public T eliminarMin() {
        if (estaVacio()) {
            return null;
        }
        
        T min = elementos[1];
        elementos[1] = elementos[tamanio];
        tamanio--;
        hundir(1);
        
        return min;
    }
    
    /**
     * Obtiene el mínimo sin eliminarlo.
     * @return Elemento mínimo
     */
    public T verMin() {
        return estaVacio() ? null : elementos[1];
    }
    
    /**
     * Flota un elemento desde una posición hacia arriba.
     */
    private void flotar(int posicion) {
        T elemento = elementos[posicion];
        
        while (posicion > 1 && elemento.compareTo(elementos[posicion / 2]) < 0) {
            elementos[posicion] = elementos[posicion / 2];
            posicion = posicion / 2;
        }
        
        elementos[posicion] = elemento;
    }
    
    /**
     * Hunde un elemento desde una posición hacia abajo.
     */
    private void hundir(int posicion) {
        T elemento = elementos[posicion];
        
        while (posicion * 2 <= tamanio) {
            int hijo = posicion * 2;
            
            // Elegir el hijo menor
            if (hijo < tamanio && elementos[hijo + 1].compareTo(elementos[hijo]) < 0) {
                hijo++;
            }
            
            if (elemento.compareTo(elementos[hijo]) <= 0) {
                break;
            }
            
            elementos[posicion] = elementos[hijo];
            posicion = hijo;
        }
        
        elementos[posicion] = elemento;
    }
    
    /**
     * Expande la capacidad del arreglo cuando es necesario.
     */
    @SuppressWarnings("unchecked")
    private void expandirCapacidad() {
        T[] nuevo = (T[]) new Comparable[elementos.length * 2];
        for (int i = 0; i < elementos.length; i++) {
            nuevo[i] = elementos[i];
        }
        elementos = nuevo;
    }
    
    /**
     * Verifica si el montículo está vacío.
     */
    public boolean estaVacio() {
        return tamanio == 0;
    }
    
    /**
     * Obtiene el tamaño actual.
     */
    public int getTamanio() {
        return tamanio;
    }
    
    /**
     * Obtiene todos los elementos como arreglo (para visualización).
     */
    public T[] getElementos() {
        @SuppressWarnings("unchecked")
        T[] copia = (T[]) new Comparable[tamanio + 1];
        for (int i = 1; i <= tamanio; i++) {
            copia[i] = elementos[i];
        }
        return copia;
    }
    
    /**
     * Representación como árbol (para visualización).
     */
    public String toTreeString() {
        StringBuilder sb = new StringBuilder();
        toTreeString(1, "", true, sb);
        return sb.toString();
    }
    
    private void toTreeString(int index, String prefijo, boolean esUltimo, StringBuilder sb) {
        if (index > tamanio) return;
        
        sb.append(prefijo);
        sb.append(esUltimo ? "└── " : "├── ");
        sb.append(elementos[index]).append("\n");
        
        int izquierdo = index * 2;
        int derecho = index * 2 + 1;
        
        if (izquierdo <= tamanio || derecho <= tamanio) {
            String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");
            
            if (izquierdo <= tamanio) {
                toTreeString(izquierdo, nuevoPrefijo, derecho > tamanio, sb);
            }
            if (derecho <= tamanio) {
                toTreeString(derecho, nuevoPrefijo, true, sb);
            }
        }
    }
}
