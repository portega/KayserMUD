package commands;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import server.Control;

public class Interpreter {

    private TreeMap<String, String> diccionari;
    private Control control;

    public Interpreter(Control ctl) {
        control = ctl;
        diccionari = new TreeMap<String, String>();

        /*diccionari.put("cast", "CONJURAR");
        diccionari.put("conjurar", "CONJURAR");
        diccionari.put("map", "MAPA");
        diccionari.put("mapa", "MAPA");
         */
        diccionari.put("ayuda", "system.ComandoHelp");
        diccionari.put("help", "system.ComandoHelp");

        diccionari.put("look", "ComandoMirar");
        diccionari.put("mirar", "ComandoMirar");

        diccionari.put("quien", "system.ComandoQuien");

        diccionari.put("e", "ComandoMover");
        diccionari.put("o", "ComandoMover");
        diccionari.put("n", "ComandoMover");
        diccionari.put("s", "ComandoMover");
        diccionari.put("a", "ComandoMover");
        diccionari.put("este", "ComandoMover");
        diccionari.put("oeste", "ComandoMover");
        diccionari.put("norte", "ComandoMover");
        diccionari.put("sur", "ComandoMover");
        diccionari.put("arriba", "ComandoMover");
        diccionari.put("abajo", "ComandoMover");

        diccionari.put("salidas", "ComandoSalidas");
        diccionari.put("coger", "objs.ComandoCoger");
        diccionari.put("dejar", "objs.ComandoDejar");
        diccionari.put("dar", "objs.ComandoDar");
        diccionari.put("inventario", "objs.ComandoInventario");

        diccionari.put("equipo", "objs.ComandoEquipo");
        diccionari.put("vestir", "objs.ComandoVestir");
        diccionari.put("desvestir", "objs.ComandoDesvestir");

        diccionari.put("decir", "comm.ComandoDecir");
        diccionari.put("matar", "ComandoCombate");

        diccionari.put("quit", "system.ComandoSalir");
        diccionari.put("salir", "system.ComandoSalir");

        // Sociales
        /*Social s;
        for (String nombre : control.getSocials().keySet()) {
            s = control.getSocial(nombre);
            if (s.allows(control.getPlayer())) {
                diccionari.put(s.getNombre(), "ComandoSocial");
            }
        }*/
    }

    public String find(String input) {
        Map.Entry<String, String> clau;
        String txt = "";
        input = expandSpecialCharacters(input);
        String[] params = input.split(" ");

        clau = diccionari.ceilingEntry(params[0]);
        if (clau == null || !clau.getKey().startsWith(params[0])) {
            return "Eso no lo entiendo";
        }
        try {
            Class<?> c = Class.forName("commands." + clau.getValue());
            Comando cmd = (Comando) c.newInstance();
            txt = cmd.execute(control.getPlayer(), input);
            return txt;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String expandSpecialCharacters(String input) {
        String output;
        if (input.startsWith("'")) {
            output = "decir " + input.substring(1).trim();
        } else if (input.startsWith(".")) {
            output = "charlar " + input.substring(1).trim();
        } else if (input.startsWith(":")) {
            output = "immtalk " + input.substring(1).trim();
        } else {
            output = new String(input);
        }
        return output;
    }

    public Set<String> list() {
        return diccionari.keySet();
    }
}
