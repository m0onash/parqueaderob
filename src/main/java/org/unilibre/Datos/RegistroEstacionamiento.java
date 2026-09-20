package org.unilibre.Datos;

import java.util.ArrayList;
import java.util.Random;

public class RegistroEstacionamiento {
    private ArrayList<Bicicleta> bicicletas;

    // Variables para almacenar temporalmente los datos del último cobro realizado
    private int ultimoTiempo;
    private int ultimoCosto;

    public RegistroEstacionamiento() {
        this.bicicletas = new ArrayList<>();
    }

    public void modificarBicicletas(ArrayList<Bicicleta> bicicletas){
        this.bicicletas = bicicletas;
    }

    public ArrayList<Bicicleta> Obtenerbicicletas(){
        return bicicletas;
    }

    public int calcularTiempoMinutos() {
        // Como no tenemos una "hora de entrada" registrada en Bicicleta,
        // simularemos un tiempo aleatorio entre 30 y 240 minutos para el ejercicio.
        Random random = new Random();
        this.ultimoTiempo = random.nextInt(210) + 30; // Minutos simulados

        return this.ultimoTiempo;
    }

    public int calcularCostoTotal() {
        // Supongamos que el costo es de $50 pesos por cada minuto
        int tarifaPorMinuto = 50;
        this.ultimoCosto = this.ultimoTiempo * tarifaPorMinuto;

        return this.ultimoCosto;
    }

    public String generarRecibo() {
        StringBuilder recibo = new StringBuilder();
        recibo.append("========== TICKET DE SALIDA ==========\n");
        recibo.append("Tiempo de parqueo : ").append(ultimoTiempo).append(" minutos\n");
        recibo.append("Tarifa por minuto : $ 50\n");
        recibo.append("--------------------------------------\n");
        recibo.append("TOTAL A PAGAR     : $ ").append(ultimoCosto).append("\n");
        recibo.append("======================================\n");
        recibo.append("¡Gracias por utilizar nuestro servicio!");

        return recibo.toString();
    }
}