package commands;

import static world.Player.StatType.STAMINA;

import server.PlayerThread;
import world.Room;
import world.Player;
import world.Exit.Direcciones;

public class ComandoMover implements Comando {

	@Override
	public String execute(Player p, String args)
			throws CommandException {
		String txt;
		Room h, nova_hab;

		h = (Room)p.getOwner();
		if (args.startsWith("e")) nova_hab = h.getSortida(Direcciones.EAST);
		else if (args.startsWith("o")) nova_hab = h.getSortida(Direcciones.WEST);
		else if (args.startsWith("n")) nova_hab = h.getSortida(Direcciones.NORTH);
		else if (args.startsWith("s")) nova_hab = h.getSortida(Direcciones.SOUTH);
		else if (args.startsWith("ab")) nova_hab = h.getSortida(Direcciones.DOWN);
		else if (args.startsWith("a")) nova_hab = h.getSortida(Direcciones.UP);
		else return "No puedes moverte alli";
		
		if (nova_hab != null) {
			//TODO: Y los mobs?
			if (p.getControl() instanceof PlayerThread && p.getCurrent(STAMINA) < h.getMoveCost()) {
				return "Estas demasiado cansado";
			}
			p.addStat(STAMINA, -h.getMoveCost());
			nova_hab.addPlayer(p);
			h.removePlayer(p);
			txt = nova_hab.mostrar(p);
			
			h.sendAll(p.getName()+" se ha ido.", p);
			
			nova_hab.sendAll(p.getName()+" ha llegado.", p);
		} else txt = "No hay salida";
		
		return txt;
	}

}
