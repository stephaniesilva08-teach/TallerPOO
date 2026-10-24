package co.edu.puj.rpg.integracion.engine;

import co.edu.puj.rpg.integracion.exception.SinManaException;
import co.edu.puj.rpg.integracion.model.Personaje;
import co.edu.puj.rpg.integracion.model.enums.EstadoPersonaje;

public class CombateEngine {

    private static final int DANO_VENENO = 5;

    public int ejecutarAtaqueEspecial(Personaje atacante, int costoMana) throws SinManaException {
        if (atacante.getEstado() == EstadoPersonaje.ATURDIDO) {
            System.out.println(atacante.getNombre() + " esta ATURDIDO y pierde su turno.");
            atacante.setEstado(EstadoPersonaje.VIVO);
            return 0;
        }
        if (!atacante.getEstado().puedeActuar()) {
            System.out.println(atacante.getNombre() + " no puede actuar (" + atacante.getEstado() + ").");
            return 0;
        }
        return atacante.lanzarHechizo(costoMana);
    }

    public void aplicarEfectosDeEstado(Personaje p) {
        if (p.getEstado() == EstadoPersonaje.ENVENENADO) {
            p.recibirDano(DANO_VENENO);
            System.out.println(p.getNombre() + " sufre " + DANO_VENENO + " de dano por veneno.");
        }
    }
}
