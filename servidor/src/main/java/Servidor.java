import interfaces.servidor.ServicioAutenticacionInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        new Servidor().launch();
    }

    public Servidor() {

    }

    private void launch() throws RemoteException, MalformedURLException, NotBoundException {
        arrancarRegistro(ConstantesRMI.PUERTO_SERVIDOR);
        Utils.setCodeBase(ServicioAutenticacionInterface.class);

        //Levantamos servicio datos
        ServicioDatosImpl servicioDatos = new ServicioDatosImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_DATOS, servicioDatos);
        System.out.println("Operacion: Servicio Datos preparado con exito");

        //Levantamos servicio autenticador
        ServicioAutenticacionImpl objetoAutenticador = new ServicioAutenticacionImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_AUTENTICADOR, objetoAutenticador);
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