//This file will create the NameSurferEntry variable that can later be used in graph to get its ranks
//and its name to be able to graph the name. It gets the information or dataline it needs to create 
//the name surfer entry from the database and sends the new entry to the graph. 

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	//The following will create the instance variables needed for the methods. The ranks will be 
	//stored in an array for easy retrieval. 
	String name;
	int[] ranks = new int[11];

	//This constructor will initalize the instance variables given the dataline from find entry method. 
	public NameSurferEntry(String dataLine) {

		Scanner input = new Scanner(dataLine);
		name = input.next();
		System.out.println(name);
		for (int i = 0; i < 11; i ++) {
			ranks[i] = input.nextInt();
		}
		input.close();

	}

	//This file gets the uppercase version of the name and returns the normal case version. 
	public String getName() {

		String newName = "";
		newName += name.charAt(0);
		for (int i = 1; i < name.length(); i ++) {
			char ch = name.charAt(i);
			char n = Character.toLowerCase(ch);
			newName += n;
		}
		return newName;	

	}


	//This method returns the rank for a given year (decade since 1900) using the array of ranks. 
	public int getRank(int decadesSince1900) {

		if (decadesSince1900 <= 10){
			return ranks[decadesSince1900];
		} 
		return -1;	

	}

	//The following method will return the string version of a name with the ranks inside [] as 
	//specified in the handout. 
	public String toString() {

		String print = getName() + " [";
		print += ranks[0];
		for (int j = 1; j < 11; j ++) {
			print += " " + ranks[j]  ;
		}
		print += "]";

		return print;

	}

}

