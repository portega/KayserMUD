package world;

import server.Constantes;
import server.Control;

public class Player extends Template {
	private Race raza;
	private Gender sexo;
	private int gold;
	private int nivel;
	private Container equipo;
	private Container inventario;
	private Control control;
	private int vida, maxVida, mana, maxMana, move, maxMove;
	private Constantes.Estados estado;
	private Player victim;
	private int damm;
	private long fecha_nacimiento;

	public Player() {
		inventario = new Container(this);
		equipo = new Container(this);
		estado = Constantes.Estados.NORMAL;
		fecha_nacimiento = System.currentTimeMillis();
	}

	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public int getEdad() {
		long ahora = System.currentTimeMillis();
		return (int)(fecha_nacimiento - ahora)/Constantes.HORAS;
	}
	public Race getRaza() {
		return raza;
	}
	public void setRaza(Race raza) {
		this.raza = raza;
	}
	public void addInventario(Template obj) {
		inventario.add(obj);
	}
	public void removeInventario(Template obj) {
		inventario.remove(obj);
	}
	public String getInventario() {
		return inventario.list();
	}
	
	public Template findObjeto(String nombre) {
		return inventario.find(nombre);
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public Control getControl() {
		return control;
	}

	

	@Override
	public void update() {
		// TODO Auto-generated method stub
		switch(estado) {
		case FIGHT:
			
			break;
		}
	}

	public void setEstado(Constantes.Estados estado) {
		this.estado = estado;
	}

	public Constantes.Estados getEstado() {
		return estado;
	}

	public void setVictim(Player victim) {
		this.victim = victim;
	}

	public Player getVictim() {
		return victim;
	}

	public void setDamm(int damm) {
		this.damm = damm;
	}

	public int getDamm() {
		return damm;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getMaxVida() {
		return maxVida;
	}

	public void setMaxVida(int maxVida) {
		this.maxVida = maxVida;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}

	public int getMaxMove() {
		return maxMove;
	}

	public void setMaxMove(int maxMove) {
		this.maxMove = maxMove;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getGold() {
		return gold;
	}

	public void setSexo(Gender sexo) {
		this.sexo = sexo;
	}

	public Gender getSexo() {
		return sexo;
	}

        // Metodos de equipo
        public void addEquipo(Template obj) {
		equipo.add(obj);
	}
	public void removeEquipo(Template obj) {
		equipo.remove(obj);
	}
	public String getEquipo() {
		return equipo.list();
	}

        public Template findEquipo(String nombre) {
		return equipo.find(nombre);
	}
}
