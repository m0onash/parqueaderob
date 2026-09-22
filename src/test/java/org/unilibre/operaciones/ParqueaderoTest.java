package org.unilibre.operaciones;

import org.junit.jupiter.api.Test;
import org.unilibre.Datos.Bicicleta;
import org.unilibre.Datos.RegistroEstacionamiento;
import org.unilibre.Datos.TipoIdentificacion;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ParqueaderoTest {

    // =========================================================
    // PRUEBA DE INGRESO
    // =========================================================

    @Test
    void testIngresarBicicleta() {

        Parqueadero parqueadero = new Parqueadero();

        boolean resultado = parqueadero.ingresarBicicleta(
                123,
                TipoIdentificacion.CEDULA_CIUDADANIA,
                "ABC123",
                "Rojo"
        );

        assertTrue(resultado);

        assertEquals(
                1,
                parqueadero.obtenerRegistro()
                        .Obtenerbicicletas()
                        .size()
        );

        Bicicleta bicicleta =
                parqueadero.obtenerRegistro()
                        .Obtenerbicicletas()
                        .get(0);

        assertEquals(123, bicicleta.obtenerIdpropietario());
        assertEquals(
                TipoIdentificacion.CEDULA_CIUDADANIA,
                bicicleta.obtenerTipoid()
        );
        assertEquals("ABC123", bicicleta.obtenerSerial());
        assertEquals("Rojo", bicicleta.obtenerColor());

        // Verificar que se registró la hora de entrada
        assertNotNull(bicicleta.obtenerHoraEntrada());
    }


    // =========================================================
    // NO PERMITIR DOS BICICLETAS DEL MISMO PROPIETARIO
    // =========================================================

    @Test
    void testNoPermitirDosBicicletasMismoPropietario() {

        Parqueadero parqueadero = new Parqueadero();

        boolean primera =
                parqueadero.ingresarBicicleta(
                        123,
                        TipoIdentificacion.CEDULA_CIUDADANIA,
                        "ABC123",
                        "Rojo"
                );

        boolean segunda =
                parqueadero.ingresarBicicleta(
                        123,
                        TipoIdentificacion.DOCUMENTO_IDENTIDAD,
                        "XYZ789",
                        "Azul"
                );

        assertTrue(primera);
        assertFalse(segunda);

        assertEquals(
                1,
                parqueadero.obtenerRegistro()
                        .Obtenerbicicletas()
                        .size()
        );
    }


    // =========================================================
    // PERMITIR BICICLETAS DE DIFERENTES PROPIETARIOS
    // =========================================================

    @Test
    void testIngresarBicicletasDiferentesPropietarios() {

        Parqueadero parqueadero = new Parqueadero();

        boolean primera =
                parqueadero.ingresarBicicleta(
                        123,
                        TipoIdentificacion.CEDULA_CIUDADANIA,
                        "ABC123",
                        "Rojo"
                );

        boolean segunda =
                parqueadero.ingresarBicicleta(
                        456,
                        TipoIdentificacion.DOCUMENTO_IDENTIDAD,
                        "XYZ789",
                        "Azul"
                );

        assertTrue(primera);
        assertTrue(segunda);

        assertEquals(
                2,
                parqueadero.obtenerRegistro()
                        .Obtenerbicicletas()
                        .size()
        );
    }


    // =========================================================
    // PRUEBA DE CALCULAR TIEMPO
    // =========================================================

    @Test
    void testCalcularTiempoMinutos() {

        RegistroEstacionamiento registro =
                new RegistroEstacionamiento();

        Bicicleta bicicleta = new Bicicleta();

        // Simulamos que la bicicleta entró hace 30 minutos
        bicicleta.modificarHoraEntrada(
                LocalDateTime.now().minusMinutes(30)
        );

        int tiempo =
                registro.calcularTiempoMinutos(bicicleta);

        // Debe ser aproximadamente 30 minutos
        assertTrue(tiempo >= 30);
    }


    // =========================================================
    // PRUEBA DE CALCULAR COSTO
    // =========================================================

    @Test
    void testCalcularCostoTotal() {

        RegistroEstacionamiento registro =
                new RegistroEstacionamiento();

        int tiempo = 30;

        int costo =
                registro.calcularCostoTotal(tiempo);

        // $10 por minuto
        assertEquals(300, costo);
    }


    // =========================================================
    // PRUEBA DE TIEMPO + COSTO
    // =========================================================

    @Test
    void testCalcularTiempoYCosto() {

        RegistroEstacionamiento registro =
                new RegistroEstacionamiento();

        Bicicleta bicicleta = new Bicicleta();

        // Simular 30 minutos de estacionamiento
        bicicleta.modificarHoraEntrada(
                LocalDateTime.now().minusMinutes(30)
        );

        int tiempo =
                registro.calcularTiempoMinutos(bicicleta);

        int costo =
                registro.calcularCostoTotal(tiempo);

        assertTrue(tiempo >= 30);
        assertEquals(tiempo * 10, costo);
    }


    // =========================================================
    // PRUEBA DE REGISTRAR SALIDA
    // =========================================================

    @Test
    void testRegistrarSalida() {

        Parqueadero parqueadero = new Parqueadero();

        parqueadero.ingresarBicicleta(
                123,
                TipoIdentificacion.CEDULA_CIUDADANIA,
                "ABC123",
                "Rojo"
        );

        boolean resultado =
                parqueadero.registrarSalida(123);

        assertTrue(resultado);

        // La bicicleta debe haber sido eliminada
        assertEquals(
                0,
                parqueadero.obtenerRegistro()
                        .Obtenerbicicletas()
                        .size()
        );
    }


    // =========================================================
    // SALIDA DE PROPIETARIO INEXISTENTE
    // =========================================================

    @Test
    void testRegistrarSalidaPropietarioNoExistente() {

        Parqueadero parqueadero = new Parqueadero();

        parqueadero.ingresarBicicleta(
                123,
                TipoIdentificacion.CEDULA_CIUDADANIA,
                "ABC123",
                "Rojo"
        );

        boolean resultado =
                parqueadero.registrarSalida(999);

        assertFalse(resultado);

        // La bicicleta original debe seguir ahí
        assertEquals(
                1,
                parqueadero.obtenerRegistro()
                        .Obtenerbicicletas()
                        .size()
        );
    }


    // =========================================================
    // REPORTE CON PARQUEADERO VACÍO
    // =========================================================

    @Test
    void testGenerarReporteParqueaderoVacio() {

        Parqueadero parqueadero = new Parqueadero();

        String reporte =
                parqueadero.generarReporteDiario();

        assertTrue(
                reporte.contains(
                        "Total de bicicletas parqueadas actualmente: 0"
                )
        );

        assertTrue(
                reporte.contains(
                        "El parqueadero está vacío."
                )
        );
    }


    // =========================================================
    // REPORTE CON BICICLETA
    // =========================================================

    @Test
    void testGenerarReporteConBicicleta() {

        Parqueadero parqueadero = new Parqueadero();

        parqueadero.ingresarBicicleta(
                123,
                TipoIdentificacion.CEDULA_CIUDADANIA,
                "ABC123",
                "Rojo"
        );

        String reporte =
                parqueadero.generarReporteDiario();

        assertTrue(
                reporte.contains(
                        "Total de bicicletas parqueadas actualmente: 1"
                )
        );

        assertTrue(reporte.contains("123"));
        assertTrue(reporte.contains("ABC123"));
        assertTrue(reporte.contains("Rojo"));
        assertTrue(
                reporte.contains("CEDULA_CIUDADANIA")
        );
    }


    // =========================================================
    // OBTENER REGISTRO
    // =========================================================

    @Test
    void testObtenerRegistro() {

        Parqueadero parqueadero = new Parqueadero();

        assertNotNull(
                parqueadero.obtenerRegistro()
        );

        assertNotNull(
                parqueadero.obtenerRegistro()
                        .Obtenerbicicletas()
        );
    }


    // =========================================================
    // GENERAR RECIBO
    // =========================================================

    @Test
    void testGenerarRecibo() {

        RegistroEstacionamiento registro =
                new RegistroEstacionamiento();

        Bicicleta bicicleta = new Bicicleta();

        bicicleta.modificarHoraEntrada(
                LocalDateTime.now().minusMinutes(30)
        );

        int tiempo =
                registro.calcularTiempoMinutos(bicicleta);

        registro.calcularCostoTotal(tiempo);

        String recibo =
                registro.generarRecibo();

        assertTrue(
                recibo.contains(
                        "TICKET DE SALIDA"
                )
        );

        assertTrue(
                recibo.contains(
                        "Tiempo de parqueo"
                )
        );

        assertTrue(
                recibo.contains(
                        "Tarifa por minuto : $ 10"
                )
        );

        assertTrue(
                recibo.contains(
                        "TOTAL A PAGAR"
                )
        );

        assertTrue(
                recibo.contains(
                        "Gracias por utilizar nuestro servicio"
                )
        );
    }
}
```

