/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commands.objs;

import commands.Comando;
import commands.CommandException;
import java.util.Optional;
import world.EquipmentObj;
import world.Player;
import world.Template;

/**
 *
 * @author Pau
 */
public class ComandoDesvestir  implements Comando {

    @Override
    public String execute(Player p, String args) throws CommandException {
        String[] params;
        if (args != null && args.length() >0) {
            params = args.split(" ");
            if (params.length != 2) {
                return "¿Desvestir qué?";
            }
        } else {
            return "¿Desvestir qué?";
        }
        Optional<EquipmentObj> obj = p.findEquipo(params[1]);

        if (obj.isPresent()) {
            EquipmentObj eq = obj.get();
            p.removeEquipo(eq);
            p.addInventario(eq);

            return "Te has quitado "+eq.getNombre();
        }

        return "No llevas eso";
    }
}
