package co.edu.puj.rpg.integracion;

import co.edu.puj.rpg.integracion.engine.Bestiario;
import co.edu.puj.rpg.integracion.engine.CombateEngine;
import co.edu.puj.rpg.integracion.engine.EstadisticasCombate;
import co.edu.puj.rpg.integracion.exception.CapacidadExcedidaException;
import co.edu.puj.rpg.integracion.exception.SinManaException;
import co.edu.puj.rpg.integracion.exception.VidaInsuficienteException;
import co.edu.puj.rpg.integracion.model.PerfilJugador;
import co.edu.puj.rpg.integracion.model.Personaje;
import co.edu.puj.rpg.integracion.model.enums.TipoPersonaje;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    private static final int SIN_ENTRADA = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Personaje> personajes = new ArrayList<>();
        Bestiario bestiario = new Bestiario();
        PerfilJugador perfil = new PerfilJugador("Jugador 1");
        EstadisticasCombate estadisticas = new EstadisticasCombate();
        CombateEngine engine = new CombateEngine();

        int opcion;
        do {
            System.out.println("\n=== ARENA DE HEROES RPG ===");
            System.out.println("1. Crear Personaje");
            System.out.println("2. Asignar Habilidad Unica");
            System.out.println("3. Registrar Victoria en Bestiario");
            System.out.println("4. Ejecutar Ataque Especial");
            System.out.println("5. Consultar Bestiario y Logros");
            System.out.println("0. Salir");

            opcion = leerEntero(scanner, "Seleccione una opcion: ");
            if (opcion == SIN_ENTRADA) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    crearPersonaje(scanner, personajes);
                    break;
                case 2:
                    asignarHabilidad(scanner, personajes);
                    break;
                case 3:
                    registrarVictoria(scanner, bestiario, perfil, estadisticas);
                    break;
                case 4:
                    ejecutarAtaqueEspecial(scanner, personajes, engine, estadisticas);
                    break;
                case 5:
                    consultar(bestiario, perfil, estadisticas);
                    break;
                case 0:
                    System.out.println("Cerrando la Arena de Heroes...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);

        scanner.close();
    }
    private static void crearPersonaje(Scanner scanner, List<Personaje> personajes) {
        String nombre = leerTexto(scanner, "Nombre del personaje: ");
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }
        if (buscarPorNombre(personajes, nombre) != null) {
            System.out.println("Ya existe un personaje con ese nombre.");
            return;
        }

        TipoPersonaje[] tipos = TipoPersonaje.values();
        System.out.println("Tipos disponibles:");
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("%d. %s (ataque +%.0f%%, defensa +%.0f%%)%n", i + 1, tipos[i],
                    tipos[i].getBonificadorAtaqueBase() * 100, tipos[i].getBonificadorDefensaBase() * 100);
        }
        int eleccion = leerEntero(scanner, "Elige el tipo: ");
        if (eleccion < 1 || eleccion > tipos.length) {
            System.out.println("Tipo invalido. No se creo el personaje.");
            return;
        }

        Personaje nuevo = new Personaje(nombre, tipos[eleccion - 1]);
        personajes.add(nuevo);
        System.out.println("Personaje creado: " + nuevo);
    }
    private static void asignarHabilidad(Scanner scanner, List<Personaje> personajes) {
        Personaje p = seleccionarPersonaje(scanner, personajes);
        if (p == null) {
            return;
        }
        String habilidad = leerTexto(scanner, "Nombre de la habilidad: ");
        if (habilidad.isEmpty()) {
            System.out.println("La habilidad no puede estar vacia.");
            return;
        }

        try {
            if (p.aprenderHabilidad(habilidad)) {
                System.out.println(p.getNombre() + " aprendio: " + habilidad);
            } else {
                System.out.println("Aviso: " + p.getNombre() + " ya tiene aprendida la habilidad '" + habilidad + "'.");
            }
        } catch (CapacidadExcedidaException e) {
            System.out.println("No se pudo aprender: " + e.getMessage());
        }
        System.out.println("Habilidades de " + p.getNombre() + ": " + p.obtenerHabilidades());
    }
    private static void registrarVictoria(Scanner scanner, Bestiario bestiario, PerfilJugador perfil,
                                          EstadisticasCombate estadisticas) {
        String enemigo = leerTexto(scanner, "Nombre del enemigo derrotado: ");
        if (enemigo.isEmpty()) {
            System.out.println("El nombre del enemigo no puede estar vacio.");
            return;
        }

        boolean primera = bestiario.registrarVictoria(enemigo);
        estadisticas.incrementar("Victorias totales", 1);
        System.out.printf("Victorias contra %s: %d%n", enemigo, bestiario.getVictorias(enemigo));

        if (primera) {
            String logro = "Cazador de " + normalizarNombre(enemigo);
            if (perfil.registrarLogro(logro)) {
                System.out.println("LOGRO DESBLOQUEADO: " + logro);
            }
        }
    }
    private static void ejecutarAtaqueEspecial(Scanner scanner, List<Personaje> personajes,
                                               CombateEngine engine, EstadisticasCombate estadisticas) {
        Personaje p = seleccionarPersonaje(scanner, personajes);
        if (p == null) {
            return;
        }
        int costo = leerEntero(scanner, "Costo de mana del ataque: ");
        if (costo <= 0) {
            System.out.println("El costo debe ser un numero positivo.");
            return;
        }

        try {
            int dano = engine.ejecutarAtaqueEspecial(p, costo);
            if (dano > 0) {
                System.out.printf("%s lanza su ataque especial y causa %d de dano. Mana restante: %d/%d%n",
                        p.getNombre(), dano, p.getMana(), p.getManaMaximo());
                estadisticas.incrementar("Ataques especiales", 1);
                estadisticas.incrementar("Dano total infligido", dano);
                estadisticas.incrementar("Mana total gastado", costo);
            }
            engine.aplicarEfectosDeEstado(p);
        } catch (SinManaException e) {
            System.out.println("Ataque fallido: " + e.getMessage());
        } catch (VidaInsuficienteException e) {
            System.out.println("Accion imposible: " + e.getMessage());
        }
    }
    private static void consultar(Bestiario bestiario, PerfilJugador perfil, EstadisticasCombate estadisticas) {
        System.out.println("\n===== BESTIARIO =====");
        if (bestiario.estaVacio()) {
            System.out.println("(aun no hay victorias registradas)");
        }
        for (Map.Entry<String, Integer> entry : bestiario.getRegistro().entrySet()) {
            System.out.printf("%-20s derrotado %d vez/veces%n", entry.getKey(), entry.getValue());
        }

        System.out.println("\n===== LOGROS (orden alfabetico) =====");
        if (perfil.listarLogrosOrdenados().isEmpty()) {
            System.out.println("(sin logros)");
        }
        for (String logro : perfil.listarLogrosOrdenados()) {
            System.out.println("- " + logro);
        }

        System.out.println("\n===== ESTADISTICAS =====");
        if (estadisticas.getValores().isEmpty()) {
            System.out.println("(sin estadisticas)");
        }
        for (Map.Entry<String, Double> entry : estadisticas.getValores().entrySet()) {
            System.out.printf("%-24s %.2f%n", entry.getKey(), entry.getValue());
        }
    }
    private static Personaje seleccionarPersonaje(Scanner scanner, List<Personaje> personajes) {
        if (personajes.isEmpty()) {
            System.out.println("Primero crea un personaje (opcion 1).");
            return null;
        }
        if (personajes.size() == 1) {
            return personajes.get(0);
        }
        System.out.println("Personajes:");
        for (int i = 0; i < personajes.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, personajes.get(i).getNombre());
        }
        int idx = leerEntero(scanner, "Selecciona el personaje: ");
        if (idx < 1 || idx > personajes.size()) {
            System.out.println("Seleccion invalida.");
            return null;
        }
        return personajes.get(idx - 1);
    }

    private static Personaje buscarPorNombre(List<Personaje> lista, String nombre) {
        for (Personaje p : lista) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    private static String normalizarNombre(String texto) {
        String limpio = texto.trim().replaceAll("\\s+", " ");
        return limpio.substring(0, 1).toUpperCase() + limpio.substring(1).toLowerCase();
    }
    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.err.println("Entrada invalida: escribe un numero entero. Intenta de nuevo.");
            } catch (NoSuchElementException e) {
                return SIN_ENTRADA;
            } finally {
                System.out.flush();
            }
        }
    }

    private static String leerTexto(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        if (!scanner.hasNextLine()) {
            return "";
        }
        return scanner.nextLine().trim();
    }
}
