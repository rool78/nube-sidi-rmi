import com.sun.istack.internal.Nullable;
import commons.Fichero;
import commons.Respuesta;
import commons.interfaces.repositorio.ServicioClOperadorInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioClOperadorImpl extends UnicastRemoteObject implements ServicioClOperadorInterface {

    protected ServicioClOperadorImpl() throws RemoteException {
        super();
    }

    @Override
    public int subirFichero(Fichero fichero) throws RemoteException {
        @Nullable OutputStream flujoSalida = null;

        try {
            File directorioDestino = new File(fichero.obtenerPropietario());

            if (!directorioDestino.exists()
                    || !directorioDestino.isDirectory()) {
                System.out.println("Repositorio  no tiene ningún directorio para cliente");
                return Respuesta.ERROR.getCodigo();
            }

            final File rutaDestino = new File(
                    fichero.obtenerPropietario(),
                    fichero.obtenerNombre());

            if (rutaDestino.exists()) {
                System.out.println("Fichero ya existe para cliente, sera sobreescrito");
            }

            flujoSalida = new FileOutputStream(rutaDestino);

            if (!fichero.escribirEn(flujoSalida)) {
                System.out.println("Error al escribir fichero");
                return Respuesta.ERROR.getCodigo();
            }
            System.out.println("Fichero subido correctamente");
            return Respuesta.OK.getCodigo();

        } catch (final Exception e) {

        } finally {
            try {
                if (flujoSalida != null) {
                    flujoSalida.close();
                }
            } catch (final IOException e) {
                // Nada que podamos hacer aquí
            }
        }
        return Respuesta.ERROR.getCodigo();
    }

    @Override
    public int borrarFichero(String fichero, String carpeta) throws RemoteException {
        return 0;
    }
}

