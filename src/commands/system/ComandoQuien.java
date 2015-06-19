package commands.system;

import world.Player;
import commands.Comando;
import commands.CommandException;
import java.util.HashMap;
import server.Constantes;
import server.Servidor;

public class ComandoQuien implements Comando {

	@Override
	public String execute(Player p, String args) throws CommandException {
		HashMap<String, Player> l_players = Servidor.getPjs_conectados();
                String txt = "";
                txt += "Jugadores conectados"+Constantes.EOL;
                txt += "-----------------------------------------"+Constantes.EOL;
                for (Player p2 : l_players.values()) {
                    txt += p2.getNombre()+Constantes.EOL;
                }
                txt += "-----------------------------------------"+Constantes.EOL;
		return txt;
	}

}
