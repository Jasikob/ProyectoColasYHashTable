/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

import javax.swing.*;
import java.io.*;

/**
 * Controlador para cargar usuarios desde archivo CSV.
 * @version 1.0
 */
public class CargadorArchivo {
    private static File ultimoArchivo;
    private static boolean cambiosPendientes = false;
    
    /**
     * Carga usuarios desde un archivo CSV.
     * @param tablaHash Tabla donde almacenar los usuarios
     * @param parent Ventana padre para diálogos
     * @return true si se cargó correctamente
     */
    public static boolean cargarUsuarios(TablaHash<String, Usuario> tablaHash, JFrame parent) {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Seleccionar archivo de usuarios CSV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Archivos CSV (*.csv)", "csv"));
        
        int resultado = fileChooser.showOpenDialog(parent);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            ultimoArchivo = archivo;
            return procesarArchivoUsuarios(archivo, tablaHash, parent);
        }
        
        return false;
    }
    
    /**
     * Procesa el archivo CSV de usuarios.
     */
    private static boolean procesarArchivoUsuarios(File archivo, TablaHash<String, Usuario> tablaHash, JFrame parent) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int lineasLeidas = 0;
            int usuariosCargados = 0;
            
            while ((linea = br.readLine()) != null) {
                lineasLeidas++;
                
                if (linea.trim().isEmpty()) continue;
                
                // Saltar encabezados si existen
                if (lineasLeidas == 1 && linea.contains("usuario")) {
                    continue;
                }
                
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    String nombreUsuario = partes[0].trim();
                    String tipo = partes[1].trim();
                    
                    if (!tablaHash.contiene(nombreUsuario)) {
                        Usuario usuario = new Usuario(nombreUsuario, tipo);
                        tablaHash.put(nombreUsuario, usuario);
                        usuariosCargados++;
                    }
                }
            }
            
            JOptionPane.showMessageDialog(parent,
                "Usuarios cargados exitosamente:\n" +
                "Archivo: " + archivo.getName() + "\n" +
                "Usuarios cargados: " + usuariosCargados,
                "Carga exitosa",
                JOptionPane.INFORMATION_MESSAGE);
            
            cambiosPendientes = false;
            return true;
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent,
                "Error al leer el archivo:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Guarda los usuarios en un archivo CSV.
     */
    public static boolean guardarUsuarios(TablaHash<String, Usuario> tablaHash, JFrame parent) {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Guardar archivo de usuarios");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Archivos CSV (*.csv)", "csv"));
        
        if (ultimoArchivo != null) {
            fileChooser.setSelectedFile(ultimoArchivo);
        }
        
        int resultado = fileChooser.showSaveDialog(parent);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            if (!archivo.getName().toLowerCase().endsWith(".csv")) {
                archivo = new File(archivo.getAbsolutePath() + ".csv");
            }
            
            return guardarArchivoUsuarios(archivo, tablaHash, parent);
        }
        
        return false;
    }
    
    /**
     * Guarda el archivo de usuarios.
     */
    private static boolean guardarArchivoUsuarios(File archivo, TablaHash<String, Usuario> tablaHash, JFrame parent) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("usuario,tipo");
            
            Lista<String> claves = tablaHash.getClaves();
            for (int i = 0; i < claves.getTamaño(); i++) {
                String clave = claves.obtener(i);
                Usuario u = tablaHash.get(clave);
                pw.println(u.getNombre() + "," + u.getTipo());
            }
            
            ultimoArchivo = archivo;
            cambiosPendientes = false;
            
            JOptionPane.showMessageDialog(parent,
                "Usuarios guardados exitosamente:\n" + archivo.getName(),
                "Guardado exitoso",
                JOptionPane.INFORMATION_MESSAGE);
            
            return true;
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent,
                "Error al guardar el archivo:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Carga archivo por defecto si existe.
     */
    public static boolean cargarArchivoPorDefecto(TablaHash<String, Usuario> tablaHash, JFrame parent) {
        File archivo = new File("usuarios.csv");
        if (archivo.exists()) {
            ultimoArchivo = archivo;
            return procesarArchivoUsuarios(archivo, tablaHash, parent);
        }
        return false;
    }
    
    public static void marcarCambios() { cambiosPendientes = true; }
    public static boolean hayCambiosPendientes() { return cambiosPendientes; }
    public static String getNombreUltimoArchivo() { 
        return ultimoArchivo != null ? ultimoArchivo.getName() : "Ninguno"; 
    }
}
