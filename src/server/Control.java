package server;

import world.Habitacio;
import world.Player;

import commands.Interpreter;

public abstract class Control extends Thread {
	protected Habitacio hab_actual;
	protected Player player;
	protected Interpreter dicc;

	public Control(String txt, Habitacio h, Player p) {
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

	public Habitacio getHabitacio() {
		return hab_actual;
	}

	public void setHabitacio(Habitacio habActual) {
		hab_actual = habActual;
		
	}
	
	public Interpreter getDiccionari() {
		return dicc;
	}
	
	public void setDiccionari(Interpreter d) {
		dicc = d;
	}
}
