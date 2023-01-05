package alathra_bingo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		ArrayList<String> bingoList = new ArrayList<String>();
		String[][] bingoMap = new String[5][5];
		int count = 0;
		
		if(args.length < 1) {
			System.out.println("No text file provided, exiting.");
			return;
		}
		try {
			// Creates a file object
			File bingoTextFile = new File(args[0]);
			// trys to create a scanner out of the text file
			Scanner Reader = new Scanner(bingoTextFile);
				while (Reader.hasNextLine()) {
					count++;
					String data = Reader.nextLine();
					bingoList.add(data);
				}
			
				Reader.close();
			
		}
		catch (FileNotFoundException e) {
			System.out.println("The file was not found.");
			e.printStackTrace();
		}
		
		// if there's only 24 it will add a free slot
		if (count < 24) {
			System.out.println("Not enough strings in %s.".formatted(args[0]));
			return;
		}
		
		// initializing random class
		Random random_method = new Random();
		
		for(int i = 0; i < 25; i++) {
			// If the list size is 24, set the middle to free and the bottom right to the middle
			if(i == 24 && bingoList.size() == 0) {
				String temp = bingoMap[2][2];
				bingoMap[2][2] = "Free";
				bingoMap[4][4] = temp;
			}
			int index = random_method.nextInt(bingoList.size());
			
			// Get random string from bingo list
			String entry = bingoList.get(index);
			// remove string from list
			bingoList.remove(index);
			
			bingoMap[i/5][i%5] =  entry;
			
			
		}
		
		if (args.length > 1) {
			// Going to grab all arguments beside the first, and split them into pairs
			String[][] flagged_items = new String[args.length-1][2];
			for(int i = 1; i < args.length; i++) {
				// Sets the array inside flagged items to the split of an arg around :
				flagged_items[i-1] = args[i].split(":");
			}
			for(String[] arg: flagged_items) {
				try {
					if(arg.length > 1) {
						//slot from the users perspective, so it starts at 1 instead of 0 
					int slot = Integer.parseInt(arg[0]) -1;
					bingoMap[slot/5][slot%5] = arg[1];
					System.out.println("Successfully replaced spot %d with %s".formatted(slot, arg[1]));
					} else {
						System.out.println("Incorrect arg formatting with %s".formatted(args[0]));
						return;
					}
				} catch(NumberFormatException e) {
					System.out.println("The value inputted as an argument '%s' was not parsable as an integer.".formatted(args[0]));
					e.printStackTrace();
				};
			}
		}
		
		
		
		
		// Creates and writes to an output file
		try {
			File output = new File("output.csv");
			if(output.createNewFile()) {
				System.out.println("File created: %s".formatted(output.getName()));
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		try {
			FileWriter outputWriter = new FileWriter("output.csv");
			outputWriter.write("\"Alathra Bingo\"\n"); //Adding title
			outputWriter.write("\"made by BrokenMegatron88\"\n"); //Adding Attributions
			for(String[] row: bingoMap) {
				for(String item: row) {
					outputWriter.write("\"%s\",".formatted(item));
				}
				outputWriter.write("\n");
			}
			outputWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		
		
		
			
		
	}
	
	
}
