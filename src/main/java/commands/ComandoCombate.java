package commands;

import static world.Player.StatType.HEALTH;

import java.util.TimerTask;

import server.Constants;
import server.PlayerThread;
import server.Server;
import world.Room;
import world.Player;

public class ComandoCombate extends TimerTask implements Comando {
	private Player player;
	public static final int SEGUNDOS = 1000; // milisegundos
	@Override
	public String execute(Player p, String args) throws CommandException {
		String txt = "";
		String[] params = args.split(" ");
		Room h = (Room)p.getOwner();
		if (params.length < 2) {
			return "Matar que?";
		}
		Player victim = h.findPlayer(params[1]);
		
		if (victim == p) {
			p.addStat(HEALTH, -p.getDamage());
			return "Te danyas a ti mismo.  Ouch!";
		}
		if (victim == null) return "No existe";
		
		player = p;
		p.setVictim(victim);
		victim.setVictim(p);
		p.setStatus(Constants.Estados.FIGHT);
		victim.setStatus(Constants.Estados.FIGHT);
		txt = "Atacas a "+victim.getName();

		Server.getTimer().scheduleAtFixedRate(this, 0, 3*SEGUNDOS);
		return txt;
	}
	@Override
	public void run() {
		player.getControl().send(hit(player, player.getVictim()));
	}
	
	private String hit(Player ch, Player victim) {
		int vida = victim.getCurrent(HEALTH);
		int damage = ch.getDamage();
		vida = vida - damage;
		victim.addStat(HEALTH, -damage);
		String txt = "\r\nTu mamporro "+str_damage(victim.getMax(HEALTH),damage)+" a "+victim.getName();
		if (vida <= 0) {
			victim.setCurrent(HEALTH, 1);
			victim.setStatus(Constants.Estados.NORMAL);
			ch.setStatus(Constants.Estados.NORMAL);
			if (victim.getControl() instanceof PlayerThread) 
				victim.getControl().send("TE HAS MUERTO");
			else {
				Room h = (Room)player.getOwner();
				h.removePlayer(victim);
				//TODO: respawn del mob
			}
			//victim.update();
			//ch.setVictim(null);
			txt += "\r\n"+victim.getName()+" ha muerto";
			cancel();
		} else {		
			vida = ch.getCurrent(HEALTH);
			damage = victim.getDamage();
			vida = vida - damage;
			ch.setCurrent(HEALTH, vida);
			txt += "\r\nEl golpe de "+victim.getName()+" te "+str_damage(ch.getMax(HEALTH),damage);
			txt += "\n\r"+victim.getName()+" "+str_estado(victim);

			int maxVida = ch.getMax(HEALTH);
			if (damage > maxVida/4) txt += "\n\rEso realmente te DOLIO!";
			if (vida < maxVida/4) txt += "\n\rTe gustaria dejar de sangrar tanto!";
			else if (vida < maxVida/2) txt += "\n\rEstas muy malherido";
			if (vida <= 0) {
				ch.setCurrent(HEALTH, 1);
				victim.setStatus(Constants.Estados.NORMAL);
				ch.setStatus(Constants.Estados.NORMAL);
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
		int percent = 10-(int)(10*p.getCurrent(HEALTH)/p.getMax(HEALTH));
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
