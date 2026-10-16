package com.taller.poo;

public class CuentaBancaria {
    private String numeroCuenta;
    private String titular;
    private double saldo;
    private Double limiteCredito;

        /*
    * PREGUNTAS DE ANÁLISIS:
    * 1. ¿Qué diferencia ocurre a nivel de memoria y asignación por defecto al
    declarar 'saldo' como double primitivo frente a 'limiteCredito' como Wrapper
    Double?
    * RTA: a nivel de memoria el primitivo consume menos memoria que el Wrapper Double, el primitivo en valor por defecto
    al declararlo daria "0.0", el Wrapper Double por defecto declara "null"

    * 2. ¿Por qué una operación aritmética sobre un atributo no inicializado de
    tipo Double provoca una NullPointerException, mientras que sobre un double
    primitivo opera con 0.0?
    * RTA: Por que el NULL es el valor por defecto de Double y el 0.0 es el valor por defecto de double */

    public CuentaBancaria(String numeroCuenta, String titular, double saldo, Double limiteCredito){
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
    }

    public String getNumeroCuenta(){
        return this.numeroCuenta;
    }
    public void setNumeroCuenta(String numeroCuenta){
        this.numeroCuenta = numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }
    public void setTitular(String titular) {
        this.titular = titular;
    }

    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }
    public void setLimiteCredito(Double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }
    public boolean depositar(double saldo){
        if (saldo > 0 ) {
            this.saldo += saldo;
            return true;
        } else {
            return false;
        }

    }
    public boolean retirar(double saldo) {
        if (saldo > 0 && this.saldo + this.limiteCredito >= saldo){
            this.saldo -= saldo;
            return true;
        }else {
            return false;
        }
    }
}
