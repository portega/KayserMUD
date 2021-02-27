package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import world.Room;
import world.Player;

public class PlayerThread extends Control {
	private Socket socket = null;
	private PrintWriter out;
	private String prompt = "<%h/%Hpv %m/%Mm %vmv>";

	public PlayerThread(Socket socket, Room inicio, Player p) {
		super("PlayerThread", inicio, p);
		this.socket = socket;
		try {
		out = new PrintWriter(socket.getOutputStream(), true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

    @Override
	public void run() {
		String inputLine, outputLine;
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));

			out.println("Bienvenido");
			out.print(currentRoom.mostrar(player)+"\n\r"+process_prompt()+" ");
			out.flush();
			while ((inputLine = in.readLine()) != null) {

				if (inputLine.length() > 0) {
					outputLine = dicc.find(inputLine);
					if (outputLine.equals("quit"))
						break;
					out.print(outputLine+"\n\r"+process_prompt()+" ");
					out.flush();
				} else {
					out.print(process_prompt()+" ");
					out.flush();
				}
			}
			System.out.println("Finalizado cliente");
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void send(String mensaje) {
		out.print("\n\r"+mensaje+"\n\r"+process_prompt()+" ");
		out.flush();
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getPrompt() {
		return prompt;
	}
	
	private String process_prompt() {
		String txt = prompt.replaceAll("%h", player.getVida()+"");
		txt = txt.replaceAll("%H", player.getMaxVida()+"");
		txt = txt.replaceAll("%m", player.getMana()+"");
		txt = txt.replaceAll("%M", player.getMaxMana()+"");
		txt = txt.replaceAll("%v", player.getMove()+"");
		txt = txt.replaceAll("%V", player.getMaxMove()+"");
		
		return txt;
	}
}
