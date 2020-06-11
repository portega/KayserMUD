package world;

import java.util.Vector;


public class Exit {
	public static enum Direcciones {NORTE, ESTE, OESTE, SUR, ARRIBA, ABAJO};
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
		case NORTE: result = Direcciones.SUR;
		break;
		case SUR: result = Direcciones.NORTE;
		break;
		case ESTE: result = Direcciones.OESTE;
		break;
		case OESTE: result = Direcciones.ESTE;
		break;
		case ARRIBA: result = Direcciones.ABAJO;
		break;
		case ABAJO: result = Direcciones.ARRIBA;
		break;
		}
		return result;
	}
}
