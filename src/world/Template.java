package world;

import java.util.HashMap;

public abstract class Template {
	private String nombre, descripcion;
	private int volumen;
	private int vnum;
	private Template contenedor;
	private HashMap<String, String> modificadors;
	protected Long Id;

	public Template() {
		modificadors = new HashMap<String, String>();
	}
	
	public void setContenedor(Template contenedor) {
		this.contenedor = contenedor;
	}

	public Template getContenedor() {
		return contenedor;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public int getVolumen() {
		return volumen;
	}
	
	public void setVolumen(int volumen) {
		this.volumen = volumen;
	}
	
	public int getVnum() {
		return vnum;
	}
	
	public Integer getVnumAsInteger() {
		return new Integer(vnum);
	}
	
	public void setVnum(int vnum) {
		this.vnum = vnum;
	}

	public void addModificador(String nom, String valor) {
		modificadors.put(nom, valor);
	}

	public String getModificador(String nom) {
		return modificadors.get(nom);
	}
	
	public abstract void update();

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}
}
