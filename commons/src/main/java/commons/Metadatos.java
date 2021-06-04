package commons;

import java.io.Serializable;

public class Metadatos implements Serializable {

    private int idCliente;
    private String nombreFichero;
    private long peso;
    private long checksum;

    public static Metadatos of(Fichero fichero, int idCliente) {
        return new Metadatos(idCliente, fichero.obtenerNombre(), fichero.obtenerPeso(),fichero.obtenerChecksum());
    }

    public Metadatos(int idCliente, String nombreFichero, long peso, long checksum) {
        super();
        this.idCliente = idCliente;
        this.nombreFichero = nombreFichero;
        this.peso = peso;
        this.checksum = checksum;
    }

    public long getPeso() {
        return peso;
    }

    public void setPeso(long peso) {
        this.peso = peso;
    }

    public long getChecksum() {
        return checksum;
    }

    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreFichero() {
        return nombreFichero;
    }

    public void setNombreFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    @Override
    public String toString() {
        return "Metadatos{" +
                "idCliente=" + idCliente +
                ", nombreFichero='" + nombreFichero + '\'' +
                ", peso=" + peso +
                ", checksum=" + checksum +
                '}';
    }
}
