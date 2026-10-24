package co.edu.puj.rpg.relaciones.modelo;

public class CorazonRPG {

    private int saludBase;
    private int manaBase;
    private int saludActual;
    private int manaActual;

    public CorazonRPG(int saludBase, int manaBase) {
        this.saludBase = saludBase;
        this.manaBase = manaBase;
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
