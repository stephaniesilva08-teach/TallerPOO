package com.taller.poo;
public class PruebaEstudiante {
    public static void main(String[] args) {
        Estudiante estudiante1;
// Intento de invocación sobre una variable no instanciada
        String reporte = estudiante1.obtenerReporteAcademicos();
        System.out.println(reporte);
    }
}
/* Preguntas Analíticas:
1.¿Qué error indica el compilador de Java o qué excepción se lanza en tiempo de ejecución al
intentar compilar/ejecutar el código anterior?
RTA:El compilador no deja ejecutar y marca variable estudiante1 might not have been initialized.
La NullPointerException solo aparecería en ejecución si la variable estuviera declarada
como Estudiante estudiante1 = null;.

2. ¿Por qué la variable estudiante1 no puede invocar el método de instancia
obtenerReporteAcademicos() en su estado actual?
RTA:estudiante1 es una referencia que no apunta a ningún objeto.
 Un método de instancia trabaja con los atributos de un objeto, y aquí no hay ninguno.

3. Indique la instrucción exacta de código que debe agregarse para instanciar correctamente el
objeto estudiante1 mediante el operador new antes de invocar el método
RTA: estudiante1 = new Estudiante("E001", "María López", 4.35, true);
 */
