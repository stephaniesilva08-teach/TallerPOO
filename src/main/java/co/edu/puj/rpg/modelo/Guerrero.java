package co.edu.puj.rpg.modelo;

public class Guerrero extends Personaje {

    protected int fuerzaEscudo;

    public Guerrero(String nombre, int vida, int ataqueBase, int posX, int posY, int fuerzaEscudo) {
        super(nombre, vida, ataqueBase, posX, posY);
        this.fuerzaEscudo = fuerzaEscudo;
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        if (objetivo == null || !objetivo.estaVivo()) {
            return;
        }
        int dano = ataqueBase + fuerzaEscudo;
        System.out.printf("  %s lanza un GOLPE DE ESCUDO a %s%n", nombre, objetivo.getNombre());
        objetivo.defender(dano);
    }
    @Override
    public void defender(int danoRecibido) {
        int reducido = Math.max(1, danoRecibido - fuerzaEscudo / 2);
        if (reducido < danoRecibido) {
            System.out.printf("  El escudo de %s bloquea %d de dano%n", nombre, danoRecibido - reducido);
        }
        super.defender(reducido);
    }
    @Override
    public char getSimbolo() {
        return 'G';
    }
    @Override
    public String getEstado() {
        return super.getEstado() + " | Escudo: " + fuerzaEscudo;
    }
    public int getFuerzaEscudo() {
        return fuerzaEscudo;
    }
}
