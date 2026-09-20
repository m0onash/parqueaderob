package org.unilibre.Datos;

import java.util.ArrayList;

public class RegistroEstacionamiento {
    private ArrayList<Bicicleta> bicicletas;

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
        return 0;
    }

    public int calcularCostoTotal() {
        return 0;
    }

    public String generarRecibo() {
        return "Detalle del recibo...";
    }
}