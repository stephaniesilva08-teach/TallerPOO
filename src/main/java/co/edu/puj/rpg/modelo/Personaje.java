package co.edu.puj.rpg.modelo;

/**
 * Clase base abstracta de los combatientes (heroes y enemigos).
 * Guarda el estado común y la lógica que Guerrero y Mago comparten,
 * pero NO se puede hacer new Personaje(...) porque es abstracta.
 */
public abstract class Personaje implements ICombatiente {

    // Atributos protegidos: las subclases los ven directo, el resto usa getters
    protected String nombre;
    protected int vida;
    protected int ataqueBase;
    protected int posicionX; // horizontal -> columna del tablero
    protected int posicionY; // vertical   -> fila del tablero

    // Esta no viene en el UML, pero la necesito para que las pociones no
    // dejen la vida por encima de la que el personaje tenía al empezar
    private final int vidaMaxima;

    public Personaje(String nombre, int vida, int ataqueBase, int posicionX, int posicionY) {
        this.nombre = nombre;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.ataqueBase = ataqueBase;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    // Cada subclase decide cómo es su ataque único (por eso no lleva cuerpo)
    public abstract void realizarAtaqueEspecial(Personaje objetivo);

    public boolean estaVivo() {
        return vida > 0;
    }

    public void recibirDano(int cantidad) {
        if (cantidad <= 0) {
            return; // un daño negativo curaría, y eso no queremos
        }
        vida -= cantidad;
        if (vida < 0) {
            vida = 0; // ojo: nunca puede bajar de cero
        }
    }

    // Implementación base de la interfaz; Guerrero la sobreescribe con su escudo
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

    // ---------- Sobrecarga: mismo nombre, distintos parámetros ----------

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
                vida -= 5; // la furia cuesta un poquito de vida
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

    // Letra que se dibuja en el tablero; cada subclase pone la suya
    public char getSimbolo() {
        return '?';
    }

    // Texto para mostrar las estadísticas; Mago y Guerrero le agregan lo suyo
    public String getEstado() {
        return String.format("%s | Vida: %d/%d", nombre, vida, vidaMaxima);
    }

    // ---------- Getters y posición ----------

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
