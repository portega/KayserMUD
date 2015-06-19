package commands.system;

import commands.Comando;
import commands.CommandException;

import world.Habitacio;
import world.Player;

public class ComandoSalir implements Comando {

	@Override
	public String execute(Player p, String args)
			throws CommandException {
		Habitacio h = (Habitacio)p.getContenedor();
		h.removePlayer(p);
		return "quit";
	}


}
