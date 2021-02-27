package commands;

import world.Room;
import world.Player;

public class ComandoSocial implements Comando {

	@Override
	public String execute(Player p, String args) throws CommandException {
		String[] params = args.split(" ");
		String player_msg = "", txt;
		Social s = p.getControl().getSocial(params[0]);
		
		if (s == null) return "Eso no lo entiendo";

		Room h = (Room)p.getContenedor();
		Player p2;
		
		// SOCIAL A SI MISMO
		if (params.length == 1) {
			txt = s.getMessage(Social.Targets.ACT_NOTARG);
			player_msg = processMsg(txt, p);

			// Mensaje al resto de la room
			txt = s.getMessage(Social.Targets.PER_NOTARG);
			if (txt != null) h.sendAll(processMsg(txt,p), p); 
		// SOCIAL SOBRE OTRO
		} else {
			p2 = h.findPlayer(params[1]);
			// NO EXISTE EL TARGET
			if (p2 == null) {
				// Error para si mismo
				txt = s.getMessage(Social.Targets.ACT_BADTARG);
				player_msg = processMsg(txt, p);
			// EXISTE TARGET Y ES EL MISMO
			} else if (p.getNombre().equals(p2.getNombre())){
				// Mensaje a si mismo
				txt = s.getMessage(Social.Targets.ACT_ACTTARG);
				player_msg = processMsg(txt, p);
				
				// Mensaje al resto de la room
				txt = s.getMessage(Social.Targets.PER_ACTTARG);
				if (txt != null) h.sendAll(processMsg(txt,p), p);
			// EXISTE TARGET Y ES OTRO
			} else {
				// Mensaje a si mismo
				txt = s.getMessage(Social.Targets.ACTOR);
				player_msg = processMsg(txt, p, p2);
				
				// Mensaje al otro
				txt = s.getMessage(Social.Targets.TARGET);
				p2.getControl().send(processMsg(txt, p, p2));
				
				// Mensaje al resto de la room
				txt = s.getMessage(Social.Targets.PEERS);
				if (txt != null) h.sendAll(processMsg(txt,p,p2), p, p2);
			}
		}
		return player_msg;
	}

	private String processMsg(String input, Player... players) {
		String output;
		
		//TODO: $s, $m, etc segun Genero
		output = input.replaceAll("\\$n", players[0].getNombre());
		if (players.length == 2) {
			output = output.replaceAll("\\$N", players[1].getNombre());
		}
		return output;
	}
}
