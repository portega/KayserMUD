/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;

import world.Habitacio;
import world.Player;

/**
 *
 * @author Pau
 */
public class ComandoSalidas implements Comando {

    @Override
    public String execute(Player p, String args) throws CommandException {
        String txt = "";
        Habitacio h = (Habitacio)p.getContenedor();
        txt = h.llistaSortides();
        return txt;
    }

}
