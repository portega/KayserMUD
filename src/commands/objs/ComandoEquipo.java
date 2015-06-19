package commands.objs;

import commands.Comando;
import commands.CommandException;
import world.Player;

public class ComandoEquipo implements Comando {

    @Override
    public String execute(Player p, String args)
            throws CommandException {
        String txt = p.getEquipo();
        if (txt.equals("")) {
            txt = "Estás desnudo";
        }
        return txt;
    }
}
