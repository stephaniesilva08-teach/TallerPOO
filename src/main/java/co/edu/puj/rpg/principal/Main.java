package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.enemigo.Enemigo;

import java.util.Scanner;

public class Main {

    private static final int CURACION_POCION_PEQUENA = 30;
    private static final int CURACION_POCION_GRANDE = 70;
    private static final double COSTO_POCION_GRANDE = 40.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Personaje heroe = new Personaje("Aragorn", 100, 15, 100.0);
        Enemigo enemigo = new Enemigo("Orco Bruto", 80, 12, 60, 35.0);
        StringBuilder historial = new StringBuilder();
        int pocionesPequenas = 2;
        int pocionesGrandes = 0;
        boolean huyo = false;
        int ronda = 0;
        System.out.println(" ARENA DE HEROES ");
        System.out.printf("%s (nivel %d) se enfrenta a %s%n", heroe.getNombre(), heroe.getNivel(), enemigo.getNombre());
        do {
            ronda++;
            System.out.printf("%n--- Ronda %d ---%n", ronda);
            System.out.println("1. Atacar");
            System.out.println("2. Usar Pocion");
            System.out.println("3. Ver Estado");
            System.out.println("4. Huir");
            System.out.println("5. Tienda");
            System.out.print("Elige una opcion: ");

            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Entrada invalida: escribe un numero del 1 al 5.");
                ronda--;
                continue;
            }
            int opcion = scanner.nextInt();
            scanner.nextLine();
            boolean consumeTurno = false;
            switch (opcion) {
                case 1:
                    heroe.atacar(enemigo);
                    if (heroe.isUltimoGolpeCritico()) {
                        historial.append(String.format("[Ronda %d] GOLPE CRITICO! %s inflige %d de dano a %s.%n",
                                ronda, heroe.getNombre(), heroe.getUltimoDanoInfligido(), enemigo.getNombre()));
                    } else {
                        historial.append(String.format("[Ronda %d] %s inflige %d de dano a %s.%n",
                                ronda, heroe.getNombre(), heroe.getUltimoDanoInfligido(), enemigo.getNombre()));
                    }
                    consumeTurno = true;
                    break;
                case 2:
                    if (pocionesGrandes == 0 && pocionesPequenas == 0) {
                        System.out.println("No tienes pociones. Visita la tienda (opcion 5).");
                        historial.append(String.format("[Ronda %d] Intento de usar pocion sin inventario.%n", ronda));
                    } else {
                        int vidaAntes = heroe.getPuntosVida();
                        if (pocionesGrandes > 0) {
                            heroe.usarPocion(CURACION_POCION_GRANDE);
                            pocionesGrandes--;
                        } else {
                            heroe.usarPocion(CURACION_POCION_PEQUENA);
                            pocionesPequenas--;
                        }
                        historial.append(String.format("[Ronda %d] %s se cura %d puntos de vida.%n",
                                ronda, heroe.getNombre(), heroe.getPuntosVida() - vidaAntes));
                        consumeTurno = true;
                    }
                    break;
                case 3:
                    mostrarEstado(heroe, enemigo, pocionesPequenas, pocionesGrandes);
                    break;
                case 4:
                    huyo = true;
                    historial.append(String.format("[Ronda %d] %s huye del combate.%n", ronda, heroe.getNombre()));
                    break;
                case 5:
                    menuTienda(heroe);
                    pocionesGrandes += comprarEnTienda(scanner, heroe, historial, ronda);
                    break;
                default:
                    System.out.println("Opcion invalida: elige un numero del 1 al 5.");
                    break;
            }
            if (consumeTurno && enemigo.isEstaVivo()) {
                int vidaAntes = heroe.getPuntosVida();
                enemigo.atacar(heroe);
                historial.append(String.format("[Ronda %d] %s inflige %d de dano a %s.%n",
                        ronda, enemigo.getNombre(), vidaAntes - heroe.getPuntosVida(), heroe.getNombre()));
            }
            if (!enemigo.isEstaVivo()) {
                historial.append(String.format("[Ronda %d] %s ha sido derrotado.%n", ronda, enemigo.getNombre()));
            }
            if (!heroe.isEstaVivo()) {
                historial.append(String.format("[Ronda %d] %s ha caido en combate.%n", ronda, heroe.getNombre()));
            }
            System.out.println(" HISTORIAL DE BATALLA ");
            System.out.print(historial);

        } while (heroe.isEstaVivo() && enemigo.isEstaVivo() && !huyo);

        System.out.println(" FIN DEL COMBATE ");
        if (!enemigo.isEstaVivo() && heroe.isEstaVivo()) {
            int nivelAntes = heroe.getNivel();
            heroe.ganarExperiencia(enemigo.getExperienciaRecompensa());
            heroe.setOro(heroe.getOro() + enemigo.getOroRecompensa());
            System.out.printf("Victoria! Ganas %d XP y %.2f de oro.%n",
                    enemigo.getExperienciaRecompensa(), enemigo.getOroRecompensa());
            if (heroe.getNivel() > nivelAntes) {
                System.out.printf("SUBES DE NIVEL: %d -> %d%n", nivelAntes, heroe.getNivel());
            }
            mostrarEstado(heroe, enemigo, pocionesPequenas, pocionesGrandes);
        } else if (!heroe.isEstaVivo()) {
            System.out.println("Derrota... el heroe ha caido.");
        } else {
            System.out.println("Has huido de la arena.");
        }
        scanner.close();
    }
    private static void mostrarEstado(Personaje heroe, Enemigo enemigo, int pequenas, int grandes) {
        System.out.println(" ESTADO ");
        System.out.printf("Heroe : %s | Nivel: %d | Vida: %d/%d | Ataque: %d%n",
                heroe.getNombre(), heroe.getNivel(), heroe.getPuntosVida(), heroe.getVidaMaxima(), heroe.getPuntosAtaque());
        System.out.printf("Oro   : %.2f | XP: %d/%d | Vivo: %s%n",
                heroe.getOro(), heroe.getExperiencia(), heroe.getExperienciaParaSubir(), heroe.isEstaVivo());
        System.out.printf("Pociones pequenas: %d | Pociones grandes: %d%n", pequenas, grandes);
        System.out.printf("Enemigo: %s | Vida: %d/%d | Vivo: %s%n",
                enemigo.getNombre(), enemigo.getPuntosVida(), enemigo.getVidaMaxima(), enemigo.isEstaVivo());
    }
    private static void menuTienda(Personaje heroe) {
        System.out.println(" TIENDA ");
        System.out.printf("Oro disponible: %.2f%n", heroe.getOro());
        System.out.printf("1. Pocion Grande de Salud (+%d vida) - %.2f de oro%n", CURACION_POCION_GRANDE, COSTO_POCION_GRANDE);
        System.out.println("2. Salir de la tienda");
        System.out.print("Elige una opcion: ");
    }
    private static int comprarEnTienda(Scanner scanner, Personaje heroe, StringBuilder historial, int ronda) {
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Entrada invalida en la tienda.");
            return 0;
        }
        int opcion = scanner.nextInt();
        scanner.nextLine();

        if (opcion == 1) {
            double nuevoSaldo = heroe.getOro() - COSTO_POCION_GRANDE;
            if (heroe.setOro(nuevoSaldo)) {
                System.out.printf("Compra exitosa. Saldo: %.2f%n", heroe.getOro());
                historial.append(String.format("[Ronda %d] Compra una Pocion Grande de Salud.%n", ronda));
                return 1;
            }
            historial.append(String.format("[Ronda %d] Compra rechazada por saldo insuficiente.%n", ronda));
            return 0;
        } else if (opcion == 2) {
            System.out.println("Sales de la tienda.");
        } else {
            System.out.println("Opcion invalida");
        }
        return 0;
    }
}
