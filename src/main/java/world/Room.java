package world;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import lombok.Data;
import world.Exit.Direcciones;

@Data
public class Room extends Template {

  private HashMap<Direcciones, Room> salidas;
  private Container<Template> contenido;
  private Container<Player> habitantes;
  private int moveCost = 1;

  public Room() {
    super();
    salidas = new HashMap<Exit.Direcciones, Room>();
    contenido = new Container<Template>(this);
    habitantes = new Container<Player>(this);
  }

  public Room(String name, String description) {
    this();
    setName(name);
    setDescription(description);
  }

  public void setSortida(Room h, Exit.Direcciones s) {
    if (salidas.get(s) == null) {
      salidas.put(s, h);
      h.setSortida(this, Exit.inversa(s));
    }
  }

  public Room getSortida(Exit.Direcciones s) {
		if (salidas.get(s) != null) {
			return salidas.get(s);
		} else {
			return null;
		}
  }

  public void mergeExits(Room otherInstance) {
    HashMap<Direcciones, Room> otherExits = otherInstance.getSalidas();
    for (Direcciones d : otherExits.keySet()) {
      setSortida(otherExits.get(d), d);
    }
  }

  public String llistaSortides() {
    String txt = "[";
    for (Exit.Direcciones e : salidas.keySet()) {
      txt += e + ", ";
    }
    txt = txt.substring(0, txt.length() - 2) + "]";
    return txt;
  }

  public String mostrar() {
    return mostrar(null);
  }

  public String mostrar(Template t) {
    String txt, txt2;
    txt = "[" + getName() + "]\n\r" +
        getDescription() + "\n\r" +
        llistaSortides();

    txt2 = contenido.list();
		if (txt2.length() > 0) {
			txt += "\n\r" + txt2;
		}
		if (t != null && t instanceof Player) {
			txt2 = habitantes.list((Player) t);
		} else {
			txt2 = habitantes.list();
		}
		if (txt2.length() > 0) {
			txt += "\n\r" + txt2;
		}

    return txt;
  }

  public void addPlayer(Player p) {
    habitantes.add(p);
  }

  public void removePlayer(Player p) {
    habitantes.remove(p);
  }

  public void addObjeto(Template o) {
    contenido.add(o);
  }

  public void removeObjeto(Template o) {
    contenido.remove(o);
  }

  public Item findObjeto(String nombre) {
    return (Item) contenido.find(nombre);
  }

  public Player findPlayer(String nombre) {
    return (Player) habitantes.find(nombre);
  }

  /**
   * Busca por nombre en la room, ya sea un objeto o un jugador
   */
  public Template find(String nombre) {
    Template o = findPlayer(nombre);
		if (o == null) {
			o = findObjeto(nombre);
		}

    return o;
  }

  /**
   * Env&iacute;a un mensaje a cada jugador de la habitaci&oacute;n excepto a los indicados
   *
   * @param msg Mensaje a enviar
   * @param except Jugador/es excluidos
   */
  public void sendAll(String msg, Player... except) {
    // Lista de destinatarios
    List<Player> l_dest = habitantes.getAll();
    // Eliminamos de la copia las excepciones
    l_dest.removeAll(Arrays.asList(except));
    //Enviamos mensaje a cada jugador de la lista definitiva
    for (Player p : l_dest) {
      p.getControl().send(msg);
    }
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub

  }

  @Override
  public String toString() {
    return this.getDescription();
  }

}
