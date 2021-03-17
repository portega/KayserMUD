package world;

import java.util.List;

public class Item extends Template {

	public enum Size {S, M, L};
	public enum Rank {NOVATO, APRENDIZ, ESCUDERO, CABALLERO, HEROE, INMORTAL, CREADOR};
	//serveixen els de Player?
	public enum Type {TESORO, CUERPO, COMIDA, BEBIDA, ALCOHOL, LUZ, ARMADURA, ARMA, PERGAMINO, VARA_MAGICA, LLAVE}
	//segur que en falten, certes accions requereixen d'un tipus d'item concret

	private Item.Size size;
	private Rank rank;
	private int weight;
	private BodyPart wear;
	private long timer;  // para bombas u objetos perecederos como food, cuerpos, etc.

	private Material material;
	//no sé si cal una classe o serveix un enum, ja que depén si hi fem més atributs dependents, com o no
	private int durability;
	// aquí no em decideixo, ha de ser l'estat NUEVO, USADO, DANYADO, INSERVIBLE o algo així, o si tenir un int que depengui del material quan arribi a 0 peta
	private int sellcost;
	private int buycost;
	// no sé si cal tenir 2 o podem definir un % a world per a que ningú faci negoci compra/venta , o que hi hagi un int a material amb value...


	private List<Affect> affects;

	@Override11
	public void update() {
		// TODO Auto-generated method stub
		
	}
}

public class Material {
	public enum Materials {NADA, PAPEL, MADERA, METAL, PIEDRA, ENERGIA, MITRHILL}

	private Materials mater;
	private int durability;
	private int value;
}