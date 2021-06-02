import commons.ConstantesRMI;
import commons.Utils;
import commons.interfaces.servidor.ServicioAutenticacionInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Servidor {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        new Servidor().launch();
    }

    public Servidor() {

    }

    private void launch() throws RemoteException, MalformedURLException, NotBoundException {
        Utils.arrancarRegistro(ConstantesRMI.PUERTO_SERVIDOR);
        Utils.setCodeBase(ServicioAutenticacionInterface.class);

        //Levantamos servicio datos
        ServicioDatosImpl servicioDatos = new ServicioDatosImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_DATOS, servicioDatos);
        System.out.println("Servicio Datos preparado con exito");

        //Levantamos servicio autenticador
        ServicioAutenticacionImpl servicioAutenticacion = new ServicioAutenticacionImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_AUTENTICADOR, servicioAutenticacion);
        System.out.println("Servicio Autenticador preparado con exito");

        //Levantamos servicio gestor
        ServicioGestorImpl servicioGestor = new ServicioGestorImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_GESTOR, servicioGestor);
        System.out.println("Servicio gestor preparado con exito");

    }

}