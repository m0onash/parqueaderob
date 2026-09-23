package org.unilibre.Datos;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RegistroEstacionamiento {

    private ArrayList<Bicicleta> bicicletas;
    private int ultimoTiempo;
    private int ultimoCosto;

    public RegistroEstacionamiento() {
        this.bicicletas = new ArrayList<>();
    }

    public void modificarBicicletas(ArrayList<Bicicleta> bicicletas) {
        this.bicicletas = bicicletas;
    }

    public ArrayList<Bicicleta> Obtenerbicicletas() {
        return bicicletas;
    }

    public int calcularTiempoMinutos(Bicicleta bicicleta) {

        if (bicicleta == null || bicicleta.obtenerHoraEntrada() == null) {
            return 0;
        }

        LocalDateTime horaSalida = LocalDateTime.now();

        this.ultimoTiempo = (int) Duration.between(
                bicicleta.obtenerHoraEntrada(),
                horaSalida
        ).toMinutes();

        return this.ultimoTiempo;
    }

    /**
     * Calcula el costo usando una tarifa de $10 por minuto.
     */
    public int calcularCostoTotal(int tiempoMinutos) {

        int tarifaPorMinuto = 10;

        this.ultimoCosto = tiempoMinutos * tarifaPorMinuto;

        return this.ultimoCosto;
    }

    public String generarRecibo() {

        StringBuilder recibo = new StringBuilder();

        recibo.append("========== TICKET DE SALIDA ==========\n");

        recibo.append("Tiempo de parqueo : ")
                .append(ultimoTiempo)
                .append(" minutos\n");

        recibo.append("Tarifa por minuto : $ 10\n");

        recibo.append("--------------------------------------\n");

        recibo.append("TOTAL A PAGAR     : $ ")
                .append(ultimoCosto)
                .append("\n");

        recibo.append("======================================\n");

        recibo.append("¡Gracias por utilizar nuestro servicio!");

        return recibo.toString();
    }
}