package ui.controllers;

import model.Rol;
import model.Usuario;
import services.UsuarioService;
import ui.views.GestionUsuariosView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class GestionUsuariosController {
    private final GestionUsuariosView view;
    private final UsuarioService usuarioService;
    private final TableRowSorter<DefaultTableModel> sorter;

    public GestionUsuariosController(GestionUsuariosView view, UsuarioService usuarioService) {
        this.view = view;
        this.usuarioService = usuarioService;
        this.sorter = new TableRowSorter<>(view.getTableModel());
        this.view.getTablaUsuarios().setRowSorter(sorter);

        
        this.view.addCrearListener(e -> crearUsuario());
        this.view.addEditarListener(e -> editarUsuario());
        this.view.addDesactivarListener(e -> cambiarEstadoUsuario());
        this.view.addCerrarListener(e -> view.dispose());
        this.view.addBuscarListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarTabla();
            }
        });

        cargarUsuariosEnTabla();
    }

    public void iniciar() {
        view.setVisible(true);
    }

    private void cargarUsuariosEnTabla() {
        view.getTableModel().setRowCount(0); 
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        for (Usuario u : usuarios) {
            Object[] rowData = {
                u.getId(),
                u.getNombre(),
                u.getRol().toString(),
                u.isActivo() ? "Activo" : "Inactivo"
            };
            view.getTableModel().addRow(rowData);
        }
    }

    private void filtrarTabla() {
        String texto = view.getTextoBusqueda();
        if (texto.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        }
    }

    private void crearUsuario() {
        String id = JOptionPane.showInputDialog(view, "ID del nuevo usuario:", "Crear Usuario", JOptionPane.PLAIN_MESSAGE);
        if (id == null || id.trim().isEmpty()) return;

        if (usuarioService.buscarUsuarioPorId(id) != null) {
            view.mostrarMensaje("El ID de usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = JOptionPane.showInputDialog(view, "Nombre:", "Crear Usuario", JOptionPane.PLAIN_MESSAGE);
        String contrasena = JOptionPane.showInputDialog(view, "Contraseña:", "Crear Usuario", JOptionPane.PLAIN_MESSAGE);
        Rol[] roles = {Rol.MEDICO, Rol.AUXILIAR};
        Rol rolSeleccionado = (Rol) JOptionPane.showInputDialog(view, "Seleccione el rol:", "Crear Usuario", JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

        if (nombre != null && contrasena != null && rolSeleccionado != null) {
            usuarioService.crearUsuario(id, nombre, contrasena, rolSeleccionado);
            cargarUsuariosEnTabla();
            view.mostrarMensaje("Usuario creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editarUsuario() {
        int filaModelo = obtenerFilaSeleccionada();
        if (filaModelo == -1) return;

        String idUsuario = (String) view.getTableModel().getValueAt(filaModelo, 0);
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);

        if (usuario.getRol() == Rol.ADMINISTRADOR) {
             view.mostrarMensaje("El usuario Administrador no puede ser editado.", "Acción no permitida", JOptionPane.ERROR_MESSAGE);
             return;
        }

        String nuevoNombre = JOptionPane.showInputDialog(view, "Nuevo nombre:", usuario.getNombre());
        if (nuevoNombre != null) {
            usuario.setNombre(nuevoNombre);
            usuarioService.actualizarUsuario(usuario);
            cargarUsuariosEnTabla();
            view.mostrarMensaje("Usuario actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cambiarEstadoUsuario() {
        int filaModelo = obtenerFilaSeleccionada();
        if (filaModelo == -1) return;

        String idUsuario = (String) view.getTableModel().getValueAt(filaModelo, 0);
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);

        if (usuario.getRol() == Rol.ADMINISTRADOR) {
             view.mostrarMensaje("El usuario Administrador no puede ser desactivado.", "Acción no permitida", JOptionPane.ERROR_MESSAGE);
             return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, "¿Desea cambiar el estado de este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            usuarioService.cambiarEstadoUsuario(idUsuario);
            cargarUsuariosEnTabla();
        }
    }

    private int obtenerFilaSeleccionada() {
        int filaVista = view.getTablaUsuarios().getSelectedRow();
        if (filaVista == -1) {
            view.mostrarMensaje("Por favor, seleccione un usuario de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        return view.getTablaUsuarios().convertRowIndexToModel(filaVista);
    }
}