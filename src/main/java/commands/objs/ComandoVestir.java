/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commands.objs;

import commands.Comando;
import commands.CommandException;
import world.Player;
import world.Template;

/**
 *
 * @author Pau
 */
public class ComandoVestir implements Comando {

    @Override
    public String execute(Player p, String args) throws CommandException {
        String[] params;
        if (args != null && args.length() >0) {
            params = args.split(" ");
            if (params.length != 2) {
                return "¿Vestir qué?";
            }
        } else {
            return "¿Vestir qué?";
        }
        Template obj = p.findObjeto(params[1]);

        if (obj == null) return "No tienes eso";

        p.removeInventario(obj);
        p.addEquipo(obj);

        return "Te has equipado con "+obj.getNombre();
    }

}
