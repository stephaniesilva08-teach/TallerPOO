package co.edu.puj.rpg.integracion.engine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Bestiario {

    private final Map<String, Integer> derrotas = new HashMap<>();

    public boolean registrarVictoria(String enemigo) {
        String clave = normalizar(enemigo);
        if (clave.isEmpty()) {
            return false;
        }
        boolean primera = !derrotas.containsKey(clave);
        int actuales = derrotas.getOrDefault(clave, 0);
        derrotas.put(clave, actuales + 1);
        return primera;
    }

    public int getVictorias(String enemigo) {
        return derrotas.getOrDefault(normalizar(enemigo), 0);
    }

    public boolean estaVacio() {
        return derrotas.isEmpty();
    }


    public Map<String, Integer> getRegistro() {
        return Collections.unmodifiableMap(derrotas);
    }

    private String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String limpio = texto.trim().replaceAll("\\s+", " ");
        if (limpio.isEmpty()) {
            return "";
        }
        return limpio.substring(0, 1).toUpperCase() + limpio.substring(1).toLowerCase();
    }
}
