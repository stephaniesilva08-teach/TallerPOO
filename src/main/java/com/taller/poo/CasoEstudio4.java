package com.taller.poo;
public class Vehiculo {
    private int velocidadMaxima;
    private boolean encendido;
    public Vehiculo(int velocidadMaxima, boolean encendido) {
// Asignación de variables omitiendo la referencia 'this'
        velocidadMaxima = velocidadMaxima;
        encendido = encendido;
    }
    public void imprimirEstado() {
        System.out.printf("Velocidad Máxima: %d | Encendido: %b%n",
                velocidadMaxima, encendido);
    }
}
/*
1. Si se instanciar la clase mediante Vehiculo v = new Vehiculo(200, true); y luego se invoca
v.imprimirEstado(); , ¿qué valores se proyectarán en la consola y por qué?
RTA: Imprime `Velocidad Máxima: 0 | Encendido: false`. Dentro del constructor, `
velocidadMaxima = velocidadMaxima;` asigna el parámetro a sí mismo y
los atributos quedan con su valor por defecto.

2. Explique el fenómeno de sombreado de variables (variable shadowing) y cómo afecta la
asignación cuando el nombre del parámetro coincide con el del atributo.
RTA:  El sombreado (*variable shadowing*) ocurre cuando un parámetro o variable local tiene el mismo nombre que un atributo.
Dentro de ese bloque, el nombre se refiere al parámetro y "tapa" al atributo,
que nunca se modifica. Java no marca error porque la instrucción es válida.

3. Reescriba el bloque del constructor corrigiendo el error mediante la utilización de la palabra clave
this.
RTA:
public Vehiculo(int velocidadMaxima, boolean encendido) {
       this.velocidadMaxima = velocidadMaxima;
       this.encendido = encendido;
   }
 */



