package com.taller.poo;

import java.util.Locale;

public class Principal {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Vehiculo vehiculo1 = new Vehiculo("Toyota", "Corolla", 15000.50, 180, true);
        Vehiculo vehiculo2 = new Vehiculo();
        Vehiculo vehiculo3 = new Vehiculo("Renault", "Duster", 32000.00, 170, false);
        Vehiculo[] vehiculos = {vehiculo1, vehiculo2, vehiculo3};

        Estudiante estudiante1 = new Estudiante("E001", "María López", 4.35, true);
        Estudiante estudiante2 = new Estudiante("E002", "Juan Torres", 3.80, true);
        Estudiante estudiante3 = new Estudiante("E003", "Sofía Ramírez", 2.95, false);
        Estudiante[] estudiantes = {estudiante1, estudiante2, estudiante3};

        CuentaBancaria cuenta1 = new CuentaBancaria("1001", "Carlos Pérez", 1000.00, 500.0);
        CuentaBancaria cuenta2 = new CuentaBancaria("1002", "Ana Gómez");
        CuentaBancaria cuenta3 = new CuentaBancaria();

        boolean deposito1 = cuenta1.depositar(250.75);
        boolean deposito2 = cuenta2.depositar(100.00);
        boolean depositoInvalido = cuenta3.depositar(-20.00);
        boolean retiro2 = cuenta2.retirar(50.00);
        boolean retiro3 = cuenta3.retirar(150.00);

        System.out.printf("=== REPORTE DE VEHÍCULOS ===%n");
        for (int i = 0; i < vehiculos.length; i++) {
            Vehiculo v = vehiculos[i];
            System.out.printf("[%d] Marca: %s | Modelo: %s | KM: %.2f | VelMax: %d km/h | Encendido: %b%n",
                    i + 1, v.getMarcaVehiculo(), v.getModeloVehiculo(), v.getKilometrajeActual(),
                    v.getVelocidadMaxima(), v.isEstadoMotor());
        }

        System.out.printf("%n=== REPORTE DE ESTUDIANTES ===%n");
        for (Estudiante e : estudiantes) {
            System.out.printf("%s%n", e.obtenerReporteAcademicos());
        }

        System.out.printf("%n=== RESULTADO DE OPERACIONES ===%n");
        System.out.printf("Depósito en 1001: %b | Depósito en 1002: %b | Depósito inválido en 000000: %b%n",
                deposito1, deposito2, depositoInvalido);
        System.out.printf("Retiro en 1002: %b | Retiro en 000000 (sin fondos): %b%n", retiro2, retiro3);

        System.out.printf("%n=== REPORTE DE CUENTAS BANCARIAS ===%n");
        CuentaBancaria[] cuentas = {cuenta1, cuenta2, cuenta3};
        for (CuentaBancaria c : cuentas) {
            System.out.printf("Cuenta: %s | Titular: %s | Saldo Final: %s | Crédito: %s%n",
                    c.getNumeroCuenta(), c.getTitular(),
                    ConversorMonedas.formatearMoneda(c.getSaldo()),
                    ConversorMonedas.formatearMoneda(c.getLimiteCredito()));
        }

        double dolares = 100.0;
        double tasa = 0.92;
        double euros = ConversorMonedas.convertirDolaresAEuros(dolares, tasa);
        System.out.printf("%n=== CONVERSOR DE MONEDA ===%n");
        System.out.printf("%s USD a tasa %.2f = %.2f EUR%n",
                ConversorMonedas.formatearMoneda(dolares), tasa, euros);
        System.out.printf("Monto inválido (-5.00) -> %.1f%n", ConversorMonedas.convertirDolaresAEuros(-5.0, tasa));
    }
}