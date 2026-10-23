package co.edu.puj.rpg.relaciones.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gremio {

    private String nombre;
    private List<Personaje> miembros; // 0..*

    public Gremio(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
    }

    public void agregarMiembro(Personaje p) {
        if (p == null) {
            return;
        }
        if (!miembros.contains(p)) {
            miembros.add(p);
        }
        // Si el personaje todavía no sabe de este gremio, se lo digo.
        // (la condición es lo que corta la recursión infinita)
        if (p.getGremio() != this) {
            p.unirseAGremio(this);
        }
    }

    public void retirarMiembro(Personaje p) {
        if (p == null) {
            return;
        }
        miembros.remove(p);
        // si el personaje aún cree que es de este gremio, lo suelto también
        if (p.getGremio() == this) {
            p.abandonarGremio();
        }
    }

    public String getNombre() {
        return nombre;
    }

    /** Vista de solo lectura: nadie de afuera puede modificar la lista sin pasar por los métodos de arriba. */
    public List<Personaje> getMiembros() {
        return Collections.unmodifiableList(miembros);
    }

    /** Solo nombres de los miembros (nunca su toString completo) para evitar StackOverflowError. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gremio{nombre='").append(nombre).append("', miembros=[");
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
