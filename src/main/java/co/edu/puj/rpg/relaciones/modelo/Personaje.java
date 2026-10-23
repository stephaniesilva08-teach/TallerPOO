package co.edu.puj.rpg.relaciones.modelo;

public class Personaje {

    private String nombre;
    private int nivel;
    private Arma armaEquipada;   // Asociación unidireccional (0..1)
    private Gremio gremio;       // Asociación bidireccional (0..1 de este lado)
    private final CorazonRPG corazon; // Composición (1..1)

    public Personaje(String nombre, int nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.armaEquipada = null; // Inicialmente desarmado
        this.gremio = null;       // y sin gremio
        // Composición: el corazón se crea AQUÍ, no llega de afuera.
        // Y no hay setCorazon() por ningún lado a propósito.
        this.corazon = new CorazonRPG(100 + nivel * 10, 50 + nivel * 5);
    }

    // =====================================================================
    //  Asociación unidireccional: Personaje -> Arma
    // =====================================================================

    public void equiparArma(Arma nuevaArma) {
        if (nuevaArma == null) {
            System.out.println("No se puede equipar un arma nula.");
            return;
        }
        this.armaEquipada = nuevaArma;
        System.out.printf("%s ahora lleva equipada: %s%n", nombre, nuevaArma);
    }

    public void desequiparArma() {
        if (this.armaEquipada != null) {
            System.out.printf("%s guarda su arma: %s%n", nombre, armaEquipada.getNombre());
            this.armaEquipada = null;
        } else {
            System.out.printf("%s no tiene ningun arma para retirar.%n", nombre);
        }
    }

    public int calcularAtaqueTotal() {
        int ataqueBase = this.nivel * 5;
        // Siempre verifico null antes de pedirle algo al arma (si no: NullPointerException)
        if (this.armaEquipada != null) {
            ataqueBase += this.armaEquipada.getDanioBase();
        }
        return ataqueBase;
    }

    // =====================================================================
    //  Asociación bidireccional: Personaje <-> Gremio
    // =====================================================================

    public void unirseAGremio(Gremio nuevoGremio) {
        // si es null o ya estoy en ese gremio, no hay nada que hacer
        if (nuevoGremio == null || nuevoGremio == this.gremio) {
            return;
        }
        // si ya estaba en otro gremio, primero me salgo de ese
        if (this.gremio != null) {
            abandonarGremio();
        }
        this.gremio = nuevoGremio;
        // sincronización cruzada: que el gremio también me anote
        nuevoGremio.agregarMiembro(this);
    }

    public void abandonarGremio() {
        if (this.gremio == null) {
            return;
        }
        Gremio anterior = this.gremio;
        this.gremio = null;            // primero suelto mi referencia...
        anterior.retirarMiembro(this); // ...y después le aviso al gremio (no hay bucle porque ya soy null)
    }

    // =====================================================================
    //  Delegación al corazón (el objeto corazón nunca sale de esta clase)
    // =====================================================================

    public void recibirDano(int cantidad) {
        corazon.recibirDano(cantidad);
    }

    public void curar(int cantidad) {
        corazon.curar(cantidad);
    }

    public boolean estaVivo() {
        return corazon.getSaludActual() > 0;
    }

    public int getSaludActual() {
        return corazon.getSaludActual();
    }

    public int getSaludBase() {
        return corazon.getSaludBase();
    }

    public int getManaActual() {
        return corazon.getManaActual();
    }

    public int getManaBase() {
        return corazon.getManaBase();
    }

    // =====================================================================
    //  Getters
    // =====================================================================

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public Arma getArmaEquipada() {
        return armaEquipada;
    }

    public Gremio getGremio() {
        return gremio;
    }

    // =====================================================================
    //  Reportes (StringBuilder)
    // =====================================================================

    /**
     * Ojo con esto: del gremio solo pongo el NOMBRE. Si llamara gremio.toString(),
     * y el gremio llamara a toString() de cada miembro, se arma un ciclo infinito
     * y revienta con StackOverflowError.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Personaje{nombre='").append(nombre).append('\'');
        sb.append(", nivel=").append(nivel);
        sb.append(", ataque=").append(calcularAtaqueTotal());
        sb.append(", corazon=[").append(corazon).append(']');
        sb.append(", arma=").append(armaEquipada != null ? armaEquipada.toString() : "desarmado");
        sb.append(", gremio=").append(gremio != null ? gremio.getNombre() : "sin gremio");
        sb.append('}');
        return sb.toString();
    }

    /** Reporte de varias líneas para la opción de estado detallado. */
    public String generarReporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(nombre).append(" ---\n");
        sb.append("  Nivel        : ").append(nivel).append('\n');
        sb.append("  Ataque total : ").append(calcularAtaqueTotal()).append('\n');
        sb.append("  Corazon RPG  : ").append(corazon).append('\n');
        sb.append("  Arma         : ").append(armaEquipada != null ? armaEquipada.toString() : "desarmado").append('\n');
        sb.append("  Gremio       : ").append(gremio != null ? gremio.getNombre() : "sin gremio").append('\n');
        return sb.toString();
    }
}
