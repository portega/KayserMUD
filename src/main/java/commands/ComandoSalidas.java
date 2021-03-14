/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;

import world.Room;
import world.Player;

/**
 *
 * @author Pau
 */
public class ComandoSalidas implements Comando {

    @Override
    public String execute(Player p, String args) throws CommandException {
        String txt = "";
        Room h = (Room)p.getOwner();
        txt = h.llistaSortides();
        return txt;
    }

}
