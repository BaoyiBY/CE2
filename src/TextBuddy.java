import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextBuddy {

	private static final int FILELENGTH = 4;
	private static ArrayList<String> textList = new ArrayList<String>();

	// The possible commands
	public enum Commands {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SEARCH, SORT;
	};

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		
		String fileName = createOrLoadFile(args);
		displayWelcomeMessage(fileName);
		executeCommandUntilExit(fileName);

	}

	// This method is use to display the welcome message
	private static void displayWelcomeMessage(String fileName) {
		System.out.println("Welcome to TextBuddy. " + fileName
				+ " is ready for use");
	}

	// This method will check if there is a argument if there is no argument it
	// will exit
	private static void exitIfNoArgument(String[] args) {
		if (!(args.length > 0)) {
			System.out.println("There is no arguments.");
			System.exit(0);
		}
	}

	/*
	 * This method is use to check if user are using the right file format if
	 * file format is incorrect, it will exit the program.
	 */
	private static void exitIfWrongFormat (String fileName,String[] args) {

		if (!fileName.contains(".")) {
			System.out.println("Wrong File Format!");
			System.exit(0);
		}
		int dotIndex = args[0].indexOf(".");
		String fileFormat = args[0].substring(dotIndex, args[0].length());
		if (!(fileFormat.length() == FILELENGTH && fileFormat.equalsIgnoreCase(".txt"))) {
			System.out.println("Wrong File Format");
			System.exit(0);
		}
	}

	// This method is to check whether to create or load file
	public static String createOrLoadFile(String[] args) {

		exitIfNoArgument(args);
		String fileName = args[0];
		exitIfWrongFormat(fileName,args);

		File filepath = new File(args[0]);
		if (filepath.exists() && !filepath.isDirectory()) {
			// file exist
			// call and load the contents to arraylist
			loadFile(fileName);
		} else {
			// The file does not exist.
			// Create file
			try {
				filepath.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileName;

	}

	// This method is use to to load from textlist to the arraylist
	private static void loadFile(String fileName) {
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader bufferRead = new BufferedReader(reader);
			String txtLine = "";
			try {
				while ((txtLine = bufferRead.readLine()) != null) {
					textList.add(txtLine);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	// This method is to execute the user command until it exit
	private static void executeCommandUntilExit(String fileName) {
		String[] input;
		System.out.print("command: ");

		while (true) {
			input = sc.nextLine().trim().split(" ");
			printMessageInput(commandExecution(fileName,input));
			System.out.print("command: ");
		}
	}

	public static String commandExecution(String fileName,String[] input) {
		Commands cmd = determineCommand(input);

		switch (cmd) {
		
		case ADD: {
			return executeAdd(fileName,input);
		}
		case DISPLAY: {
			return executeDisplay(fileName, input);
		}
		case DELETE: {
			return executeDelete(fileName,input);
		}
		case CLEAR: {
			return executeClear(fileName, input);
		}
		case INVALID: {
			return "Invalid Command!";
		}
		case SEARCH: {
			return executeSearch(input);
		}
		case SORT: {
			return executeSort(fileName);
		}
		case EXIT: {
			System.exit(0);
			break;
		}
		default: {

			return "Command Unrecognized!";
		}
		}

		return "No command entered";
	}

	/*
	 * This method is use to determine the type of command
	 */
	private static Commands determineCommand(String[] commandType) {
		if (commandType == null || commandType.length == 0) {
			//throw new Error("Command type cannot be null");
			return Commands.INVALID;
		}
		if (commandType[0].equalsIgnoreCase("add")) {
			return Commands.ADD;
		} else if (commandType[0].equalsIgnoreCase("display")) {
			return Commands.DISPLAY;
		} else if (commandType[0].equalsIgnoreCase("delete")) {
			return Commands.DELETE;
		} else if (commandType[0].equalsIgnoreCase("clear")) {
			return Commands.CLEAR;
		} else if (commandType[0].equalsIgnoreCase("exit")) {
			return Commands.EXIT;
		} else if (commandType[0].equalsIgnoreCase("search")) {
			return Commands.SEARCH;
		} else if (commandType[0].equalsIgnoreCase("sort")) {
			return Commands.SORT;
		} else {
			return Commands.INVALID;
		}
	}

	// This method is use to add to arraylist and add to file
	private static String executeAdd(String fileName,String[] input) {
		String txtToAdd = "";// declare a string to put the words into it

		// loop and write it to txtToAdd
		// I start the loop from 1, because I do not want to add in the command which is place at input array of 0.
		for (int i = 1; i < input.length; i++) {
			txtToAdd += input[i];// append or add on

			// check if can add a space after the last letter
			if ((i + 1) != input.length)
				txtToAdd += " ";
		}
		textList.add(txtToAdd);
		writeToFile(fileName);
		return ("added to " + fileName + ": \"" + txtToAdd + "\"");
	}

	// This method is to display the command
	private static String executeDisplay(String fileName, String[] input) {
		if(input.length != 1)
		{
			return "Please remove additional content";
		}

		String text = new String();

		if (textList.isEmpty())
			return (fileName + " is empty");

		for (int i = 0; i < textList.size(); i++) {
			text += ((i + 1) + ". " + textList.get(i));

			if ((i + 1) != textList.size())
				text += "\n";
		}

		return text;
	}

	/*
	 * This method is to check which command to be deleted and print the deleted
	 * command out
	 */
	private static String executeDelete(String fileName,String[] input) {
		if(input.length != 2){
			return "Incorrect Argument!";
		}
		
		if(!isIntegerBoolean(input[1])){
			return "Please enter a number to delete!";
		}
		
		int textListIndex = Integer.parseInt(input[1]);
		if (textListIndex > 0 && textListIndex <= textList.size()) {
			String removedText = textList.get(textListIndex - 1);
			textList.remove(textListIndex - 1);
			writeToFile(fileName);
			return ("deleted from " + fileName +": \"" + removedText + "\"" );
		}

		return "This item do not exist!";
	}
	
	private static boolean isIntegerBoolean(String input){
		try{
			Integer.parseInt(input);
		} catch (NumberFormatException e){
			return false;
		}
		
		return true;
	}


	private static String executeClear(String fileName, String[] input) {
		if(input.length != 1){
			return "Please remove additional text";
		}
		
		
		clearArrayList();// clear all contents from arraylist
		try {
			FileWriter fw = new FileWriter(fileName);// setup a file writer with
														// nothing inside
			fw.close();
			return ("all content deleted from " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Nothing to clear in the file";
	}

	/** I use public, so I can use for unit testing
	 * 
	 */
	public static void clearArrayList() {
		textList.clear();
	}
	
	
	
	private static String executeSort(String fileName) {
		Collections.sort(textList);
		writeToFile(fileName);
		return "Text List Sorted";
	}

	private static String executeSearch(String[] item) {
		
		String searchItem = new String();
		int i = 0;
		
		while(i < textList.size())
		{
			//Contains(item[1]), reason because, I passed in the whole command line, so for item[0] will be the search command.
			if(textList.get(i).contains(item[1]))
			{
				searchItem += ((i + 1) + ". " + textList.get(i));

				if ((i + 1) != textList.size()){
					searchItem += "\n";
				}
			}
			
			i++;
		}
		
		if(searchItem.isEmpty())
			return "No such item";
		
		return searchItem;
	}

	/*
	 * This method is use to write the arraylist contents into the textfile.
	 */
	private static void writeToFile(String fileName) {
		// this try catch statement will capture the behavior of the file writer
		// and also add the string to the file
		try {
			FileWriter fw = new FileWriter(fileName);// setup a file writer
			fw.flush();
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < textList.size(); i++) {
				bw.write(textList.get(i).toString());
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void printMessageInput(String messageInput) {
		System.out.println(messageInput);
	}
}
