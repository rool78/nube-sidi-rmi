package commons;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;

public class Fichero implements Serializable {

    private static final long serialVersionUID = -701591859841871541L;
    private String propietario;        //Identificador del propietario original del fichero
    private String nombre;            //Nombre del fichero
    private long peso;                //Peso del fichero en bytes
    private long checksum;            //Suma de chequeo de los bytes del fichero
    private byte[] data;            //Contenido del fichero

    public Fichero(String nombre, String propietario)    //constructor
    {
        this.nombre = nombre;
        this.propietario = propietario;

        CheckedInputStream c = null;
        peso = 0;

        try {
            c = new CheckedInputStream(new FileInputStream(nombre), new CRC32());
            peso = new File(nombre).length();
            data = new byte[(int) this.peso];
            while (c.read(data) > 0) {
            }
            c.close();

        } catch (FileNotFoundException ef) {
            System.out.println("commons.Fichero no encontrado");
        } catch (IOException e) {

            System.out.println("Error leyendo fichero" + e);
        }
        checksum = c.getChecksum().getValue();

    }

    public Fichero(String ruta, String nombre, String propietario)    //constructor
    {
        this.nombre = nombre;
        this.propietario = propietario;

        CheckedInputStream c = null;
        peso = 0;

        try {
            //c = new CheckedInputStream(new FileInputStream(ruta+"\\"+nombre), new CRC32());
            c = new CheckedInputStream(new FileInputStream(ruta + File.separator + nombre), new CRC32());//se ha cambiado el separador
            //peso = new File(ruta+"\\"+nombre).length();
            peso = new File(ruta + File.separator + nombre).length();//se ha cambiado el separador
            data = new byte[(int) this.peso];
            while (c.read(data) >= 0) {
            }
            c.close();

        } catch (FileNotFoundException ef) {
            System.out.println("Fichero no encontrado");
        } catch (IOException e) {

            System.out.println("Error leyendo fichero" + e);
        }
        checksum = c.getChecksum().getValue();

    }

    public boolean escribirEn(OutputStream os) {
        long CheckSum;
        CheckedOutputStream cs = new CheckedOutputStream(os, new CRC32());
        try {
            cs.write(data);
            CheckSum = cs.getChecksum().getValue();
            cs.close();
            if (CheckSum != this.checksum) {
                return (false);    //Falla el checksum, debera mandarse de nuevo
            }
        } catch (Exception e) {
            System.err.println("Error escribiendo fichero" + e);
        }
        return (true); //commons.Fichero mandado Ok
    }

    public String obtenerPropietario() {
        return (propietario);
    }

    public String obtenerNombre() {
        return (nombre);
    }

    public long obtenerPeso() {
        return (peso);
    }

    public long obtenerChecksum() {
        return (checksum);
    }

}
