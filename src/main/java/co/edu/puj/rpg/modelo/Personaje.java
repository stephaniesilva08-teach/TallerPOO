package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.modelo.enemigo.Enemigo;
public class Personaje {
    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private int nivel;
    private double oro;
    private boolean estaVivo;
    private int experiencia;
    private int ultimoDanoInfligido;
    private boolean ultimoGolpeCritico;

    public Personaje(String nombre, int vidaMaxima, int puntosAtaque, double oro) {
        this.nombre = nombre;
        this.vidaMaxima = (vidaMaxima > 0) ? vidaMaxima : 1;
        this.puntosAtaque = (puntosAtaque > 0) ? puntosAtaque : 1;
        this.nivel = 1;
        this.estaVivo = true;
        this.experiencia = 0;
        this.oro = 0.0;
        this.setPuntosVida(this.vidaMaxima);
        this.setOro(oro);
    }
    public Personaje(String nombre) {
        this(nombre, 100, 15, 50.0);
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
    public boolean setOro(double oro) {
        if (oro < 0.0) {
            System.out.println("Saldo insuficiente");
            return false;
        }
        this.oro = oro;
        return true;
    }
    protected void setNivel(int nivel) {
        if (nivel < 1) {
            return;
        }
        this.nivel = nivel;
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
    public int getNivel() {
        return nivel;
    }
    public double getOro() {
        return oro;
    }
    public boolean isEstaVivo() {
        return estaVivo;
    }
    public int getExperiencia() {
        return experiencia;
    }
    public int getUltimoDanoInfligido() {
        return ultimoDanoInfligido;
    }
    public boolean isUltimoGolpeCritico() {
        return ultimoGolpeCritico;
    }
    public int getExperienciaParaSubir() {
        return nivel * 50;
    }
    public void atacar(Enemigo enemigo) {
        int dano = this.puntosAtaque;
        this.ultimoGolpeCritico = false;
        if (Math.random() < 0.15) {
            dano = dano * 2;
            this.ultimoGolpeCritico = true;
        }
        this.ultimoDanoInfligido = dano;
        enemigo.recibirDano(dano);
    }
    public void usarPocion(int cantidadRestaurada) {
        if (cantidadRestaurada <= 0) {
            return;
        }
        this.setPuntosVida(this.puntosVida + cantidadRestaurada);
    }
    public void recibirDano(int cantidadDano) {
        if (cantidadDano <= 0) {
            return;
        }
        this.setPuntosVida(this.puntosVida - cantidadDano);
    }
    public void ganarExperiencia(int puntos) {
        if (puntos <= 0) {
            return;
        }
        this.experiencia += puntos;
        while (this.experiencia >= this.getExperienciaParaSubir()) {
            this.experiencia -= this.getExperienciaParaSubir();
            this.setNivel(this.nivel + 1);
            this.vidaMaxima += 10;
            this.puntosAtaque += 2;
            this.setPuntosVida(this.vidaMaxima);
        }
    }
}
