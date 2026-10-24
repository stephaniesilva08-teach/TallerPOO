package co.edu.puj.rpg.modelo;

public class Mago extends Personaje {

    private static final int COSTO_ESPECIAL = 15;

    protected int puntosMana;

    public Mago(String nombre, int vida, int ataqueBase, int posX, int posY, int puntosMana) {
        super(nombre, vida, ataqueBase, posX, posY);
        this.puntosMana = puntosMana;
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        if (objetivo == null || !objetivo.estaVivo()) {
            return;
        }
        if (puntosMana >= COSTO_ESPECIAL) {
            puntosMana -= COSTO_ESPECIAL;
            int dano = ataqueBase * 2;
            System.out.printf("  %s lanza una BOLA DE FUEGO a %s (mana restante: %d)%n",
                    nombre, objetivo.getNombre(), puntosMana);
            objetivo.defender(dano);
        } else {
            System.out.printf("  %s no tiene mana suficiente, ataca normal%n", nombre);
            puntosMana += 5;
            atacar(objetivo);
        }
    }

    @Override
    public char getSimbolo() {
        return 'M';
    }
    @Override
    public String getEstado() {
        return super.getEstado() + " | Mana: " + puntosMana;
    }
    public int getPuntosMana() {
        return puntosMana;
    }
}
