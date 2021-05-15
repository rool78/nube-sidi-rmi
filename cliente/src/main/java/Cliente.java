import interfaces.cliente.ServicioDiscoClienteInterface;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente {

    private final static int puertoServicio = 8080;
    private final static String direccionServicio = "localhost";

    private static String discocliente;

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        System.out.println("Prueba cliente, levanta su propio servicio...");
        String URLdiscoCliente = "rmi://" + direccionServicio + ":" + puertoServicio + "/discocliente/";
        arrancarRegistro(puertoServicio);
        Utils.setCodeBase(ServicioDiscoClienteInterface.class);
        ServicioDiscoClienteImpl servicioDiscoCliente = new ServicioDiscoClienteImpl();
        Naming.rebind(URLdiscoCliente, servicioDiscoCliente);
        System.out.println("Operacion: Servicio Disco Cliente preparado con exito");

        servicioDiscoCliente.bajarFichero(1);

    }

    public static void arrancarRegistro(int numPuertoRMI) throws RemoteException {
        Registry registryServicio;
        try {
            registryServicio = LocateRegistry.getRegistry(numPuertoRMI);
            registryServicio.list();
        }
        catch (RemoteException e) {
            System.out.println("El registro RMI no se puede localizar en el puerto "+ numPuertoRMI);
            LocateRegistry.createRegistry(numPuertoRMI);
            System.out.println("Registro RMI creado en el puerto " + numPuertoRMI);
        }
    }

}
