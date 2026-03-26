/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package interfaz;
import estructuras.CargadorArchivo;
import estructuras.Documento;
import estructuras.Simulador;
import estructuras.Usuario;
import estructuras.RegistroImpresion;
import javax.swing.JOptionPane;
import estructuras.Lista;
import java.awt.GridLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Simon
 */
public class VentanaPrincipal extends javax.swing.JFrame {
    private Simulador simulador;
    private DefaultListModel<String> modeloUsuarios;
    private DefaultComboBoxModel<String> modeloDocumentos;
    private String usuarioSeleccionado;
    private DefaultComboBoxModel<String> modeloDocumentosEnCola;
    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        initComponents();
        inicializarPersonalizado();
    }
    
    private void inicializarPersonalizado() {
        simulador = new Simulador();
        modeloUsuarios = new DefaultListModel<>();
        listaUsuarios.setModel(modeloUsuarios);

        modeloDocumentos = new DefaultComboBoxModel<>();
        cmbDocumentosUsuario.setModel(modeloDocumentos);
        
        listaUsuarios.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                if (!evt.getValueIsAdjusting()) {
                    usuarioSeleccionado = listaUsuarios.getSelectedValue();
                    actualizarCombosDocumentos();
                }
            }
        });
        
        rbVistaSecuencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarVistaCola();
            }
        });
    
        rbVistaArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarVistaCola();
            }
        });
        
        modeloDocumentosEnCola = new DefaultComboBoxModel<>();
        cmbDocumentosEnCola.setModel(modeloDocumentosEnCola);

        listaUsuarios.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                if (!evt.getValueIsAdjusting()) {
                    usuarioSeleccionado = listaUsuarios.getSelectedValue();
                    actualizarCombosDocumentos();
                    actualizarCombosDocumentosEnCola(); 
                }
            }
        });

        rbVistaSecuencia.addActionListener(e -> actualizarVistaCola());
        rbVistaArbol.addActionListener(e -> actualizarVistaCola());
        
        cargarArchivoInicial();
        actualizarInfo();
        actualizarVistaCola();

    }
    
    private void cargarArchivoInicial() {
        if (CargadorArchivo.cargarArchivoPorDefecto(simulador.getTablaUsuarios(), this)) {
            actualizarListaUsuarios();
            txtCola.append("Archivo por defecto cargado.\n");
        }
    }
    
    private void actualizarListaUsuarios() {
        modeloUsuarios.removeAllElements();
        Lista<Usuario> usuarios = simulador.getUsuarios();
        System.out.println("Total usuarios en simulador: " + usuarios.getTamaño());

        for (int i = 0; i < usuarios.getTamaño(); i++) {
            Usuario u = usuarios.obtener(i);
            System.out.println("Agregando usuario: " + u.getNombre());
            modeloUsuarios.addElement(u.getNombre());  // Solo el nombre
        }
    }
    
    private void actualizarCombosDocumentos() {
        modeloDocumentos.removeAllElements();
        if (usuarioSeleccionado != null) {
            String nombreUsuario = usuarioSeleccionado.split(" \\(")[0];
            Usuario u = simulador.getUsuario(nombreUsuario);
            if (u != null) {
                Lista<Documento> docs = u.getDocumentosCreados();
                for (int i = 0; i < docs.getTamaño(); i++) {
                    Documento d = docs.obtener(i);
                    if (!d.isEnCola()) {
                        modeloDocumentos.addElement(d.getNombre());
                    }
                }
            }
        }
    }
    
    private void actualizarVistaCola() {
        txtCola.setText("");

        if (rbVistaSecuencia.isSelected()) {
            txtCola.append("COLA DE IMPRESIÓN (VISTA SECUENCIA) \n\n");
            RegistroImpresion[] cola = simulador.getColaComoArreglo();

            if (cola == null || cola.length <= 1) {
                txtCola.append("Cola vacía\n");
            } else {
                int contador = 1;
                boolean hayAlguno = false;
                for (int i = 1; i < cola.length; i++) {
                    if (cola[i] != null) {
                        txtCola.append(contador + ". " + cola[i].toString() + "\n");
                        contador++;
                        hayAlguno = true;
                    }
                }
                if (!hayAlguno) {
                    txtCola.append("Cola vacía\n");
                }
            }
        } else if (rbVistaArbol.isSelected()) {
            txtCola.append("COLA DE IMPRESIÓN (VISTA ÁRBOL) \n\n");
            String arbol = simulador.getColaComoArbol();
            txtCola.append(arbol);
        }

        // Actualizar los documentos en cola del usuario seleccionado
        actualizarCombosDocumentosEnCola();
    }
    
    private void actualizarInfo() {
        lblReloj.setText("Tiempo: " + String.format("%.2f", simulador.getTiempoActual()));

        int enCola = simulador.getColaComoArreglo().length - 1;
        lblStats.setText("Usuarios: " + simulador.getUsuarios().getTamaño() + 
                         " | En cola: " + enCola);
    }
    
    private void actualizarCombosDocumentosEnCola() {
        modeloDocumentosEnCola.removeAllElements();
        if (usuarioSeleccionado != null && !usuarioSeleccionado.isEmpty()) {
            Lista<RegistroImpresion> docsEnCola = simulador.getDocumentosEnColaDe(usuarioSeleccionado);
            for (int i = 0; i < docsEnCola.getTamaño(); i++) {
                RegistroImpresion reg = docsEnCola.obtener(i);
                modeloDocumentosEnCola.addElement(reg.getNombreDocumento());
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoVistas = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblReloj = new javax.swing.JLabel();
        lblArchivo = new javax.swing.JLabel();
        lblStats = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaUsuarios = new javax.swing.JList<>();
        btnCargarUsuarios = new javax.swing.JButton();
        btnAgregarUsuario = new javax.swing.JButton();
        btnEliminarUsuario = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        rbVistaSecuencia = new javax.swing.JRadioButton();
        rbVistaArbol = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCola = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cmbDocumentosUsuario = new javax.swing.JComboBox<>();
        chkPrioritario = new javax.swing.JCheckBox();
        btnCrearDocumento = new javax.swing.JButton();
        btnEnviarACola = new javax.swing.JButton();
        btnEliminarDocumento = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnAvanzarReloj = new javax.swing.JButton();
        btnLiberarImpresora = new javax.swing.JButton();
        btnEliminarDeCola = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        cmbDocumentosEnCola = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Proyecto Colas y Hash Table");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, -1));

        lblReloj.setText("Tiempo: 0.00");
        jPanel1.add(lblReloj, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        lblArchivo.setText("Archivo: Ninguno");
        jPanel1.add(lblArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, -1, -1));

        lblStats.setText("Usuarios: 0 | Documentos en cola: 0");
        jPanel1.add(lblStats, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 60));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        listaUsuarios.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaUsuarios);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        btnCargarUsuarios.setText("Cargar Usuarios");
        btnCargarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarUsuariosActionPerformed(evt);
            }
        });
        jPanel2.add(btnCargarUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        btnAgregarUsuario.setText("Agregar Usuario");
        btnAgregarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarUsuarioActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgregarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

        btnEliminarUsuario.setText("Eliminar Usuario");
        btnEliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUsuarioActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jLabel2.setText("Usuarios");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 240, 300));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        grupoVistas.add(rbVistaSecuencia);
        rbVistaSecuencia.setText("Vista Secuencia");
        rbVistaSecuencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbVistaSecuenciaActionPerformed(evt);
            }
        });
        jPanel3.add(rbVistaSecuencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        grupoVistas.add(rbVistaArbol);
        rbVistaArbol.setText("Vista Árbol");
        rbVistaArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbVistaArbolActionPerformed(evt);
            }
        });
        jPanel3.add(rbVistaArbol, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        txtCola.setColumns(20);
        txtCola.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtCola.setRows(5);
        jScrollPane2.setViewportView(txtCola);

        jScrollPane3.setViewportView(jScrollPane2);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 280, 100));

        jLabel3.setText("Documentos En Cola");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 310, 230));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Documentos");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        cmbDocumentosUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbDocumentosUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDocumentosUsuarioActionPerformed(evt);
            }
        });
        jPanel4.add(cmbDocumentosUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        chkPrioritario.setText("Enviar como prioritario");
        jPanel4.add(chkPrioritario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        btnCrearDocumento.setText("Crear Documento");
        btnCrearDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearDocumentoActionPerformed(evt);
            }
        });
        jPanel4.add(btnCrearDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        btnEnviarACola.setText("Enviar Cola");
        btnEnviarACola.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarAColaActionPerformed(evt);
            }
        });
        jPanel4.add(btnEnviarACola, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        btnEliminarDocumento.setText("Eliminar Documento");
        btnEliminarDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarDocumentoActionPerformed(evt);
            }
        });
        jPanel4.add(btnEliminarDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 240, 210));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("Controles");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, -1));

        btnAvanzarReloj.setText("Avanzar Reloj");
        btnAvanzarReloj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvanzarRelojActionPerformed(evt);
            }
        });
        jPanel5.add(btnAvanzarReloj, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        btnLiberarImpresora.setText("Liberar Impresora");
        btnLiberarImpresora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiberarImpresoraActionPerformed(evt);
            }
        });
        jPanel5.add(btnLiberarImpresora, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, -1, -1));

        btnEliminarDeCola.setText("Eliminar De Cola");
        btnEliminarDeCola.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarDeColaActionPerformed(evt);
            }
        });
        jPanel5.add(btnEliminarDeCola, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel5.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, -1, -1));

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel5.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, -1, -1));

        cmbDocumentosEnCola.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel5.add(cmbDocumentosEnCola, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 400, 310, 210));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarUsuariosActionPerformed
        // TODO add your handling code here:
        if (CargadorArchivo.cargarUsuarios(simulador.getTablaUsuarios(), this)) {
            actualizarListaUsuarios();
            actualizarInfo();
            txtCola.append("Usuarios cargados exitosamente.\n");
        }
    }//GEN-LAST:event_btnCargarUsuariosActionPerformed

    private void btnAgregarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarUsuarioActionPerformed
        // TODO add your handling code here:
        JTextField txtNombre = new JTextField();
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"prioridad_alta", "prioridad_media", "prioridad_baja"});
    
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Tipo:"));
        panel.add(comboTipo);
    
        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Usuario",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
        if (result == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String tipo = (String) comboTipo.getSelectedItem();
        
            if (!nombre.isEmpty() && simulador.agregarUsuario(nombre, tipo)) {
                actualizarListaUsuarios();
                actualizarInfo();
                txtCola.append("Usuario agregado: " + nombre + " (" + tipo + ")\n");
            } else {
                JOptionPane.showMessageDialog(this, "El usuario ya existe o nombre inválido");
            }
        }
    }//GEN-LAST:event_btnAgregarUsuarioActionPerformed

    private void btnEliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUsuarioActionPerformed
        // TODO add your handling code here:
        usuarioSeleccionado = listaUsuarios.getSelectedValue();

        System.out.println("Usuario seleccionado: " + usuarioSeleccionado);

        if (usuarioSeleccionado == null || usuarioSeleccionado.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un usuario de la lista", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = simulador.getUsuario(usuarioSeleccionado);
        System.out.println("Usuario encontrado en simulador: " + (usuario != null));

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, 
                "El usuario no existe en el sistema", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar usuario " + usuarioSeleccionado + "?\n" +
            "Se eliminarán sus documentos no enviados.",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminado = simulador.eliminarUsuario(usuarioSeleccionado);
            System.out.println("Usuario eliminado: " + eliminado);

            if (eliminado) {
                actualizarListaUsuarios();
                actualizarInfo();
                txtCola.append("Usuario eliminado: " + usuarioSeleccionado + "\n");
                usuarioSeleccionado = null;
                modeloDocumentos.removeAllElements();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se pudo eliminar el usuario.\n" +
                    "Asegúrese de que el usuario no tenga documentos en cola.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarUsuarioActionPerformed

    private void rbVistaSecuenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbVistaSecuenciaActionPerformed
        // TODO add your handling code here:
        actualizarVistaCola();
    }//GEN-LAST:event_rbVistaSecuenciaActionPerformed

    private void rbVistaArbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbVistaArbolActionPerformed
        // TODO add your handling code here:
        actualizarVistaCola();
    }//GEN-LAST:event_rbVistaArbolActionPerformed

    private void cmbDocumentosUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDocumentosUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDocumentosUsuarioActionPerformed

    private void btnCrearDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearDocumentoActionPerformed
        // TODO add your handling code here:
        if (usuarioSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario primero");
            return;
        }
    
        JTextField txtNombre = new JTextField();
        JTextField txtTamanio = new JTextField("1");
        JTextField txtTipo = new JTextField("pdf");
    
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Nombre documento:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Tamaño (páginas):"));
        panel.add(txtTamanio);
        panel.add(new JLabel("Tipo:"));
        panel.add(txtTipo);
    
        int result = JOptionPane.showConfirmDialog(this, panel, "Crear Documento",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                int tamanio = Integer.parseInt(txtTamanio.getText().trim());
                String tipo = txtTipo.getText().trim();

                if (!nombre.isEmpty() && tamanio > 0) {
                    if (simulador.crearDocumento(usuarioSeleccionado, nombre, tamanio, tipo)) {
                        actualizarCombosDocumentos();
                        txtCola.append("Documento creado: " + nombre + 
                            " (" + tamanio + " páginas, " + tipo + ")\n");
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo crear el documento");
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tamaño inválido");
            }
        }
    }//GEN-LAST:event_btnCrearDocumentoActionPerformed

    private void btnEnviarAColaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarAColaActionPerformed
        // TODO add your handling code here:
        if (usuarioSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }
    
        String docSeleccionado = (String) cmbDocumentosUsuario.getSelectedItem();
        if (docSeleccionado == null || docSeleccionado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay documentos disponibles para enviar");
            return;
        }

        boolean prioritario = chkPrioritario.isSelected();

        if (simulador.enviarACola(usuarioSeleccionado, docSeleccionado, prioritario)) {
            actualizarCombosDocumentos();
            actualizarVistaCola();
            actualizarInfo();
            txtCola.append("Documento enviado a cola: " + docSeleccionado + 
                (prioritario ? " (PRIORITARIO)" : "") + "\n");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo enviar el documento");
        }
    }//GEN-LAST:event_btnEnviarAColaActionPerformed

    private void btnEliminarDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarDocumentoActionPerformed
        // TODO add your handling code here:
        if (usuarioSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }
    
        String docSeleccionado = (String) cmbDocumentosUsuario.getSelectedItem();
        if (docSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un documento");
            return;
        }

        if (simulador.eliminarDocumento(usuarioSeleccionado, docSeleccionado)) {
            actualizarCombosDocumentos();
            txtCola.append("Documento eliminado: " + docSeleccionado + "\n");
        }
    }//GEN-LAST:event_btnEliminarDocumentoActionPerformed

    private void btnAvanzarRelojActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvanzarRelojActionPerformed
        // TODO add your handling code here:
        simulador.avanzarReloj();
        actualizarInfo();
        txtCola.append("Reloj avanzado. Tiempo: " + String.format("%.2f", simulador.getTiempoActual()) + "\n");
    }//GEN-LAST:event_btnAvanzarRelojActionPerformed

    private void btnEliminarDeColaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarDeColaActionPerformed
        // TODO add your handling code here:
        usuarioSeleccionado = listaUsuarios.getSelectedValue();
    
        if (usuarioSeleccionado == null || usuarioSeleccionado.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un usuario de la lista", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener el documento seleccionado del combo box de documentos en cola
        String docSeleccionado = (String) cmbDocumentosEnCola.getSelectedItem();

        if (docSeleccionado == null || docSeleccionado.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El usuario no tiene documentos en cola para eliminar", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar de la cola el documento '" + docSeleccionado + "'\n" +
            "del usuario " + usuarioSeleccionado + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (simulador.eliminarDeCola(usuarioSeleccionado, docSeleccionado)) {
                actualizarVistaCola();
                actualizarInfo();
                actualizarCombosDocumentosEnCola();
                txtCola.append("Documento eliminado de cola: " + docSeleccionado + "\n");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se pudo eliminar el documento de la cola", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarDeColaActionPerformed

    private void btnLiberarImpresoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiberarImpresoraActionPerformed
        // TODO add your handling code here:
        RegistroImpresion impreso = simulador.liberarImpresora();
        if (impreso != null) {
            actualizarVistaCola();
            actualizarInfo();
            txtCola.append("IMPRESO: " + impreso.getNombreDocumento() + 
                " [t=" + String.format("%.2f", impreso.getTiempoEtiqueta()) + "]\n");
        } else {
            JOptionPane.showMessageDialog(this, "No hay documentos en la cola");
        }
    }//GEN-LAST:event_btnLiberarImpresoraActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if (CargadorArchivo.guardarUsuarios(simulador.getTablaUsuarios(), this)) {
            txtCola.append("Usuarios guardados exitosamente.\n");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        txtCola.setText("");
    }//GEN-LAST:event_btnLimpiarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarUsuario;
    private javax.swing.JButton btnAvanzarReloj;
    private javax.swing.JButton btnCargarUsuarios;
    private javax.swing.JButton btnCrearDocumento;
    private javax.swing.JButton btnEliminarDeCola;
    private javax.swing.JButton btnEliminarDocumento;
    private javax.swing.JButton btnEliminarUsuario;
    private javax.swing.JButton btnEnviarACola;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLiberarImpresora;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JCheckBox chkPrioritario;
    private javax.swing.JComboBox<String> cmbDocumentosEnCola;
    private javax.swing.JComboBox<String> cmbDocumentosUsuario;
    private javax.swing.ButtonGroup grupoVistas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblArchivo;
    private javax.swing.JLabel lblReloj;
    private javax.swing.JLabel lblStats;
    private javax.swing.JList<String> listaUsuarios;
    private javax.swing.JRadioButton rbVistaArbol;
    private javax.swing.JRadioButton rbVistaSecuencia;
    private javax.swing.JTextArea txtCola;
    // End of variables declaration//GEN-END:variables
}
