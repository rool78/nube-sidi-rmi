import commons.Fichero;
import commons.Respuesta;
import commons.interfaces.repositorio.ServicioClOperadorInterface;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioClOperadorImpl extends UnicastRemoteObject implements ServicioClOperadorInterface {

    protected ServicioClOperadorImpl() throws RemoteException {
        super();
    }

    @Override
    public int subirFichero(Fichero fichero) throws RemoteException {
        OutputStream os;
        String nombreFichero = fichero.obtenerPropietario() + File.separator + fichero.obtenerNombre();

        try {
            os = new FileOutputStream(nombreFichero);
            if (!fichero.escribirEn(os)) {
                os.close();
                return Respuesta.ERROR.getCodigo();
            }
            os.close();
            System.out.println("Fichero " + nombreFichero + " recibido y guardado");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Respuesta.OK.getCodigo();
    }

    @Override
    public int borrarFichero(String fichero, String carpeta) throws RemoteException {
        return 0;
    }
}
