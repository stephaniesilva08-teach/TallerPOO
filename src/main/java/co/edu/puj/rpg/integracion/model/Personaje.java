package co.edu.puj.rpg.integracion.model;

import co.edu.puj.rpg.integracion.exception.CapacidadExcedidaException;
import co.edu.puj.rpg.integracion.exception.SinManaException;
import co.edu.puj.rpg.integracion.exception.VidaInsuficienteException;
import co.edu.puj.rpg.integracion.model.enums.EstadoPersonaje;
import co.edu.puj.rpg.integracion.model.enums.TipoPersonaje;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Personaje {

    public static final int CAPACIDAD_MAX_HABILIDADES = 5;
    public static final int CAPACIDAD_MAX_INVENTARIO = 3;

    private static final int VIDA_MAXIMA_BASE = 100;
    private static final int MANA_MAXIMO_BASE = 50;
    private static final int ATAQUE_BASE = 20;
    private static final int DEFENSA_BASE = 10;

    private final String nombre;
    private final TipoPersonaje tipoPersonaje;   // antes era un String suelto
    private EstadoPersonaje estado;              // antes eran banderas boolean
    private int vida;
    private int mana;
    private final Set<String> habilidades;       // HashSet: sin duplicados
    private final List<Item> inventario;

    public Personaje(String nombre, TipoPersonaje tipoPersonaje) {
        if (tipoPersonaje == null) {
            throw new IllegalArgumentException("El tipo de personaje es obligatorio.");
        }
        this.nombre = nombre;
        this.tipoPersonaje = tipoPersonaje;
        this.estado = EstadoPersonaje.VIVO; // estado por defecto
        this.vida = VIDA_MAXIMA_BASE;
        this.mana = MANA_MAXIMO_BASE;
        this.habilidades = new HashSet<>();
        this.inventario = new ArrayList<>();
    }

    // =====================================================================
    //  Estadisticas: usan directamente los bonificadores del enum
    // =====================================================================

    public int calcularAtaque() {
        return (int) Math.round(ATAQUE_BASE * (1 + tipoPersonaje.getBonificadorAtaqueBase()));
    }

    public int calcularDefensa() {
        return (int) Math.round(DEFENSA_BASE * (1 + tipoPersonaje.getBonificadorDefensaBase()));
    }

    // =====================================================================
    //  Set: libro de habilidades
    // =====================================================================

    /**
     * @return true si se aprendio; false si ya la tenia (no toca la coleccion).
     * @throws CapacidadExcedidaException si el libro ya esta lleno.
     */
    public boolean aprenderHabilidad(String habilidad) throws CapacidadExcedidaException {
        if (habilidad == null || habilidad.trim().isEmpty()) {
            return false;
        }
        String limpia = habilidad.trim();
        if (habilidades.contains(limpia)) {
            return false; // duplicado: se rechaza sin cambiar nada
        }
        if (habilidades.size() >= CAPACIDAD_MAX_HABILIDADES) {
            throw new CapacidadExcedidaException("El libro de habilidades de " + nombre
                    + " esta lleno (maximo " + CAPACIDAD_MAX_HABILIDADES + ").");
        }
        return habilidades.add(limpia);
    }

    /** Vista no modificable: nadie de afuera puede meter habilidades saltandose las reglas. */
    public Set<String> obtenerHabilidades() {
        return Collections.unmodifiableSet(habilidades);
    }

    // Alias con nombre de getter (el checklist de depuracion lo usa asi)
    public Set<String> getHabilidades() {
        return obtenerHabilidades();
    }

    // =====================================================================
    //  Inventario
    // =====================================================================

    public void agregarItem(Item item) throws CapacidadExcedidaException {
        if (item == null) {
            return;
        }
        if (inventario.size() >= CAPACIDAD_MAX_INVENTARIO) {
            throw new CapacidadExcedidaException("El inventario de " + nombre
                    + " esta lleno (maximo " + CAPACIDAD_MAX_INVENTARIO + ").");
        }
        inventario.add(item);
    }

    public List<Item> getInventario() {
        return Collections.unmodifiableList(inventario);
    }

    // =====================================================================
    //  Costos de mana y de vida (reglas con excepciones)
    // =====================================================================

    /**
     * Lanza un hechizo gastando mana.
     * @return dano causado por el hechizo.
     * @throws SinManaException (verificada) si costoMana > mana actual.
     */
    public int lanzarHechizo(int costoMana) throws SinManaException {
        if (costoMana <= 0) {
            throw new IllegalArgumentException("El costo de mana debe ser positivo.");
        }
        if (costoMana > mana) {
            throw new SinManaException(nombre + " no tiene mana suficiente: necesita "
                    + costoMana + " y solo tiene " + mana + ".");
        }
        mana -= costoMana;
        return (int) Math.round(calcularAtaque() + costoMana * 1.5);
    }

    /**
     * Gasta vida como costo de una accion.
     * @throws VidaInsuficienteException (no verificada) si costoVida >= vida actual.
     */
    public void consumirVida(int costoVida) {
        if (costoVida >= vida) {
            throw new VidaInsuficienteException(nombre + " no puede pagar " + costoVida
                    + " de vida: solo tiene " + vida + ".");
        }
        vida -= costoVida;
    }

    public void recibirDano(int cantidad) {
        if (cantidad <= 0) {
            return;
        }
        vida = Math.max(0, vida - cantidad);
        if (vida == 0) {
            estado = EstadoPersonaje.DERROTADO;
        }
    }

    // =====================================================================
    //  Getters / estado
    // =====================================================================

    public String getNombre() {
        return nombre;
    }

    public TipoPersonaje getTipoPersonaje() {
        return tipoPersonaje;
    }

    public EstadoPersonaje getEstado() {
        return estado;
    }

    public void setEstado(EstadoPersonaje nuevoEstado) {
        if (nuevoEstado != null) {
            this.estado = nuevoEstado;
        }
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return VIDA_MAXIMA_BASE;
    }

    public int getMana() {
        return mana;
    }

    public int getManaMaximo() {
        return MANA_MAXIMO_BASE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append(" [").append(tipoPersonaje).append(", ").append(estado).append("]");
        sb.append(" Vida ").append(vida).append("/").append(VIDA_MAXIMA_BASE);
        sb.append(" Mana ").append(mana).append("/").append(MANA_MAXIMO_BASE);
        sb.append(" Ataque ").append(calcularAtaque());
        sb.append(" Defensa ").append(calcularDefensa());
        sb.append(" Habilidades ").append(habilidades.size()).append("/").append(CAPACIDAD_MAX_HABILIDADES);
        return sb.toString();
    }
}
