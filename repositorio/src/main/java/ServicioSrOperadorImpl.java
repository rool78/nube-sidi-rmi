import interfaces.repositorio.ServicioSrOperadorInterface;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioSrOperadorImpl extends UnicastRemoteObject implements ServicioSrOperadorInterface {

    protected ServicioSrOperadorImpl() throws RemoteException {
        super();
    }

    @Override
    public int crearCarpeta(int idCliente) throws RemoteException {
        System.out.println("##Crear carpeta idCliente: " + idCliente);
        File carpeta = new File("" + idCliente);
        System.out.println("Carpeta: " + carpeta.getAbsolutePath());
        if (!carpeta.mkdir()) {
            System.out.println("Carpeta creada correctamente");
            return Respuesta.OK.getCodigo();
        }
        System.out.println("Error al crear la carpeta");
        return Respuesta.ERROR_AL_CREAR_CARPETA.getCodigo();
    }

    @Override
    public int bajarFichero(String URLdiscoCliente, String nombreFichero, int idCliente) throws MalformedURLException, RemoteException, NotBoundException {
        //conversion implicita a cadena ""+idCliente
//        Fichero fichero= new Fichero(""+idCliente,nombreFichero,""+idCliente);
//        ServicioDiscoClienteInterface servicioDiscoCliente =(ServicioDiscoClienteInterface) Naming.lookup(URLdiscoCliente);

//        if (servicioDiscoCliente.bajarFichero(fichero,idCliente)==false)
//        {
//            System.out.println("Error en el envío (Checksum failed), intenta de nuevo");
//        }
//        else{
//            System.out.println("Fichero: " + nombreFichero + " enviado");
//        }
        return 0;
    }
}
