/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Implementación de un montículo binario (min-heap) usando arreglo.
 * @param <T> Tipo de elementos (deben ser Comparables)
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
     * Obtiene el elemento en una posición específica (índice 1 = raíz).
     */
    public T obtener(int indice) {
        if (indice < 1 || indice > tamanio) {
            return null;
        }
        return elementos[indice];
    }
    
    /**
     * Obtiene todos los elementos como arreglo (para visualización).
     */
    @SuppressWarnings("unchecked")
    public T[] getElementos() {
        // Crear un arreglo de tipo T
        T[] resultado = (T[]) new Comparable[tamanio + 1];
        for (int i = 1; i <= tamanio; i++) {
            resultado[i] = elementos[i];
        }
        return resultado;
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
     * Representación como árbol (para visualización).
     */
    public String toTreeString() {
        if (tamanio == 0) {
            return "Cola vacía";
        }
        StringBuilder sb = new StringBuilder();
        toTreeString(1, "", true, sb);
        return sb.toString();
    }

    
    private void toTreeString(int index, String prefijo, boolean esUltimo, StringBuilder sb) {
        if (index > tamanio) return;

        // Agregar el nodo actual
        sb.append(prefijo);
        sb.append(esUltimo ? "└── " : "├── ");

        // Obtener el elemento en la posición index
        T elemento = elementos[index];
        if (elemento != null) {
            sb.append(elemento.toString());
        } else {
            sb.append("null");
        }
        sb.append("\n");

        // Calcular índices de hijos
        int izquierdo = index * 2;
        int derecho = index * 2 + 1;

        // Determinar si hay hijos
        boolean tieneIzquierdo = izquierdo <= tamanio;
        boolean tieneDerecho = derecho <= tamanio;

        if (tieneIzquierdo || tieneDerecho) {
            String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");

            if (tieneIzquierdo) {
                toTreeString(izquierdo, nuevoPrefijo, !tieneDerecho, sb);
            }
            if (tieneDerecho) {
                toTreeString(derecho, nuevoPrefijo, true, sb);
            }
        }
    }
}
