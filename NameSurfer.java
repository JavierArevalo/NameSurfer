//Assignment 6: NameSurfer
//By: Javier Arevalo
//Section: Thursdays 330
//This extension allows the user to deletes the name he specifies  in the textfield while the other
//names already plotted remain the same (color does not change). It also allows to to find the highest rating
//on average of all the names in the screen, or lowest average of ranks. The highest name will be shown in a bold font. 

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

//This is the main program for NameSurfer. It responds to the user interactions and redirects 
//the tasks to the other classes of NameSurfer. 
public class NameSurfer extends Program implements NameSurferConstants {

	//The following are the instance variables used in the action performed events. 
	JTextField name;
	NameSurferDatabase one;
	NameSurferGraph background;

	//This code sets the buttons and their actions to react appropriately. 
	public void init() {
		JLabel intro = new JLabel("Name: ");
		add(intro, NORTH);
		name = new JTextField(TEXT_FIELD_WIDTH);
		add(name, NORTH);
		name.addActionListener(this);
		name.setActionCommand("Graph");
		JButton graph = new JButton("Graph");
		add(graph, NORTH);
		JButton clear = new JButton("Clear");
		add(clear, NORTH);
		JButton delete = new JButton("Delete");
		add(delete, NORTH);
		JButton highest = new JButton("Highest on average");
		add(highest, NORTH);
		addActionListeners();
		one = new NameSurferDatabase("res/names-data.txt");
		background = new NameSurferGraph();
		add(background);
		background.update();
		
	}

	//The following code reacts to what the user enters. If the user enters a name and graphs/presses enter,
	//the code will create the NameSurferEntry for that name given the dataline from the database, and
	//send it to the graph to be plotted. If the user presses clear, the whole graph and names will be cleared. 
	public void actionPerformed(ActionEvent event) {
		
		if (event.getActionCommand().equals("Graph")) {
			String text = name.getText();
			NameSurferEntry current = one.findEntry(text);
			if (current != null) {
				background.addEntry(current);
				background.update();
			}
		}
		
		if (event.getActionCommand().equals("Clear")) {
			background.clear();
			background.update();
		}
		
		if (event.getActionCommand().equals("Delete")) {
			String n = name.getText();
			String newName = "";
			char h = n.charAt(0);
			char g = Character.toUpperCase(h);
			newName += g;
			for (int i = 1; i < n.length(); i ++) {
				char ch = n.charAt(i);
				char a = Character.toLowerCase(ch);
				newName += a;
			}
			n = newName;
				background.deleteEntry(n);
				background.update();
				background.reset();
			
		}
		
		if (event.getActionCommand().equals("Highest on average")) {
			background.highest();
			background.update();
			background.reset2();
		}
		
	}
	
}