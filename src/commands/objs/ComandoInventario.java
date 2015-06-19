package commands.objs;

import commands.Comando;
import commands.CommandException;

import world.Player;

public class ComandoInventario implements Comando {

	@Override
	public String execute(Player p, String args)
			throws CommandException {
		String txt = p.getInventario();
		if (txt.length() == 0) txt = "Que triste, no llevas nada en los bolsillos!";
		
		return txt;
	}

}
