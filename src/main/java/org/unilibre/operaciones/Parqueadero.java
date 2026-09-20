package org.unilibre.operaciones;

import org.unilibre.Datos.RegistroEstacionamiento;

public class Parqueadero {
    private RegistroEstacionamiento registro;

    public Parqueadero() {
        this.registro = new RegistroEstacionamiento();
    }

    public boolean ingresarBicicleta(int idPropietario, String serial, String color) {
        return true;
    }

    public boolean registrarSalida(int idPropietario) {
        return true;
    }

    public String generarReporteDiario() {
        return "Reporte diario de bicicletas...";
    }
}