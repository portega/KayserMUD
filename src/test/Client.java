package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import world.Habitacio;
import world.Sortida;

//TODO: IMPLEMENTAR CONNEXIO AL SERVIDOR
public class Client implements ActionListener {
	private JFrame f;
	private JTextField input;
	private JTextArea output;
	private Habitacio hab_actual;
	
	public Client() {
		f = new JFrame("Kayser MUD v1.0");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		JButton ok = new JButton("Enviar");
		ok.addActionListener(this);
		input = new JTextField("", 30);
		input.addActionListener(this);
		output = new JTextArea(20, 50);
		output.setEditable(false);
		output.setBackground(Color.BLACK);
		output.setForeground(Color.WHITE);
		JScrollPane scroll = new JScrollPane(output, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panel.add(scroll);
		f.add(panel, BorderLayout.CENTER);
		
		panel = new JPanel();
		panel.add(input);
		panel.add(ok);
		f.add(panel, BorderLayout.SOUTH);
		
		f.pack();
		f.setVisible(true);
		input.requestFocusInWindow();
		
		creaHabitacions();
		output.append(hab_actual.toString());
	}

	public void creaHabitacions() {
		Habitacio h;
		String txt = "Una habitaci\u00f2 igual que l'anterior.";
		
		hab_actual = new Habitacio("Inici", "Una petita habitaci\u00f2 amb una sortida al nord");
		h = new Habitacio("Segona habitacio", txt);
		hab_actual.setSortida(h, Sortida.Direcciones.NORTE);
		h.setSortida(new Habitacio("Habitacio nord", txt), Sortida.Direcciones.NORTE);
		h.setSortida(new Habitacio("Habitacio est", txt), Sortida.Direcciones.ESTE);
		h.setSortida(new Habitacio("Habitacio oest", txt), Sortida.Direcciones.OESTE);
	}
	
    @Override
	public void actionPerformed(ActionEvent e) {
		String txt = input.getText();
		String[] comandes={"nord", "sud", "est", "oest", "quit", "exit", "sortides"};
		int i;
		
		for (i=0; i<comandes.length; i++) {
			if (comandes[i].startsWith(txt)) {
				switch(i) {
				case 0: hab_actual = hab_actual.getSortida(Sortida.Direcciones.NORTE);
				output.setText(hab_actual.toString());
				break;
				case 1: hab_actual = hab_actual.getSortida(Sortida.Direcciones.SUR);
				output.setText(hab_actual.toString());
				break;
				case 2: hab_actual = hab_actual.getSortida(Sortida.Direcciones.ESTE);
				output.setText(hab_actual.toString());
				break;
				case 3: hab_actual = hab_actual.getSortida(Sortida.Direcciones.OESTE);
				output.setText(hab_actual.toString());
				break;
				case 4:
				case 5: System.exit(0);
				break;
				case 6: output.setText(hab_actual.toString());
				break;
				default: output.setText("Comanda desconeguda");
				break;
				}
				break;
			}
		}
		
		input.setText("");
		input.requestFocusInWindow();
	}

	public static void main(String[] args) {
		new Client();

	}

}
