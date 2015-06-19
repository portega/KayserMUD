package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class TestTreeMap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TreeMap<String, String> diccionari;
		Map.Entry<String, String> clau;
		
		diccionari = new TreeMap<String, String>();
		
		/*diccionari.put("cast", "CONJURAR");
		diccionari.put("conjurar", "CONJURAR");*/
		diccionari.put("look", "MIRAR");
		diccionari.put("mirar", "MIRAR");
		/*diccionari.put("map", "MAPA");
		diccionari.put("mapa", "MAPA");
		diccionari.put("quien", "QUIEN");
		diccionari.put("quit", "SALIR");
		diccionari.put("este", "ESTE");
		diccionari.put("exit", "SALIR");*/
		
		String inputLine, outputLine;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			while ((inputLine = in.readLine()) != null) {

				if (inputLine.length() > 0) {
					clau = diccionari.ceilingEntry(inputLine);
					outputLine = "["+clau.getKey()+","+clau.getValue()+"]";
					System.out.print(outputLine);
				}
			}
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
