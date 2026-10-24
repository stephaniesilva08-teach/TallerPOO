package co.edu.puj.rpg.integracion.model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/** Perfil del jugador: guarda los logros desbloqueados, sin repetidos y siempre ordenados. */
public class PerfilJugador {

    private final String nombre;
    private final Set<String> logros = new TreeSet<>(); // TreeSet = unico + orden natural (alfabetico)

    public PerfilJugador(String nombre) {
        this.nombre = nombre;
    }

    /** @return true si el logro es nuevo; false si ya estaba desbloqueado. */
    public boolean registrarLogro(String logro) {
        if (logro == null || logro.trim().isEmpty()) {
            return false;
        }
        return logros.add(logro.trim()); // add() ya devuelve false si existia
    }

    /** Vista de solo lectura; al recorrerla sale en orden alfabetico. */
    public Set<String> listarLogrosOrdenados() {
        return Collections.unmodifiableSet(logros);
    }

    public String getNombre() {
        return nombre;
    }
}
