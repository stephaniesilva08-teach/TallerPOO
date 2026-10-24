package co.edu.puj.rpg.integracion.model;

import co.edu.puj.rpg.integracion.model.enums.RaridadItem;

public class Item {

    private final String nombre;
    private final double valorBase;
    private final RaridadItem rareza;

    public Item(String nombre, double valorBase, RaridadItem rareza) {
        if (rareza == null) {
            throw new IllegalArgumentException("La rareza del item no puede ser null.");
        }
        this.nombre = nombre;
        this.valorBase = valorBase;
        this.rareza = rareza;
    }

    public String getNombre() {
        return nombre;
    }

    public RaridadItem getRareza() {
        return rareza;
    }

    /** Valor base escalado por el multiplicador de la rareza (lo saca del enum, sin if ni switch). */
    public double getValorFinal() {
        return valorBase * rareza.getMultiplicadorStat();
    }

    @Override
    public String toString() {
        return nombre + " [" + rareza + ", valor " + String.format("%.2f", getValorFinal()) + "]";
    }
}
