package co.edu.puj.rpg.relaciones.modelo;

public class Personaje {

    private String nombre;
    private int nivel;
    private Arma armaEquipada;
    private Gremio gremio;
    private final CorazonRPG corazon;
    public Personaje(String nombre, int nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.armaEquipada = null;
        this.gremio = null;
        this.corazon = new CorazonRPG(100 + nivel * 10, 50 + nivel * 5);
    }
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
        if (this.armaEquipada != null) {
            ataqueBase += this.armaEquipada.getDanioBase();
        }
        return ataqueBase;
    }
    public void unirseAGremio(Gremio nuevoGremio) {
        if (nuevoGremio == null || nuevoGremio == this.gremio) {
            return;
        }
        if (this.gremio != null) {
            abandonarGremio();
        }
        this.gremio = nuevoGremio;
        nuevoGremio.agregarMiembro(this);
    }

    public void abandonarGremio() {
        if (this.gremio == null) {
            return;
        }
        Gremio anterior = this.gremio;
        this.gremio = null;
        anterior.retirarMiembro(this);
    }
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
