package com.taller.poo ;
public class GestorVehiculos {
    public void encenderMotor() {
        System.out.println("El motor del vehículo ha sido encendido.");
    }
    public static void main(String[] args) {
// Intento de llamada directa a método de instancia desde el método
        estático main
        encenderMotor();
    }
}
/*
1. ¿Cuál es el mensaje de error emitido por el compilador de Java en la línea donde se invoca
encenderMotor() ?
RTA: non-static method encenderMotor() cannot be referenced from a static context

2. Explique desde la perspectiva de la POO por qué un método con el modificador static (como
 main ) no puede invocar directamente un método de instancia que requiere un estado de objeto.
RTA: main es estático y pertenece a la clase, así que corre sin que exista ningún objeto.
encenderMotor() necesita un objeto concreto (su estado),
y el compilador no puede garantizar que exista uno.

3. Describa las dos alternativas de corrección válidas para que el código compile y ejecute
correctamente el método
RTA: Dos correcciones válidas:
Volver el método estático: public static void encenderMotor().
Instanciar un objeto: GestorVehiculos gestor = new GestorVehiculos(); gestor.encenderMotor();
 */


