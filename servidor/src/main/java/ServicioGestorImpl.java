import commons.ConstantesRMI;
import commons.Metadatos;
import commons.interfaces.servidor.ServicioDatosInterface;
import commons.interfaces.servidor.ServicioGestorInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {

    private final ServicioDatosInterface servicioDatos;

    protected ServicioGestorImpl() throws RemoteException, MalformedURLException, NotBoundException {
        super();
        this.servicioDatos = (ServicioDatosInterface) Naming.lookup(ConstantesRMI.DIRECCION_DATOS);
    }

    @Override
    public String subirFichero(int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException {
        System.out.println("##Servicio gestor: subirFichero");
        return this.servicioDatos.obtenerIdRepositorioDeCliente(idSesionCliente);
    }

    @Override
    public String bajarFichero(String URLdiscoCliente, int idFichero, int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException {
        return null;
    }

    @Override
    public String listarFicherosCliente(int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException {
        return this.servicioDatos.listarFicherosCliente(idSesionCliente);
    }

    @Override
    public String listarClientes() throws MalformedURLException, RemoteException, NotBoundException {
        return this.servicioDatos.listarClientes();
    }

    @Override
    public String borrarFichero(int idFichero, int idSesionCliente) throws MalformedURLException, RemoteException, NotBoundException {
        return null;
    }

    @Override
    public int ficheroSubido(Metadatos ficheroSubido) throws RemoteException {
        return  this.servicioDatos.ficheroSubido(ficheroSubido);
    }

    @Override
    public int ficheroBorrado(Metadatos ficheroBorrado) throws RemoteException {
        return  this.servicioDatos.fihceroBorrado(ficheroBorrado);
    }
}

