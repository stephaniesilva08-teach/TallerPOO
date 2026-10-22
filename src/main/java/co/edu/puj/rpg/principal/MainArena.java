package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Guerrero;
import co.edu.puj.rpg.modelo.Inventario;
import co.edu.puj.rpg.modelo.Mago;
import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.Pocion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainArena {

    private static final int TAM = 5;
    private static final char VACIO = '.';
    private static final int COFRE_X = 2; // columna del cofre
    private static final int COFRE_Y = 2; // fila del cofre
    private static final int SIN_ENTRADA = Integer.MIN_VALUE; // cuando ya no hay nada que leer

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random azar = new Random();

        // ---------- 1. Inicialización ----------
        char[][] tablero = new char[TAM][TAM];
        List<Personaje> heroes = new ArrayList<>();
        List<Personaje> enemigos = new ArrayList<>();
        Inventario<Pocion> inventario = new Inventario<>();

        heroes.add(new Guerrero("Thorin", 120, 15, 0, 0, 10));
        heroes.add(new Mago("Gandalf", 80, 25, 1, 1, 50));

        enemigos.add(new Guerrero("Orco", 60, 12, 4, 0, 5));
        enemigos.add(new Mago("Brujo Oscuro", 50, 18, 0, 4, 40));
        enemigos.add(new Guerrero("Uruk-hai", 70, 14, 4, 4, 8));

        inventario.agregar(new Pocion("Pocion Pequena", 30));
        inventario.agregar(new Pocion("Pocion Pequena", 30));
        inventario.agregar(new Pocion("Pocion Grande", 50));

        Personaje heroePrincipal = heroes.get(0); // Thorin es el que tiene que llegar al cofre
        boolean cofreAlcanzado = false;
        boolean salir = false;
        int ronda = 0;

        System.out.println("=== ARENA RPG ===");
        System.out.println("Gana si eliminas a todos los enemigos o si " + heroePrincipal.getNombre()
                + " llega al cofre [C].");

        // ---------- 2. Ciclo principal ----------
        do {
            ronda++;
            System.out.printf("%n########## RONDA %d ##########%n", ronda);

            // ---------- 3. Renderizado y estado ----------
            actualizarTablero(tablero, heroes, enemigos);
            mostrarTablero(tablero);
            mostrarEstado(heroes, enemigos, inventario);

            // ---------- 4. Turno del jugador ----------
            for (Personaje heroe : heroes) {
                if (!heroe.estaVivo()) {
                    continue;
                }
                if (!hayVivos(enemigos) || cofreAlcanzado || salir) {
                    break;
                }

                boolean turnoHecho = false;
                while (!turnoHecho && !salir) {
                    System.out.printf("%n>> Turno de %s%n", heroe.getNombre());
                    System.out.println("1. Atacar (ataque especial)");
                    System.out.println("2. Usar Inventario");
                    System.out.println("3. Mover en Tablero");
                    System.out.print("Opcion: ");

                    int opcion = leerEntero(scanner);
                    if (opcion == SIN_ENTRADA) {
                        salir = true;
                        break;
                    }

                    switch (opcion) {
                        case 1:
                            turnoHecho = atacarEnemigo(scanner, heroe, enemigos);
                            break;
                        case 2:
                            turnoHecho = usarInventario(scanner, heroe, inventario);
                            break;
                        case 3:
                            turnoHecho = moverHeroe(scanner, heroe, heroes, enemigos, tablero);
                            if (turnoHecho && heroe == heroePrincipal
                                    && heroe.getPosicionX() == COFRE_X && heroe.getPosicionY() == COFRE_Y) {
                                cofreAlcanzado = true;
                            }
                            break;
                        default:
                            System.out.println("Opcion invalida, elige 1, 2 o 3.");
                            break;
                    }
                }
                actualizarTablero(tablero, heroes, enemigos);
            }

            // ---------- 5. Turno enemigo ----------
            if (!salir && !cofreAlcanzado && hayVivos(enemigos) && hayVivos(heroes)) {
                turnoEnemigo(enemigos, heroes, azar);
            }

            // ---------- 6. Limpieza de bajas ----------
            heroes.removeIf(p -> !p.estaVivo());
            enemigos.removeIf(p -> !p.estaVivo());

        } while (!heroes.isEmpty() && !enemigos.isEmpty() && !cofreAlcanzado && !salir);

        // ---------- Resultado ----------
        System.out.println("\n================ FIN DE LA PARTIDA ================");
        if (cofreAlcanzado) {
            System.out.println("VICTORIA! " + heroePrincipal.getNombre() + " llego al cofre.");
        } else if (enemigos.isEmpty()) {
            System.out.println("VICTORIA! Todos los enemigos fueron eliminados.");
        } else if (heroes.isEmpty() || sumaVida(heroes) <= 0) {
            System.out.println("DERROTA... tu escuadron cayo en la arena.");
        } else {
            System.out.println("Partida terminada.");
        }
        scanner.close();
    }

    // =====================================================================
    //  Tablero 2D
    // =====================================================================

    /** Dibuja la matriz tal cual la pide la guía (bucles anidados). */
    public static void mostrarTablero(char[][] matriz) {
        System.out.println();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print("[" + matriz[i][j] + "]");
            }
            System.out.println(); // salto de línea por cada fila
        }
        System.out.println("G=Guerrero M=Mago E=Enemigo C=Cofre .=Vacio");
    }

    /**
     * Reconstruye la matriz desde las posiciones reales de cada unidad.
     * Primero todo vacío, luego cofre, enemigos y al final los héroes.
     */
    private static void actualizarTablero(char[][] tablero, List<Personaje> heroes, List<Personaje> enemigos) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = VACIO;
            }
        }
        tablero[COFRE_Y][COFRE_X] = 'C';

        for (Personaje e : enemigos) {
            if (e.estaVivo() && dentroDelTablero(e.getPosicionX(), e.getPosicionY())) {
                tablero[e.getPosicionY()][e.getPosicionX()] = 'E';
            }
        }
        for (Personaje h : heroes) {
            if (h.estaVivo() && dentroDelTablero(h.getPosicionX(), h.getPosicionY())) {
                tablero[h.getPosicionY()][h.getPosicionX()] = h.getSimbolo();
            }
        }
    }

    // Esto me evita el IndexOutOfBoundsException al mover cerca de los bordes
    private static boolean dentroDelTablero(int x, int y) {
        return x >= 0 && x < TAM && y >= 0 && y < TAM;
    }

    private static boolean casillaOcupada(int x, int y, List<Personaje> heroes, List<Personaje> enemigos) {
        for (Personaje h : heroes) {
            if (h.estaVivo() && h.getPosicionX() == x && h.getPosicionY() == y) {
                return true;
            }
        }
        for (Personaje e : enemigos) {
            if (e.estaVivo() && e.getPosicionX() == x && e.getPosicionY() == y) {
                return true;
            }
        }
        return false;
    }

    // =====================================================================
    //  Acciones del jugador
    // =====================================================================

    private static boolean atacarEnemigo(Scanner scanner, Personaje heroe, List<Personaje> enemigos) {
        List<Personaje> vivos = new ArrayList<>();
        for (Personaje e : enemigos) {
            if (e.estaVivo()) {
                vivos.add(e);
            }
        }
        if (vivos.isEmpty()) {
            System.out.println("No quedan enemigos.");
            return false;
        }

        System.out.println("Elige el objetivo:");
        for (int i = 0; i < vivos.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, vivos.get(i).getEstado());
        }
        System.out.print("Objetivo: ");
        int eleccion = leerEntero(scanner);

        if (eleccion < 1 || eleccion > vivos.size()) {
            System.out.println("Objetivo invalido.");
            return false; // no gasta turno
        }

        // Polimorfismo: según sea Guerrero o Mago, se ejecuta su versión
        heroe.realizarAtaqueEspecial(vivos.get(eleccion - 1));
        return true;
    }

    private static boolean usarInventario(Scanner scanner, Personaje heroe, Inventario<Pocion> inventario) {
        if (inventario.estaVacio()) {
            System.out.println("El inventario esta vacio.");
            return false;
        }

        if (heroe.getVida() >= heroe.getVidaMaxima()) {
            // para no gastar una pocion en balde
            System.out.println(heroe.getNombre() + " ya tiene la vida llena.");
            return false;
        }

        System.out.println("Inventario:");
        for (int i = 0; i < inventario.tamano(); i++) {
            Pocion p = inventario.obtener(i); // sin casteo gracias al genérico
            System.out.printf("%d. %s%n", i + 1, p);
        }
        System.out.print("Pocion (0 para cancelar): ");
        int eleccion = leerEntero(scanner);

        if (eleccion == 0) {
            return false;
        }
        if (eleccion < 1 || eleccion > inventario.tamano()) {
            System.out.println("Pocion invalida.");
            return false;
        }

        Pocion elegida = inventario.remover(eleccion - 1);
        heroe.curar(elegida.getCuracion());
        return true;
    }

    private static boolean moverHeroe(Scanner scanner, Personaje heroe, List<Personaje> heroes,
                                      List<Personaje> enemigos, char[][] tablero) {
        System.out.println("1. Arriba  2. Abajo  3. Izquierda  4. Derecha");
        System.out.print("Direccion: ");
        int dir = leerEntero(scanner);

        int nuevoX = heroe.getPosicionX();
        int nuevoY = heroe.getPosicionY();

        switch (dir) {
            case 1:
                nuevoY--;
                break;
            case 2:
                nuevoY++;
                break;
            case 3:
                nuevoX--;
                break;
            case 4:
                nuevoX++;
                break;
            default:
                System.out.println("Direccion invalida.");
                return false;
        }

        if (!dentroDelTablero(nuevoX, nuevoY)) {
            System.out.println("No puedes salir del tablero.");
            return false;
        }
        if (casillaOcupada(nuevoX, nuevoY, heroes, enemigos)) {
            System.out.println("Esa casilla ya esta ocupada.");
            return false;
        }

        heroe.setPosicion(nuevoX, nuevoY);
        System.out.printf("%s se mueve a (x=%d, y=%d)%n", heroe.getNombre(), nuevoX, nuevoY);
        actualizarTablero(tablero, heroes, enemigos);
        mostrarTablero(tablero);
        return true;
    }

    // =====================================================================
    //  IA enemiga
    // =====================================================================

    private static void turnoEnemigo(List<Personaje> enemigos, List<Personaje> heroes, Random azar) {
        System.out.println("\n--- Turno de los enemigos ---");
        for (Personaje enemigo : enemigos) {
            if (!enemigo.estaVivo()) {
                continue;
            }
            List<Personaje> vivos = new ArrayList<>();
            for (Personaje h : heroes) {
                if (h.estaVivo()) {
                    vivos.add(h);
                }
            }
            if (vivos.isEmpty()) {
                break;
            }

            Personaje objetivo = vivos.get(azar.nextInt(vivos.size()));
            int tirada = azar.nextInt(100);
            if (tirada < 35) {
                enemigo.realizarAtaqueEspecial(objetivo);
            } else if (tirada < 55) {
                enemigo.atacar(objetivo, true); // sobrecarga con furia
            } else {
                enemigo.atacar(objetivo); // sobrecarga normal
            }
        }
    }

    // =====================================================================
    //  Utilidades
    // =====================================================================

    private static void mostrarEstado(List<Personaje> heroes, List<Personaje> enemigos, Inventario<Pocion> inv) {
        System.out.println("\n-- Heroes --");
        for (Personaje h : heroes) {
            System.out.println(h.getSimbolo() + " " + h.getEstado());
        }
        System.out.println("-- Enemigos --");
        for (Personaje e : enemigos) {
            System.out.println("E " + e.getEstado());
        }
        System.out.println("Pociones en inventario: " + inv.tamano());
    }

    private static boolean hayVivos(List<Personaje> lista) {
        for (Personaje p : lista) {
            if (p.estaVivo()) {
                return true;
            }
        }
        return false;
    }

    private static int sumaVida(List<Personaje> lista) {
        int total = 0;
        for (Personaje p : lista) {
            total += p.getVida();
        }
        return total;
    }

    /** Lee un entero; si escriben letras devuelve -1 en vez de romper el programa. */
    private static int leerEntero(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return SIN_ENTRADA;
        }
        String linea = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linea);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}
