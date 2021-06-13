/*
 * Autor: Raúl Maza Sampériz
 * Email: rmaza14@alumno.uned.es
 */

package commons;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Utils {
    public static final String CODEBASE = "java.rmi.server.codebase";

    public static void setCodeBase(Class<?> c) {
        String ruta = c.getProtectionDomain().getCodeSource().getLocation().toString();
        String path = System.getProperty(CODEBASE);
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
            System.out.println("[INFO] El registro RMI no se puede localizar en el puerto " + numPuertoRMI);
            LocateRegistry.createRegistry(numPuertoRMI);
            System.out.println("[INFO] Registro RMI creado en el puerto " + numPuertoRMI);
        }
    }

}
