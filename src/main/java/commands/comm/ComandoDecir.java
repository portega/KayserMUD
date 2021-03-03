package commands.comm;

import commands.Comando;
import commands.CommandException;
import server.Constants;

import world.Room;
import world.Player;

public class ComandoDecir implements Comando {

	@Override
	public String execute(Player p, String args) throws CommandException {
		String[] params = args.split(" ");
		String txt = "";
		
		Room h = (Room)p.getContenedor();
		Player target = h.findPlayer(params[1]);
		
		if (target != null) {
			String mensaje = args.substring(args.indexOf(params[1])+params[1].length());
			target.getControl().send(p.getNombre()+" te dice '"+mensaje+"'");
			txt = Constants.VERDE + "Le dices a "+target.getNombre()+" '"+Constants.RESET+mensaje+Constants.VERDE+"'"+ Constants.EOL;
		} else {
			String mensaje = args.substring(params[0].length()+1);
			h.sendAll(p.getNombre()+" dice '"+mensaje+"'",p);
			txt = Constants.VERDE+ "Dices '"+Constants.RESET+mensaje+Constants.VERDE+"'"+ Constants.EOL;
		}
		return txt;
	}

}
