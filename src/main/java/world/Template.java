package world;

import lombok.Data;

@Data
public abstract class Template {
	private String name, shortDescription, description;
	private Template owner;
	private int vnum;
	//private HashMap<String, String> modificadors;

	public Integer getVnumAsInteger() {
		return Integer.valueOf(vnum);
	}
	public abstract void update();
}
