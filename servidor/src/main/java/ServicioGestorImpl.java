import commons.ConstantesRMI;
import commons.Metadatos;
import commons.Respuesta;
import commons.interfaces.repositorio.ServicioSrOperadorInterface;
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
        return this.servicioDatos.obtenerUrlRepositorioDeCliente(idSesionCliente);
    }

    @Override
    public int bajarFichero(String urldiscoCliente, String nombreFichero, int idCliente) throws RemoteException, MalformedURLException, NotBoundException {
        String urlRepo = this.servicioDatos.obtenerUrlRepositorio(idCliente);
        ServicioSrOperadorInterface servicioSrOperador = (ServicioSrOperadorInterface) Naming.lookup(urlRepo);
        servicioSrOperador.bajarFichero(urldiscoCliente, nombreFichero, idCliente);
        return Respuesta.OK.getCodigo();
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
    public String listarClientesRepositorio(int id) throws RemoteException {
        return this.servicioDatos.listarClientesRepositorio(id);
    }

    @Override
    public String borrarFichero(int idSesionCliente) throws MalformedURLException, RemoteException, NotBoundException {
        return this.servicioDatos.obtenerUrlRepositorioDeCliente(idSesionCliente);
    }

    @Override
    public int ficheroSubido(Metadatos ficheroSubido) throws RemoteException {
        return  this.servicioDatos.ficheroSubido(ficheroSubido);
    }

    @Override
    public int ficheroBorrado(Metadatos ficheroBorrado) throws RemoteException {
        return  this.servicioDatos.ficheroBorrado(ficheroBorrado);
    }
}

