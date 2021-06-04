import commons.ConstantesRMI;
import commons.Metadatos;
import commons.Respuesta;
import commons.interfaces.repositorio.ServicioSrOperadorInterface;
import commons.interfaces.servidor.ServicioDatosInterface;
import commons.modelo.Repositorio;
import commons.modelo.Usuario;

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
    private Map<Integer, List<Metadatos>> ficherosCliente = new HashMap<>();

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
        System.out.println("Registrar cliente datos");
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

        System.out.println("Servidor: Usuario registrado con exito");

        //Creamos carpeta
        ServicioSrOperadorInterface servidorSrOperador = (ServicioSrOperadorInterface) Naming.lookup(ConstantesRMI.DIRECCION_SR_OPERADOR);
        int respuesta = servidorSrOperador.crearCarpeta(nuevoUsuario.getId());
        if (respuesta == Respuesta.ERROR_AL_CREAR_CARPETA.getCodigo()) {
            return respuesta;
        }
        return nuevoUsuario.getId();
    }

    public String listarClientes() {
        return clientesRegistrados.toString();
    }

    @Override
    public String obtenerIdRepositorioDeCliente(int clienteId) {
        for (Usuario u: clientesEnLinea) {
            if (u.getId() == clienteId) {
                System.out.println("Url repo cliente: " + ConstantesRMI.DIRECCION_CL_OPERADOR + "/" + u.getRepositorio().getId());
                return  ConstantesRMI.DIRECCION_CL_OPERADOR + "/" + u.getRepositorio().getId();
            }
        }
        return "";
    }

    @Override
    public String listarFicherosCliente(int idCliente) throws RemoteException {
        List<Metadatos> ficheros = this.ficherosCliente.get(idCliente);
        StringBuilder stringBuilder = new StringBuilder("Lista ficheros: ");
        for (Metadatos m: ficheros) {
            stringBuilder.append(" [")
                         .append(m.getNombreFichero())
                         .append("] ");
        }
        return stringBuilder.toString();
    }

    @Override
    public int ficheroSubido(Metadatos ficheroSubido) throws RemoteException {
        if (this.ficherosCliente.containsKey(ficheroSubido.getIdCliente())) {
            this.ficherosCliente.get(ficheroSubido.getIdCliente()).add(ficheroSubido);
        } else {
            List<Metadatos> list = new ArrayList<Metadatos>();
            list.add(ficheroSubido);
            this.ficherosCliente.put(ficheroSubido.getIdCliente(), list);
        }
        return Respuesta.OK.getCodigo();
    }

    @Override
    public int fihceroBorrado(Metadatos ficheroBorrado) throws RemoteException {
        List<Metadatos> ficheros = this.ficherosCliente.get(ficheroBorrado.getIdCliente());
        Iterator<Metadatos> it = ficheros.iterator();
        while (it.hasNext()) {
            Metadatos m = it.next();
            if (m.getNombreFichero().equals(ficheroBorrado.getNombreFichero())) {
                it.remove();
                break;
            }
        }
        return 0;
    }

    @Override
    public int autenticarRepositorio(String nombre) throws RemoteException {
        Repositorio repositorioParaAutenticar = null;
        boolean repositorioRegistrado = false;
        for (Repositorio r: repositoriosRegistrados) {
            if (r.getNombre().equals(nombre)) {
                repositorioParaAutenticar = r;
                repositorioRegistrado = true;
            }
        }
        if (!repositorioRegistrado) {
            System.out.println("El repositorio no esta registrado");
            return Respuesta.ERROR_AUTENTICACION.getCodigo();
        }
        for (Repositorio r: repositoriosEnLinea) {
            if (r.getNombre().equals(repositorioParaAutenticar.getNombre())) {
                System.out.println("El repositorio ya estaba en linea");
                return r.getId();
            }
        }
        //Lo añadimos a la lista de repositorios en linea
        repositoriosEnLinea.add(repositorioParaAutenticar);

        return repositorioParaAutenticar.getId();
    }

    @Override
    public int registrarRepositorio(String nombre) throws RemoteException {
        for (Repositorio r : repositoriosRegistrados) {
            if (r.getNombre().equals(nombre)) {
                System.out.println("Nombre del repositorio ya en uso");
                return Respuesta.NOMBRE_YA_EN_USO.getCodigo();
            }
        }
        Repositorio nuevoRepositorio = new Repositorio(nombre, generarIdRepositorio());
        repositoriosRegistrados.add(nuevoRepositorio);
        repositoriosEnLinea.add(nuevoRepositorio);
        System.out.println("Repositorio creado correctamente");
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
