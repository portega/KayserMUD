package commands;

import java.util.TimerTask;

import server.Constantes;
import server.PlayerThread;
import server.Servidor;
import world.Habitacio;
import world.Player;

public class ComandoCombate extends TimerTask implements Comando {
	private Player player;
	public static final int SEGUNDOS = 1000; // milisegundos
	@Override
	public String execute(Player p, String args) throws CommandException {
		String txt = "";
		String[] params = args.split(" ");
		Habitacio h = (Habitacio)p.getContenedor();
		if (params.length < 2) {
			return "Matar que?";
		}
		Player victim = h.findPlayer(params[1]);
		
		if (victim == p) {
			p.setVida(p.getVida()-p.getDamm());
			return "Te danyas a ti mismo.  Ouch!";
		}
		if (victim == null) return "No existe";
		
		player = p;
		p.setVictim(victim);
		victim.setVictim(p);
		p.setEstado(Constantes.Estados.FIGHT);
		victim.setEstado(Constantes.Estados.FIGHT);
		txt = "Atacas a "+victim.getNombre();

		Servidor.getTimer().scheduleAtFixedRate(this, 0, 3*SEGUNDOS);
		return txt;
	}
	@Override
	public void run() {
		player.getControl().send(hit(player, player.getVictim()));
	}
	
	private String hit(Player ch, Player victim) {
		int vida = victim.getVida();
		int damage = ch.getDamm();
		vida = vida - damage;
		victim.setVida(vida);
		String txt = "\r\nTu mamporro "+str_damage(victim.getMaxVida(),damage)+" a "+victim.getNombre();
		if (vida <= 0) {
			victim.setVida(1);
			victim.setEstado(Constantes.Estados.NORMAL);
			ch.setEstado(Constantes.Estados.NORMAL);
			if (victim.getControl() instanceof PlayerThread) 
				victim.getControl().send("TE HAS MUERTO");
			else {
				Habitacio h = (Habitacio)player.getContenedor();
				h.removePlayer(victim);
				//TODO: respawn del mob
			}
			//victim.update();
			//ch.setVictim(null);
			txt += "\r\n"+victim.getNombre()+" ha muerto";
			cancel();
		} else {		
			vida = ch.getVida();
			damage = victim.getDamm();
			vida = vida - damage;
			ch.setVida(vida);
			txt += "\r\nEl golpe de "+victim.getNombre()+" te "+str_damage(ch.getMaxVida(),damage);
			txt += "\n\r"+victim.getNombre()+" "+str_estado(victim);
			
			if (damage > ch.getMaxVida()/4) txt += "\n\rEso realmente te DOLIO!";
			if (vida < ch.getMaxVida()/4) txt += "\n\rTe gustaria dejar de sangrar tanto!";
			else if (vida < ch.getMaxVida()/2) txt += "\n\rEstas muy malherido";
			if (vida <= 0) {
				ch.setVida(1);
				victim.setEstado(Constantes.Estados.NORMAL);
				ch.setEstado(Constantes.Estados.NORMAL);
				txt += "\r\nTe han MATADO!!";
				cancel();
			}
		}
		return txt;
	}
	
	private String str_damage(int vida, int damage) {
		String txt;
		String[] damage_txt = {
				"roza",
				"aranya",
				"golpea",
				"desgarra",
				"hiere",
				"parte",
				"muele",
				"devasta",
				"desmonta",
				"MUTILA!",
				"DESTROZA!",
				"DESTRIPA!",
				"MASACRA!",
				"DEMUELE!",
				"* FULMINA *!",
				"* PULVERIZA *!",
				"* DESINTEGRA *!",
				"** DESCOMPONE **!",
				"** EXTERMINA **!",
				"*** ANIQUILA ***!"
		};
		int[] damage_pt = {1,2,3,5,10,15,20,30,40,50,55,60,65,70,75,80,85,90,95,100};
		int percent = (int)(damage*100.0/vida);
		
		txt = damage_txt[0];
		for (int i = 1; i < damage_pt.length; i++) {
			if (damage_pt[i] <= percent) txt = damage_txt[i];
		}
		if (percent > 100) txt = "**** DESMATERIALIZA ****!";
		return txt;
	}
	
	public static String str_estado(Player p) {
		int percent = 10-(int)(10*p.getVida()/p.getMaxVida());
		String[] v_txt = {
				"esta en perfecta forma.",
				"tiene algun aranyazo.",
				"tiene algun moraton.",
				"tiene algunos cortes.",
				"tiene bastantes heridas.",
				"tiene heridas de consideracion.",
				"esta sangrando a borbotones.",
				"esta cubierto de sangre.",
				"palidece al ver que la muerte se acerca.",
				"esta entre la vida y la muerte.",
				"esta practicamente muerto."
		};
		
		return v_txt[percent];
	}
}
