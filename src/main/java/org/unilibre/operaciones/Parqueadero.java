package org.unilibre.operaciones;

import org.unilibre.Datos.Bicicleta;
import org.unilibre.Datos.RegistroEstacionamiento;
import org.unilibre.Datos.TipoIdentificacion;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Parqueadero {

    private RegistroEstacionamiento registro;

    public Parqueadero() {
        this.registro = new RegistroEstacionamiento();
    }

    public boolean ingresarBicicleta(
            int idDueño,
            TipoIdentificacion tipoId,
            String serial,
            String color) {

        for (Bicicleta b : registro.Obtenerbicicletas()) {

            if (b.obtenerIdpropietario() == idDueño) {
                return false;
            }
        }

        Bicicleta nuevaBici = new Bicicleta();

        nuevaBici.modificarIdpropietario(idDueño);
        nuevaBici.modificarTipoid(tipoId);
        nuevaBici.modificarSerial(serial);
        nuevaBici.modificarColor(color);

        nuevaBici.modificarHoraEntrada(LocalDateTime.now());

        registro.Obtenerbicicletas().add(nuevaBici);

        return true;
    }

    public boolean registrarSalida(int idDueño) {

        ArrayList<Bicicleta> lista =
                registro.Obtenerbicicletas();

        for (int i = 0; i < lista.size(); i++) {

            Bicicleta bicicleta = lista.get(i);

            if (bicicleta.obtenerIdpropietario() == idDueño) {

                int tiempo = registro.calcularTiempoMinutos(bicicleta);

                registro.calcularCostoTotal(tiempo);

                lista.remove(i);

                return true;
            }
        }

        return false;
    }

    public String generarReporteDiario() {

        ArrayList<Bicicleta> lista =
                registro.Obtenerbicicletas();

        StringBuilder reporte =
                new StringBuilder();

        reporte.append(
                "Total de bicicletas parqueadas actualmente: "
        ).append(lista.size()).append("\n\n");

        if (lista.isEmpty()) {

            reporte.append(
                    "El parqueadero está vacío."
            );

        } else {

            for (Bicicleta b : lista) {

                reporte.append("Propietario ID: ")
                        .append(b.obtenerIdpropietario())
                        .append(" | Tipo ID: ")
                        .append(b.obtenerTipoid())
                        .append(" | Serial: ")
                        .append(b.obtenerSerial())
                        .append(" | Color: ")
                        .append(b.obtenerColor())
                        .append("\n");
            }
        }

        return reporte.toString();
    }

    public RegistroEstacionamiento obtenerRegistro() {
        return this.registro;
    }
}