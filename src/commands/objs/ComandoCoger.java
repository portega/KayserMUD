package commands.objs;

import commands.Comando;
import commands.CommandException;

import world.Habitacio;
import world.Player;
import world.Template;

public class ComandoCoger implements Comando {

	@Override
	public String execute(Player p, String args)
			throws CommandException {
		String txt = "";
		String[] params = args.split(" ");
		Habitacio h = (Habitacio)p.getContenedor();
		Template obj = h.findObjeto(params[1]);
		if (obj != null) {
			p.addInventario(obj);
			h.removeObjeto(obj);
			txt = "Coges "+obj.getNombre();
		} else txt = "No lo encuentras";
		return txt;
	}
}
