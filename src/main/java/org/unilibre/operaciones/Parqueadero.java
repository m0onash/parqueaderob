package org.unilibre.operaciones;

import org.unilibre.Datos.Bicicleta;
import org.unilibre.Datos.RegistroEstacionamiento;
import org.unilibre.Datos.TipoIdentificacion;
import java.util.ArrayList;

public class Parqueadero {
    private RegistroEstacionamiento registro;

    public Parqueadero() {
        this.registro = new RegistroEstacionamiento();
    }

    public boolean ingresarBicicleta(int idDueño, TipoIdentificacion tipoId, String serial, String color) {
        // 1. Validar que el dueño no tenga ya una bicicleta en el parqueadero
        for (Bicicleta b : registro.Obtenerbicicletas()) {
            if (b.obtenerIdpropietario() == idDueño) {
                return false; // Retorna falso porque ya hay una bici de ese dueño
            }
        }

        // 2. Crear la bicicleta y asignarle los datos usando los métodos modificadores (setters)
        Bicicleta nuevaBici = new Bicicleta();
        nuevaBici.modificarIdpropietario(idDueño);
        nuevaBici.modificarTipoid(tipoId);
        nuevaBici.modificarSerial(serial); // Asumiendo que cambiaste el parámetro a String como sugerimos antes
        nuevaBici.modificarColor(color);

        // 3. Guardarla en la lista del registro
        registro.Obtenerbicicletas().add(nuevaBici);

        return true; // Ingreso exitoso
    }

    public boolean registrarSalida(int idDueño) {
        ArrayList<Bicicleta> lista = registro.Obtenerbicicletas();

        // 1. Buscar la bicicleta por el ID del dueño
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).obtenerIdpropietario() == idDueño) {

                // 2. Si la encuentra, calculamos los cobros y retiramos la bicicleta
                registro.calcularTiempoMinutos();
                registro.calcularCostoTotal();

                lista.remove(i); // Sacamos la bicicleta del parqueadero
                return true; // Salida exitosa
            }
        }

        return false; // No se encontró ninguna bicicleta con ese ID
    }

    public String generarReporteDiario() {
        ArrayList<Bicicleta> lista = registro.Obtenerbicicletas();
        StringBuilder reporte = new StringBuilder();

        reporte.append("Total de bicicletas parqueadas actualmente: ").append(lista.size()).append("\n\n");

        if (lista.isEmpty()) {
            reporte.append("El parqueadero está vacío.");
        } else {
            for (Bicicleta b : lista) {
                reporte.append("Propietario ID: ").append(b.obtenerIdpropietario())
                        .append(" | Tipo ID: ").append(b.obtenerTipoid())
                        .append(" | Serial: ").append(b.obtenerSerial())
                        .append(" | Color: ").append(b.obtenerColor())
                        .append("\n");
            }
        }

        return reporte.toString();
    }

    // Método extra para poder acceder al registro desde la Ventana y obtener el recibo
    public RegistroEstacionamiento obtenerRegistro() {
        return this.registro;
    }
}