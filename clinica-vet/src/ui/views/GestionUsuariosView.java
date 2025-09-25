package ui.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class GestionUsuariosView extends JFrame {
    private JTable tablaUsuarios;
    private DefaultTableModel tableModel;
    private JButton btnCrear, btnEditar, btnDesactivar, btnCerrar;
    private JTextField txtBuscar;

    public GestionUsuariosView() {
        setTitle("Gestión de Usuarios");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        JPanel panelBusqueda = new JPanel(new BorderLayout(5, 5));
        panelBusqueda.setBorder(new TitledBorder("Buscar Usuarios"));
        txtBuscar = new JTextField();
        panelBusqueda.add(new JLabel("Filtrar por nombre o ID:"), BorderLayout.WEST);
        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);
        contentPane.add(panelBusqueda, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Rol", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaUsuarios = new JTable(tableModel);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnCrear = new JButton("Crear");
        btnEditar = new JButton("Editar");
        btnDesactivar = new JButton("Activar/Desactivar");
        btnCerrar = new JButton("Cerrar");
        
        btnCrear.setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
        btnEditar.setIcon(UIManager.getIcon("Actions.warningIcon"));
        btnCerrar.setIcon(UIManager.getIcon("InternalFrame.closeIcon"));

        panelBotones.add(btnCrear);
        panelBotones.add(btnEditar);
        panelBotones.add(btnDesactivar);
        panelBotones.add(Box.createHorizontalStrut(20));
        panelBotones.add(btnCerrar);
        contentPane.add(panelBotones, BorderLayout.SOUTH);
    }

    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTablaUsuarios() { return tablaUsuarios; }
    public String getTextoBusqueda() { return txtBuscar.getText(); }
    public void addBuscarListener(KeyListener listener) { txtBuscar.addKeyListener(listener); }
    public void addCrearListener(ActionListener listener) { btnCrear.addActionListener(listener); }
    public void addEditarListener(ActionListener listener) { btnEditar.addActionListener(listener); }
    public void addDesactivarListener(ActionListener listener) { btnDesactivar.addActionListener(listener); }
    public void addCerrarListener(ActionListener listener) { btnCerrar.addActionListener(listener); }
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}