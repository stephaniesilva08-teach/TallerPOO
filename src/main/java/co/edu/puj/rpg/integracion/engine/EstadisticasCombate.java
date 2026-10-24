package co.edu.puj.rpg.integracion.engine;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class EstadisticasCombate {

    private final Map<String, Double> valores = new TreeMap<>();

    public void incrementar(String clave, double cantidad) {
        if (valores.containsKey(clave)) {
            valores.put(clave, valores.get(clave) + cantidad);
        } else {
            valores.put(clave, cantidad);
        }
    }

    public double getValor(String clave) {
        return valores.getOrDefault(clave, 0.0);
    }

    public Map<String, Double> getValores() {
        return Collections.unmodifiableMap(valores);
    }
}
