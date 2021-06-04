import commons.ConstantesRMI;
import commons.Fichero;
import commons.Metadatos;
import commons.Respuesta;
import commons.interfaces.repositorio.ServicioClOperadorInterface;
import commons.interfaces.servidor.ServicioGestorInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioClOperadorImpl extends UnicastRemoteObject implements ServicioClOperadorInterface {

    protected ServicioClOperadorImpl() throws RemoteException {
        super();
    }

    @Override
    public int subirFichero(Fichero fichero, int idCliente) throws RemoteException {
        OutputStream outputStream = null;
        try {
            File directorioDestino = new File(fichero.obtenerPropietario());

            if (!directorioDestino.exists()
                    || !directorioDestino.isDirectory()) {
                System.out.println("Repositorio  no tiene ningún directorio para cliente");
                return Respuesta.ERROR.getCodigo();
            }

            File rutaDestino = new File(fichero.obtenerPropietario(), fichero.obtenerNombre());

            outputStream = new FileOutputStream(rutaDestino);

            if (!fichero.escribirEn(outputStream)) {
                System.out.println("Error al escribir fichero");
                return Respuesta.ERROR.getCodigo();
            }
            //todo llmar al gestor para avisar de que el cliente ha subido un fichero, guardar informacion para poder listar sus ficheros
            ServicioGestorInterface servicioGestor = (ServicioGestorInterface) Naming.lookup(ConstantesRMI.DIRECCION_GESTOR);
            servicioGestor.ficheroSubido(Metadatos.of(fichero, idCliente));


            System.out.println("Fichero subido correctamente");
            return Respuesta.OK.getCodigo();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ignored) {

            }
        }
        return Respuesta.ERROR.getCodigo();
    }

    @Override
    public int borrarFichero(String fichero, String carpeta) throws RemoteException {
        return 0;
    }
}

