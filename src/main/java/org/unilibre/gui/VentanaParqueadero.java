package org.unilibre.gui;

import org.unilibre.Datos.TipoIdentificacion;
import org.unilibre.operaciones.Parqueadero;

import org.unilibre.Datos.TipoIdentificacion;

import javax.swing.*;
import java.awt.*;

/**
 * Interfaz Gráfica para la Gestión del Parqueadero de Bicicletas
 */
public class VentanaParqueadero extends JFrame {

    // Instancia principal del sistema
    private final Parqueadero parqueadero = new Parqueadero();

    // Campos de texto para el ingreso
    private JTextField txtIdPropietarioIngreso;
    private JTextField txtSerial;
    private JTextField txtColor;
    private JComboBox<TipoIdentificacion> cbTipoId;

    // Campo de texto para la salida
    private JTextField txtIdPropietarioSalida;

    // Área de consola y botones
    private JTextArea txtAreaConsola;
    private JButton btnIngresar;
    private JButton btnRegistrarSalida;
    private JButton btnGenerarReporte;

    /**
     * Constructor de la ventana
     */
    public VentanaParqueadero() {

        setTitle("Gestión de Parqueadero de Bicicletas");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        initComponentes();

        actualizarAreaTexto(
                "Bienvenido al sistema de parqueadero de bicicletas.\n" +
                        "Esperando acciones..."
        );
    }

    /**
     * Inicializa todos los componentes de la interfaz
     */
    private void initComponentes() {

        // =========================================================
        // PANEL IZQUIERDO
        // =========================================================

        JPanel panelIzquierdo = new JPanel();

        panelIzquierdo.setLayout(
                new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS)
        );

