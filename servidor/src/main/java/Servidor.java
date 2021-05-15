import interfaces.cliente.ServicioDiscoClienteInterface;
import interfaces.servidor.ServicioAutenticacionInterface;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {

    public int puertoServidor = 8080;
    private static String direccion = "localhost";


    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        new Servidor().launch();
    }

    public Servidor() {

    }

    private void launch() throws RemoteException, MalformedURLException, NotBoundException {
        arrancarRegistro(puertoServidor);
        Utils.setCodeBase(ServicioAutenticacionInterface.class);

        //Levantar Datos, el almacen, el constructor mantendra la persistencia
        ServicioDatosImpl servicioDatos = new ServicioDatosImpl();
        String direccionServicioDatos = "rmi://" + direccion + ":" + puertoServidor + "/datos";
        Naming.rebind(direccionServicioDatos, servicioDatos);
        System.out.println("Operacion: Servicio Datos preparado con exito");

        //Levantar Autenticador
        ServicioAutenticacionImpl objetoAutenticador = new ServicioAutenticacionImpl();
        String direccionServicioAutenticacion = "rmi://" + direccion + ":" + puertoServidor + "/autenticador";
        Naming.rebind(direccionServicioAutenticacion, objetoAutenticador);
        System.out.println("Operacion: Servicio Autenticador preparado con exito");

    }

    public void arrancarRegistro(int numPuertoRMI) throws RemoteException {
        Registry registryServicio;
        try {
            registryServicio = LocateRegistry.getRegistry(numPuertoRMI);
            registryServicio.list();
        } catch (RemoteException e) {
            System.out.println("El registro RMI no se puede localizar en el puerto " + numPuertoRMI);
            LocateRegistry.createRegistry(numPuertoRMI);
            System.out.println("Registro RMI creado en el puerto " + numPuertoRMI);
        }
    }

}