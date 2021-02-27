package world;

import java.util.Vector;


public class Exit {
	public static enum Direcciones {NORTH, EAST, WEST, SOUTH, UP, DOWN};
	public static enum Estado {ABIERTA, CERRADA, BLOQUEADA};
	public static enum Tipo {NO_PASS, INVIS, HIDDEN};
	
	private Room origen, destino;
	private Vector<Tipo> flags_tipo;
	
	public Exit(Room or, Room dest) {
		origen = or;
		destino = dest;
	}

	public Room getOrigen() {
		return origen;
	}

	public void setOrigen(Room origen) {
		this.origen = origen;
	}

	public Room getDestino() {
		return destino;
	}

	public void setDestino(Room destino) {
		this.destino = destino;
	}
	
	public void addTipo(Tipo t) {
		if (!flags_tipo.contains(t)) flags_tipo.add(t);
	}
	
	public static Direcciones inversa(Direcciones s) {
		Direcciones result = null;
		switch (s) {
		case NORTH: result = Direcciones.SOUTH;
		break;
		case SOUTH: result = Direcciones.NORTH;
		break;
		case EAST: result = Direcciones.WEST;
		break;
		case WEST: result = Direcciones.EAST;
		break;
		case UP: result = Direcciones.DOWN;
		break;
		case DOWN: result = Direcciones.UP;
		break;
		}
		return result;
	}
}
