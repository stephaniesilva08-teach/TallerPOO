package co.edu.puj.rpg.relaciones.principal;

import co.edu.puj.rpg.relaciones.modelo.Arma;
import co.edu.puj.rpg.relaciones.modelo.Escuadron;
import co.edu.puj.rpg.relaciones.modelo.Gremio;
import co.edu.puj.rpg.relaciones.modelo.Personaje;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AplicacionArenaRPG {

    private static final int SIN_ENTRADA = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        List<Personaje> personajes = new ArrayList<>();
        List<Gremio> gremios = new ArrayList<>();
        List<Escuadron> escuadrones = new ArrayList<>();

        do {
            System.out.println("\n=== MENÚ ARENA DE HÉROES RPG ===");
            System.out.println("1. Crear Personaje");
            System.out.println("2. Crear Gremio");
            System.out.println("3. Reclutar Personaje a Escuadrón");
            System.out.println("4. Equipar Arma a Personaje");
            System.out.println("5. Mostrar Estado Detallado (Personaje y Corazón RPG)");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero(scanner);
            if (opcion == SIN_ENTRADA) {
                opcion = 6;
            }

            switch (opcion) {
                case 1:
                    crearPersonaje(scanner, personajes);
                    break;
                case 2:
                    gestionarGremio(scanner, gremios, personajes);
                    break;
                case 3:
                    gestionarEscuadron(scanner, escuadrones, personajes);
                    break;
                case 4:
                    gestionarArma(scanner, personajes);
                    break;
                case 5:
                    mostrarEstado(personajes, gremios, escuadrones);
                    break;
                case 6:
                    System.out.println("Cerrando la Arena de Héroes...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 6);

        scanner.close();
    }
    private static void crearPersonaje(Scanner scanner, List<Personaje> personajes) {
        System.out.print("Nombre del personaje: ");
        String nombre = leerTexto(scanner);
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        if (buscarPersonaje(personajes, nombre) != null) {
            System.out.println("Ya existe un personaje con ese nombre.");
            return;
        }

        System.out.print("Nivel (1 a 99): ");
        int nivel = leerEntero(scanner);
        if (nivel < 1 || nivel > 99) {
            System.out.println("Nivel inválido. No se creó el personaje.");
            return;
        }

        Personaje nuevo = new Personaje(nombre, nivel);
        personajes.add(nuevo);
        System.out.println("Personaje creado: " + nuevo);
    }
    private static void gestionarGremio(Scanner scanner, List<Gremio> gremios, List<Personaje> personajes) {
        System.out.println("1. Crear gremio nuevo");
        System.out.println("2. Inscribir personaje en un gremio existente");
        System.out.print("Opción: ");
        int sub = leerEntero(scanner);

        if (sub == 1) {
            System.out.print("Nombre del gremio: ");
            String nombre = leerTexto(scanner);
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
                return;
            }
            if (buscarGremio(gremios, nombre) != null) {
                System.out.println("Ya existe un gremio con ese nombre.");
                return;
            }
            Gremio nuevo = new Gremio(nombre);
            gremios.add(nuevo);
            System.out.println("Gremio creado: " + nuevo);

        } else if (sub == 2) {
            if (gremios.isEmpty() || personajes.isEmpty()) {
                System.out.println("Necesitas al menos un gremio y un personaje.");
                return;
            }
            Personaje p = seleccionarPersonaje(scanner, personajes);
            if (p == null) {
                return;
            }
            System.out.println("Gremios disponibles:");
            for (int i = 0; i < gremios.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, gremios.get(i).getNombre());
            }
            System.out.print("Gremio: ");
            int idx = leerEntero(scanner);
            if (idx < 1 || idx > gremios.size()) {
                System.out.println("Gremio inválido.");
                return;
            }
            Gremio g = gremios.get(idx - 1);

            if (p.getGremio() == g) {
                System.out.println(p.getNombre() + " ya pertenece a " + g.getNombre() + ".");
                return;
            }
            if (p.getGremio() != null) {
                System.out.println(p.getNombre() + " deja el gremio " + p.getGremio().getNombre() + ".");
            }
            p.unirseAGremio(g);
            System.out.println(p.getNombre() + " ahora pertenece a " + g.getNombre() + ".");
            System.out.println(g);

        } else {
            System.out.println("Opción inválida.");
        }
    }
    private static void gestionarEscuadron(Scanner scanner, List<Escuadron> escuadrones, List<Personaje> personajes) {
        if (personajes.isEmpty()) {
            System.out.println("Primero crea al menos un personaje (opción 1).");
            return;
        }

        if (!escuadrones.isEmpty()) {
            System.out.println("Escuadrones existentes:");
            for (Escuadron e : escuadrones) {
                System.out.println("  - " + e);
            }
        }
        System.out.print("Nombre del escuadrón (si no existe, se crea): ");
        String nombre = leerTexto(scanner);
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        Escuadron escuadron = buscarEscuadron(escuadrones, nombre);
        if (escuadron == null) {
            escuadron = new Escuadron(nombre);
            escuadrones.add(escuadron);
            System.out.println("Escuadrón creado: " + nombre);
        }

        System.out.println("1. Reclutar personaje");
        System.out.println("2. Expulsar personaje");
        System.out.print("Opción: ");
        int sub = leerEntero(scanner);

        if (sub == 1) {
            Personaje p = seleccionarPersonaje(scanner, personajes);
            if (p != null) {
                escuadron.reclutarPersonaje(p);
            }
        } else if (sub == 2) {
            if (escuadron.getCantidadMiembros() == 0) {
                System.out.println("Ese escuadrón no tiene miembros.");
                return;
            }
            Personaje p = seleccionarPersonaje(scanner, escuadron.getMiembros());
            if (p != null) {
                escuadron.expulsarPersonaje(p);
            }
        } else {
            System.out.println("Opción inválida.");
        }
        System.out.println(escuadron);
    }
    private static void gestionarArma(Scanner scanner, List<Personaje> personajes) {
        if (personajes.isEmpty()) {
            System.out.println("Primero crea al menos un personaje (opción 1).");
            return;
        }
        Personaje p = seleccionarPersonaje(scanner, personajes);
        if (p == null) {
            return;
        }

        System.out.println("1. Equipar un arma nueva");
        System.out.println("2. Desequipar el arma actual");
        System.out.print("Opción: ");
        int sub = leerEntero(scanner);

        if (sub == 1) {
            System.out.print("Nombre del arma: ");
            String nombreArma = leerTexto(scanner);
            if (nombreArma.isEmpty()) {
                System.out.println("El nombre del arma no puede estar vacío.");
                return;
            }
            System.out.print("Daño base del arma (0 o más): ");
            int danio = leerEntero(scanner);
            if (danio < 0) {
                System.out.println("Daño inválido. No se equipó el arma.");
                return;
            }
            p.equiparArma(new Arma(nombreArma, danio));
            System.out.println("Ataque total de " + p.getNombre() + ": " + p.calcularAtaqueTotal());
        } else if (sub == 2) {
            p.desequiparArma();
        } else {
            System.out.println("Opción inválida.");
        }
    }
    private static void mostrarEstado(List<Personaje> personajes, List<Gremio> gremios, List<Escuadron> escuadrones) {
        if (personajes.isEmpty()) {
            System.out.println("Aún no hay personajes registrados.");
            return;
        }

        System.out.println("\n== PERSONAJES ==");
        System.out.printf("%-12s %5s %7s %9s %9s %-22s %-12s%n",
                "Nombre", "Nivel", "Ataque", "Salud", "Mana", "Arma", "Gremio");
        for (Personaje p : personajes) {
            String salud = p.getSaludActual() + "/" + p.getSaludBase();
            String mana = p.getManaActual() + "/" + p.getManaBase();
            String arma = (p.getArmaEquipada() != null) ? p.getArmaEquipada().toString() : "desarmado";
            String gremio = (p.getGremio() != null) ? p.getGremio().getNombre() : "sin gremio";
            System.out.printf("%-12s %5d %7d %9s %9s %-22s %-12s%n",
                    p.getNombre(), p.getNivel(), p.calcularAtaqueTotal(), salud, mana, arma, gremio);
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("\n= DETALLE =\n");
        for (Personaje p : personajes) {
            reporte.append(p.generarReporte());
        }
        reporte.append("\n= GREMIOS =\n");
        if (gremios.isEmpty()) {
            reporte.append("(sin gremios)\n");
        }
        for (Gremio g : gremios) {
            reporte.append(g).append('\n');
        }
        reporte.append("\n=ESCUADRONES =\n");
        if (escuadrones.isEmpty()) {
            reporte.append("(sin escuadrones)\n");
        }
        for (Escuadron e : escuadrones) {
            reporte.append(e).append('\n');
        }
        System.out.print(reporte);
    }

    private static Personaje seleccionarPersonaje(Scanner scanner, List<Personaje> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay personajes disponibles.");
            return null;
        }
        System.out.println("Personajes:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("%d. %s (nivel %d)%n", i + 1, lista.get(i).getNombre(), lista.get(i).getNivel());
        }
        System.out.print("Selecciona el número del personaje: ");
        int idx = leerEntero(scanner);
        if (idx < 1 || idx > lista.size()) {
            System.out.println("Selección inválida.");
            return null;
        }
        return lista.get(idx - 1);
    }

    private static Personaje buscarPersonaje(List<Personaje> lista, String nombre) {
        for (Personaje p : lista) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    private static Gremio buscarGremio(List<Gremio> lista, String nombre) {
        for (Gremio g : lista) {
            if (g.getNombre().equalsIgnoreCase(nombre)) {
                return g;
            }
        }
        return null;
    }

    private static Escuadron buscarEscuadron(List<Escuadron> lista, String nombre) {
        for (Escuadron e : lista) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                return e;
            }
        }
        return null;
    }
    private static String leerTexto(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return "";
        }
        return scanner.nextLine().trim();
    }
    private static int leerEntero(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return SIN_ENTRADA;
        }
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}
