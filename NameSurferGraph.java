

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
implements NameSurferConstants, ComponentListener {


	public NameSurferGraph() {
		addComponentListener(this);
	}

	//When the user presses clear, the Canvas will clear and reset the ArrayList of names to plot. 
	public void clear() {
		removeAll();
		namesToGraph = new ArrayList<>();
	}

	ArrayList <NameSurferEntry> namesToGraph = new ArrayList<>();
	//When the main program passes a new NameSurferEntry to be plotted, this method adds it to the ArrayList
	//of the names that need to be graphed. This arrayList is used to graph all the names when the screen is 
	//resized, so it clears all and regraphs all the names. 
	public void addEntry(NameSurferEntry entry) {
		namesToGraph.add(entry);
	}
	int color = -1;
	int counter = 0;
	public void deleteEntry(String n) {
		for (int i = 0; i < namesToGraph.size(); i ++) {
			NameSurferEntry a = namesToGraph.get(i);
			String b = a.getName();
			if (n.equals(b)) {
				NameSurferEntry entry = a;
				color = i;
				counter++;
				namesToGraph.remove(entry);
			}
		}
		
	}
	
	public void reset() {
		color = -1;
		counter = 0;
	}
	
	//the variable highest is the index of the name surfer with the highest average rating (lowest). 
	int highest = -1;
	
	public void reset2() {
		highest = -1;
	}
	
	//sets the index of the name in the array list with the highest rating or lowest average of ranks.
	public void highest () {
		
		double highestAverage = 1000;
		for (int i = 0; i < namesToGraph.size(); i ++) {
			double sum = 0;
			NameSurferEntry a = namesToGraph.get(i);
			for (int j = 0; j < 11; j ++) {
				int rank = a.getRank(j);
				if (!(rank > 0)) {
					sum = sum + 1000;
				} else {
				sum += a.getRank(j);
				}
			}
			double average = sum/11.0;
			if (i == 0) {
				
			}
			if (average < highestAverage) { 
				highestAverage = average;
				highest = i;
			}
			
		}
		
		
	}

	//All of the graphical objects on the screen are added on this method to allow the resize function. 
	public void update() {
		removeAll();

		double totalAvailableForGraph = this.getHeight() - (GRAPH_MARGIN_SIZE * 2);
		double unit = totalAvailableForGraph/1000;
		double decade = this.getWidth()/11;
		setBackground();

		//The following for loop will graph all the names in the ArrayList names to graph. 
		for (int i = 0; i < namesToGraph.size(); i++) {
			NameSurferEntry entry = namesToGraph.get(i);

			//For each name, it will create the 10 lines from decade to decade using another for loop. 
			for (int j = 0; j < 10; j++) {
				double x = j * decade;

				int rank = entry.getRank(j); 
				//If the rank is in the 1000, it will chose the correct first y-coordinate of the line.
				double y = GRAPH_MARGIN_SIZE + (rank * unit);
				if (rank == 0) {
					y = this.getHeight() - GRAPH_MARGIN_SIZE;
				} 

				//Then it will choose the next rank or where the y coordinate of where the line should end. 
				int rank2 = entry.getRank(j+1); 
				double yEnd = GRAPH_MARGIN_SIZE + (rank2 * unit);
				if (rank2 == 0) {
					yEnd = this.getHeight() -GRAPH_MARGIN_SIZE;
				}

				//Then it will graph the labels for each line. 
				String r = getRank(rank);
				GLabel label = new GLabel ((entry.getName() + r), x+1, y);
				
				//if the current name is the highest, it will make it change the font. 
				if (i == highest) {
					label.setFont(new Font ("Times New Roman",Font.BOLD, 15 ));
				}
				
				
				GLine current = new GLine (x, y, x + decade, yEnd);

				Color c = selectColor(i);
				current.setColor(c);
				label.setColor(c);

				add(current);
				add(label);

				//This code is to add the last line for the given entry. 
				if (j == 9 ) {
					String b = getRank(rank2);
					GLabel last = new GLabel ((entry.getName() + b), x+ decade, yEnd); {
						Color lastColor = label.getColor();
						last.setColor(lastColor);
						if (i == highest) {
							last.setFont(new Font ("Times New Roman",Font.BOLD, 15));
						}
						add(last);
					}
				}

			}

		}

	}

	//This method checks if the rank is zero (outside top 1000) to add * instead of a rank if true. 
	//If false, it just returns the rank. 
	private String getRank(int rank) {
		
		String a = " ";
		if (rank == 0) {
			a = " * ";
		} else {
			a = " " + rank;
		}
		return a;
		
	}

	//The following method will return the color the line is going to be based on the number of line.	
	private Color selectColor(int i) {
		
		//The following if statement is for the colors to remain the same when one name is deleted. 
		if (color >= 0) {
			if (i >= color) {
			i += counter;
			}
		}

		Color colorReturn = Color.BLACK;
		if (i % 4 == 0){
			colorReturn = Color.BLACK;
		}
		if (i % 4 == 1){
			colorReturn = Color.RED;
		}
		if (i % 4 == 2){
			colorReturn = Color.BLUE;
		}
		if (i % 4 == 3){
			colorReturn = Color.MAGENTA;
		}
		return colorReturn;

	}

	//The following method will draw the basic outline needed for the name surfer graph and the years. 
	private void setBackground() {

		GLine labelUp = new GLine(0, GRAPH_MARGIN_SIZE, this.getWidth(), GRAPH_MARGIN_SIZE);
		add(labelUp);
		GLine labelDown = new GLine (0, this.getHeight() - GRAPH_MARGIN_SIZE, this.getWidth(), this.getHeight() - GRAPH_MARGIN_SIZE);
		add(labelDown);

		//Adds the vertical lines
		double decade = this.getWidth()/11;
		for (int i = 0; i < 11; i ++) {
			double x = i * decade;
			GLine vertical = new GLine (x, 0, x, this.getHeight());
			add(vertical);
		}

		//Adds the years label
		GLabel firstyear = new GLabel ("1900", 0, this.getHeight() -5.0);
		add(firstyear);
		for (int j = 1; j < 10; j ++) {
			double x = j * decade;
			String year = "19" + (j*10);
			GLabel yearLabel = new GLabel (year, x, this.getHeight() - 5.0);
			add(yearLabel);
		}
		GLabel lastyear = new GLabel ("2000", (10*decade), this.getHeight() -5.0);
		add(lastyear);

	}


	/* Implementation of the ComponentListener interface for updating when the window is resized */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
