package server;

import commands.Social;
import java.util.TreeMap;
import localization.Language;
import world.Room;
import world.Player;

import commands.Interpreter;

public abstract class Control extends Thread {
	protected Room currentRoom;
	protected Player player;
	protected Interpreter dicc;
	protected Language language;
	protected TreeMap<String, Social> socials;

	public Social getSocial(String name) {
		String key = socials.ceilingKey(name);
		if (key.startsWith(name)) return socials.get(key);

		return null;
	}
	public TreeMap<String, Social> getSocials() {
		return socials;
	}

	public void setSocials(TreeMap<String, Social> social) {
		this.socials = social;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Control(String txt, Room h, Player p) {
		super(txt);
		setDiccionari(new Interpreter(this));
		setHabitacio(h);
		setPlayer(p);
		p.setControl(this);
		h.addPlayer(p);
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
	
	public void finish() {
		
	}
	
	@Override
	public abstract void run();
	
	public abstract void send(String mensaje);

	public Room getHabitacio() {
		return currentRoom;
	}

	public void setHabitacio(Room habActual) {
		currentRoom = habActual;
		
	}
	
	public Interpreter getDiccionari() {
		return dicc;
	}
	
	public void setDiccionari(Interpreter d) {
		dicc = d;
	}
}
