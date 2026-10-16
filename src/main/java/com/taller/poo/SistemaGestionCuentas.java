package com.taller.poo;

import java.util.Locale;
import java.util.Scanner;

public class SistemaGestionCuentas {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        CuentaBancaria[] cuentas = new CuentaBancaria[3];
        cuentas[0] = new CuentaBancaria("1001", "Carlos Pérez", 1000.00, 500.0);
        cuentas[1] = new CuentaBancaria("1002", "Ana Gómez");
        cuentas[2] = new CuentaBancaria("1003", "Luis Rojas", 300.00, 200.0);
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero(scanner);
            switch (opcion) {
                case 1:
                    depositar(scanner, cuentas);
                    break;
                case 2:
                    retirar(scanner, cuentas);
                    break;
                case 3:
                    mostrarReporte(cuentas);
                    break;
                case 4:
                    System.out.printf("Saliendo del sistema. ¡Hasta pronto!%n");
                    break;
                default:
                    System.out.printf("Opción inválida. Elija un número entre 1 y 4.%n");
            }
        } while (opcion != 4);
        scanner.close();
    }
    private static void mostrarMenu() {
        System.out.printf("%n===== SISTEMA DE GESTIÓN DE CUENTAS =====%n");
        System.out.printf("1. Depositar%n");
        System.out.printf("2. Retirar%n");
        System.out.printf("3. Ver reporte consolidado%n");
        System.out.printf("4. Salir%n");
        System.out.printf("Seleccione una opción: ");
    }
    private static int leerEntero(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    private static double leerDecimal(Scanner scanner) {
        try {
            return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    private static CuentaBancaria buscarCuenta(CuentaBancaria[] cuentas, String numeroCuenta) {
        for (int i = 0; i < cuentas.length; i++) {
            if (cuentas[i].getNumeroCuenta().equals(numeroCuenta)) {
                return cuentas[i];
            }
        }
        return null;
    }
    private static void depositar(Scanner scanner, CuentaBancaria[] cuentas) {
        System.out.printf("Número de cuenta: ");
        String numero = scanner.nextLine().trim();
        CuentaBancaria cuenta = buscarCuenta(cuentas, numero);
        if (cuenta == null) {
            System.out.printf("Error: la cuenta '%s' no existe.%n", numero);
            return;
        }
        System.out.printf("Monto a depositar: ");
        double monto = leerDecimal(scanner);
        if (monto <= 0) {
            System.out.printf("Error: el monto debe ser un número mayor a 0.%n");
        } else if (cuenta.depositar(monto)) {
            System.out.printf("Depósito exitoso. Nuevo saldo de %s: %s%n",
                    cuenta.getNumeroCuenta(), ConversorMonedas.formatearMoneda(cuenta.getSaldo()));
        } else {
            System.out.printf("No se pudo realizar el depósito.%n");
        }
    }
    private static void retirar(Scanner scanner, CuentaBancaria[] cuentas) {
        System.out.printf("Número de cuenta: ");
        String numero = scanner.nextLine().trim();
        CuentaBancaria cuenta = buscarCuenta(cuentas, numero);
        if (cuenta == null) {
            System.out.printf("Error: la cuenta '%s' no existe.%n", numero);
            return;
        }
        System.out.printf("Monto a retirar: ");
        double monto = leerDecimal(scanner);
        if (monto <= 0) {
            System.out.printf("Error: el monto debe ser un número mayor a 0.%n");
        } else if (cuenta.retirar(monto)) {
            System.out.printf("Retiro exitoso. Nuevo saldo de %s: %s%n",
                    cuenta.getNumeroCuenta(), ConversorMonedas.formatearMoneda(cuenta.getSaldo()));
        } else {
            System.out.printf("Fondos insuficientes (saldo + crédito) para retirar %s.%n",
                    ConversorMonedas.formatearMoneda(monto));
        }
    }
    private static void mostrarReporte(CuentaBancaria[] cuentas) {
        StringBuilder detalle = new StringBuilder();
        double saldoTotal = 0.0;

        for (int i = 0; i < cuentas.length; i++) {
            CuentaBancaria c = cuentas[i];
            detalle.append(String.format("[%d] Cuenta: %s | Titular: %s | Saldo: %.2f | Crédito: %.2f | ¿Saldo positivo? %b%n",
                    i + 1, c.getNumeroCuenta(), c.getTitular(), c.getSaldo(),
                    c.getLimiteCredito(), c.getSaldo() > 0));
            saldoTotal += c.getSaldo();
        }
        System.out.printf("%n=== REPORTE CONSOLIDADO ===%n");
        System.out.printf("%s", detalle);
        System.out.printf("Cuentas registradas: %d%n", cuentas.length);
        System.out.printf("Saldo total acumulado: %s%n", ConversorMonedas.formatearMoneda(saldoTotal));
    }
}