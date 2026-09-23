package org.unilibre.Datos;

import java.time.LocalDateTime;

public class Bicicleta {
    private int idDueño;
    private TipoIdentificacion tipoId;
    private String serial;
    private String color;
    private LocalDateTime horaEntrada;

    public void modificarIdpropietario(int idPropietario) {
        this.idDueño = idPropietario;
    }

    public int obtenerIdpropietario() {
        return idDueño;
    }

    public void modificarSerial(String serial) {
        this.serial = serial;
    }

    public String obtenerSerial() {
        return serial;
    }

    public void modificarColor(String color) {
        this.color = color;
    }

    public String obtenerColor() {
        return color;
    }

    public void modificarTipoid(TipoIdentificacion tipoId) {
        this.tipoId = tipoId;
    }

    public TipoIdentificacion obtenerTipoid() {
        return tipoId;
    }

    public void modificarHoraEntrada (LocalDateTime horaEntrada){
        this.horaEntrada = horaEntrada;

    }

    public LocalDateTime obtenerHoraEntrada(){
        return horaEntrada;
    }
}