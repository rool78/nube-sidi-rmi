import interfaces.repositorio.ServicioSrOperadorInterface;
import interfaces.servidor.ServicioDatosInterface;
import modelo.Repositorio;
import modelo.Usuario;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface {

    private List<Usuario> clientesRegistrados = new ArrayList<>();
    private List<Usuario> clientesEnLinea = new ArrayList<>();

    private List<Repositorio> repositoriosRegistrados = new ArrayList<>();
    private List<Repositorio> repositoriosEnLinea = new ArrayList<>();
    
//    private Map<Repositorio, List<Usuario>> clientesEnRepositorios = new HashMap<>();
//    private Map<Usuario, List<Metadatos>> clientFiles = new HashMap<>();

    private List<Usuario> usuarios = new ArrayList<>();

    private int idUsuarios = 0;
    private int idRepositorios = 0;

    protected ServicioDatosImpl() throws RemoteException {
        super();
    }

    @Override
    public int autenticarCliente(String nombre, String password) throws RemoteException {
        for (Usuario u : clientesRegistrados) {
            if (u.getNombre().equals(nombre) && u.getPassword().equals(password)) {
                //Tenemos que ver si su repo esta online =)
                for (Repositorio r : repositoriosEnLinea) {
                    if (r.equals(u.getRepositorio())) {
                        //repo en linea
                        System.out.println("Login correcto");
                        return u.getId();
                    }
                }
            }
        }
        return Respuesta.ERROR_AUTENTICACION.getCodigo();
    }

    @Override
    public int registrarCliente(String nombre, String password) throws RemoteException, MalformedURLException, NotBoundException {
        for (Usuario u : clientesRegistrados) {
            if (u.getNombre().equals(nombre)) {
                System.out.println("Ya existe un Usuario con ese nombre");
                return Respuesta.NOMBRE_YA_EN_USO.getCodigo();
            }
        }

        if (repositoriosEnLinea.isEmpty()) {
            System.out.println("No hay repositorios en linea");
            return Respuesta.NO_HAY_REPOSITORIOS_EN_LINEA.getCodigo();
        }

        int asignacionAleatoria = (int) (Math.random() * repositoriosEnLinea.size());
        System.out.println("Repos en linea asignacion: " + asignacionAleatoria);

        Usuario nuevoUsuario = new Usuario(nombre, password, generarIdUsuario());

        //Añadimos el repo al usuario
        nuevoUsuario.setRepositorio(repositoriosEnLinea.get(asignacionAleatoria));

        clientesRegistrados.add(nuevoUsuario);
        clientesEnLinea.add(nuevoUsuario);

        //Añadimos el nuevo usuario al repo asignado
        repositoriosEnLinea.get(asignacionAleatoria).getUsuarios().add(nuevoUsuario);

        System.out.println("Usuario registrado con exito");

//        if (clientesEnRepositorios.containsKey(repositoriosEnLinea.get(asignacionAleatoria))) {
//            List<Usuario> list = clientesEnRepositorios.get(repositoriosEnLinea.get(asignacionAleatoria));
//            list.add(nuevoUsuario);
//        } else {
//            clientesEnRepositorios.put(repositoriosEnLinea.get(asignacionAleatoria), Collections.singletonList(nuevoUsuario));
//        }
        //Creamos carpeta
        ServicioSrOperadorInterface servidorSrOperador = (ServicioSrOperadorInterface) Naming.lookup(ConstantesRMI.DIRECCION_SR_OPERADOR);
        int respuesta = servidorSrOperador.crearCarpeta(nuevoUsuario.getId());

        if (respuesta == Respuesta.ERROR_AL_CREAR_CARPETA.getCodigo()) {
            return respuesta;
        }
        return Respuesta.OK.getCodigo();
    }

    public String listarClientes() {
        return clientesRegistrados.toString();
    }

    @Override
    public int autenticarRepositorio(String nombre) throws RemoteException {
        //TODO repos en linea??
        return 0;
    }

    @Override
    public int registrarRepositorio(String nombre) throws RemoteException {
        for (Repositorio r : repositoriosRegistrados) {
            if (r.getNombre().equals(nombre)) {
                return Respuesta.NOMBRE_YA_EN_USO.getCodigo();
            }
        }
        Repositorio nuevoRepositorio = new Repositorio(nombre, generarIdRepositorio());
        repositoriosRegistrados.add(nuevoRepositorio);
        return nuevoRepositorio.getId();
    }

    @Override
    public void desconectarCliente(String nombre) throws RemoteException {
        for (Usuario u : clientesEnLinea) {
            if (u.getNombre().equals(nombre)) {
                clientesEnLinea.remove(u); //todo funciona bien asi?
                System.out.println("El usuario se ha desconectado correctamnente");
            }
        }
        System.out.println("No se ha encontrado un usuario para poder desconectarlo");
    }

    @Override
    public void desconectarRepositorio(String nombre) throws RemoteException {
        for (Repositorio r : repositoriosEnLinea) {
            if (r.getNombre().equals(nombre)) {
                repositoriosEnLinea.remove(r);
                System.out.println("Repositorio desconectado correctamente");
            }
        }
        System.out.println("No se ha encontrado un repositorio para poder desconectarlo");
    }

    private int generarIdUsuario() {
        return idUsuarios++;
    }

    private int generarIdRepositorio() {
        return idRepositorios++;
    }
}
