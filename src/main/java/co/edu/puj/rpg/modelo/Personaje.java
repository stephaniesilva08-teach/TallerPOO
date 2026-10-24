package co.edu.puj.rpg.modelo;
public abstract class Personaje implements ICombatiente {
    protected String nombre;
    protected int vida;
    protected int ataqueBase;
    protected int posicionX;
    protected int posicionY;
    private final int vidaMaxima;

    public Personaje(String nombre, int vida, int ataqueBase, int posicionX, int posicionY) {
        this.nombre = nombre;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.ataqueBase = ataqueBase;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }
    public abstract void realizarAtaqueEspecial(Personaje objetivo);
    public boolean estaVivo() {
        return vida > 0;
    }
    public void recibirDano(int cantidad) {
        if (cantidad <= 0) {
            return;
        }
        vida -= cantidad;
        if (vida < 0) {
            vida = 0;
        }
    }
    @Override
    public void defender(int danoRecibido) {
        int vidaAntes = vida;
        recibirDano(danoRecibido);
        System.out.printf("  %s recibe %d de dano (vida: %d -> %d)%n",
                nombre, vidaAntes - vida, vidaAntes, vida);
        if (!estaVivo()) {
            System.out.printf("  %s ha caido!%n", nombre);
        }
    }
    public void atacar(Personaje objetivo) {
        atacar(objetivo, false);
    }
    public void atacar(Personaje objetivo, boolean modoFuria) {
        if (objetivo == null || !objetivo.estaVivo()) {
            return;
        }
        int dano = ataqueBase;
        if (modoFuria) {
            dano = ataqueBase * 2;
            System.out.printf("  %s entra en MODO FURIA!%n", nombre);
            if (vida > 5) {
                vida -= 5;
            }
        }
        System.out.printf("  %s ataca a %s%n", nombre, objetivo.getNombre());
        objetivo.defender(dano);
    }

    public void curar(int cantidad) {
        if (!estaVivo() || cantidad <= 0) {
            return;
        }
        int vidaAntes = vida;
        vida = Math.min(vida + cantidad, vidaMaxima);
        System.out.printf("  %s se cura %d puntos (vida: %d -> %d)%n",
                nombre, vida - vidaAntes, vidaAntes, vida);
    }
    public char getSimbolo() {
        return '?';
    }
    public String getEstado() {
        return String.format("%s | Vida: %d/%d", nombre, vida, vidaMaxima);
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getAtaqueBase() {
        return ataqueBase;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicion(int x, int y) {
        this.posicionX = x;
        this.posicionY = y;
    }
}
