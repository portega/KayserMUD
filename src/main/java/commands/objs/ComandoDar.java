package commands.objs;

import commands.Comando;
import commands.CommandException;

import world.Item;
import world.Room;
import world.Player;

public class ComandoDar implements Comando {

	@Override
	public String execute(Player p, String args)
			throws CommandException {
		String txt = "";
		String[] params = args.split(" ");
		if (params.length != 3) txt = "No te entiendo";
		else {
			Room h = (Room)p.getOwner();
			Player p2 = h.findPlayer(params[2]);
			Item o = (Item)p.findObjeto(params[1]);
			p2.addInventario(o);
			p.removeInventario(o);
			txt = "Le das "+o.getDescription()+" a "+p2.getName();
			p2.getControl().send(p.getName()+" te da "+o.getDescription());
			
			h.sendAll(p.getName()+" le da "+o.getDescription()+" a "+p2.getName(), p, p2);
		}
		
		return txt;
	}

}
