import commons.ConstantesRMI;
import commons.Fichero;
import commons.Metadatos;
import commons.Respuesta;
import commons.interfaces.repositorio.ServicioClOperadorInterface;
import commons.interfaces.servidor.ServicioGestorInterface;

import java.io.File;
import java.io.FileOutputStream;
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
        OutputStream outputStream;
        try {
            File file = new File(fichero.obtenerPropietario());

            if (!file.exists()) {
                System.out.println("No se ha encontado el fichero");
                return Respuesta.ERROR.getCodigo();
            }
            File rutaDestino = new File(fichero.obtenerPropietario(), fichero.obtenerNombre());
            outputStream = new FileOutputStream(rutaDestino);

            if (!fichero.escribirEn(outputStream)) {
                System.out.println("Error al escribir fichero");
                outputStream.close();
                return Respuesta.ERROR.getCodigo();
            }
            ServicioGestorInterface servicioGestor = (ServicioGestorInterface) Naming.lookup(ConstantesRMI.DIRECCION_GESTOR);
            servicioGestor.ficheroSubido(Metadatos.of(fichero, idCliente));

            System.out.println("Fichero subido correctamente");
            return Respuesta.OK.getCodigo();

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return Respuesta.ERROR.getCodigo();
    }

    @Override
    public int borrarFichero(int idCliente, String nombreFichero) throws RemoteException {
        try {
            File fichero = new File(String.valueOf(idCliente) + File.separator + nombreFichero);

            if (!fichero.exists()) {
                System.out.println("El fichero no existe");
                return Respuesta.ERROR.getCodigo();
            }

            final Metadatos metadatos = Metadatos.of(new Fichero(nombreFichero, String.valueOf(idCliente)), idCliente);

            if (!fichero.delete()) {
                System.out.println("Error en el borrado del fichero");
                return Respuesta.ERROR.getCodigo();
            }
            ServicioGestorInterface servicioGestor = (ServicioGestorInterface) Naming.lookup(ConstantesRMI.DIRECCION_GESTOR);
            servicioGestor.ficheroBorrado(metadatos);
            System.out.println("Fichero borrado con exito");
            return Respuesta.OK.getCodigo();
        } catch (final Exception e) {
            System.out.println("Excepcion borrado: " + e.getMessage());
        }
        return Respuesta.ERROR.getCodigo();
    }
}


