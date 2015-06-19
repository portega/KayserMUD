package world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import world.Sortida.Direcciones;

public class Habitacio extends Template {
	private HashMap<Direcciones, Habitacio> sortides;
	private Container<Template> contenido;
	private Container<Player> habitantes;
	private int moveCost = 1;
	
	public Habitacio(String short_desc, String long_desc) {
		setNombre(short_desc);
		setDescripcion(long_desc);
		sortides = new HashMap<Sortida.Direcciones, Habitacio>();
		contenido = new Container<Template>(this);
		habitantes = new Container<Player>(this);
	}

	public void setSortida(Habitacio h, Sortida.Direcciones s) {
		if (sortides.get(s) == null) {
			sortides.put(s, h);
			h.setSortida(this, Sortida.inversa(s));
		}
	}
	
	public Habitacio getSortida(Sortida.Direcciones s) {
		if (sortides.get(s) != null) return sortides.get(s);
		else return null;
	}
	
	public String llistaSortides() {
		String txt = "[";
		for (Sortida.Direcciones e : sortides.keySet()) {
			txt += e+", ";
		}
		txt = txt.substring(0,txt.length()-2)+"]";
		return txt;
	}
	
	public String mostrar() {
		return mostrar(null);
	}

	public String mostrar(Template t) {
		String txt, txt2;
		txt = "["+getNombre()+"]\n\r"+
		getDescripcion()+"\n\r"+
		llistaSortides();
		
		txt2 = contenido.list();
		if (txt2.length() > 0) txt += "\n\r" + txt2;
		if (t != null && t instanceof Player) txt2 = habitantes.list((Player)t);
		else txt2 = habitantes.list();
		if (txt2.length() > 0) txt += "\n\r" + txt2;
		
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
	
	public Objeto findObjeto(String nombre) {
		return (Objeto)contenido.find(nombre);
	}

	public Player findPlayer(String nombre) {
		return (Player)habitantes.find(nombre);
	}

	/**
         * Busca por nombre en la room, ya sea un objeto o un jugador
         * @param nombre
         * @return 
         */
        public Template find(String nombre) {
		Template o = findPlayer(nombre);
		if (o == null) o = findObjeto(nombre);
		
		return o;
	}
	
	/**
         * Env&iacute;a un mensaje a cada jugador de la habitaci&oacute;n excepto a los indicados
         * @param msg Mensaje a enviar
         * @param except Jugador/es excluidos
         */
        public void sendAll(String msg, Player... except) {
            // Lista de destinatarios
            List<Player> l_dest = new ArrayList<Player>();
            //Copiamos la lista para no modificar el contenido de la room
            Collections.copy(habitantes.getAll(), l_dest);
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
	@Id
        @GeneratedValue
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	public void setMoveCost(int moveCost) {
		this.moveCost = moveCost;
	}

	public int getMoveCost() {
		return moveCost;
	}

    @Override
    public String toString() {
        return this.getDescripcion();
    }


}
