package commons;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Utils {
    public static final String CODEBASE = "java.rmi.server.codebase";

    public static void setCodeBase(Class<?> c) {
        //Calculara la ruta donde este cargada la clase
        //A la clase donde esta el codigo fuente, dame la ubicacion y pasala a string
        String ruta = c.getProtectionDomain().getCodeSource().getLocation().toString();

        //si seteamos el codebase en otra ubicacion, antes de setearlo, mejor
        //comprobar si ya esta puesta, asi evitamos problemas si pedimos mas veces el codebase
        //esto es para no tener que lanzar desde el shell
        String path = System.getProperty(CODEBASE); //si se seteo contrendra ya algo

        if (path != null && !path.isEmpty()) {
            ruta = path + " " + ruta;
        }
        System.setProperty(CODEBASE,ruta);
    }

    public static void arrancarRegistro(int numPuertoRMI) throws RemoteException {
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