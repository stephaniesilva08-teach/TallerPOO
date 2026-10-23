package co.edu.puj.rpg.relaciones.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Agregación: el escuadrón agrupa personajes que se crean por fuera.
 * Si el escuadrón desaparece, los personajes siguen vivos.
 */
public class Escuadron {

    private String nombre;
    private List<Personaje> miembros; // Agregación (0..*)

    public Escuadron(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>(); // Inicialización de la colección
    }

    public void reclutarPersonaje(Personaje p) {
        if (p == null) {
            System.out.println("No se puede reclutar un personaje nulo.");
            return;
        }
        if (miembros.contains(p)) {
            System.out.printf("%s ya pertenece al escuadron %s.%n", p.getNombre(), nombre);
            return;
        }
        miembros.add(p); // solo guardo la referencia, no creo ni modifico al personaje
        System.out.printf("%s fue reclutado en el escuadron %s.%n", p.getNombre(), nombre);
    }

    public void expulsarPersonaje(Personaje p) {
        if (p != null && miembros.remove(p)) {
            // p sigue con su estado, vida y objetos intactos en memoria
            System.out.printf("%s fue expulsado del escuadron %s.%n", p.getNombre(), nombre);
        } else {
            System.out.println("Ese personaje no esta en el escuadron " + nombre + ".");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public List<Personaje> getMiembros() {
        return Collections.unmodifiableList(miembros);
    }

    public int getCantidadMiembros() {
        return miembros.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Escuadron{nombre='").append(nombre).append("', miembros=[");
        for (int i = 0; i < miembros.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(miembros.get(i).getNombre());
        }
        sb.append("]}");
        return sb.toString();
    }
}
