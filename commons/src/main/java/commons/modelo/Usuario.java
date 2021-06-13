/*
 * Autor: Raúl Maza Sampériz
 * Email: rmaza14@alumno.uned.es
 */

package commons.modelo;

import java.util.Objects;

public class Usuario {

    private String nombre;
    private String password;
    private int id;
    private Repo repositorio;

    public Usuario(String nombre, String password, int id) {
        this.nombre = nombre;
        this.password = password;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public Repo getRepositorio() {
        return repositorio;
    }

    public void setRepositorio(Repo repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return nombre.equals(usuario.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }
}
