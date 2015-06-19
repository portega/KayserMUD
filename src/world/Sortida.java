package world;

import java.util.Vector;


public class Sortida {
	public static enum Direcciones {NORTE, ESTE, OESTE, SUR, ARRIBA, ABAJO};
	public static enum Estado {ABIERTA, CERRADA, BLOQUEADA};
	public static enum Tipo {NO_PASS, INVIS, HIDDEN};
	
	private Habitacio origen, destino;
	private Vector<Tipo> flags_tipo;
	
	public Sortida(Habitacio or, Habitacio dest) {
		origen = or;
		destino = dest;
	}

	public Habitacio getOrigen() {
		return origen;
	}

	public void setOrigen(Habitacio origen) {
		this.origen = origen;
	}

	public Habitacio getDestino() {
		return destino;
	}

	public void setDestino(Habitacio destino) {
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
