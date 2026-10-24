package co.edu.puj.rpg.relaciones.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gremio {

    private String nombre;
    private List<Personaje> miembros;

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
        if (p.getGremio() != this) {
            p.unirseAGremio(this);
        }
    }

    public void retirarMiembro(Personaje p) {
        if (p == null) {
            return;
        }
        miembros.remove(p);
        if (p.getGremio() == this) {
            p.abandonarGremio();
        }
    }

    public String getNombre() {
        return nombre;
    }
    public List<Personaje> getMiembros() {
        return Collections.unmodifiableList(miembros);
    }
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
