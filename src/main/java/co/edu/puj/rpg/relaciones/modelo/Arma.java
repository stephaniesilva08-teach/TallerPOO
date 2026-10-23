package co.edu.puj.rpg.relaciones.modelo;

/**
 * Arma que puede equipar un Personaje.
 * Ojo: el Arma NO conoce a su dueño (asociación unidireccional).
 */
public class Arma {

    private String nombre;
    private int danioBase;

    public Arma(String nombre, int danioBase) {
        this.nombre = nombre;
        this.danioBase = danioBase;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDanioBase() {
        return danioBase;
    }

    @Override
    public String toString() {
        return nombre + " (+" + danioBase + " dano)";
    }
}
