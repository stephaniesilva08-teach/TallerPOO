package co.edu.puj.rpg.integracion.model.enums;

/** Ciclo de vida y alteraciones de estado de un heroe en combate. */
public enum EstadoPersonaje {

    VIVO,
    DERROTADO,
    ENVENENADO,
    ATURDIDO;

    /** ATURDIDO pierde el turno y DERROTADO ya no actua; los demas si pueden. */
    public boolean puedeActuar() {
        return this != ATURDIDO && this != DERROTADO;
    }
}
