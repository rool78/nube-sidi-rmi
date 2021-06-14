/*
 * Autor: Raúl Maza Sampériz
 * Email: rmaza14@alumno.uned.es
 */

import commons.Fichero;
import commons.Respuesta;
import commons.interfaces.cliente.ServicioDiscoClienteInterface;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioDiscoClienteImpl extends UnicastRemoteObject implements ServicioDiscoClienteInterface {

    protected ServicioDiscoClienteImpl() throws RemoteException {
        super();
    }

    @Override
    public int bajarFichero(Fichero fichero) throws RemoteException {
        OutputStream outputStream;
        final String nombreFichero = fichero.obtenerNombre();
        try {
            outputStream = new FileOutputStream(nombreFichero);
            if (!fichero.escribirEn(outputStream)) {
                outputStream.close();
                return Respuesta.ERROR.getCodigo();
            }
            outputStream.close();
            System.out.println("Fichero descargado con exito");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Respuesta.OK.getCodigo();
    }
}
