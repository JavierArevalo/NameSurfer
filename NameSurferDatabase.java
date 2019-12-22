

import java.io.*;
import java.util.*;

public class NameSurferDatabase implements NameSurferConstants {
	HashMap <String, String> database = new HashMap<>();

	//This constructor scans the data from the file and stores it in a Hashmap for easy retrieval. 
	//The key is the name, and the list of ranks are the value. The findEntry  uses information from 
	//this database and combines the key-value into one line to pass it on to the graph as a nameSurferEntry. 
	public NameSurferDatabase(String filename) {

		try {
			Scanner input = new Scanner(new File(filename));
			while (input.hasNextLine()) {
				//String line = input.nextLine();
				String word = input.next();
				String Cword = word.toUpperCase();
				String numbers = "";
				while (input.hasNextInt()) {
					int n = input.nextInt();
					numbers += n + " ";
				}
				database.put(Cword, numbers);
				input.nextLine();
			}
			input.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	//This method will receive the text entered by the user. When it receives it it will find it in
	//the database, get its ranks and then combine the name with the ranks into one string/dataline. 
	//Then it will return a new NameSurferEntry given the correct format of the dataline. If the name
	//is not in the hashmap, it means it is not in the database so the program does nothing.
	public NameSurferEntry findEntry(String name) {

		String Cname = name.toUpperCase();
		if (database.containsKey(Cname)) {
			String numbers = database.get(Cname);
			String dataline = Cname + " " + numbers;
			return new NameSurferEntry(dataline);
		}

		return null;
	}
}

