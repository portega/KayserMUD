package server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Timer;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilderFactory;

import localization.Language;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.Utils;
import world.Exit;
import world.Item;
import world.Room;
import world.Player;

import commands.Social;

import static utils.Utils.loadJSON;

public class Server {

    private static Timer timer;
    private static int puerto = 4000;
    private static HashMap<String, Player> pjs_conectados;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;
        Socket clientSocket;
        Room hab_actual;
        boolean listening = true;
        String locale;
        Language language;
        JsonNode config;
        int port;

        config = loadJSON(Server.class, "config.json");
        port = config.get("port").asInt();
        locale = config.get("locale").asText();
        language = new Language(locale);

        try {
            serverSocket = new ServerSocket(port);
            System.out.println(language.get("init"));
            setTimer(new Timer());
            hab_actual = loadRooms(language);
            pjs_conectados = new HashMap<String, Player>();
            System.out.println(language.get("ready"));
            while (listening) {
                clientSocket = serverSocket.accept();
                crea_jugador(clientSocket, hab_actual, language);
            }
            getTimer().cancel();
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(language.get("port.error") + puerto + ". "+e.getMessage());
            System.exit(-1);
        }
    }

    public static Room loadRooms(Language language) {
        JsonNode area = Utils.loadJSON(Server.class, language.getLocale()+"/area.json");
        ArrayNode rooms = (ArrayNode)area.get("rooms");

        Room first_room = null, current;
        HashMap<Integer, Room> rooms_list = new HashMap<Integer, Room>();
        for (JsonNode node: rooms) {
            current = readRoom(node);
            if (first_room == null) first_room = current;
            rooms_list.put(current.getVnumAsInteger(), current);
        }

        return first_room;
    }

    public static Room readRoom(JsonNode node) {
        Room r = new Room();
        r.setNombre(node.get("short_desc").asText());
        r.setDescripcion(node.get("long_desc").asText());
        r.setVnum(node.get("vnum").asInt());

        return r;
    }

    public static TreeMap<String, Social> loadSocials(Language language) {
        TreeMap<String, Social> socials = new TreeMap<String, Social>();
        try {
            JsonNode list = loadJSON(Server.class, language.getLocale()+"/socials.json");
            ArrayNode socialArray = (ArrayNode)list.get("socials");
            for (JsonNode node : socialArray){
                socials.put(node.get("name").asText(), cargarSocial(node));
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return socials;
    }

    public static Social cargarSocial(JsonNode e) {
        // TODO: allow & deny
        Social s = new Social(e.get("name").asText());
        for (Social.Targets t : Social.Targets.values()) {
            if (e.has(t.name())) {
                s.addMessage(t.name(), e.get(t.name()).asText());
            }
        }
        return s;
    }

    public static void setTimer(Timer timer) {
        Server.timer = timer;
    }

    public static Timer getTimer() {
        return timer;
    }

    public static void cargarMobs(Room h) {
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
        ctl_guardia.addProg(Constants.MobProgs.RAND, "if (Math.random() > 0.5) {"
                + "control.exec(\"decir Sin novedad!\")"
                + "} else {"
                + "control.exec(\"decir puagh\");"
                + "control.exec(\"decir Hacer la ronda es un asco!\");"
                + "}");
        ctl_guardia.start();
    }

    public static void crea_jugador(Socket clientSocket, Room hab_actual, Language language) {
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
        pt.setLanguage(language);
        pt.setSocials(loadSocials(language));
        pt.start();
        System.out.println("Aceptado cliente " + p.getNombre());
    }

    public static HashMap<String, Player> getPjs_conectados() {
        return pjs_conectados;
    }
}