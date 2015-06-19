/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commands.system;

import commands.Comando;
import commands.CommandException;
import commands.Interpreter;
import server.Constantes;
import world.Player;

/**
 *
 * @author Pau
 */
public class ComandoHelp implements Comando {

    @Override
    public String execute(Player p, String args) throws CommandException {
        String txt = "Comandos disponibles"+Constantes.EOL;
        txt += "-----------------------------------------"+Constantes.EOL;
        int linea = 0;

        Interpreter dicc = p.getControl().getDiccionari();

        for (String s : dicc.list()) {
            if (s.length() == 1) continue;
            if (linea + s.length() > 80) {
                txt += Constantes.EOL;
                linea = 0;
            }
            txt += s+"\t";
            linea += s.length()+8; // Suponiendo TAB = 8 espacios
        }
        txt += Constantes.EOL+"-----------------------------------------"+Constantes.EOL;

        return txt;
    }

}
