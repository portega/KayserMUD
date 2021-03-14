package commands;

import server.Constants;
import world.Room;
import world.Player;
import world.Template;

public class ComandoMirar implements Comando {

	@Override
	public String execute(Player p, String args) throws CommandException {
		String txt = "";
		String[] params;
		if (args != null && args.length() >0) {
			params = args.split(" ");
			Room h = (Room)p.getOwner();
			switch (params.length) {
			case 1: // MIRAR	
				txt = h.mostrar(p);
				break;
			case 2: // MIRAR X
				Template o = h.find(params[1]);
				
				if (o == null) {
					// SALIDAS
					txt = "Ves una salida";
				}
				
				if (o != null) {
					txt = o.getDescription();
					if (o instanceof Player) txt += Constants.EOL+o.getName()+" "+ComandoCombate.str_estado((Player)o);
				}
				else txt = "No veo eso";
				break;
			case 3: // MIRAR EN X
				txt = "Miras en "+params[2];
				break;
			default:
				txt = "No entiendo eso";
				break;
			}
		}
		return txt;
	}

}
