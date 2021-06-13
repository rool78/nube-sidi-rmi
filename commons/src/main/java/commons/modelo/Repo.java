/*
 * Autor: Raúl Maza Sampériz
 * Email: rmaza14@alumno.uned.es
 */

package commons.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Repo {

    private String nombre;
    private int id;
    private List<Usuario> usuarios;

    public Repo(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
        this.usuarios = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repo that = (Repo) o;
        return nombre.equals(that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        return "Repositorio{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                '}';
    }
}
