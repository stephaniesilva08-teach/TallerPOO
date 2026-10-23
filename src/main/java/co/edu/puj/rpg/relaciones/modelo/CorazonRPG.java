package co.edu.puj.rpg.relaciones.modelo;

/**
 * Núcleo de atributos vitales del Personaje (composición 1..1).
 * Solo Personaje lo crea (dentro de su constructor) y nadie de afuera
 * recibe la referencia, así el corazón vive y muere con su personaje.
 */
public class CorazonRPG {

    private int saludBase;
    private int manaBase;
    private int saludActual;
    private int manaActual;

    public CorazonRPG(int saludBase, int manaBase) {
        this.saludBase = saludBase;
        this.manaBase = manaBase;
        // al nacer arranca lleno
        this.saludActual = saludBase;
        this.manaActual = manaBase;
    }

    public int getSaludBase() {
        return saludBase;
    }

    public int getManaBase() {
        return manaBase;
    }

    public int getSaludActual() {
        return saludActual;
    }

    public int getManaActual() {
        return manaActual;
    }

    // ---------- manipulación interna de vida y maná ----------

    public void recibirDano(int cantidad) {
        if (cantidad <= 0) {
            return;
        }
        saludActual = Math.max(0, saludActual - cantidad);
    }

    public void curar(int cantidad) {
        if (cantidad <= 0) {
            return;
        }
        saludActual = Math.min(saludBase, saludActual + cantidad);
    }

    /** @return true si alcanzó el maná, false si no (y no gasta nada). */
    public boolean gastarMana(int cantidad) {
        if (cantidad <= 0 || cantidad > manaActual) {
            return false;
        }
        manaActual -= cantidad;
        return true;
    }

    public void recuperarMana(int cantidad) {
        if (cantidad <= 0) {
            return;
        }
        manaActual = Math.min(manaBase, manaActual + cantidad);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Salud ").append(saludActual).append("/").append(saludBase)
                .append(", Mana ").append(manaActual).append("/").append(manaBase)
                .toString();
    }
}
