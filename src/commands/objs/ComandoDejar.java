package commands.objs;

import commands.Comando;
import commands.CommandException;

import world.Habitacio;
import world.Player;
import world.Template;

public class ComandoDejar implements Comando {

	@Override
	public String execute(Player p, String args)
			throws CommandException {
		String txt = "";
		String[] params = args.split(" ");
		Habitacio h = (Habitacio)p.getContenedor();
		Template obj = p.findObjeto(params[1]);
		if (obj != null) {
			p.removeInventario(obj);
			h.addObjeto(obj);
			txt = "Dejas "+obj.getNombre();
		} else txt = "No lo llevas encima";
		return txt;
	}

}
