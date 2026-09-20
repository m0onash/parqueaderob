package org.unilibre.gui;

import org.unilibre.Datos.TipoIdentificacion;
import org.unilibre.operaciones.Parqueadero;

import javax.swing.*;
import java.awt.*;

/**
 * Interfaz Gráfica para la Gestión del Parqueadero de Bicicletas
 */
public class VentanaParqueadero extends JFrame {

    // Instancia principal de nuestro sistema
    private final Parqueadero parqueadero = new Parqueadero();

    // Campos de texto para el ingreso
    private JTextField txtIdPropietarioIngreso;
    private JTextField txtSerial;
    private JTextField txtColor;
    private JComboBox<TipoIdentificacion> cbTipoId;


    // Campos de texto para la salida
    private JTextField txtIdPropietarioSalida;

    // Área de consola y botones
    private JTextArea txtAreaConsola;
    private JButton btnIngresar, btnRegistrarSalida, btnGenerarReporte;

    public VentanaParqueadero() {
        setTitle("Gestión de Parqueadero de Bicicletas");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        initComponentes();
        actualizarAreaTexto("Bienvenido al sistema de parqueadero de bicicletas.\nEsperando acciones...");
    }

    private void initComponentes() {
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---------------------------------------------------------
        // 1. Panel de Ingreso de Bicicletas
        // ---------------------------------------------------------
        JPanel pnlIngreso = new JPanel(new GridLayout(4, 2, 6, 6));
        pnlIngreso.setBorder(BorderFactory.createTitledBorder("1. Ingresar Bicicleta"));

        pnlIngreso.add(new JLabel("ID Propietario (Numérico):"));
        txtIdPropietarioIngreso = new JTextField();
        pnlIngreso.add(txtIdPropietarioIngreso);

        pnlIngreso.add(new JLabel("Serial de la bicicleta:"));
        txtSerial = new JTextField();
        pnlIngreso.add(txtSerial);

        pnlIngreso.add(new JLabel("Color:"));
        txtColor = new JTextField();
        pnlIngreso.add(txtColor);

        btnIngresar = new JButton("Registrar Ingreso");
        estilizarBoton(btnIngresar);
        pnlIngreso.add(new JLabel()); // Espacio vacío
        pnlIngreso.add(btnIngresar);

        pnlIngreso.add(new JLabel("Tipo de ID:"));
        // Si TipoIdentificacion es un enum, .values() carga las opciones automáticamente
        cbTipoId = new JComboBox<>(TipoIdentificacion.values());
        pnlIngreso.add(cbTipoId);


        // ---------------------------------------------------------
        // 2. Panel de Salida de Bicicletas
        // ---------------------------------------------------------
        JPanel pnlSalida = new JPanel(new GridLayout(2, 2, 6, 6));
        pnlSalida.setBorder(BorderFactory.createTitledBorder("2. Registrar Salida"));

        pnlSalida.add(new JLabel("ID Propietario:"));
        txtIdPropietarioSalida = new JTextField();
        pnlSalida.add(txtIdPropietarioSalida);

        btnRegistrarSalida = new JButton("Registrar Salida / Pagar");
        estilizarBoton(btnRegistrarSalida);
        pnlSalida.add(new JLabel());
        pnlSalida.add(btnRegistrarSalida);

        // ---------------------------------------------------------
        // 3. Panel de Reportes
        // ---------------------------------------------------------
        JPanel pnlReporte = new JPanel(new GridLayout(1, 1, 6, 6));
        pnlReporte.setBorder(BorderFactory.createTitledBorder("3. Reportes Administrativos"));

        btnGenerarReporte = new JButton("Generar Reporte Diario");
        estilizarBoton(btnGenerarReporte);
        pnlReporte.add(btnGenerarReporte);

        // Añadir los sub-paneles al panel izquierdo
        panelIzquierdo.add(pnlIngreso);
        panelIzquierdo.add(Box.createVerticalStrut(15));
        panelIzquierdo.add(pnlSalida);
        panelIzquierdo.add(Box.createVerticalStrut(15));
        panelIzquierdo.add(pnlReporte);

        // ---------------------------------------------------------
        // Panel Derecho (Consola de Visualización)
        // ---------------------------------------------------------
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Consola de Operaciones"));
        txtAreaConsola = new JTextArea();
        txtAreaConsola.setEditable(false);
        txtAreaConsola.setFont(new Font("Monospaced", Font.PLAIN, 13));
        panelDerecho.add(new JScrollPane(txtAreaConsola), BorderLayout.CENTER);

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

        // ---------------------------------------------------------
        // MANEJO DE EVENTOS
        // ---------------------------------------------------------

        // Evento Ingresar Bicicleta
        btnIngresar.addActionListener(e -> {
            try {
                int idDueño = Integer.parseInt(txtIdPropietarioIngreso.getText().trim());
                TipoIdentificacion tipoId = (TipoIdentificacion) cbTipoId.getSelectedItem();
                String serial = txtSerial.getText().trim();
                String color = txtColor.getText().trim();

                if (serial.isEmpty() || color.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe llenar todos los campos (Serial y Color).", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean exito = parqueadero.ingresarBicicleta(idDueño, tipoId, serial, color);

                if (exito) {
                    actualizarAreaTexto("✅ INGRESO EXITOSO:\nPropietario ID: " + idDueño + "\nBicicleta: " + color + ", Serial: " + serial);
                    limpiarCamposIngreso();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo registrar el ingreso. Verifique disponibilidad.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID del propietario debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Evento Registrar Salida
        btnRegistrarSalida.addActionListener(e -> {
            try {
                int idProp = Integer.parseInt(txtIdPropietarioSalida.getText().trim());

                boolean exito = parqueadero.registrarSalida(idProp);

                if (exito) {
                    // Aquí asumimos que en el futuro tu lógica retornará el recibo,
                    // por ahora mostramos un mensaje general.
                    actualizarAreaTexto("✅ SALIDA EXITOSA:\nEl propietario ID " + idProp + " ha retirado su bicicleta.\nSe ha generado el recibo correspondiente.");
                    txtIdPropietarioSalida.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró una bicicleta registrada con ese ID de propietario.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID del propietario debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Evento Generar Reporte Diario
        btnGenerarReporte.addActionListener(e -> {
            String reporte = parqueadero.generarReporteDiario();
            actualizarAreaTexto("📋 REPORTE DIARIO:\n\n" + reporte);
        });
    }

    /**
     * Método auxiliar para darle diseño uniforme a los botones
     */
    private void estilizarBoton(JButton boton) {
        boton.setBackground(new Color(51, 111, 158)); // Azul oscuro
        boton.setForeground(Color.WHITE);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setFocusPainted(false);
    }

    /**
     * Limpia los campos de texto de la sección de ingreso
     */
    private void limpiarCamposIngreso() {
        txtIdPropietarioIngreso.setText("");
        txtSerial.setText("");
        txtColor.setText("");
    }

    /**
     * Actualiza la consola derecha con un texto decorado
     */
    private void actualizarAreaTexto(String mensaje) {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("      SISTEMA DE ESTACIONAMIENTO         \n");
        sb.append("=========================================\n\n");
        sb.append(mensaje).append("\n\n");
        sb.append("-----------------------------------------\n");

        txtAreaConsola.setText(sb.toString());
    }

    public static void main(String[] args) {
        // Aplicar el aspecto visual nativo del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            Font fuenteGeneral = new Font("Arial", Font.PLAIN, 14);
            java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object value = UIManager.get(key);
                if (value instanceof javax.swing.plaf.FontUIResource) {
                    UIManager.put(key, fuenteGeneral);
                }
            }
        } catch (Exception e) {
            System.err.println("No se pudo establecer el Look and Feel del sistema.");
        }

        SwingUtilities.invokeLater(() -> {
            new VentanaParqueadero().setVisible(true);
        });
    }
}