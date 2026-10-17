package co.edu.puj.rpg.modelo.enemigo;

import co.edu.puj.rpg.modelo.Personaje;
public class Enemigo {
    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private boolean estaVivo;
    private int experienciaRecompensa;
    private double oroRecompensa;

    public Enemigo(String nombre, int vidaMaxima, int puntosAtaque, int experienciaRecompensa, double oroRecompensa) {
        this.nombre = nombre;
        this.vidaMaxima = (vidaMaxima > 0) ? vidaMaxima : 1;
        this.puntosAtaque = (puntosAtaque > 0) ? puntosAtaque : 1;
        this.experienciaRecompensa = Math.max(experienciaRecompensa, 0);
        this.oroRecompensa = Math.max(oroRecompensa, 0.0);
        this.estaVivo = true;
        this.setPuntosVida(this.vidaMaxima);
    }
    public void setPuntosVida(int vida) {
        if (vida <= 0) {
            this.puntosVida = 0;
            this.estaVivo = false;
        } else if (vida > this.vidaMaxima) {
            this.puntosVida = this.vidaMaxima;
            this.estaVivo = true;
        } else {
            this.puntosVida = vida;
            this.estaVivo = true;
        }
    }
    public String getNombre() {
        return nombre;
    }
    public int getPuntosVida() {
        return puntosVida;
    }
    public int getVidaMaxima() {
        return vidaMaxima;
    }
    public int getPuntosAtaque() {
        return puntosAtaque;
    }
    public boolean isEstaVivo() {
        return estaVivo;
    }
    public int getExperienciaRecompensa() {
        return experienciaRecompensa;
    }
    public double getOroRecompensa() {
        return oroRecompensa;
    }
    public void recibirDano(int cantidadDano) {
        if (cantidadDano <= 0) {
            return;
        }
        this.setPuntosVida(this.puntosVida - cantidadDano);
    }
    public void atacar(Personaje heroe) {
        heroe.recibirDano(this.puntosAtaque);
    }
}