        panelIzquierdo.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );


        // =========================================================
        // 1. PANEL DE INGRESO DE BICICLETAS
        // =========================================================

        JPanel pnlIngreso = new JPanel(
                new GridLayout(5, 2, 6, 6)
        );

        pnlIngreso.setBorder(
                BorderFactory.createTitledBorder(
                        "1. Ingresar Bicicleta"
                )
        );


        // ID propietario
        pnlIngreso.add(
                new JLabel("ID Propietario (Numérico):")
        );

        txtIdPropietarioIngreso = new JTextField();

        pnlIngreso.add(
                txtIdPropietarioIngreso
        );


        // Serial
        pnlIngreso.add(
                new JLabel("Serial de la bicicleta:")
        );

        txtSerial = new JTextField();

        pnlIngreso.add(
                txtSerial
        );


        // Color
        pnlIngreso.add(
                new JLabel("Color:")
        );

        txtColor = new JTextField();

        pnlIngreso.add(
                txtColor
        );


        // Tipo de identificación
        pnlIngreso.add(
                new JLabel("Tipo de ID:")
        );

        cbTipoId = new JComboBox<>(
                TipoIdentificacion.values()
        );

        pnlIngreso.add(
                cbTipoId
        );


        // Botón ingresar
        pnlIngreso.add(
                new JLabel()
        );

        btnIngresar = new JButton(
                "Registrar Ingreso"
        );

        estilizarBoton(btnIngresar);

        pnlIngreso.add(
                btnIngresar
        );


        // =========================================================
        // 2. PANEL DE SALIDA
        // =========================================================

        JPanel pnlSalida = new JPanel(
                new GridLayout(2, 2, 6, 6)
        );

        pnlSalida.setBorder(
                BorderFactory.createTitledBorder(
                        "2. Registrar Salida"
                )
        );


        pnlSalida.add(
                new JLabel("ID Propietario:")
        );

        txtIdPropietarioSalida = new JTextField();

        pnlSalida.add(
                txtIdPropietarioSalida
        );


        btnRegistrarSalida = new JButton(
                "Registrar Salida / Pagar"
        );

        estilizarBoton(btnRegistrarSalida);

        pnlSalida.add(
                new JLabel()
        );

        pnlSalida.add(
                btnRegistrarSalida
        );


        // =========================================================
        // 3. PANEL DE REPORTES
        // =========================================================

        JPanel pnlReporte = new JPanel(
                new GridLayout(1, 1, 6, 6)
        );

        pnlReporte.setBorder(
                BorderFactory.createTitledBorder(
                        "3. Reportes Administrativos"
                )
        );


        btnGenerarReporte = new JButton(
                "Generar Reporte Diario"
        );

        estilizarBoton(btnGenerarReporte);

        pnlReporte.add(
                btnGenerarReporte
        );


        // =========================================================
        // AGREGAR PANELES AL PANEL IZQUIERDO
        // =========================================================

        panelIzquierdo.add(pnlIngreso);

        panelIzquierdo.add(
                Box.createVerticalStrut(15)
        );

        panelIzquierdo.add(pnlSalida);

        panelIzquierdo.add(
                Box.createVerticalStrut(15)
        );

        panelIzquierdo.add(pnlReporte);


        // =========================================================
        // PANEL DERECHO - CONSOLA
        // =========================================================

        JPanel panelDerecho = new JPanel(
                new BorderLayout()
        );

        panelDerecho.setBorder(
                BorderFactory.createTitledBorder(
                        "Consola de Operaciones"
                )
        );


        txtAreaConsola = new JTextArea();

        txtAreaConsola.setEditable(false);

        txtAreaConsola.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        13
                )
        );


        panelDerecho.add(
                new JScrollPane(txtAreaConsola),
                BorderLayout.CENTER
        );


        // =========================================================
        // AGREGAR PANELES PRINCIPALES A LA VENTANA
        // =========================================================

        add(
                panelIzquierdo,
                BorderLayout.WEST
        );

        add(
                panelDerecho,
                BorderLayout.CENTER
        );


        // =========================================================
        // EVENTO: INGRESAR BICICLETA
        // =========================================================

        btnIngresar.addActionListener(e -> {

            try {

                // Obtener ID
                int idDueño = Integer.parseInt(
                        txtIdPropietarioIngreso
                                .getText()
                                .trim()
                );


                // Obtener tipo de identificación
                TipoIdentificacion tipoId =
                        (TipoIdentificacion)
                                cbTipoId.getSelectedItem();


                // Obtener serial
                String serial =
                        txtSerial.getText().trim();


                // Obtener color
                String color =
                        txtColor.getText().trim();


                // Validar campos
                if (serial.isEmpty() ||
                        color.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Debe llenar todos los campos.",
                            "Campos incompletos",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                // Intentar registrar bicicleta
                boolean exito =
                        parqueadero.ingresarBicicleta(
                                idDueño,
                                tipoId,
                                serial,
                                color
                        );


                if (exito) {

                    actualizarAreaTexto(
                            "✅ INGRESO EXITOSO\n\n" +
                                    "Propietario ID: " +
                                    idDueño + "\n" +
                                    "Tipo de identificación: " +
                                    tipoId + "\n" +
                                    "Bicicleta: " +
                                    color + "\n" +
                                    "Serial: " +
                                    serial + "\n\n" +
                                    "La hora de entrada ha sido registrada."
                    );


                    limpiarCamposIngreso();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "No se pudo registrar el ingreso.\n" +
                                    "El propietario ya tiene una bicicleta " +
                                    "dentro del parqueadero.",
                            "Ingreso no permitido",
                            JOptionPane.ERROR_MESSAGE
                    );
                }


            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "El ID del propietario debe ser " +
                                "un número entero válido.",
                        "Error de Formato",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });


        // =========================================================
        // EVENTO: REGISTRAR SALIDA
        // =========================================================

        btnRegistrarSalida.addActionListener(e -> {

            try {

                // Obtener ID del propietario
                int idProp =
                        Integer.parseInt(
                                txtIdPropietarioSalida
                                        .getText()
                                        .trim()
                        );


                // Registrar salida
                boolean exito =
                        parqueadero.registrarSalida(
                                idProp
                        );


                if (exito) {

                    /*
                     * registrarSalida() ya calculó:
                     *
                     * 1. Tiempo de permanencia
                     * 2. Costo total
                     * 3. Retiró la bicicleta
                     *
                     * Ahora obtenemos el recibo generado
                     * por RegistroEstacionamiento.
                     */

                    String recibo =
                            parqueadero
                                    .obtenerRegistro()
                                    .generarRecibo();


                    actualizarAreaTexto(
                            "✅ SALIDA EXITOSA\n\n" +
                                    "Propietario ID: " +
                                    idProp + "\n\n" +
                                    recibo
                    );


                    // Limpiar campo
                    txtIdPropietarioSalida.setText("");


                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "No se encontró una bicicleta " +
                                    "registrada con ese ID de propietario.",
                            "No Encontrado",
                            JOptionPane.WARNING_MESSAGE
                    );
                }


            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "El ID del propietario debe ser " +
                                "un número entero válido.",
                        "Error de Formato",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });


        // =========================================================
        // EVENTO: GENERAR REPORTE DIARIO
        // =========================================================

        btnGenerarReporte.addActionListener(e -> {

            String reporte =
                    parqueadero.generarReporteDiario();


            actualizarAreaTexto(
                    "📋 REPORTE DIARIO\n\n" +
                            reporte
            );
        });
    }


    /**
     * Estiliza los botones de la interfaz
     */
    private void estilizarBoton(JButton boton) {

        boton.setBackground(
                new Color(51, 111, 158)
        );

        boton.setForeground(
                Color.WHITE
        );

        boton.setContentAreaFilled(false);

        boton.setOpaque(true);

        boton.setFocusPainted(false);
    }


    /**
     * Limpia los campos de ingreso
     */
    private void limpiarCamposIngreso() {

        txtIdPropietarioIngreso.setText("");

        txtSerial.setText("");

        txtColor.setText("");

        cbTipoId.setSelectedIndex(0);
    }


    /**
     * Actualiza el área de consola
     */
    private void actualizarAreaTexto(
            String mensaje
    ) {

        StringBuilder sb =
                new StringBuilder();


        sb.append(
                "=========================================\n"
        );

        sb.append(
                "      SISTEMA DE ESTACIONAMIENTO         \n"
        );

        sb.append(
                "=========================================\n\n"
        );

        sb.append(
                mensaje
        );

        sb.append("\n\n");

        sb.append(
                "-----------------------------------------\n"
        );


        txtAreaConsola.setText(
                sb.toString()
        );
    }


    /**
     * Método principal
     */
    public static void main(String[] args) {

        // =========================================================
        // CONFIGURACIÓN DEL ASPECTO VISUAL
        // =========================================================

        try {

            UIManager.setLookAndFeel(
                    UIManager
                            .getSystemLookAndFeelClassName()
            );


            Font fuenteGeneral =
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            14
                    );


            java.util.Enumeration<Object> keys =
                    UIManager
                            .getDefaults()
                            .keys();


            while (keys.hasMoreElements()) {

                Object key =
                        keys.nextElement();

                Object value =
                        UIManager.get(key);


                if (value instanceof
                        javax.swing.plaf.FontUIResource) {

                    UIManager.put(
                            key,
                            fuenteGeneral
                    );
                }
            }


        } catch (Exception e) {

            System.err.println(
                    "No se pudo establecer el " +
                            "Look and Feel del sistema."
            );
        }


        // =========================================================
        // INICIAR INTERFAZ
        // =========================================================

        SwingUtilities.invokeLater(() -> {

            new VentanaParqueadero()
                    .setVisible(true);

        });
    }
}