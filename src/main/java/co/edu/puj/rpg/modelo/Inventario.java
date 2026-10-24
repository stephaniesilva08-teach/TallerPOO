package co.edu.puj.rpg.modelo;

import java.util.ArrayList;
import java.util.List;

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
