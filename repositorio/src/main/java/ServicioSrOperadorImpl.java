import commons.Fichero;
import commons.Respuesta;
import commons.interfaces.cliente.ServicioDiscoClienteInterface;
import commons.interfaces.repositorio.ServicioSrOperadorInterface;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioSrOperadorImpl extends UnicastRemoteObject implements ServicioSrOperadorInterface {

    protected ServicioSrOperadorImpl() throws RemoteException {
        super();
    }

    @Override
    public int crearCarpeta(int idCliente) throws RemoteException {
        File carpeta = new File(String.valueOf(idCliente));
        if (!carpeta.mkdir()) {
            return Respuesta.OK.getCodigo();
        }
        return Respuesta.ERROR_AL_CREAR_CARPETA.getCodigo();
    }

    @Override
    public int bajarFichero(String URLdiscoCliente, String nombreFichero, int idCliente) throws MalformedURLException, RemoteException, NotBoundException {
        Fichero fichero = new Fichero(String.valueOf(idCliente), nombreFichero, String.valueOf(idCliente));
        ServicioDiscoClienteInterface servicioDiscoCliente = (ServicioDiscoClienteInterface) Naming.lookup(URLdiscoCliente);

        int respuesta = servicioDiscoCliente.bajarFichero(fichero);

        if (respuesta == Respuesta.OK.getCodigo()) {
            System.out.println("Fichero: " + nombreFichero + " enviado");
            return Respuesta.OK.getCodigo();
        }
        System.out.println("Error en el envío");
        return Respuesta.ERROR.getCodigo();
    }
}
