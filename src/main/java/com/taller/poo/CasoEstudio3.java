package com.taller.poo;
public class CuentaBancaria {
    private String numeroCuenta;
    private double saldo;
    // Declaración errónea del constructor
    public void CuentaBancaria(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;com.taller.poo
        Preguntas Analíticas:
        Caso de Estudio 4: Sombreado de Variables (Variable Shadowing) por Ausencia de
        this
        Preguntas Analíticas:
        this.saldo = saldoInicial;
    }
}
/*
1. ¿Por qué la inclusión de la palabra clave void altera la naturaleza del método y hace que Java
deje de reconocerlo como un constructor?
RTA:Un constructor no lleva tipo de retorno, ni siquiera void. Con void,
Java lo trata como un método normal que se llama igual que la clase. Es legal, pero ya no es constructor.

2. Si desde otra clase se intenta ejecutar CuentaBancaria cuenta = new CuentaBancaria("A- 101", 500.0); ,
¿por qué la compilación reportará que no se encuentra un constructor adecuado?
RTA: Como no hay ningún constructor real, Java le da a la clase uno por defecto sin parámetros.
Al hacer new CuentaBancaria("A-101", 500.0) busca uno con (String, double)
y no lo encuentra: constructor CuentaBancaria in class CuentaBancaria cannot be applied to given types.

 3. Escriba la firma y declaración correcta del constructor omitiendo el tipo de retorno.
 RTA:
 public CuentaBancaria(String numeroCuenta, double saldoInicial) {
    this.numeroCuenta = numeroCuenta;
    this.saldo = saldoInicial;
}
 */




