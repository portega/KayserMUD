package server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Timer;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import world.Habitacio;
import world.Objeto;
import world.Player;
import world.Sortida;

import commands.Social;

public class Servidor {

    private static TreeMap<String, Social> sociales;
    private static Timer timer;
    private static int puerto = 4000;
    private static HashMap<String, Player> pjs_conectados;

    /**
     * @param args puerto (opcional)
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;
        Socket clientSocket;
        Habitacio hab_actual;
        boolean listening = true;

        if (args.length > 0) {
            try {
                puerto = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.err.println("Uso: Servidor <puerto>");
                System.exit(-1);
            }
        }

        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println("Iniciando servidor");
            setTimer(new Timer());
            cargarSociales(new File("ini/sociales.xml"));
            hab_actual = creaHabitacions();
            pjs_conectados = new HashMap<String, Player>();
            System.out.println("Servidor listo, esperando jugadores");
            while (listening) {
                clientSocket = serverSocket.accept();
                crea_jugador(clientSocket, hab_actual);
            }
            getTimer().cancel();
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + puerto + ". "+e.getMessage());
            System.exit(-1);
        }
    }

    public static Habitacio creaHabitacions() {
        Habitacio h, hab_actual;
        String txt = "Una habitaci\u00f2 igual que l'anterior.";

        hab_actual = new Habitacio("Inici", "Una petita habitaci\u00f2 amb una sortida al nord");
        Objeto o = new Objeto();
        o.setNombre("piedra");
        o.setVnum(9001);
        o.setDescripcion("Una simple y miserable piedra");
        hab_actual.addObjeto(o);

        o = new Objeto();
        o.setNombre("seta");
        o.setVnum(9002);
        o.setDescripcion("Una seta magica");
        hab_actual.addObjeto(o);

        o = new Objeto();
        o.setNombre("seta");
        o.setVnum(9002);
        o.setDescripcion("Una seta magica");
        hab_actual.addObjeto(o);

        o = new Objeto();
        o.setNombre("seta");
        o.setVnum(9002);
        o.setDescripcion("Una seta magica");
        hab_actual.addObjeto(o);

        h = new Habitacio("Segona habitacio", txt);
        hab_actual.setSortida(h, Sortida.Direcciones.NORTE);
        h.setSortida(new Habitacio("Habitacio nord", txt), Sortida.Direcciones.NORTE);
        h.setSortida(new Habitacio("Habitacio est", txt), Sortida.Direcciones.ESTE);
        h.setSortida(new Habitacio("Habitacio oest", txt), Sortida.Direcciones.OESTE);
        cargarMobs(h);
        return hab_actual;
    }

    public static void cargarSociales(File fichero) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fichero);
            doc.getDocumentElement().normalize();
            NodeList nl = doc.getElementsByTagName("SOCIAL");
            Node n;
            Social s;
            sociales = new TreeMap<String, Social>();
            for (int i = 0; i < nl.getLength(); i++) {
                n = nl.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    s = cargarSocial((Element) n);
                    sociales.put(s.getNombre(), s);
                }
            }
            Social.setList(sociales);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public static Social cargarSocial(Element e) {
        Social s = new Social(e.getAttribute("NAME"));
        Node n;
        NodeList nl;
        for (Social.Targets t : Social.Targets.values()) {
            nl = e.getElementsByTagName(t.name());
            if (nl.getLength() > 0) {
                n = nl.item(0);
                s.addMessage(n.getNodeName(), n.getTextContent());
            }
        }
        // TODO: allow & deny
		/*
        nl = e.getElementsByTagName("ALLOW");
        for (int i = 0; i< nl.getLength();i++) {
        n = nl.item(i);
        for (int j = 0; j < nl.getLength(); j++) {

        }
        }
         */
        return s;
    }

    public static void setTimer(Timer timer) {
        Servidor.timer = timer;
    }

    public static Timer getTimer() {
        return timer;
    }

    public static void cargarMobs(Habitacio h) {
        MobThread ctl_guardia, ctl_pitufo;
        Player guardia, pitufo;

        pitufo = new Player();
        pitufo.setNombre("pitufo");
        pitufo.setDescripcion("Un jodido pitufo azul");
        pitufo.setDamm(5);
        pitufo.setVida(100);
        pitufo.setMaxVida(100);
        ctl_pitufo = new MobThread(pitufo, h);
        ctl_pitufo.start();

        guardia = new Player();
        guardia.setNombre("Un guardia");
        guardia.setDescripcion("Un guardia patrullando la ciudad");
        guardia.setDamm(100);
        guardia.setVida(5000);
        guardia.setMaxVida(5000);
        guardia.setMaxMove(100);
        guardia.setMove(100);
        guardia.setVnum(2002);

        ctl_guardia = new MobThread(guardia, h);
        ctl_guardia.setIntervalo(5);
        ctl_guardia.setScript(new String[]{"norte", "sur", "este", "oeste"});
        ctl_guardia.addProg(Constantes.MobProgs.RAND, "if (Math.random() > 0.5) {"
                + "control.exec(\"decir Sin novedad!\")"
                + "} else {"
                + "control.exec(\"decir puagh\");"
                + "control.exec(\"decir Hacer la ronda es un asco!\");"
                + "}");
        ctl_guardia.start();
    }

    public static void crea_jugador(Socket clientSocket, Habitacio hab_actual) {
        int num_players = pjs_conectados.size();
        String nombre = "Jugador " + num_players;
        Player p = new Player();
        p.setNombre(nombre);
        p.setDescripcion("Un tio alto y moreno");
        p.setDamm(100);
        p.setVida(1000);
        p.setMaxVida(1000);
        p.setMove(100);
        p.setMaxMove(100);
        p.setMana(100);
        p.setMaxMana(100);
        p.setVnum(1000 + num_players);

        pjs_conectados.put(nombre, p);

        PlayerThread pt = new PlayerThread(clientSocket, hab_actual, p);
        pt.start();
        System.out.println("Aceptado cliente " + p.getNombre());
    }

    public static HashMap<String, Player> getPjs_conectados() {
        return pjs_conectados;
    }
}
