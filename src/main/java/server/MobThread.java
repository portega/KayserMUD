package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import world.Room;
import world.Player;

public class MobThread extends Control {
	/** Rutina del mob */
	private String[] script = null;
	/** Segundos de descanso en la ejecucion de la rutina */
	private int intervalo = Constants.DEFAULT_INTERVALO;
	/** Indice del vector de rutinas */
	private int index = 0;
	/** Mobprogs */
	private HashMap<Constants.MobProgs, List<String>> progs;

	/**
	 * Inicializa los mobprogs y llama al constructor de Control
	 * @param p Jugador asociado
	 * @param h Habitacion actual
	 */
	public MobThread(Player p, Room h) {
		super("MobThread", h, p);
		progs = new HashMap<Constants.MobProgs, List<String>>();
	}

	/**
	 * Comprueba el texto recibido y activa los mobprogs
	 * @param mensaje texto de estado o mensaje recibido
	 */
	@Override
	public void send(String mensaje) {
		String target = mensaje.split(" ")[0];
		Player ptarget = currentRoom.findPlayer(target);
		if (mensaje.indexOf("te dice") > 0) {
			run_progs(Constants.MobProgs.SPEECH, ptarget);
		} else if (mensaje.indexOf("te da") > 0) {
			run_progs(Constants.MobProgs.GIVE, ptarget);
		}
	}
	
	/**
	 * Ejecuta los mobprogs del tipo solicitado
	 * @param tipo Tipo de mobprog
	 * @param target Player que ha provocado el trigger, si lo hay
	 * @return Resultado del mobprog
	 */
	private Object run_progs(Constants.MobProgs tipo, Player target) {
		//System.out.println("run_progs: "+tipo+( target != null ? ", "+target.getNombre() : "") );
		Object result = null;
		if (progs.get(tipo) == null) return null;
		for (String p : progs.get(tipo)) {
			Context cx = Context.enter();
			try {
				Scriptable scope = cx.initStandardObjects();
				Object jsOut = Context.javaToJS(this, scope);
				ScriptableObject.putProperty(scope, "control", jsOut);
				if (target != null) {
					jsOut = Context.javaToJS(target, scope);
					ScriptableObject.putProperty(scope, "target", jsOut);
				}
				result = cx.evaluateString(scope, p, "MobThread.java", 36, null);
			} finally {
				Context.exit();
			}
		}
		return result;
	}

	/**
	 * Ejecuta el script y/o mobprogs
	 */
	@Override
	public void run() {
		if (script == null && progs.isEmpty())
			return;
		try {
			while (true) {
				switch (player.getStatus()) {
				case FIGHT:
					run_progs(Constants.MobProgs.FIGHT, player.getVictim());
					break;
				default: // NORMAL
					run_progs(Constants.MobProgs.RAND, null);
					if (script != null) {
						dicc.find(script[index]);
						index = (index + 1) % script.length;
					}
					break;
				}
				sleep(intervalo);
			}
		} catch (InterruptedException e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * Devuelve el listado de &oacute;rdenes de la rutina del mob, si tiene
	 * @return Array con los comandos que ejecuta en bucle el mob
	 */
	public String[] getScript() {
		return script;
	}

	/**
	 * Fija la rutina del mob
	 * @param script Array de ordenes del mob
	 */
	public void setScript(String[] script) {
		this.script = script;
	}

	/**
	 * Devuelve el intervalo de espera entre comandos del script
	 * 
	 * @return intervalo de espera, en segundos
	 */
	public int getIntervalo() {
		return intervalo / Constants.SEGUNDOS;
	}

	/**
	 * Fija el intervalo de espera entre comandos del script
	 * 
	 * @param intervalo
	 *            Espera entre comandos, en segundos
	 */
	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo * Constants.SEGUNDOS;
	}

	/**
	 * A&ntilde;ade un mobprog del tipo indicado
	 * @param tipo Tipo de mobprog
	 * @param prog Script del mobprog
	 */
	public void addProg(Constants.MobProgs tipo, String prog) {
		List<String> l = progs.get(tipo);
		if (l == null) {
			l = new ArrayList<String>();
			progs.put(tipo, l);
		}
		l.add(prog);
		
	}

	/**
	 * Ejecuta una serie de &oacute;rdenes, con sintaxis de consola
	 * @param ordenes Array con las &oacute;rdenes
	 */
	public void exec(String[] ordenes) {
		for (String s : ordenes) {
			dicc.find(s);
		}
	}

	/**
	 * Ejecuta una orden
	 * @param s Comando a ejecutar, con sintaxis de consola
	 */
	public void exec(String s) {
		dicc.find(s);
	}
}
