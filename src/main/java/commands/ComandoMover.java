package commands;

import server.PlayerThread;
import world.Room;
import world.Player;
import world.Exit.Direcciones;

public class ComandoMover implements Comando {

	@Override
	public String execute(Player p, String args)
			throws CommandException {
		String txt = "";
		Room h, nova_hab;

		h = (Room)p.getContenedor();
		if (args.startsWith("e")) nova_hab = h.getSortida(Direcciones.ESTE);
		else if (args.startsWith("o")) nova_hab = h.getSortida(Direcciones.OESTE);
		else if (args.startsWith("n")) nova_hab = h.getSortida(Direcciones.NORTE);
		else if (args.startsWith("s")) nova_hab = h.getSortida(Direcciones.SUR);
		else if (args.startsWith("ab")) nova_hab = h.getSortida(Direcciones.ABAJO);
		else if (args.startsWith("a")) nova_hab = h.getSortida(Direcciones.ARRIBA);
		else return "No puedes moverte alli";
		
		if (nova_hab != null) {
			//TODO: Y los mobs?
			if (p.getControl() instanceof PlayerThread && p.getMove() < h.getMoveCost()) {
				return "Estas demasiado cansado";
			}
			p.setMove(p.getMove()-h.getMoveCost());
			nova_hab.addPlayer(p);
			h.removePlayer(p);
			txt = nova_hab.mostrar(p);
			
			h.sendAll(p.getNombre()+" se ha ido.", p);
			
			nova_hab.sendAll(p.getNombre()+" ha llegado.", p);
		} else txt = "No hay salida";
		
		return txt;
	}

}
