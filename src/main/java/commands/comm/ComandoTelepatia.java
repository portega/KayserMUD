package commands.comm;

import server.Server;
import world.Player;

import commands.Comando;
import commands.CommandException;

public class ComandoTelepatia implements Comando {

	@Override
	public String execute(Player p, String args) throws CommandException {
		String txt;
		String[] params = args.split(" ");
		// params[0] telepatia
		// params[1] nombre_target
		// params[2...] mensaje
		if (params.length < 2) return "Telepatia a quien?";
		if (params.length < 3) return "Decir que?";

		Player target = Server.getPjs_conectados().get(params[1]);
		
		if (target == null) return "No encuentro a "+params[1];
		String msg = args.substring(args.indexOf(params[1])+params[1].length());
		target.getControl().send(p.getName()+" te transmite: "+msg);
		txt = "Transmites a "+params[1]+": "+msg;
		return txt;
	}

}
