package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Container<T extends Template> {
	private HashMap<Integer, List<T>> objetos_vnum;
	private HashMap<String, List<T>> objetos_nombre;
	private Template propietario;
	
	/**
	 * @param contenedor Objeto al que pertenece el Container
	 */
	public Container(Template contenedor) {
		objetos_vnum = new HashMap<Integer, List<T>>();
		objetos_nombre = new HashMap<String, List<T>>();
		propietario = contenedor;
	}

	/**
	 * A&ntilde;ade el objeto <b>o</b> al Container.
	 * Se notifica al objeto quien es el propietario del Container
	 * @param o Objeto a a&ntilde;adir
	 */
	public void add(T o) {
		Integer vnum = o.getVnumAsInteger();
		List<T> v = objetos_vnum.get(vnum);
		
		if (v == null) {
			v = new ArrayList<T>();
			objetos_vnum.put(vnum, v);
		}
		v.add(o);
		o.setContenedor(propietario);
                
                // Guardamos tambien en el listado por nombre
                String nombre = o.getNombre();
                v = objetos_nombre.get(nombre);
                
                if (v == null) {
                    v = new ArrayList<T>();
                    objetos_nombre.put(nombre, v);
                }
                v.add(o);
	}
	
        /**
         * Obtiene el n-&eacute;simo objeto con un vnum determinado
         * @param vnum
         * @param posicion
         * @return 
         */
	public T get(Integer vnum, int posicion) {
		List<T> l_vnum = objetos_vnum.get(vnum);
                if (l_vnum == null) return null;
                
		return l_vnum.get(posicion);
	}

        /**
         * Obtiene el primer elemento con el vnum indicado
         * @param vnum
         * @return 
         */
	public T get(Integer vnum) {
		return get(vnum, 0);
	}
	
	/**
         * Obtiene el primer vnum buscando por nombre
         * @param nombre
         * @return 
         */
        public int findVnum(String nombre) {
            T obj = find(nombre);
            if (obj != null) return obj.getVnum();
            return -1;
	}
	
        /**
         * Obtiene el n-&eacute;simo objeto buscando por nombre
         * @param nombre
         * @param posicion
         * @return 
         */
        public T find(String nombre, int posicion) {
		List<T> l_nombres = objetos_nombre.get(nombre);
                if (l_nombres == null) return null;
                
		return l_nombres.get(posicion);
	}

        /**
         * Obtiene el primer objeto buscando por nombre
         * @param nombre
         * @return 
         */
	public T find(String nombre) {
		return find(nombre, 0);
	}
	
        /**
         * Elimina el primer objeto encontrado
         * @param o
         * @return 
         */
	public boolean remove(T o) {
		return remove(o, 0);
	}
	
        /**
         * Elimina la n-&eacute;sima posici&oacute;n de un objeto
         * @param o
         * @param posicion
         * @return 
         */
	public boolean remove(T o, int posicion) {
		List<T> v = objetos_vnum.get(o.getVnum());
		
		if (v == null || v.size() < posicion) return false;
		else v.remove(posicion);
		
		if (v.isEmpty()) objetos_vnum.remove(o.getVnum());
		return true;
	}
	
	/**
         * Devuelve un listado de todos los objetos y su cantidad
         * @return 
         */
        public String list() {
		String txt = "";
		List<T> lista;
		
		for (Integer vnum : objetos_vnum.keySet()) {
			lista = objetos_vnum.get(vnum);
			if (lista.size() > 1) txt += "("+lista.size()+") "; 
			txt += lista.get(0).getDescripcion()+"\r\n";
		}
		return txt;
	}	
	
        /**
         * Devuelve el listado de todos los objetos menos el indicado
         * @param t Objeto a filtrar
         * @return 
         */
	public String list(T t) {
		String txt = "";
		List<T> v;
		T o;
		
		for (Integer vnum : objetos_vnum.keySet()) {
			v = objetos_vnum.get(vnum);
			o = v.get(0);
			if (t.getVnum() != o.getVnum()) {
				if (v.size() > 1) txt += "("+v.size()+") ";
				txt += o.getDescripcion()+"\r\n";
			}
		}
		return txt;
	}
	
	public void setPropietario(Template propietario) {
		this.propietario = propietario;
	}
	
	public Template getPropietario() {
		return propietario;
	}
	
	public List<T> getAll() {
            List<T> lista = new ArrayList<T>();
            for (List<T> lv : objetos_vnum.values()) {
                lista.addAll(lv);
            }
            return lista;
	}
}
