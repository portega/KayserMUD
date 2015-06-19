package commands;

import world.Player;

public interface Comando {
    public abstract String execute(Player p, String args) throws CommandException;
}
