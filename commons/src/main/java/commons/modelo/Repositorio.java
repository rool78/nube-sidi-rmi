package commons.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Repositorio {

    private String nombre;
    private int id;
    private List<Usuario> usuarios;

    public Repositorio(String nombre, int id) {
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

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repositorio that = (Repositorio) o;
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
