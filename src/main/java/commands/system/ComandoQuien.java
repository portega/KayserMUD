package commands.system;

import world.Player;
import commands.Comando;
import commands.CommandException;
import java.util.HashMap;
import server.Constants;
import server.Server;

public class ComandoQuien implements Comando {

	@Override
	public String execute(Player p, String args) throws CommandException {
		HashMap<String, Player> l_players = Server.getPjs_conectados();
                String txt = "";
                txt += "Jugadores conectados"+ Constants.EOL;
                txt += "-----------------------------------------"+ Constants.EOL;
                for (Player p2 : l_players.values()) {
                    txt += p2.getNombre()+ Constants.EOL;
                }
                txt += "-----------------------------------------"+ Constants.EOL;
		return txt;
	}

}
