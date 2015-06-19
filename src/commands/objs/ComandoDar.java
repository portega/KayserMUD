package commands.objs;

import commands.Comando;
import commands.CommandException;

import world.Habitacio;
import world.Objeto;
import world.Player;

public class ComandoDar implements Comando {

	@Override
	public String execute(Player p, String args)
			throws CommandException {
		String txt = "";
		String[] params = args.split(" ");
		if (params.length != 3) txt = "No te entiendo";
		else {
			Habitacio h = (Habitacio)p.getContenedor();
			Player p2 = h.findPlayer(params[2]);
			Objeto o = (Objeto)p.findObjeto(params[1]);
			p2.addInventario(o);
			p.removeInventario(o);
			txt = "Le das "+o.getDescripcion()+" a "+p2.getNombre();
			p2.getControl().send(p.getNombre()+" te da "+o.getDescripcion());
			
			h.sendAll(p.getNombre()+" le da "+o.getDescripcion()+" a "+p2.getNombre(), p, p2);
		}
		
		return txt;
	}

}
