package co.edu.puj.rpg.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Inventario genérico: sirve para pociones, equipamiento, lo que sea.
 * Con <T> el compilador no deja meter cosas de otro tipo y no hace falta casteo.
 */
public class Inventario<T> {

    private List<T> items = new ArrayList<>();

    public void agregar(T item) {
        items.add(item);
    }

    public T obtener(int index) {
        return items.get(index);
    }

    public List<T> getItems() {
        return items;
    }

    // Extras que me sirvieron para gastar las pociones
    public T remover(int index) {
        return items.remove(index);
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public int tamano() {
        return items.size();
    }
}
