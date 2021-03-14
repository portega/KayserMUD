package commands.system;

import commands.Comando;
import commands.CommandException;

import world.Room;
import world.Player;

public class ComandoSalir implements Comando {

	@Override
	public String execute(Player p, String args)
			throws CommandException {
		Room h = (Room)p.getOwner();
		h.removePlayer(p);
		return "quit";
	}


}
