package co.edu.puj.rpg.modelo;

/**
 * Contrato de comportamiento: cualquier cosa que pueda pelear tiene que
 * saber defenderse. Aquí no hay atributos, solo la "promesa" del método.
 */
public interface ICombatiente {

    // Procesa el daño que llega, aplicando la armadura/mitigación de cada quien
    void defender(int danoRecibido);
}
