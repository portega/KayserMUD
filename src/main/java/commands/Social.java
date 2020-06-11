package commands;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;

import world.Gender;
import world.Player;
import world.Race;

public class Social {
	public enum Targets {
		ACT_NOTARG, 	// Actor, no target 
		PER_NOTARG, 	// Peers, no target
		ACT_BADTARG, 	// Actor, bad target
		ACT_ACTTARG, 	// Actor, actor target
		PER_ACTTARG, 	// Peers, actor target
		ACTOR, 
		TARGET, 
		PEERS};
	private String nombre;
	private Hashtable<Targets, String> mensajes;
	private Hashtable<Race, Boolean> razas;
	private Hashtable<Gender, Boolean> generos;
	
	public Social(String nom) {
		setNombre(nom);
		mensajes = new Hashtable<Targets, String>();
		razas = new Hashtable<Race, Boolean>();
		generos = new Hashtable<Gender, Boolean>();
	}
	
	public void addMessage(String target, String msg) {
		mensajes.put(Targets.valueOf(target), msg);
	}
	
	public String getMessage(Targets t) {
		return mensajes.get(t);
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void addRace(Race r, boolean allowed) {
		razas.put(r, new Boolean(allowed));
	}
	
	public void addGender(Gender g, boolean allowed) {
		generos.put(g, new Boolean(allowed));
	}
	
	public void allow(String tipo, String valor) {
		if (tipo.equals("GENDER")) addGender(Gender.getGender(valor), true);
		else if (tipo.equals("RACE")) addRace(Race.getRace(valor), true); 
	}
	
	public void deny(String tipo, String valor) {
		if (tipo.equals("GENDER")) addGender(Gender.getGender(valor), false);
		else if (tipo.equals("RACE")) addRace(Race.getRace(valor), false); 
	}

	public boolean allows(Player player) {
		// TODO comprobar restricciones al Player (Gender, Race)
		return true;
	}
}
